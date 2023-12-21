package model.cell;

/**
 * A square cell for our square reversi game.
 */
public class SquareCell extends HexCell implements ICell {

  /**
   * Constructor for a square cell.
   */
  public SquareCell() {
    super();
  }

  /**
   * Creates a new square cell.
   *
   * @return a new square cell.
   */
  @Override
  public ICell makeCell() {
    return new SquareCell();
  }
}
