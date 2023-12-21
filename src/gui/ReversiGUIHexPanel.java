package gui;

import java.awt.Color;

import model.board.Coordinate;
import model.board.ReadOnlyModel;

import static gui.ReversiGUIView.CELL_SIZE;

/**
 * This class represents the panel on which the game is drawn.
 */
public class ReversiGUIHexPanel extends AbstractGUIPanel {

  /**
   * Constructs a ReversiPanel.
   *
   * @param model is the model of the Read-Only version of the game.
   */
  public ReversiGUIHexPanel(ReadOnlyModel model) {
    super(model);
  }

  @Override
  public int getSides() {
    return 6;
  }

  /**
   * Draws the grid of hexagon cells.
   *
   * @param layers is the number of layers in the game.
   * @param model  is the ReadOnly version model of the game.
   */
  protected void drawGrid(int layers, ReadOnlyModel model) {
    int xCanvasCoordinate;
    int yCanvasCoordinate;
    double leftAlign = layers * CELL_SIZE * 0.8;
    double topAlign = layers * CELL_SIZE * 0.4;
    double lastX = leftAlign;
    double lastY = topAlign;

    int lowerBoundCol = 0;
    int upperBoundCol = layers;
    for (int row = -layers; row <= layers; row++) {
      for (int col = lowerBoundCol; col <= upperBoundCol; col++) {
        Color color = model.getCellAt(new Coordinate(row, col)).getColor().getDiscColorValue();
        xCanvasCoordinate = (int) (lastX + (Math.sqrt(3.0) * CELL_SIZE * 0.5));
        yCanvasCoordinate = (int) lastY;
        lastX = xCanvasCoordinate;
        GUIHexCell cell =
                new GUIHexCell(color, false,
                        row, col,
                        xCanvasCoordinate,
                        yCanvasCoordinate,
                    CELL_SIZE);
        this.cellList.add(cell);
      }

      if (row < 0) {
        lowerBoundCol--;
        leftAlign -= Math.sqrt(3.0) * CELL_SIZE * 0.25;
      } else {
        upperBoundCol--;
        leftAlign += Math.sqrt(3.0) * CELL_SIZE * 0.25;
      }
      lastX = leftAlign;
      lastY = lastY + CELL_SIZE * 0.75;
    }
  }
}
