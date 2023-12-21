package player;

import java.util.List;
import java.util.Optional;

import model.board.Coordinate;
import model.board.ReadOnlyModel;
import model.cell.DiscColor;
import strategy.AIStrategy;

/**
 * Represents an AI player that can utilize a single strategy or a composite AI strategy.
 */
public class AIPlayer implements Player {
  private final DiscColor playerColor;
  private final AIStrategy strategy;

  /**
   * Constructs an AI player with a given strategy.
   */
  public AIPlayer(DiscColor playerColor, AIStrategy strategy) {
    if (playerColor == null || playerColor == DiscColor.NONE || strategy == null) {
      throw new IllegalArgumentException("Constructor arguments are invalid.");
    }

    this.playerColor = playerColor;
    this.strategy = strategy;
  }

  /**
   * Makes a move for the AI player based on the strategy.
   * If the strategy returns an empty optional, then the AI will play a random move.
   * If there are no random possible moves, then the AI will pass.
   *
   * @param model is the model to play the move on.
   * @return the coordinates of the move.
   */
  @Override
  public Optional<Coordinate> play(ReadOnlyModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null.");
    }

    Optional<Coordinate> moveToPlay = this.strategy.move();
    if (moveToPlay.isEmpty()) {
      List<Coordinate> possibleMoves = this.strategy.getAllPossiblePlayerMoves(this.playerColor);
      if (possibleMoves.isEmpty()) {
        return Optional.empty();
      } else {
        return Optional.of(possibleMoves.get(0));
      }
    } else {
      return moveToPlay;
    }
  }

  @Override
  public DiscColor getPlayerColor() {
    return this.playerColor;
  }

  @Override
  public boolean isHuman() {
    return false;
  }
}
