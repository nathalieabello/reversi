package oldtests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Component;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;
import javax.swing.JPanel;

import controller.IController;
import controller.ReversiController;
import gui.GUIPanel;
import gui.GUIView;
import gui.ReversiGUIView;
import model.board.Coordinate;
import model.board.IModel;
import model.board.ReadOnlyModel;
import model.board.ReadOnlyReversiModel;
import player.HumanPlayer;
import model.cell.DiscColor;

/**
 * Tests for the Subscriber/ event classes.
 */
public abstract class EventTests {
  protected ByteArrayOutputStream out;
  protected GUIView view;
  private IModel actionableModel;
  private IController controller;
  private HumanPlayer player;

  @Before
  public void init() {
    this.out = new ByteArrayOutputStream();
    PrintStream redirectedOutput = new PrintStream(this.out); // saves the original System.out
    System.setOut(redirectedOutput); // sets the System.out to the PrintStream

    this.actionableModel = this.getModel();
    actionableModel.startGame();
    ReadOnlyModel model = new ReadOnlyReversiModel(actionableModel);
    this.view = new ReversiGUIView(model, this.getPanel());
    this.player = new HumanPlayer(DiscColor.BLACK, model);
    this.controller =
            new ReversiController(this.actionableModel, player, this.view, false);
    this.view.setVisible(false);
    this.out.reset();
  }

  /**
   * Used for to abstract tests and limit repetition in our tests.
   *
   * @return the panel to work with
   */
  protected abstract GUIPanel getPanel();

  /**
   * Used for to abstract tests and limit repetition in our tests.
   *
   * @return the model to work with
   */
  protected abstract IModel getModel();

  /**
   * Used for to abstract tests and limit repetition in our tests.
   *
   * @return the cell click fired
   */
  abstract protected String getCellClickedTranscript();

  /**
   * Used for to abstract tests and limit repetition in our tests.
   *
   * @return the expected transcript when pressing M
   */
  abstract protected String getExpectedTranscript();

  @Test
  public void testPressingMKeyWithNoCellSelectedTriggersNotificationThread() {
    KeyEvent key = new KeyEvent(((Component) this.view),
            KeyEvent.KEY_RELEASED, 0, 0, KeyEvent.VK_M, 'm');

    this.view.keyReleased(key);

    Assert.assertEquals(this.out.toString(),
            "M key pressed.\n" +
                    "NOTIFY Controller that a PlayDisc move has been made " +
                    "on the view by BLACK player.\n" +
                    "NOTIFY HumanPlayer that the move has been made on the view.\n" +
                    "NOTIFY Strategy that the move has been made on the view.\n" +
                    "SHOW No selected cell for BLACK player.\n");
  }

  @Test
  public void testPressingPKeyTriggersNotificationThread() {
    KeyEvent key = new KeyEvent(((Component) this.view),
            KeyEvent.KEY_RELEASED, 0, 0, KeyEvent.VK_P, 'p');

    this.view.keyReleased(key);

    Assert.assertEquals(this.out.toString(),
            "P key pressed.\n" +
                    "NOTIFY Controller that a Pass move has been made " +
                    "on the view by BLACK player.\n" +
                    "NOTIFY HumanPlayer that the move has been made on the view.\n" +
                    "NOTIFY Strategy that the move has been made on the view.\n" +
                    "PASS move made by BLACK player.\n" +
                    "NOTIFY Controller 0 that model has changed.\n");
  }

  @Test
  public void testNotifyModelHasChanged() {
    this.actionableModel.notifyModelHasChanged();
    Assert.assertEquals(this.out.toString(),
            "NOTIFY Controller 0 that model has changed.\n"
                    + "SHOW Your Turn for BLACK player.\n");
  }

  @Test
  public void testNotifyMovePerformedPlayDiscTriggersPlayDiscNotificationThread() {
    this.controller.notifyMovePerformed("PlayDisc");
    Assert.assertTrue(this.out.toString().contains(
            "NOTIFY Controller that a PlayDisc move has been " +
                    "made on the view by BLACK player.\n"));
    Assert.assertTrue(this.out.toString().contains(
            "NOTIFY HumanPlayer that the move has been made on the view.\n" +
                    "NOTIFY Strategy that the move has been made on the view.\n" +
                    "SHOW No selected cell for BLACK player.\n"));
  }

  @Test
  public void testNotifyMovePerformedPassTriggersPassNotificationThread() {
    this.controller.notifyMovePerformed("Pass");
    Assert.assertTrue(this.out.toString().contains(
            "NOTIFY Controller that a Pass move has been made on the view by BLACK player.\n"));
    Assert.assertTrue(this.out.toString().contains(
            "NOTIFY HumanPlayer that the move has been made on the view.\n" +
                    "NOTIFY Strategy that the move has been made on the view.\n" +
                    "PASS move made by BLACK player.\n" +
                    "NOTIFY Controller 0 that model has changed."));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNotifyMovePerformedStringNull() {
    this.controller.notifyMovePerformed(null);
  }

  @Test
  public void testNotifyMovePerformedStringDoesNotMatchAnyTriggerEvent() {
    this.controller.notifyMovePerformed("Random");
    this.controller.notifyMovePerformed("Play");
    this.controller.notifyMovePerformed("Disc");
    this.controller.notifyMovePerformed("Pas");
    Assert.assertEquals(this.out.toString(), "");
  }

  @Test
  public void testNotifyOfMoveOrPassInPlayerGoesThroughToTheStrategyAlways() {
    this.player.notifyOfMoveOrPass(-1, null);
    Assert.assertTrue(this.out.toString().contains(
            "NOTIFY HumanPlayer that the move has been made on the view.\n" +
                    "NOTIFY Strategy that the move has been made on the view.\n"));

    this.out.reset();
    this.player.notifyOfMoveOrPass(0, null);
    Assert.assertTrue(this.out.toString().contains(
            "NOTIFY HumanPlayer that the move has been made on the view.\n" +
                    "NOTIFY Strategy that the move has been made on the view.\n"));

    this.out.reset();
    this.player.notifyOfMoveOrPass(1, null);
    Assert.assertTrue(this.out.toString().contains(
            "NOTIFY HumanPlayer that the move has been made on the view.\n" +
                    "NOTIFY Strategy that the move has been made on the view.\n"));

    this.out.reset();
    this.player.notifyOfMoveOrPass(2, null);
    Assert.assertTrue(this.out.toString().contains(
            "NOTIFY HumanPlayer that the move has been made on the view.\n" +
                    "NOTIFY Strategy that the move has been made on the view.\n"));

    this.out.reset();
    this.player.notifyOfMoveOrPass(-1, new Coordinate(0, 0));
    Assert.assertTrue(this.out.toString().contains(
            "NOTIFY HumanPlayer that the move has been made on the view.\n" +
                    "NOTIFY Strategy that the move has been made on the view.\n"));

    this.out.reset();
    this.player.notifyOfMoveOrPass(0, new Coordinate(0, 0));
    Assert.assertTrue(this.out.toString().contains(
            "NOTIFY HumanPlayer that the move has been made on the view.\n" +
                    "NOTIFY Strategy that the move has been made on the view.\n"));

    this.out.reset();
    this.player.notifyOfMoveOrPass(1, new Coordinate(0, 0));
    Assert.assertTrue(this.out.toString().contains(
            "NOTIFY HumanPlayer that the move has been made on the view.\n" +
                    "NOTIFY Strategy that the move has been made on the view.\n"));

    this.out.reset();
    this.player.notifyOfMoveOrPass(2, new Coordinate(0, 0));
    Assert.assertTrue(this.out.toString().contains(
            "NOTIFY HumanPlayer that the move has been made on the view.\n" +
                    "NOTIFY Strategy that the move has been made on the view.\n"));
  }

  @Test
  public void testCellClickedSelectsCellIfSelectedBooleanTrue() {
    Assert.assertNull(this.view.getSelectedCell());
    this.view.cellClicked(0, 0, true);
    Assert.assertEquals(this.view.getSelectedCell(), new Coordinate(0, 0));
    Assert.assertEquals(this.out.toString(), "Cell clicked: 0, 0.\n");
  }

  @Test
  public void testCellClickedDeselectsCellIfSelectedBooleanFalse() {
    this.testCellClickedSelectsCellIfSelectedBooleanTrue();
    Assert.assertEquals(this.view.getSelectedCell(), new Coordinate(0, 0));
    this.view.cellClicked(0, 0, false);
    Assert.assertNull(this.view.getSelectedCell());
  }


  @Test
  public void testCellClickedEventFiredWhenClickingCenterCellInView() {
    MouseEvent click = new MouseEvent(((Component) this.view),
            MouseEvent.MOUSE_CLICKED, 0,
            InputEvent.BUTTON1_DOWN_MASK,
        ((JPanel) this.view.getPanel()).getWidth() / 2,
        ((JPanel) this.view.getPanel()).getHeight() / 2,
            1, false, MouseEvent.BUTTON1);

    try {
      SwingUtilities.invokeAndWait(() -> {
        this.view.getPanel().mouseClicked(click);
      });
    } catch (InvocationTargetException | InterruptedException e) {
      e.printStackTrace();
    }

    Assert.assertEquals(this.out.toString(), this.getCellClickedTranscript());
  }

  @Test
  public void testPressingMKeyWithCenterCellSelectedTriggersNotificationThread() {
    KeyEvent key = new KeyEvent(((Component) this.view),
            KeyEvent.KEY_RELEASED, 0, 0, KeyEvent.VK_M, 'm');

    testCellClickedEventFiredWhenClickingCenterCellInView();
    try {
      SwingUtilities.invokeAndWait(() -> {
        this.view.keyReleased(key);
      });
    } catch (InvocationTargetException | InterruptedException e) {
      e.printStackTrace();
    }

    Assert.assertEquals(this.out.toString(), this.getExpectedTranscript());
  }
}
