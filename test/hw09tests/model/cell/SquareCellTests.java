package hw09tests.model.cell;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.cell.DiscColor;
import model.cell.SquareCell;
import model.cell.ICell;

/**
 * The test class for the SquareCell class.
 */
public class SquareCellTests extends AbstractCellTests {

  @Override
  protected ICell getCell() {
    return new SquareCell();
  }

  @Before
  public void init() {
    super.init();
  }

  @Test
  public void testSameColorCellsNotEqual() {
    Assert.assertNotEquals(cell, new SquareCell());
    Assert.assertNotEquals(new SquareCell(), new SquareCell());
    Assert.assertEquals(cell, cell);
    ICell black1 = new SquareCell();
    black1.changeColor(DiscColor.BLACK);
    ICell black2 = new SquareCell();
    black2.changeColor(DiscColor.BLACK);
    Assert.assertNotEquals(black1, black2);
  }

  @Test
  public void testMakeCell() {
    Assert.assertEquals(cell.makeCell().getClass(), SquareCell.class);
  }
}
