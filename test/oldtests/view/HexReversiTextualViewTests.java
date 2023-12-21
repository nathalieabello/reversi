package oldtests.view;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import model.board.Coordinate;
import model.board.IModel;
import model.board.HexReversiModel;
import model.cell.DiscColor;
import view.ITextualView;
import view.HexReversiTextualView;

/**
 * Tests our reversi textual view.
 */
public class HexReversiTextualViewTests {
  private ITextualView view;
  private IModel model;

  @Before
  public void init() {
    this.model = new HexReversiModel.HexBuilder()
        .setLayers(3)
        .build();
    this.model.startGame();
    this.view = new HexReversiTextualView(this.model);
  }

  @Test
  public void testStartingBoardViewWith1Layer() {
    this.model = new HexReversiModel.HexBuilder()
        .setLayers(1)
        .build();
    this.model.startGame();
    this.view = new HexReversiTextualView(this.model);

    Assert.assertEquals(this.view.toString(),
        " O X\n" +
            "X _ O\n" +
            " O X\n");
  }

  @Test
  public void testStartingBoardViewWith5Layers() {
    this.model = new HexReversiModel.HexBuilder()
        .setLayers(5)
        .build();
    this.model.startGame();
    this.view = new HexReversiTextualView(this.model);

    Assert.assertEquals(this.view.toString(),
        "     _ _ _ _ _ _\n" +
            "    _ _ _ _ _ _ _\n" +
            "   _ _ _ _ _ _ _ _\n" +
            "  _ _ _ _ _ _ _ _ _\n" +
            " _ _ _ _ O X _ _ _ _\n" +
            "_ _ _ _ X _ O _ _ _ _\n" +
            " _ _ _ _ O X _ _ _ _\n" +
            "  _ _ _ _ _ _ _ _ _\n" +
            "   _ _ _ _ _ _ _ _\n" +
            "    _ _ _ _ _ _ _\n" +
            "     _ _ _ _ _ _\n");
  }

  @Test
  public void testStartingBoardViewWith1OLayers() {
    this.model = new HexReversiModel.HexBuilder()
        .setLayers(10)
        .build();
    this.model.startGame();
    this.view = new HexReversiTextualView(this.model);

    Assert.assertEquals(this.view.toString(),
        "          _ _ _ _ _ _ _ _ _ _ _\n" +
            "         _ _ _ _ _ _ _ _ _ _ _ _\n" +
            "        _ _ _ _ _ _ _ _ _ _ _ _ _\n" +
            "       _ _ _ _ _ _ _ _ _ _ _ _ _ _\n" +
            "      _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\n" +
            "     _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\n" +
            "    _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\n" +
            "   _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\n" +
            "  _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\n" +
            " _ _ _ _ _ _ _ _ _ O X _ _ _ _ _ _ _ _ _\n" +
            "_ _ _ _ _ _ _ _ _ X _ O _ _ _ _ _ _ _ _ _\n" +
            " _ _ _ _ _ _ _ _ _ O X _ _ _ _ _ _ _ _ _\n" +
            "  _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\n" +
            "   _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\n" +
            "    _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\n" +
            "     _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\n" +
            "      _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\n" +
            "       _ _ _ _ _ _ _ _ _ _ _ _ _ _\n" +
            "        _ _ _ _ _ _ _ _ _ _ _ _ _\n" +
            "         _ _ _ _ _ _ _ _ _ _ _ _\n" +
            "          _ _ _ _ _ _ _ _ _ _ _\n");
  }

  @Test
  public void testPlayDiscAllMoves() {
    List<DiscColor> listOfPlayerColors = new ArrayList<>();
    listOfPlayerColors.add(DiscColor.BLACK); //first player
    listOfPlayerColors.add(DiscColor.WHITE); //second player

    this.model = new HexReversiModel.HexBuilder()
        .setLayers(3)
        .setPlayerColors(listOfPlayerColors)
        .build();
    this.model.startGame();
    this.view = new HexReversiTextualView(this.model);

    Assert.assertEquals(this.view.toString(),
        "   _ _ _ _\n" +
            "  _ _ _ _ _\n" +
            " _ _ O X _ _\n" +
            "_ _ X _ O _ _\n" +
            " _ _ O X _ _\n" +
            "  _ _ _ _ _\n" +
            "   _ _ _ _\n");

    //sandwiching one line for player 1.
    Assert.assertEquals(this.model.getTurn(), DiscColor.BLACK);
    this.model.playDisc(new Coordinate(-1, 2));
    Assert.assertEquals(this.view.toString(),
        "   _ _ _ _\n" +
            "  _ _ _ _ _\n" +
            " _ _ O X X _\n" +
            "_ _ X _ X _ _\n" +
            " _ _ O X _ _\n" +
            "  _ _ _ _ _\n" +
            "   _ _ _ _\n");

    //sandwiching one line for player 2.
    Assert.assertEquals(this.model.getTurn(), DiscColor.WHITE);
    this.model.playDisc(new Coordinate(1, -2));
    Assert.assertEquals(this.view.toString(),
        "   _ _ _ _\n" +
            "  _ _ _ _ _\n" +
            " _ _ O X X _\n" +
            "_ _ O _ X _ _\n" +
            " _ O O X _ _\n" +
            "  _ _ _ _ _\n" +
            "   _ _ _ _\n");

    //sandwiching two lines for player 1.
    Assert.assertEquals(this.model.getTurn(), DiscColor.BLACK);
    this.model.playDisc(new Coordinate(1, -3));
    Assert.assertEquals(this.view.toString(),
        "   _ _ _ _\n" +
            "  _ _ _ _ _\n" +
            " _ _ O X X _\n" +
            "_ _ O _ X _ _\n" +
            " X X X X _ _\n" +
            "  _ _ _ _ _\n" +
            "   _ _ _ _\n");

    //overriding sandwich from player 1 to player 2.
    Assert.assertEquals(this.model.getTurn(), DiscColor.WHITE);
    this.model.playDisc(new Coordinate(2, -3));
    Assert.assertEquals(this.view.toString(),
        "   _ _ _ _\n" +
            "  _ _ _ _ _\n" +
            " _ _ O X X _\n" +
            "_ _ O _ X _ _\n" +
            " X O X X _ _\n" +
            "  O _ _ _ _\n" +
            "   _ _ _ _\n");

    //normal move for player 1.
    Assert.assertEquals(this.model.getTurn(), DiscColor.BLACK);
    this.model.playDisc(new Coordinate(2, -2));
    Assert.assertEquals(this.view.toString(),
        "   _ _ _ _\n" +
            "  _ _ _ _ _\n" +
            " _ _ O X X _\n" +
            "_ _ O _ X _ _\n" +
            " X O X X _ _\n" +
            "  O X _ _ _\n" +
            "   _ _ _ _\n");

    //normal move for player 2.
    Assert.assertEquals(this.model.getTurn(), DiscColor.WHITE);
    this.model.playDisc(new Coordinate(0, -2));
    Assert.assertEquals(this.view.toString(),
        "   _ _ _ _\n" +
            "  _ _ _ _ _\n" +
            " _ _ O X X _\n" +
            "_ O O _ X _ _\n" +
            " X O X X _ _\n" +
            "  O X _ _ _\n" +
            "   _ _ _ _\n");
  }
}
