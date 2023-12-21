package controller;

import java.awt.Component;
import java.util.Objects;
import java.util.Optional;

import javax.swing.JOptionPane;

import gui.GUIView;
import model.board.Coordinate;
import model.board.IModel;
import model.cell.DiscColor;
import player.HumanPlayer;
import player.Player;
import ruleskeeper.ReversiRuleKeeper;
import ruleskeeper.RuleKeeper;

/**
 * Represents a ReversiController that can run a game of Reversi.
 */
public class ReversiController implements IController {
  private final IModel model;
  private final Player player;
  private final GUIView view;
  private final RuleKeeper ruleKeeper;
  private boolean firstRun;
  public static boolean EXIT;
  private final boolean showAlerts;

  /**
   * Constructs a ReversiController, where the player can be a human or an AI.
   * The view is a GUIView, and the model is an IModel.
   * The alerts are shown by default.
   *
   * @param model  is the model to be used.
   * @param player is the player to be used.
   * @param view   is the view to be used.
   */
  public ReversiController(IModel model, Player player, GUIView view) {
    this(model, player, view, true);
  }

  /**
   * Constructs a ReversiController, where the player can be a human or an AI.
   * The view is a GUIView, and the model is an IModel.
   * The alerts are shown if showAlerts is true.
   *
   * @param model      is the model to be used.
   * @param player     is the player to be used.
   * @param view       is the view to be used.
   * @param showAlerts is whether to show alerts or not.
   */
  public ReversiController(IModel model, Player player, GUIView view, boolean showAlerts) {
    if (model == null || player == null || view == null) {
      throw new IllegalArgumentException("Arguments cannot be null.");
    }
    this.model = model;
    this.model.addModelListener(this);
    this.player = player;
    this.ruleKeeper = new ReversiRuleKeeper();
    this.view = view;
    this.view.addViewListener(this);
    this.view.setPlayer(player);

    this.view.setVisible(true);

    this.firstRun = true;
    this.showAlerts = showAlerts;
  }

  /**
   * Runs the game of Reversi.
   * This method is called when the game is started, and it is called again every time a move is
   * made, or the model's state has changed.
   */
  @Override
  public void run() {
    if (!this.firstRun) {
      this.view.updateCanvas(false);
    } else {
      this.firstRun = false; //no longer first run.
    }
    if (!this.model.isGameOver()) {
      if (this.model.getTurn() == this.player.getPlayerColor()) { // check turn
        if (this.makeControllerMoveForHuman()) {
          this.showYourTurn(); // notify player that it is their turn
        }
        if (this.tryToMakeMove()) {
          this.view.updateCanvas(false); // update view
          this.model.notifyModelHasChanged();
        }
      }
    } else {
      this.showGameOver(); // show game over
      this.view.setVisible(false);
      if (!EXIT) {
        EXIT = true;
      } else {
        System.exit(0);
      }
    }
  }

  /**
   * Tries to make a move and returns true if the move was made, false otherwise.
   * If the move is valid, then the move is made, the model is updated, the view is updated,
   * and the controllers are notified that the model has changed.
   * If the move is invalid, then the player is notified that their move was invalid.
   * If the move is not the player's turn, then the player is notified that it is not their turn.
   * If the player has not selected a cell, then the player is notified that no cell is selected.
   *
   * @return true if the move was made, false otherwise.
   * @throws IllegalStateException    if the player has not selected a cell.
   * @throws IllegalArgumentException if the move is invalid.
   */
  private boolean tryToMakeMove() {
    try {
      Optional<Coordinate> optCoord = this.player.play(this.model); // try to get a move.
      if (this.makeControllerMoveForHuman()) { // reset move after it is chosen if human player.
        ((HumanPlayer) this.player).resetMove();
      }

      if (this.model.getTurn() == this.player.getPlayerColor()) { //check turn before move.
        return this.makeMove(optCoord);
      } else {
        if (this.view.getSelectedCell() != null) {
          this.view.updateCanvas(true); //deselect the cell.
        }
        // notify player that it is not their turn/ that they cannot make a move for the AI.
        if (this.makeControllerMoveForHuman()) {
          this.showNotYourTurn();
        } else {
          this.showCannotMakeMoveForAI();
        }
      }
    } catch (IllegalStateException e) {
      if (this.makeControllerMoveForHuman()) {
        this.showNoSelectedCell(); // notify player that no cell is selected.
      }
    } catch (IllegalArgumentException e) {
      // a move has not been made yet by the human player.
    }
    return false;
  }

  /**
   * Makes a move based on the given optional coordinate, and updates the model and view.
   *
   * @param optCoord is the optional coordinate to make a move at.
   * @return true if the move was made, false otherwise.
   */
  private boolean makeMove(Optional<Coordinate> optCoord) {
    if (optCoord.isEmpty()) { //no move -> pass.
      this.model.pass(); //perform a pass.
      System.out.println("PASS move made by "
              + this.player.getPlayerColor().name() + " player.");
      return true;
    } else { // want to move --> check if move is valid
      Coordinate coord = optCoord.get(); // get coordinate
      if (ruleKeeper.isValid(this.model, coord, this.player.getPlayerColor())
              || !this.makeControllerMoveForHuman()) {
        this.model.playDisc(coord); //perform the move if it is valid.
        System.out.println("PLAY DISC move made by "
                + this.player.getPlayerColor().name() + " player.");
        return true;
      } else {
        this.view.updateCanvas(true); //deselect the cell.
        this.showInvalidMove(); // notify player that move is invalid.
        return false;
      }
    }
  }

  /**
   * Shows a message dialog that it is not the player's turn.
   */
  private void showNotYourTurn() {
    System.out.println("SHOW Not your turn for "
            + this.player.getPlayerColor().name() + " player.");
    if (this.showAlerts) {
      JOptionPane.showMessageDialog((Component) this.view,
              "It is not your turn. Wait for other player's move.",
              "WAIT", JOptionPane.ERROR_MESSAGE);
    }
  }

  /**
   * Shows a message dialog that you cannot make a move on the AI player's part.
   */
  private void showCannotMakeMoveForAI() {
    System.out.println("SHOW Cannot make move for AI");
    if (this.showAlerts) {
      JOptionPane.showMessageDialog((Component) this.view,
              "You cannot make a move for your AI.",
              "SORRY...", JOptionPane.ERROR_MESSAGE);
    }
  }

  /**
   * Shows a message dialog that no cell is selected.
   */
  private void showNoSelectedCell() {
    System.out.println("SHOW No selected cell for "
            + this.player.getPlayerColor().name() + " player.");
    if (this.showAlerts) {
      JOptionPane.showMessageDialog((Component) this.view,
              "Cannot move. No selected cell. Try again.",
              "NO SELECTION", JOptionPane.ERROR_MESSAGE);
    }
  }

  /**
   * Shows a message dialog that the move is invalid.
   */
  private void showInvalidMove() {
    System.out.println("SHOW Invalid move for "
            + this.player.getPlayerColor().name() + " player.");
    if (this.showAlerts) {
      JOptionPane.showMessageDialog((Component) this.view, "Invalid move. Try again.",
              "INVALID MOVE", JOptionPane.ERROR_MESSAGE);
    }
  }

  /**
   * Shows a message dialog that it is the player's turn.
   */
  private void showYourTurn() {
    System.out.println("SHOW Your Turn for "
            + this.player.getPlayerColor().name() + " player.");
    if (this.showAlerts) {
      JOptionPane.showOptionDialog((Component) this.view, "It is your turn to play, player "
                      + this.player.getPlayerColor().name() + "!",
              "YOUR TURN",
              JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
              null, new Object[]{"OK"}, JOptionPane.OK_OPTION);
    }
  }

  /**
   * Shows a message dialog that the game is over.
   */
  private void showGameOver() {
    if (this.model.getWinner().isEmpty()) {
      System.out.println("SHOW Game over: TIED.");
      if (this.showAlerts) {
        JOptionPane.showMessageDialog((Component) this.view, "You tied!",
                "TIED", JOptionPane.INFORMATION_MESSAGE);
      }
    } else {
      DiscColor winner = this.model.getWinner().get();
      if (winner.equals(this.player.getPlayerColor())) {
        System.out.println("SHOW Game over: WON by "
                + this.player.getPlayerColor().name() + " player.");
        if (this.showAlerts) {
          JOptionPane.showMessageDialog((Component) this.view, "You won!",
                  this.player.getPlayerColor().name() + " WON",
                  JOptionPane.INFORMATION_MESSAGE);
        }
      } else {
        System.out.println("SHOW Game over: LOST by "
                + this.player.getPlayerColor().name() + " player.");
        if (this.showAlerts) {
          JOptionPane.showMessageDialog((Component) this.view, "You lost :(",
                  this.player.getPlayerColor().name() + " LOST",
                  JOptionPane.INFORMATION_MESSAGE);
        }
      }
    }
  }

  /**
   * Processes an action event.
   * If the action command is MOVE, then the player wishes to play a disc at the selected cell.
   * If the action command is PASS, then the player wishes to pass.
   *
   * @param move the move to be made.
   * @throws IllegalArgumentException if the move is null.
   */
  @Override
  public void notifyMovePerformed(String move) {
    if (move == null) {
      throw new IllegalArgumentException("Move cannot be null.");
    }
    if (move.equals("PlayDisc")) {
      System.out.println("NOTIFY Controller that a PlayDisc move has been made on the view by "
              + this.player.getPlayerColor().name() + " player.");
      if (this.makeControllerMoveForHuman()) {
        ((HumanPlayer) this.player).notifyOfMoveOrPass(
                0, this.view.getSelectedCell());
      }
      if (this.tryToMakeMove()) { // try to make move
        this.view.updateCanvas(false);
        this.model.notifyModelHasChanged();
      }
    } else if (move.equals("Pass")) {
      System.out.println("NOTIFY Controller that a Pass move has been made on the view by "
              + this.player.getPlayerColor().name() + " player.");
      if (this.makeControllerMoveForHuman()) {
        ((HumanPlayer) this.player).notifyOfMoveOrPass(1, null);
      }
      if (this.tryToMakeMove()) { // try to make move
        this.view.updateCanvas(false);
        this.model.notifyModelHasChanged();
      }
    }
  }

  @Override
  public int toggleHint(boolean hintOn) {
    if (hintOn) {
      Coordinate selectedCell = this.view.getSelectedCell();
      if (Objects.isNull(selectedCell)) {
        return -1;
      } else {
        if (this.ruleKeeper.isValid(this.model, selectedCell, this.player.getPlayerColor())) {
          return this.model
                  .getSandwichableNeighbors(selectedCell, this.player.getPlayerColor()).size() + 1;
        } else {
          return -1;
        }
      }
    }
    return -1;
  }

  /**
   * Determines if the controller should make a move for the human player.
   *
   * @return true if the controller should make a move for the human player, false otherwise.
   */
  private boolean makeControllerMoveForHuman() {
    return this.player.isHuman();
  }
}