package hw09tests.strategies;

import org.junit.Assert;
import org.junit.Test;

import model.board.Coordinate;
import model.board.ReadOnlyModel;
import model.cell.DiscColor;
import model.cell.ICell;
import strategy.AbstractSingleStrategy;
import strategy.PrioritizeCorners;
import strategy.TryToBlockSingleStrategy;

/**
 * Tests for the TryToBlockSingleStrategy strategy.
 */
public class TryToBlockSingleSquareStrategyTests extends AbstractSquareStrategyTests {

  /**
   * Gets the try to block single strategy to be used for testing.
   * We are using the AvoidCornerNeighbors strategy as the strategy to block for testing purposes.
   * These tests are created to make sure that the result of the getStrategyPossibleMoves() method
   * is the intersection of the result of the getStrategyPossibleMoves() method of the
   * AvoidCornerNeighbors strategy for the opposing player color and this player's possible moves.
   *
   * @param player which player is trying to make a move
   * @param model  which model to play on
   * @return the strategy to be used for testing.
   */
  @Override
  protected AbstractSingleStrategy getStrategy(DiscColor player, ReadOnlyModel model) {
    return new TryToBlockSingleStrategy(new PrioritizeCorners(player, model));
  }

  /*
    CONSTRUCTOR TESTS:
   */

  @Test(expected = IllegalArgumentException.class)
  public void testNullStrategyConstructor() {
    new TryToBlockSingleStrategy(null);
  }

  /*
    List<Coordinate> getStrategyPossibleMoves() TESTS:
   */

  @Test
  public void testGetStrategyPossibleMovesEmptyForOtherPlayerAndEmptyForThisPlayer() {
    //modify the board to adapt to testing scenario.
    for (Coordinate coord : this.actionableModel.getCopyOfAllCoords().keySet()) {
      ICell cell = this.actionableModel.getCellAt(coord);
      if (cell.getColor() == DiscColor.NONE) {
        this.actionableModel.getCellAt(coord).changeColor(DiscColor.BLACK);
      }
    }

    DiscColor opposingPlayer = this.strategy.getOpposingPlayerColor();

    Assert.assertTrue(this.strategy.getAllPossiblePlayerMoves(this.player).isEmpty());
    Assert.assertTrue(this.strategy.getAllPossiblePlayerMoves(opposingPlayer).isEmpty());
    Assert.assertTrue(this.strategy.getStrategyPossibleMoves(
        this.strategy.getAllPossiblePlayerMoves(this.player), this.player).isEmpty());
  }
}
