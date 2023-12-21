package strategy;

import java.util.List;

import model.board.Coordinate;
import model.cell.DiscColor;

/**
 * Represents a strategy for an AI player to make a move which can be single or combined.
 */
public interface AIStrategy extends IStrategy {
  /**
   * Provides a list of possible moves for the player based on the strategy.
   *
   * @param candidateMoves is the list of all candidate moves that have yet to be filtered to fit.
   * @return a list of possible moves for the player based on the strategy.
   */
  List<Coordinate> getStrategyPossibleMoves(List<Coordinate> candidateMoves, DiscColor player);

  /**
   * Gets all possible moves for the player.
   *
   * @return all possible moves for the player.
   */
  List<Coordinate> getAllPossiblePlayerMoves(DiscColor player);

  /**
   * Gets the opposing player color.
   */
  DiscColor getOpposingPlayerColor();
}
