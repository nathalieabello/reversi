package hw09tests.model.cell;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.cell.DiscColor;
import model.cell.ICell;

/**
 * The test class for the ICell interface (HexCell and Square Cell classes).
 */
public abstract class AbstractCellTests {
  protected ICell cell;

  /**
   * Gets the cell to test.
   * @return the cell to test.
   */
  protected abstract ICell getCell();

  @Before
  public void init() {
    cell = this.getCell();
  }

  @Test
  public void testColors() {
    Assert.assertEquals(cell.getColor(), DiscColor.NONE);
    Assert.assertThrows(IllegalArgumentException.class, () ->
        cell.changeColor(DiscColor.NONE));
    Assert.assertEquals(cell.getColor(), DiscColor.NONE);
    cell.changeColor(DiscColor.BLACK);
    Assert.assertEquals(cell.getColor(), DiscColor.BLACK);
    Assert.assertThrows(IllegalArgumentException.class, () ->
        cell.changeColor(DiscColor.BLACK));
    Assert.assertEquals(cell.getColor(), DiscColor.BLACK);
    cell.changeColor(DiscColor.NONE);
    Assert.assertEquals(cell.getColor(), DiscColor.NONE);
    cell.changeColor(DiscColor.WHITE);
    Assert.assertEquals(cell.getColor(), DiscColor.WHITE);
    Assert.assertThrows(IllegalArgumentException.class, () ->
        cell.changeColor(DiscColor.WHITE));
    Assert.assertEquals(cell.getColor(), DiscColor.WHITE);
    cell.changeColor(DiscColor.NONE);
    Assert.assertEquals(cell.getColor(), DiscColor.NONE);
  }

}
