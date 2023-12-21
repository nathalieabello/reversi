import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import controller.ReversiController;
import gui.GUIPanel;
import gui.GUIPanelHintDecorator;
import gui.GUIView;
import gui.GUIViewHintDecorator;
import gui.ReversiGUIHexPanel;
import gui.ReversiGUISquarePanel;
import gui.ReversiGUIView;
import model.board.IModel;
import model.board.ReadOnlyModel;
import model.board.ReadOnlyReversiModel;
import model.board.HexReversiModel;
import model.board.SquareReversiModel;
import model.cell.DiscColor;
import player.AIPlayer;
import player.HumanPlayer;
import player.Player;
import strategy.AIStrategy;
import strategy.AvoidCornerNeighbors;
import strategy.CaptureMostCells;
import strategy.PrioritizeCorners;
import strategy.TryToWinCompositeStrategy;

/**
 * The main class for the full Reversi game with an implemented controller.
 */
public class ReversiWithController {
  private static int VERSION = 0;

  /**
   * The main method for the full Reversi game with an implemented controller.
   *
   * @param args the command line arguments.
   */
  public static void main(String[] args) {
    ArrayList<DiscColor> playerColors = new ArrayList<>();
    playerColors.add(DiscColor.BLACK);
    playerColors.add(DiscColor.WHITE);

    IModel actionableModel = setUpModel(playerColors);
    ReadOnlyModel model = new ReadOnlyReversiModel(actionableModel);

    List<Player> players = setUpPlayers(playerColors, model);

    Player player1 = players.get(0);
    Player player2 = players.get(1);

    List<GUIView> views = setUpViews(model);
    GUIView viewPlayer1 = views.get(0);
    GUIView viewPlayer2 = views.get(1);

    new ReversiController(actionableModel, player1, viewPlayer1);
    new ReversiController(actionableModel, player2, viewPlayer2);
    model.startGame();
  }

  /**
   * Sets up the views for the game using the command line.
   *
   * @param model is the read only model to be used.
   * @return the list of views.
   */
  private static List<GUIView> setUpViews(ReadOnlyModel model) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Would you like the option to see hints?\n" +
            "Type y for yes or n for no.");

    // Continuously prompt until valid input is provided
    while (!scanner.hasNext("y") && !scanner.hasNext("n")) {
      System.out.println("Invalid input. Please type y for yes or n for no.");
      scanner.next(); //go to next token.
    }

    if (scanner.next().equals("y")) {
      scanner.close();
      return setUpHintViews(model);
    } else {
      scanner.close();
      return setUpOriginalViews(model);
    }
  }

  /**
   * Sets up the views without hints for the game using the command line.
   *
   * @param model is the read only model to be used.
   * @return the list of views.
   */
  private static List<GUIView> setUpOriginalViews(ReadOnlyModel model) {
    GUIPanel panel1;
    GUIPanel panel2;
    if (VERSION == 1) { // Hexagonal Reversi
      panel1 = new ReversiGUIHexPanel(model);
      panel2 = new ReversiGUIHexPanel(model);
    } else { // Square Reversi
      panel1 = new ReversiGUISquarePanel(model);
      panel2 = new ReversiGUISquarePanel(model);
    }
    return Arrays.asList(new ReversiGUIView(model, panel1),
            new ReversiGUIView(model, panel2));
  }

  /**
   * Sets up the views with hints for the game using the command line.
   *
   * @param model is the read only model to be used.
   * @return the list of views.
   */
  private static List<GUIView> setUpHintViews(ReadOnlyModel model) {
    GUIPanel panel1;
    GUIPanel panel2;
    if (VERSION == 1) { // Hexagonal Reversi
      panel1 = new GUIPanelHintDecorator(new ReversiGUIHexPanel(model));
      panel2 = new GUIPanelHintDecorator(new ReversiGUIHexPanel(model));
    } else { // Square Reversi
      panel1 = new GUIPanelHintDecorator(new ReversiGUISquarePanel(model));
      panel2 = new GUIPanelHintDecorator(new ReversiGUISquarePanel(model));
    }
    return Arrays.asList(new GUIViewHintDecorator(new ReversiGUIView(model, panel1)),
            new GUIViewHintDecorator(new ReversiGUIView(model, panel2)));
  }

  /**
   * Sets up the players for the game using the command line.
   *
   * @param playerColors the colors of the players.
   * @param model        is the read only model to be used.
   * @return the list of players.
   */
  private static List<Player> setUpPlayers(List<DiscColor> playerColors, ReadOnlyModel model) {
    Scanner scanner = new Scanner(System.in);
    HashMap<List<Integer>, AIStrategy> levelMap = populateAILevelMap(playerColors, model);

    List<Player> players = setUpCandidatePlayers(playerColors, model, scanner, levelMap);

    System.out.println("Press 0 to continue or r to reselect the players.");
    // Continuously prompt until the user enters "0" or "r"
    while (!scanner.hasNext("0") && !scanner.hasNext("r")) {
      System.out.println("Invalid input. Please type 0 to start the game or r to reselect.");
      scanner.next(); //go to next token.
    }

    if (scanner.next().equals("r")) {
      return setUpPlayers(playerColors, model);
    } else {
      return players;
    }
  }

  /**
   * Populates the AI level map.
   *
   * @param playerColors the colors of the players.
   * @param model        the model to be used.
   * @return the AI level map.
   */
  private static HashMap<List<Integer>, AIStrategy> populateAILevelMap(
          List<DiscColor> playerColors, ReadOnlyModel model) {
    HashMap<List<Integer>, AIStrategy> aiLevelMap = new HashMap<>();
    for (int i = 0; i < playerColors.size(); i++) {
      aiLevelMap.put(Arrays.asList(i, 1), new CaptureMostCells(playerColors.get(i), model));
      aiLevelMap.put(Arrays.asList(i, 2),
              new TryToWinCompositeStrategy(new PrioritizeCorners(playerColors.get(i), model),
                      new CaptureMostCells(playerColors.get(i), model)));
      aiLevelMap.put(Arrays.asList(i, 3),
              new TryToWinCompositeStrategy(new PrioritizeCorners(playerColors.get(i), model),
                      new TryToWinCompositeStrategy(new AvoidCornerNeighbors(playerColors.get(i),
                              model), new CaptureMostCells(playerColors.get(i), model))));
    }
    return aiLevelMap;
  }

  /**
   * Sets up the candidate players.
   *
   * @param playerColors the colors of the players.
   * @param model        the model to be used.
   * @param scanner      the scanner to be used.
   * @param aiLevelMap   the AI level map to be used.
   * @return the list of players.
   */
  private static List<Player> setUpCandidatePlayers(List<DiscColor> playerColors,
                                                    ReadOnlyModel model, Scanner scanner,
                                                    HashMap<List<Integer>, AIStrategy> aiLevelMap) {
    List<Player> players = new ArrayList<>();
    for (int playerNum = 0; playerNum < playerColors.size(); playerNum++) {
      String chosenPlayer = selectPlayerType(scanner, playerNum);

      switch (chosenPlayer) {
        case "h":  // human player
          players.add(new HumanPlayer(playerColors.get(playerNum), model));
          break;
        case "a":  // AI player
          int level = selectAILevel(scanner);

          players.add(new AIPlayer(playerColors.get(playerNum),
                  aiLevelMap.get(Arrays.asList(playerNum, level))));
          break;
        case "r":  //reselect
          return setUpPlayers(playerColors, model);
        default:
          // nothing should happen
      }
    }
    return players;
  }

  /**
   * Selects the player type:
   * human or AI.
   *
   * @param scanner   the scanner to be used.
   * @param playerNum the player number.
   * @return the player type.
   */
  private static String selectPlayerType(Scanner scanner, int playerNum) {
    System.out.println("What do you want player " + (playerNum + 1) + " to be?\n"
            + "Type h for human or a for AI.\n" +
            "Type r to reselect the players.");

    // Continuously prompt until valid input is provided
    while (!scanner.hasNext("[har]")) {
      System.out.println("Invalid input. Please type h for human or a for AI, " +
              "or r to reselect the players.");
      scanner.next(); //go to next token.
    }

    return scanner.next();
  }

  /**
   * Selects the AI level:
   * easy, medium, hard, or expert.
   *
   * @param scanner the scanner to be used.
   * @return the AI level.
   */
  private static int selectAILevel(Scanner scanner) {
    System.out.println("How difficult do you want the AI player to be?\n" +
            "1. Easy\n" +
            "2. Medium\n" +
            "3. Hard");

    // Continuously prompt until a valid integer is provided
    while (!scanner.hasNext("[123]")) {
      System.out.println("Invalid input. Please type a valid number difficulty.");
      scanner.next(); //go to next token.
    }

    return scanner.nextInt();
  }

  /**
   * Sets up the model for the game using the command line.
   *
   * @param playerColors the colors of the players.
   * @return the model.
   */
  private static IModel setUpModel(List<DiscColor> playerColors) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("What version of the game would you like to play?\n" +
            "1. Hexagonal Reversi\n" +
            "2. Square Reversi");
    // Continuously prompt until a valid model is provided
    while (!scanner.hasNext("[12]")) {
      System.out.println("Invalid input. Please type a valid model version number.");
      scanner.next(); //go to next token.
    }

    VERSION = scanner.nextInt();
    if (VERSION == 1) { // Hexagonal Reversi
      return new HexReversiModel.HexBuilder()
              .setLayers(4)
              .setPlayerColors(playerColors)
              .build();
    } else { // Square Reversi
      return new SquareReversiModel.SquareBuilder()
              .setLayers(4)
              .setPlayerColors(playerColors)
              .build();
    }
  }
}