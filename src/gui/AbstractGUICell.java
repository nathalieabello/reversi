package gui;

import java.awt.Color;
import java.awt.Polygon;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.geom.Point2D;

import model.board.Coordinate;
import model.cell.DiscColor;

/**
 * An abstract class that represents a GUI cell.
 */
public abstract class AbstractGUICell {

  protected final Color color;
  protected boolean isSelected;
  protected final int xCoordinate;
  protected final int yCoordinate;
  protected final int size;
  protected final int xCanvasCoordinate;
  protected final int yCanvasCoordinate;

  /**
   * Common constructor for all GUI cells.
   * @param color is the color of the cell.
   * @param isSelected is true if the cell is selected.
   * @param xCoordinate is the x coordinate of the cell on the board.
   * @param yCoordinate is the y coordinate of the cell on the board based on the diagonal.
   * @param xCanvasCoordinate is the x coordinate of the cell on the canvas.
   * @param yCanvasCoordinate is the y coordinate of the cell on the canvas.
   * @param size is the size of the cell.
   */
  public AbstractGUICell(Color color, boolean isSelected,
                         int xCoordinate, int yCoordinate,
                         int xCanvasCoordinate, int yCanvasCoordinate, int size) {

    this.color = color;
    this.isSelected = isSelected;

    this.xCoordinate = xCoordinate;
    this.yCoordinate = yCoordinate;

    this.xCanvasCoordinate = xCanvasCoordinate;
    this.yCanvasCoordinate = yCanvasCoordinate;

    this.size = size;
  }

  /**
   * Returns the x coordinate of the cell on the board.
   *
   * @return the x coordinate of the cell on the board.
   */
  protected int getXCoordinate() {
    return this.xCoordinate;
  }

  /**
   * Returns the y coordinate of the cell on the board.
   *
   * @return the y coordinate of the cell on the board.
   */
  protected int getYCoordinate() {
    return this.yCoordinate;
  }

  /**
   * Returns the x coordinate of the cell on the canvas.
   *
   * @return the x coordinate of the cell on the canvas.
   */
  protected int xCanvasCoordinate() {
    return this.xCanvasCoordinate;
  }

  /**
   * Returns the y coordinate of the cell on the canvas.
   *
   * @return the y coordinate of the cell on the canvas.
   */
  protected int yCanvasCoordinate() {
    return this.yCanvasCoordinate;
  }

  /**
   * Returns the size of the cell.
   *
   * @return the size of the cell.
   */
  protected int size() {
    return this.size;
  }

  /**
   * Returns the polygon of the cell.
   *
   * @return the polygon of the cell.
   */
  protected abstract Polygon polygon();

  /**
   * Returns true if the cell is selected, false otherwise.
   *
   * @return true if the cell is selected, false otherwise.
   */
  protected boolean isSelected() {
    return this.isSelected;
  }

  /**
   * Returns the color of the cell.
   *
   * @return the color of the cell.
   */
  protected Color color() {
    return this.color;
  }

  /**
   * Calculates the x or y coordinates of the cell.
   *
   * @param isX is true if the x coordinates are to be calculated, false if the y coordinates are
   *            to be calculated.
   * @return the x or y coordinates of the cell.
   */
  protected abstract int[] getXOrYCoordinates(boolean isX);

  /**
   * Draws the hexagon tile with a gray fill, and a black border.
   *
   * @param g is the graphics object.
   */
  protected void draw(Graphics2D g) {
    g.setColor(Color.BLACK); //set the color for the border.
    g.setStroke(new BasicStroke(3));
    g.drawPolygon(this.polygon()); //draw the border.

    if (this.isSelected()) {
      g.setColor(Color.CYAN);
      g.fillPolygon(this.polygon());
    } else {
      g.setColor(Color.LIGHT_GRAY); //set the color for the hexagon.
      g.fillPolygon(this.polygon()); //fill the hexagon.
    }

    //draw the cell's disc if there is one.
    if (this.color() != DiscColor.NONE.getDiscColorValue() && this.color() != null) {
      this.drawDisc(g);
    }
  }

  /**
   * Returns true if the given point is contained in the hexagon.
   *
   * @param point is the point to check.
   * @return true if the given point is contained in the hexagon.
   */
  public boolean containsPoint(Point2D point) {
    return this.polygon().contains(point);
  }

  /**
   * Sets the selected cell boolean to the given boolean. This is used to track
   * which cell has been selected as a potential move.
   *
   * @param isSelected is the boolean to set whether the cell is selected.
   */
  public void setIsSelected(boolean isSelected) {
    this.isSelected = isSelected;
  }

  /**
   * Returns true if the given object is equal to the cell based on the x and y coordinates.
   *
   * @param other is the object to compare to.
   * @return true if the given object is equal to the cell based on the x and y coordinates.
   */
  @Override
  public abstract boolean equals(Object other);

  /**
   * Returns the hash code of the cell based on the x and y coordinates.
   *
   * @return the hash code of the cell based on the x and y coordinates.
   */
  @Override
  public abstract int hashCode();

  /**
   * Returns the canvas center of the cell.
   *
   * @return the canvas center of the cell.
   */
  public Coordinate getCellCenter() {
    return new Coordinate(this.xCanvasCoordinate(), this.yCanvasCoordinate());
  }

  /**
   * Draws the disc of the cell.
   *
   * @param g is the graphics object.
   */
  protected void drawDisc(Graphics2D g) {
    g.setColor(this.color());
    int ovalSize = this.size() / 2;
    int ovalX = this.xCanvasCoordinate() - ovalSize / 2;
    int ovalY = this.yCanvasCoordinate() - ovalSize / 2;
    g.fillOval(ovalX, ovalY, ovalSize, ovalSize);
  }
}
