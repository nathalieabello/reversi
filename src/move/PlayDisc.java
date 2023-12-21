package move;

import java.util.Optional;

import model.board.Coordinate;

/**
 * Represents a player's move being to move a disc.
 */
public class PlayDisc implements Move {
  private final Coordinate playDiscAt;

  /**
   * Constructs a play disc move.
   *
   * @param playDiscAt is the coordinate to play the move on.
   */
  public PlayDisc(Coordinate playDiscAt) {
    this.playDiscAt = playDiscAt;
  }

  /**
   * Determines the coordinate at which a disc is to be played.
   *
   * @return the coordinate at which a disc is to be played.
   */
  @Override
  public Optional<Coordinate> getMoveCoordinate() {
    return Optional.ofNullable(this.playDiscAt);
  }
}
