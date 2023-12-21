import gui.GUIPanel;
import gui.GUIView;

import gui.ReversiGUIHexPanel;
import gui.ReversiGUIView;
import model.board.IModel;
import model.board.ReadOnlyReversiModel;
import model.board.HexReversiModel;
import model.board.Coordinate;

/**
 * Main class for running the Reversi game GUI.
 */
public class Reversi {
  /**
   * Main method for running the Reversi game GUI.
   *
   * @param args is the command line arguments.
   */
  public static void main(String[] args) {
    IModel actionableModel = new HexReversiModel.HexBuilder().setLayers(3).build();
    actionableModel.startGame();
    /*
      Comment the following to enable a start of the game state.
     */
    actionableModel.playDisc(new Coordinate(-1, 2));
    actionableModel.playDisc(new Coordinate(1, -2));
    actionableModel.playDisc(new Coordinate(1, -3));
    actionableModel.playDisc(new Coordinate(2, -3));
    actionableModel.playDisc(new Coordinate(2, -2));

    ReadOnlyReversiModel model = new ReadOnlyReversiModel(actionableModel);
    GUIPanel panel = new ReversiGUIHexPanel(model);
    GUIView view = new ReversiGUIView(model, panel);
    view.setVisible(true);
  }
}