package hw09tests.model.board;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import model.board.Coordinate;
import model.board.IModel;
import model.board.ReadOnlyModel;
import model.board.Status;
import model.cell.DiscColor;

/**
 * Tests all kinds of ReversiModels, abstractly.
 */
public abstract class AbstractReversiModelTests {
  protected ReadOnlyModel model;
  protected IModel actionableModel; //needed for modifying the model to test the methods.

  /**
   * Gets the model to be used for testing.
   *
   * @return the model to be used for testing.
   */
  protected abstract ReadOnlyModel getModel();

  /**
   * Gets the actionable model to be used for testing.
   *
   * @return the actionable model to be used for testing.
   */
  protected abstract IModel getActionableModel();

  @Before
  public void init() {
    this.model = this.getModel();
    this.actionableModel = this.getActionableModel();
  }

  @Test
  public void testStartGame() {
    Assert.assertEquals(this.model.getGameState(), Status.PLAYING);
  }

  @Test(expected = IllegalStateException.class)
  public void testStartGameException() {
    this.actionableModel.startGame();
  }

  /*
    List<Coordinate> getNeighbors(Coordinate coord) TESTS
   */

  @Test(expected = IllegalArgumentException.class)
  public void testGetNeighborsNullCoordinateIllegalArgumentException() {
    this.model.getNeighbors(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNeighborsInvalidCoordinateIllegalArgumentException() {
    this.model.getNeighbors(new Coordinate(-5, -3));
  }

  /*
  ICell getCellAt(Coordinate coord) TESTS
 */

  @Test(expected = IllegalArgumentException.class)
  public void testGetCellAtNullCoordinateIllegalArgumentException() {
    this.model.getCellAt(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCellAtInvalidCoordinateIllegalArgumentException() {
    this.model.getCellAt(new Coordinate(-5, -3));
  }

  /*
  void pass() TESTS
 */

  @Test
  public void testPass() {
    Assert.assertEquals(this.model.getTurn(), DiscColor.BLACK);
    Assert.assertEquals(this.model.getGameState(), Status.PLAYING);
    this.actionableModel.pass();
    Assert.assertEquals(this.model.getTurn(), DiscColor.WHITE);
    Assert.assertEquals(this.model.getGameState(), Status.PLAYING);
    this.actionableModel.pass();
    Assert.assertEquals(this.model.getTurn(), DiscColor.BLACK);
    Assert.assertEquals(this.model.getGameState(), Status.TIED);
  }

  /*
    DiscColor getTurn() TESTS
   */

  @Test
  public void testGetTurn() {
    Assert.assertEquals(this.model.getTurn(), DiscColor.BLACK);
    this.actionableModel.pass();
    Assert.assertEquals(this.model.getTurn(), DiscColor.WHITE);
  }

  /*
    List<Coordinate> getSandwichableNeighbors(Coordinate origin) TESTS
   */

  @Test(expected = IllegalArgumentException.class)
  public void testGetSandwichableNeighborsNullCoordinateIllegalArgumentException() {
    this.model.getSandwichableNeighbors(null, model.getPlayerColors().get(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetSandwichableNeighborsInvalidCoordinateIllegalArgumentException() {
    this.model.getSandwichableNeighbors(
            new Coordinate(-5, -3), model.getPlayerColors().get(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetSandwichableNeighborsNullPlayerColorIllegalArgumentException() {
    this.model.getSandwichableNeighbors(
            new Coordinate(1, 1), null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetSandwichableNeighborsNONEPlayerColorIllegalArgumentException() {
    this.model.getSandwichableNeighbors(
            new Coordinate(1, 2), DiscColor.NONE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetSandwichableNeighborsInvalidPlayerColorIllegalArgumentException() {
    this.model.getSandwichableNeighbors(
            new Coordinate(1, 1), DiscColor.MAGENTA);
  }

  /*
    List<Coordinate> getLikeNeighbors(Coordinate origin) TESTS
  */

  @Test(expected = IllegalArgumentException.class)
  public void testGetLikeNeighborsNullCoordinateIllegalArgumentException() {
    this.model.getLikeNeighbors(null, model.getPlayerColors().get(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetLikeNeighborsInvalidCoordinateIllegalArgumentException() {
    this.model.getLikeNeighbors(new Coordinate(-5, -3), model.getPlayerColors().get(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetLikeNeighborsNullPlayerColorIllegalArgumentException() {
    this.model.getLikeNeighbors(
            new Coordinate(1, 1), null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetLikeNeighborsNONEPlayerColorIllegalArgumentException() {
    this.model.getLikeNeighbors(
            new Coordinate(1, 2), DiscColor.NONE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetLikeNeighborsInvalidPlayerColorIllegalArgumentException() {
    this.model.getLikeNeighbors(
            new Coordinate(1, 1), DiscColor.MAGENTA);
  }

  /*
  List<Coordinate> getOppositeNeighbors(Coordinate origin) TESTS
  */

  @Test(expected = IllegalArgumentException.class)
  public void testGetOppositeNeighborsNullCoordinateIllegalArgumentException() {
    this.model.getOppositeNeighbors(null,
            model.getPlayerColors().get(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetOppositeNeighborsInvalidCoordinateIllegalArgumentException() {
    this.model.getOppositeNeighbors(new Coordinate(-5, -3),
            model.getPlayerColors().get(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetOppositeNeighborsNullPlayerColorIllegalArgumentException() {
    this.model.getOppositeNeighbors(
            new Coordinate(1, 1), null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetOppositeNeighborsNONEPlayerColorIllegalArgumentException() {
    this.model.getOppositeNeighbors(
            new Coordinate(1, 2), DiscColor.NONE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetOppositeNeighborsInvalidPlayerColorIllegalArgumentException() {
    this.model.getOppositeNeighbors(
            new Coordinate(1, 1), DiscColor.MAGENTA);
  }

  /*
    boolean isGameOver() TESTS
   */

  @Test
  public void testIsGameOverGameFalseStillPlaying() {
    Assert.assertFalse(this.model.isGameOver());
  }

  @Test
  public void testIsGameOverTrueDoublePassGameTied() {
    Assert.assertFalse(this.model.isGameOver());
    this.actionableModel.pass();
    Assert.assertFalse(this.model.isGameOver());
    this.actionableModel.pass();
    Assert.assertTrue(this.model.isGameOver());
  }

  @Test(expected = IllegalStateException.class)
  public void testIsGameOverTrueThrowsIllegalStateExceptionIfMoveIsMade() {
    this.actionableModel.pass(); //pass player 1's turn for testing purposes.
    this.actionableModel.pass(); //pass player 2's turn for testing purposes.

    Assert.assertEquals(this.model.getGameState(), Status.TIED);
    Assert.assertTrue(this.model.isGameOver());
    this.actionableModel.playDisc(new Coordinate(0, 3));
  }

  @Test(expected = IllegalStateException.class)
  public void testIsGameOverTrueThrowsIllegalStateExceptionIfAttemptToPass() {
    this.actionableModel.pass(); //pass player 1's turn for testing purposes.
    this.actionableModel.pass(); //pass player 2's turn for testing purposes.

    Assert.assertEquals(this.model.getGameState(), Status.TIED);
    Assert.assertTrue(this.model.isGameOver());
    this.actionableModel.pass();
  }

  /*
    Status getGameState() TESTS
   */

  @Test
  public void testGetGameStatePlaying() {
    Assert.assertEquals(this.model.getGameState(), Status.PLAYING);
  }

  /*
    DiscColor getWinner() TESTS
   */

  @Test(expected = IllegalStateException.class)
  public void testGetWinnerWhileGameIsNotOverIllegalStateException() {
    Assert.assertEquals(this.model.getGameState(), Status.PLAYING);
    Assert.assertFalse(this.model.isGameOver());
    this.model.getWinner();
  }

  @Test
  public void testGetWinnerTiedBothPassedNoWinner() {
    this.actionableModel.pass(); //first player passes
    this.actionableModel.pass(); //second player passes

    Assert.assertEquals(this.model.getGameState(), Status.TIED);
    Assert.assertTrue(this.model.isGameOver());
    Assert.assertEquals(this.model.getWinner(), Optional.empty());
  }


  /*
    List<DiscColor> getPlayerColors() TESTS
  */

  @Test
  public void testGetPlayerColors() {
    Assert.assertEquals(this.model.getPlayerColors(), List.of(DiscColor.BLACK, DiscColor.WHITE));
  }

  /*
    DiscColor getOtherPlayerColor() TESTS
  */

  @Test
  public void testGetOtherPlayerColor() {
    Assert.assertEquals(this.model.getTurn(), DiscColor.BLACK);
    Assert.assertEquals(this.model.getOtherPlayerColor(), DiscColor.WHITE);

    this.actionableModel.pass();
    Assert.assertEquals(this.model.getTurn(), DiscColor.WHITE);
    Assert.assertEquals(this.model.getOtherPlayerColor(), DiscColor.BLACK);
  }

  /*
    boolean getPass() TESTS
  */

  @Test
  public void testGetPass() {
    Assert.assertFalse(this.model.getPass());

    this.actionableModel.pass(); //pass player 1's turn for testing purposes.

    Assert.assertTrue(this.model.getPass());
  }
}
