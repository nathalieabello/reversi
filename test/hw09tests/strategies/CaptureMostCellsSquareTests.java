package hw09tests.strategies;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import model.board.Coordinate;
import model.board.ReadOnlyModel;
import model.cell.DiscColor;
import model.cell.ICell;
import strategy.AbstractSingleStrategy;
import strategy.CaptureMostCells;

/**
 * Tests for the CaptureMostCells strategy.
 */
public class CaptureMostCellsSquareTests extends AbstractSquareStrategyTests {
  @Override
  protected AbstractSingleStrategy getStrategy(DiscColor player, ReadOnlyModel model) {
    return new CaptureMostCells(player, model);
  }

  /*
  getStrategyPossibleMoves() TESTS:
 */

  @Test
  public void testGetStrategyPossibleMovesEmptyNoSandwiches() {
    //change board to fit testing purposes: change two corners to pink, change all other cells to
    // black except for the center cell.
    for (Coordinate c : this.actionableModel.getCopyOfAllCoords().keySet()) {
      ICell cellToChange = this.actionableModel.getCellAt(c);
      if (c.equals(new Coordinate(-3, 0))
              || c.equals(new Coordinate(0, -3))) {
        cellToChange.changeColor(DiscColor.WHITE);
      } else if (cellToChange.getColor() != DiscColor.BLACK
              && !c.equals(new Coordinate(0, 0))) {
        cellToChange.changeColor(DiscColor.BLACK);
      }
    }

    Assert.assertTrue(this.strategy
            .getStrategyPossibleMoves(this.strategy
                    .getAllPossiblePlayerMoves(this.player), this.player).isEmpty());

  }

  @Test
  public void testGetStrategyPossibleMovesOnePossibleSandwich() {
    //change board to fit testing purposes
    this.actionableModel.getCellAt(new Coordinate(-1, 1)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(1, -2)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(1, -3)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(2, -2)).changeColor(DiscColor.BLACK);
    List<Coordinate> possibleMoves = this.strategy
            .getStrategyPossibleMoves(this.strategy
                    .getAllPossiblePlayerMoves(this.player), this.player);
    Assert.assertEquals(possibleMoves.size(), 1);
    Assert.assertEquals(possibleMoves.get(0), new Coordinate(2, -1));
  }

  @Test
  public void testGetStrategyPossibleMovesMoreThanOneSandwichDescendingOrderByWeight() {
    //change board to fit testing purposes
    this.actionableModel.getCellAt(new Coordinate(-1, 2)).changeColor(DiscColor.WHITE);

    List<Coordinate> allPossibleMoves = this.strategy
            .getAllPossiblePlayerMoves(this.player);

    List<Coordinate> strategyPossibleMoves = this.strategy
            .getStrategyPossibleMoves(allPossibleMoves, this.player);

    Assert.assertEquals(strategyPossibleMoves.size(), 5);
    Coordinate winnerCoord = strategyPossibleMoves.get(0);
    Coordinate runnerUpCoord = strategyPossibleMoves.get(1);
    Assert.assertEquals(winnerCoord, new Coordinate(-1, 3));
    Assert.assertEquals(runnerUpCoord, new Coordinate(-2, 1));
    Assert.assertEquals(this.actionableModel
            .getSandwichableNeighbors(winnerCoord, this.player).size(), 2);
    Assert.assertEquals(this.actionableModel
            .getSandwichableNeighbors(runnerUpCoord, this.player).size(), 1);
  }

  @Test
  public void testGetStrategyPossibleMovesMoreThanOneSandwichAscendingOrderByCoordinate() {
    //change board to fit testing purposes
    this.actionableModel.getCellAt(new Coordinate(-1, 1)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(1, -2)).changeColor(DiscColor.BLACK);
    this.actionableModel.getCellAt(new Coordinate(1, -3)).changeColor(DiscColor.BLACK);
    List<Coordinate> allPossibleMoves = this.strategy
            .getAllPossiblePlayerMoves(this.player);
    List<Coordinate> strategyPossibleMoves = this.strategy
            .getStrategyPossibleMoves(allPossibleMoves, this.player);
    Assert.assertEquals(strategyPossibleMoves.size(), 2);
    Coordinate winnerCoord = strategyPossibleMoves.get(0);
    Coordinate runnerUpCoord = strategyPossibleMoves.get(1);
    Assert.assertEquals(winnerCoord, new Coordinate(2, -2));
    Assert.assertEquals(runnerUpCoord, new Coordinate(2, -1));
    Assert.assertEquals(this.actionableModel
            .getSandwichableNeighbors(winnerCoord, this.player).size(), 1);
    Assert.assertEquals(this.actionableModel
            .getSandwichableNeighbors(runnerUpCoord, this.player).size(), 1);
  }

  @Test
  public void testGetStrategyPossibleMovesMoreThanOneSandwichDescOrderByWeightAscByCoordinate() {
    //change board to fit testing purposes
    this.actionableModel.getCellAt(new Coordinate(-1, 2)).changeColor(DiscColor.WHITE);
    List<Coordinate> allPossibleMoves = this.strategy
            .getAllPossiblePlayerMoves(this.player);

    List<Coordinate> strategyPossibleMoves = this.strategy
            .getStrategyPossibleMoves(allPossibleMoves, this.player);

    Assert.assertEquals(strategyPossibleMoves.size(), 5);
    Coordinate winnerCoord = strategyPossibleMoves.get(0);
    Coordinate runnerUpCoord = strategyPossibleMoves.get(1);
    Coordinate runnerUpToRunnerUpCoord = strategyPossibleMoves.get(2);

    Assert.assertEquals(winnerCoord, new Coordinate(-1, 3));
    Assert.assertEquals(runnerUpCoord, new Coordinate(-2, 1));
    Assert.assertEquals(runnerUpToRunnerUpCoord, new Coordinate(1, -2));

    Assert.assertEquals(this.actionableModel
            .getSandwichableNeighbors(winnerCoord, this.player).size(), 2);
    Assert.assertEquals(this.actionableModel
            .getSandwichableNeighbors(runnerUpCoord, this.player).size(), 1);
    Assert.assertEquals(this.actionableModel
            .getSandwichableNeighbors(runnerUpToRunnerUpCoord, this.player).size(), 1);
  }

  @Test
  public void testChecksSandwichableNeighborOfEveryPossibleMove() {
    this.strategy.getStrategyPossibleMoves(this.strategy
            .getAllPossiblePlayerMoves(this.player), this.player);
    Assert.assertTrue(this.a.toString().contains(
            "get like neighbors of X at: x=-1, y=2\n" +
                    "get sandwichable neighbors of X at: x=-1, y=-2\n" +
                    "get sandwichable neighbors of X at: x=1, y=-2\n" +
                    "get sandwichable neighbors of X at: x=2, y=1\n" +
                    "get sandwichable neighbors of X at: x=-2, y=-1\n" +
                    "get sandwichable neighbors of X at: x=-2, y=1\n" +
                    "get sandwichable neighbors of X at: x=2, y=-1\n" +
                    "get sandwichable neighbors of X at: x=2, y=2\n" +
                    "get sandwichable neighbors of X at: x=1, y=2\n" +
                    "get sandwichable neighbors of X at: x=-2, y=-2\n" +
                    "get sandwichable neighbors of X at: x=-1, y=2"));
  }

  @Test
  public void testGetMoveTranscriptForSimplestStrategyChoosingFirstMoveForBlackPlayer() {
    Optional<Coordinate> move = this.strategy.move();
    Assert.assertEquals(this.a.toString(),
            "get player colors\n" +
                    "get all coords\n" +
                    "get rule keeper\n" +
                    "get all coords\n" +
                    "get player colors\n" +
                    "get cell at: x=-1, y=-2\n" +
                    "get like neighbors of X at: x=-1, y=-2\n" +
                    "get rule keeper\n" +
                    "get all coords\n" +
                    "get player colors\n" +
                    "get cell at: x=-2, y=2\n" +
                    "get like neighbors of X at: x=-2, y=2\n" +
                    "get sandwichable neighbors of X at: x=-2, y=2\n" +
                    "get rule keeper\n" +
                    "get all coords\n" +
                    "get player colors\n" +
                    "get cell at: x=-3, y=2\n" +
                    "get like neighbors of X at: x=-3, y=2\n" +
                    "get sandwichable neighbors of X at: x=-3, y=2\n" +
                    "get rule keeper\n" +
                    "get all coords\n" +
                    "get player colors\n" +
                    "get cell at: x=1, y=-2\n" +
                    "get like neighbors of X at: x=1, y=-2\n" +
                    "get rule keeper\n" +
                    "get all coords\n" +
                    "get player colors\n" +
                    "get cell at: x=3, y=3\n" +
                    "get like neighbors of X at: x=3, y=3\n" +
                    "get sandwichable neighbors of X at: x=3, y=3\n" +
                    "get rule keeper\n" +
                    "get all coords\n" +
                    "get player colors\n" +
                    "get cell at: x=2, y=-2\n" +
                    "get like neighbors of X at: x=2, y=-2\n" +
                    "get sandwichable neighbors of X at: x=2, y=-2\n" +
                    "get rule keeper\n" +
                    "get all coords\n" +
                    "get player colors\n" +
                    "get cell at: x=3, y=1\n" +
                    "get like neighbors of X at: x=3, y=1\n" +
                    "get sandwichable neighbors of X at: x=3, y=1\n" +
                    "get rule keeper\n" +
                    "get all coords\n" +
                    "get player colors\n" +
                    "get cell at: x=1, y=3\n" +
                    "get like neighbors of X at: x=1, y=3\n" +
                    "get sandwichable neighbors of X at: x=1, y=3\n" +
                    "get rule keeper\n" +
                    "get all coords\n" +
                    "get player colors\n" +
                    "get cell at: x=3, y=-2\n" +
                    "get like neighbors of X at: x=3, y=-2\n" +
                    "get sandwichable neighbors of X at: x=3, y=-2\n" +
                    "get rule keeper\n" +
                    "get all coords\n" +
                    "get player colors\n" +
                    "get cell at: x=2, y=1\n" +
                    "get like neighbors of X at: x=2, y=1\n" +
                    "get rule keeper\n" +
                    "get all coords\n" +
                    "get player colors\n" +
                    "get cell at: x=2, y=3\n" +
                    "get like neighbors of X at: x=2, y=3\n" +
                    "get sandwichable neighbors of X at: x=2, y=3\n" +
                    "get rule keeper\n" +
                    "get all coords\n" +
                    "get player colors\n" +
                    "get cell at: x=1, y=1\n" +
                    "get rule keeper\n" +
                    "get all coords\n" +
                    "get player colors\n" +
                    "get cell at: x=-1, y=3\n" +
                    "get like neighbors of X at: x=-1, y=3\n" +
                    "get sandwichable neighbors of X at: x=-1, y=3\n" +
                    "get rule keeper\n" +
                    "get all coords\n" +
                    "get player colors\n" +
                    "get cell at: x=-3, y=-1\n" +
                    "get like neighbors of X at: x=-3, y=-1\n" +
                    "get sandwichable neighbors of X at: x=-3, y=-1\n" +
                    "get rule keeper\n" +
                    "get all coords\n" +
                    "get player colors\n" +
                    "get cell at: x=-2, y=-3\n" +
                    "get like neighbors of X at: x=-2, y=-3\n" +
                    "get sandwichable neighbors of X at: x=-2, y=-3\n" +
                    "get rule keeper\n" +
                    "get all coords\n" +
                    "get player colors\n" +
                    "get cell at: x=-1, y=1\n" +
                    "get rule keeper\n" +
                    "get all coords\n" +
                    "get player colors\n" +
                    "get cell at: x=-2, y=-1\n" +
                    "get like neighbors of X at: x=-2, y=-1\n" +
                    "get rule keeper\n" +
                    "get all coords\n" +
                    "get player colors\n" +
                    "get cell at: x=-3, y=-3\n" +
                    "get like neighbors of X at: x=-3, y=-3\n" +
                    "get sandwichable neighbors of X at: x=-3, y=-3\n" +
                    "get rule keeper\n" +
                    "get all coords\n" +
                    "get player colors\n" +
                    "get cell at: x=-3, y=3\n" +
                    "get like neighbors of X at: x=-3, y=3\n" +
                    "get sandwichable neighbors of X at: x=-3, y=3\n" +
                    "get rule keeper\n" +
                    "get all coords\n" +
                    "get player colors\n" +
                    "get cell at: x=-2, y=1\n" +
                    "get like neighbors of X at: x=-2, y=1\n" +
                    "get rule keeper\n" +
                    "get all coords\n" +
                    "get player colors\n" +
                    "get cell at: x=-1, y=-1\n" +
                    "get rule keeper\n" +
                    "get all coords\n" +
                    "get player colors\n" +
                    "get cell at: x=-2, y=3\n" +
                    "get like neighbors of X at: x=-2, y=3\n" +
                    "get sandwichable neighbors of X at: x=-2, y=3\n" +
                    "get rule keeper\n" +
                    "get all coords\n" +
                    "get player colors\n" +
                    "get cell at: x=-3, y=1\n" +
                    "get like neighbors of X at: x=-3, y=1\n" +
                    "get sandwichable neighbors of X at: x=-3, y=1\n" +
                    "get rule keeper\n" +
                    "get all coords\n" +
                    "get player colors\n" +
                    "get cell at: x=-1, y=-3\n" +
                    "get like neighbors of X at: x=-1, y=-3\n" +
                    "get sandwichable neighbors of X at: x=-1, y=-3\n" +
                    "get rule keeper\n" +
                    "get all coords\n" +
                    "get player colors\n" +
                    "get cell at: x=1, y=-1\n" +
                    "get rule keeper\n" +
                    "get all coords\n" +
                    "get player colors\n" +
                    "get cell at: x=2, y=-3\n" +
                    "get like neighbors of X at: x=2, y=-3\n" +
                    "get sandwichable neighbors of X at: x=2, y=-3\n" +
                    "get rule keeper\n" +
                    "get all coords\n" +
                    "get player colors\n" +
                    "get cell at: x=2, y=-1\n" +
                    "get like neighbors of X at: x=2, y=-1\n" +
                    "get rule keeper\n" +
                    "get all coords\n" +
                    "get player colors\n" +
                    "get cell at: x=1, y=-3\n" +
                    "get like neighbors of X at: x=1, y=-3\n" +
                    "get sandwichable neighbors of X at: x=1, y=-3\n" +
                    "get rule keeper\n" +
                    "get all coords\n" +
                    "get player colors\n" +
                    "get cell at: x=3, y=2\n" +
                    "get like neighbors of X at: x=3, y=2\n" +
                    "get sandwichable neighbors of X at: x=3, y=2\n" +
                    "get rule keeper\n" +
                    "get all coords\n" +
                    "get player colors\n" +
                    "get cell at: x=3, y=-1\n" +
                    "get like neighbors of X at: x=3, y=-1\n" +
                    "get sandwichable neighbors of X at: x=3, y=-1\n" +
                    "get rule keeper\n" +
                    "get all coords\n" +
                    "get player colors\n" +
                    "get cell at: x=2, y=2\n" +
                    "get like neighbors of X at: x=2, y=2\n" +
                    "get rule keeper\n" +
                    "get all coords\n" +
                    "get player colors\n" +
                    "get cell at: x=1, y=2\n" +
                    "get like neighbors of X at: x=1, y=2\n" +
                    "get rule keeper\n" +
                    "get all coords\n" +
                    "get player colors\n" +
                    "get cell at: x=3, y=-3\n" +
                    "get like neighbors of X at: x=3, y=-3\n" +
                    "get sandwichable neighbors of X at: x=3, y=-3\n" +
                    "get rule keeper\n" +
                    "get all coords\n" +
                    "get player colors\n" +
                    "get cell at: x=-3, y=-2\n" +
                    "get like neighbors of X at: x=-3, y=-2\n" +
                    "get sandwichable neighbors of X at: x=-3, y=-2\n" +
                    "get rule keeper\n" +
                    "get all coords\n" +
                    "get player colors\n" +
                    "get cell at: x=-2, y=-2\n" +
                    "get like neighbors of X at: x=-2, y=-2\n" +
                    "get rule keeper\n" +
                    "get all coords\n" +
                    "get player colors\n" +
                    "get cell at: x=-1, y=2\n" +
                    "get like neighbors of X at: x=-1, y=2\n" +
                    "get player colors\n" +
                    "get all coords\n" +
                    "get rule keeper\n" +
                    "get all coords\n" +
                    "get player colors\n" +
                    "get cell at: x=-1, y=-2\n" +
                    "get like neighbors of X at: x=-1, y=-2\n" +
                    "get all coords\n" +
                    "get rule keeper\n" +
                    "get all coords\n" +
                    "get player colors\n" +
                    "get cell at: x=1, y=-2\n" +
                    "get like neighbors of X at: x=1, y=-2\n" +
                    "get all coords\n" +
                    "get rule keeper\n" +
                    "get all coords\n" +
                    "get player colors\n" +
                    "get cell at: x=2, y=1\n" +
                    "get like neighbors of X at: x=2, y=1\n" +
                    "get all coords\n" +
                    "get rule keeper\n" +
                    "get all coords\n" +
                    "get player colors\n" +
                    "get cell at: x=-2, y=-1\n" +
                    "get like neighbors of X at: x=-2, y=-1\n" +
                    "get all coords\n" +
                    "get rule keeper\n" +
                    "get all coords\n" +
                    "get player colors\n" +
                    "get cell at: x=-2, y=1\n" +
                    "get like neighbors of X at: x=-2, y=1\n" +
                    "get all coords\n" +
                    "get rule keeper\n" +
                    "get all coords\n" +
                    "get player colors\n" +
                    "get cell at: x=2, y=-1\n" +
                    "get like neighbors of X at: x=2, y=-1\n" +
                    "get all coords\n" +
                    "get rule keeper\n" +
                    "get all coords\n" +
                    "get player colors\n" +
                    "get cell at: x=2, y=2\n" +
                    "get like neighbors of X at: x=2, y=2\n" +
                    "get all coords\n" +
                    "get rule keeper\n" +
                    "get all coords\n" +
                    "get player colors\n" +
                    "get cell at: x=1, y=2\n" +
                    "get like neighbors of X at: x=1, y=2\n" +
                    "get all coords\n" +
                    "get rule keeper\n" +
                    "get all coords\n" +
                    "get player colors\n" +
                    "get cell at: x=-2, y=-2\n" +
                    "get like neighbors of X at: x=-2, y=-2\n" +
                    "get all coords\n" +
                    "get rule keeper\n" +
                    "get all coords\n" +
                    "get player colors\n" +
                    "get cell at: x=-1, y=2\n" +
                    "get like neighbors of X at: x=-1, y=2\n" +
                    "get sandwichable neighbors of X at: x=-1, y=-2\n" +
                    "get sandwichable neighbors of X at: x=1, y=-2\n" +
                    "get sandwichable neighbors of X at: x=2, y=1\n" +
                    "get sandwichable neighbors of X at: x=-2, y=-1\n" +
                    "get sandwichable neighbors of X at: x=-2, y=1\n" +
                    "get sandwichable neighbors of X at: x=2, y=-1\n" +
                    "get sandwichable neighbors of X at: x=2, y=2\n" +
                    "get sandwichable neighbors of X at: x=1, y=2\n" +
                    "get sandwichable neighbors of X at: x=-2, y=-2\n" +
                    "get sandwichable neighbors of X at: x=-1, y=2\n");
    Assert.assertEquals(move, Optional.of(new Coordinate(-2, 1)));
  }
}
