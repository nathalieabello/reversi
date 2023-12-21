package oldtests.player;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import model.board.Coordinate;
import model.board.ReadOnlyModel;
import model.board.ReadOnlyReversiModel;
import model.cell.DiscColor;
import model.cell.ICell;
import player.AIPlayer;
import player.Player;
import strategy.AIStrategy;
import strategy.AvoidCornerNeighbors;
import strategy.CaptureMostCells;
import strategy.PrioritizeCorners;
import strategy.TryToWinCompositeStrategy;

/**
 * Provides abstract tests with an AI player.
 * Also, provides additional tests for an AI player.
 */
public class AIPlayerTests extends PlayerTests {
  private AIStrategy playerStrategy;

  @Override
  protected Player getPlayer(DiscColor color, ReadOnlyModel model, AIStrategy aiStrategy) {
    this.playerStrategy = aiStrategy;
    return new AIPlayer(color, aiStrategy);
  }

  @Before
  public void init() {
    super.init();
  }

  @Test
  public void testAIPlayerPlayStrategyReturnsEmptyAndNoPossibleMovesPlaysPass() {
    for (Coordinate c : this.actionableModel.getCopyOfAllCoords().keySet()) {
      ICell cell = this.actionableModel.getCellAt(c);
      if (cell.getColor() == DiscColor.NONE && cell.getColor() != DiscColor.WHITE) {
        cell.changeColor(DiscColor.WHITE);
      }
    }
    this.actionableModel.getCellAt(new Coordinate(1, 0)).changeColor(DiscColor.NONE);
    this.actionableModel.getCellAt(new Coordinate(1, -3)).changeColor(DiscColor.NONE);
    this.model = new ReadOnlyReversiModel(actionableModel);
    Assert.assertEquals(Optional.empty(), this.player.play(this.model));
    Assert.assertTrue(this.playerStrategy.getAllPossiblePlayerMoves(DiscColor.BLACK).isEmpty());
    Assert.assertEquals(Optional.empty(), this.playerStrategy.move());
  }

  @Test
  public void testAIPlayerPlayStrategyReturnsEmptyAndPossibleMovesPlaysDisc() {
    this.player = this.getPlayer(DiscColor.BLACK, this.model,
            new TryToWinCompositeStrategy(new PrioritizeCorners(DiscColor.BLACK, model),
                    new TryToWinCompositeStrategy(new AvoidCornerNeighbors(DiscColor.BLACK, model),
                            new CaptureMostCells(DiscColor.BLACK, model))));
    for (Coordinate c : this.actionableModel.getCopyOfAllCoords().keySet()) {
      ICell cell = this.actionableModel.getCellAt(c);
      if (cell.getColor() == DiscColor.NONE && cell.getColor() != DiscColor.WHITE) {
        cell.changeColor(DiscColor.WHITE);
      }
    }
    this.actionableModel.getCellAt(new Coordinate(1, 0)).changeColor(DiscColor.NONE);
    this.actionableModel.getCellAt(new Coordinate(1, -3)).changeColor(DiscColor.NONE);
    this.actionableModel.getCellAt(new Coordinate(0, -2)).changeColor(DiscColor.NONE);
    this.actionableModel.getCellAt(new Coordinate(0, -3)).changeColor(DiscColor.NONE);
    this.model = new ReadOnlyReversiModel(actionableModel);
    Assert.assertNotEquals(Optional.empty(), this.player.play(this.model));
    Assert.assertFalse(this.playerStrategy.getAllPossiblePlayerMoves(DiscColor.BLACK).isEmpty());
    Assert.assertEquals(Optional.empty(), this.playerStrategy.move());
  }

  @Test
  public void testAIPlayerPlayStrategyReturnsMovePlaysMove() {
    Assert.assertNotEquals(Optional.empty(), this.player.play(this.model));
    Assert.assertFalse(this.playerStrategy.getAllPossiblePlayerMoves(DiscColor.BLACK).isEmpty());
    Assert.assertNotEquals(Optional.empty(), this.playerStrategy.move());
  }

  @Test
  public void testIsHuman() {
    Assert.assertFalse(this.player.isHuman());
  }
}
