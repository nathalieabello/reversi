package strategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a composite strategy for a player to make a move, which is a combination of
 * multiple single strategies.
 */
public abstract class AbstractCompositeStrategy extends AbstractSingleStrategy {
  protected final AbstractSingleStrategy strategy;
  protected final AIStrategy nextStrategy;

  /**
   * Constructs a composite strategy with multiple strategies.
   *
   * @param strategy     is the strategy to be used.
   * @param nextStrategy is the composite strategy.
   */
  public AbstractCompositeStrategy(AbstractSingleStrategy strategy, AIStrategy nextStrategy) {
    super(strategy == null ? null : strategy.player, strategy == null ? null : strategy.model);

    if (nextStrategy == null) {
      throw new IllegalArgumentException("Next strategy cannot be null");
    }

    this.strategy = strategy;
    this.nextStrategy = nextStrategy;

    this.checkStrategyOrder(this.getAllStrategiesFromInnerToOuterMost());
  }

  /**
   * Returns the list of strategies from inner to outermost, so that we can stack the strategies
   * to make sure the best move is chosen.
   *
   * @return the list of strategies from inner to outermost.
   */
  public List<AbstractSingleStrategy> getAllStrategiesFromInnerToOuterMost() {
    List<AbstractSingleStrategy> strategiesFromInnerToOuterMost = new ArrayList<>();

    strategiesFromInnerToOuterMost.add(0, this.strategy); //add at the first strategy.

    IStrategy currentStrategy = this.nextStrategy; //start at the next strategy.
    //recursively get all strategies from inner to outermost.
    while (currentStrategy instanceof AbstractCompositeStrategy) {
      AbstractCompositeStrategy compositeStrategy = (AbstractCompositeStrategy) currentStrategy;
      strategiesFromInnerToOuterMost.add(0, compositeStrategy.strategy);
      currentStrategy = compositeStrategy.nextStrategy; //get the next strategy recursively.
    }

    //add the last strategy non-composite strategy.
    strategiesFromInnerToOuterMost.add(0, (AbstractSingleStrategy) currentStrategy);

    return strategiesFromInnerToOuterMost; //return the list of strategies from inner to outermost.
  }

  /**
   * Checks if the strategy order is valid.
   *
   * @param strategiesFromInnerToOuterMost the strategies to check the order of.
   */
  protected abstract void checkStrategyOrder(
      List<AbstractSingleStrategy> strategiesFromInnerToOuterMost);
}
