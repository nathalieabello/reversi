package ruleskeeper;

import java.util.ArrayList;
import java.util.List;

import model.board.Coordinate;
import model.board.IModel;
import model.board.ReadOnlyModel;
import model.cell.DiscColor;

/**
 * The class that determines the rules of the game based on the given assignment instructions.
 */
public class ReversiRuleKeeper implements RuleKeeper {
  /**
   * Constructs a ReversiRuleKeeper.
   */
  public ReversiRuleKeeper() {
    // does not need to take in anything yet --> may need to later after further implementation
  }

  /**
   * Determines if the given coordinates indicates a valid move.
   *
   * @param model the model to check.
   * @param coord the coordinates ot check
   * @return a boolean explaining if the given coord represents a valid move.
   * @throws IllegalArgumentException if either one of the given arguments are null.
   */
  public boolean isValid(ReadOnlyModel model, Coordinate coord, DiscColor player) {
    this.nullArgumentsException(model, coord);
    if (player == null || player == DiscColor.NONE || !model.getPlayerColors().contains(player)) {
      throw new IllegalArgumentException("Invalid player color argument.");
    }

    if (model.getCellAt(coord).getColor() == DiscColor.NONE) {
      return this.hasLikeNeighbor(model, coord, player)
          || this.canSandwich(model, coord, player);
    } else {
      return false;
    }
  }

  /**
   * Returns a list of coordinates that
   * need their colors changed based on the given coordinate,
   * and the current player's turn.
   *
   * @param model the model to play the disc on.
   * @param coord the coordinate to play the disc at.
   * @return a list of coordinates of cells whose color should be changed.
   * @throws IllegalStateException    if the move is invalid.
   * @throws IllegalArgumentException if either one of the given arguments are null.
   */
  public List<Coordinate> playDisc(IModel model, Coordinate coord) {
    this.nullArgumentsException(model, coord);

    List<Coordinate> coordOfCellsToChangeColor = new ArrayList<>();
    if (this.isValid(model, coord, model.getTurn())) {
      coordOfCellsToChangeColor.add(coord);
      //adds all the sandwichable neighbors if they exit.
      coordOfCellsToChangeColor.addAll(model.getSandwichableNeighbors(coord, model.getTurn()));
    } else {
      throw new IllegalStateException("Invalid move. Can't play disc here.");
    }

    return coordOfCellsToChangeColor;
  }

  /**
   * Returns a boolean representing whether the given coordinate has like neighbors.
   *
   * @param coord the cell's coordinate to check for like neighbors.
   * @return whether the given coordinate has like neighbors.
   */
  private boolean hasLikeNeighbor(ReadOnlyModel model, Coordinate coord, DiscColor player) {
    List<Coordinate> likeNeighbors = model.getLikeNeighbors(coord, player);
    return !likeNeighbors.isEmpty();
  }

  /**
   * Returns a boolean that determines whether playing a disc, at the given coordinates,
   * will sandwich any of the opposite player's discs.
   *
   * @param coord where clicked.
   * @return if we can sandwich with this coord.
   */
  private boolean canSandwich(ReadOnlyModel model, Coordinate coord, DiscColor player) {
    List<Coordinate> sandwichNeighbors = model.getSandwichableNeighbors(coord, player);
    return !sandwichNeighbors.isEmpty();
  }

  /**
   * Throws an IllegalArgumentException if either one of the given arguments are null.
   *
   * @param model the model to check.
   * @param coord the coordinate to check.
   * @throws IllegalArgumentException if either one of the given arguments are null.
   */
  private void nullArgumentsException(ReadOnlyModel model, Coordinate coord) {
    if (model == null || coord == null) {
      throw new IllegalArgumentException("Cannot pass in null arguments.");
    } else if (!model.getCopyOfAllCoords().containsKey(coord)) {
      throw new IllegalArgumentException("Invalid coordinate argument.");
    }
  }

}
