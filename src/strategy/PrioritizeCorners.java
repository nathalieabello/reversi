package strategy;

import java.util.ArrayList;
import java.util.List;

import model.board.Coordinate;
import model.board.ReadOnlyModel;
import model.cell.DiscColor;

/**
 * Represents a single strategy where a player will prioritize making a move on the edge.
 */
public class PrioritizeCorners extends AbstractSingleStrategy {
  /**
   * Constructs a strategy for a player to make a move by finding the first possible move.
   *
   * @param player is the player whose move it is.
   * @param model  is the model of the game.
   */
  public PrioritizeCorners(DiscColor player, ReadOnlyModel model) {
    super(player, model);
  }

  /**
   * Returns a list of possible moves that fit the PrioritizeEdges strategy which
   * is the intersection of the possible moves of the player and the possible moves on the edge.
   *
   * @param candidateMoves is the list of all candidate moves that have yet to be filtered to fit
   *                       the strategy.
   * @return a list of possible moves for the player based on the strategy.
   */
  @Override
  public List<Coordinate> getStrategyPossibleMoves(List<Coordinate> candidateMoves,
                                                   DiscColor player) {
    this.checkPlayerException(player);
    this.checkCandidateMovesException(candidateMoves, player);

    List<Coordinate> possibleMoves = new ArrayList<>();
    for (Coordinate c : candidateMoves) {
      if (this.model.isCorner(c)) {
        possibleMoves.add(c);
      }
    }
    return possibleMoves;
  }
}
