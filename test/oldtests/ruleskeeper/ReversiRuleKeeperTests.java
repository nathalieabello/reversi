package oldtests.ruleskeeper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import model.board.Coordinate;
import model.board.IModel;
import model.board.HexReversiModel;
import model.cell.DiscColor;
import ruleskeeper.ReversiRuleKeeper;
import ruleskeeper.RuleKeeper;

/**
 * Test class for the ReversiRuleKeeper class.
 */
public class ReversiRuleKeeperTests {
  private RuleKeeper ruleKeeper;
  private IModel model;

  @Before
  public void init() {
    this.ruleKeeper = new ReversiRuleKeeper();
    this.model = new HexReversiModel.HexBuilder().setRuleKeeper(this.ruleKeeper).build();
    this.model.startGame();
  }

  /*
    boolean isValid(IModel model, Coordinate coordinate) TESTS
   */

  @Test(expected = IllegalArgumentException.class)
  public void testIsValidNullModelIllegalArgumentException() {
    this.ruleKeeper.isValid(null, new Coordinate(0, 0), DiscColor.BLACK);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIsValidNullCoordinateIllegalArgumentException() {
    this.ruleKeeper.isValid(this.model, null, DiscColor.BLACK);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIsValidInvalidCoordinateIllegalArgumentException() {
    this.ruleKeeper.isValid(this.model, new Coordinate(-10, -10), DiscColor.BLACK);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIsValidNullPlayerColorIllegalArgumentException() {
    this.ruleKeeper.isValid(this.model, new Coordinate(0, 0), null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIsValidNeighborsNONEPlayerColorIllegalArgumentException() {
    this.ruleKeeper.isValid(this.model, new Coordinate(0, 0), DiscColor.NONE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIsValidNeighborsInvalidPlayerColorIllegalArgumentException() {
    this.ruleKeeper.isValid(this.model, new Coordinate(0, 0), DiscColor.MAGENTA);
  }

  @Test
  public void testIsValidTrueBothHasLikeNeighborAndCanSandwich() {
    Coordinate coordToCheck = new Coordinate(2, -1);
    Assert.assertEquals(this.model.getTurn(), DiscColor.BLACK);
    Assert.assertEquals(
        this.model.getLikeNeighbors(coordToCheck, DiscColor.BLACK).size(), 1);
    Assert.assertEquals(
        this.model.getSandwichableNeighbors(coordToCheck, DiscColor.BLACK).size(), 1);
    Assert.assertTrue(this.ruleKeeper.isValid(this.model, coordToCheck, DiscColor.BLACK));
  }

  @Test
  public void testIsValidTrueHasLikeNeighborOnly() {
    Coordinate coordToCheck = new Coordinate(2, 0);
    Assert.assertEquals(this.model.getTurn(), DiscColor.BLACK);
    Assert.assertEquals(
        this.model.getLikeNeighbors(coordToCheck, DiscColor.BLACK).size(), 1);
    Assert.assertEquals(
        this.model.getSandwichableNeighbors(coordToCheck, DiscColor.BLACK).size(), 0);
    Assert.assertTrue(this.ruleKeeper.isValid(this.model, coordToCheck, DiscColor.BLACK));
  }

  @Test
  public void testIsValidTrueCanSandwichOnly() {
    this.model.getCellAt(new Coordinate(2, 0)).changeColor(DiscColor.WHITE);

    Coordinate coordToCheck = new Coordinate(3, 0);
    Assert.assertEquals(this.model.getTurn(), DiscColor.BLACK);
    Assert.assertEquals(
        this.model.getLikeNeighbors(coordToCheck, DiscColor.BLACK).size(), 0);
    Assert.assertEquals(
        this.model.getSandwichableNeighbors(coordToCheck, DiscColor.BLACK).size(), 1);
    Assert.assertTrue(this.ruleKeeper.isValid(this.model, coordToCheck, DiscColor.BLACK));
  }

  @Test
  public void testIsValidFalseHasNeighborsButInvalidMove() {
    Coordinate coordToCheck = new Coordinate(2, -2);
    Assert.assertEquals(this.model.getTurn(), DiscColor.BLACK);
    Assert.assertEquals(
        this.model.getLikeNeighbors(coordToCheck, DiscColor.BLACK).size(), 0);
    Assert.assertEquals(
        this.model.getOppositeNeighbors(coordToCheck, DiscColor.BLACK).size(), 1);
    Assert.assertFalse(this.ruleKeeper.isValid(this.model, coordToCheck, DiscColor.BLACK));
  }

  @Test
  public void testIsValidFalseNoNeighborsInvalidMove() {
    Coordinate coordToCheck = new Coordinate(0, 3);
    Assert.assertEquals(this.model.getTurn(), DiscColor.BLACK);
    Assert.assertEquals(
        this.model.getLikeNeighbors(coordToCheck, DiscColor.BLACK).size(), 0);
    Assert.assertEquals(
        this.model.getOppositeNeighbors(coordToCheck, DiscColor.BLACK).size(), 0);
    Assert.assertFalse(this.ruleKeeper.isValid(this.model, coordToCheck, DiscColor.BLACK));
  }

  /*
    List<Coordinate> playDisc(IModel model, Coordinate coordinate) TESTS
   */
  @Test(expected = IllegalArgumentException.class)
  public void testPlayDiscNullModelIllegalArgumentException() {
    this.ruleKeeper.playDisc(null, new Coordinate(0, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayDiscNullCoordinateIllegalArgumentException() {
    this.ruleKeeper.playDisc(this.model, null);
  }

  @Test(expected = IllegalStateException.class)
  public void testPlayDiscHasNeighborsButInvalidMoveIllegalStateException() {
    this.testIsValidFalseHasNeighborsButInvalidMove();
    this.ruleKeeper.playDisc(this.model, new Coordinate(0, 3));
  }

  @Test(expected = IllegalStateException.class)
  public void testPlayDiscNoNeighborsInvalidMoveIllegalStateException() {
    this.testIsValidFalseNoNeighborsInvalidMove();
    this.ruleKeeper.playDisc(this.model, new Coordinate(2, -2));
  }

  @Test
  public void testPlayDiscBothHasLikeNeighborAndCanSandwichValidMove() {
    this.testIsValidTrueBothHasLikeNeighborAndCanSandwich();

    Coordinate coordToPlay = new Coordinate(2, -1);
    List<Coordinate> cellsToChange =
        this.ruleKeeper.playDisc(this.model, coordToPlay);
    Assert.assertEquals(cellsToChange.size(), 2);
    Assert.assertTrue(cellsToChange.contains(coordToPlay));
    Assert.assertTrue(cellsToChange.contains(new Coordinate(1, -1)));
  }

  @Test
  public void testPlayDiscHasLikeNeighborOnlyValidMove() {
    this.testIsValidTrueHasLikeNeighborOnly();

    Coordinate coordToPlay = new Coordinate(2, 0);
    List<Coordinate> cellsToChange =
        this.ruleKeeper.playDisc(this.model, coordToPlay);
    Assert.assertEquals(cellsToChange.size(), 1);
    Assert.assertTrue(cellsToChange.contains(coordToPlay));
  }

  @Test
  public void testPlayDiscOneCanSandwichInOneDirectionOnlyValidMove() {
    this.model.getCellAt(new Coordinate(2, 0)).changeColor(DiscColor.WHITE);

    Coordinate coordToPlay = new Coordinate(3, 0);
    Assert.assertEquals(this.model.getTurn(), DiscColor.BLACK);
    Assert.assertEquals(
        this.model.getLikeNeighbors(coordToPlay, DiscColor.BLACK).size(), 0);
    Assert.assertEquals(
        this.model.getSandwichableNeighbors(coordToPlay, DiscColor.BLACK).size(), 1);
    List<Coordinate> cellsToChange =
        this.ruleKeeper.playDisc(this.model, coordToPlay);
    Assert.assertEquals(cellsToChange.size(), 2);
    Assert.assertTrue(cellsToChange.contains(coordToPlay));
    Assert.assertTrue(cellsToChange.contains(new Coordinate(2, 0)));
  }

  @Test
  public void testPlayDiscMoreThanOneCanSandwichInOneDirectionOnlyValidMove() {
    this.model.getCellAt(new Coordinate(0, 0)).changeColor(DiscColor.WHITE);

    Coordinate coordToPlay = new Coordinate(0, 2);
    Assert.assertEquals(this.model.getTurn(), DiscColor.BLACK);
    Assert.assertEquals(
        this.model.getLikeNeighbors(coordToPlay, DiscColor.BLACK).size(), 0);
    Assert.assertEquals(
        this.model.getSandwichableNeighbors(coordToPlay, DiscColor.BLACK).size(), 2);
    List<Coordinate> cellsToChange =
        this.ruleKeeper.playDisc(this.model, coordToPlay);
    Assert.assertEquals(cellsToChange.size(), 3);
    Assert.assertTrue(cellsToChange.contains(coordToPlay));
    Assert.assertTrue(cellsToChange.contains(new Coordinate(0, 0)));
    Assert.assertTrue(cellsToChange.contains(new Coordinate(0, 1)));
  }

  @Test
  public void testPlayDiscOneCanSandwichInMoreThanOneDirectionOnlyValidMove() {
    this.model.getCellAt(new Coordinate(1, 0)).changeColor(DiscColor.WHITE);
    this.model.getCellAt(new Coordinate(0, 1)).changeColor(DiscColor.BLACK);

    Coordinate coordToPlay = new Coordinate(2, -1);
    Assert.assertEquals(this.model.getTurn(), DiscColor.BLACK);
    Assert.assertEquals(
        this.model.getLikeNeighbors(coordToPlay, DiscColor.BLACK).size(), 0);
    Assert.assertEquals(
        this.model.getSandwichableNeighbors(coordToPlay, DiscColor.BLACK).size(), 2);
    List<Coordinate> cellsToChange =
        this.ruleKeeper.playDisc(this.model, coordToPlay);
    Assert.assertEquals(cellsToChange.size(), 3);
    Assert.assertTrue(cellsToChange.contains(coordToPlay));
    Assert.assertTrue(cellsToChange.contains(new Coordinate(1, -1)));
    Assert.assertTrue(cellsToChange.contains(new Coordinate(1, 0)));
  }

  @Test
  public void testPlayDiscMoreThanOneCanSandwichInMoreThanOneDirectionOnlyValidMove() {
    this.model.getCellAt(new Coordinate(-1, 1)).changeColor(DiscColor.WHITE);
    this.model.getCellAt(new Coordinate(0, -1)).changeColor(DiscColor.WHITE);
    this.model.getCellAt(new Coordinate(1, -2)).changeColor(DiscColor.BLACK);
    this.model.getCellAt(new Coordinate(1, 1)).changeColor(DiscColor.BLACK);

    Coordinate coordToPlay = new Coordinate(-2, 1);
    Assert.assertEquals(this.model.getTurn(), DiscColor.BLACK);
    Assert.assertEquals(
        this.model.getLikeNeighbors(coordToPlay, DiscColor.BLACK).size(), 0);
    Assert.assertEquals(
        this.model.getSandwichableNeighbors(coordToPlay, DiscColor.BLACK).size(), 4);
    List<Coordinate> cellsToChange =
        this.ruleKeeper.playDisc(this.model, coordToPlay);
    Assert.assertEquals(cellsToChange.size(), 5);
    Assert.assertTrue(cellsToChange.contains(coordToPlay));
    Assert.assertTrue(cellsToChange.contains(new Coordinate(0, -1)));
    Assert.assertTrue(cellsToChange.contains(new Coordinate(-1, 0)));
    Assert.assertTrue(cellsToChange.contains(new Coordinate(0, 1)));
    Assert.assertTrue(cellsToChange.contains(new Coordinate(-1, 1)));
  }

}
