package oldtests.player;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.board.IModel;
import model.board.ReadOnlyModel;
import model.board.ReadOnlyReversiModel;
import model.board.HexReversiModel;
import model.cell.DiscColor;
import player.AIPlayer;
import player.HumanPlayer;
import player.Player;
import strategy.AIStrategy;
import strategy.CaptureMostCells;
import strategy.PrioritizeCorners;

/**
 * Tests for the Player classes: AIPlayer, HumanPlayer.
 */
public abstract class PlayerTests {
  protected HumanPlayer human;
  protected AIPlayer ai;
  protected ReadOnlyModel model;
  protected Player player;
  protected IModel actionableModel;

  @Before
  public void init() {
    this.actionableModel = new HexReversiModel.HexBuilder().setLayers(3).build();
    actionableModel.startGame();
    this.model = new ReadOnlyReversiModel(actionableModel);
    this.human = new HumanPlayer(DiscColor.BLACK, this.model);
    this.ai = new AIPlayer(DiscColor.WHITE, new PrioritizeCorners(DiscColor.WHITE, this.model));
    this.player = this.getPlayer(DiscColor.BLACK, model,
            new CaptureMostCells(DiscColor.BLACK, model));
  }


  protected abstract Player getPlayer(DiscColor color, ReadOnlyModel model, AIStrategy aiStrategy);

  @Test
  public void testConstructorExceptions() {
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.getPlayer(null, this.model,
                    new CaptureMostCells(DiscColor.BLACK, model)));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.getPlayer(DiscColor.NONE, this.model,
                    new CaptureMostCells(DiscColor.BLACK, model)));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            new AIPlayer(DiscColor.WHITE, null));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayNullModelException() {
    this.player.play(null);
  }

  @Test
  public void testGetPlayerColor() {
    Assert.assertEquals(DiscColor.BLACK, this.human.getPlayerColor());
    Assert.assertEquals(DiscColor.WHITE, this.ai.getPlayerColor());
  }
}
