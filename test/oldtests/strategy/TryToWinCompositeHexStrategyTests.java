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
import strategy.TryToWinCompositeStrategy;
import view.HexReversiTextualView;

/**
 * Tests for the TryToBlockCompositeStrategy strategy.
 */
public class TryToWinCompositeHexStrategyTests extends AbstractCompositeHexStrategyTests {
  @Override
  protected AbstractCompositeStrategy getCompositeStrategy(
          AbstractSingleStrategy strategy, AIStrategy nextStrategy) {
    return new TryToWinCompositeStrategy(strategy, nextStrategy);
  }

  @Override
  protected AIStrategy getNextStrategy(DiscColor player, ReadOnlyModel model) {
    return new TryToWinCompositeStrategy(new AvoidCornerNeighbors(player, model),
        new CaptureMostCells(player, model));
  }

  @Override
  protected AbstractSingleStrategy getSingleStrategy(DiscColor player, ReadOnlyModel model) {
    return new PrioritizeCorners(player, model);
  }

  /*
    List<Coordinate> getStrategyPossibleMoves() TESTS: abbreviation -> GetSPM.
    Abbreviations: CaptureMostCells -> CMC; AvoidCornerNeighbors -> ACN; PrioritizeCorners -> PC.
   */

  @Test
  public void testGetSPMNonEmptyCMCNonEmptyACNNonEmptyPC() {
    this.actionableModel.getCellAt(new Coordinate(2, 0)).changeColor(DiscColor.WHITE);

    List<Coordinate> allMoves = this.strategy.getAllPossiblePlayerMoves(this.player);
    List<Coordinate> pcMoves = this.pc.getStrategyPossibleMoves(allMoves, this.player);
    List<Coordinate> acnMoves = this.acn.getStrategyPossibleMoves(allMoves, this.player);
    List<Coordinate> cmcMoves = this.cmc.getStrategyPossibleMoves(allMoves, this.player);

    Assert.assertFalse(pcMoves.isEmpty());
    Assert.assertFalse(acnMoves.isEmpty());
    Assert.assertFalse(cmcMoves.isEmpty());

    List<Coordinate> strategyMovesACNWithCMCInput =
        this.acn.getStrategyPossibleMoves(cmcMoves, this.player);
    Assert.assertFalse(strategyMovesACNWithCMCInput.isEmpty());

    List<Coordinate> strategyMovesPCWithCMCInputFollowedByACNInput =
        this.pc.getStrategyPossibleMoves(strategyMovesACNWithCMCInput, this.player);
    Assert.assertFalse(strategyMovesPCWithCMCInputFollowedByACNInput.isEmpty());

    List<Coordinate> strategyMoves = this.strategy.getStrategyPossibleMoves(allMoves, this.player);
    Assert.assertFalse(strategyMoves.isEmpty());
    Assert.assertEquals(strategyMoves.get(0),
        strategyMovesPCWithCMCInputFollowedByACNInput.get(0));
  }

  @Test
  public void testGetSPMNonEmptyCMCNonEmptyACNEmptyPC() {
    List<Coordinate> allMoves = this.strategy.getAllPossiblePlayerMoves(this.player);
    List<Coordinate> pcMoves = this.pc.getStrategyPossibleMoves(allMoves, this.player);
    List<Coordinate> acnMoves = this.acn.getStrategyPossibleMoves(allMoves, this.player);
    List<Coordinate> cmcMoves = this.cmc.getStrategyPossibleMoves(allMoves, this.player);
    Assert.assertTrue(pcMoves.isEmpty());
    Assert.assertFalse(acnMoves.isEmpty());
    Assert.assertFalse(cmcMoves.isEmpty());

    List<Coordinate> strategyMoves = this.strategy.getStrategyPossibleMoves(allMoves, this.player);
    List<Coordinate> strategyMovesACNWithCMCInput =
        this.acn.getStrategyPossibleMoves(cmcMoves, this.player);

    TryToWinCompositeStrategy testStrategy = new TryToWinCompositeStrategy(acn, cmc);
    List<Coordinate> testStrategyMoves =
        testStrategy.getStrategyPossibleMoves(testStrategy
            .getAllPossiblePlayerMoves(this.player), this.player);
    Assert.assertEquals(testStrategyMoves.get(0), strategyMoves.get(0));
    Assert.assertEquals(testStrategyMoves, strategyMoves);

    Assert.assertFalse(strategyMoves.isEmpty());
    Assert.assertEquals(strategyMoves.get(0), strategyMovesACNWithCMCInput.get(0));

  }

  @Test
  public void testGetSPMNonEmptyCMCEmptyACNEmptyPC() {
    for (Coordinate coord : this.actionableModel.getCopyOfAllCoords().keySet()) {
      ICell cell = this.actionableModel.getCellAt(coord);
      if (cell.getColor() == DiscColor.NONE) {
        if (!coord.equals(new Coordinate(-3, 2))
            && !coord.equals(new Coordinate(-2, 2))
            && !coord.equals(new Coordinate(-2, 3))
            && !coord.equals(new Coordinate(-3, 3))) {
          this.actionableModel.getCellAt(coord).changeColor(DiscColor.BLACK);
        }
      }
    }
    this.actionableModel.getCellAt(new Coordinate(-2, 1)).changeColor(DiscColor.WHITE);
    List<Coordinate> allMoves = this.strategy.getAllPossiblePlayerMoves(this.player);
    List<Coordinate> pcMoves = this.pc.getStrategyPossibleMoves(allMoves, this.player);
    List<Coordinate> acnMoves = this.acn.getStrategyPossibleMoves(allMoves, this.player);
    List<Coordinate> cmcMoves = this.cmc.getStrategyPossibleMoves(allMoves, this.player);
    Assert.assertTrue(pcMoves.isEmpty());
    Assert.assertTrue(acnMoves.isEmpty());
    Assert.assertFalse(cmcMoves.isEmpty());
    List<Coordinate> strategyMoves = this.strategy.getStrategyPossibleMoves(allMoves, this.player);
    Assert.assertFalse(strategyMoves.isEmpty());
    Assert.assertEquals(strategyMoves, cmcMoves);
    Assert.assertEquals(strategyMoves.get(0), cmcMoves.get(0));
  }

  @Test
  public void testGetSPEmptyCMCNonEmptyACNEmptyPC() {
    this.actionableModel.getCellAt(new Coordinate(-3, 0)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-3, 3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(0, 3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(0, -3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(3, -3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(3, 0)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-1, 0)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(1, -1)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(0, 1)).changeColor(DiscColor.BLACK);
    List<Coordinate> allMoves = this.strategy.getAllPossiblePlayerMoves(this.player);
    List<Coordinate> pcMoves = this.pc.getStrategyPossibleMoves(allMoves, this.player);
    List<Coordinate> acnMoves = this.acn.getStrategyPossibleMoves(allMoves, this.player);
    List<Coordinate> cmcMoves = this.cmc.getStrategyPossibleMoves(allMoves, this.player);
    List<Coordinate> strategyMoves = this.strategy.getStrategyPossibleMoves(allMoves, this.player);
    Assert.assertTrue(pcMoves.isEmpty());
    Assert.assertFalse(acnMoves.isEmpty());
    Assert.assertTrue(cmcMoves.isEmpty());
    Assert.assertEquals(acnMoves.get(0), strategyMoves.get(0));
  }

  @Test
  public void testGetSPEmptyCMCNonEmptyACNNonEmptyPC() {
    this.actionableModel.getCellAt(new Coordinate(-3, 0)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-3, 3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(0, 3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(0, -3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(3, -3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-1, 0)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(2, 0)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(1, -1)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(0, 1)).changeColor(DiscColor.BLACK);
    List<Coordinate> allMoves = this.strategy.getAllPossiblePlayerMoves(this.player);
    List<Coordinate> pcMoves = this.pc.getStrategyPossibleMoves(allMoves, this.player);
    List<Coordinate> acnMoves = this.acn.getStrategyPossibleMoves(allMoves, this.player);
    List<Coordinate> cmcMoves = this.cmc.getStrategyPossibleMoves(allMoves, this.player);
    List<Coordinate> strategyMoves = this.strategy.getStrategyPossibleMoves(allMoves, this.player);
    Assert.assertFalse(pcMoves.isEmpty());
    Assert.assertFalse(acnMoves.isEmpty());
    Assert.assertTrue(cmcMoves.isEmpty());

    TryToWinCompositeStrategy pCandACNstrategy = new TryToWinCompositeStrategy(pc, acn);
    List<Coordinate> pCandACNstrategyMoves = pCandACNstrategy
        .getStrategyPossibleMoves(pCandACNstrategy
            .getAllPossiblePlayerMoves(this.player), this.player);
    Assert.assertEquals(pCandACNstrategyMoves.get(0), strategyMoves.get(0));
    Assert.assertEquals(pCandACNstrategyMoves, strategyMoves);
  }

  @Test
  public void testGetSPEmptyCMCEmptyACNEmptyPC() {
    this.actionableModel.getCellAt(new Coordinate(0, -3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(3, -3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(0, 3)).changeColor(DiscColor.WHITE);

    this.actionableModel.getCellAt(new Coordinate(-3, 2)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(-3, 3)).changeColor(DiscColor.BLACK);

    this.actionableModel.getCellAt(new Coordinate(-2, 1)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(-2, 2)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(-2, 3)).changeColor(DiscColor.BLACK);

    this.actionableModel.getCellAt(new Coordinate(-1, -2)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-1, -1)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(-1, 0)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(-1, 2)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(-1, 3)).changeColor(DiscColor.BLACK);

    this.actionableModel.getCellAt(new Coordinate(0, -2)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(0, 0)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(0, 1)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(0, 2)).changeColor(DiscColor.BLACK);

    this.actionableModel.getCellAt(new Coordinate(1, -3)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(1, -2)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(1, -1)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(1, 1)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(1, 2)).changeColor(DiscColor.BLACK);

    this.actionableModel.getCellAt(new Coordinate(2, -3)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(2, -2)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(2, -1)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(2, 0)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(2, 1)).changeColor(DiscColor.BLACK);

    this.actionableModel.getCellAt(new Coordinate(3, 0)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(3, -1)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(3, -2)).changeColor(DiscColor.BLACK);

    HexReversiTextualView view = new HexReversiTextualView(this.actionableModel);
    System.out.println(view);

    List<Coordinate> allMoves = this.strategy.getAllPossiblePlayerMoves(this.player);
    List<Coordinate> pcMoves = this.pc.getStrategyPossibleMoves(allMoves, this.player);
    List<Coordinate> acnMoves = this.acn.getStrategyPossibleMoves(allMoves, this.player);
    List<Coordinate> cmcMoves = this.cmc.getStrategyPossibleMoves(allMoves, this.player);
    List<Coordinate> strategyMoves = this.strategy.getStrategyPossibleMoves(allMoves, this.player);

    Assert.assertTrue(pcMoves.isEmpty());
    Assert.assertTrue(acnMoves.isEmpty());
    Assert.assertTrue(cmcMoves.isEmpty());
    Assert.assertTrue(strategyMoves.isEmpty());
  }
}
