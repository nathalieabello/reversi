package model.board;

import java.util.ArrayList;
import java.util.List;

import model.cell.DiscColor;
import model.cell.ICell;
import model.cell.SquareCell;
import ruleskeeper.RuleKeeper;

import model.board.HexReversiModel.HexBuilder;

/**
 * Represents a model for a square Reversi game.
 * A square Reversi game is represented by a square board.
 */
public class SquareReversiModel extends AbstractReversiModel {

  /**
   * Represents a Builder object for a ReversiModel, that
   * allows flexibility in the construction of a ReversiModel
   * by allowing different types of cells, different numbers of layers,
   * different player colors,
   * and different rule keepers.
   */
  public static class SquareBuilder extends HexBuilder {

    /**
     * Constructs a Square Builder object.
     * Sets the default rule keeper to a ReversiRuleKeeper,
     * sets the default type of cell to a SquareCell,
     * sets the default number of layers to 5,
     * sets the default player colors to black and white.
     */
    public SquareBuilder() {
      super();
      this.typeCell = new SquareCell();
    }

    /**
     * Builds a Square ReversiModel object.
     *
     * @return a Square ReversiModel object.
     */
    @Override
    public IModel build() {
      return new SquareReversiModel(this.ruleKeeper, this.typeCell,
              this.layers, this.playerColors);
    }
  }

  /**
   * Constructor of the class HexReversiModel, that sets placeholder values
   * for the fields of the class/ default values for the parameters.
   * INVARIANT: the given number of layers is not less than 1.
   *
   * @param ruleKeeper   the rule keeper to use for this board.
   * @param typeCell     the type of cell to fill the grid width.
   * @param layers       the number of layers in this board (side-length divided by 2).
   * @param playerColors the player colors (to be able to play with different colors).
   * @throws IllegalArgumentException if the given rule keeper is null,
   *                                  or if the given cell is null,
   *                                  or if the given number of layers is less than 1 or odd,
   *                                  or if the given list of colors is not at least of size 2,
   *                                  or if the given list of colors contains a NONE color,
   *                                  or if the given list of colors contains a null color,
   *                                  or if the given list of colors contains a duplicate color,
   *                                  or if the given list of colors is null.
   */
  private SquareReversiModel(RuleKeeper ruleKeeper, ICell typeCell,
                             int layers, List<DiscColor> playerColors) {
    super(ruleKeeper, typeCell, layers, playerColors);
    if (layers == 1) {
      throw new IllegalArgumentException("The number of layers cannot be 1 for a square board.");
    }
    this.fillGrid(typeCell); //use the overriden method in this class
  }

  /**
   * Create a copy of the given model.
   *
   * @param model the model to copy.
   */
  public SquareReversiModel(IModel model) {
    super(model);
  }

  /**
   * Fills this grid with cells for all possible coordinates of Square Reversi board.
   */
  @Override
  protected void fillGrid(ICell cell) {
    for (int row = -layers; row <= layers; row++) {
      for (int col = -layers; col <= layers; col++) {
        if (col != 0 && row != 0) { // no center cell, so no origin col or row.
          ICell curr = cell.makeCell();
          Coordinate coord = new Coordinate(row, col);
          this.grid.put(coord, curr);
        }
      }
    }
    DiscColor player1 = this.playerColors.get(0);
    DiscColor player2 = this.playerColors.get(1);
    // change to player 1's color:
    this.changeCellColor(new Coordinate(-1, -1), player1); // top left
    this.changeCellColor(new Coordinate(1, 1), player1); // bottom right
    // change to player 2's color:
    this.changeCellColor(new Coordinate(-1, 1), player2); // top right
    this.changeCellColor(new Coordinate(1, -1), player2); // bottom left
  }

  /**
   * Get the neighboring cells' coordinates of the cell at the given coordinate.
   *
   * @param coord the coordinate of the cell to get the neighbors of.
   * @return the neighboring cells' coordinates of the cell at the given coordinate.
   * @throws IllegalArgumentException if the given coordinate is invalid.
   * @throws IllegalStateException    if the game has not yet been started.
   */
  @Override
  public List<Coordinate> getNeighbors(Coordinate coord) {
    this.invalidCoordinateException(coord);
    List<Coordinate> workingList = new ArrayList<>();
    int row = coord.getRow();
    int col = coord.getCol();
    for (int r = -1; r <= 1; r++) {
      for (int c = -1; c <= 1; c++) {
        int newRow = row + r;
        int newCol = col + c;
        if (newRow == 0) {
          if (newRow < coord.getRow()) {
            newRow = - 1;
          } else {
            newRow = 1;
          }
        }
        if (newCol == 0) {
          if (newCol < coord.getCol()) {
            newCol = - 1;
          } else {
            newCol = 1;
          }
        }
        Coordinate neighborCoord = new Coordinate(newRow, newCol);
        if (this.grid.containsKey(neighborCoord)
                && !neighborCoord.equals(coord)
                && !workingList.contains(neighborCoord)) {
          workingList.add(neighborCoord);
        }
      }
    }
    // return the list of neighbors
    return workingList;
  }

  /**
   * Returns the next coordinate in the direction of the given coordinate.
   *
   * @param origin the origin coordinate.
   * @param c      the coordinate to get the next coordinate of.
   * @return the next coordinate in the direction of the given coordinate.
   */
  protected Coordinate getNextCoordinate(Coordinate origin, Coordinate c) {
    Coordinate coord = super.getNextCoordinate(origin, c);

    int finalCoordX = coord.getRow();
    int finalCoordY = coord.getCol();
    if (finalCoordX == 0) {
      if (c.getRow() > 0) {
        finalCoordX -= 1;
      } else {
        finalCoordX += 1;
      }
    }
    if (finalCoordY == 0) {
      if (c.getCol() > 0) {
        finalCoordY -= 1;
      } else {
        finalCoordY += 1;
      }
    }
    return new Coordinate(finalCoordX, finalCoordY);
  }

  /**
   * Returns whether the given coordinate is a corner or not.
   *
   * @param coord the coordinate to check.
   * @return a boolean that determines it.
   * @throws IllegalArgumentException if the given coordinate is invalid.
   * @throws IllegalStateException    if the game has not yet been started.
   */
  @Override
  public boolean isCorner(Coordinate coord) {
    this.gameNotYetStartedException();
    this.invalidCoordinateException(coord);
    int coordCol = coord.getCol();
    int coordRow = coord.getRow();
    return (coordRow == -layers && (coordCol == -layers || coordCol == layers))
            || (coordRow == layers && (coordCol == layers || coordCol == -layers));
  }


  /**
   * Returns an actionable copy of the board.
   *
   * @param model the model to get the actionable copy of.
   * @return an actionable copy of the board.
   * @throws IllegalStateException if the game has not yet been started.
   */
  @Override
  public IModel getActionableCopyOfBoard(IModel model) {
    this.gameNotYetStartedException();

    return new SquareReversiModel(model);
  }
}
