package hw09tests.strategies;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import model.board.Coordinate;
import model.board.ReadOnlyModel;
import model.cell.DiscColor;
import strategy.AbstractSingleStrategy;
import strategy.PrioritizeCorners;

/**
 * Tests for the PrioritizeCorners strategy.
 */
public class PrioritizeCornersSquareTests extends AbstractSquareStrategyTests {
  /**
   * Gets the strategy to be used for testing a PrioritizeCorners strategy.
   *
   * @param player which player is trying to make a move
   * @param model  which model to play on
   * @return the strategy to be used for testing.
   */
  @Override
  protected AbstractSingleStrategy getStrategy(DiscColor player, ReadOnlyModel model) {
    return new PrioritizeCorners(player, model);
  }

  /*
  getStrategyPossibleMoves() TESTS:
 */

  @Test
  public void testGetStrategyPossibleMovesEmptyAllCandidateMovesAreNotCorners() {
    List<Coordinate> possibleMoves = this.strategy
            .getStrategyPossibleMoves(this.strategy.getAllPossiblePlayerMoves(this.player),
                    this.player);
    Assert.assertTrue(this.a.toString().contains(
            "get like neighbors of X at: x=-1, y=2\n" +
                    "is corner?: x=-1, y=-2\n" +
                    "is corner?: x=1, y=-2\n" +
                    "is corner?: x=2, y=1\n" +
                    "is corner?: x=-2, y=-1\n" +
                    "is corner?: x=-2, y=1\n" +
                    "is corner?: x=2, y=-1\n" +
                    "is corner?: x=2, y=2\n" +
                    "is corner?: x=1, y=2\n" +
                    "is corner?: x=-2, y=-2\n" +
                    "is corner?: x=-1, y=2"));
    Assert.assertTrue(possibleMoves.isEmpty());
  }

  @Test
  public void testGetStrategyPossibleMovesSameAsStartAllCandidateMovesAreCorners() {
    for (Coordinate c : this.actionableModel.getCopyOfAllCoords().keySet()) {
      if (this.actionableModel.getCellAt(c).getColor() == DiscColor.NONE) {
        if (!this.actionableModel.isCorner(c)) {
          this.actionableModel.getCellAt(c).changeColor(DiscColor.BLACK);
        }
      }
    }
    List<Coordinate> allMoves = this.strategy.getAllPossiblePlayerMoves(this.player);
    List<Coordinate> stratMoves = this.strategy.getStrategyPossibleMoves(allMoves, this.player);
    Assert.assertEquals(allMoves, stratMoves);
  }

  @Test
  public void testGetStrategyPossibleMovesOneCandidateMoveIsCorner() {
    for (Coordinate c : this.actionableModel.getCopyOfAllCoords().keySet()) {
      if (this.actionableModel.getCellAt(c).getColor() == DiscColor.NONE) {
        if (!(c.getRow() == this.actionableModel.getNumLayers()
                && c.getCol() == this.actionableModel.getNumLayers())) {
          this.actionableModel.getCellAt(c).changeColor(DiscColor.BLACK);
        }
      }
    }
    List<Coordinate> allMoves = this.strategy.getAllPossiblePlayerMoves(this.player);
    List<Coordinate> stratMoves = this.strategy.getStrategyPossibleMoves(allMoves, this.player);
    Assert.assertEquals(List.of(new Coordinate(3, 3)), stratMoves);
  }

  @Test
  public void testGetStrategyPossibleMovesMoreThanOneCandidateMoveIsCorner() {
    for (Coordinate c : this.actionableModel.getCopyOfAllCoords().keySet()) {
      if (this.actionableModel.getCellAt(c).getColor() == DiscColor.NONE) {
        if (!(c.getRow() == this.actionableModel.getNumLayers()
                && (c.getCol() == this.actionableModel.getNumLayers()
                || c.getCol() == -this.actionableModel.getNumLayers()))) {
          this.actionableModel.getCellAt(c).changeColor(DiscColor.BLACK);
        }
      }
    }
    List<Coordinate> allMoves = this.strategy.getAllPossiblePlayerMoves(this.player);
    List<Coordinate> stratMoves = this.strategy.getStrategyPossibleMoves(allMoves, this.player);
    Assert.assertEquals(List.of(new Coordinate(3, 3),
            new Coordinate(3, -3)), stratMoves);
  }

  @Test
  public void testGetStrategyPossibleMovesChecksIfAllCoordinateInListAreCorners() {
    List<Coordinate> allMoves = this.strategy.getAllPossiblePlayerMoves(this.player);
    allMoves.removeAll(allMoves.subList(0, allMoves.size() - 3));
    this.strategy.getStrategyPossibleMoves(allMoves, this.player);
    Assert.assertTrue(this.a.toString().contains("get like neighbors of X at: x=-1, y=2\n" +
            "is corner?: x=1, y=2\n" +
            "is corner?: x=-2, y=-2\n" +
            "is corner?: x=-1, y=2"));
  }
}
