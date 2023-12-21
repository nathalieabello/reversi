package oldtests.strategy;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import model.board.Coordinate;
import model.board.IModel;
import model.board.ReadOnlyModel;
import model.board.Status;
import model.cell.DiscColor;
import model.cell.ICell;
import ruleskeeper.RuleKeeper;

/**
 * Mock class for the Read Only Model.
 */
public class MockReadOnlyModel implements ReadOnlyModel {
  protected final Appendable a;
  private final IModel actionableModel;

  /**
   * Constructor for the mock read only model.
   *
   * @param a     where to append what actions are happening
   * @param model the model to play with
   */
  public MockReadOnlyModel(Appendable a, IModel model) {
    this.actionableModel = model;
    this.a = Objects.requireNonNull(a);
  }

  protected static class Utils {
    protected static void writeIO(Appendable appendable, String message) {
      try {
        appendable.append(message);
        appendable.append("\n");
      } catch (IOException e) {
        throw new IllegalStateException("Append failed");
      }
    }

  }

  @Override
  public int getNumLayers() {
    Utils.writeIO(this.a, "get num layers");
    return this.actionableModel.getNumLayers();
  }

  @Override
  public DiscColor getTurn() {
    Utils.writeIO(this.a, "get turn");
    return this.actionableModel.getTurn();
  }

  @Override
  public ICell getCellAt(Coordinate coord) {
    Utils.writeIO(this.a, "get cell at: x=" + coord.getRow()
            + ", y=" + coord.getCol());
    return this.actionableModel.getCellAt(coord);
  }

  @Override
  public boolean isGameOver() {
    Utils.writeIO(this.a, "is game over?");
    return this.actionableModel.isGameOver();
  }

  @Override
  public List<Coordinate> getSandwichableNeighbors(Coordinate origin, DiscColor player) {
    Utils.writeIO(this.a, "get sandwichable neighbors of " + player
            + " at: x="
            + origin.getRow() + ", y=" + origin.getCol());
    return this.actionableModel.getSandwichableNeighbors(origin, player);
  }

  @Override
  public List<Coordinate> getOppositeNeighbors(Coordinate origin, DiscColor player) {
    Utils.writeIO(this.a, "get opposite neighbors of " + player
            + " at: x="
            + origin.getRow() + ", y=" + origin.getCol());
    return this.actionableModel.getOppositeNeighbors(origin, player);
  }

  @Override
  public List<Coordinate> getLikeNeighbors(Coordinate coord, DiscColor player) {
    Utils.writeIO(this.a, "get like neighbors of " + player
            + " at: x="
            + coord.getRow() + ", y=" + coord.getCol());
    return this.actionableModel.getLikeNeighbors(coord, player);
  }

  @Override
  public Optional<DiscColor> getWinner() {
    Utils.writeIO(this.a, "get winner");
    return this.actionableModel.getWinner();
  }

  @Override
  public Status getGameState() {
    Utils.writeIO(this.a, "get game state");
    return this.actionableModel.getGameState();
  }

  @Override
  public ReadOnlyModel getReadOnlyCopyOfBoard(IModel model) {
    Utils.writeIO(this.a, "get read only copy of board");
    return this.actionableModel.getReadOnlyCopyOfBoard(model);
  }

  @Override
  public int getPlayerScore(DiscColor player) {
    Utils.writeIO(this.a, "get player score of " + player);
    return this.actionableModel.getPlayerScore(player);
  }

  @Override
  public RuleKeeper getRuleKeeper() {
    Utils.writeIO(this.a, "get rule keeper");
    return this.actionableModel.getRuleKeeper();
  }

  @Override
  public List<DiscColor> getPlayerColors() {
    Utils.writeIO(this.a, "get player colors");
    return this.actionableModel.getPlayerColors();
  }

  @Override
  public boolean getPass() {
    Utils.writeIO(this.a, "get pass");
    return this.actionableModel.getPass();
  }

  @Override
  public DiscColor getOtherPlayerColor() {
    Utils.writeIO(this.a, "get other player color");
    return this.actionableModel.getOtherPlayerColor();
  }

  @Override
  public HashMap<Coordinate, ICell> getCopyOfAllCoords() {
    Utils.writeIO(this.a, "get all coords");
    return this.actionableModel.getCopyOfAllCoords();
  }

  @Override
  public List<Coordinate> getNeighbors(Coordinate c) {
    Utils.writeIO(this.a, "get neighbors at: x="
            + c.getRow() + ", y=" + c.getCol());
    return this.actionableModel.getNeighbors(c);
  }

  @Override
  public boolean isCorner(Coordinate coord) {
    Utils.writeIO(this.a, "is corner?: x="
            + coord.getRow() + ", y=" + coord.getCol());
    return this.actionableModel.isCorner(coord);
  }

  @Override
  public void startGame() {
    Utils.writeIO(this.a, "start game");
  }
}
