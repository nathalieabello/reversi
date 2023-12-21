package events;

/**
 * Represents an IPanelEvent interface that allows the canvas to communicate with its view.
 */
public interface IPanelEvent {
  /**
   * Determines which cell was clicked, and if it has been selected or deselected.
   * If the cell is selected, the selected cell is set to the cell clicked.
   * If the cell is deselected, the selected cell is set to null.
   *
   * @param xCoordinate is the x coordinate of the cell clicked.
   * @param yCoordinate is the y coordinate of the cell clicked.
   * @param selected    is whether the cell is selected.
   */
  void cellClicked(int xCoordinate, int yCoordinate, boolean selected);
}

