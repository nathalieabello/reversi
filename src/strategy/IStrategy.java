package strategy;

import java.util.Optional;

import model.board.Coordinate;

/**
 * Represents a human or AI strategy for a player to make a move.
 */
public interface IStrategy {
  /**
   * Determines the coordinate at which a disc is to be played.
   * Return an empty optional if the player is passing meaning there are no valid moves.
   *
   * @return the coordinate at which a disc is to be played.
   */
  Optional<Coordinate> move();
}
