package model.board;

import java.util.ArrayList;
import java.util.List;

import model.cell.DiscColor;
import model.cell.HexCell;
import model.cell.ICell;
import ruleskeeper.ReversiRuleKeeper;
import ruleskeeper.RuleKeeper;

/**
 * Represents the board of the hex Reversi game.
 * A hex Reversi game is represented by a hexagonal board.
 */
public class HexReversiModel extends AbstractReversiModel {

  /**
   * Represents a Builder object for a ReversiModel, that
   * allows flexibility in the construction of a ReversiModel
   * by allowing different types of cells, different numbers of layers, different player colors,
   * and different rule keepers.
   */
  public static class HexBuilder {
    protected RuleKeeper ruleKeeper;
    protected ICell typeCell;
    protected int layers;
    protected List<DiscColor> playerColors;

    /**
     * Constructs a Hex Builder object.
     * Sets the default rule keeper to a ReversiRuleKeeper,
     * sets the default type of cell to a HexCell,
     * sets the default number of layers to 5,
     * sets the default player colors to black and white.
     */
    public HexBuilder() {
      this.ruleKeeper = new ReversiRuleKeeper();
      this.typeCell = new HexCell();
      this.layers = 5;
      this.playerColors = new ArrayList<>(List.of(DiscColor.BLACK, DiscColor.WHITE));
    }

    /**
     * Sets the rule keeper of this builder.
     *
     * @param ruleKeeper the rule keeper to set.
     * @return this builder.
     */
    public HexBuilder setRuleKeeper(RuleKeeper ruleKeeper) {
      this.ruleKeeper = ruleKeeper;
      return this;
    }

    /**
     * Sets the type of cell of this builder.
     *
     * @param typeCell the type of cell to set.
     * @return this builder.
     */
    public HexBuilder setTypeCell(ICell typeCell) {
      this.typeCell = typeCell;
      return this;
    }

    /**
     * Sets the number of layers of this builder.
     *
     * @param layers the number of layers to set.
     * @return this builder.
     */
    public HexBuilder setLayers(int layers) {
      this.layers = layers;
      return this;
    }

    /**
     * Sets the player colors of this builder.
     *
     * @param playerColors the player colors to set.
     * @return this builder.
     */
    public HexBuilder setPlayerColors(List<DiscColor> playerColors) {
      this.playerColors = playerColors;
      return this;
    }

    /**
     * Builds a Hex ReversiModel object.
     *
     * @return a Hex ReversiModel object.
     */
    public IModel build() {
      return new HexReversiModel(this.ruleKeeper, this.typeCell, this.layers, this.playerColors);
    }
  }

  private HexReversiModel(RuleKeeper ruleKeeper, ICell typeCell,
                                 int layers, List<DiscColor> playerColors) {
    super(ruleKeeper, typeCell, layers, playerColors);
  }

  /**
   * Create a copy of the given model.
   *
   * @param model the model to copy.
   */
  public HexReversiModel(IModel model) {
    super(model);
  }

  /**
   * Fills this grid with cells for all possible coordinates of Hex Reversi board.
   */
  protected void fillGrid(ICell cell) {
    int colStart = 0;
    int colEnd = layers;
    for (int row = -layers; row <= layers; row++) {
      int col = colStart;
      while (col <= colEnd) {
        ICell curr = cell.makeCell();
        Coordinate coord = new Coordinate(row, col);
        grid.put(coord, curr);
        col++;
      }
      if (row < 0) {
        colStart--;
      } else {
        colEnd--;
      }
    }
    DiscColor player1 = this.playerColors.get(0);
    DiscColor player2 = this.playerColors.get(1);
    // change to player 1's color:
    this.changeCellColor(new Coordinate(1, 0), player1); // right
    this.changeCellColor(new Coordinate(0, -1), player1); // top left
    this.changeCellColor(new Coordinate(-1, 1), player1); // bottom left
    // change to player 2's color:
    this.changeCellColor(new Coordinate(-1, 0), player2); // left
    this.changeCellColor(new Coordinate(0, 1), player2); // bottom right
    this.changeCellColor(new Coordinate(1, -1), player2); // top right
  }

  /**
   * Get the neighboring cells' coordinates of the cell at the given coordinate.
   *
   * @param coord the coordinate of the cell to get the neighbors of.
   * @return the neighboring cells' coordinates of the cell at the given coordinate.
   * @throws IllegalArgumentException if the given coordinate is invalid.
   * @throws IllegalStateException    if the game has not yet been started.
   */
  public List<Coordinate> getNeighbors(Coordinate coord) {
    this.gameNotYetStartedException();
    this.invalidCoordinateException(coord);

    List<Coordinate> workingList = new ArrayList<>();
    int row = coord.getRow(); // given row
    int col = coord.getCol(); // given col
    workingList.add(new Coordinate(row - 1, col)); // left
    workingList.add(new Coordinate(row + 1, col)); // right
    workingList.add(new Coordinate(row, col - 1)); // top left
    workingList.add(new Coordinate(row, col + 1)); // bottom right
    workingList.add(new Coordinate(row + 1, col - 1)); // top right
    workingList.add(new Coordinate(row - 1, col + 1)); // bottom left
    // removes any coordinates added that are beyond the lengths of this grid
    workingList.removeIf(c -> !this.grid.containsKey(c));
    // return the list of neighbors
    return workingList;
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
    if (coordRow == -layers) {
      return coordCol == 0 || coordCol == layers;
    } else if (coordRow == layers) {
      return coordCol == 0 || coordCol == -layers;
    } else if (coordRow == 0) {
      return coordCol == layers || coordCol == -layers;
    }
    return false;
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

    return new HexReversiModel(model);
  }
}
