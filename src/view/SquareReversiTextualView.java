package view;

import java.io.IOException;

import model.board.Coordinate;
import model.board.IModel;
import model.board.ReadOnlyModel;

/**
 * A textual view of the Square Reversi game. Renders the current state of the model.
 * The board is represented as a square.
 */
public class SquareReversiTextualView extends HexReversiTextualView implements ITextualView {

  /**
   * Class constructor for a textual view of the Reversi game,
   * which initializes a model and an appendable to append board the drawing to.
   *
   * @param model is the model of the game to be displayed.
   * @throws IllegalArgumentException if the model is null.
   */
  public SquareReversiTextualView(ReadOnlyModel model) {
    super(model);
  }

  /**
   * Class constructor for a textual view of the Reversi game,
   * which initializes a model and an appendable to append the board drawing to.
   *
   * @param model      is the model of the game to be displayed.
   * @param appendable is the appendable.
   * @throws IllegalArgumentException if the model is null.
   */
  public SquareReversiTextualView(IModel model, Appendable appendable) {
    super(model, appendable);
  }

  /**
   * Renders a model in some manner (e.g. as text, or as graphics, etc.).
   *
   * @throws IOException if the rendering fails for some reason.
   */
  @Override
  public void render() throws IOException {
    try {
      this.appendable.append(this.toString());
    } catch (IOException e) {
      throw new IOException("Appendable failed to append.");
    }
  }

  /**
   * Converts the current game into a visual text-based rendering board game.
   *
   * @return the game in text mode.
   */
  @Override
  public String toString() {
    StringBuilder textGame = new StringBuilder();
    int layers = this.model.getNumLayers();
    for (int row = -layers; row <= layers; row++) {
      for (int col = -layers; col <= layers; col++) {
        if (col != 0 && row != 0) {
          textGame.append(this.model.getCellAt(new Coordinate(row, col)).toString());
          this.addWhitespace(textGame, 1); //add whitespace between each cell.
        }
      }
      textGame.deleteCharAt(textGame.length() - 1); //delete last whitespace.
      textGame.append("\n");
    }

    return textGame.toString();
  }
}
