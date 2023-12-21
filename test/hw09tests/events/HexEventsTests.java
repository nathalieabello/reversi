package hw09tests.events;

import gui.GUIPanel;
import gui.ReversiGUIHexPanel;
import oldtests.EventTests;
import model.board.HexReversiModel;
import model.board.IModel;

/**
 * Provides the event tests with some expectations, a model, and a panel.
 */
public class HexEventsTests extends EventTests {
  private final IModel model = new HexReversiModel.HexBuilder().setLayers(3).build();
  private final GUIPanel panel = new ReversiGUIHexPanel(model);

  @Override
  protected GUIPanel getPanel() {
    return panel;
  }

  @Override
  protected IModel getModel() {
    return model;
  }

  @Override
  protected String getCellClickedTranscript() {
    return "Cell clicked: 0, 0.\n";
  }

  @Override
  protected String getExpectedTranscript() {
    return "Cell clicked: 0, 0.\n" +
            "M key pressed.\n" +
            "NOTIFY Controller that a PlayDisc move " +
            "has been made on the view by BLACK player.\n" +
            "NOTIFY HumanPlayer that the move has been made on the view.\n" +
            "NOTIFY Strategy that the move has been made on the view.\n" +
            "PLAY DISC move made by BLACK player.\n" +
            "NOTIFY Controller 0 that model has changed.\n";
  }
}
