package player;

import java.util.Optional;

import model.board.Coordinate;
import model.board.ReadOnlyModel;
import model.cell.DiscColor;

/**
 * Interface for a player in a game of Reversi.
 *
 * <p>Design choice: A player can make one of two moves: play a disc or pass their turn.
 * This interface allows for the implementation of different types of players, by not
 * specifying how a player plays a disc or if they should pass their turn.</p>
 *
 * <p>For example, a human player would play a disc by clicking on the board, while
 * an player. AI player would play a disc by calculating the best move.
 * The same applies for passing a turn. A human player would pass their turn by clicking
 * on the pass button, while an player. AI player would pass their turn by determining if it is
 * the best move to make.</p>
 */
public interface Player {
  /**
   * Determines what the move coordinates are based on the player's strategy.
   * Returns an empty optional if the player is passing.
   *
   * @return what the player wants to do
   */
  Optional<Coordinate> play(ReadOnlyModel model);

  /**
   * Returns the player's color.
   *
   * @return the player's color
   */
  DiscColor getPlayerColor();

  /**
   * Determines if the player is human.
   *
   * @return true if the player is human, false otherwise.
   */
  boolean isHuman();
}
