package oldtests.strategy;

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
public class PrioritizeCornersHexTests extends AbstractHexStrategyTests {
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
        "is corner?: x=-2, y=2\n" +
            "is corner?: x=0, y=-2\n" +
            "is corner?: x=1, y=-2\n" +
            "is corner?: x=1, y=1\n" +
            "is corner?: x=-2, y=1\n" +
            "is corner?: x=-1, y=-1\n" +
            "is corner?: x=2, y=-1\n" +
            "is corner?: x=2, y=0\n" +
            "is corner?: x=0, y=0\n" +
            "is corner?: x=-1, y=2"));
    Assert.assertTrue(possibleMoves.isEmpty());
  }

  @Test
  public void testGetStrategyPossibleMovesSameAsStartAllCandidateMovesAreCorners() {
    this.actionableModel.playDisc(new Coordinate(0, 0));
    this.actionableModel.playDisc(new Coordinate(2, -1));
    this.actionableModel.playDisc(new Coordinate(-2, 1));
    this.actionableModel.playDisc(new Coordinate(1, -2));
    this.actionableModel.playDisc(new Coordinate(1, 1));
    this.actionableModel.playDisc(new Coordinate(-1, -1));
    this.actionableModel.playDisc(new Coordinate(-1, 2));
    this.actionableModel.playDisc(new Coordinate(-2, 2));
    this.actionableModel.playDisc(new Coordinate(0, 2));
    this.actionableModel.playDisc(new Coordinate(0, -2));
    this.actionableModel.playDisc(new Coordinate(-2, 0));
    this.actionableModel.playDisc(new Coordinate(2, -2));
    this.actionableModel.playDisc(new Coordinate(2, 0));
    this.actionableModel.playDisc(new Coordinate(3, -1));
    this.actionableModel.playDisc(new Coordinate(3, -2));
    this.actionableModel.playDisc(new Coordinate(-3, 2));
    this.actionableModel.playDisc(new Coordinate(-3, 1));
    this.actionableModel.playDisc(new Coordinate(-2, -1));
    this.actionableModel.playDisc(new Coordinate(-1, -2));
    this.actionableModel.playDisc(new Coordinate(-2, 3));
    this.actionableModel.playDisc(new Coordinate(-1, 3));
    this.actionableModel.playDisc(new Coordinate(2, -3));
    this.actionableModel.playDisc(new Coordinate(1, -3));
    this.actionableModel.playDisc(new Coordinate(2, 1));
    this.actionableModel.playDisc(new Coordinate(1, 2));
    List<Coordinate> allMoves = this.strategy.getAllPossiblePlayerMoves(this.player);
    List<Coordinate> stratMoves = this.strategy.getStrategyPossibleMoves(allMoves, this.player);
    Assert.assertEquals(allMoves, stratMoves);
  }

  @Test
  public void testGetStrategyPossibleMovesOneCandidateMoveIsCorner() {
    this.actionableModel.playDisc(new Coordinate(0, 0));
    this.actionableModel.playDisc(new Coordinate(2, -1));
    this.actionableModel.playDisc(new Coordinate(-2, 1));
    this.actionableModel.playDisc(new Coordinate(1, -2));
    this.actionableModel.playDisc(new Coordinate(1, 1));
    this.actionableModel.playDisc(new Coordinate(-1, -1));
    this.actionableModel.playDisc(new Coordinate(-1, 2));
    this.actionableModel.playDisc(new Coordinate(-2, 2));
    this.actionableModel.playDisc(new Coordinate(0, 2));
    this.actionableModel.playDisc(new Coordinate(0, -2));
    this.actionableModel.playDisc(new Coordinate(-2, 0));
    this.actionableModel.playDisc(new Coordinate(2, -2));
    this.actionableModel.playDisc(new Coordinate(2, 0));
    this.actionableModel.playDisc(new Coordinate(3, -1));
    // 5 corners below
    this.actionableModel.playDisc(new Coordinate(-3, 0));
    this.actionableModel.playDisc(new Coordinate(3, -3));
    this.actionableModel.playDisc(new Coordinate(0, 3));
    this.actionableModel.playDisc(new Coordinate(0, -3));
    this.actionableModel.playDisc(new Coordinate(-3, 3));
    List<Coordinate> allMoves = this.strategy.getAllPossiblePlayerMoves(this.player);
    List<Coordinate> stratMoves = this.strategy.getStrategyPossibleMoves(allMoves, this.player);
    Assert.assertEquals(List.of(new Coordinate(3, 0)), stratMoves);
  }

  @Test
  public void testGetStrategyPossibleMovesMoreThanOneCandidateMoveIsCorner() {
    this.actionableModel.playDisc(new Coordinate(0, 0));
    this.actionableModel.playDisc(new Coordinate(2, -1));
    this.actionableModel.playDisc(new Coordinate(-2, 1));
    this.actionableModel.playDisc(new Coordinate(1, -2));
    this.actionableModel.playDisc(new Coordinate(1, 1));
    this.actionableModel.playDisc(new Coordinate(-1, -1));
    this.actionableModel.playDisc(new Coordinate(-1, 2));
    this.actionableModel.playDisc(new Coordinate(-2, 2));
    this.actionableModel.playDisc(new Coordinate(0, 2));
    this.actionableModel.playDisc(new Coordinate(0, -2));
    this.actionableModel.playDisc(new Coordinate(-2, 0));
    this.actionableModel.playDisc(new Coordinate(2, -2));
    this.actionableModel.playDisc(new Coordinate(2, 0));
    this.actionableModel.playDisc(new Coordinate(3, -1));
    // 5 corners below
    this.actionableModel.playDisc(new Coordinate(-3, 0));
    this.actionableModel.playDisc(new Coordinate(3, -3));
    this.actionableModel.playDisc(new Coordinate(0, 3));
    this.actionableModel.playDisc(new Coordinate(0, -3));
    List<Coordinate> allMoves = this.strategy.getAllPossiblePlayerMoves(this.player);
    List<Coordinate> stratMoves = this.strategy.getStrategyPossibleMoves(allMoves, this.player);
    Assert.assertEquals(List.of(new Coordinate(-3, 3),
        new Coordinate(3, 0)), stratMoves);
  }

  @Test
  public void testGetStrategyPossibleMovesChecksIfAllCoordinateInListAreCorners() {
    List<Coordinate> allMoves = this.strategy.getAllPossiblePlayerMoves(this.player);
    allMoves.removeAll(allMoves.subList(0, allMoves.size() - 3));
    this.strategy.getStrategyPossibleMoves(allMoves, this.player);
    Assert.assertTrue(this.a.toString().contains("is corner?: x=2, y=0\n" +
        "is corner?: x=0, y=0\n" +
        "is corner?: x=-1, y=2"));
  }
}
