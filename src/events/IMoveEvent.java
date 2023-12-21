package events;

import model.board.Coordinate;

/**
 * Represents a move event that allows the controller to communicate with its player and
 * consequent strategy.
 */
public interface IMoveEvent {

  /**
   * Notifies the subscriber of the move that was made.
   *
   * @param move is the move that was made.
   * @param selectedCell is the cell that was selected.
   */
  void notifyOfMoveOrPass(int move, Coordinate selectedCell);
}
