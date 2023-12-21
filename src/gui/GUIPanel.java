package gui;


import java.awt.event.MouseListener;

import events.IPanelEvent;

/**
 * Represents a GUIPanel interface.
 */
public interface GUIPanel extends MouseListener {
  /**
   * Adds a listener to the list of listeners.
   *
   * @param listener is the listener to be added.
   */
  void addPanelListener(IPanelEvent listener);

  /**
   * Updates the size of the canvas.
   *
   * @param cellSize is the size of the cell.
   */
  void updateSize(int cellSize);

  /**
   * Updates the canvas and determines whether to only deselect the cell or not.
   */
  void updateCanvas(boolean deselect);

  /**
   * Get the selected cell.
   *
   * @return the selected cell.
   */
  AbstractGUICell getSelectedCell();

  /**
   * Sets up the panel.
   */
  void setUpPanel();

  /**
   * The number of sides this panel has.
   *
   * @return the number of sides of the grid, for initial scoring purposes
   */
  int getSides();
}
