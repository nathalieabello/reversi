package gui;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.util.Objects;

import javax.swing.JPanel;
import javax.swing.JLabel;

import events.IPanelEvent;
import model.board.Coordinate;

import static gui.ReversiGUIView.CELL_SIZE;

/**
 * Allows for hints to be made without altering any previous code on the panel that is passed in.
 */
public class GUIPanelHintDecorator extends JPanel implements GUIPanel {
  private final GUIPanel panel;
  private final JLabel hintLabel;

  /**
   * Allows for hints to be made without altering any previous code on the panel that is passed in.
   *
   * @param panel the panel to add hints to
   */
  public GUIPanelHintDecorator(GUIPanel panel) {
    this.panel = panel;
    this.hintLabel = new JLabel();
    this.hintLabel.setBackground(new Color(0, 0, 0, 0));
    this.hintLabel.setForeground(Color.BLACK);
    this.hintLabel.setVisible(false);

    setLayout(new BorderLayout());
    add(hintLabel, BorderLayout.CENTER);
    add((JPanel) panel, BorderLayout.CENTER);
  }

  @Override
  public void addPanelListener(IPanelEvent listener) {
    this.panel.addPanelListener(listener);
  }

  @Override
  public void updateSize(int cellSize) {
    this.panel.updateSize(cellSize);
    this.showHintLabel();
  }

  @Override
  public void updateCanvas(boolean deselect) {
    this.panel.updateCanvas(deselect);
    this.showHintLabel();
  }


  @Override
  public AbstractGUICell getSelectedCell() {
    return this.panel.getSelectedCell();
  }

  @Override
  public void setUpPanel() {
    this.panel.setUpPanel();
  }

  @Override
  public int getSides() {
    return this.panel.getSides();
  }

  private void showHintLabel() {
    if (this.hintLabel.isVisible()) {
      this.showHint(Integer.parseInt(this.hintLabel.getText()));
    }
  }

  /**
   * Adds the hint on top of the selected cell.
   *
   * @param points the hint to portray
   */
  public void showHint(int points) {
    if ((points >= 0) && (!Objects.isNull(this.panel.getSelectedCell()))) {
      this.hintLabel.setText(Integer.toString(points));
      this.hintLabel.setPreferredSize(new Dimension(CELL_SIZE / 2, CELL_SIZE / 2));
      // Get the center coordinates of the selected cell
      Coordinate center = this.panel.getSelectedCell().getCellCenter();
      // Set the location of the hint label to the center of the selected cell
      this.hintLabel.setBounds(center.getRow() - 5, center.getCol() - 15,
              hintLabel.getPreferredSize().width,
              hintLabel.getPreferredSize().height);

      this.hintLabel.setVisible(true);
    } else {
      this.hintLabel.setVisible(false);
    }
    revalidate();
    repaint();
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    this.panel.mouseClicked(e);
  }

  @Override
  public void mousePressed(MouseEvent e) {
    this.panel.mousePressed(e);
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    this.panel.mouseReleased(e);
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    this.panel.mouseEntered(e);
  }

  @Override
  public void mouseExited(MouseEvent e) {
    this.panel.mouseExited(e);
  }
}