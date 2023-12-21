package model.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import controller.IController;
import model.cell.DiscColor;
import model.cell.ICell;
import ruleskeeper.RuleKeeper;

/**
 * Represents the board of the hex Reversi game.
 * A hex Reversi game is represented by a hexagonal board.
 */
public abstract class AbstractReversiModel implements IModel {

  protected final RuleKeeper ruleKeeper;
  protected final int layers;
  protected final HashMap<Coordinate, ICell> grid;
  protected DiscColor whoseTurn;
  protected final List<DiscColor> playerColors;
  protected Status gameState;
  protected boolean pass;
  protected final ICell cellType;
  protected List<IController> listenerList = new ArrayList<>();

  /**
   * Constructor of the class HexReversiModel, that sets placeholder values
   * for the fields of the class/ default values for the parameters.
   * INVARIANT: the given number of layers is not less than 1.
   * @param ruleKeeper the rule keeper to use for this board.
   * @param typeCell the type of cell to fill the grid width.
   * @param layers the number of layers in this board.
   * @param playerColors the player colors (to be able to play with different colors).
   * @throws IllegalArgumentException if the given rule keeper is null,
   *                                 or if the given cell is null,
   *                                 or if the given number of layers is less than 1.
   *                                 or if the given list of colors is not at least of size 2,
   *                                 or if the given list of colors contains a NONE color,
   *                                 or if the given list of colors contains a null color,
   *                                 or if the given list of colors contains a duplicate color,
   *                                 or if the given list of colors is null.
   */
  protected AbstractReversiModel(RuleKeeper ruleKeeper, ICell typeCell,
                            int layers, List<DiscColor> playerColors) {
    this.constructorExceptions(ruleKeeper, typeCell, layers);
    this.playerColorsExceptions(playerColors);
    this.cellType = typeCell;
    this.ruleKeeper = ruleKeeper;
    this.layers = layers;
    this.playerColors = playerColors;
    this.grid = new HashMap<>();
    this.gameState = null; //game has not yet been started so game state is null.
    this.fillGrid(cellType);
  }

  /**
   * Create a copy of the given model.
   *
   * @param model the model to copy.
   */
  public AbstractReversiModel(IModel model) {
    this.cellType = model.getCellAt(new Coordinate(0, 0)).makeCell();
    this.ruleKeeper = model.getRuleKeeper();
    this.layers = model.getNumLayers();
    this.playerColors = new ArrayList<>(model.getPlayerColors());
    this.whoseTurn = model.getTurn();
    this.gameState = model.getGameState();
    this.pass = model.getPass();
    this.grid = model.getCopyOfAllCoords();
    this.listenerList = new ArrayList<>(this.listenerList);
  }

  /**
   * Throws an exception if the given cell is null,
   * if the given number of layers is less than 1,
   * if the given rule keeper is null.
   *
   * @param ruleKeeper the rule keeper to use for this board.
   * @param typeCell   the type of cell to fill the grid width.
   * @param layers     the number of layers in this board.
   */
  protected void constructorExceptions(RuleKeeper ruleKeeper, ICell typeCell, int layers) {
    if (ruleKeeper == null) {
      throw new IllegalArgumentException("Rule keeper cannot be null.");
    } else if (typeCell == null) {
      throw new IllegalArgumentException("Cell cannot be null.");
    } else if (layers < 1) {
      throw new IllegalArgumentException("Must have at least 1 layer.");
    }
  }

  /**
   * Throws an exception if the given list of colors is not at least of size 2,
   * if the given list of colors contains a NONE color,
   * if the given list of colors contains a null color,
   * if the given list of colors contains a duplicate color.
   *
   * @param playerColors the player colors (to be able to play with different colors).
   */
  protected void playerColorsExceptions(List<DiscColor> playerColors) {
    if (playerColors == null) {
      throw new IllegalArgumentException("Colors cannot be null.");
    } else if (playerColors.size() != 2) {
      throw new IllegalArgumentException("Must have 2 players.");
    } else if (playerColors.contains(DiscColor.NONE) || playerColors.contains(null)) {
      throw new IllegalArgumentException("Cannot have a player with no color.");
    } else {
      for (int i = 0; i < playerColors.size(); i++) {
        for (int j = i + 1; j < playerColors.size(); j++) {
          if (playerColors.get(i) == playerColors.get(j)) {
            throw new IllegalArgumentException("Cannot have duplicate colors.");
          }
        }
      }
    }
  }

  /**
   * Starts a game of Reversi with the received parameters.
   *
   * @throws IllegalStateException if the game has already started.
   */
  public void startGame() {
    this.gameAlreadyStartedException();
    this.gameState = Status.PLAYING;
    this.whoseTurn = this.playerColors.get(0);
    this.pass = false;
    this.notifyModelHasChanged();
  }

  /**
   * Throws an IllegalStateException if the game has already started.
   */
  protected void gameAlreadyStartedException() {
    if (this.gameState != null) {
      throw new IllegalStateException("Game has already started.");
    }
  }

  /**
   * Throws an IllegalStateException if the game has not yet been started.
   */
  protected void gameNotYetStartedException() {
    if (this.gameState == null) {
      throw new IllegalStateException("Game has not yet been started.");
    }
  }

  /**
   * Fills this grid with cells for all possible coordinates of Hex Reversi board.
   */
  protected abstract void fillGrid(ICell cell);

  /**
   * Returns a list of coordinates of 1st degree neighbors of the same color.
   *
   * @param coord  the coordinate of the cell to get the neighbors of.
   * @param player the player color to get the like neighbors of.
   * @return the neighboring cells' coordinates of the cell at the given coordinate.
   * @throws IllegalArgumentException if the given coordinate is invalid,
   *                                  or if the given player is null.
   * @throws IllegalStateException    if the game has not yet been started.
   */
  @Override
  public List<Coordinate> getLikeNeighbors(Coordinate coord, DiscColor player) {
    this.gameNotYetStartedException();
    this.invalidCoordinateException(coord);
    this.invalidPlayerColorException(player);

    List<Coordinate> neighbors = this.getNeighbors(coord);
    // turns ALL neighbors coordinates to ALL neighboring coordinates of opposite color
    List<Coordinate> likeNeighbors = new ArrayList<>();
    for (Coordinate c : neighbors) {
      ICell neighbor = this.getCellAt(c);
      // if player's turn color, add to list.
      if (neighbor.getColor() == player) {
        likeNeighbors.add(c);
      }
    }
    return likeNeighbors;
  }

  /**
   * Throws an exception if the given player color is invalid.
   *
   * @param player the player color to check.
   */
  protected void invalidPlayerColorException(DiscColor player) {
    if (player == null) {
      throw new IllegalArgumentException("Player color cannot be null.");
    } else if (player == DiscColor.NONE) {
      throw new IllegalArgumentException("Player color cannot be NONE.");
    } else if (!this.playerColors.contains(player)) {
      throw new IllegalArgumentException("Player color not in this game.");
    }
  }

  /**
   * Get the neighboring cells' coordinates of the cell at the given coordinate.
   *
   * @param coord the coordinate of the cell to get the neighbors of.
   * @return the neighboring cells' coordinates of the cell at the given coordinate.
   * @throws IllegalArgumentException if the given coordinate is invalid.
   * @throws IllegalStateException    if the game has not yet been started.
   */
  public abstract List<Coordinate> getNeighbors(Coordinate coord);

  /**
   * Returns whether the given coordinate is a corner or not.
   *
   * @param coord the coordinate to check.
   * @return a boolean that determines it.
   * @throws IllegalArgumentException if the given coordinate is invalid.
   * @throws IllegalStateException    if the game has not yet been started.
   */
  @Override
  public abstract boolean isCorner(Coordinate coord);

  /**
   * Change the cell color at the given coordinates to the given disc color.
   *
   * @param coord the coordinate of the cell to change the color of.
   * @param color the color to change the cell to.
   * @throws IllegalArgumentException if the given color is NONE or invalid,
   *                                  or if the given coordinate is invalid.
   */
  protected void changeCellColor(Coordinate coord, DiscColor color) {
    this.invalidCoordinateException(coord);
    this.invalidPlayerColorException(color);

    ICell cell = this.grid.get(coord);
    if (color == DiscColor.NONE) {
      throw new IllegalArgumentException("Cannot remove a cell's color.");
    }
    cell.changeColor(color);
  }

  /**
   * Returns the number of layers in this board.
   *
   * @return the number of layers in this board.
   */
  @Override
  public int getNumLayers() {
    return this.layers;
  }

  /**
   * Returns the cell at the given coordinate.
   *
   * @param coord the coordinate corresponding to the cell to fetch.
   * @return the cell at the given coordinate.
   * @throws IllegalArgumentException if the given coordinate is invalid.
   */
  @Override
  public ICell getCellAt(Coordinate coord) {
    this.invalidCoordinateException(coord);
    return this.grid.get(coord);
  }

  /**
   * Changes whose turn it is to the opposite DiscColor.
   *
   * @throws IllegalStateException if the game is over,
   *                               or if the game has not yet been started.
   */
  @Override
  public void pass() {
    this.gameNotYetStartedException();
    this.gameIsOverException();

    this.switchPass();
    this.nextTurn();
  }

  /**
   * Changes whose turn it is to the opposite DiscColor after a player makes a move.
   */
  protected void nextTurn() {
    DiscColor passing = this.playerColors.remove(0);
    this.playerColors.add(passing);
    this.whoseTurn = this.getTurn();
  }

  /**
   * To check if there are two consecutive passes, which would make the game over.
   */
  protected void switchPass() {
    if (pass) {
      this.gameState = Status.TIED;
    } else {
      pass = true;
    }
  }

  /**
   * Returns which player's turn it is based on that player's chosen disc color.
   *
   * @return whose turn it is.
   */
  @Override
  public DiscColor getTurn() {
    return this.playerColors.get(0);
  }

  /**
   * Returns a list of coordinates of 1st degree neighbors the origin cell can sandwich.
   *
   * @param origin the origin cell that was clicked on.
   * @param player the player color to get the sandwichable neighbors of.
   * @return a list of neighbors to sandwich with.
   * @throws IllegalStateException    if the game has not yet been started.
   * @throws IllegalArgumentException if the given coordinate is invalid,
   *                                  or if the given player color is invalid.
   */
  @Override
  public List<Coordinate> getSandwichableNeighbors(Coordinate origin, DiscColor player) {
    this.gameNotYetStartedException();
    this.invalidCoordinateException(origin);
    this.invalidPlayerColorException(player);

    List<Coordinate> oppositeNeighbors = this.getOppositeNeighbors(origin, player);
    List<Coordinate> sandwichableNeighbors = new ArrayList<>();
    List<Coordinate> possibleSandwichableNeighbors = new ArrayList<>();

    // checks all opposite neighbors.
    while (!oppositeNeighbors.isEmpty()) {
      Coordinate c = oppositeNeighbors.get(0);
      // adds transitions from origin to 2nd degree neighbor to check
      Coordinate coord = this.getNextCoordinate(origin, c);
      if (this.grid.containsKey(coord)) {
        if (this.getCellAt(coord).getColor() == player) {
          possibleSandwichableNeighbors.add(c);
          sandwichableNeighbors.addAll(possibleSandwichableNeighbors);
          possibleSandwichableNeighbors.clear();
          oppositeNeighbors.remove(c);
        } else if (this.getCellAt(coord).getColor() == this.getCellAt(c).getColor()) {
          oppositeNeighbors.remove(c);
          possibleSandwichableNeighbors.add(c);
          oppositeNeighbors.add(0, coord);
        } else {
          oppositeNeighbors.remove(c);
          possibleSandwichableNeighbors.clear();
        }
      } else {
        possibleSandwichableNeighbors.clear();
      }
      // out of range
      oppositeNeighbors.remove(c);
    }
    return sandwichableNeighbors;
  }

  /**
   * Returns a list of coordinates of 1st degree neighbors of the opposite color.
   *
   * @param origin the origin cell that was clicked on.
   * @param player the player color to get the like neighbors of.
   * @return a list of the given coordinate's 1st degree neighbors of the opposite color's coords.
   * @throws IllegalStateException    if the game has not yet been started.
   * @throws IllegalArgumentException if the given coordinate is invalid, or
   *                                  if the given player color is invalid.
   */
  @Override
  public List<Coordinate> getOppositeNeighbors(Coordinate origin, DiscColor player) {
    this.gameNotYetStartedException();
    this.invalidCoordinateException(origin);
    this.invalidPlayerColorException(player);

    List<Coordinate> neighbors = this.getNeighbors(origin);
    // turns ALL neighbors coordinates to ALL neighboring coordinates of opposite color
    List<Coordinate> oppositeNeighbors = new ArrayList<>();
    for (Coordinate c : neighbors) {
      ICell neighbor = this.getCellAt(c);
      // if is of opposing player turn's color, add to list.
      if (neighbor.getColor() != DiscColor.NONE) {
        if (neighbor.getColor() != player) {
          oppositeNeighbors.add(c);
        }
      }
    }
    return oppositeNeighbors;
  }

  /**
   * Returns the next coordinate in the direction of the given coordinate.
   *
   * @param origin the origin coordinate.
   * @param c      the coordinate to get the next coordinate of.
   * @return the next coordinate in the direction of the given coordinate.
   */
  protected Coordinate getNextCoordinate(Coordinate origin, Coordinate c) {
    this.gameNotYetStartedException();
    this.invalidCoordinateException(origin);
    this.invalidCoordinateException(c);

    int transitionRow = (c.getRow() - origin.getRow());
    int transitionCol = (c.getCol() - origin.getCol());
    while (transitionRow > 1 || transitionRow < -1) {
      transitionRow = transitionRow / 2;
    }
    while (transitionCol > 1 || transitionCol < -1) {
      transitionCol = transitionCol / 2;
    }
    int newRow = c.getRow() + transitionRow;
    int newCol = c.getCol() + transitionCol;
    return new Coordinate(newRow, newCol);
  }

  /**
   * Throws an exception if the game is over.
   */
  private void gameIsOverException() {
    if (this.gameState != Status.PLAYING) {
      throw new IllegalStateException("Game is over.");
    }
  }

  /**
   * Play the disc at the given coordinate, and modify
   * the color of the cells that need to be changed accordingly.
   *
   * @param coord where the player is trying to play.
   * @throws IllegalStateException    if the move is invalid.
   * @throws IllegalArgumentException if the given coordinate is invalid.
   * @throws IllegalStateException    if the game is over, or if the game has not yet been started.
   */
  @Override
  public void playDisc(Coordinate coord) {
    this.gameNotYetStartedException();
    this.gameIsOverException();
    this.invalidCoordinateException(coord);

    List<Coordinate> coordOfCellsToChangeColor =
        this.ruleKeeper.playDisc(this, coord);

    //change the color of the cells that need to be changed.
    for (Coordinate c : coordOfCellsToChangeColor) {
      this.changeCellColor(c, this.whoseTurn);
    }

    // player played a move so not a double pass
    this.pass = false;
    // next player's turn
    this.nextTurn();
    // nowhere for the new player to go, auto-pass.
    if (this.noPossibleMoves(this.whoseTurn)) {
      this.pass();
    }
  }

  /**
   * Returns an actionable copy of the board.
   *
   * @param model the model to get the actionable copy of.
   * @return an actionable copy of the board.
   * @throws IllegalStateException if the game has not yet been started.
   */
  @Override
  public abstract IModel getActionableCopyOfBoard(IModel model);

  /**
   * Returns the score of the given player.
   *
   * @param player the player whose score to get based on their disc color.
   * @return the score of the given player.
   * @throws IllegalStateException if the game has not yet been started.
   */
  @Override
  public int getPlayerScore(DiscColor player) {
    this.gameNotYetStartedException();

    int score = 0;
    for (Coordinate c : this.getCopyOfAllCoords().keySet()) {
      if (this.grid.get(c).getColor() == player) {
        score++;
      }
    }
    return score;
  }

  /**
   * Returns the rule keeper of the game.
   *
   * @return the rule keeper of the game.
   */
  @Override
  public RuleKeeper getRuleKeeper() {
    return this.ruleKeeper;
  }

  /**
   * Returns a list of the players' disc colors.
   *
   * @return a list of the players' disc colors.
   */
  @Override
  public List<DiscColor> getPlayerColors() {
    return new ArrayList<>(this.playerColors);
  }

  /**
   * Returns the pass boolean of the game that determines if the player passed their turn or not.
   *
   * @return whether the player passed their turn or not.
   * @throws IllegalStateException if the game has not yet been started.
   */
  @Override
  public boolean getPass() {
    this.gameNotYetStartedException();
    return this.pass;
  }

  /**
   * Returns the color of the other player.
   *
   * @return the color of the other player.
   * @throws IllegalStateException if the game has not yet been started.
   */
  @Override
  public DiscColor getOtherPlayerColor() {
    this.gameNotYetStartedException();
    if (this.playerColors.get(0) == this.whoseTurn) {
      return this.playerColors.get(1);
    } else {
      return this.whoseTurn;
    }
  }

  /**
   * Returns a copy of the grid of this model.
   *
   * @return a copy of the grid of this model.
   * @throws IllegalStateException if the game has not yet been started.
   */
  public HashMap<Coordinate, ICell> getCopyOfAllCoords() {
    this.gameNotYetStartedException();

    HashMap<Coordinate, ICell> copyGrid = new HashMap<>();
    for (Coordinate c : this.grid.keySet()) {
      ICell cellToCopy = this.grid.get(c);
      ICell copyCell = cellToCopy.makeCell();

      //change color if necessary
      if (cellToCopy.getColor() != copyCell.getColor()) {
        copyCell.changeColor(cellToCopy.getColor());
      }
      copyGrid.put(c, copyCell);
    }
    return copyGrid;
  }


  /**
   * Throws an exception if the given coordinate is invalid.
   *
   * @param coord the coordinate to check.
   * @throws IllegalArgumentException if the given coordinate is invalid,
   *                                  or if the given coordinate is null.
   */
  protected void invalidCoordinateException(Coordinate coord) {
    if (coord == null) {
      throw new IllegalArgumentException("Coordinate cannot be null.");
    } else if (!this.grid.containsKey(coord)) {
      throw new IllegalArgumentException("Invalid coordinate.");
    }
  }

  /**
   * Returns whether the game is over or not. The game is over when there are no more valid moves
   * for both players (i.e., when the board is full). A game is also over if the two players both
   * pass their turns (even if there are still valid moves on the board).
   *
   * @return whether the game is over or not.
   * @throws IllegalStateException if the game has not yet been started.
   */
  @Override
  public boolean isGameOver() {
    this.gameNotYetStartedException();
    if (gameState != Status.PLAYING) { //if game state is TIED or WON.
      return true;
    } else if (this.noPossibleMoves(this.whoseTurn)) {
      if (this.noPossibleMoves(this.getOtherPlayerColor())) {
        gameState = Status.WON; // this player can't move and the other player can't move.
        return true;
      }
    }
    return false; // game is still going.
  }

  /**
   * Returns the current status of the game.
   *
   * @return the current status of the game, returns null if the game has not yet been started.
   */
  @Override
  public Status getGameState() {
    return this.gameState;
  }

  /**
   * Returns a read only copy of the board.
   *
   * @param model the model to get the read only copy of.
   * @return a read only copy of the board.
   * @throws IllegalStateException if the game has not yet been started.
   */
  @Override
  public ReadOnlyModel getReadOnlyCopyOfBoard(IModel model) {
    this.gameNotYetStartedException();
    return new ReadOnlyReversiModel(this.getActionableCopyOfBoard(model));
  }

  /**
   * Returns the winner of the game.
   * If the game is tied, returns an empty optional.
   * If the game is over and a player won, returns an optional of the winner.
   *
   * @return the winner of the game.
   * @throws IllegalStateException if the game is not over yet.
   * @throws IllegalStateException if the game has not yet been started.
   */
  @Override
  public Optional<DiscColor> getWinner() {
    this.gameNotYetStartedException();
    DiscColor player1 = this.whoseTurn;
    DiscColor player2 = this.getOtherPlayerColor();
    int player1Score = this.getPlayerScore(this.whoseTurn);
    int player2Score = this.getPlayerScore(this.getOtherPlayerColor());
    if (this.getGameState() != Status.PLAYING) {
      if (player1Score > player2Score) {
        return Optional.of(player1);
      } else if (player1Score < player2Score) {
        return Optional.of(player2);
      } else {
        this.gameState = Status.TIED;
        return Optional.empty();
      }
    } else {
      throw new IllegalStateException("Game is not over yet.");
    }
  }

  /**
   * Determines whether the current player can make any moves.
   *
   * @return whether the current player can make any moves
   */
  private boolean noPossibleMoves(DiscColor player) {
    for (Coordinate c : this.grid.keySet()) {
      if (this.ruleKeeper.isValid(this, c, player)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Adds the given listener to this model.
   *
   * @param listener the listener to add.
   */
  @Override
  public void addModelListener(IController listener) {
    this.listenerList.add(listener);
  }

  /**
   * Notifies all listeners that the model's state has changed.
   */
  @Override
  public void notifyModelHasChanged() {
    for (IController listener : this.listenerList) {
      System.out.println("NOTIFY Controller " + this.listenerList.indexOf(listener)
          + " that model has changed.");
      listener.run();
    }
  }
}
