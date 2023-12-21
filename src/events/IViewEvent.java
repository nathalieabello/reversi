package events;

/**
 * Represents a view event that allows the view to communicate with its controller.
 */
public interface IViewEvent {
  /**
   * Notifies the subscriber of the move that was made.
   *
   * @param move is the move that was made.
   * @throws IllegalArgumentException if the move is null.
   */
  void notifyMovePerformed(String move);

  /**
   * Returns the number of points that would be won if the player moved to the given selected cell.
   *
   * @param hintOn whether to toggle hints on or off.
   * @return the number of points that would be won if the player moved to the given selected cell.
   */
  int toggleHint(boolean hintOn);
}
