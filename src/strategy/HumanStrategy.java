package strategy;

import java.util.Optional;

import model.board.Coordinate;
import move.Move;
import move.Pass;
import move.PlayDisc;

/**
 * Represents a human strategy meaning one that takes input from the GUI View.
 */
public class HumanStrategy implements IStrategy {
  private Move choice;

  /**
   * Constructs a strategy for a player to make a move.
   */
  public HumanStrategy() {
    this.choice = null;
  }

  /**
   * Determines the coordinate at which a disc is to be played based on the player's choice.
   * Return an empty optional if the player decides to pass.
   * @return the player's move of choice.
   */
  @Override
  public Optional<Coordinate> move() {
    if (this.choice == null) {
      throw new IllegalArgumentException("Cannot generate move coordinates without a choice.");
    }
    return this.choice.getMoveCoordinate();
  }

  /**
   * Notifies the strategy of the move the human player has made.
   *
   * @param playOrPass 0 if the player is trying to play, 1 if the player is trying to pass.
   * @param selectedCell the cell the player has selected.
   */
  public void notifyOfMoveOrPass(int playOrPass, Coordinate selectedCell) {
    System.out.println("NOTIFY Strategy that the move has been made on the view.");
    if (playOrPass != -1) {
      if (playOrPass == 0) {
        // trying to play
        this.choice = new PlayDisc(selectedCell);
      } else if (playOrPass == 1) {
        // trying to pass
        this.choice = new Pass();
      }
    }
  }

  /**
   * Resets the choice of the human strategy.
   */
  public void resetChoice() {
    this.choice = null;
  }
}
