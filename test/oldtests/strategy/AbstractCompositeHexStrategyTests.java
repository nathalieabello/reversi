package oldtests.strategy;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import model.board.ReadOnlyModel;
import model.cell.DiscColor;
import strategy.AIStrategy;
import strategy.AbstractCompositeStrategy;
import strategy.AbstractSingleStrategy;
import strategy.AvoidCornerNeighbors;
import strategy.CaptureMostCells;
import strategy.PrioritizeCorners;

/**
 * The test class for the AbstractCompositeStrategy class.
 */
public abstract class AbstractCompositeHexStrategyTests extends AbstractHexStrategyTests {

  protected AbstractCompositeStrategy compositeStrategy;
  protected AbstractSingleStrategy singleStrategy;
  protected AIStrategy nextStrategy; //for testing purposes the next will composite.
  protected PrioritizeCorners pc; // for ease of testing
  protected AvoidCornerNeighbors acn; // for ease of testing
  protected CaptureMostCells cmc; // for ease of testing

  /**
   * Gets the composite strategy to be used for testing.
   *
   * @param strategy     the strategy to be used for testing
   * @param nextStrategy the next strategy to be used for testing.
   * @return the composite strategy to be used for testing.
   */
  protected abstract AbstractCompositeStrategy getCompositeStrategy(
          AbstractSingleStrategy strategy,
          AIStrategy nextStrategy);

  /**
   * Gets the single strategy to be used for testing.
   *
   * @param player which player is trying to make a move
   * @param model  which model to play on
   * @return the single strategy to be used for testing.
   */
  protected abstract AbstractSingleStrategy getSingleStrategy(DiscColor player,
                                                              ReadOnlyModel model);

  /**
   * Gets the next strategy to be used for testing.
   */
  protected abstract AIStrategy getNextStrategy(DiscColor player, ReadOnlyModel model);

  /**
   * Gets the strategy to be used for testing.
   *
   * @param player which player is trying to make a move
   * @param model  which model to play on
   * @return the strategy to be used for testing.
   */
  protected AIStrategy getStrategy(DiscColor player, ReadOnlyModel model) {
    return this.getCompositeStrategy(this.getSingleStrategy(player, model),
            this.getNextStrategy(player, model));
  }

  @Before
  public void init() {
    super.init();

    this.singleStrategy = this.getSingleStrategy(this.player, this.readOnlyMock);
    this.nextStrategy = this.getNextStrategy(this.player, this.readOnlyMock);
    this.compositeStrategy = this.getCompositeStrategy(this.singleStrategy, this.nextStrategy);
    this.pc = new PrioritizeCorners(this.player, this.readOnlyMock);
    this.acn = new AvoidCornerNeighbors(this.player, this.readOnlyMock);
    this.cmc = new CaptureMostCells(this.player, this.readOnlyMock);
  }

  /*
    CONSTRUCTOR TESTS:
   */

  @Test(expected = IllegalArgumentException.class)
  public void testNullStrategyConstructor() {
    this.getCompositeStrategy(null, this.compositeStrategy);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullNextStrategyConstructor() {
    this.getCompositeStrategy(this.singleStrategy, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidStrategyOrder123Constructor() {
    this.getCompositeStrategy(
            new CaptureMostCells(this.player, this.readOnlyMock),
            this.getCompositeStrategy(
                    new AvoidCornerNeighbors(this.player, this.readOnlyMock),
                    new PrioritizeCorners(this.player, this.readOnlyMock)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidStrategyOrder132Constructor() {
    this.getCompositeStrategy(
            new CaptureMostCells(this.player, this.readOnlyMock),
            this.getCompositeStrategy(
                    new PrioritizeCorners(this.player, this.readOnlyMock),
                    new AvoidCornerNeighbors(this.player, this.readOnlyMock)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidStrategyOrder213Constructor() {
    this.getCompositeStrategy(
            new AvoidCornerNeighbors(this.player, this.readOnlyMock),
            this.getCompositeStrategy(
                    new CaptureMostCells(this.player, this.readOnlyMock),
                    new PrioritizeCorners(this.player, this.readOnlyMock)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidStrategyOrder231Constructor() {
    this.getCompositeStrategy(
            new AvoidCornerNeighbors(this.player, this.readOnlyMock),
            this.getCompositeStrategy(
                    new PrioritizeCorners(this.player, this.readOnlyMock),
                    new CaptureMostCells(this.player, this.readOnlyMock)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidStrategyOrder312Constructor() {
    this.getCompositeStrategy(
            new PrioritizeCorners(this.player, this.readOnlyMock),
            this.getCompositeStrategy(
                    new CaptureMostCells(this.player, this.readOnlyMock),
                    new AvoidCornerNeighbors(this.player, this.readOnlyMock)));
  }

  @Test
  public void testValidStrategyOrder321Constructor() {
    this.compositeStrategy = this.getCompositeStrategy(
            new PrioritizeCorners(this.player, this.readOnlyMock),
            this.getCompositeStrategy(
                    new AvoidCornerNeighbors(this.player, this.readOnlyMock),
                    new CaptureMostCells(this.player, this.readOnlyMock)));

    Assert.assertEquals(
            this.compositeStrategy.getAllStrategiesFromInnerToOuterMost().size(), 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidStrategyOrder12Constructor() {
    this.getCompositeStrategy(
            new CaptureMostCells(this.player, this.readOnlyMock),
            new AvoidCornerNeighbors(this.player, this.readOnlyMock));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidStrategyOrder13Constructor() {
    this.getCompositeStrategy(
            new CaptureMostCells(this.player, this.readOnlyMock),
            new PrioritizeCorners(this.player, this.readOnlyMock));
  }

  @Test
  public void testValidStrategyOrder21Constructor() {
    this.compositeStrategy = this.getCompositeStrategy(
            new AvoidCornerNeighbors(this.player, this.readOnlyMock),
            new CaptureMostCells(this.player, this.readOnlyMock));

    Assert.assertEquals(
            this.compositeStrategy.getAllStrategiesFromInnerToOuterMost().size(), 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidStrategyOrder23Constructor() {
    this.getCompositeStrategy(
            new AvoidCornerNeighbors(this.player, this.readOnlyMock),
            new PrioritizeCorners(this.player, this.readOnlyMock));
  }

  @Test
  public void testValidStrategyOrder31Constructor() {
    this.compositeStrategy = this.getCompositeStrategy(
            new PrioritizeCorners(this.player, this.readOnlyMock),
            new CaptureMostCells(this.player, this.readOnlyMock));

    Assert.assertEquals(
            this.compositeStrategy.getAllStrategiesFromInnerToOuterMost().size(), 2);
  }

  @Test
  public void testValidStrategyOrder32Constructor() {
    this.compositeStrategy = this.getCompositeStrategy(
            new PrioritizeCorners(this.player, this.readOnlyMock),
            new AvoidCornerNeighbors(this.player, this.readOnlyMock));

    Assert.assertEquals(
            this.compositeStrategy.getAllStrategiesFromInnerToOuterMost().size(), 2);
  }

  /*
    List<AbstractSingleStrategy> getAllStrategiesFromInnerToOuterMost() TESTS:
   */

  @Test
  public void testGetAllStrategiesFromInnerToOuterMost() {
    List<String> expected =
            List.of(CaptureMostCells.class.getSimpleName(),
                    AvoidCornerNeighbors.class.getSimpleName(),
                    PrioritizeCorners.class.getSimpleName());

    List<AbstractSingleStrategy> strategiesFromInnerToOuterMost =
            this.compositeStrategy.getAllStrategiesFromInnerToOuterMost();

    List<String> strategiesFromInnerToOuterMostNames = new ArrayList<>();
    for (AbstractSingleStrategy strategy : strategiesFromInnerToOuterMost) {
      strategiesFromInnerToOuterMostNames.add(strategy.getClass().getSimpleName());
    }
    Assert.assertEquals(strategiesFromInnerToOuterMostNames, expected);
  }
}
