package model.cell;

/**
 * A hexagonal cell for our hex reversi game.
 */
public class HexCell implements ICell {
  protected DiscColor color;

  /**
   * Constructor for a hexagonal cell.
   */
  public HexCell() {
    this.color = DiscColor.NONE;
  }

  @Override
  public DiscColor getColor() {
    return this.color;
  }

  @Override
  public void changeColor(DiscColor color) {
    if (this.color == color) {
      throw new IllegalArgumentException("already this color");
    } else {
      this.color = color;
    }
  }

  @Override
  public String toString() {
    return this.color.toString();
  }

  @Override
  public ICell makeCell() {
    return new HexCell();
  }
}
