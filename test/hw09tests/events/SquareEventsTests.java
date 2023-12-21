package hw09tests.events;

import gui.GUIPanel;
import gui.ReversiGUISquarePanel;
import oldtests.EventTests;
import model.board.IModel;
import model.board.SquareReversiModel;

/**
 * Provides the event tests with some expectations, a model, and a panel.
 */
public class SquareEventsTests extends EventTests {
  private final IModel model = new SquareReversiModel.SquareBuilder().setLayers(3).build();

  @Override
  protected GUIPanel getPanel() {
    return new ReversiGUISquarePanel(model);
  }

  @Override
  protected IModel getModel() {
    return model;
  }

  @Override
  protected String getCellClickedTranscript() {
    return "Cell clicked: -1, -1.\n";
  }

  @Override
  protected String getExpectedTranscript() {
    return "Cell clicked: -1, -1.\n" +
            "M key pressed.\n" +
            "NOTIFY Controller that a PlayDisc move " +
            "has been made on the view by BLACK player.\n" +
            "NOTIFY HumanPlayer that the move has been made on the view.\n" +
            "NOTIFY Strategy that the move has been made on the view.\n" +
            "SHOW Invalid move for BLACK player.\n";
  }
}
