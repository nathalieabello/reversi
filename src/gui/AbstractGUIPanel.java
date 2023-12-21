package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.Point;
import java.util.List;
import java.util.Objects;

import javax.swing.JPanel;

import events.IPanelEvent;
import model.board.ReadOnlyModel;

import static gui.ReversiGUIView.BACKGROUND_COLOR;
import static gui.ReversiGUIView.CELL_SIZE;

/**
 * AbstractGUIPanel is a panel in which all repetition between different panels is consumed.
 */
public abstract class AbstractGUIPanel extends JPanel
        implements GUIPanel {
  protected final int sides;
  protected final ReadOnlyModel model;
  protected final int layers;
  protected AbstractGUICell selectedCell;
  protected final List<AbstractGUICell> cellList;
  protected final List<IPanelEvent> listenerList;

  /**
   * Constructs a ReversiPanel.
   *
   * @param model is the model of the Read-Only version of the game.
   */
  public AbstractGUIPanel(ReadOnlyModel model) {
    this.model = model;
    this.layers = model.getNumLayers();
    this.listenerList = new java.util.ArrayList<>();
    this.cellList = new java.util.ArrayList<>();
    this.sides = this.getSides();

    this.selectedCell = null;
    this.setUpPanel();
  }

  public abstract int getSides();

  /**
   * Sets up the panel.
   */
  public void setUpPanel() {
    setPreferredSize(this.createDimension(this.layers));
    setLayout(null);
    setBackground(BACKGROUND_COLOR);
    addMouseListener(this);

    this.drawGrid(this.layers, this.model);
  }

  protected AbstractGUICell[] getCellList() {
    return this.cellList.toArray(new AbstractGUICell[0]);
  }

  public AbstractGUICell getSelectedCell() {
    return this.selectedCell;
  }

  protected void setSelectedCell(AbstractGUICell cell) {
    this.selectedCell = cell;
  }

  public ReadOnlyModel getModel() {
    return this.model;
  }

  protected int getLayers() {
    return this.layers;
  }

  protected void clearCellList() {
    this.cellList.clear();
  }

  /**
   * Adds a listener to the list of listeners.
   *
   * @param listener is the listener to be added.
   */
  public void addPanelListener(IPanelEvent listener) {
    this.listenerList.add(Objects.requireNonNull(listener));
  }

  /**
   * Draws the grid of hexagon cells.
   *
   * @param layers is the number of layers in the game.
   * @param model  is the ReadOnly version model of the game.
   */
  protected abstract void drawGrid(int layers, ReadOnlyModel model);

  /**
   * Updates the size of the canvas.
   *
   * @param cellSize is the size of the cell.
   */
  public void updateSize(int cellSize) {
    this.clearCellList();

    setPreferredSize(this.createDimension(this.getLayers()));
    this.drawGrid(this.getLayers(), this.getModel());
    for (AbstractGUICell cell : this.getCellList()) {
      if (cell.equals(this.getSelectedCell())) {
        cell.setIsSelected(true);
        this.setSelectedCell(cell);
        repaint();
      }
    }
    this.revalidate();
    this.repaint();
  }

  /**
   * Creates the panel dimensions.
   *
   * @param layers is the number of layers in the game.
   * @return the dimensions of the panel.
   */
  protected Dimension createDimension(int layers) {
    int side = layers * CELL_SIZE;
    return new Dimension(side, side);
  }

  /**
   * Fires a cell clicked event.
   *
   * @param xCoordinate is the x coordinate of the cell clicked.
   * @param yCoordinate is the y coordinate of the cell clicked.
   */
  private void fireCellClicked(int xCoordinate, int yCoordinate, boolean selected) {
    for (IPanelEvent listener : this.listenerList) {
      listener.cellClicked(xCoordinate, yCoordinate, selected);
    }
  }

  /**
   * Draws the canvas.
   *
   * @param g is the graphics object.
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    for (AbstractGUICell cell : this.getCellList()) {
      cell.draw((Graphics2D) g);
    }
  }

  /**
   * Handles a mouse click event.
   *
   * @param e is the mouse event.
   */
  @Override
  public void mouseClicked(MouseEvent e) {
    Point pointClicked = e.getPoint();

    //deselects the selected cell
    if (this.getSelectedCell() != null) {
      this.getSelectedCell().setIsSelected(false);
      fireCellClicked(this.getSelectedCell().getXCoordinate(),
              this.getSelectedCell().getYCoordinate(), false);
      repaint();
    }

    for (AbstractGUICell cell : this.getCellList()) {
      if (cell.containsPoint(pointClicked)) {
        if (cell.equals(this.getSelectedCell())) {
          cell.setIsSelected(false);
          this.setSelectedCell(null);
          fireCellClicked(cell.getXCoordinate(), cell.getYCoordinate(), false);
        } else {
          this.setSelectedCell(cell);
          cell.setIsSelected(true);
          fireCellClicked(cell.getXCoordinate(), cell.getYCoordinate(), true);
        }
        repaint();
      }
    }
  }

  /**
   * Handles a mouse press event.
   *
   * @param e is the mouse event.
   */
  @Override
  public void mousePressed(MouseEvent e) {
    // no need to track this event
  }

  /**
   * Handles a mouse release event.
   *
   * @param e is the mouse event.
   */
  @Override
  public void mouseReleased(MouseEvent e) {
    // no need to track this event
  }

  /**
   * Handles a mouse enter event.
   *
   * @param e is the mouse event.
   */
  @Override
  public void mouseEntered(MouseEvent e) {
    // no need to track this event
  }

  /**
   * Handles a mouse exit event.
   *
   * @param e is the mouse event.
   */
  @Override
  public void mouseExited(MouseEvent e) {
    // no need to track this event
  }

  /**
   * Updates the canvas.
   *
   * @param deselect is whether to deselect the cell.
   */
  @Override
  public void updateCanvas(boolean deselect) {
    if (this.getSelectedCell() != null) {
      this.getSelectedCell().setIsSelected(false);
      this.setSelectedCell(null);
    }
    if (!deselect) {
      this.clearCellList();
      this.drawGrid(this.getLayers(), this.getModel());
    }
    this.revalidate();
    this.repaint();
  }

}
