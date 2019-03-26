CompSci 307: Simulation Project Analysis
===================

> This is the link to the [assignment](http://www.cs.duke.edu/courses/compsci307/current/assign/03_simulation/):

Design Review
=======

### Status

What makes the code well-written and readable (i.e., does it do what you expect, does it require comments to understand)?

One way that our code is well-written and readable is our use of descriptive packages. Right away, just by browsing our
directory you can easily tell that we used a model-view-controller design. Further, within model, we have more packages
to help separate the code further: board, cell, rules. Thus, for example it is easy to look at how we create a board object and how
board objects may vary based on the subclasses within the board package. Further, our view package, which was written mainly
by Ed, also has a good level of readability, as the classes are separated by the individual portion of the view they control.
For example, BoardView controls the display of the board, ControlView controls the display of the buttons atop the frame that allow a user
to control the simulation, and NewConfigView controls the display of the pop up that is activated when the new config button
is pressed.

Another factor that makes our code easily readable, is our extensive javadoc commenting and descriptive method names.
Additionally, most of our methods are relatively short (less than 20 lines) and most of our methods serve to complete only
one thing. For example, consider the following two methods:
```java
public int[][] findNeighbors(int[][] neighborsType) {
        // code to get expectedNeighbors based on current cell's coordinates
        int[][] tempNeighbors = getTempNeighborsForType();
        for (int i = 0; i < tempNeighbors.length; i++) {
            tempNeighbors[i] = chooseAppropriateNeighbors(neighborsType, i);
        }
        return tempNeighbors;
    }

private int[] chooseAppropriateNeighbors(int[][] neighborsType, int i) {
    if (edgeType == 0) {
        return getNeighbor(myX, myY, neighborsType[i]);
    } else if (edgeType == 1) {
        return getNeighborFinite(myX, myY, neighborsType[i]);
    }
    return getNeighborFlipped(myX, myY, neighborsType[i]);
}
```
Originally this code was combined into one function, but we decided that separating it into two made the code more readable
as we these methods still have clear objectives, and now are more aptly named. Although there are a few methods that are
over 20 lines (still less than 50), that are slightly difficult to read due to a decent number of conditional statements,
we found these methods difficult to split up in any meaningful way. For example:
```java
    private void getRulesFromLine (String line, int type) {
        String[] splitByWhiteSpace = line.split("\\s+");
        Integer startState = Integer.parseInt(splitByWhiteSpace[0]);
        Integer endState = -1;
        Integer altEndState = -1;
        if (type == 4 ) {
            endState = Integer.parseInt(splitByWhiteSpace[2].split(",")[0]);
            try {
                altEndState = Integer.parseInt(splitByWhiteSpace[2].split(",")[1]);
            } catch (ArrayIndexOutOfBoundsException ex) {
                throw ex;
            }
        } else {
            endState = Integer.parseInt(splitByWhiteSpace[2]);
        }
        String[] splitByColon = splitByWhiteSpace[1].split(":");
        Integer desiredNeighborState = Integer.parseInt(splitByColon[0]);
        String requiredAmount = splitByColon[1];

        if (requiredAmount.equals("x")) {
            for (int i = 0; i < 9; i++) {
                int[] tempRuleArray = {startState, desiredNeighborState, i, endState, altEndState};
                rulesArray.add(tempRuleArray);
            }

        } else  if (requiredAmount.equals("p")){
            int min = getMinFromProbability();
            for (int i = min; i < numberOfNeighbors+1; i++) {
                int[] tempRuleArray = {startState, desiredNeighborState, i, endState, altEndState};
                rulesArray.add(tempRuleArray);
            }

        } else{
            int[] tempRuleArray = {startState, desiredNeighborState, Integer.parseInt(requiredAmount), endState, altEndState};
            rulesArray.add(tempRuleArray);
        }
    }
```
This method parses the rules from a rules.txt file. Since there are multiple rule formats, it was necessary to have these
conditionals in order to account for each of the possible types.

What dependencies between the code are clear (e.g., public methods and parameters) and what are through "back channels" (e.g., static calls, order of method call, requirements for specific subclass types)?

With separate classes for each individual "object" in our simulation, there are many dependencies in our code.

The model package consists of various classes used to operate the backend of our code. Each of the classes in model, is
"summarized" through the Simulation class. Thus, the Simulation class has dependencies with each of the three main classes
that operate our model: Board, Cell, and Rules. The simulation is a Board of Cells, in which cells update based on Rules,
thus we have a logical reason to think that these dependencies would exist. To prove this consider the following code:
```java
public Simulation(Board board, Rules rule){
        myBoard = board;
        myCells = board.getCells();
        myRules = rule;
}
```
You can see the dependency on Board and Rules, as these are both arguments in the constructor, additionally the dependency on
Cells, is apparent through the call on the Board object to the method getCells()

The abstract class Board is dependent on the abstract class Cell and its subclasses as well as CSV parser and Rules Parser.
This is apparent in the Board constructor:
```java
public Board(ResourceBundle myProperties) {
        myGame = myProperties.getString("type_of_game");
        edgePolicy = myProperties.getString("edge_policy");
        myParser = new CSVParser(myProperties);
        neighborType = myParser.getNeighborType();
        cells = myParser.getCells();

        myHeight = myParser.getMyHeight();
        myWidth = myParser.getMyWidth();
    }
```
As you can see in the code above, the constructor, gets the corresponding CSV file from the ResourceBundle argument,
then creates a CSV parser object. This CSV parser object then allows us to store a 2D cell array that represents the board,
which highlight the dependencies on Cell and its subclasses. Lastly, we can see the dependency on Rules and Rules Parser, through
the following method:
```java
public abstract Cell[][] updateBoard(Rules rules);
```
Since the public method updateBoard, which updates the 2D array of cells, has a Rules object as its argument, we know that
there is a dependency between Rules and Board.
Another class that has many dependencies is the abstract class Cell. There are dependencies with GridShape, State, Board,
Rules, and Rules Parser.
The dependency on GridShapeType and State are logical because in our design each Cell object has an associated state and an associated
shape. In retrospect, I think that instead of the dependency between Cell and GridShape, our code would make more sense if the
dependency were between Board and GridShape, since for any given Board of Cell objects, each of the Cells will always have the
same shape as the other cells. That being said, the dependencies on GridShapeType and State are apparent through the following segment of code:
```java
public GridShapeType getMyGridShapeType() {
    return myGridShapeType;
}
@Override
public boolean equals(Object obj) {
    if (obj.getClass().equals(this.getClass())){
        Cell tempCell = (Cell) obj;

        if (tempCell.myState == this.myState && tempCell.myX == this.myX && tempCell.myY == this.myY) {
            return true;
        }
    }
    return false;
}

```
Note that in the method, equals, although State is not an argument for the function, equivalent cells are identified in part
by comparing the state of the object.

The other dependencies, on Board, Rules and Rules Parser, are apparent in the following method:
```java
    public int getNextState(Rules currentRules, Board board) {
        for (State state : currentRules.getPossibleStates()) {
            if (myState == state.getMyState()) {
                for (int[] rule : state.getRulesForState()) {
                    int actual = findNumberOfNeighborsInState(rule[1], neighbors, board);
                    if (currentRules.getMyRulesParser().getType() == RULES_TYPE_WITH_CHRONONS) {
                        if (currentChronons == maxChronons){
                            currentChronons = 0;
                            reproductionFlag = true;
                            return rule[4];
                        } else {
                            currentChronons ++;
                            return rule[3];
                        }
                    } else {
                        if (actual == rule[2]) {
                            return rule[3];
                        }
                    }
                }
            }
        }
        if (currentRules.getMyRulesParser().getType() == RULES_TYPE_WITH_CHRONONS) {
            currentChronons++;
        }
        return this.getMyState();
    }
```
You can see that this public method takes both a Rules object and a Board object, in order to calculate the correct next
state for any given cell. This is because, in order to update a cell, you must look at that cells state, then find the rules
that apply for that initial state from the Rules object, and then find the next state based on the states of the neighboring
cells which is derived from the Board object. The dependency on Rules Parser is a result of the call to getMyRulesParser(),
which allows the method to check the type to see if we need to deal with Chronons.

Another class with many dependencies is the SimulationController class. It has dependencies on Board and its subclasses,
Rules, Simulation, BoardView, ControlView, GraphView, MainView, and RightView. The controller is dependent on most of the classes within
the view Package because this class is the only class that talks directly to both the view and the model. Thus any updates
of the model, must be first relayed to the controller in order to be reflected in the view. We can see the dependency on BoardView,
RightView, GraphView, ControlView and MainView quite clearly through the following methods:
```java
private MainView myMainView;
private BoardView myBoardView;
private ControlView myControlView;
private RightView myRightView;
private GraphView myGraphView;

private void setUpScene(){
        BorderPane tempRoot = new BorderPane();
        myMainView = new MainView(myBoardView, tempRoot,this, myControlView, myRightView, myGraphView);
        startSimulation = myMainView.getMyStartBoolean();
        myScene = myMainView.getScene();
    }
```
Further we can see the dependencies on Board, Simulation, and Rules through the constructor:
```java
 public SimulationController( ResourceBundle myProperties) {//Will change to instantiating simulation and simulationView inside controller, not as input
        this.myProperties = myProperties;
        boardType = getBoardType();
        getBoard();
        myRules = new Rules(myProperties);
        myBoardView = new BoardView(myBoard, myProperties, this, false, false);
        mySimulationModel = new Simulation(myBoard, myRules);
        useImage = false;
        myImageList = new ArrayList<>();
        initMyPropList();
        myControlView = new ControlView(this);
        myGraphView = new GraphView(myProperties);
        myRightView = new RightView(this, myBoardView,myGraphView);
        getStateData();
        myFramesPerSecond = 1;//magic number that is set for now, need to be changed into form of input later
        mySimulationModel.setStart();
        setUpScene();
        setTimeline();
}
```

Lastly, the MainView class also has many dependencies: SimulationController, BoardView, ControlView, GraphView,
and RightView. The dependencies on those classes within the view package, are used to allow, all of the view objects to
be compiled into one object, the MainView object. You can easily see all of these dependencies in the constructor:
```java
 public MainView(BoardView bv,BorderPane root, SimulationController sc, ControlView cv, RightView rv, GraphView gv) {
        myStartBoolean = false;
        myBoardView = bv;
        myRightView = rv;
        myControlView = cv;
        this.myRoot = root;
        myRoot.setTop(this.makeTop());
        myRoot.setCenter(this.makeCenter());
        myRoot.setRight(this.makeRight());
        myScene = new Scene(myRoot, VIEW_WIDTH, VIEW_HEIGHT);
        myScene.getStylesheets().add(getClass().getResource("/simulationStyle.css").toExternalForm());
 }
```
You can see that each of the dependencies listed above is an argument in the constructor. Although there are other dependencies
in the code, I have chosen to highlight the main dependencies that most directly impact the functionality of our simulation.

You can put links to commits like this: [My favorite commit](https://coursework.cs.duke.edu/compsci307_2019spring/example_bins/commit/33a37fe42915da319f7ae140c8e66555cf28d2c8)


### Overall Design

Reflect on how the program is currently organized.
As briefly as possible, describe everything that needs to be done to add a new kind of simulation to the project.
Describe the overall design of the program, without referring to specific data structures or actual code (focus on how the classes relate to each other through behavior (methods) rather than their state (instance variables)).
Justify why your overall code is designed the way it is and any issues the team wrestled with when coming up with the final design.

You can also make lists:

* Bullets are made with asterisks

1. You can order things with numbers.


### Flexibility
Describe what you think makes this project's overall design flexible or not (i.e., what new features do you feel it is able to support adding easily).

Overall I think that the project is relatively flexible, as many new features can be implemented with ease.
The implementation of a new edge policy is relatively simple. In the abstract class Cell, currently there are
three methods that allow for the implementation of a specific edge policy: getNeighbors() (torodial),
getNeighborsFinite(), and getNeighborsFlipped(). To implement a new edge policy, a new method must be written in
Cell in the same manner as the existing methods listed above. Once a method has been written, something like
getNeighborsNewEdgePolicy(). Then in order to use this edge policy in a simulation, one must add a conditional
statement into chooseAppropriateNeighbors(), that checks if the nth policy is selected make a call to
getNeighborsNewEdgePolicy(). Lastly, edge policy is directly specified in the properties file for any given,
thus to implement this policy you must create a new properties file or modify an existing one so that
edge_policy = NewPolicy. The data for edge policy in the properties file is parsed within the abstract Class Board.
So, in order to implement this parsing, one must modify the method getEdgeType(), with a conditional that checks
if the string matches the name of the new policy, and if so returns an integer n, that represents our nth policy.
I will not go into as in depth detail for implementing the following new features, as this can be read about in
the DESIGN.md file. Other features that can be implemented with relative ease are new Board types, and Cell types,
as each of these can be done by creating a new subclass of the corresponding abstract class. Each subclass does not
need many functions, for example HexCell only require 3 unique methods, and GenericBoard only requires two.

Similarly, implementing new visual features is also relatively simple, as you can see in the view package, there is a separate
class for each of the separate elements in our display, so if you want to create a new element, one must just create a new
class in the view package. Once this has been accomplished a new instance of that class must be declared in the SimulationController
class and correspondingly added to the setUpScene() method, which should calls the MainView constructor with the new class object
as an argument, thus in order for this to be possible MainView must be modified as well to implement the new view feature.

Overall, I think that our design is semi-flexible because for any given feature that you want to add to the game, the  most
classes that you will need to modify is about 4. That being said, ideally our design would have been more flexible so that adding
these new features requires modifications for the minimum number of classes possible.

Describe one feature from the assignment specification that was implemented by a team mate in detail:
One feature that Ed implemented was the option to create a new configuration. The class NewConfigView, is responsible for
the pop-up display, that appears when the "new configuration" button is clicked. The window that appears displays a form,
to be filled out in order to create a new config, and then upon clicking "submit" the new configuration is compiled into
a resource file and then added to the existing list of configurations accessible through load configuration.
```java
    public NewConfigView(SimulationController sc){
        myProperties = ResourceBundle.getBundle("english");
        mySimulationController = sc;
        myStage = new Stage();
        setScene();
        myStage.setScene(myScene);
        myStage.setTitle(TITLE);
        myStage.show();
    }

    private void setScene(){
        myRoot = new VBox();
        setComponent();
        setGames();
        setShapes();
        setEdgePolicies();
        setNeighborPolicies();
        setDropDown();
        setBottom();
        myRoot.setAlignment(Pos.CENTER);
        myScene = new Scene(myRoot,PAGE_WIDTH, PAGE_HEIGHT);
        myScene.getStylesheets().add(getClass().getResource("/simulationStyle.css").toExternalForm());
    }

    private void setGames(){
            List<String> gameList = new ArrayList<>();
            for(String game : GameList){
                gameList.add(game);
            }
            ObservableList<String> tempGameOptions = FXCollections.observableArrayList(gameList);
            myGames = new ComboBox(tempGameOptions);
            myGames.setPromptText(myProperties.getString("choose_a_game_prompt"));
            myRoot.getChildren().add(myGames);
        }
```
In the code above from NewConfigView, we can see how the constructor which takes a SimulationController object,
sets up the scene for the new config popup to be displayed. You can see above, that after setting the properties
file, the simulation controller, and stage, the scene is then set up with a call to setScene(). The method, setScene(),
has various calls to other methods, that set up the various entries required form, but first it must create a VBox element
which will serve as the root that all of the options are added to. Since all of them are relatively similar,
I have just chosen to show the code for setGames(). You can see that when setScene calls setGames, a list is created, to
hold the options the user can choose from for this entry, then the list is made Observable, so that we are able to use
it in a ComboBox object. The ComboBox, object, will then have a list of each game, and users may select the game they want
to simulate in their new configuration. Lastly, the prompt is set for the initial display of the new configuration window,
and then the ComboBox is added to the VBox object mentioned above so it can be displayed in the scene. After all of the options
have been added, then the root is aligned, the scene is set with the root, and the specific style is applied to the scene.

What is interesting about this code? (why did you choose it?)

I chose this feature, because I generally prefer to work on the backend, so I figured that I should try to better understand
how the frontend works, in order to be more prepared for when I inevitably have to work on frontend. I thought this particular
portion was interesting, because it used various objects that I have not had a lot of experience with such as ObservableList,
ComboBox, Stage and Scene.
Describe the design of this feature in detail? (what parts are closed? what implementation details are encapsulated? what assumptions are made? do they limit its flexibility?)

How flexible is the design of this feature? (is it clear how to extend the code as designed? what kind of change might be hard given this design?)

You need to put blank lines to write some text

in separate paragraphs.


### Your Design

Reflect on the coding details of your part of the project.

Describe an abstraction (either a class or class hierarchy) you created and how it helped (or hurt) the design.

Two abstractions that I created were the abstract class Board, and the abstract class Cell. Each of these proved to be very
helpful in adding new features to our simulation. Each of the Board subclasses: FireBoard, GenericBoard, PredatorPreyBoard,
and SegregationBoard have separate updateBoard() methods that appropriately update the entire board of cells. Since the
updateBoard() method varies based on simulation type, we are able to easily differentiate between which update, to
use by identify the subclass of the Board object to be updated. Originally, Board was a very large class, that was not
abstract and had no subtypes. Thus, in order to accommodate the different ways that a given simulation must be updated,
we had one very large updateBoard function, with many conditionals, to identify what type of simulation is being run.
The abstraction of the Board class made it particularly easy to add in the new simulation types. For example, although
the Fire simulation, had a widely different update method than say Game of Life it was not overly difficult to add the new
FireBoard as all that was needed was one updateBoard() method that suffices for the Fire simulation. By having an abstract
class Board, the Simulation class which has an abstract Board object as an argument, was able to accept any subtype of board.
Thus if a new simulation is desired, that requires a new updateBoard() method, we do not have to worry about the changes
propagating through to the Simulation class. Similarly with the abstract class Cell and its subclasses: HexCell, RectangleCell,
and RhombusCell by creating the abstraction we were able to reduce the amount of convolution in the Cell, class by specifying
the neighbors of a given cell shape, in its respective Cell subclass. For example HexCell has three methods: findNeighbors(),
getTempNeighborsForType(), and the constructor, HexCell(). This allows us to differentiate between how the neighbors are
calculated based on the Cell shape, without having this propagate up into, all of the instances of Cell in other classes.

Discuss any Design Checklist issues within your code (justify why they do not need to be fixed or describe how they could be fixed if you had more time).

One issue that we had, that was apparent in the lab, when we got the summary analysis of our code, was our use of magic values.
Although we fixed the majority of the instances, we decided, that it was not the best use of our time to correct, all the
instance of numbers as magic values. For example, we used a set-sized array to hold, the coordinates of the neighbor
values for  cells, thus if the neighbors of a rectangular cell are Von Neumann we would need an array of size 4, whereas
with a Moore Neighborhood, we would need an array of size 8. Ideally, we would have corrected these issues as well, but
we decided that our time would be better spent correct other issues.

Another issue on the Design Checklist that is not totally resolved is the frequent use of getters and setters. For example,
in the abstract class Cell, there are many getters and setters that are used to modify the instance variables of cells.
I think that the use of getters and setters is limited to only a few classes, so I did not think this a problem that needed
to be addressed in our refactoring.

Lastly, I think that our design could have benefited from reduced direct dependencies. When doing the dependencies analysis,
I was surprised in some instances of the dependencies between classes. For example, SimulationController has a dependency on
Board, and Cell. However, since Simulation also has these dependencies, I think that we should have designed it so that Simulation
Controller only has a dependency on Simulation.

Describe one feature that you implemented in detail:

One feature that I implemented was the class FireBoard, and its updateBoard() method. Since the updating of a cell in the
Fire simulation is dependent on the state of its neighboring cells and probability, a new updateBoard() method was necessary,
as opposed to the updateBoard method from the GenericBoard class. First, in the constructor, that takes a ResourceBundle as an
argument, we must call the constructor from Board as well as getting the two values in the ResourceBundle, for catch probability
and grow probability.
```java
public FireBoard(ResourceBundle myProperties) {
        super(myProperties);
        growProbability = Double.parseDouble(myProperties.getString("grow_probability"));
        catchProbability = Double.parseDouble(myProperties.getString("probability"));
}
```
Then to use the probabilities retrieved from the ResourceBundle, a new updateBoard() method was written:
```java
public Cell[][] updateBoard(Rules rules) {
        Random generator = new Random();
        double number;
        Cell[][] tempCells = new Cell[super.getMyHeight()][super.getMyWidth()];
        for (int i = 0; i < super.getMyHeight(); i++) {
            for (int j = 0; j < super.getMyWidth(); j++) {
                Cell tempCell = super.getCells()[i][j];
                number = generator.nextDouble();
                int currState = tempCell.getMyState();
                int nextState = tempCell.getNextState(rules, this);
                if (number < growProbability && currState == 0) {
                    tempCells[i][j] = tempCell;
                } else if ( currState == 1) {
                    if (tempCell.findNeighborsInState(2, tempCell.getNeighbors(),this).size() > 0 && number < catchProbability) {
                        tempCells[i][j] = createNewCellFromSubClass(tempCell, nextState, j, i, super.getMyHeight(), super.getMyWidth(), super.getNeighborType(), -1, -1, super.getEdgeType());
                    } else {
                        tempCells[i][j] = tempCell;
                    }
                }else {
                    tempCells[i][j] = createNewCellFromSubClass(tempCell, nextState, j, i, super.getMyHeight(), super.getMyWidth(), super.getNeighborType(), -1, -1, super.getEdgeType());
                }
            }
        }
        super.setCells(tempCells);
        return tempCells;
```
You can see above that a random number is generated in order to simulate the probability of a tree catching on fire and
the probability of a tree growing. Then a Cell[][] object is filled in order to simulate the update of the board. Using
nested for loops, I loop through each of the current cells, store the current state of the cell and store the next state
of the cell, as calculated by the method in the abstract class cell. Then I check if the current state of the cell was 0,
this indicates an empty cell, thus it is possible that a tree will grow into this cell, and if the random number is less than
the grow probability, we create a new cell with state 1 to indicate that a tree has grown into this cell. Then, we must check, if the
current state is 1, if that is the case, a tree is in this cell, and thus has a chance of catching on fire. Again,
we compare the random number however, this time with catchProbability, to simulate the likelihood of a tree catching on fire.
For this to happen, we must look at the states of its neighbors to see if any of its neighbors are on fire and put this tree
at risk of catching on fire too. Thus if the probability is satisfied we added the new cell created with next state to the temporary
board. Thus if a tree catches on fire or if a tree grows into an empty cell, a new cell is created with the next state and added to
the temporary board. A new cell is also created when a fire burns out after one turn. Otherwise, the current cell will be
placed into the temporary board and the calculated next state is essentially disregarded.

Provide links to one or more GIT commits that contributed to implementing this feature, identifying whether the commit represented primarily new development, debugging, testing, refactoring, or something else.

[fixed fire board update] (https://coursework.cs.duke.edu/compsci307_2019spring/simulation_team11/commit/d4cb1d1bce35071570f26b7dfd0f14ee88d7426e):

[made board an abstract class, and split into subclasses] (https://coursework.cs.duke.edu/compsci307_2019spring/simulation_team11/commit/fc87b34bb2713baebf78c8cd9ad7a9b4b675748e):

These two commits represent all the changes made to FireBoard throughout the process.

Justify why the code is designed the way it is or what issues you wrestled with that made the design challenging.

Although I am not sure whether or not this question is directly related to the one above, I am going to assume so, as justification
of the high-level design issues can be read about elsewhere in this document and in the DESIGN.md document. The implementation
of FireBoard relied on the use of the abstract class Board. It was particularly, helpful to create an abstract class in this
case, because it allowed us to separate out the update methods, without using a series of conditionals. Originally I attempted
to shove all of this information into a single Board class, but found this to be overwhelming, as Board quickly became cluttered
with code that was specific to only one simulation type.

Are there any assumptions or dependencies from this code that impact the overall design of the program? If not, how did you hide or remove them?

Since this is a subclass, the dependencies on the FireBoard class are minimal, as there are dependencies on its parent class
Board. One assumption that is made, is that the ResourceBundle has access to the necessary values to simulate the probabilities.
If the wrong properties file is used, one without grow_probability or catch_probability, the update method, will not work, in
retrospect, I should have made it so that, if no probabilities are specified, we assume some probabilities, but unfortunately,
I did not think of this until, I started writing this portion of the analysis.


### Alternate Designs

#### Reflect on alternate designs for the project based on your analysis of the current design.

One alternative design that I considered during the project and further in the analysis, was a different set of subclasses
for the abstract class Cell. In cell, there are methods which are related to chronons for the implementation of the
Predator Prey simulation, so I think that is would've been logical to make subclasses based on the simulation type, similar
to how Board is abstracted, rather than based on cell shape. Instead to account for the differences in cell shape, we could've
created a separate class CellShape, with subclass for rectangle, rhombus, and hexagon. This was we would be able to create
a subclass of Cell specific to Predator Prey so that all of the chronon related methods can be contained in the class that
would be contained within the PredatorPreyCell instead of Cell. I think I would have preferred this design as it would have
made the Cell class more succinct.
