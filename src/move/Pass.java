package move;

import java.util.Optional;

import model.board.Coordinate;

/**
 * Represents a players' move being a pass.
 */
public class Pass implements Move {
  /**
   * Determines the coordinate at which a disc is to be played.
   *
   * @return empty optional to indicate a pass move.
   */
  @Override
  public Optional<Coordinate> getMoveCoordinate() {
    return Optional.empty();
  }
}
