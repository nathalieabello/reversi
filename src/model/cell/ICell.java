package model.cell;

/**
 * The cell interface, so it easy to implement different types cells.
 */
public interface ICell {
  /**
   * Returns this cell's color.
   *
   * @return this cells color.
   */
  DiscColor getColor();

  /**
   * Changes this cell's color to the given color.
   *
   * @param color the color to change to.
   * @throws IllegalArgumentException if cell is not able to change to the given color.
   */
  void changeColor(DiscColor color);

  /**
   * Returns a string representation of this cell.
   *
   * @return a string representation of this cell.
   */
  String toString();

  /**
   * Creates a new hexagonal cell.
   *
   * @return a new hexagonal cell.
   */
  ICell makeCell();
}
