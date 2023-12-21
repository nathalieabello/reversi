package hw09tests.strategies;

import org.junit.Before;

import oldtests.strategy.MockReadOnlyModel;
import model.board.IModel;
import model.board.ReadOnlyModel;
import model.cell.DiscColor;
import strategy.AIStrategy;

/**
 * Enables abstract tests for different strategies and models.
 */
public abstract class AbstractModelStrategyTests {
  protected Appendable a;
  protected IModel actionableModel;
  protected ReadOnlyModel readOnlyMock;
  protected DiscColor player;
  protected AIStrategy strategy;

  @Before
  public void init() {
    this.a = new StringBuilder();
    this.player = DiscColor.BLACK;
    this.actionableModel = this.getModel();
    this.actionableModel.startGame();
    this.readOnlyMock = new MockReadOnlyModel(this.a, this.actionableModel);
    this.strategy = this.getStrategy(this.player, this.readOnlyMock);
  }

  abstract protected IModel getModel();

  abstract protected AIStrategy getStrategy(DiscColor player, ReadOnlyModel readOnlyMock);
}
