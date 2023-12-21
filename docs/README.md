# README: Reversi

Welcome to the README for our Reversi game project.
This document provides an overview of the project's purpose, gameplay, architectural choices, and
what to expect in terms of design and implementation.

### Overview

Reversi is a two-player game played on a regular grid of cells.
Players are assigned colors, either black or white, and the game pieces are dual-colored discs (one
side black and the other white).
Game play begins with an equal number of black and white discs arranged in the center of the grid.
In our version, we use a hexagonal grid.
Player Black takes the first turn.
On each turn, a player can either pass to let the opponent move or place a disc in a legal empty
cell.
A move is legal for Player A if the placed disc is adjacent to another disc of Player A.
A move is also legal for Player A if the placed disc is adjacent to a straight line of the
opponent's discs, with another disc of Player A at the far end.
This kind of move is called a 'sandwich', and results in the opponent's discs between two Player A
discs being flipped to Player A.
A game is over if neither player can make a move (2 passes in a row).
After a game is declared over, we can determine the winner by seeing which player has more discs of
their color on the final board.

The initial layout of the game should look something like the diagram below.

![img1.png](READMEimgs%2Fimg1.png)

Legal and illegal moves are illustrated below, showing the consequences of each move.

LEGAL MOVE:
Player X turn (* represents where Player X wants to move)

![img2.png](READMEimgs%2Fimg2.png)

LEGAL MOVE:
Player X turn (* represents where Player X wants to move)

![img3.png](READMEimgs%2Fimg3.png)

LEGAL MOVE: **double sandwich**
Player X turn (* represents where Player X wants to move)

![img4.png](READMEimgs%2Fimg4.png)

ILLEGAL MOVE:
Player X turn (* represents where Player X wants to move)

![img5.png](READMEimgs%2Fimg5.png)

ILLEGAL MOVE:
Player X turn (* represents where Player X wants to move)

![img6.png](READMEimgs%2Fimg6.png)

### Quick Start

There is no way to "start" our game just yet -- since we still need to implement more aspects of our
game.
However, to get an idea of how our game is going to work, take a look at /src/Reversi (our main
method is in there).

### Key Components

Our Reversi game does not rigidly adhere to the traditional model-view-controller (MVC) structure.
Instead, it revolves around several interconnected components that drive the game:

Player Manager: Manages the players, whether human or AI, and handles their moves.
Rule Keeper: This component orchestrates the game's rules, player's turns, and control flow,
ensuring fair gameplay.
Controller: This component takes what the rule keeper confirmed as fair game play and communicates
that change to the model.
Model: Responsible for representing the hexagonal game board and maintaining the state of the game.

## Key Subcomponents

Within each key component, we have various subcomponents that play crucial roles:

# Player Manager

Player Interface: Provides a consistent interface for both human and AI players to interact with the
game.
Player Decision Logic: Handles player decisions on moves and strategy (if AI).

# Rule Keeper

Turn Logic: Controls whose turn it is and enforces the rules of legal moves and capturing.
Game State: Keeps track of the game's current state, such as the number of pieces on the board.
Game Ender: Determines when the game ends due to no legal moves.

# Controller

Game Updates: Manages the communication of rule keeper decisions to update the game's state.
User Interface Interaction: Handles user input and communication between the model and the view (
alongside rule keeper).

# Game Board

Board Representation: Represents the hexagonal game board and its contents, including the discs and
empty cells.
Coordinate System: Manages the coordinate system of the board to specify cell locations.
Move Validator: Validates and enforces the rules of valid moves and captures.

### Architectural Choices

We chose to implement many different architectural choices in such a way that the implementation
could easily be changed.

# Builder pattern for ReversiModel

Allows for different implementations of the rule keeper, cell type, number of layers, and player
colors.

# IModel interface

Allows for different kinds models/games to be implemented, not just standard Reversi.

# ICell interface

Allows for different kinds of cells to be used (with different properties, not different number of
sides though because it was specified we would only be using hexagonal cells).

# Move interface

Allows for different kinds of moves to be made, including but not limited to pass and play disc.

# Player interface

Allows for different players and different kinds of players (AI, human, etc.).

# RuleKeeper interface

Allows for different rule sets to be used for the game.

# ITextualView interface

Allows for different textual representations to be used.

# RuleKeeper and Controller

Allows for more clarity between what should even be tried on the model, as well as flexibility for
rule set and controller combinations.

# ReadOnlyReversiModel

Ensures that the user/player does not mutate anything in the model, throws exceptions for any
methods that are mutation methods (not "getters").

# DiscColor enum

Includes associated textual representation (toString) and Color values (value) for use in textual
view and later on when we make an gui.GUI view.

## Source Organization

To navigate the source code effectively, we've organized it as follows:

### docs DIRECTORY: Contains the Javadoc documentation for the project.

docs/JARfiles - Contains both JAR files for HW06 and HW07.

docs/READMEimgs - Contains the screenshots used in the README.

docs/screenshots - Contains the screenshots of the GUI.

docs/JARfiles/HW06.jar - Contains the JAR file for HW06. To run the jar file, you have to navigate
to the directory that contains the jar file in the terminal, then enter the
following command to get it started: "java -jar HW06.jar"

docs/JARfiles/HW07.jar - Contains the JAR file for HW07. To run the jar file, you have to navigate
to the directory that contains the jar file in the terminal, then enter the
following command to get it started: "java -jar HW07.jar"

docs/README.md - This document.

docs/strategy-transcript.txt - Contains the transcript of our simplest strategy.

### src DIRECTORY: Contains the source code for the project.

#### src/controller: Contains classes related to the controller component in the MVC architecture.

src/controller/IController.java - An interface for the controller.

src/controller/ReversiController.java - The controller for the Reversi game.

#### src/events: Contains classes relating to events, their listeners, and notifications.

src/events/IModelEvent.java - Represents a model event that allows the active controller to
communicate with the model, and then the model to communicate with all its subscriber controllers.

src/events/IMoveEvent.java - Represents a move event that allows the controller to communicate with
its player and consequent strategy.

src/events/IPanelEvent.java - Represents an IPanelEvent interface that allows the canvas to
communicate with its view.

src/events/IViewEvent.java - Represents a view event that allows the view to communicate with its
controller.

#### src/gui: Contains classes related to the game's GUI view component in the MVC architecture.

src/gui/GUIHexCell.java - A GUI representation of a HexCell.

src/gui/GUIPanel.java - A GUI interface that represents the game board.

src/gui/GUIView.java - A GUI interface that represents the overall game's view.

src/gui/ReversiGUIPanel.java - A GUI representation of the Reversi game board.

src/gui/ReversiGUIView.java - A GUI representation of the Reversi game's view.

#### src/model: Contains classes related to the model component in the MVC architecture.

###### src/model/board: Contains classes related to the game board components, such as coordinate system, game status, etc.

src/model/board/Coordinate.java - A representation of a coordinate on the game board.

src/model/board/IModel.java - An interface for the model.

src/model/board/ReadOnlyModel.java - An interface for a read-only model.

src/model/board/ReadOnlyReversiModel.java - A read-only model for the Reversi game.

src/model/board/ReversiModel.java - A model for the Reversi game.

src/model/board/Status.java - An enum for the status of the game.

###### src/model/cell: Contains classes related to the cells used on the board (in our case HexCells) and their color.

src/model/cell/DiscColor.java - An enum for the color of the discs.

src/model/cell/HexCell.java - A representation of a hexagonal cell.

src/model/cell/ICell.java - An interface for a cell.

#### src/move: Contains classes related to a move object.

src/move/Move.java - An interface for a move.

src/move/Pass.java - A representation of a pass move.

src/move/PlayDisc.java - A representation of a play disc move.

#### src/player: Contains classes related to players of the game.

src/player/AIPlayer.java - An AI player.

src/player/HumanPlayer.java - A human player.

src/player/Player.java - An interface for a player.

#### src/ruleskeeper: Contains classes related to the game's rules, turns, and control flow.

src/ruleskeeper/ReversiRuleKeeper.java - A rule keeper for the Reversi game.

src/ruleskeeper/RuleKeeper.java - An interface for a rule keeper.

#### src/strategy: Contains classes related to the game's strategies.

src/strategy/AbstractCompositeStrategy.java - An abstract class for a composite strategy.

src/strategy/AbstractSingleStrategy.java - An abstract class for a single strategy.

src/strategy/AIStrategy.java - An interface for an AI strategy (a calculated strategy)

src/strategy/AvoidCornerNeighbors.java - A single strategy that avoids corner neighbors.

src/strategy/CaptureMostCells.java - A single strategy that captures the most cells.

src/strategy/HumanStrategy.java - A strategy that takes human input.

src/strategy/IStrategy.java - An interface for a strategy.

src/strategy/PrioritizeCorners.java - A single strategy that prioritizes corners.

src/strategy/TryToBlockCompositeStrategy.java - A composite strategy that tries to block.

src/strategy/TryToBlockSingleStrategy.java - A single strategy that tries to block.

src/strategy/TryToWinCompositeStrategy.java - A composite strategy that tries to win.

#### src/view: Contains classes related to the game's textual view component in the MVC architecture.

src/view/ITextualView.java - An interface for a textual view.

src/view/ReversiTextualView.java - A textual view for the Reversi game.

#### src/Reversi.java - The main method for the Reversi GUI game.

#### src/ReversiTextual.java - The main method for the Reversi game (textual version).

#### src/ReversiWithController.java - The main method for the Reversi game, implementing the controllers and players.

### test DIRECTORY: Contains the test code for the project.

#### test/hw06tests directory: contains the tests for the HW06 portion of the project.

test/hw06tests/model: Contains the tests for the model component of the MVC architecture.

test/hw06tests/board: Contains the tests for the classes related to the game board components, such
as coordinate system, etc.

test/hw06tests/cell: Contains the tests for the classes related to the cells.

test/hw06tests/model/board/AbstractReversiModelTests.java - An abstract class for the Reversi model
tests.

test/hw06tests/model/board/CoordinateTests.java - Tests for the Coordinate class.

test/hw06tests/model/board/CopyConstructorModelTests.java - Tests for the copy board functionality
of the Reversi model.

test/hw06tests/model/board/ReadOnlyModelTests.java - Tests for the ReadOnlyModel interface.

test/hw06tests/model/board/ReversiModelTests.java - Tests for the ReversiModel class.

test/hw06tests/model/cell: Contains the tests for the classes related to the cells used on the
board (in our case HexCells) and their color.

test/hw06tests/model/cell/CellTests.java - Tests for the Cell class.

test/hw06tests/model/cell/DiscColorTests.java - Tests for the DiscColor enum.

test/hw06tests/move: Contains the tests for the move objects.

test/hw06tests/move/MoveTests.java - Tests for the Move objects.

test/hw06tests/player: Contains the tests for the player objects.

test/hw06tests/player/PlayerTests.java - Tests for the Player objects.

test/hw06tests/ruleskeeper: Contains the tests for the rule keeper.

test/hw06tests/ruleskeeper/ReversiRuleKeeperTests.java - Tests for the ReversiRuleKeeper class.

test/hw06tests/strategy: Contains the tests for different player strategies (single and composite).

test/hw06tests/strategy/AbstractCompositeStrategyTests.java - An abstract class for the composite
strategy tests.

test/hw06tests/strategy/AbstractStrategyTests.java - An abstract class for the strategy tests (
single and composite).

test/hw06tests/strategy/AvoidCornerNeighborsTests.java - Tests for the AvoidCornerNeighbors class.

test/hw06tests/strategy/CaptureMostCellsTests.java - Tests for the CaptureMostCells class.

test/hw06tests/strategy/MockReadOnlyModel.java - A mock read-only model for testing purposes.

test/hw06tests/strategy/PrioritizeCornersTests.java - Tests for the PrioritizeCorners class.

test/hw06tests/strategy/TryToBlockCompositeStrategyTests.java - Tests for the
TryToBlockCompositeStrategy class.

test/hw06tests/strategy/TryToBlockSingleStrategyTests.java - Tests for the TryToBlockSingleStrategy
class.

test/hw06tests/strategy/TryToWinCompositeStrategyTests.java - Tests for the
TryToWinCompositeStrategy class.

test/hw06tests/view - Contains the tests for the textual view.

test/hw06tests/view/ReversiTextualViewTests.java - Tests for the ReversiTextualView class.

#### test/hw07tests DIRECTORY: Contains the tests for the HW07 portion of the project.

test/hw07tests/controller: Contains tests pertaining to the controller.

test/hw07tests/events: Contains tests pertaining to events (IModelEvent, IMoveEvent, IPanelEvent, IViewEvent).

test/hw07tests/model: Contains tests pertaining to the startGame exceptions.

test/hw07tests/player: Contains tests pertaining to the AI and human players, as well as human strategies.

test/hw07tests/controller/ReversiControllerTests.java - Contains tests pertaining to our reversi controller implementation.

test/hw07tests/events/EventTests.java - Contains tests pertaining to all the events (IModelEvent, IMoveEvent, IPanelEvent, IViewEvent).

test/hw07tests/model/AbstractModelExceptionsTests.java - Contains tests that can be abstracted for both the read only and actionable models.

test/hw07tests/model/ReadOnlyModelNotStartedTests.java - Contains the read only model for the abstract tests.

test/hw07tests/model/ReversiModelNotStartedTests.java - Contains the actionable model for the abstract tests. Also has tests for executable commands like pass, playDisc, etc.

test/hw07tests/player/AIPlayerTests.java - Contains tests for the AI player. Also, provides the abstract PlayerTests with an AI player to use.

test/hw07tests/player/HumanPlayerTests.java - Contains tests for the human player. Also, provides the abstract PlayerTests with a human player to use.

test/hw07tests/player/HumanStrategyTests.java - Contains tests for the human strategy.

test/hw07tests/player/PlayerTests.java - Contains tests that can be abstracted for both the human and AI players.

#### This directory structure helps you locate specific components and their code.
#### For more in-depth details and implementation specifics, please refer to the Javadoc documentation in the relevant code files.

## Changes for part 2

1. Reversi Method Modification:
   The main Reversi method has been edited to display the GUI instead of using System.out for
   textual output.
   This logic has been moved into a new class, ReversiTextual.

2. Controller Update:
   The controller now accepts a GUIView as a parameter.

3. Model Restructuring:
   ReadOnlyModel is now its own interface, and Model extends it.
   ReadOnlyReversiModel implements the ReadOnlyModel interface, which exclusively contains observer
   methods. No need to throw exceptions for mutable methods.

4. Neighbor Methods Enhancement:
   Methods such as getSandwichableNeighbors, getLikeNeighbors, getOppositeNeighbors, and
   getNeighbors now take a player color as a parameter, facilitating their use in strategies.
   getNeighbors is now public instead of private, making it accessible for strategies.

5. Winner Determination Adjustment:
   The getWinner method now returns an Optional<DiscColor>.

6. Move Interface Modification:
   The getMoveCoordinate in the Move interface now takes an Optional instead of returning a null for
   a pass.

7. Interface Parameter Update:
   The Player interface now takes a ReadOnlyModel instead of an IModel.

8. RuleKeeper Parameter Update:
   RuleKeeper methods now take a ReadOnlyModel instead of an IModel, and isValid now takes an extra
   DiscColor player parameter for use in strategies.

9. View Class Modification:
   ReversiTextualView now takes a ReadOnlyModel instead of an IModel.

10. Additional Tests:
    Extra tests have been added to validate the updated functionalities.

### Additional Changes:

1. GUI Components Introduction:
   Introducing a new GUIHexCell class, a GUIView interface, an ICanvasEvent interface, a
   ReversiGUIPanel class, and a ReversiGUIView class.

2. Coordinate Enhancement:
   Implemented a new compareTo method in the Coordinate class (requires testing).

3. ReadOnlyReversiModel Enhancement:
   Added a new getCopyBoard method to ReadOnlyReversiModel (requires testing).

4. Additional Model Methods:
   Introduced new methods in ReadOnlyReversiModel:
   getPlayerScore, getRuleKeeper, getPlayerColors, getPass, getOtherPlayerColor,
   getAllCoords, getReadOnlyCopyOfBoard, getActionableCopyOfBoard, and isOnEdge.

5. Strategy Classes Introduction:
   Created a new AbstractCompositeStrategy class,
   an AbstractSingleStrategy class,
   AvoidEdgeNeighbors class,
   CaptureMostCells class,
   HumanStrategy (yet to be implemented),
   an IStrategy interface,
   an AIStrategy interface,
   PrioritizeEdges class,
   TryToWinCompositeStrategy class,
   and a TryToBlockCompositeStrategy class.

6. Player Classes Introduction: (default behavior is to pass if no moves are available)
   Created a new HumanPlayer class,
   an AIPlayer class.

7. Move to a Specific Cell:
   Users can indicate their desire to move to a specific cell by clicking on that cell with their
   cursor / mouse.
   To pass, a user can press the P key.
   To confirm the selected cell as the move, a user can press the M key.

8. Test Coverage:
   Added the necessary tests to ensure the correctness of the newly introduced features.

# EXTRA CREDIT: Chained Strategies

In this project, we have extended the basic game implementation to incorporate advanced strategies
by chaining multiple components together.
Each strategy contributes to the overall decision-making process, allowing us to create
sophisticated gameplay.

Implemented Single Strategies: (all extend AbstractSingleStrategy which implements AIStrategy)

### Strategy 1: Capture Most Cells

Description: A simplistic strategy that focuses on capturing the most cells possible (via sandwich).
Implementation: CaptureMostCells class (in strategy package)
The CaptureMostCells strategy in our Reversi game implementation is designed
to guide a player in making moves that maximize the number of captured cells.
Moves that do not result in capturing any cells are excluded from the list of possible moves.
This strategy calculates the potential gains for each candidate move by
examining the sandwichable neighbors associated with each cell.
The number of cells that can be captured becomes the weight of a move.
The strategy then sorts the candidate moves in descending order of their weights,
ensuring that moves with a higher potential for capturing cells come first.
In case of ties, the strategy prioritizes the uppermost-leftmost coordinate (focusing on the
lesser sum of rows and cols to resolve ties).
This implementation not only captures the essence of the Reversi game's fundamental strategy,
but also provides a modular and flexible structure that can be easily integrated
with other strategies for a more sophisticated and adaptable gameplay experience.

### Strategy 2: Avoid Corner Neighbors

Description: Recognizing the strategic disadvantage, this strategy avoids placing discs in cells
adjacent to corners,
limiting the opponent's ability to secure corners on
Implementation: AvoidCornerNeighbors class (in strategy package)
The AvoidCornerNeighbors class represents a strategic approach for a player in a game,
specifically designed to discourage moves that have neighboring cells on the corner of the game
board.
In this strategy, the getStrategyPossibleMoves method filters a list of candidate moves
by considering the neighboring coordinates of each candidate.
The strategy identifies whether any neighbor is located on the corner, and if so,
the candidate move is excluded from the list of possible moves.
However, if the candidate move itself is on the corner, it is still considered a valid option.
This implementation ensures that the player avoids making moves that could potentially lead to a
neighbor on the corner,
promoting a more strategic and tactically advantageous gameplay.

### Strategy 3: Prioritize Corners

Description: This strategy aims for corners because discs placed in corners cannot be captured,
providing a long-term advantage.
Implementation: PrioritizeCorners class (in strategy package)
The PrioritizeCorners class defines a strategy where a player prioritizes making moves on the
corners of the game board.
In this strategy, the getStrategyPossibleMoves method filters a list of candidate moves by selecting
only those coordinates that are on the corners.
The implementation checks each candidate move and adds it to the list of possible moves if it is
determined to be a corner coordinate.
This strategic approach encourages the player to focus on occupying positions at the corners of the
game board,
potentially providing positional advantages or influencing the overall gameplay.
By specializing in corner moves, this strategy aims to shape the player's decision-making to align
with a particular tactical goal,
contributing to a more nuanced and diverse gameplay experience.

### Strategy 4: Try To Block

Description: This strategy enables a player to counter an opponent's move by blocking corresponding
candidate moves of a given single strategy.
Implementation: TryToBlockSingleStrategy class (in strategy package)
The TryToBlockSingleStrategy class represents a strategy where a player attempts to block a single
move made by the opposing player.
This strategy is composed of another single strategy, denoted as strategy, and aims to identify
and prioritize moves that can prevent the opposing player from achieving a specific goal.
In the getStrategyPossibleMoves method, the implementation filters a list of candidate moves
based on whether those moves coincide with the possible moves of the opposing player.
It leverages the strategy field to obtain the possible moves of the opposing player.
The process involves checking each coordinate in the opposing player's possible moves and adding
it to the list of possible moves for the current player if it is also present in the list of
candidate moves.
This strategy is designed to counteract the opposing player's moves by identifying situations
where the player's moves align with the opposing player's potential victories or advantageous
positions.
By blocking specific moves, the player employing this strategy aims to disrupt
the opposing player's plans and gain a strategic advantage in the game.

### Implemented Human Strategy: (implements IStrategy)

Strategy: Human Strategy
Description: Takes human input through the GUI and translates it to a move.
Implementation: The Human Strategy class is in the strategy package. It implements the IStrategy
and IViewEvent interfaces. Whenever the human player makes a move, by pressing the
M or P keys to indicate a (PlayDisc or Pass move), the Human Strategy class is notified of the move
through the Human Player. Once a choice is set, the controller acts appropriately to update the model.

## Implemented Composite Strategies:

##### both extend AbstractCompositeStrategy, which extends AbstractSingleStrategy and implements AIStrategy

To enhance the sophistication of our gameplay, we have designed the system to allow for the dynamic
combination of strategies.
This flexibility enables us to create various levels of AI intelligence by stacking strategies in
different sequences.

### Composite Strategy 1: Try To Win

Description: This composite strategy allows for the combination of multiple single strategies
to be stacked and used to find the best possible move, taking all strategies into account.
Implementation: TryToWin class in strategy package
The TryToWinCompositeStrategy is a composite strategy designed for a player to make moves
strategically
with the goal of winning the game. This strategy combines multiple sub-strategies in a specific
order,
aiming to prioritize certain types of moves over others. The order of strategies, from outermost to
innermost,
is enforced and includes: 1. PrioritizeCorners, 2. AvoidCornerNeighbors, and 3. CaptureMostCells.
The strategy iteratively filters candidate moves through each sub-strategy,
ensuring that the final list of possible moves is the result of the intersection of moves favored by
all strategies.
If an empty list is encountered at any point in the filtering process, the default behavior
is to try the original candidate moves from the most beneficial strategy, which is
PrioritizeCorners,
to the least beneficial strategy, which is CaptureMostCells until a non-empty list is obtained.
If no valid moves are found, the strategy returns an empty optional.
The implementation checks the validity of the strategy order and prevents duplicate strategies
within the composite.
This hierarchical approach enhances the player's decision-making process,
providing a sophisticated strategy to increase the chances of winning.

### Composite Strategy 2: Try To Block

Description: This composite strategy allows for the combination of multiple single strategies
to be stacked and used to find the best move that allows us to block our opponent,
taking all strategies into account.
Implementation: TryToWin class in strategy package
The TryToBlockCompositeStrategy is a composite strategy designed for a player to make moves
strategically
with the intention of blocking the opponent. Similar to the TryToWinCompositeStrategy,
this strategy combines multiple sub-strategies in a specific order,
enforcing the order from outermost to innermost as: 1. PrioritizeCorners, 2. AvoidCornerNeighbors,
and 3. CaptureMostCells.
The primary objective of this strategy is to consider moves that obstruct the opponent's winning
opportunities.
The implementation begins by creating a default TryToWinCompositeStrategy to obtain possible moves
for the opposing player.
It then intersects these moves with the possible moves of the current player,
resulting in a refined list of moves that strategically block the opponent.
The strategy order is validated to ensure it follows the specified sequence and avoids duplicate
strategies within the composite.
Additionally, a helper method createTryToWinCompositeStrategy is introduced to construct a
version of the TryToWinCompositeStrategy specific to this TryToBlockCompositeStrategy.
This method assists in checking the order of strategies and evaluating the possible moves of the
opposing player.
In summary, the TryToBlockCompositeStrategy provides a comprehensive approach for
a player to strategically obstruct the opponent's progress, offering a sophisticated decision-making
process
based on a combination of strategies.

## Handling Ties and No Valid Moves:

In situations where multiple strategies result in equally good options or when no valid moves are
available,
our implementation resolves ties by choosing the move with the uppermost-leftmost coordinate (for
testing purposes).

## Generalization of Strategy Signatures:

To facilitate easy recombination of strategies, we have generalized the signature of each strategy.
Unlike the Tic Tac Toe strategies, our strategies return more than just a single move possibility.
The inclusion of potential scores accommodates varying complexities in decision-making.

## More Complex AI Implementation Flexibility:

Our implementation allows for the creation of more complex AI implementations by combining multiple
strategies.
The flexibility of our implementation enables us to create a variety of AI implementations with
different levels of sophistication.
For example, we can create a more complex AI by combining the TryToWinCompositeStrategy and
TryToBlockCompositeStrategy.
This AI would be able to make moves that are both strategically advantageous and tactically
disruptive.
The ability to combine strategies in different sequences allows us to create a diverse range of AI
implementations,
contributing to a more engaging and challenging gameplay experience.

# Changes for HW07

#### 1. Refactored tests

We have refactored our tests to improve code organization. Tests for the previous parts of the
homework are now located in the test/hw06tests directory, while tests for this part of the homework
are in test/hw07tests. This restructuring aims to enhance code navigation. Details about these
changes are also updated in the Source Organization section of the README.

#### 2. Disc shown if cell is selected

In response to feedback received on the previous part of the homework, where users mentioned, "When
clicking a cell with a disc, the entire cell turns blue; should still be able to see the disc," we
have adjusted our view to address this issue. Now, the selected cell highlights without obstructing
the visibility of the disc.

#### 3. Fixed java doc

Based on feedback from the last part of the homework, we have made improvements to our JavaDoc
comments to provide more descriptive and comprehensive documentation.

#### 4. ReversiWithController main

We made a new main method, which fully runs the game with the controller, players, strategies, etc.
To set up the game, the user is prompted with a System.out message: "What do you want player 1 to be? Type h for human or a for AI."
The main method then waits for the user to type in either "h" or "a" to indicate their first player.
Then, the user is prompted with "What do you want player 2 to be? Type h for human or a for AI. Type r to reselect."
If for either of the players, the user chooses an AI player, the user is then prompted with:

"How difficult do you want the AI player to be?
1. Easy
2. Medium
3. Hard "

After selecting the AI difficulty, either (1, 2, or 3 on the command line), the user then is prompted with a final message:
"Press 0 to start or r to reselect."
If the user doesn't press 0, the user is re-prompted until they do. Once they press 0, the game is rendered and they can begin playing.

#### 5. Notifications
In order for the model, controller, and view to be able to communicate with one another only when necessary, we created "events".
One of these events is the IModelEvent which allows the active controller to communicate with the model, and the model to communicate with all its subscriber controllers.
Another event, IMoveEvent allows the controller to communicate with its player and consequent strategy.
An IPanelEvent allows the canvas to communicate with its view.
Lastly, an IViewEvent allows the view to communicate with its controller.
For testing, we redirected System.out to a PrintStream to verify that the correct Objects were being notified of the correct events.
To see this in full detail, please visit test/hw07tests/events/EventTests.java.

## Additional Changes For HW07

#### 1. startGame method

We have implemented a startGame method to facilitate the initialization of the game.

#### 2. Controller

The controller has undergone modifications to enhance its functionality and improve overall game
control.

#### 3. Human strategy

A human strategy has been incorporated, allowing for human players to make decisions during the
game.

#### 4. Human Player

The HumanPlayer class, which previously had default behavior, has been updated to accommodate
decisions made by human players.

#### 5. AI Player

Similar to the HumanPlayer, the AIPlayer class has been modified to introduce strategic
decision-making for AI players.

#### 6. selectedCell no longer static

The selectedCell attribute is no longer static in the ReversiGUIPanel.

#### 7. updateCanvas method

We have introduced a method, updateCanvas which takes in a boolean parameter.
If the parameter is true, it signifies the only change to be made is unselecting the currently
selected cell.
So, it will unselect the cell without redrawing the grid.
If the parameter is false, it will redraw the entire grid, which is especially useful in scenarios
involving sandwiches.

#### 8. Score on view

We added the score to the view so players can more easily see who is in the lead.
# Screenshots for HW07
### game set up with command line:
![cmdLine.png](READMEimgs%2Fhw07screenshots%2FcmdLine.png)
### selected cell
![selectCell.png](READMEimgs%2Fhw07screenshots%2FselectCell.png)
### your turn notification
![yourTurn.png](READMEimgs%2Fhw07screenshots%2FyourTurn.png)
### invalid move notification
![invalidMove.png](READMEimgs%2Fhw07screenshots%2FinvalidMove.png)
### cannot play for AI notification
![cannotPlayForAI.png](READMEimgs%2Fhw07screenshots%2FcannotPlayForAI.png)
### you tied notification
![youTied.png](READMEimgs%2Fhw07screenshots%2FyouTied.png)
### you won notification
![youWin.png](READMEimgs%2Fhw07screenshots%2FyouWin.png)
### you lost notification
![youLose.png](READMEimgs%2Fhw07screenshots%2FyouLose.png)


## Assignment 9: Flipping the Script – Reversi, part 5

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