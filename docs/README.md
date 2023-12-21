## Assignment 9: Flipping the Script – Reversi, part 5

#### by Nathalie Abello & Celine Semaan

### Level 0: Showing Scoring Hints

- Implemented a "hint" mode that shows how many discs would be flipped for the selected move.
- Hints are toggleable at runtime for each player independently, by pressing the "h" key.
- Maintained proper design by avoiding direct modifications to rendering code, used the decorator
  pattern.
- In order to use the decorator pattern we made GUIViewHintDecorator and GUIPanelHintDecorator which
  essentially wrap their respective classes (delegate) and add hint functionality.

### Level 1: Square Reversi

- Reimplemented the model (SquareReversiModel) for square grid Reversi with capturing in eight
  directions.
- In order to reimplement the model without redundancy, we added an abstract model (
  AbstractReversiModel).
- Implemented a textual rendering for the square model. (SquareTextualView)

### Level 2: Visualizing Square Reversi

- Implemented a visual view for square-grid Reversi. (ReversiGUISquarePanel)
- In order to avoid overlap, we created an abstract panel. (AbstractGUIPanel)
    * The only difference between square panel and hex panel is the cells they use & how they render
      the grid (drawGrid method).
- Implemented a square cell. (GUISquareCell)
- In order to avoid overlap, we created an abstract gui cell. (AbstractGUICell)
- Both Hexagonal and Square views extend the same abstract panel class, which extends JPanel and
  implements GUIPanel and MouseListener.

### Level 3: Controlling Square Reversi

- Did not have to alter our controller to work with both Hexagonal and Square Reversi.
- We did not have to parameterize anything because we followed the same coordinate system -- axial.

### Level 4: Strategic Square Reversi

- Did not have to alter our strategy implementation(s) to work with both Hexagonal and Square
  Reversi.
- We did not have to parameterize anything because we followed the same coordinate system -- axial.