package gui;

import java.awt.Color;
import java.awt.Polygon;

/**
 * Represents a square tile in the game board.
 */
public class GUISquareCell extends AbstractGUICell {
  private final Polygon square;

  /**
   * Constructs a GUIHexCell.
   *
   * @param color       is the color of the cell.
   * @param xCoordinate is the x coordinate of the cell on the board.
   * @param yCoordinate is the y coordinate of the cell on the board based on the diagonal.
   * @param size        is the size of the cell.
   */
  public GUISquareCell(Color color, boolean isSelected,
                    int xCoordinate, int yCoordinate,
                    int xCanvasCoordinate, int yCanvasCoordinate, int size) {
    super(color, isSelected,
        xCoordinate, yCoordinate,
        xCanvasCoordinate, yCanvasCoordinate, size);
    this.square = new Polygon(this.getXOrYCoordinates(true),
            this.getXOrYCoordinates(false), 4);
  }

  @Override
  protected Polygon polygon() {
    return this.square;
  }

  /**
   * Calculates the x or y coordinates of the square.
   *
   * @param isX is true if the x coordinates are to be calculated, false if the y coordinates are
   *            to be calculated.
   * @return the x or y coordinates of the square.
   */
  @Override
  protected int[] getXOrYCoordinates(boolean isX) {
    int[] points = new int[4];
    int halfSize = this.size / 2;
    if (isX) {
      points[0] = this.xCanvasCoordinate - halfSize;
      points[1] = this.xCanvasCoordinate + halfSize;
      points[2] = this.xCanvasCoordinate + halfSize;
      points[3] = this.xCanvasCoordinate - halfSize;
    } else {
      points[0] = this.yCanvasCoordinate - halfSize;
      points[1] = this.yCanvasCoordinate - halfSize;
      points[2] = this.yCanvasCoordinate + halfSize;
      points[3] = this.yCanvasCoordinate + halfSize;
    }
    return points;
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof GUISquareCell)) {
      return false;
    }
    GUISquareCell that = (GUISquareCell) other;
    return this.xCoordinate == that.xCoordinate
        && this.yCoordinate == that.yCoordinate;
  }

  @Override
  public int hashCode() {
    return this.xCoordinate * 102343 + this.yCoordinate * 650 + 42;
  }
}
