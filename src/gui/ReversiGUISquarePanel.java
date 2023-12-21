package gui;

import java.awt.Color;

import model.board.Coordinate;
import model.board.ReadOnlyModel;

import static gui.ReversiGUIView.CELL_SIZE;

/**
 * The square variation of Reversi panel.
 */
public class ReversiGUISquarePanel extends AbstractGUIPanel {

  /**
   * Constructs a square ReversiPanel.
   *
   * @param model is the model of the Read-Only version of the game.
   */
  public ReversiGUISquarePanel(ReadOnlyModel model) {
    super(model);
  }

  @Override
  public int getSides() {
    return 4;
  }

  @Override
  protected void drawGrid(int layers, ReadOnlyModel model) {
    int xCanvasCoordinate = (int) (layers * CELL_SIZE * 0.4);
    int yCanvasCoordinate = (int) (layers * CELL_SIZE * 0.4);
    for (int row = -layers; row <= layers; row++) {
      if (row != 0) {
        for (int col = -layers; col <= layers; col++) {
          if (col != 0) {
            Color color = model.getCellAt(new Coordinate(row, col)).getColor().getDiscColorValue();
            xCanvasCoordinate += CELL_SIZE;
            GUISquareCell cell =
                    new GUISquareCell(color, false,
                            row, col,
                            xCanvasCoordinate,
                            yCanvasCoordinate,
                            CELL_SIZE);
            this.cellList.add(cell);
          }
        }
        xCanvasCoordinate = (int) (layers * CELL_SIZE * 0.4);
        yCanvasCoordinate += CELL_SIZE;
      }
    }
  }
}
