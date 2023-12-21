package ruleskeeper;

import java.util.List;

import model.board.Coordinate;
import model.board.IModel;
import model.board.ReadOnlyModel;
import model.cell.DiscColor;

/**
 * Represents a way of only allowing moves from players that follow the rules to the game.
 */
public interface RuleKeeper {
  /**
   * Determines if the given coordinates indicates a valid move.
   *
   * @param model  the model to check.
   * @param coord  the coordinates ot check.
   * @param player the player whose turn it is.
   * @return a boolean explaining if the given coord represents a valid move.
   * @throws IllegalArgumentException if either one of the given arguments are null.
   */
  boolean isValid(ReadOnlyModel model, Coordinate coord, DiscColor player);

  /**
   * Returns a list of coordinates that
   * need their colors changed based on the given coordinate,
   * and the current player's turn.
   *
   * @param model the model to play the disc on.
   * @param coord the coordinate to play the disc at.
   * @return a list of coordinates of cells whose color should be changed.
   * @throws IllegalStateException    if the move is invalid.
   * @throws IllegalArgumentException if either one of the given arguments are null.
   */
  List<Coordinate> playDisc(IModel model, Coordinate coord);
}
