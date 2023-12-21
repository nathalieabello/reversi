package move;

import java.util.Optional;

import model.board.Coordinate;

/**
 * Represents a move that the player can make.
 */
public interface Move {
  /**
   * Determines the coordinate at which a disc is to be played.
   * Return an empty optional if the player is passing.
   *
   * @return the coordinate at which a disc is to be played.
   */
  Optional<Coordinate> getMoveCoordinate();
}

