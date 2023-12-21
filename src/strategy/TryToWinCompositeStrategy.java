package strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.board.Coordinate;
import model.cell.DiscColor;

/**
 * Represents a composite strategy for a player to make a move that tries to win the game.
 * It is simply a combination of single strategies that enforce a particular order denoted
 * in the constructor's javadoc.
 */
public class TryToWinCompositeStrategy extends AbstractCompositeStrategy {

  /**
   * Constructs a composite strategy with multiple strategies.
   * <p>Enforce this order from outermost to innermost:
   * 1. PrioritizeCorners
   * 2. AvoidCornerNeighbors
   * 3. CaptureMostCells</p>
   *
   * @param strategy     is the strategy to be used.
   * @param nextStrategy is the composite strategy.
   * @throws IllegalArgumentException if the next composite strategy is not a
   *                                  TryToWinCompositeStrategy.
   */
  public TryToWinCompositeStrategy(AbstractSingleStrategy strategy, AIStrategy nextStrategy) {
    super(strategy, nextStrategy);

    if (nextStrategy instanceof AbstractCompositeStrategy) {
      if (!(nextStrategy instanceof TryToWinCompositeStrategy)) {
        throw new IllegalArgumentException("Cannot have a composite strategy in this class" +
            "that is not TryToWinCompositeStrategy");
      }
    }
  }

  /**
   * Gets the possible moves for the TryToWinCompositeStrategy which is the intersection of the
   * possible moves of the all the strategies in the composite strategy based on the number of
   * times the strategies find it as a valid move.
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

    List<Coordinate> originalCandidateMoves = new ArrayList<>(candidateMoves);

    List<AbstractSingleStrategy> strategiesFromInnerToOuterMost =
        new ArrayList<>(this.getAllStrategiesFromInnerToOuterMost());

    List<Coordinate> possibleMoves = new ArrayList<>();
    List<Coordinate> filteredMoves = new ArrayList<>();
    int strategyPassedCount = 0;

    for (AbstractSingleStrategy strategy : strategiesFromInnerToOuterMost) {
      filteredMoves = new ArrayList<>(strategy.getStrategyPossibleMoves(candidateMoves, player));
      //if the filtered moves are empty:
      if (filteredMoves.isEmpty()) {
        if (strategy instanceof PrioritizeCorners) {
          possibleMoves = strategy.getStrategyPossibleMoves(originalCandidateMoves, player);
          return this.checkResultingListOfEmptyStackedStrategy(possibleMoves,
              originalCandidateMoves, candidateMoves, strategyPassedCount);
        } else if (strategy instanceof AvoidCornerNeighbors) {
          possibleMoves = strategy.getStrategyPossibleMoves(originalCandidateMoves, player);
          return this.checkResultingListOfEmptyStackedStrategy(possibleMoves,
              originalCandidateMoves, candidateMoves, strategyPassedCount);
        } else if (strategy instanceof CaptureMostCells) {
          candidateMoves = new ArrayList<>(originalCandidateMoves);
        }
      } else {
        candidateMoves = new ArrayList<>(filteredMoves);
        strategyPassedCount++;
      }
    }

    //if no filtered moves were empty, return the last valid filtered moves.
    //otherwise, keep the possible moves as the last non-null filtered moves.
    if (possibleMoves.isEmpty()) {
      return new ArrayList<>(filteredMoves);
    }

    return possibleMoves;
  }

  /**
   * Checks if the resulting list of empty stacked strategy is empty.
   *
   * @param possibleMoves          is the list of possible moves
   *                               resulting from the unstacked strategy.
   * @param originalCandidateMoves is the list of candidate moves before the strategy was stacked.
   * @param candidateMoves         is the list of candidate moves after the strategy was stacked.
   * @param strategyPassedCount    is the number of strategies that passed.
   * @return the list of possible moves resulting from the stacked strategy.
   */
  private List<Coordinate> checkResultingListOfEmptyStackedStrategy(
      List<Coordinate> possibleMoves, List<Coordinate> originalCandidateMoves,
      List<Coordinate> candidateMoves, int strategyPassedCount) {
    if (possibleMoves.isEmpty()) {
      if (originalCandidateMoves.equals(candidateMoves) && strategyPassedCount == 0) {
        return new ArrayList<>();
      } else {
        return new ArrayList<>(candidateMoves);
      }
    }
    return possibleMoves;
  }

  /**
   * Checks if the strategy order is valid.
   * <p>Strategies have to be in the following order from inner to outermost:
   * 1. PrioritizeCorners
   * 2. AvoidCornerNeighbors
   * 3. CaptureMostCells</p>
   *
   * @param strategiesFromInnerToOuterMost the strategies to check the order of.
   * @throws IllegalArgumentException if the strategy order is invalid,
   *                                  or if there are duplicate strategies.
   */
  protected void checkStrategyOrder(List<AbstractSingleStrategy> strategiesFromInnerToOuterMost) {
    Map<String, Integer> strategyOrder = new HashMap<>();
    strategyOrder.put("PrioritizeCorners", 3);
    strategyOrder.put("AvoidCornerNeighbors", 2);
    strategyOrder.put("CaptureMostCells", 1);

    List<String> strategiesFromInnerToOuterMostNames =
        this.getStrategyNames(strategiesFromInnerToOuterMost, strategyOrder);

    for (int i = 0; i < strategiesFromInnerToOuterMost.size() - 1; i++) {
      String currentStrategy = strategiesFromInnerToOuterMostNames.get(i);
      String nextStrat = strategiesFromInnerToOuterMostNames.get(i + 1);
      if (strategyOrder.get(currentStrategy)
          > strategyOrder.get(nextStrat)) {
        throw new IllegalArgumentException("Invalid strategy order");
      }
    }
  }

  /**
   * Gets the names of the strategies from inner to outermost.
   *
   * @param strategiesFromInnerToOuterMost the strategies to get the names of.
   * @param strategyOrder                  the order of the strategies.
   * @return the names of the strategies from inner to outermost.
   * @throws IllegalArgumentException if the strategy order is invalid,
   *                                  or if there are duplicate strategies.
   */
  private List<String> getStrategyNames(
      List<AbstractSingleStrategy> strategiesFromInnerToOuterMost,
      Map<String, Integer> strategyOrder) {
    List<String> strategiesFromInnerToOuterMostNames = new ArrayList<>();
    for (AbstractSingleStrategy strat : strategiesFromInnerToOuterMost) {
      strategiesFromInnerToOuterMostNames.add(strat.getClass().getSimpleName());
    }

    List<String> strategyNames = new ArrayList<>();
    for (String strat : strategiesFromInnerToOuterMostNames) {
      if (!strategyOrder.containsKey(strat)) {
        throw new IllegalArgumentException("Strategy cannot be used for this composite strategy.");
      } else if (strategyNames.contains(strat)) {
        throw new IllegalArgumentException("Duplicate strategy");
      } else {
        strategyNames.add(strat);
      }
    }
    return strategiesFromInnerToOuterMostNames;
  }
}
