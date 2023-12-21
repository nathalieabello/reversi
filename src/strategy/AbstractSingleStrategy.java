package strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.board.Coordinate;
import model.board.ReadOnlyModel;
import model.cell.DiscColor;

/**
 * Represents an abstract single strategy for a player to make a move.
 */
public abstract class AbstractSingleStrategy implements AIStrategy {

  protected final DiscColor player;
  protected final ReadOnlyModel model;

  /**
   * Constructs a strategy for a player to make a move.
   */
  protected AbstractSingleStrategy(DiscColor player, ReadOnlyModel model) {
    if (player == null || model == null) {
      throw new IllegalArgumentException("Player and model cannot be null");
    } else if (player == DiscColor.NONE) {
      throw new IllegalArgumentException("Player cannot be NONE");
    }
    this.player = player;
    this.model = model;
  }

  /**
   * Determines the coordinate at which a disc is to be played.
   * Return an empty optional if no possible moves based on the strategy.
   *
   * @return the coordinate at which a disc is to be played.
   */
  @Override
  public Optional<Coordinate> move() {
    List<Coordinate> candidateMoves =
        this.getStrategyPossibleMoves(this.getAllPossiblePlayerMoves(this.player), this.player);
    if (candidateMoves.isEmpty()) {
      return Optional.empty();
    } else {
      return Optional.of(candidateMoves.get(0));
    }
  }

  /**
   * Provides a list of possible moves for the player based on the strategy.
   *
   * @param candidateMoves is the list of all candidate moves that have yet to be filtered to fit
   *                       the strategy.
   * @param player         is the player whose move it is.
   * @return a list of possible moves for the player based on the strategy.
   */
  public abstract List<Coordinate> getStrategyPossibleMoves(List<Coordinate> candidateMoves,
                                                            DiscColor player);

  /**
   * Gets all possible moves for the player.
   *
   * @return all possible moves for the player.
   */
  public List<Coordinate> getAllPossiblePlayerMoves(DiscColor player) {
    this.checkPlayerException(player);
    List<Coordinate> possibleMoves = new ArrayList<>();
    // get all possible moves
    for (Coordinate c : this.model.getCopyOfAllCoords().keySet()) {
      if (this.model.getRuleKeeper().isValid(this.model, c, player)) {
        possibleMoves.add(c);
      }
    }
    return possibleMoves;
  }

  /**
   * Gets the opposing player color.
   */
  public DiscColor getOpposingPlayerColor() {
    List<DiscColor> playerColors = new ArrayList<>(this.model.getPlayerColors());
    playerColors.remove(this.player);
    return playerColors.get(0);
  }

  /**
   * Checks if the player is valid.
   *
   * @param player is the player to be checked.
   * @throws IllegalArgumentException if the player is null or NONE, or if the player is not a
   *                                  player in the model.
   */
  protected void checkPlayerException(DiscColor player) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null");
    } else if (player == DiscColor.NONE) {
      throw new IllegalArgumentException("Player cannot be NONE");
    } else if (!this.model.getPlayerColors().contains(player)) {
      throw new IllegalArgumentException("Player has to be a player in the model");
    }
  }

  /**
   * Checks if the given list of candidate moves is valid.
   *
   * @param candidateMoves is the list of candidate moves to be checked.
   * @throws IllegalArgumentException if the list of candidate moves is null.
   */
  protected void checkCandidateMovesException(List<Coordinate> candidateMoves, DiscColor player) {
    if (candidateMoves == null) {
      throw new IllegalArgumentException("Candidate moves cannot be null");
    } else {
      try {
        if (candidateMoves.contains(null)) {
          throw new IllegalArgumentException("Candidate moves cannot contain null");
        }
      } catch (NullPointerException e) {
        throw new IllegalArgumentException("Candidate moves cannot contain null");
      }
    }

    for (Coordinate c : candidateMoves) {
      if (!this.model.getCopyOfAllCoords().containsKey(c)) {
        throw new IllegalArgumentException("Candidate moves must be in the model");
      } else if (!this.model.getRuleKeeper().isValid(this.model, c, player)) {
        throw new IllegalArgumentException("Candidate moves must be valid moves in the model");
      }
    }
  }

}
