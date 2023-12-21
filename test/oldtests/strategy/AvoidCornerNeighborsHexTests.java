package oldtests.strategy;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import model.board.Coordinate;
import model.board.IModel;
import model.board.ReadOnlyModel;
import model.board.ReadOnlyReversiModel;
import model.board.HexReversiModel;
import model.cell.DiscColor;
import strategy.AbstractSingleStrategy;
import strategy.AvoidCornerNeighbors;
import view.HexReversiTextualView;

/**
 * Tests for the AvoidEdgeNeighbors strategy.
 */
public class AvoidCornerNeighborsHexTests extends AbstractHexStrategyTests {

  @Override
  protected AbstractSingleStrategy getStrategy(DiscColor player, ReadOnlyModel model) {
    return new AvoidCornerNeighbors(player, model);
  }

  /*
  getStrategyPossibleMoves() TESTS:
 */

  @Test
  public void testGetStrategyPossibleMovesEmptyAllCandidateMovesAreCornerNeighbors() {
    this.actionableModel.playDisc(new Coordinate(0, 0));
    this.actionableModel.pass();
    this.actionableModel.playDisc(new Coordinate(2, -1));
    this.actionableModel.pass();
    this.actionableModel.playDisc(new Coordinate(-2, 1));
    this.actionableModel.pass();
    this.actionableModel.playDisc(new Coordinate(1, -2));
    this.actionableModel.pass();
    this.actionableModel.playDisc(new Coordinate(1, 1));
    this.actionableModel.playDisc(new Coordinate(-1, -1));
    this.actionableModel.playDisc(new Coordinate(-1, 2));
    Assert.assertTrue(this.strategy
        .getStrategyPossibleMoves(this.strategy.getAllPossiblePlayerMoves(this.player),
            this.player).isEmpty());
  }

  @Test
  public void testGetStrategyPossibleMovesSameAsStartAllCandidateMovesAreNotCornerNeighbors() {
    IModel bigModel = new HexReversiModel.HexBuilder().setLayers(5).build();
    bigModel.startGame();
    ReadOnlyModel bigReadOnlyModel = new ReadOnlyReversiModel(bigModel);
    AbstractSingleStrategy bigStrat = new AvoidCornerNeighbors(this.player, bigReadOnlyModel);
    List<Coordinate> allMoves = bigStrat.getAllPossiblePlayerMoves(this.player);
    List<Coordinate> stratMoves = bigStrat.getStrategyPossibleMoves(allMoves, this.player);
    Assert.assertEquals(allMoves, stratMoves);
  }

  @Test
  public void testGetStrategyPossibleMovesSameAsStartSomeCandidateMovesAreNotCornerNeighbors() {
    List<Coordinate> allMoves = this.strategy.getAllPossiblePlayerMoves(this.player);
    List<Coordinate> stratMoves = this.strategy.getStrategyPossibleMoves(allMoves, this.player);
    allMoves.remove(new Coordinate(-2, 2));
    allMoves.remove(new Coordinate(0, -2));
    allMoves.remove(new Coordinate(2, 0));
    Assert.assertEquals(allMoves, stratMoves);
  }

  @Test
  public void testGetStrategyPossibleMovesOneCandidateMoveIsCornerNeighbor() {
    this.actionableModel.getCellAt(new Coordinate(0, 0)).changeColor(DiscColor.WHITE);
    List<Coordinate> allMoves = this.strategy.getAllPossiblePlayerMoves(this.player);
    List<Coordinate> stratMoves = this.strategy.getStrategyPossibleMoves(allMoves, this.player);
    Assert.assertFalse(stratMoves.contains(new Coordinate(0, 2)));
    Assert.assertTrue(allMoves.contains(new Coordinate(0, 2)));
    Assert.assertEquals(allMoves.size(), stratMoves.size() + 6);
  }

  @Test
  public void testGetStrategyPossibleMovesMoreThanOneCandidateMoveIsCornerNeighbor() {
    this.actionableModel.playDisc(new Coordinate(0, 0));
    this.actionableModel.pass();
    this.actionableModel.playDisc(new Coordinate(2, -1));
    this.actionableModel.pass();
    this.actionableModel.playDisc(new Coordinate(1, 1));
    this.actionableModel.playDisc(new Coordinate(-1, -1));
    this.actionableModel.playDisc(new Coordinate(-1, 2));
    List<Coordinate> allMoves = this.strategy.getAllPossiblePlayerMoves(this.player);
    List<Coordinate> stratMoves = this.strategy.getStrategyPossibleMoves(allMoves, this.player);
    Assert.assertEquals(List.of(new Coordinate(1, -2), new Coordinate(-2, 1)),
        stratMoves);
  }

  @Test
  public void testGetStrategyPossibleMovesCornerCandidateMoveDoesNotGetFiltered() {
    this.actionableModel.playDisc(new Coordinate(0, 0));
    this.actionableModel.pass();
    this.actionableModel.playDisc(new Coordinate(2, -1));
    this.actionableModel.pass();
    this.actionableModel.playDisc(new Coordinate(-2, 1));
    this.actionableModel.pass();
    this.actionableModel.playDisc(new Coordinate(1, -2));
    this.actionableModel.pass();
    this.actionableModel.playDisc(new Coordinate(1, 1));
    this.actionableModel.playDisc(new Coordinate(-1, -1));
    this.actionableModel.playDisc(new Coordinate(-1, 2));
    this.actionableModel.playDisc(new Coordinate(-2, 0));
    HexReversiTextualView view = new HexReversiTextualView(this.actionableModel);
    System.out.println(view);
    Assert.assertFalse(this.strategy
        .getStrategyPossibleMoves(this.strategy.getAllPossiblePlayerMoves(this.player),
            this.player).isEmpty());
    Assert.assertTrue(this.strategy
        .getStrategyPossibleMoves(this.strategy.getAllPossiblePlayerMoves(this.player),
            this.player).contains(new Coordinate(-3, 0)));
  }

  @Test
  public void testGetStrategyPossibleMovesChecksEveryNeighbor() {
    this.strategy.getStrategyPossibleMoves(this.strategy.getAllPossiblePlayerMoves(this.player),
        this.player);
    System.out.println(a);
    Assert.assertTrue(this.a.toString().contains("get cell at: x=-1, y=-2\n" +
        "get like neighbors of X at: x=-1, y=-2\n" +
        "get sandwichable neighbors of X at: x=-1, y=-2\n"));
    Assert.assertTrue(this.a.toString().contains("get cell at: x=-2, y=2\n" +
        "get like neighbors of X at: x=-2, y=2"));
    Assert.assertTrue(this.a.toString().contains("get cell at: x=-3, y=2\n" +
        "get like neighbors of X at: x=-3, y=2\n" +
        "get sandwichable neighbors of X at: x=-3, y=2"));
    Assert.assertTrue(this.a.toString()
        .contains("get cell at: x=1, y=-2\n" +
            "get like neighbors of X at: x=1, y=-2"));
    Assert.assertTrue(this.a.toString().contains("get cell at: x=2, y=-2\n" +
        "get like neighbors of X at: x=2, y=-2\n" +
        "get sandwichable neighbors of X at: x=2, y=-2"));
    Assert.assertTrue(this.a.toString().contains("get cell at: x=3, y=-2\n" +
        "get like neighbors of X at: x=3, y=-2\n" +
        "get sandwichable neighbors of X at: x=3, y=-2"));
    Assert.assertTrue(this.a.toString().contains("get cell at: x=2, y=1\n" +
        "get like neighbors of X at: x=2, y=1\n" +
        "get sandwichable neighbors of X at: x=2, y=1\n"));
    Assert.assertTrue(this.a.toString().contains("get cell at: x=1, y=1\n" +
        "get like neighbors of X at: x=1, y=1"));
    Assert.assertTrue(this.a.toString().contains("get cell at: x=-1, y=3\n" +
        "get like neighbors of X at: x=-1, y=3\n" +
        "get sandwichable neighbors of X at: x=-1, y=3"));
    Assert.assertTrue(this.a.toString().contains("get cell at: x=0, y=1"));
    Assert.assertTrue(this.a.toString().contains("get cell at: x=0, y=3\n" +
        "get like neighbors of X at: x=0, y=3\n" +
        "get sandwichable neighbors of X at: x=0, y=3"));
    Assert.assertTrue(this.a.toString().contains("get cell at: x=-1, y=1"));
    Assert.assertTrue(this.a.toString().contains("get cell at: x=-2, y=-1\n" +
        "get like neighbors of X at: x=-2, y=-1\n" +
        "get sandwichable neighbors of X at: x=-2, y=-1"));
    Assert.assertTrue(this.a.toString().contains("get cell at: x=-3, y=3\n" +
        "get like neighbors of X at: x=-3, y=3\n" +
        "get sandwichable neighbors of X at: x=-3, y=3"));
    Assert.assertTrue(this.a.toString().contains("get cell at: x=-2, y=1\n" +
        "get like neighbors of X at: x=-2, y=1"));
    Assert.assertTrue(this.a.toString().contains("get cell at: x=-1, y=-1\n" +
        "get like neighbors of X at: x=-1, y=-1"));
    Assert.assertTrue(this.a.toString().contains("get cell at: x=0, y=-3\n" +
        "get like neighbors of X at: x=0, y=-3\n" +
        "get sandwichable neighbors of X at: x=0, y=-3"));
    Assert.assertTrue(this.a.toString().contains("get cell at: x=-2, y=3\n" +
        "get like neighbors of X at: x=-2, y=3\n" +
        "get sandwichable neighbors of X at: x=-2, y=3"));
    Assert.assertTrue(this.a.toString().contains("get cell at: x=-3, y=1\n" +
        "get like neighbors of X at: x=-3, y=1\n" +
        "get sandwichable neighbors of X at: x=-3, y=1"));
    Assert.assertTrue(this.a.toString().contains("get cell at: x=0, y=-1"));
    Assert.assertTrue(this.a.toString().contains("get cell at: x=1, y=-1"));
    Assert.assertTrue(this.a.toString().contains("get cell at: x=2, y=-3\n" +
        "get like neighbors of X at: x=2, y=-3\n" +
        "get sandwichable neighbors of X at: x=2, y=-3"));
    Assert.assertTrue(this.a.toString().contains("is corner?: x=-3, y=2\n" +
            "is corner?: x=-1, y=2\n" +
            "is corner?: x=-2, y=1\n" +
            "is corner?: x=-2, y=3\n" +
            "is corner?: x=-1, y=1\n" +
            "is corner?: x=-3, y=3\n" +
            "get cell at: x=-3, y=3\n" +
            "is corner?: x=-2, y=2\n" +
            "get neighbors at: x=0, y=-2\n" +
            "is corner?: x=-1, y=-2\n" +
            "is corner?: x=1, y=-2\n" +
            "is corner?: x=0, y=-3\n" +
            "get cell at: x=0, y=-3\n" +
            "is corner?: x=0, y=-2\n" +
            "get neighbors at: x=1, y=-2\n" +
            "is corner?: x=0, y=-2\n" +
            "is corner?: x=2, y=-2\n" +
            "is corner?: x=1, y=-3\n" +
            "is corner?: x=1, y=-1\n" +
            "is corner?: x=2, y=-3\n" +
            "is corner?: x=0, y=-1\n" +
            "get neighbors at: x=1, y=1\n" +
            "is corner?: x=0, y=1\n" +
            "is corner?: x=2, y=1\n" +
            "is corner?: x=1, y=0\n" +
            "is corner?: x=1, y=2\n" +
            "is corner?: x=2, y=0\n" +
            "is corner?: x=0, y=2\n" +
            "get neighbors at: x=-2, y=1\n" +
            "is corner?: x=-3, y=1\n" +
            "is corner?: x=-1, y=1\n" +
            "is corner?: x=-2, y=0\n" +
            "is corner?: x=-2, y=2\n" +
            "is corner?: x=-1, y=0\n" +
            "is corner?: x=-3, y=2\n" +
            "get neighbors at: x=-1, y=-1\n" +
            "is corner?: x=-2, y=-1\n" +
            "is corner?: x=0, y=-1\n" +
            "is corner?: x=-1, y=-2\n" +
            "is corner?: x=-1, y=0\n" +
            "is corner?: x=0, y=-2\n" +
            "is corner?: x=-2, y=0\n" +
            "get neighbors at: x=2, y=-1\n" +
            "is corner?: x=1, y=-1\n" +
            "is corner?: x=3, y=-1\n" +
            "is corner?: x=2, y=-2\n" +
            "is corner?: x=2, y=0\n" +
            "is corner?: x=3, y=-2\n" +
            "is corner?: x=1, y=0\n" +
            "get neighbors at: x=2, y=0\n" +
            "is corner?: x=1, y=0\n" +
            "is corner?: x=3, y=0\n" +
            "get cell at: x=3, y=0\n" +
            "is corner?: x=2, y=0\n" +
            "get neighbors at: x=0, y=0\n" +
            "is corner?: x=-1, y=0\n" +
            "is corner?: x=1, y=0\n" +
            "is corner?: x=0, y=-1\n" +
            "is corner?: x=0, y=1\n" +
            "is corner?: x=1, y=-1\n" +
            "is corner?: x=-1, y=1\n" +
            "get neighbors at: x=-1, y=2\n" +
            "is corner?: x=-2, y=2\n" +
            "is corner?: x=0, y=2\n" +
            "is corner?: x=-1, y=1\n" +
            "is corner?: x=-1, y=3\n" +
            "is corner?: x=0, y=1\n" +
            "is corner?: x=-2, y=3"));
  }
}
