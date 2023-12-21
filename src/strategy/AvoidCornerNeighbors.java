package strategy;

import java.util.ArrayList;
import java.util.List;

import model.board.Coordinate;
import model.board.ReadOnlyModel;
import model.cell.DiscColor;

/**
 * Represents a single strategy where a player will avoid making a move
 * that has a neighbor on the edge.
 */
public class AvoidCornerNeighbors extends AbstractSingleStrategy {

  /**
   * Constructs a strategy for a player to make a move.
   *
   * @param player the current player
   * @param model  the model to use
   */
  public AvoidCornerNeighbors(DiscColor player, ReadOnlyModel model) {
    super(player, model);
  }

  /**
   * Returns a list of possible moves that fit the AvoidEdgeNeighbors strategy which is the
   * intersection of the possible moves of the player and the possible moves that do not have a
   * neighbor on the edge.
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
      // get coordinate's neighbors
      List<Coordinate> neighbors = this.model.getNeighbors(c);
      // flag to check if any neighbor is on the edge
      boolean isCorner = false;
      // check if any neighbor is on the edge
      for (Coordinate coord : neighbors) {
        if (this.model.isCorner(coord)) {
          // if corner is already filled, ignore
          if (this.model.getCellAt(coord).getColor() == DiscColor.NONE) {
            isCorner = true;
            break; // exit the loop if any neighbor is on the edge
          }
        }
      }
      // add the candidate coordinate only if none of its neighbors are on the edge
      if (!isCorner) {
        possibleMoves.add(c);
      } else if (this.model.isCorner(c)) {
        possibleMoves.add(c);
      }
    }
    return possibleMoves;
  }

}
