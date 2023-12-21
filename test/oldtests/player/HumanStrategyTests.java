package oldtests.player;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.board.Coordinate;
import strategy.HumanStrategy;

/**
 * Tests for the HumanStrategy class.
 */
public class HumanStrategyTests {
  HumanStrategy strategy;

  @Before
  public void init() {
    this.strategy = new HumanStrategy();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveNullChoiceIllegalArgumentException() {
    this.strategy.move();
  }

  @Test
  public void testMoveForPlayDiscChoiceWithNoSelectedCellShouldReturnEmptyOptional() {
    this.strategy.notifyOfMoveOrPass(0, null);
    Assert.assertTrue(this.strategy.move().isEmpty());
  }

  @Test
  public void testMoveForPlayDiscChoiceWithSelectedCellShouldReturnOptionalWithSelectedCell() {
    this.strategy.notifyOfMoveOrPass(0, new Coordinate(0, 0));
    Assert.assertTrue(this.strategy.move().isPresent());
    Assert.assertEquals(this.strategy.move().get(), new Coordinate(0, 0));
  }

  @Test
  public void testMoveForPassChoiceNoSelectionShouldReturnEmptyOptional() {
    this.strategy.notifyOfMoveOrPass(1, null);
    Assert.assertTrue(this.strategy.move().isEmpty());
  }

  @Test
  public void testMoveForPassChoiceWithSelectionShouldReturnEmptyOptional() {
    this.strategy.notifyOfMoveOrPass(1, new Coordinate(0, 0));
    Assert.assertTrue(this.strategy.move().isEmpty());
  }

  @Test
  public void testResetChoiceWorks() {
    this.strategy.notifyOfMoveOrPass(1, null);
    this.strategy.resetChoice();
    Assert.assertThrows(IllegalArgumentException.class, () -> this.strategy.move());
    this.strategy.notifyOfMoveOrPass(0, new Coordinate(0, 0));
    this.strategy.resetChoice();
    Assert.assertThrows(IllegalArgumentException.class, () -> this.strategy.move());
  }
}
