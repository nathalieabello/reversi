package model.cell;

import java.awt.Color;

/**
 * The different colors a cell's disk can be and their string representation.
 */
public enum DiscColor {
  WHITE("O", Color.WHITE),
  BLACK("X", Color.BLACK),
  MAGENTA("M", Color.MAGENTA), //for testing purposes.
  NONE("_", Color.LIGHT_GRAY); //NONE is a special value for a cell that has no disc (color).

  private final String string;
  private final Color value;

  /**
   * Constructor for the DiscColor enum.
   *
   * @param string the string representation of this color.
   * @param value  the color value of this color.
   */
  DiscColor(String string, Color value) {
    this.string = string;
    this.value = value;
  }

  /**
   * Returns the string representation of this color.
   *
   * @return the string representation of this color.
   */
  @Override
  public String toString() {
    return this.string;
  }

  /**
   * Returns the value of this disc color.
   *
   * @return the value of this disc color.
   */
  public Color getDiscColorValue() {
    return this.value;
  }
}
