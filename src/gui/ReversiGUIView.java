package gui;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;
import javax.swing.Box;

import events.IViewEvent;
import model.board.Coordinate;
import model.board.ReadOnlyModel;
import model.cell.DiscColor;
import player.Player;

/**
 * Represents an AbstractGUIView class.
 */
public class ReversiGUIView extends JFrame
        implements GUIView {
  public static int CELL_SIZE = 60;
  public static final Color BACKGROUND_COLOR = Color.darkGray;
  private final int layers;
  private final ArrayList<IViewEvent> listeners = new ArrayList<>();
  private Coordinate selectedCell;
  private final ReadOnlyModel model;
  private final JPanel scorePanel;
  private final List<DiscColor> playerColors;
  private final GUIPanel canvas;

  /**
   * Constructs an AbstractGUIView.
   *
   * @param model is the model of the game.
   */
  public ReversiGUIView(ReadOnlyModel model, GUIPanel canvas) {
    this.model = model;
    this.layers = this.model.getNumLayers();
    this.selectedCell = null;
    this.playerColors = this.model.getPlayerColors();
    this.canvas = canvas;

    this.scorePanel = new JPanel();
    this.displayScore();

    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    this.canvas.addPanelListener(this);

    setLayout(new BorderLayout());
    add((JPanel) this.canvas, BorderLayout.CENTER);

    addKeyListener(this);
    addComponentListener(this);

    add(this.scorePanel, BorderLayout.NORTH);

    setPreferredSize(this.createDimension(this.layers));
    setLocationRelativeTo(null); // Center the JFrame on the screen
    pack();
  }

  /**
   * Creates the window dimensions.
   *
   * @param layers is the number of layers in the game.
   * @return the dimensions of the window.
   */
  private Dimension createDimension(int layers) {
    int side = layers * 3 * CELL_SIZE;
    return new Dimension(side, side);
  }

  /**
   * Determines which cell was clicked, and if it has been selected or deselected.
   * If the cell is selected, the selected cell is set to the cell clicked.
   * If the cell is deselected, the selected cell is set to null.
   *
   * @param xCoordinate is the x coordinate of the cell clicked.
   * @param yCoordinate is the y coordinate of the cell clicked.
   * @param selected    is whether the cell is selected.
   */
  public void cellClicked(int xCoordinate, int yCoordinate, boolean selected) {
    if (selected) {
      this.selectedCell = new Coordinate(xCoordinate, yCoordinate);
      System.out.println("Cell clicked: " + xCoordinate + ", " + yCoordinate + ".");
    } else {
      this.selectedCell = null;
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {
    //no need to track this event.
  }

  @Override
  public void keyPressed(KeyEvent e) {
    //no need to track this event.
  }

  /**
   * Processes a key released event.
   * If the M key is pressed, the player wishes to play a disc at the selected cell.
   * If the P key is pressed, the player wishes to pass.
   *
   * @param e the event to be processed.
   */
  public void keyReleased(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_M) {
      for (IViewEvent listener : this.listeners) {
        System.out.println("M key pressed.");
        listener.notifyMovePerformed("PlayDisc");
      }
    } else if (e.getKeyCode() == KeyEvent.VK_P) {
      for (IViewEvent listener : this.listeners) {
        System.out.println("P key pressed.");
        listener.notifyMovePerformed("Pass");
      }
    }
  }

  @Override
  public void componentResized(ComponentEvent e) {
    Dimension newSize = e.getComponent().getSize();

    int minDimension = Math.min(newSize.width, newSize.height);
    CELL_SIZE = minDimension / (this.layers * 3);

    this.canvas.updateSize(CELL_SIZE);

    this.displayScore();

    //maintain aspect ratio.
    setPreferredSize(this.createDimension(this.layers));
    pack();
  }

  @Override
  public void componentMoved(ComponentEvent e) {
    //no need to track this event.
  }

  @Override
  public void componentShown(ComponentEvent e) {
    //no need to track this event.
  }

  @Override
  public void componentHidden(ComponentEvent e) {
    //no need to track this event.
  }

  /**
   * Sets the player that corresponds with this view.
   *
   * @param player the player that corresponds with this view
   */
  @Override
  public void setPlayer(Player player) {
    this.setTitle(player.getPlayerColor().name());
  }

  /**
   * Allows the controller to be added as a listener to the view.
   *
   * @param listener the controller.
   */
  public void addViewListener(IViewEvent listener) {
    this.listeners.add(listener);
  }

  @Override
  public Coordinate getSelectedCell() {
    return this.selectedCell;
  }

  /**
   * Updates the canvas and determines whether to only deselect the cell or not.
   *
   * @param deselect is whether to deselect the cell.
   */
  @Override
  public void updateCanvas(boolean deselect) {
    SwingUtilities.invokeLater(() -> {
      canvas.updateCanvas(deselect);

      this.displayScore();

      this.selectedCell = null;
      pack();
    });
  }

  /**
   * Get the board.
   *
   * @return the board.
   */
  public GUIPanel getPanel() {
    return this.canvas;
  }

  /**
   * Get list of listeners.
   *
   * @return the list of listeners.
   */
  public List<IViewEvent> getListeners() {
    return this.listeners;
  }

  /**
   * Displays the score of the game.
   * The score is displayed in the top center of the window.
   * The score is displayed in the color of the player.
   */
  private void displayScore() {
    DiscColor player1Color = this.playerColors.get(0);
    DiscColor player2Color = this.playerColors.get(1);

    int playerScore1;
    int playerScore2;
    try {
      playerScore1 = this.model.getPlayerScore(player1Color);
      playerScore2 = this.model.getPlayerScore(player2Color);
    } catch (IllegalStateException e) {
      playerScore1 = this.canvas.getSides() / 2;
      playerScore2 = this.canvas.getSides() / 2;
    }
    JLabel player1ScoreLabel = new JLabel();
    JLabel player2ScoreLabel = new JLabel();

    Font font = player1ScoreLabel.getFont();

    player1ScoreLabel.setFont(new Font(font.getName(), Font.BOLD, CELL_SIZE / 3));
    player2ScoreLabel.setFont(new Font(font.getName(), Font.BOLD, CELL_SIZE / 3));
    player1ScoreLabel.setForeground(player1Color.getDiscColorValue());
    player2ScoreLabel.setForeground(player2Color.getDiscColorValue());
    player1ScoreLabel.setText(player1Color.name() + " Score: " + playerScore1);
    player2ScoreLabel.setText(player2Color.name() + " Score: " + playerScore2);

    // Create a panel for the scores
    this.scorePanel.removeAll();
    this.scorePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    this.scorePanel.setBorder(new EmptyBorder(CELL_SIZE, 0, 0, 0));
    this.scorePanel.setBackground(BACKGROUND_COLOR);
    this.scorePanel.add(player1ScoreLabel);
    this.scorePanel.add(Box.createHorizontalStrut(CELL_SIZE / 2)); // Add glue to separate scores
    this.scorePanel.add(player2ScoreLabel);

    setPreferredSize(this.createDimension(this.layers));
  }

  public ReadOnlyModel getModel() {
    return this.model;
  }
}
