package oldtests.player;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import model.board.Coordinate;
import model.board.ReadOnlyModel;
import model.cell.DiscColor;
import player.HumanPlayer;
import player.Player;
import strategy.AIStrategy;

/**
 * Provides abstract tests with a Human player.
 * Also, provides additional tests for a Human player.
 */
public class HumanPlayerTests extends PlayerTests {
  @Override
  protected Player getPlayer(DiscColor color, ReadOnlyModel model, AIStrategy aiStrategy) {
    return new HumanPlayer(color, model);
  }

  @Before
  public void init() {
    super.init();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testHumanPlayerPlayNoChoiceMadeIllegalArgumentException() {
    this.human.play(this.model);
  }


  @Test(expected = IllegalStateException.class)
  public void testHumanPlayerPlayPlayDiscWithEmptyOptionalCoordinateIllegalStateException() {
    this.human.notifyOfMoveOrPass(0, null);
    this.human.play(this.model);
  }

  @Test
  public void testHumanPlayerPlayPassChoiceMadeValid() {
    this.human.notifyOfMoveOrPass(1, null);
    Assert.assertEquals(Optional.empty(), this.human.play(this.model));
  }

  @Test
  public void testHumanPlayerPlayPlayDiscChoiceMadeValid() {
    this.human.notifyOfMoveOrPass(0, new Coordinate(0, 0));
    Assert.assertTrue(this.human.play(model).isPresent());
    Assert.assertEquals(this.human.play(model), Optional.of(new Coordinate(0, 0)));
  }

  @Test
  public void testHumanPlayerResetChoiceAndPlayWorks() {
    this.human.notifyOfMoveOrPass(0, new Coordinate(0, 0));
    Assert.assertTrue(this.human.play(model).isPresent());
    Assert.assertEquals(this.human.play(model), Optional.of(new Coordinate(0, 0)));
    this.human.resetMove();
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.human.play(this.model));
    this.human.notifyOfMoveOrPass(0, new Coordinate(-1, 2));
    Assert.assertTrue(this.human.play(model).isPresent());
    Assert.assertEquals(this.human.play(model), Optional.of(new Coordinate(-1, 2)));
  }

  @Test
  public void testIsHuman() {
    Assert.assertTrue(this.player.isHuman());
  }
}
