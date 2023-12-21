package view;

import java.io.IOException;

import model.board.Coordinate;
import model.board.IModel;
import model.board.ReadOnlyModel;

/**
 * A textual view of the Hex Reversi game. Renders the current state of the model.
 * The board is represented as a hexagon.
 */
public class HexReversiTextualView implements ITextualView {
  protected final ReadOnlyModel model;
  protected final Appendable appendable;

  /**
   * Class constructor for a textual view of the Reversi game,
   * which initializes a model and an appendable to append board the drawing to.
   *
   * @param model is the model of the game to be displayed.
   * @throws IllegalArgumentException if the model is null.
   */
  public HexReversiTextualView(ReadOnlyModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.model = model;
    this.appendable = new StringBuilder();
  }

  /**
   * Class constructor for a textual view of the Reversi game,
   * which initializes a model and an appendable to append the board drawing to.
   *
   * @param model      is the model of the game to be displayed.
   * @param appendable is the appendable.
   * @throws IllegalArgumentException if the model is null.
   */
  public HexReversiTextualView(IModel model, Appendable appendable) {
    if (model == null || appendable == null) {
      throw new IllegalArgumentException("Model or appendable cannot be null.");
    }
    this.model = model;
    this.appendable = appendable;
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
    int lowerBoundCol = 0;
    int upperBoundCol = layers;
    for (int row = -layers; row <= layers; row++) {
      this.addWhitespace(textGame, Math.abs(row)); //add whitespace before each row.
      for (int col = lowerBoundCol; col <= upperBoundCol; col++) {
        textGame.append(this.model.getCellAt(new Coordinate(row, col)).toString());
        this.addWhitespace(textGame, 1); //add whitespace between each cell.
      }
      textGame.deleteCharAt(textGame.length() - 1); //delete last whitespace.
      textGame.append("\n");

      //change bounds for next row.
      //if row is negative, then we are in the top half of the board.
      //if row is positive, then we are in the bottom half of the board.
      if (row < 0) {
        lowerBoundCol--;
      } else {
        upperBoundCol--;
      }
    }

    return textGame.toString();
  }

  /**
   * Adds whitespace to the given string builder the given number of times.
   *
   * @param textGame       is the StringBuilder to add whitespace to.
   * @param numWhitespaces is the number of whitespaces to add.
   */
  protected void addWhitespace(StringBuilder textGame, int numWhitespaces) {
    textGame.append(" ".repeat(Math.max(0, numWhitespaces)));
  }
}
