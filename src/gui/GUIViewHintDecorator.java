package gui;

import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import events.IViewEvent;
import model.board.Coordinate;
import player.Player;

/**
 * Allows for hints to be made without altering any previous code on the view that is passed in.
 */
public class GUIViewHintDecorator extends JFrame implements GUIView {
  private final GUIView view;
  private boolean hintOn;
  private final GUIPanelHintDecorator panel;

  /**
   * Sets up the view with the ability to make hints.
   *
   * @param view the view to make hints on
   */
  public GUIViewHintDecorator(GUIView view) {
    this.view = view;
    if (!(this.view.getPanel() instanceof GUIPanelHintDecorator)) {
      throw new IllegalArgumentException("Cannot create a hint decorator on a non-hint panel.");
    }

    ((JFrame) this.view).removeKeyListener(this.view);
    ((JFrame) this.view).addKeyListener(this);
    this.panel = (GUIPanelHintDecorator) this.view.getPanel();
    this.view.getPanel().addPanelListener(this);
    this.hintOn = false;
  }

  @Override
  public void cellClicked(int xCoordinate, int yCoordinate, boolean selected) {
    this.view.cellClicked(xCoordinate, yCoordinate, selected);
    if (this.hintOn) {
      for (IViewEvent listener : this.getListeners()) {
        System.out.println("Notify hint listener of cell clicked");
        this.panel.showHint(listener.toggleHint(hintOn));
      }
    }
  }

  @Override
  public void setVisible(boolean b) {
    this.view.setVisible(b);
  }

  @Override
  public void setPlayer(Player player) {
    this.view.setPlayer(player);
  }

  @Override
  public void addViewListener(IViewEvent listener) {
    this.view.addViewListener(listener);
  }

  @Override
  public Coordinate getSelectedCell() {
    return this.view.getSelectedCell();
  }

  @Override
  public void updateCanvas(boolean deselect) {
    SwingUtilities.invokeLater(() -> {
      // Delegate the canvas update to the underlying view
      view.updateCanvas(deselect);

      // Additional logic related to hint display
      if (hintOn) {
        for (IViewEvent listener : getListeners()) {
          System.out.println("Notify hint listener of canvas update");
          panel.showHint(listener.toggleHint(hintOn));
        }
      }
    });
  }

  @Override
  public GUIPanel getPanel() {
    return this.view.getPanel();
  }

  @Override
  public List<IViewEvent> getListeners() {
    return this.view.getListeners();
  }

  @Override
  public void keyTyped(KeyEvent e) {
    this.view.keyTyped(e);
  }

  @Override
  public void keyPressed(KeyEvent e) {
    this.view.keyPressed(e);
    if (e.getKeyCode() == KeyEvent.VK_H) {
      for (IViewEvent listener : this.getListeners()) {
        this.hintOn = !this.hintOn;
        System.out.println("H key pressed.");
        this.panel.showHint(listener.toggleHint(this.hintOn));
      }
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    this.view.keyReleased(e);
  }

  @Override
  public void componentResized(ComponentEvent e) {
    this.view.componentResized(e);
  }

  @Override
  public void componentMoved(ComponentEvent e) {
    this.view.componentMoved(e);
  }

  @Override
  public void componentShown(ComponentEvent e) {
    this.view.componentShown(e);
  }

  @Override
  public void componentHidden(ComponentEvent e) {
    this.view.componentHidden(e);
  }
}
