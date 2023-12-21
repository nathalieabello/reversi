package hw09tests.model.board;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import model.board.Coordinate;
import model.board.IModel;
import model.board.ReadOnlyModel;
import model.board.SquareReversiModel;
import model.board.Status;
import model.cell.DiscColor;
import model.cell.ICell;

/**
 * Test class for the Square ReversiModel class, which extends the AbstractReversiModelTests class.
 */
public class SquareReversiModelTests extends AbstractReversiModelTests {
  private final IModel model = new SquareReversiModel.SquareBuilder().setLayers(3).build();

  /**
   * Gets the model to be used for testing.
   *
   * @return the model to be used for testing.
   */
  @Override
  protected ReadOnlyModel getModel() {
    this.model.startGame();
    return this.model;
  }

  @Override
  protected IModel getActionableModel() {
    return this.model;
  }

  /*
    CONSTRUCTING THE GAME TESTS
  */

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullRuleKeeperIllegalArgumentException() {
    new SquareReversiModel.SquareBuilder().setRuleKeeper(null).build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullTypeCellIllegalArgumentException() {
    new SquareReversiModel.SquareBuilder().setTypeCell(null).build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorOutOfLowerBoundLayersIllegalArgumentException() {
    new SquareReversiModel.SquareBuilder().setLayers(1).build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullPlayerColorsIllegalArgumentException() {
    new SquareReversiModel.SquareBuilder().setPlayerColors(null).build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorLessThanTwoPlayerColorsIllegalArgumentException() {
    List<DiscColor> playerColors = List.of(DiscColor.BLACK);
    new SquareReversiModel.SquareBuilder().setPlayerColors(playerColors).build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorMoreThanTwoPlayerColorsIllegalArgumentException() {
    List<DiscColor> playerColors = List.of(DiscColor.BLACK, DiscColor.WHITE, DiscColor.MAGENTA);
    new SquareReversiModel.SquareBuilder().setPlayerColors(playerColors).build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorContainsNullPlayerColorsIllegalArgumentException() {
    List<DiscColor> playerColors = Arrays.asList(DiscColor.BLACK, null);
    new SquareReversiModel.SquareBuilder().setPlayerColors(playerColors).build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidFirstPlayerColorsIllegalArgumentException() {
    List<DiscColor> playerColors = Arrays.asList(DiscColor.NONE, DiscColor.WHITE);
    new SquareReversiModel.SquareBuilder().setPlayerColors(playerColors).build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidSecondPlayerColorsIllegalArgumentException() {
    List<DiscColor> playerColors = Arrays.asList(DiscColor.BLACK, DiscColor.NONE);
    new SquareReversiModel.SquareBuilder().setPlayerColors(playerColors).build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidDuplicatePlayerColorsIllegalArgumentException() {
    List<DiscColor> playerColors = Arrays.asList(DiscColor.BLACK, DiscColor.BLACK);
    new SquareReversiModel.SquareBuilder().setPlayerColors(playerColors).build();
  }

  @Test
  public void testOddLayersIllegalArgumentException() {
    Assert.assertThrows(IllegalArgumentException.class, () ->
            new SquareReversiModel.SquareBuilder().setLayers(1).build());
    Assert.assertEquals(
            new SquareReversiModel.SquareBuilder().setLayers(2).build().getNumLayers(), 2);
    Assert.assertEquals(
            new SquareReversiModel.SquareBuilder().setLayers(3).build().getNumLayers(), 3);
    Assert.assertEquals(
            new SquareReversiModel.SquareBuilder().setLayers(4).build().getNumLayers(), 4);
  }

  @Test
  public void testGameSetUpCorrectlyForFourLayerGameDefaultPlayerColors() {
    //check grid
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.model.getCellAt(new Coordinate(0, 0)).getColor());
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.model.getCellAt(new Coordinate(-1, 0)).getColor());
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.model.getCellAt(new Coordinate(-1, 0)).getColor());
    Assert.assertEquals(this.model.getCellAt(new Coordinate(1, -1)).getColor(),
            DiscColor.WHITE);
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.model.getCellAt(new Coordinate(1, 0)).getColor());
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.model.getCellAt(new Coordinate(0, -1)).getColor());
    Assert.assertEquals(this.model.getCellAt(new Coordinate(-1, 1)).getColor(),
            DiscColor.WHITE);
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.model.getCellAt(new Coordinate(0, -3)).getColor());
    Assert.assertEquals(this.model.getCellAt(new Coordinate(1, -3)).getColor(),
            DiscColor.NONE);
    Assert.assertEquals(this.model.getCellAt(new Coordinate(2, -3)).getColor(),
            DiscColor.NONE);
    Assert.assertEquals(this.model.getCellAt(new Coordinate(3, -3)).getColor(),
            DiscColor.NONE);
    Assert.assertEquals(this.model.getCellAt(new Coordinate(-1, -2)).getColor(),
            DiscColor.NONE);
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.model.getCellAt(new Coordinate(0, -2)).getColor());
    Assert.assertEquals(this.model.getCellAt(new Coordinate(1, -2)).getColor(),
            DiscColor.NONE);
    Assert.assertEquals(this.model.getCellAt(new Coordinate(2, -2)).getColor(),
            DiscColor.NONE);
    Assert.assertEquals(this.model.getCellAt(new Coordinate(3, -2)).getColor(),
            DiscColor.NONE);
    Assert.assertEquals(this.model.getCellAt(new Coordinate(-2, -1)).getColor(),
            DiscColor.NONE);
    Assert.assertEquals(this.model.getCellAt(new Coordinate(-1, -1)).getColor(),
            DiscColor.BLACK);
    Assert.assertEquals(this.model.getCellAt(new Coordinate(2, -1)).getColor(),
            DiscColor.NONE);
    Assert.assertEquals(this.model.getCellAt(new Coordinate(3, -1)).getColor(),
            DiscColor.NONE);
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.model.getCellAt(new Coordinate(-3, 0)).getColor());
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.model.getCellAt(new Coordinate(-2, 0)).getColor());
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.model.getCellAt(new Coordinate(0, 0)).getColor());
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.model.getCellAt(new Coordinate(2, 0)).getColor());
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.model.getCellAt(new Coordinate(3, 0)).getColor());
    Assert.assertEquals(this.model.getCellAt(new Coordinate(-3, 1)).getColor(),
            DiscColor.NONE);
    Assert.assertEquals(this.model.getCellAt(new Coordinate(-2, 1)).getColor(),
            DiscColor.NONE);
    Assert.assertEquals(this.model.getCellAt(new Coordinate(1, 1)).getColor(),
            DiscColor.BLACK);
    Assert.assertEquals(this.model.getCellAt(new Coordinate(2, 1)).getColor(),
            DiscColor.NONE);
    Assert.assertEquals(this.model.getCellAt(new Coordinate(-3, 2)).getColor(),
            DiscColor.NONE);
    Assert.assertEquals(this.model.getCellAt(new Coordinate(-2, 2)).getColor(),
            DiscColor.NONE);
    Assert.assertEquals(this.model.getCellAt(new Coordinate(-1, 2)).getColor(),
            DiscColor.NONE);
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.model.getCellAt(new Coordinate(0, 2)).getColor());
    Assert.assertEquals(this.model.getCellAt(new Coordinate(1, 2)).getColor(),
            DiscColor.NONE);
    Assert.assertEquals(this.model.getCellAt(new Coordinate(-3, 3)).getColor(),
            DiscColor.NONE);
    Assert.assertEquals(this.model.getCellAt(new Coordinate(-2, 3)).getColor(),
            DiscColor.NONE);
    Assert.assertEquals(this.model.getCellAt(new Coordinate(-1, 3)).getColor(),
            DiscColor.NONE);
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.model.getCellAt(new Coordinate(0, 3)).getColor());
    Assert.assertEquals(this.model.getCellAt(new Coordinate(-3, -3)).getColor(),
            DiscColor.NONE);
    Assert.assertEquals(this.model.getCellAt(new Coordinate(-3, -2)).getColor(),
            DiscColor.NONE);
    Assert.assertEquals(this.model.getCellAt(new Coordinate(-3, -1)).getColor(),
            DiscColor.NONE);
    Assert.assertEquals(this.model.getCellAt(new Coordinate(-2, -3)).getColor(),
            DiscColor.NONE);
    Assert.assertEquals(this.model.getCellAt(new Coordinate(-2, -2)).getColor(),
            DiscColor.NONE);
    Assert.assertEquals(this.model.getCellAt(new Coordinate(-1, -3)).getColor(),
            DiscColor.NONE);
    Assert.assertEquals(this.model.getCellAt(new Coordinate(1, 3)).getColor(),
            DiscColor.NONE);
    Assert.assertEquals(this.model.getCellAt(new Coordinate(2, 2)).getColor(),
            DiscColor.NONE);
    Assert.assertEquals(this.model.getCellAt(new Coordinate(2, 3)).getColor(),
            DiscColor.NONE);
    Assert.assertEquals(this.model.getCellAt(new Coordinate(3, 1)).getColor(),
            DiscColor.NONE);
    Assert.assertEquals(this.model.getCellAt(new Coordinate(3, 2)).getColor(),
            DiscColor.NONE);
    Assert.assertEquals(this.model.getCellAt(new Coordinate(3, 3)).getColor(),
            DiscColor.NONE);

    //verify observer methods output the correct data.
    Assert.assertEquals(this.model.getNumLayers(), 3);
    Assert.assertEquals(this.model.getTurn(), DiscColor.BLACK); //is by default the first player.
    Assert.assertFalse(this.model.isGameOver());
    Assert.assertEquals(this.model.getOtherPlayerColor(), DiscColor.WHITE);
    Assert.assertFalse(this.model.getPass());
  }

  /*
    boolean getPass() TESTS
  */

  @Test
  public void testGetPass() {
    super.testGetPass();
    this.actionableModel.playDisc(new Coordinate(-2, -1));
    Assert.assertFalse(this.model.getPass());
  }

  /*
    List<Coordinate> getNeighbors(Coordinate coord) TESTS
   */

  @Test
  public void testGetNeighborsNotEdgeCell() {
    List<Coordinate> originNeighbors = Arrays.asList(new Coordinate(-2, -2),
            new Coordinate(-2, -1), new Coordinate(-2, 1),
            new Coordinate(-1, -2), new Coordinate(-1, 1),
            new Coordinate(1, -2), new Coordinate(1, -1),
            new Coordinate(1, 1));
    Assert.assertEquals(this.model.getNeighbors(new Coordinate(-1, -1)), originNeighbors);
  }

  @Test
  public void testGetNeighborsEdgeCell() {
    List<Coordinate> neighbors = Arrays.asList(new Coordinate(-3, -2),
            new Coordinate(-2, -3), new Coordinate(-2, -2));
    Assert.assertEquals(
            this.model.getNeighbors(new Coordinate(-3, -3)), neighbors);
  }

  /*
    int getNumLayers() TESTS
   */
  @Test
  public void testGetLayersForThreeLayerGame() {
    Assert.assertEquals(this.model.getNumLayers(), 3);
  }

  /*
    ICell getCellAt(Coordinate coord) TESTS
   */

  @Test
  public void testGetCellAtTopRightCoordinate() {
    ICell cell = this.model.getCellAt(new Coordinate(-1, 1));
    Assert.assertEquals(cell.getColor(), DiscColor.WHITE);
  }

  /*
    List<Coordinate> getSandwichableNeighbors(Coordinate origin) TESTS
   */

  @Test
  public void testGetSandwichableNeighborsNoNeighbors() {
    Assert.assertTrue(this.model
            .getSandwichableNeighbors(new Coordinate(-3, -3),
                    model.getPlayerColors().get(0)).isEmpty());
  }

  @Test
  public void testGetSandwichableNeighborsOneNeighbor() {
    Assert.assertEquals(this.model.getSandwichableNeighbors(new Coordinate(-2, 1),
                    model.getPlayerColors().get(0)),
            List.of(new Coordinate(-1, 1)));
    Assert.assertEquals(this.model.getSandwichableNeighbors(new Coordinate(-2, 1),
                    model.getPlayerColors().get(0)).size(),
            1);
  }

  @Test
  public void testGetSandwichableNeighborsMoreThanOneNeighbor() {
    this.model.getCellAt(new Coordinate(-1, -1)).changeColor(DiscColor.WHITE);
    this.model.getCellAt(new Coordinate(-1, 2)).changeColor(DiscColor.WHITE);
    this.model.getCellAt(new Coordinate(1, -1)).changeColor(DiscColor.BLACK);
    Assert.assertEquals(this.model.getSandwichableNeighbors(new Coordinate(2, -1),
                    model.getPlayerColors().get(1)),
            List.of(new Coordinate(1, -1), new Coordinate(1, 1)));
    Assert.assertEquals(this.model.getSandwichableNeighbors(new Coordinate(2, -1),
                    model.getPlayerColors().get(1)).size(),
            2);
  }

  /*
    List<Coordinate> getLikeNeighbors(Coordinate origin) TESTS
  */

  @Test
  public void testGetLikeNeighborsNoNeighbors() {
    Assert.assertTrue(this.model.getLikeNeighbors(new Coordinate(-2, -2),
            model.getPlayerColors().get(1)).isEmpty());
  }

  @Test
  public void testGetLikeNeighborsOneNeighbor() {
    List<Coordinate> list = this.model.getLikeNeighbors(new Coordinate(2, -1),
            model.getPlayerColors().get(1));
    Assert.assertEquals(list, List.of(new Coordinate(1, -1)));
    Assert.assertEquals(list.size(), 1);
  }

  @Test
  public void testGetLikeNeighborsMoreThanOneNeighbor() {
    List<Coordinate> list = this.model.getLikeNeighbors(new Coordinate(-1, -1),
            model.getPlayerColors().get(1));
    Assert.assertEquals(list,
            List.of(new Coordinate(-1, 1), new Coordinate(1, -1)));
    Assert.assertEquals(list.size(), 2);
  }

  /*
    List<Coordinate> getOppositeNeighbors(Coordinate origin) TESTS
  */

  @Test
  public void testGetOppositeNeighborsNoNeighbors() {
    List<Coordinate> list = this.model.getOppositeNeighbors(new Coordinate(2, 2),
            model.getPlayerColors().get(0));
    Assert.assertTrue(list.isEmpty());
  }

  @Test
  public void testGetOppositeNeighborsOneNeighbor() {
    List<Coordinate> list = this.model.getOppositeNeighbors(new Coordinate(2, -1),
            model.getPlayerColors().get(1));
    Assert.assertEquals(list, List.of(new Coordinate(1, 1)));
    Assert.assertEquals(list.size(), 1);
  }

  @Test
  public void testGetOppositeNeighborsMoreThanOneNeighbor() {
    List<Coordinate> list = this.model.getOppositeNeighbors(new Coordinate(-1, -1),
            model.getPlayerColors().get(0));
    Assert.assertEquals(list,
            List.of(new Coordinate(-1, 1), new Coordinate(1, -1)));
    Assert.assertEquals(list.size(), 2);
  }

  /*
    boolean isGameOver() TESTS
   */

  @Test
  public void testIsGameOverTrueNoMorePossibleMoves() {
    this.actionableModel.getCellAt(new Coordinate(-2, 1)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-2, 2)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-1, 2)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-3, 3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(2, -2)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(3, -3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(1, 2)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(1, 3)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(-2, -2)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(-3, -3)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(2, 2)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(3, 3)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(1, -2)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(1, -3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-2, 3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-1, 3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(2, 3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(3, -2)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(3, -1)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(3, 1)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(3, 2)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(2, -3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(2, -1)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(2, 1)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(-1, -2)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(-1, -3)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(-2, -3)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(-2, -1)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(-3, -2)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(-3, -1)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(-3, 1)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(-3, 2)).changeColor(DiscColor.BLACK);
    Assert.assertTrue(this.model.isGameOver());
  }

  @Test
  public void testIsGameOverTruePlayerCapturedAllCells() {
    this.actionableModel.getCellAt(new Coordinate(-2, 1)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-2, 2)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-1, 2)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-3, 3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(2, -2)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(3, -3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(1, 2)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(1, 3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-2, -2)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-3, -3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(2, 2)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(3, 3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(1, -2)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(1, -3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-2, 3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-1, 3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(2, 3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(3, -2)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(3, -1)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(3, 1)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(3, 2)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(2, -3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(2, -1)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(2, 1)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-1, -2)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-1, -3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-2, -3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-2, -1)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-3, -2)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-3, -1)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-3, 1)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-3, 2)).changeColor(DiscColor.WHITE);
    Assert.assertTrue(this.model.isGameOver());
  }

  /*
    DiscColor getWinner() TESTS
   */

  @Test
  public void testGetWinnerBlackWins() {
    this.actionableModel.getCellAt(new Coordinate(-2, 1)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-2, 2)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-1, 2)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-3, 3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(2, -2)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(3, -3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(1, 2)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(1, 3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-2, -2)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-3, -3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(2, 2)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(3, 3)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(1, -2)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(1, -3)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(-2, 3)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(-1, 3)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(2, 3)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(3, -2)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(3, -1)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(3, 1)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(3, 2)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(2, -3)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(2, -1)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(2, 1)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(-1, -2)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(-1, -3)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(-2, -3)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(-2, -1)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(-3, -2)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(-3, -1)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(-3, 1)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(-3, 2)).changeColor(DiscColor.BLACK);
    Assert.assertTrue(this.model.isGameOver());
    Assert.assertEquals(this.model.getGameState(), Status.WON);
    Assert.assertTrue(this.model.getPlayerScore(DiscColor.BLACK) >
            this.model.getPlayerScore(DiscColor.WHITE));
    Assert.assertEquals(this.model.getWinner(), Optional.of(DiscColor.BLACK));
  }

  @Test
  public void testWhitePlayerCapturesAllCellsOnBoardAndWinsWhiteWins() {
    this.actionableModel.getCellAt(new Coordinate(-2, 1)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-2, 2)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-1, 2)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(-3, 3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(2, -2)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(3, -3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(1, 2)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(1, 3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-2, -2)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(-3, -3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(2, 2)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(3, 3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(1, -2)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(1, -3)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(-2, 3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-1, 3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(2, 3)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(3, -2)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(3, -1)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(3, 1)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(3, 2)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(2, -3)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(2, -1)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(2, 1)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-1, -2)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(-1, -3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-2, -3)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(-2, -1)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-3, -2)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-3, -1)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(-3, 1)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-3, 2)).changeColor(DiscColor.BLACK);
    Assert.assertTrue(this.model.isGameOver());
    Assert.assertEquals(this.model.getGameState(), Status.WON);
    Assert.assertTrue(this.model.getPlayerScore(DiscColor.WHITE) >
            this.model.getPlayerScore(DiscColor.BLACK));
    Assert.assertEquals(this.model.getWinner(), Optional.of(DiscColor.WHITE));
  }

  /*
    int getPlayerScore(DiscColor player) TESTS
   */

  @Test
  public void testGetPlayerScoreAtStartOfGame() {
    Assert.assertEquals(this.model.getPlayerScore(DiscColor.BLACK), 2);
    Assert.assertEquals(this.model.getPlayerScore(DiscColor.WHITE), 2);
  }

  @Test
  public void testGetPlayerScoreMidGame() {
    this.model.getCellAt(new Coordinate(3, -3)).changeColor(DiscColor.WHITE);
    this.model.getCellAt(new Coordinate(1, -3)).changeColor(DiscColor.WHITE);
    this.model.getCellAt(new Coordinate(2, -3)).changeColor(DiscColor.BLACK);

    Assert.assertEquals(this.model.getPlayerScore(DiscColor.BLACK), 3);
    Assert.assertEquals(this.model.getPlayerScore(DiscColor.WHITE), 4);
  }

  /*
    HashMap<Coordinate, ICell> getAllCoords() TESTS
   */

  @Test
  public void testGetAllCords() {
    HashMap<Coordinate, ICell> coords = this.actionableModel.getCopyOfAllCoords();
    Assert.assertEquals(coords.size(), 36);
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.actionableModel.getCellAt(new Coordinate(0, 0)));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.actionableModel.getCellAt(new Coordinate(-1, 0)));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.actionableModel.getCellAt(new Coordinate(1, 0)));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.actionableModel.getCellAt(new Coordinate(0, -1)));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.actionableModel.getCellAt(new Coordinate(0, -3)));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.actionableModel.getCellAt(new Coordinate(3, 0)));
    Assert.assertEquals(coords.get(new Coordinate(-1, 1)).getColor(), DiscColor.WHITE);
    Assert.assertEquals(coords.get(new Coordinate(1, -1)).getColor(), DiscColor.WHITE);
    Assert.assertEquals(coords.get(new Coordinate(-1, -1)).getColor(), DiscColor.BLACK);
    Assert.assertEquals(coords.get(new Coordinate(1, 1)).getColor(), DiscColor.BLACK);
    Assert.assertEquals(coords.get(new Coordinate(1, -3)).getColor(), DiscColor.NONE);
    Assert.assertEquals(coords.get(new Coordinate(2, -3)).getColor(), DiscColor.NONE);
    Assert.assertEquals(coords.get(new Coordinate(3, -3)).getColor(), DiscColor.NONE);
    Assert.assertEquals(coords.get(new Coordinate(-1, -2)).getColor(), DiscColor.NONE);
    Assert.assertEquals(coords.get(new Coordinate(1, -2)).getColor(), DiscColor.NONE);
    Assert.assertEquals(coords.get(new Coordinate(2, -2)).getColor(), DiscColor.NONE);
    Assert.assertEquals(coords.get(new Coordinate(3, -2)).getColor(), DiscColor.NONE);
    Assert.assertEquals(coords.get(new Coordinate(-2, -1)).getColor(), DiscColor.NONE);
    Assert.assertEquals(coords.get(new Coordinate(2, -1)).getColor(), DiscColor.NONE);
    Assert.assertEquals(coords.get(new Coordinate(3, -1)).getColor(), DiscColor.NONE);
    Assert.assertEquals(coords.get(new Coordinate(-3, 1)).getColor(), DiscColor.NONE);
    Assert.assertEquals(coords.get(new Coordinate(-2, 1)).getColor(), DiscColor.NONE);
    Assert.assertEquals(coords.get(new Coordinate(2, 1)).getColor(), DiscColor.NONE);
    Assert.assertEquals(coords.get(new Coordinate(-3, 2)).getColor(), DiscColor.NONE);
    Assert.assertEquals(coords.get(new Coordinate(-2, 2)).getColor(), DiscColor.NONE);
    Assert.assertEquals(coords.get(new Coordinate(-1, 2)).getColor(), DiscColor.NONE);
    Assert.assertEquals(coords.get(new Coordinate(1, 2)).getColor(), DiscColor.NONE);
    Assert.assertEquals(coords.get(new Coordinate(-3, 3)).getColor(), DiscColor.NONE);
    Assert.assertEquals(coords.get(new Coordinate(-2, 3)).getColor(), DiscColor.NONE);
    Assert.assertEquals(coords.get(new Coordinate(-1, 3)).getColor(), DiscColor.NONE);
    Assert.assertEquals(coords.get(new Coordinate(3, 3)).getColor(), DiscColor.NONE);
    Assert.assertEquals(coords.get(new Coordinate(3, -3)).getColor(), DiscColor.NONE);
    Assert.assertEquals(coords.get(new Coordinate(-3, 3)).getColor(), DiscColor.NONE);
    Assert.assertEquals(coords.get(new Coordinate(-3, -3)).getColor(), DiscColor.NONE);
  }

  /*
    boolean isCorner() TESTS
  */

  @Test
  public void testIsCorner() {
    Assert.assertTrue(this.model.isCorner(new Coordinate(-3, -3)));
    Assert.assertTrue(this.model.isCorner(new Coordinate(-3, 3)));
    Assert.assertTrue(this.model.isCorner(new Coordinate(3, -3)));
    Assert.assertTrue(this.model.isCorner(new Coordinate(3, 3)));
    Assert.assertFalse(this.model.isCorner(new Coordinate(-3, 1)));
    Assert.assertFalse(this.model.isCorner(new Coordinate(-3, 2)));
    Assert.assertFalse(this.model.isCorner(new Coordinate(-2, -1)));
    Assert.assertFalse(this.model.isCorner(new Coordinate(-2, 3)));
    Assert.assertFalse(this.model.isCorner(new Coordinate(-1, -2)));
    Assert.assertFalse(this.model.isCorner(new Coordinate(-1, 3)));
    Assert.assertFalse(this.model.isCorner(new Coordinate(1, -3)));
    Assert.assertFalse(this.model.isCorner(new Coordinate(1, 2)));
    Assert.assertFalse(this.model.isCorner(new Coordinate(2, -3)));
    Assert.assertFalse(this.model.isCorner(new Coordinate(2, 1)));
    Assert.assertFalse(this.model.isCorner(new Coordinate(3, -2)));
    Assert.assertFalse(this.model.isCorner(new Coordinate(3, -1)));
    Assert.assertFalse(this.model.isCorner(new Coordinate(-2, 2)));
  }

  /*
     void playDisc(Coordinate coord) TESTS
    */
  @Test(expected = IllegalArgumentException.class)
  public void testPlayDiscNullIllegalArgumentException() {
    this.model.playDisc(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayDiscInvalidCoordinateIllegalArgumentException() {
    this.model.playDisc(new Coordinate(-5, -3));
  }

  @Test(expected = IllegalStateException.class)
  public void testPlayDiscInvalidMovePlayAtNotNONECellIllegalStateException() {
    this.model.playDisc(new Coordinate(-1, 1));
  }

  @Test(expected = IllegalStateException.class)
  public void testPlayDiscInvalidMovePlayAtNoNeighborCellIllegalStateException() {
    this.model.playDisc(new Coordinate(-3, 3));
  }

  @Test
  public void testPlayDiscLikeNeighbor() {
    this.model.playDisc(new Coordinate(-2, -1));
    Assert.assertEquals("X",
            this.model.getCellAt(new Coordinate(-2, -1)).getColor().toString());
    Assert.assertEquals("X",
            this.model.getCellAt(new Coordinate(-1, -1)).getColor().toString());
  }

  @Test
  public void testLikeNeighborsAndSandwich() {
    this.model.pass();
    this.model.playDisc(new Coordinate(-2, -1));
    Assert.assertEquals("O",
            this.model.getCellAt(new Coordinate(-2, -1)).getColor().toString());
    Assert.assertEquals("O",
            this.model.getCellAt(new Coordinate(-1, -1)).getColor().toString());
  }

  @Test
  public void testPlayDiscOneDirectionSingleSandwich() {
    this.model.getCellAt(new Coordinate(-1, 1)).changeColor(DiscColor.BLACK);
    this.model.pass();
    this.model.playDisc(new Coordinate(-2, -1));
    Assert.assertEquals("O",
            this.model.getCellAt(new Coordinate(-2, -1)).getColor().toString());
    Assert.assertEquals("O",
            this.model.getCellAt(new Coordinate(-1, -1)).getColor().toString());
  }

  @Test
  public void testPlayDiscMoreThanOneDirectionSingleSandwich() {
    this.model.getCellAt(new Coordinate(-1, -1)).changeColor(DiscColor.WHITE);
    this.model.getCellAt(new Coordinate(-1, 2)).changeColor(DiscColor.WHITE);
    this.model.getCellAt(new Coordinate(1, -1)).changeColor(DiscColor.BLACK);
    this.model.pass();
    // THIS IS WHERE DOUBLE SANDWICH HAPPENS
    this.model.playDisc(new Coordinate(2, -1));
    Assert.assertEquals("O",
            this.model.getCellAt(new Coordinate(2, -1)).getColor().toString());
    Assert.assertEquals("O",
            this.model.getCellAt(new Coordinate(-1, -1)).getColor().toString());
    Assert.assertEquals("O",
            this.model.getCellAt(new Coordinate(1, 1)).getColor().toString());
  }

  @Test
  public void testPlayDiscOneDirectionMultipleSandwich() {
    this.model.getCellAt(new Coordinate(1, -1)).changeColor(DiscColor.BLACK);
    this.model.getCellAt(new Coordinate(-1, 1)).changeColor(DiscColor.BLACK);
    this.model.getCellAt(new Coordinate(2, -1)).changeColor(DiscColor.WHITE);
    this.model.pass();
    this.model.playDisc(new Coordinate(-2, -1));
    Assert.assertEquals("O",
            this.model.getCellAt(new Coordinate(-2, -1)).getColor().toString());
    Assert.assertEquals("O",
            this.model.getCellAt(new Coordinate(-1, -1)).getColor().toString());
    Assert.assertEquals("O",
            this.model.getCellAt(new Coordinate(1, -1)).getColor().toString());
  }

  @Test
  public void testPlayDiscMoreThanOneDirectionMultipleSandwich() {
    this.model.getCellAt(new Coordinate(1, -1)).changeColor(DiscColor.BLACK);
    this.model.getCellAt(new Coordinate(-1, 1)).changeColor(DiscColor.BLACK);
    this.model.getCellAt(new Coordinate(1, 2)).changeColor(DiscColor.BLACK);
    this.model.getCellAt(new Coordinate(2, -1)).changeColor(DiscColor.WHITE);
    this.model.getCellAt(new Coordinate(2, 3)).changeColor(DiscColor.WHITE);
    this.model.pass();
    this.model.playDisc(new Coordinate(-2, -1));
    Assert.assertEquals("O",
            this.model.getCellAt(new Coordinate(-2, -1)).getColor().toString());
    Assert.assertEquals("O",
            this.model.getCellAt(new Coordinate(-1, -1)).getColor().toString());
    Assert.assertEquals("O",
            this.model.getCellAt(new Coordinate(1, -1)).getColor().toString());
    Assert.assertEquals("O",
            this.model.getCellAt(new Coordinate(-1, 1)).getColor().toString());
    Assert.assertEquals("O",
            this.model.getCellAt(new Coordinate(1, 2)).getColor().toString());
  }

  @Test
  public void testPlayDiscAutoPassNoPossibleMovesForNextPlayer() {
    Assert.assertEquals(this.model.getTurn(), DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(-2, 1)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-2, 2)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-1, 2)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-3, 3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(2, -2)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(3, -3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(1, 2)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(1, 3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-2, -2)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-3, -3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(2, 2)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(3, 3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(1, -2)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(1, -3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-2, 3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-1, 3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(2, 3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(3, -2)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(3, -1)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(3, 1)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(2, -1)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(2, 1)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-1, -2)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-1, -3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-2, -3)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-3, -2)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-3, -1)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-3, 1)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(-3, 2)).changeColor(DiscColor.WHITE);
    Assert.assertEquals(this.model.getTurn(), DiscColor.BLACK);
    this.model.playDisc(new Coordinate(-2, -1));
    Assert.assertEquals(this.model.getTurn(), DiscColor.WHITE);
    this.model.playDisc(new Coordinate(2, -3));
    // next turn would be black, but has no moves, so next turn is white
    Assert.assertEquals(this.model.getTurn(), DiscColor.WHITE);
  }
}
