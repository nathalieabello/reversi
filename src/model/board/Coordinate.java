package model.board;

/**
 * Used to indicate where a cell is on a board.
 */
public class Coordinate {
  private final int row;
  private final int col;

  /**
   * Constructs coordinates with the given row and column.
   *
   * @param row the row coordinate
   * @param col the col coordinate
   */
  public Coordinate(int row, int col) {
    this.row = row;
    this.col = col;
  }

  /**
   * Gets the row.
   *
   * @return this coordinate's row
   */
  public int getRow() {
    return this.row;
  }

  /**
   * Gets the column.
   *
   * @return this coordinate's column
   */
  public int getCol() {
    return this.col;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    } else if (o instanceof Coordinate) {
      Coordinate other = (Coordinate) o;
      return ((other.row == this.row)
          && (other.col == this.col));
    }
    return false;
  }

  @Override
  public int hashCode() {
    return this.col * 100000 + this.row * 700 + 56;
  }

  /**
   * Compares this coordinate to another coordinate.
   * The comparison is based on the sum of the row and column of the coordinates.
   * The coordinate with the smaller sum is considered "less than" the other coordinate.
   * If the sums are equal, then the coordinates are considered equal.
   *
   * @param other the other coordinate.
   * @return a negative number if this coordinate is less than the other coordinate,
   *         a positive number if this coordinate is greater than the other coordinate,
   *         and 0 if they are equal.
   */
  public int compareTo(Coordinate other) {
    return Integer.compare(this.col + this.row, other.col + other.row);
  }
}
