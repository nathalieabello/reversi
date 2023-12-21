package oldtests.strategy;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import model.board.Coordinate;
import model.board.ReadOnlyModel;
import model.cell.DiscColor;
import model.cell.ICell;
import strategy.AIStrategy;
import strategy.AbstractCompositeStrategy;
import strategy.AbstractSingleStrategy;
import strategy.AvoidCornerNeighbors;
import strategy.CaptureMostCells;
import strategy.PrioritizeCorners;
import strategy.TryToBlockCompositeStrategy;
import strategy.TryToWinCompositeStrategy;

/**
 * Tests for the TryToBlockCompositeStrategy strategy.
 */
public class TryToBlockCompositeHexStrategyTests extends AbstractCompositeHexStrategyTests {
  @Override
  protected AbstractCompositeStrategy getCompositeStrategy(
          AbstractSingleStrategy strategy, AIStrategy nextStrategy) {
    return new TryToBlockCompositeStrategy(strategy, nextStrategy);
  }

  @Override
  protected AIStrategy getNextStrategy(DiscColor player, ReadOnlyModel model) {
    return new TryToBlockCompositeStrategy(new AvoidCornerNeighbors(player, model),
        new CaptureMostCells(player, model));
  }

  @Override
  protected AbstractSingleStrategy getSingleStrategy(DiscColor player, ReadOnlyModel model) {
    return new PrioritizeCorners(player, model);
  }

  /*
    getStrategyPossibleMoves() TESTS: abbreviation -> GetSPM
   */

  @Test
  public void testGetStrategyPossibleMovesEmptyForOtherPlayerAndEmptyForThisPlayer() {
    //modify the board to adapt to testing scenario.
    for (Coordinate coord : this.actionableModel.getCopyOfAllCoords().keySet()) {
      ICell cell = this.actionableModel.getCellAt(coord);
      if (cell.getColor() == DiscColor.NONE) {
        this.actionableModel.getCellAt(coord).changeColor(DiscColor.BLACK);
      }
    }

    DiscColor opposingPlayer = this.compositeStrategy.getOpposingPlayerColor();
    TryToBlockCompositeStrategy blockStrategy =
        (TryToBlockCompositeStrategy) this.compositeStrategy;
    TryToWinCompositeStrategy opposingPlayerStrategy =
        blockStrategy.createTryToWinCompositeStrategy();

    Assert.assertTrue(this.compositeStrategy.getAllPossiblePlayerMoves(this.player).isEmpty());
    Assert.assertTrue(opposingPlayerStrategy.getAllPossiblePlayerMoves(opposingPlayer).isEmpty());
    Assert.assertTrue(this.compositeStrategy.getStrategyPossibleMoves(
        this.compositeStrategy.getAllPossiblePlayerMoves(this.player), this.player).isEmpty());
  }

  @Test
  public void testGetStrategyPossibleMovesNonEmptyForOtherPlayerButEmptyForThisPlayer() {
    //modify the board to adapt to testing scenario.
    for (Coordinate coord : this.actionableModel.getCopyOfAllCoords().keySet()) {
      ICell cell = this.actionableModel.getCellAt(coord);
      if ((cell.getColor() == DiscColor.NONE
          || coord.equals(new Coordinate(1, 0)))
          && !coord.equals(new Coordinate(-3, 0))) {
        this.actionableModel.getCellAt(coord).changeColor(DiscColor.WHITE);
      }
    }

    DiscColor opposingPlayer = this.compositeStrategy.getOpposingPlayerColor();
    TryToBlockCompositeStrategy blockStrategy =
        (TryToBlockCompositeStrategy) this.compositeStrategy;
    TryToWinCompositeStrategy opposingPlayerStrategy =
        blockStrategy.createTryToWinCompositeStrategy();

    Assert.assertTrue(this.compositeStrategy.getStrategyPossibleMoves(
        this.compositeStrategy.getAllPossiblePlayerMoves(player), this.player).isEmpty());
    Assert.assertEquals(opposingPlayerStrategy.getAllPossiblePlayerMoves(opposingPlayer).size(), 1);
    Assert.assertEquals(
        opposingPlayerStrategy.getStrategyPossibleMoves(
            opposingPlayerStrategy
                .getAllPossiblePlayerMoves(opposingPlayer), opposingPlayer).size(), 1);
    Assert.assertTrue(this.compositeStrategy.getStrategyPossibleMoves(
        this.compositeStrategy.getAllPossiblePlayerMoves(this.player), this.player).isEmpty());
  }

  @Test
  public void testGetSPMNonEmptyForOtherPlayerAndNonEmptyForThisPlayerButNoIntersection() {
    //modify the board to adapt to testing scenario.
    this.actionableModel.getCellAt(new Coordinate(-2, 0)).changeColor(DiscColor.WHITE);

    DiscColor opposingPlayer = this.compositeStrategy.getOpposingPlayerColor();
    TryToBlockCompositeStrategy blockStrategy =
        (TryToBlockCompositeStrategy) this.compositeStrategy;
    TryToWinCompositeStrategy opposingPlayerStrategy =
        blockStrategy.createTryToWinCompositeStrategy();

    Assert.assertEquals(this.compositeStrategy
        .getAllPossiblePlayerMoves(this.player).size(), 10);
    Assert.assertEquals(opposingPlayerStrategy
        .getAllPossiblePlayerMoves(opposingPlayer).size(), 12);
    Assert.assertEquals(
        opposingPlayerStrategy.getStrategyPossibleMoves(
            opposingPlayerStrategy
                .getAllPossiblePlayerMoves(opposingPlayer), opposingPlayer).size(), 1);
    Assert.assertTrue(this.compositeStrategy.getStrategyPossibleMoves(
        this.compositeStrategy.getAllPossiblePlayerMoves(this.player), this.player).isEmpty());
  }

  @Test
  public void testGetStrategyPossibleMovesOneMoveIntersectionWithThisAndOtherPlayer() {
    //modify the board to adapt to testing scenario.
    this.actionableModel.getCellAt(new Coordinate(-1, 0)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(0, -1)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-3, 2)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-3, 0)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(-3, 3)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(-2, 1)).changeColor(DiscColor.BLACK);

    DiscColor opposingPlayer = this.compositeStrategy.getOpposingPlayerColor();
    TryToBlockCompositeStrategy blockStrategy =
        (TryToBlockCompositeStrategy) this.compositeStrategy;
    TryToWinCompositeStrategy opposingPlayerStrategy =
        blockStrategy.createTryToWinCompositeStrategy();

    Assert.assertEquals(this.compositeStrategy
        .getAllPossiblePlayerMoves(this.player).size(), 12);
    Assert.assertEquals(opposingPlayerStrategy
        .getAllPossiblePlayerMoves(opposingPlayer).size(), 11);

    List<Coordinate> opposingPlayerStrategyMoves =
        opposingPlayerStrategy.getStrategyPossibleMoves(
            opposingPlayerStrategy
                .getAllPossiblePlayerMoves(opposingPlayer), opposingPlayer);

    Assert.assertEquals(opposingPlayerStrategyMoves.size(), 3);
    Assert.assertEquals(opposingPlayerStrategyMoves.get(0), new Coordinate(-3, 1));

    Assert.assertEquals(this.compositeStrategy.getStrategyPossibleMoves(
        this.compositeStrategy.getAllPossiblePlayerMoves(this.player), this.player).get(0),
        opposingPlayerStrategyMoves.get(0));
  }

  @Test
  public void testGetStrategyPossibleMovesMoreThanOneMoveIntersectionWithThisAndOtherPlayer() {
    //modify the board to adapt to testing scenario.
    this.actionableModel.getCellAt(new Coordinate(-1, 0)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(0, -1)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-3, 0)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(-3, 3)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(-2, 1)).changeColor(DiscColor.BLACK);

    DiscColor opposingPlayer = this.compositeStrategy.getOpposingPlayerColor();
    TryToBlockCompositeStrategy blockStrategy =
        (TryToBlockCompositeStrategy) this.compositeStrategy;
    TryToWinCompositeStrategy opposingPlayerStrategy =
        blockStrategy.createTryToWinCompositeStrategy();

    Assert.assertEquals(this.compositeStrategy
        .getAllPossiblePlayerMoves(this.player).size(), 13);
    Assert.assertEquals(opposingPlayerStrategy
        .getAllPossiblePlayerMoves(opposingPlayer).size(), 11);

    List<Coordinate> opposingPlayerStrategyMoves =
        opposingPlayerStrategy.getStrategyPossibleMoves(
            opposingPlayerStrategy
                .getAllPossiblePlayerMoves(opposingPlayer), opposingPlayer);

    Assert.assertEquals(opposingPlayerStrategyMoves.size(), 4);
    Assert.assertEquals(opposingPlayerStrategyMoves.get(0), new Coordinate(-3, 1));
    Assert.assertEquals(opposingPlayerStrategyMoves.get(1), new Coordinate(-3, 2));

    Assert.assertEquals(this.compositeStrategy.getStrategyPossibleMoves(
            this.compositeStrategy.getAllPossiblePlayerMoves(this.player), this.player).get(0),
        opposingPlayerStrategyMoves.get(0));
    Assert.assertEquals(this.compositeStrategy.getStrategyPossibleMoves(
            this.compositeStrategy.getAllPossiblePlayerMoves(this.player), this.player).get(1),
        opposingPlayerStrategyMoves.get(1));
  }
}
