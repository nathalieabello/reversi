package oldtests.strategy;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import hw09tests.strategies.AbstractModelStrategyTests;
import model.board.Coordinate;
import model.board.HexReversiModel;
import model.board.IModel;
import model.board.ReadOnlyModel;
import model.cell.DiscColor;
import strategy.AIStrategy;

/**
 * The test class for the AbstractStrategy class.
 */
public abstract class AbstractHexStrategyTests extends AbstractModelStrategyTests {
  protected Appendable a;
  protected IModel actionableModel;
  protected ReadOnlyModel readOnlyMock;
  protected DiscColor player;
  protected AIStrategy strategy;

  /**
   * Gets the strategy to be used for testing.
   *
   * @param player which player is trying to make a move
   * @param model  which model to play on
   * @return the strategy to be used for testing.
   */
  protected abstract AIStrategy getStrategy(DiscColor player, ReadOnlyModel model);

  @Before
  public void init() {
    this.a = new StringBuilder();
    this.player = DiscColor.BLACK;
    this.actionableModel = new HexReversiModel.HexBuilder().setLayers(3).build();
    this.actionableModel.startGame();
    this.readOnlyMock = new MockReadOnlyModel(this.a, this.actionableModel);

    this.strategy = this.getStrategy(this.player, this.readOnlyMock);
  }

  @Override
  public IModel getModel() {
    return this.actionableModel;
  }

  /*
    CONSTRUCTOR TESTS:
   */

  @Test(expected = IllegalArgumentException.class)
  public void testNullPlayerConstructor() {
    this.getStrategy(null, this.readOnlyMock);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullModelConstructor() {
    this.getStrategy(this.player, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayerNoneConstructor() {
    this.getStrategy(DiscColor.NONE, this.readOnlyMock);
  }

  /*
    Optional<Coordinate> move() TESTS:
   */

  @Test
  public void testMoveReturnsEmptyOptionalIfNoPossibleMoves() {
    this.actionableModel.pass();
    this.actionableModel.playDisc(new Coordinate(0, 0));
    this.actionableModel.pass();
    this.actionableModel.playDisc(new Coordinate(0, -2));
    this.actionableModel.pass();
    this.actionableModel.playDisc(new Coordinate(2, 0));
    this.actionableModel.pass();
    this.actionableModel.playDisc(new Coordinate(-2, 2));
    Assert.assertTrue(this.strategy.getAllPossiblePlayerMoves(this.player).isEmpty());
    Assert.assertTrue(this.strategy.move().isEmpty());
  }

  @Test
  public void testMoveReturnsFirstPossibleMove() {
    //allow a corner move:
    this.actionableModel.getCellAt(new Coordinate(2, 0)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(1, 1)).changeColor(DiscColor.WHITE);
    this.actionableModel.getCellAt(new Coordinate(2, 1)).changeColor(DiscColor.WHITE);

    List<Coordinate> possibleStrategyMoves = this.strategy
            .getStrategyPossibleMoves(this.strategy.getAllPossiblePlayerMoves(this.player),
                    this.player);

    Assert.assertFalse(possibleStrategyMoves.isEmpty());
    Assert.assertEquals(Optional.of(possibleStrategyMoves.get(0)), this.strategy.move());
  }

  /*
    List<Coordinate> getStrategyPossibleMoves(List<Coordinate> candidateMoves,
                                              DiscColor player) TESTS:
  */

  @Test(expected = IllegalArgumentException.class)
  public void testGetStrategyPossibleMovesNullCandidateMovesIllegalArgumentException() {
    this.strategy.getStrategyPossibleMoves(null, this.player);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetStrategyPossibleMovesContainsNullCandidateMovesIllegalArgumentException() {
    this.strategy.getStrategyPossibleMoves(
            Arrays.asList(new Coordinate(0, 0), null), this.player);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetStrategyPossibleMovesContainsInvalidCandidateMovesIllegalArgumentException() {
    this.strategy.getStrategyPossibleMoves(
            List.of(new Coordinate(-2, 0)), this.player);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetStrategyPossibleMovesNullPlayerIllegalArgumentException() {
    this.strategy.getStrategyPossibleMoves(new ArrayList<>(), null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetStrategyPossibleMovesNONEPlayerIllegalArgumentException() {
    this.strategy.getStrategyPossibleMoves(new ArrayList<>(), DiscColor.NONE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetStrategyPossibleMovesInvalidPlayerIllegalArgumentException() {
    this.strategy.getStrategyPossibleMoves(new ArrayList<>(), DiscColor.MAGENTA);
  }

  @Test
  public void testGetStrategyPossibleMovesGivenEmptyList() {
    Assert.assertTrue(this.strategy
            .getStrategyPossibleMoves(new ArrayList<>(), this.player).isEmpty());
  }

  /*
    List<Coordinate> getAllPossiblePlayerMoves(DiscColor player) TESTS:
   */

  @Test
  public void testGetAllPossiblePlayerMoves() {
    List<Coordinate> allPossiblePlayerMoves = this.strategy.getAllPossiblePlayerMoves(this.player);

    Assert.assertTrue(this.a.toString().contains(
            "get cell at: x=-1, y=-2\n" +
            "get like neighbors of X at: x=-1, y=-2\n" +
            "get sandwichable neighbors of X at: x=-1, y=-2\n"));
    Assert.assertTrue(this.a.toString().contains(
            "get cell at: x=-2, y=2\n" +
            "get like neighbors of X at: x=-2, y=2\n"));
    Assert.assertTrue(this.a.toString().contains(
            "get cell at: x=-1, y=0\n"));
    Assert.assertTrue(this.a.toString().contains(
            "get cell at: x=0, y=-2\n" +
            "get like neighbors of X at: x=0, y=-2\n"));
    Assert.assertTrue(this.a.toString().contains(
            "get cell at: x=-3, y=2\n" +
            "get like neighbors of X at: x=-3, y=2\n" +
            "get sandwichable neighbors of X at: x=-3, y=2\n"));
    Assert.assertTrue(this.a.toString().contains(
            "get cell at: x=1, y=-2\n" +
            "get like neighbors of X at: x=1, y=-2\n"));
    Assert.assertTrue(this.a.toString().contains(
            "get cell at: x=2, y=-2\n" +
            "get like neighbors of X at: x=2, y=-2\n" +
            "get sandwichable neighbors of X at: x=2, y=-2\n"));
    Assert.assertTrue(this.a.toString().contains(
            "get cell at: x=3, y=-2\n" +
            "get like neighbors of X at: x=3, y=-2\n" +
            "get sandwichable neighbors of X at: x=3, y=-2\n"));
    Assert.assertTrue(this.a.toString().contains(
            "get cell at: x=2, y=1\n" +
            "get like neighbors of X at: x=2, y=1\n" +
            "get sandwichable neighbors of X at: x=2, y=1\n"));
    Assert.assertTrue(this.a.toString().contains(
            "get cell at: x=1, y=1\n" +
            "get like neighbors of X at: x=1, y=1\n"));
    Assert.assertTrue(this.a.toString().contains(
            "get cell at: x=-1, y=3\n" +
            "get like neighbors of X at: x=-1, y=3\n" +
            "get sandwichable neighbors of X at: x=-1, y=3\n"));
    Assert.assertTrue(this.a.toString().contains(
            "get cell at: x=0, y=1\n"));
    Assert.assertTrue(this.a.toString().contains(
            "get cell at: x=0, y=3\n" +
            "get like neighbors of X at: x=0, y=3\n" +
            "get sandwichable neighbors of X at: x=0, y=3\n"));
    Assert.assertTrue(this.a.toString().contains(
            "get cell at: x=-1, y=1\n"));
    Assert.assertTrue(this.a.toString().contains(
            "get cell at: x=-2, y=-1\n" +
            "get like neighbors of X at: x=-2, y=-1\n" +
            "get sandwichable neighbors of X at: x=-2, y=-1\n"));
    Assert.assertTrue(this.a.toString().contains(
            "get cell at: x=-3, y=3\n" +
            "get like neighbors of X at: x=-3, y=3\n" +
            "get sandwichable neighbors of X at: x=-3, y=3\n"));
    Assert.assertTrue(this.a.toString().contains(
            "get cell at: x=-2, y=1\n" +
            "get like neighbors of X at: x=-2, y=1\n"));
    Assert.assertTrue(this.a.toString().contains(
            "get cell at: x=-1, y=-1\n" +
            "get like neighbors of X at: x=-1, y=-1\n"));
    Assert.assertTrue(this.a.toString().contains(
            "get cell at: x=0, y=-3\n" +
            "get like neighbors of X at: x=0, y=-3\n" +
            "get sandwichable neighbors of X at: x=0, y=-3\n"));
    Assert.assertTrue(this.a.toString().contains(
            "get cell at: x=-2, y=3\n" +
            "get like neighbors of X at: x=-2, y=3\n" +
            "get sandwichable neighbors of X at: x=-2, y=3\n"));
    Assert.assertTrue(this.a.toString().contains(
            "get cell at: x=-3, y=1\n" +
            "get like neighbors of X at: x=-3, y=1\n" +
            "get sandwichable neighbors of X at: x=-3, y=1\n"));
    Assert.assertTrue(this.a.toString().contains(
            "get cell at: x=0, y=-1\n"));
    Assert.assertTrue(this.a.toString().contains(
            "get cell at: x=1, y=-1\n"));
    Assert.assertTrue(this.a.toString().contains(
            "get cell at: x=2, y=-3\n" +
            "get like neighbors of X at: x=2, y=-3\n" +
            "get sandwichable neighbors of X at: x=2, y=-3\n"));
    Assert.assertTrue(this.a.toString().contains(
            "get cell at: x=3, y=0\n" +
            "get like neighbors of X at: x=3, y=0\n" +
            "get sandwichable neighbors of X at: x=3, y=0\n"));
    Assert.assertTrue(this.a.toString().contains(
            "get cell at: x=2, y=-1\n" +
            "get like neighbors of X at: x=2, y=-1\n"));
    Assert.assertTrue(this.a.toString().contains(
            "get cell at: x=1, y=-3\n" +
            "get like neighbors of X at: x=1, y=-3\n" +
            "get sandwichable neighbors of X at: x=1, y=-3\n"));
    Assert.assertTrue(this.a.toString().contains(
            "get cell at: x=2, y=0\n" +
            "get like neighbors of X at: x=2, y=0\n"));
    Assert.assertTrue(this.a.toString().contains(
            "get cell at: x=3, y=-1\n" +
            "get like neighbors of X at: x=3, y=-1\n" +
            "get sandwichable neighbors of X at: x=3, y=-1\n"));
    Assert.assertTrue(this.a.toString().contains(
            "get cell at: x=1, y=0\n"));
    Assert.assertTrue(this.a.toString().contains(
            "get cell at: x=1, y=2\n" +
            "get like neighbors of X at: x=1, y=2\n" +
            "get sandwichable neighbors of X at: x=1, y=2\n"));
    Assert.assertTrue(this.a.toString().contains(
            "get cell at: x=3, y=-3\n" +
            "get like neighbors of X at: x=3, y=-3\n" +
            "get sandwichable neighbors of X at: x=3, y=-3\n"));
    Assert.assertTrue(this.a.toString().contains(
            "get cell at: x=0, y=0\n" +
            "get like neighbors of X at: x=0, y=0\n"));
    Assert.assertTrue(this.a.toString().contains(
            "get cell at: x=-3, y=0\n" +
            "get like neighbors of X at: x=-3, y=0\n" +
            "get sandwichable neighbors of X at: x=-3, y=0\n"));
    Assert.assertTrue(this.a.toString().contains(
            "get cell at: x=0, y=2\n" +
            "get like neighbors of X at: x=0, y=2\n" +
            "get sandwichable neighbors of X at: x=0, y=2\n"));
    Assert.assertTrue(this.a.toString().contains(
            "get cell at: x=-2, y=0\n" +
            "get like neighbors of X at: x=-2, y=0\n" +
            "get sandwichable neighbors of X at: x=-2, y=0\n"));
    Assert.assertTrue(this.a.toString().contains(
            "get cell at: x=-1, y=2\n" +
            "get like neighbors of X at: x=-1, y=2\n"));

    Assert.assertEquals(allPossiblePlayerMoves.size(), 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetAllPossiblePlayerMovesNullPlayerIllegalArgumentException() {
    this.strategy.getAllPossiblePlayerMoves(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetAllPossiblePlayerMovesNONEPlayerIllegalArgumentException() {
    this.strategy.getAllPossiblePlayerMoves(DiscColor.NONE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetAllPossiblePlayerMovesInvalidPlayerIllegalArgumentException() {
    this.strategy.getAllPossiblePlayerMoves(DiscColor.MAGENTA);
  }

  /*
    DiscColor getOpposingPlayerColor() TESTS:
   */
  @Test
  public void testGetOpposingPlayerColor() {
    Assert.assertEquals(this.strategy.getOpposingPlayerColor(), DiscColor.WHITE);
    this.player = DiscColor.WHITE;
    this.strategy = this.getStrategy(this.player, this.readOnlyMock);
    Assert.assertEquals(this.strategy.getOpposingPlayerColor(), DiscColor.BLACK);
  }
}
