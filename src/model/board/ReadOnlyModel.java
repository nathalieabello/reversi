package model.board;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import model.cell.DiscColor;
import model.cell.ICell;
import ruleskeeper.RuleKeeper;

/**
 * Represents a read only model of the board.
 * A read only model is a model that is immutable, and can only be read from.
 */
public interface ReadOnlyModel {
  /**
   * Returns the number of layers in this board.
   *
   * @return the number of layers in this board.
   */
  int getNumLayers();

  /**
   * Returns which player's turn it is based on that player's chosen disc color.
   *
   * @return whose turn it is.
   * @throws IllegalStateException if the game has not yet started.
   */
  DiscColor getTurn();

  /**
   * Returns the cell at the given coordinate.
   *
   * @param coord the coordinate corresponding to the cell to fetch.
   * @return the cell at the given coordinate.
   * @throws IllegalArgumentException if the given coordinate is invalid.
   * @throws IllegalStateException    if the game has not yet started.
   */
  ICell getCellAt(Coordinate coord);

  /**
   * Returns whether the game is over or not. The game is over when there are no more valid moves
   * for both players (i.e., when the board is full). A game is also over if the two players both
   * pass their turns (even if there are still valid moves on the board).
   *
   * @return whether the game is over or not.
   * @throws IllegalStateException if the game has not yet started.
   */
  boolean isGameOver();

  /**
   * Returns a list of coordinates of 1st degree neighbors the origin cell can sandwich.
   *
   * @param origin the origin cell that was clicked on.
   * @param player the player color to get the sandwichable neighbors of.
   * @return a list of neighbors to sandwich with.
   * @throws IllegalArgumentException if the given coordinate is invalid.
   * @throws IllegalStateException    if the game has not yet started.
   */
  List<Coordinate> getSandwichableNeighbors(Coordinate origin, DiscColor player);

  /**
   * Returns a list of coordinates of 1st degree neighbors of the opposite color.
   *
   * @param origin the origin cell that was clicked on.
   * @param player the player color to get the like neighbors of.
   * @return a list of the given coordinate's 1st degree neighbors of the opposite color's coords.
   * @throws IllegalArgumentException if the given coordinate is invalid.
   * @throws IllegalStateException    if the game has not yet started.
   */
  List<Coordinate> getOppositeNeighbors(Coordinate origin, DiscColor player);

  /**
   * Returns a list of coordinates of 1st degree neighbors of the same color.
   *
   * @param coord  the coordinate of the cell to get the neighbors of.
   * @param player the player color to get the like neighbors of.
   * @return the neighboring cells' coordinates of the cell at the given coordinate.
   * @throws IllegalArgumentException if the given coordinate is invalid,
   *                                  or if the given player is null.
   *                                  or if the given player is not one of the players in the game.
   * @throws IllegalStateException    if the game has not yet started.
   */
  List<Coordinate> getLikeNeighbors(Coordinate coord, DiscColor player);

  /**
   * Returns the optional winner of the game.
   * If the game is tied, returns an empty optional.
   *
   * @return the winner of the game.
   * @throws IllegalStateException if the game is not over yet, or if the game has not yet started.
   */
  Optional<DiscColor> getWinner();

  /**
   * Returns the current status of the game.
   * PLAYING if the game is still going,
   * WON if the game is over and a player won,
   * TIED if the game is over and no player won.
   *
   * @return the current status of the game, returns null if the game has not yet started.
   */
  Status getGameState();

  /**
   * Returns a read only copy of the board.
   *
   * @param model the model to get the read only copy of.
   * @return a read only copy of the board.
   * @throws IllegalStateException if the game has not yet started.
   */
  ReadOnlyModel getReadOnlyCopyOfBoard(IModel model);

  /**
   * Returns the score of the given player.
   *
   * @param player the player whose score to get based on their disc color.
   * @return the score of the given player.
   * @throws IllegalArgumentException if the given player is null, or if the given player is not
   *                                  one of the players in the game.
   * @throws IllegalStateException    if the game has not yet started.
   */
  int getPlayerScore(DiscColor player);

  /**
   * Returns the rule keeper of the game.
   *
   * @return the rule keeper of the game.
   */
  RuleKeeper getRuleKeeper();

  /**
   * Returns a list of the players' disc colors.
   *
   * @return a list of the players' disc colors.
   */
  List<DiscColor> getPlayerColors();

  /**
   * Returns the pass boolean of the game that determines if the player passed their turn or not.
   *
   * @return whether the player passed their turn or not.
   * @throws IllegalStateException if the game has not yet started.
   */
  boolean getPass();

  /**
   * Returns the other player's disc color.
   *
   * @return the other player's disc color.
   * @throws IllegalStateException if the game has not yet started.
   */
  DiscColor getOtherPlayerColor();

  /**
   * Returns a hashmap of all the coordinates and their corresponding cells.
   *
   * @return a hashmap of all the coordinates and their corresponding cells.
   * @throws IllegalStateException if the game has not yet started.
   */
  HashMap<Coordinate, ICell> getCopyOfAllCoords();

  /**
   * Returns a list of the given coordinate's neighbors.
   *
   * @param coord the coordinate to get the neighbors of.
   * @return a list of the given coordinate's neighbors.
   * @throws IllegalArgumentException if the given coordinate is invalid.
   * @throws IllegalStateException    if the game has not yet been started.
   */
  List<Coordinate> getNeighbors(Coordinate coord);

  /**
   * Determines if the given coordinate is on the corner of the board.
   *
   * @param coord the coordinate to check.
   * @return true if the given coordinate is on the corner of the board.
   * @throws IllegalArgumentException if the given coordinate is invalid.
   * @throws IllegalStateException    if the game has not yet been started.
   */
  boolean isCorner(Coordinate coord);

  /**
   * Starts the game.
   *
   * @throws IllegalStateException if the game has already started.
   */
  void startGame();
}
