package strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.board.Coordinate;
import model.board.ReadOnlyModel;
import model.cell.DiscColor;

/**
 * Represents a single strategy where a player will make a move
 * that will capture the most cells.
 */
public class CaptureMostCells extends AbstractSingleStrategy {

  /**
   * Constructs a strategy for a player to make a move to try to win.
   *
   * @param player is the player whose move it is.
   * @param model  is the model of the game.
   */
  public CaptureMostCells(DiscColor player, ReadOnlyModel model) {
    super(player, model);
  }

  /**
   * Returns a list of possible moves that fit the CaptureMostCells strategy which is the
   * intersection of the possible moves of the player and the possible moves that capture the most
   * cells. The possible moves are sorted by descending weight and ascending coordinate.
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

    HashMap<Coordinate, Integer> possibleMovesWithWeights = new HashMap<>();
    //create a hashmap of all possible moves and the number of cells that can be captured
    //if that coordinate is chosen as the move.
    for (Coordinate coord : candidateMoves) {
      List<Coordinate> sandwichList = this.model.getSandwichableNeighbors(coord, player);
      if (!sandwichList.isEmpty()) {
        possibleMovesWithWeights.put(coord, sandwichList.size());
      }
    }

    //sort the hashmap by descending weight and ascending coordinate
    // and return the list of sorted moves.
    return this.sortMovesByWeight(possibleMovesWithWeights);
  }

  /**
   * Sorts possibleMovesWithWeights by descending weight and ascending coordinate.
   *
   * @return the sorted list of moves
   */
  private List<Coordinate> sortMovesByWeight(
      HashMap<Coordinate, Integer> possibleMovesWithWeights) {
    List<Map.Entry<Coordinate, Integer>> possibleMovesToBeSorted =
        new ArrayList<>(possibleMovesWithWeights.entrySet());

    //sort the list of entries in the hashmap by descending weight and ascending coordinate.
    possibleMovesToBeSorted.sort(
        (coord1, coord2) -> {
          int weightCompare =
              coord2.getValue().compareTo(coord1.getValue()); //large to small int comparison

          return weightCompare != 0 ? weightCompare :
              coord1.getKey().compareTo(coord2.getKey()); //small to large coordinate comparison
        }
    );

    //create a list of coordinates from the sorted list of entries in the hashmap.
    List<Coordinate> possibleMovesByDescWeightAscCoordinate = new ArrayList<>();
    for (Map.Entry<Coordinate, Integer> entry : possibleMovesToBeSorted) {
      possibleMovesByDescWeightAscCoordinate.add(entry.getKey());
    }

    return possibleMovesByDescWeightAscCoordinate;
  }
}
