package model.board;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import model.cell.DiscColor;
import model.cell.ICell;
import ruleskeeper.RuleKeeper;

/**
 * Represents a read-only version of our model for our reversi game.
 */
public class ReadOnlyReversiModel implements ReadOnlyModel {
  private final IModel adaptee;

  /**
   * Constructor for the read-only version of our model.
   *
   * @param adaptee the model to adapt.
   */
  public ReadOnlyReversiModel(IModel adaptee) {
    if (adaptee == null) {
      throw new IllegalArgumentException("Cannot adapt a null model.");
    }
    this.adaptee = adaptee;
  }

  @Override
  public int getNumLayers() {
    return this.adaptee.getNumLayers();
  }

  @Override
  public DiscColor getTurn() {
    return this.adaptee.getTurn();
  }

  @Override
  public ICell getCellAt(Coordinate coord) {
    return this.adaptee.getCellAt(coord);
  }

  @Override
  public boolean isGameOver() {
    return this.adaptee.isGameOver();
  }

  @Override
  public List<Coordinate> getSandwichableNeighbors(Coordinate origin, DiscColor player) {
    return this.adaptee.getSandwichableNeighbors(origin, player);
  }

  @Override
  public List<Coordinate> getOppositeNeighbors(Coordinate origin, DiscColor player) {
    return this.adaptee.getOppositeNeighbors(origin, player);
  }

  @Override
  public List<Coordinate> getLikeNeighbors(Coordinate coord, DiscColor player) {
    return this.adaptee.getLikeNeighbors(coord, player);
  }

  @Override
  public Optional<DiscColor> getWinner() {
    return this.adaptee.getWinner();
  }

  @Override
  public Status getGameState() {
    return this.adaptee.getGameState();
  }

  @Override
  public ReadOnlyModel getReadOnlyCopyOfBoard(IModel model) {
    return new ReadOnlyReversiModel(this.adaptee.getActionableCopyOfBoard(model));
  }

  @Override
  public int getPlayerScore(DiscColor player) {
    return this.adaptee.getPlayerScore(player);
  }

  @Override
  public RuleKeeper getRuleKeeper() {
    return this.adaptee.getRuleKeeper();
  }

  @Override
  public List<DiscColor> getPlayerColors() {
    return this.adaptee.getPlayerColors();
  }

  @Override
  public boolean getPass() {
    return this.adaptee.getPass();
  }

  @Override
  public DiscColor getOtherPlayerColor() {
    return this.adaptee.getOtherPlayerColor();
  }

  public HashMap<Coordinate, ICell> getCopyOfAllCoords() {
    return this.adaptee.getCopyOfAllCoords();
  }

  @Override
  public List<Coordinate> getNeighbors(Coordinate c) {
    return this.adaptee.getNeighbors(c);
  }

  @Override
  public boolean isCorner(Coordinate coord) {
    return this.adaptee.isCorner(coord);
  }

  @Override
  public void startGame() {
    this.adaptee.startGame();
  }
}
