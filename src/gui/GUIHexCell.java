package gui;

import java.awt.Color;
import java.awt.Polygon;

/**
 * Represents a hexagon tile in the game board.
 */
public class GUIHexCell extends AbstractGUICell {
  private final Polygon hex;

  /**
   * Constructs a GUIHexCell.
   *
   * @param color       is the color of the cell.
   * @param xCoordinate is the x coordinate of the cell on the board.
   * @param yCoordinate is the y coordinate of the cell on the board based on the diagonal.
   * @param size        is the size of the cell.
   */
  public GUIHexCell(Color color, boolean isSelected,
                    int xCoordinate, int yCoordinate,
                    int xCanvasCoordinate, int yCanvasCoordinate, int size) {

    super(color, isSelected,
            xCoordinate, yCoordinate,
            xCanvasCoordinate, yCanvasCoordinate, size);

    this.hex = new Polygon(this.getXOrYCoordinates(true),
            this.getXOrYCoordinates(false), 6);
  }

  @Override
  protected Polygon polygon() {
    return this.hex;
  }

  /**
   * Calculates the x or y coordinates of the hexagon.
   *
   * @param isX is true if the x coordinates are to be calculated, false if the y coordinates are
   *            to be calculated.
   * @return the x or y coordinates of the hexagon.
   */
  protected int[] getXOrYCoordinates(boolean isX) {
    //calculate the coordinates for the six points of the hexagon with a pointed top
    int[] xPoints = new int[6];
    int[] yPoints = new int[6];
    for (int i = 0; i < 6; i++) {
      double angle = (2 * Math.PI / 6 * i) + Math.PI / 6;
      xPoints[i] = (int) (this.xCanvasCoordinate + this.size / 2 * Math.cos(angle));
      yPoints[i] = (int) (this.yCanvasCoordinate + this.size / 2 * Math.sin(angle));
    }

    return isX ? xPoints : yPoints;
  }

  /**
   * Returns true if the given object is equal to the hex cell based on the x and y coordinates.
   *
   * @param other is the object to compare to.
   * @return true if the given object is equal to the hex cell based on the x and y coordinates.
   */
  @Override
  public boolean equals(Object other) {
    if (!(other instanceof GUIHexCell)) {
      return false;
    }
    GUIHexCell that = (GUIHexCell) other;
    return this.xCoordinate == that.xCoordinate
            && this.yCoordinate == that.yCoordinate;
  }

  /**
   * Returns the hash code of the hex cell based on the x and y coordinates.
   *
   * @return the hash code of the hex cell based on the x and y coordinates.
   */
  @Override
  public int hashCode() {
    return this.xCoordinate * 1000123 + this.yCoordinate * 600 + 43;
  }
}
