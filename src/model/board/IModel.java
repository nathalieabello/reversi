package model.board;

import controller.IController;
import events.IModelEvent;

/**
 * A model interface so different kinds of games can implement this interface.
 */
public interface IModel extends ReadOnlyModel, IModelEvent {

  /**
   * Starts the game.
   *
   * @throws IllegalStateException if the game has already started.
   */
  void startGame();

  /**
   * Changes whose turn it is to the opposite DiscColor.
   *
   * @throws IllegalStateException if the game is over, or if the game has not yet started.
   */
  void pass();

  /**
   * Play the disc at the given coordinate, and modify
   * the color of the cells that need to be changed accordingly.
   *
   * @param coord where the player is trying to play.
   * @throws IllegalStateException    if the move is invalid.
   * @throws IllegalArgumentException if the given coordinate is invalid.
   * @throws IllegalStateException    if the game is over, or if the game has not yet started.
   */
  void playDisc(Coordinate coord);

  /**
   * Returns an actionable copy of the board.
   *
   * @return an actionable copy of the board.
   * @throws IllegalStateException if the game has not yet started.
   */
  IModel getActionableCopyOfBoard(IModel model);

  /**
   * Adds the given listener to this model.
   *
   * @param listener the listener to add.
   */
  void addModelListener(IController listener);
}
