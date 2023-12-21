package strategy;

import java.util.ArrayList;
import java.util.List;

import model.board.Coordinate;
import model.cell.DiscColor;

/**
 * Represents a composite strategy for a player to make a move by blocking the opponent's
 * resulting strategy move.
 */
public class TryToBlockCompositeStrategy extends AbstractCompositeStrategy {

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
  public TryToBlockCompositeStrategy(AbstractSingleStrategy strategy, AIStrategy nextStrategy) {
    super(strategy, nextStrategy);

    if (nextStrategy instanceof AbstractCompositeStrategy) {
      if (!(nextStrategy instanceof TryToBlockCompositeStrategy)) {
        throw new IllegalArgumentException("Cannot have a composite strategy in this class" +
            "that is not TryToBlockCompositeStrategy");
      }
    }
  }

  /**
   * Gets the possible moves for the TryToBlockCompositeStrategy which is the intersection of the
   * possible moves of the TryToWinCompositeStrategy of the opposite player and the possible moves
   * of the current player.
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

    DiscColor opposingPlayer = this.strategy.getOpposingPlayerColor();
    List<Coordinate> possibleMoves = new ArrayList<>();

    //create a default TryToWinCompositeStrategy to get the possible moves of the opposing player.
    //prevents repetition of code.
    List<Coordinate> opposingPlayerMoves =
        this.createTryToWinCompositeStrategy()
            .getStrategyPossibleMoves(
                this.getAllPossiblePlayerMoves(opposingPlayer),
                opposingPlayer);

    for (Coordinate c : opposingPlayerMoves) {
      if (candidateMoves.contains(c)) {
        possibleMoves.add(c);
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
  @Override
  protected void checkStrategyOrder(List<AbstractSingleStrategy> strategiesFromInnerToOuterMost) {

    //constructs a default TryToWinCompositeStrategy to check the order of the strategies.
    //prevents repetition of code.
    this.createTryToWinCompositeStrategy()
        .checkStrategyOrder(strategiesFromInnerToOuterMost);
  }

  /**
   * Creates a TryToWinCompositeStrategy version of this TryToBlockCompositeStrategy
   * to check the order of the strategies, and check the opposing player's possible moves.
   *
   * @return a TryToWinCompositeStrategy version of this TryToBlockCompositeStrategy.
   */
  public TryToWinCompositeStrategy createTryToWinCompositeStrategy() {
    List<AbstractSingleStrategy> strategiesFromInnerToOuterMost =
        this.getAllStrategiesFromInnerToOuterMost();

    TryToWinCompositeStrategy nextStrategy = new TryToWinCompositeStrategy(
        strategiesFromInnerToOuterMost.get(1),
        strategiesFromInnerToOuterMost.get(0));

    for (int i = 2; i < strategiesFromInnerToOuterMost.size(); i++) {
      nextStrategy = new TryToWinCompositeStrategy(
          strategiesFromInnerToOuterMost.get(i), nextStrategy);
    }

    return nextStrategy;
  }
}

