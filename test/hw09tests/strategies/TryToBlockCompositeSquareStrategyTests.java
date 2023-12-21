package hw09tests.strategies;

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
import view.SquareReversiTextualView;

/**
 * Tests for the TryToBlockCompositeStrategy strategy.
 */
public class TryToBlockCompositeSquareStrategyTests extends AbstractCompositeSquareStrategyTests {
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
      if ((coord.getCol() == coord.getRow())
              || (coord.getCol() == -coord.getRow() + 1)
              || (coord.getRow() == 1) || (coord.getCol() == 1)) {
        if (cell.getColor() != DiscColor.WHITE) {
          cell.changeColor(DiscColor.WHITE);
        }
      }
    }
    this.actionableModel.getCellAt(new Coordinate(1, 1)).changeColor(DiscColor.BLACK);
    DiscColor opposingPlayer = this.compositeStrategy.getOpposingPlayerColor();
    TryToBlockCompositeStrategy blockStrategy =
            (TryToBlockCompositeStrategy) this.compositeStrategy;
    TryToWinCompositeStrategy opposingPlayerStrategy =
            blockStrategy.createTryToWinCompositeStrategy();

    Assert.assertTrue(this.compositeStrategy.getStrategyPossibleMoves(
            this.compositeStrategy.getAllPossiblePlayerMoves(player), this.player).isEmpty());
    Assert.assertEquals(opposingPlayerStrategy.getAllPossiblePlayerMoves(opposingPlayer).size(),
            16);
    Assert.assertEquals(
            opposingPlayerStrategy.getStrategyPossibleMoves(
                    opposingPlayerStrategy
                            .getAllPossiblePlayerMoves(opposingPlayer),
                    opposingPlayer).size(), 2);
    Assert.assertTrue(this.compositeStrategy.getStrategyPossibleMoves(
            this.compositeStrategy.getAllPossiblePlayerMoves(this.player), this.player).isEmpty());
  }

  @Test
  public void testGetSPMNonEmptyForOtherPlayerAndNonEmptyForThisPlayerButNoIntersection() {
    this.actionableModel.getCellAt(new Coordinate(-1, -1)).changeColor(DiscColor.NONE);
    this.actionableModel.getCellAt(new Coordinate(1, 1)).changeColor(DiscColor.NONE);
    this.actionableModel.getCellAt(new Coordinate(-1, 1)).changeColor(DiscColor.NONE);
    this.actionableModel.getCellAt(new Coordinate(1, -1)).changeColor(DiscColor.NONE);
    this.actionableModel.getCellAt(new Coordinate(-3, -3)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(3, 3)).changeColor(DiscColor.WHITE);
    //modify the board to adapt to testing scenario.
    DiscColor opposingPlayer = this.compositeStrategy.getOpposingPlayerColor();
    TryToBlockCompositeStrategy blockStrategy =
            (TryToBlockCompositeStrategy) this.compositeStrategy;
    TryToWinCompositeStrategy opposingPlayerStrategy =
            blockStrategy.createTryToWinCompositeStrategy();

    Assert.assertEquals(this.compositeStrategy
            .getAllPossiblePlayerMoves(this.player).size(), 3);
    Assert.assertEquals(opposingPlayerStrategy
            .getAllPossiblePlayerMoves(opposingPlayer).size(), 3);
    Assert.assertEquals(
            opposingPlayerStrategy.getStrategyPossibleMoves(
                    opposingPlayerStrategy
                            .getAllPossiblePlayerMoves(opposingPlayer), opposingPlayer).size(), 3);
    Assert.assertTrue(this.compositeStrategy.getStrategyPossibleMoves(
            this.compositeStrategy.getAllPossiblePlayerMoves(this.player), this.player).isEmpty());
  }

  @Test
  public void testGetStrategyPossibleMovesOneMoveIntersectionWithThisAndOtherPlayer() {
    //modify the board to adapt to testing scenario.
    SquareReversiTextualView view = new SquareReversiTextualView(this.actionableModel);
    System.out.println(view);
    DiscColor opposingPlayer = this.compositeStrategy.getOpposingPlayerColor();
    TryToBlockCompositeStrategy blockStrategy =
            (TryToBlockCompositeStrategy) this.compositeStrategy;
    TryToWinCompositeStrategy opposingPlayerStrategy =
            blockStrategy.createTryToWinCompositeStrategy();

    Assert.assertEquals(this.compositeStrategy
            .getAllPossiblePlayerMoves(this.player).size(), 10);
    Assert.assertEquals(opposingPlayerStrategy
            .getAllPossiblePlayerMoves(opposingPlayer).size(), 10);

    List<Coordinate> opposingPlayerStrategyMoves =
            opposingPlayerStrategy.getStrategyPossibleMoves(
                    opposingPlayerStrategy
                            .getAllPossiblePlayerMoves(opposingPlayer), opposingPlayer);

    Assert.assertEquals(opposingPlayerStrategyMoves.size(), 4);
    Assert.assertEquals(opposingPlayerStrategyMoves.get(0), new Coordinate(-1, -2));

    Assert.assertEquals(this.compositeStrategy.getStrategyPossibleMoves(
            this.compositeStrategy.getAllPossiblePlayerMoves(this.player),
            this.player).get(0), opposingPlayerStrategyMoves.get(0));
  }

  @Test
  public void testGetStrategyPossibleMovesMoreThanOneMoveIntersectionWithThisAndOtherPlayer() {
    //modify the board to adapt to testing scenario.
    for (Coordinate coord : this.actionableModel.getCopyOfAllCoords().keySet()) {
      ICell cell = this.actionableModel.getCellAt(coord);
      if ((coord.getCol() == coord.getRow())
              || (coord.getCol() == -coord.getRow() + 1)) {
        if (cell.getColor() != DiscColor.WHITE) {
          cell.changeColor(DiscColor.WHITE);
        }
      } else if ((coord.getRow() == 1) || (coord.getCol() == 1) || (coord.getRow() == -3)) {
        if (cell.getColor() != DiscColor.BLACK) {
          cell.changeColor(DiscColor.BLACK);
        }
      }
    }
    this.actionableModel.getCellAt(new Coordinate(-3, -3)).changeColor(DiscColor.NONE);
    this.actionableModel.getCellAt(new Coordinate(-3, 3)).changeColor(DiscColor.NONE);

    DiscColor opposingPlayer = this.compositeStrategy.getOpposingPlayerColor();
    TryToBlockCompositeStrategy blockStrategy =
            (TryToBlockCompositeStrategy) this.compositeStrategy;
    TryToWinCompositeStrategy opposingPlayerStrategy =
            blockStrategy.createTryToWinCompositeStrategy();

    Assert.assertEquals(this.compositeStrategy
            .getAllPossiblePlayerMoves(this.player).size(), 13);
    Assert.assertEquals(opposingPlayerStrategy
            .getAllPossiblePlayerMoves(opposingPlayer).size(), 14);

    List<Coordinate> opposingPlayerStrategyMoves =
            opposingPlayerStrategy.getStrategyPossibleMoves(
                    opposingPlayerStrategy
                            .getAllPossiblePlayerMoves(opposingPlayer), opposingPlayer);

    Assert.assertEquals(opposingPlayerStrategyMoves.size(), 3);
    Assert.assertEquals(opposingPlayerStrategyMoves.get(0), new Coordinate(-3, -3));
    Assert.assertEquals(opposingPlayerStrategyMoves.get(1), new Coordinate(-3, 3));
    Assert.assertEquals(this.compositeStrategy
                    .getStrategyPossibleMoves(this.compositeStrategy
                            .getAllPossiblePlayerMoves(this.player), this.player).get(0),
            opposingPlayerStrategyMoves.get(0));
    Assert.assertEquals(this.compositeStrategy.getStrategyPossibleMoves(
            this.compositeStrategy.getAllPossiblePlayerMoves(this.player),
            this.player).get(1), opposingPlayerStrategyMoves.get(1));
  }
}
