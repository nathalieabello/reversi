package gui;

import java.awt.event.ComponentListener;
import java.awt.event.KeyListener;
import java.util.List;

import events.IPanelEvent;
import events.IViewEvent;
import model.board.Coordinate;
import player.Player;


/**
 * Represents a GUIView interface.
 */
public interface GUIView extends IPanelEvent, KeyListener, ComponentListener {

  /**
   * Sets the view to visible.
   *
   * @param b is whether the view is visible.
   */
  void setVisible(boolean b);

  /**
   * Adds a title to the window to make the player color more clear.
   *
   * @param player the player that corresponds with this view
   */
  void setPlayer(Player player);

  /**
   * Adds a listener to the list of listeners.
   *
   * @param listener is the listener to be added.
   */
  void addViewListener(IViewEvent listener);

  /**
   * Gets the selected cell.
   *
   * @return the selected cell.
   */
  Coordinate getSelectedCell();

  /**
   * Updates the canvas and determines whether to only deselect the cell or not.
   */
  void updateCanvas(boolean deselect);

  /**
   * Get the board.
   *
   * @return the board.
   */
  GUIPanel getPanel();

  /**
   * Get list of listeners.
   *
   * @return the list of listeners.
   */
  List<IViewEvent> getListeners();
}
