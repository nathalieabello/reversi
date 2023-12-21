package strategy;

import java.util.ArrayList;
import java.util.List;

import model.board.Coordinate;
import model.cell.DiscColor;

/**
 * Represents a strategy for a player to try to block the opposing player's single
 * strategy's resulting move.
 */
public class TryToBlockSingleStrategy extends AbstractSingleStrategy {

  private final AbstractSingleStrategy strategy;

  /**
   * Constructs a strategy for a player to try to block a single move.
   */
  public TryToBlockSingleStrategy(AbstractSingleStrategy strategy) {
    super(strategy == null ? null : strategy.player, strategy == null ? null : strategy.model);
    this.strategy = strategy;
  }

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
        this.strategy.getStrategyPossibleMoves(
            this.getAllPossiblePlayerMoves(opposingPlayer),
            opposingPlayer);

    for (Coordinate c : opposingPlayerMoves) {
      if (candidateMoves.contains(c)) {
        possibleMoves.add(c);
      }
    }
    return possibleMoves;
  }
}
