package oldtests;

import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

import model.board.Coordinate;
import move.Move;
import move.Pass;
import move.PlayDisc;

/**
 * Tests the Move interface instances (pass and play).
 */
public class MoveTests {
  private final Move pass = new Pass();
  private final Move playDiscToTwoOne = new PlayDisc(new Coordinate(2, 1));

  @Test
  public void testGetMoveCoordinate() {
    Assert.assertEquals(pass.getMoveCoordinate(), Optional.empty());
    Assert.assertEquals(playDiscToTwoOne.getMoveCoordinate(),
        Optional.of(new Coordinate(2, 1)));
    Assert.assertEquals(
        new PlayDisc(new Coordinate(999, 145)).getMoveCoordinate(),
        Optional.of(new Coordinate(999, 145)));
    Assert.assertEquals(
        new PlayDisc(new Coordinate(1, -4)).getMoveCoordinate(),
        Optional.of(new Coordinate(1, -4)));
  }

}
