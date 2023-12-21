package hw09tests.strategies;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import model.board.Coordinate;
import model.board.HexReversiModel;
import model.board.IModel;
import model.board.ReadOnlyModel;
import model.board.ReadOnlyReversiModel;
import model.cell.DiscColor;
import strategy.AbstractSingleStrategy;
import strategy.AvoidCornerNeighbors;

/**
 * Tests for the AvoidEdgeNeighbors strategy.
 */
public class AvoidCornerNeighborsSquareTests extends AbstractSquareStrategyTests {

  @Override
  protected AbstractSingleStrategy getStrategy(DiscColor player, ReadOnlyModel model) {
    return new AvoidCornerNeighbors(player, model);
  }

  /*
  getStrategyPossibleMoves() TESTS:
 */

  @Test
  public void testGetStrategyPossibleMovesEmptyAllCandidateMovesAreCornerNeighbors() {
    for (Coordinate c : this.actionableModel.getCopyOfAllCoords().keySet()) {
      if (this.actionableModel.getCellAt(c).getColor() == DiscColor.NONE) {
        if (!c.equals(new Coordinate(2, 2))
                && !c.equals(new Coordinate(3, 3))) {
          this.actionableModel.getCellAt(c).changeColor(DiscColor.WHITE);
        }
      }
    }
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
    allMoves.remove(new Coordinate(2, 2));
    allMoves.remove(new Coordinate(-2, -2));
    Assert.assertEquals(allMoves, stratMoves);
  }

  @Test
  public void testGetStrategyPossibleMovesOneCandidateMoveIsCornerNeighbor() {
    List<Coordinate> allMoves = this.strategy.getAllPossiblePlayerMoves(this.player);
    List<Coordinate> stratMoves = this.strategy.getStrategyPossibleMoves(allMoves, this.player);
    Assert.assertEquals(allMoves.size(), stratMoves.size() + 2);
  }

  @Test
  public void testGetStrategyPossibleMovesMoreThanOneCandidateMoveIsCornerNeighbor() {
    for (Coordinate c : this.actionableModel.getCopyOfAllCoords().keySet()) {
      if (this.actionableModel.getCellAt(c).getColor() == DiscColor.NONE) {
        if (!c.equals(new Coordinate(2, 2))
                && !c.equals(new Coordinate(3, 3))) {
          this.actionableModel.getCellAt(c).changeColor(DiscColor.WHITE);
        }
      }
    }
    List<Coordinate> allMoves = this.strategy.getAllPossiblePlayerMoves(this.player);
    List<Coordinate> stratMoves = this.strategy.getStrategyPossibleMoves(allMoves, this.player);
    Assert.assertTrue(stratMoves.isEmpty());
  }

  @Test
  public void testGetStrategyPossibleMovesChecksEveryNeighbor() {
    this.strategy.getStrategyPossibleMoves(this.strategy.getAllPossiblePlayerMoves(this.player),
            this.player);
    Assert.assertTrue(this.a.toString().contains(
            "get neighbors at: x=-1, y=-2\n" +
            "is corner?: x=-2, y=-3\n" +
            "is corner?: x=-2, y=-2\n" +
            "is corner?: x=-2, y=-1\n" +
            "is corner?: x=-1, y=-3\n" +
            "is corner?: x=-1, y=-1\n" +
            "is corner?: x=1, y=-3\n" +
            "is corner?: x=1, y=-2\n" +
            "is corner?: x=1, y=-1\n" +
            "get neighbors at: x=1, y=-2\n" +
            "is corner?: x=-1, y=-3\n" +
            "is corner?: x=-1, y=-2\n" +
            "is corner?: x=-1, y=-1\n" +
            "is corner?: x=1, y=-3\n" +
            "is corner?: x=1, y=-1\n" +
            "is corner?: x=2, y=-3\n" +
            "is corner?: x=2, y=-2\n" +
            "is corner?: x=2, y=-1\n" +
            "get neighbors at: x=2, y=1\n" +
            "is corner?: x=1, y=-1\n" +
            "is corner?: x=1, y=1\n" +
            "is corner?: x=1, y=2\n" +
            "is corner?: x=2, y=-1\n" +
            "is corner?: x=2, y=2\n" +
            "is corner?: x=3, y=-1\n" +
            "is corner?: x=3, y=1\n" +
            "is corner?: x=3, y=2\n" +
            "get neighbors at: x=-2, y=-1\n" +
            "is corner?: x=-3, y=-2\n" +
            "is corner?: x=-3, y=-1\n" +
            "is corner?: x=-3, y=1\n" +
            "is corner?: x=-2, y=-2\n" +
            "is corner?: x=-2, y=1\n" +
            "is corner?: x=-1, y=-2\n" +
            "is corner?: x=-1, y=-1\n" +
            "is corner?: x=-1, y=1\n" +
            "get neighbors at: x=-2, y=1\n" +
            "is corner?: x=-3, y=-1\n" +
            "is corner?: x=-3, y=1\n" +
            "is corner?: x=-3, y=2\n" +
            "is corner?: x=-2, y=-1\n" +
            "is corner?: x=-2, y=2\n" +
            "is corner?: x=-1, y=-1\n" +
            "is corner?: x=-1, y=1\n" +
            "is corner?: x=-1, y=2\n" +
            "get neighbors at: x=2, y=-1\n" +
            "is corner?: x=1, y=-2\n" +
            "is corner?: x=1, y=-1\n" +
            "is corner?: x=1, y=1\n" +
            "is corner?: x=2, y=-2\n" +
            "is corner?: x=2, y=1\n" +
            "is corner?: x=3, y=-2\n" +
            "is corner?: x=3, y=-1\n" +
            "is corner?: x=3, y=1\n" +
            "get neighbors at: x=2, y=2\n" +
            "is corner?: x=1, y=1\n" +
            "is corner?: x=1, y=2\n" +
            "is corner?: x=1, y=3\n" +
            "is corner?: x=2, y=1\n" +
            "is corner?: x=2, y=3\n" +
            "is corner?: x=3, y=1\n" +
            "is corner?: x=3, y=2\n" +
            "is corner?: x=3, y=3\n" +
            "get cell at: x=3, y=3\n" +
            "is corner?: x=2, y=2\n" +
            "get neighbors at: x=1, y=2\n" +
            "is corner?: x=-1, y=1\n" +
            "is corner?: x=-1, y=2\n" +
            "is corner?: x=-1, y=3\n" +
            "is corner?: x=1, y=1\n" +
            "is corner?: x=1, y=3\n" +
            "is corner?: x=2, y=1\n" +
            "is corner?: x=2, y=2\n" +
            "is corner?: x=2, y=3\n" +
            "get neighbors at: x=-2, y=-2\n" +
            "is corner?: x=-3, y=-3\n" +
            "get cell at: x=-3, y=-3\n" +
            "is corner?: x=-2, y=-2\n" +
            "get neighbors at: x=-1, y=2\n" +
            "is corner?: x=-2, y=1\n" +
            "is corner?: x=-2, y=2\n" +
            "is corner?: x=-2, y=3\n" +
            "is corner?: x=-1, y=1\n" +
            "is corner?: x=-1, y=3\n" +
            "is corner?: x=1, y=1\n" +
            "is corner?: x=1, y=2\n" +
            "is corner?: x=1, y=3"));
  }
}
