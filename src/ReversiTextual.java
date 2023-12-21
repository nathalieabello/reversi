import model.board.Coordinate;
import model.board.IModel;
import model.board.HexReversiModel;
import view.HexReversiTextualView;

/**
 * The main class for the Reversi game.
 */
public class ReversiTextual {

  /**
   * The main method for the Reversi game textual view.
   *
   * @param args the command line arguments.
   */
  public static void main(String[] args) {
    System.out.println("STARTING BOARD VIEW TESTS");
    IModel model1Layer = new HexReversiModel.HexBuilder().setLayers(1).build();
    System.out.println(new HexReversiTextualView(model1Layer));
    IModel model5Layers = new HexReversiModel.HexBuilder().setLayers(5).build();
    System.out.println(new HexReversiTextualView(model5Layers));
    IModel model10Layers = new HexReversiModel.HexBuilder().setLayers(10).build();
    System.out.println(new HexReversiTextualView(model10Layers));

    System.out.println("BOARD VIEW AFTER MOVES TESTS");
    IModel model3Layers = new HexReversiModel.HexBuilder().setLayers(3).build();
    System.out.println(new HexReversiTextualView(model3Layers));
    model3Layers.playDisc(new Coordinate(-1, 2));
    System.out.println(new HexReversiTextualView(model3Layers));
    model3Layers.playDisc(new Coordinate(1, -2));
    System.out.println(new HexReversiTextualView(model3Layers));
    model3Layers.playDisc(new Coordinate(1, -3));
    System.out.println(new HexReversiTextualView(model3Layers));
    model3Layers.playDisc(new Coordinate(2, -3));
    System.out.println(new HexReversiTextualView(model3Layers));
    model3Layers.playDisc(new Coordinate(2, -2));
    System.out.println(new HexReversiTextualView(model3Layers));
  }
}