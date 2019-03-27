CompSci 307: Simulation Project Analysis
===================

> This is the link to the [assignment](http://www.cs.duke.edu/courses/compsci307/current/assign/03_simulation/):

Design Review
=======

--------------------------------------------------
### Status

#### What makes the code well-written and readable
While coding this program, we tried hard to make sure that the code stays readable. We tried to break up all the methods as much as we can to give single
responsibility to a single module. Also we tried to make our method names short and intuitive while keeping it descriptive. Also we packaged our codes to make it
easier to understand the overall structure of our code. It is divided up into four packages, model, view, controller and tests. Each of the names
correspond to TDD development style and MVC framework which gives an external reader of codes a general idea which component would be located at which of the packages.
Also we tried to encapsulate related features closer to each other.
 
If you see in our model package, we have three sub-packages that has related classes such as classes that are
related to board features and cell package has all the classes that are related to cell component of the model. However, I do think that some of our classes and methods can be shorter if we had
implemented better encapsulation.
 
For example, currently the SimulationController is about 340 lines, including comments and javadocumentation. This could have been reduced to
about hundred lines if I had used appropriate inheritance or abstraction, or taken different approach in designing interaction
between the view component and model component. I will elaborate on this later. 


One other thing that make the codes in the view component easy to read is how its nested and encapsulated to meet composite pattern. Each classes are separated by the portion of the view they are responsible
of. However, I think there is a lot of room for improvement in this component in terms of encapsulation and readability. I will also elaborate this later on in this analysis.
#### What makes this project's code flexible or not
There are certain components that makes this code flexible and others that decreases the flexibility of the program. 

One of the features that make the project's code more flexible is the abstraction that is done in the model package. We have abstract class Board.java and Cell.java that
constitute the basis for possible variation and extension on creating different types of board and cells. Also we have adhered to the heuristic of having no public instance variable.
These two features combined helped us adhere to the open-closed principle--this has helped our code to increase in flexibility. 
```java
public abstract class Board {
    private Cell[][] cells;
    private int myWidth;
    private int myHeight;
    private String myGame;
    private int neighborType;
    private final int[] orderToReplace = {2, 1, 0};
    private CSVParser myParser;
    private String edgePolicy;
    }
    
public abstract class Cell{
    private int type;
    private int myX;
    private int myY;
    private int[][] neighbors;
    private int myState;
    private int boardHeight;
    private int boardWidth;
    private int currentChronons;
    private int maxChronons = 10;
    private int currentEnergyLevel;
    private int edgeType = 0; //0 = torodial, 1 = finite, 2 = torodial-flipped
    private GridShapeType myGridShapeType;
    private boolean reproductionFlag = false;
    private final int NUM_CARDINAL_NEIGHBORS = 4;
    private final int NUM_ALL_BUT_LEFT_NEIGHBORS = 5;
    private final int NUM_NORMAL_NEIGHBORS = 8;
    private final int CARDINAL = 2;
    private final int ALL_BUT_LEFT = 3;
    private final int RULES_TYPE_WITH_CHRONONS = 4;
    }
    
public abstract class CellGetter {
    private Cell[][] myCells;
    String csvName;
    private String myType;
    private int myHeight;
    private int myWidth;
    private int maxState;
    private int neighborType;
    private GridShapeType myGridShapeType;
    private String gameName;
    private int errorStatus;
    private String errorType;
    private double[] myProbs;
    private double[] myCounts;
    private int edgePolicy;
    }
    
```
However, looking back at some of the instance variables, I do believe that the abstract Cell.java could have been shorter if it had less instance variables and methods.

Another aspect that makes the overall design flexible is the separation between the view and the model. In the previous project(game) one of the design mistakes I made was making components that was
responsible for both view and the model (with excessive use of the imageView class in javafx package). Having the view and model combined made adding
different view components later on harder as the project progressed. However, this time, we did it differently and tried to have the view and the model as
separated as possible starting from earlier stage of development. 

Final aspect that makes this code flexible is the way we have divided up the visual components in many different classes.
And within developing the visual components, we tried to incorporate composite pattern by layering and nesting the views. This had made
adding new visual components convenient for us. The basic idea of adding a  new visual component goes like this: we add a pane that will fit into
the main view, which is a border pane. The border pane is divided up into five parts: top, bottom, left, right, and center. So if there is a new visual
component then we would create a class or a component that has a Pane and add it to one of the five parts of the border pane. 

Each different view components are instantiated in the Mainview and put together on a borderPane object.
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
To look at this in depth, lets consider how

myRoot.setTop(this.makeTop());

works.

```java
 private Node makeTop(){
        return myControlView.getMyRoot();
    }
```
This method is in MainView.java, which is called when myRoot.setTop(this.makeTop()); is called.
What goes inside the top component of the Boarder Pane is an instance of ControlView that has some buttons that controls certain
aspects of the simulation the program is running that the moment. 

The uppermost layer of ControlView is an HBox object that takes in buttons and a slider object as its children.
```java
    public ControlView (SimulationController sc){
        myProperties = ResourceBundle.getBundle("english");
        myStartBoolean = false;
        mySimulationController = sc;
        myRoot = new HBox();
        myPropertiesList = sc.getMyPropertiesList();
        setView();

    }
    
    private void setView(){
        makeDropDown();
        setButtons();
        SpeedSlider speedSlider = new SpeedSlider(mySimulationController);
        Slider tempSlider = speedSlider.getMySlider();
        Label tempLabel = new Label(myProperties.getString("slider_label"));
        tempLabel.setLabelFor(tempSlider);
        myRoot.getChildren().add(tempSlider);
        createGridOutlineButton();
    }
```

As a result, if we were to make a change that affects only the visual aspect of the program, we would only have to modify the classes in view package.
Similarly, if we want to change something in the model, then we modify the model package and leave the view package intact. By having two components separated we were able
to end up with a code base that is self-contained and concise, easy to modify even towards later end of the project.

#### What dependencies between the code are clear

One of the aspects that we have failed in designing this program is that we were unable to contain the number of dependencies between different classes.
Although most of the classes have relatively simple set of dependencies, couple of the classes definitely has way too many of them.

One of the classes that definitely has excessive dependencies is the BoardView.java. This class has three different constructor and each of them has many dependencies.

```java
    public BoardView( Board board, ResourceBundle properties,SimulationController sc, boolean grid, boolean image, List<Image> list){
        this( board, properties, sc, grid, list, Color.WHITE, Color.BLACK, Color.BLUE);
        myImageArray = list;
    }

    public BoardView(Board board, ResourceBundle properties, SimulationController sc, boolean grid, boolean image){
        this(board, properties, sc, grid, null, Color.WHITE, Color.BLACK, Color.BLUE);
    }


    public BoardView( Board board, ResourceBundle properties, SimulationController sc, boolean grid, List<Image> images, Color c0, Color c1, Color c2){
        myProperties = properties;
        myBoardWidth = board.getMyWidth();
        myBoardHeight = board.getMyHeight();
        myBoard = board;
        useStroke = grid;
        mySimulationController = sc;
        myImageArray = images;
        myGridShape = myBoard.getCellAtCoordinates(0,0).getMyGridShapeType();
        myColor0 = c0;
        myColor1 = c1;
        myColor2 = c2;
        myStrokeColor = Color.BLACK;
        useImage = false;
        cellHeight = BOARD_HEIGHT/myBoardHeight;
        cellWidth = BOARD_WIDTH/myBoardWidth;
        myImageViewBoard = new ImageView[myBoardWidth][myBoardHeight];
        myColorBoard = new Shape[myBoardWidth][myBoardHeight];
        myRoot = new Group();

        if(myImageArray!=null) {
            myRoot = createImageBoard(myBoardWidth, myBoardHeight);
        } else if (myGridShape == GridShapeType.RECTANGLE) {
            myRoot = createColorBoardRect(myBoardWidth, myBoardHeight);
        } else {
            myRoot = createColorBoardPolygon(myBoardWidth, myBoardHeight);
        }
    }

```
The code piece shown above is the constructors of BoardView.java. Different constructors were used to handle different use cases
for the boardView object. The dependencies include Board.java, properties file, Simulation controller, a boolean value, list of images and three color object.

I think I could have definitely boil that down by making many different design choices. First, the need to have a simulation controller object seems quite bizarre and seem to complicate the dependencies.
I should have implemented a dependency inversion using inheritance between the model and the views to reduce all the dependencies that goes through the controller class. One way I could have done this is implement some variation
of Observable, Observer interfaces for model and view. One other way is having to import three colors separately. I could have wrapped around the three color object in a customized data structure or have made an ArrayList of Colors.
Then, I should have not used a boolean as an input, but just used a different kind of constructor that takes different set of input types because now I have boolean and different constructors that
takes care of different edge cases. I should have chosen just one of the two methods of taking care of different cases. This code we have at the moment is very redundant.  
  
Another class that has a lot of dependencies is the MainView.java class. However, the dependencies for this class seems to be easier to justify then
those for BoardView.java class.
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
Most of the dependencies in this Class are other view components that are used in the MainView object.

One of the classes that I think has justifiable dependency is the SimulationController.java.
Although the code did end up to be one of our longest classes, we only have one input into that class which the ResourceBundle Properties file.
I think it is reasonable for the controller class, which is in charge of making sure all of the program works properly, to take in as a properties file as an input
because that file is the file that should configure the entire game. 
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

--------------------------------------------------
### Overall Design

#### As briefly as possible, describe everything that needs to be done to add a new kind of simulation to the project.

The first thing we have to do to add a new simulation we would need files that a configuration file requires to set up a new simulation.
These include Rules text file and a CSV file that configures the initial configuration. Next, depending on the type of the game, if the game involve choronons or probabilities, we may
have to add another type of Board object and add the option of choosing the board option to the simulation controller. Finally, we would add the option to the CSVParser to ensure the simulation gets constructed.
Then it would automatically be added down in the drop down for new configurations.

#### Describe the overall design of the program, without referring to specific data structures or actual code

Three design principles we tried to achieve in implementing this project are open-closed principle, dependency inversion and single-responsibility.
In general, I think we were able to implement a certain aspects of Open-Closed and single responsibility principles but we feel that we could have done better
in implementing inversion of dependency.

To be more specific, we have implemented a version of MVC pattern with completely self-contained model part(does not have any javafx codes) and a separate view components.
These two are instantiated in the Controller class and interact with each other through controller class.

We have created an abstract class of Board.java and Cell.java that can be extended to increase flexibility in implementing 
different types of cells and games that require different rules and settings. 

We used Enumeration to handle different kinds of GridShapeType.

In creating visualization components, we used composite pattern and the views have nested structure that is easy to add or modify in the process of 
development.

And in writing most of our methods, we tried to have short methods that have single responsibility and encapsulate related features properly.

However, we have failed to inverse the dependency between the View and the model and have a system where the controller class is a middleman taking care of all the aspect of
update which has made our controller component larger then it had to be.

We have packaged the three aspects of programs into separate folders for others to look at our packages and understand each classes' roles better.

#### Justify why your overall code is designed the way it is and any issues the team wrestled with when coming up with the final design.

One design choice we implemented was the use of abstract classes for the Board and Cell objects. By creating classes to
extend board and cell, we were able to reduce the amount of code in each class to that code that is directly specific to
that type of class, whether it be cell shape, or game type.
 
By extending the cell class, and using that implementation,
it allowed us to more easily add new game types, by either using one of the existing types if applicable,
and if not by creating a new subclass that only overrides the necessary functions. This will also address many of the
instances of duplicate code as adding subclasses will eliminate the need for functions that are very similar but produce
different results.
 
For example, in Cell there are various methods for getting neighbors according to the current shape
of the cell, to mitigate this, by creating subclasses for each of the cell shapes, we were able to simply override the
getNeighbors() method with the appropriate values for that given shape. 

Similarly in board, the update board
functionality had multiple methods to allow for the different game rules, by adding subclasses of the board that are
specific to game type we were able to reduce the amount of code in board in general, and reduce the amount of code in
certain methods especially the number of conditionals, by using the variables we define explicitly in the subclasses.

--------------------------------------------------
### Flexibility

#### Describe what you think makes this project's overall design flexible or not
There are certain components that makes this code flexible and others that decreases the flexibility of the program. 

One of the features that make the project's code more flexible is the abstraction that is done in the model package. We have abstract class Board.java and Cell.java that
constitute the basis for possible variation and extension on creating different types of board and cells. Also we have adhered to the heuristic of having no public instance variable.
These two features combined helped us adhere to the open-closed principle--this has helped our code to increase in flexibility. 
```java
public abstract class Board {
    private Cell[][] cells;
    private int myWidth;
    private int myHeight;
    private String myGame;
    private int neighborType;
    private final int[] orderToReplace = {2, 1, 0};
    private CSVParser myParser;
    private String edgePolicy;
    }
    
public abstract class Cell{
    private int type;
    private int myX;
    private int myY;
    private int[][] neighbors;
    private int myState;
    private int boardHeight;
    private int boardWidth;
    private int currentChronons;
    private int maxChronons = 10;
    private int currentEnergyLevel;
    private int edgeType = 0; //0 = torodial, 1 = finite, 2 = torodial-flipped
    private GridShapeType myGridShapeType;
    private boolean reproductionFlag = false;
    private final int NUM_CARDINAL_NEIGHBORS = 4;
    private final int NUM_ALL_BUT_LEFT_NEIGHBORS = 5;
    private final int NUM_NORMAL_NEIGHBORS = 8;
    private final int CARDINAL = 2;
    private final int ALL_BUT_LEFT = 3;
    private final int RULES_TYPE_WITH_CHRONONS = 4;
    }
    
public abstract class CellGetter {
    private Cell[][] myCells;
    String csvName;
    private String myType;
    private int myHeight;
    private int myWidth;
    private int maxState;
    private int neighborType;
    private GridShapeType myGridShapeType;
    private String gameName;
    private int errorStatus;
    private String errorType;
    private double[] myProbs;
    private double[] myCounts;
    private int edgePolicy;
    }
    
```
However, looking back at some of the instance variables, I do believe that the abstract Cell.java could have been shorter if it had less instance variables and methods.

Another aspect that makes the overall design flexible is the separation between the view and the model. In the previous project(game) one of the design mistakes I made was making components that was
responsible for both view and the model (with excessive use of the imageView class in javafx package). Having the view and model combined made adding
different view components later on harder as the project progressed. However, this time, we did it differently and tried to have the view and the model as
separated as possible starting from earlier stage of development. 

Final aspect that makes this code flexible is the way we have divided up the visual components in many different classes.
And within developing the visual components, we tried to incorporate composite pattern by layering and nesting the views. This had made
adding new visual components convenient for us. The basic idea of adding a  new visual component goes like this: we add a pane that will fit into
the main view, which is a border pane. The border pane is divided up into five parts: top, bottom, left, right, and center. So if there is a new visual
component then we would create a class or a component that has a Pane and add it to one of the five parts of the border pane. 

Each different view components are instantiated in the Mainview and put together on a borderPane object.
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
To look at this in depth, lets consider how

myRoot.setTop(this.makeTop());

works.

```java
 private Node makeTop(){
        return myControlView.getMyRoot();
    }
```
This method is in MainView.java, which is called when myRoot.setTop(this.makeTop()); is called.
What goes inside the top component of the Boarder Pane is an instance of ControlView that has some buttons that controls certain
aspects of the simulation the program is running that the moment. 

The uppermost layer of ControlView is an HBox object that takes in buttons and a slider object as its children.
```java
    public ControlView (SimulationController sc){
        myProperties = ResourceBundle.getBundle("english");
        myStartBoolean = false;
        mySimulationController = sc;
        myRoot = new HBox();
        myPropertiesList = sc.getMyPropertiesList();
        setView();

    }
    
    private void setView(){
        makeDropDown();
        setButtons();
        SpeedSlider speedSlider = new SpeedSlider(mySimulationController);
        Slider tempSlider = speedSlider.getMySlider();
        Label tempLabel = new Label(myProperties.getString("slider_label"));
        tempLabel.setLabelFor(tempSlider);
        myRoot.getChildren().add(tempSlider);
        createGridOutlineButton();
    }
```
As a result, if we were to make a change that affects only the visual aspect of the program, we would only have to modify the classes in view package.
Similarly, if we want to change something in the model, then we modify the model package and leave the view package intact. By having two components separated we were able
to end up with a code base that is self-contained and concise, easy to modify even towards later end of the project.

#### Describe one feature from the assignment specification that was implemented by a team mate in detail:
This function is implemented by Jaiveer and it calculates the coordinates of the polygon object that is needed to create a hexagonal board.
```java
private Double[] getHexPoints(int rowNumber, int columnNumber){
        Double[] points = new Double[12];

        if(columnNumber % 2 == 0){
            points[0] = ((3 * cellWidth / 4) * columnNumber) + (cellWidth/4);
            points[1] = (rowNumber*cellHeight);

            points[2] = ((3 * cellWidth / 4) * columnNumber) + (3*cellWidth/4);
            points[3] = (rowNumber*cellHeight);

            points[4] = ((3 * cellWidth / 4) * columnNumber) + (cellWidth);
            points[5] = (rowNumber*cellHeight) + (cellHeight / 2);

            points[6] = ((3 * cellWidth / 4) * columnNumber) + (3*cellWidth/4);
            points[7] = (rowNumber*cellHeight) + cellHeight;

            points[8] = ((3 * cellWidth / 4) * columnNumber) + (cellWidth/4);
            points[9] = (rowNumber*cellHeight) + cellHeight;

            points[10] = (3 * cellWidth / 4) * columnNumber;
            points[11] = (rowNumber*cellHeight) + (cellHeight / 2);
        }
        else{
            // offset of cell height / 4
            points[0] = ((3 * cellWidth / 4) * columnNumber) + (cellWidth/4);
            points[1] = (rowNumber*cellHeight) + (cellHeight/2);

            points[2] = ((3 * cellWidth / 4) * columnNumber) + (3*cellWidth/4);
            points[3] = (rowNumber*cellHeight) + (cellHeight/2);

            points[4] = ((3 * cellWidth / 4) * columnNumber) + (cellWidth);
            points[5] = (rowNumber*cellHeight) + (cellHeight / 2) + (cellHeight/2);

            points[6] = ((3 * cellWidth / 4) * columnNumber) + (3*cellWidth/4);
            points[7] = (rowNumber*cellHeight) + cellHeight + (cellHeight/2);

            points[8] = ((3 * cellWidth / 4) * columnNumber) + (cellWidth/4);
            points[9] = (rowNumber*cellHeight) + cellHeight + (cellHeight/2);

            points[10] = (3 * cellWidth / 4) * columnNumber;
            points[11] = (rowNumber*cellHeight) + (cellHeight / 2) + (cellHeight/2);

        }
        return points;
    }
```
I find this piece of code really interesting because it is scalable in adding different shapes to the board. When I was first asked to add different
shapes to the board, I was thinking of making use cases for each of the different shapes. However, what it turned out to be was to use the built in Polygon
API in javafx. The clipped method above calculates mathematical location for each of the 12 points required to draw an hexagon. 

I choose this function for three reason. First is its conciseness. Although the function that calculates the points is quite long, the length is completely justified.
It contains only the core operations that is required in calculating 12 points. And because it uses the built in api, Polygon(), the return value of this method can be a 
parameter for Polygon()'s constructor which would create a hexagon and locate its position at the same time.

Second reason is its flexibility. Adding another shape just requires writing a method to find the points required, just like the one above. Then we just give the return value of that function
as parameter for Polygon()'s constructor. By this we can add any kind of shapes by just finding mathematical formula behind the shapes.

Third reason is that this code directly works with the view component, which most of it is written by me. However, in writing and adding this code into a code base, there were no
modifications made to existing code base and did not have to add a lot of code. Also it is very readable. Therefore, I think this code is a good example of open-closed principle being 
held in our development process, that adding a feature only required us to write new codes without the need to modify any of the previous codes.

--------------------------------------------------
### Your Design


#### Describe an abstraction (either a class or class hierarchy) you created and how it helped (or hurt) the design.
In writing my part of the code, which was most of the View package and the controller package, I did not implement any abstraction. There are some abstractions I wish I had implemented and there are some abstractions that
I thought about implementing it but decided not to do so because of conflicting issues. 

One of the abstractions I wish I had implement was Observable and Observer interface. I should have made these and let View components and model components to implement these interfaces so that
I can have inversion of dependency between the model and the view. I did attempt this when we were about half way in the project. However, at that point it was already too late to fix all of the methods and classes that has been implemented without having implemented these
interfaces. And I was worried that changing the basis of the program would cause a lot of miscommunication between the team members. Therefore, we have decided to not go futher with this refactoring and just keep the old method.

This sure did have a really negative impact on our program in general. By not having the observer pattern, we did not have view and model communicate between each other. Instead we had all of the communication
go through the controller class. This has lead the controller class to be excessively big with many methods that takes care of communication between view and model.
These methods include :
```java
    /**
     * Changes the Board whenever it needs to be changed
     */
    public void replaceBoardView(){
        if(useImage){
            myMainView.setMyBoardView(
                    new BoardView(mySimulationModel.getMyBoard(),myProperties,this, useGrid, useImage, myImageList));
        }
        else if(color0 != null) myMainView.setMyBoardView(
                new BoardView(mySimulationModel.getMyBoard(), myProperties, this, useGrid, null, color0, color1, color2));
        else if(useGrid){
            myMainView.setMyBoardView(
                    new BoardView(mySimulationModel.getMyBoard(), myProperties, this, useGrid, useImage));
        }
        else myMainView.setMyBoardView(
                    new BoardView(mySimulationModel.getMyBoard(), myProperties, this, useGrid, useImage));
    }
    
        /**
         * Called for every step of the Simulation, indicates changes need to be made to both model and view
         */
    public void next() {//need to update model and view for each step
        startSimulation = myControlView.getMyStartBoolean();

        if (startSimulation) {
            getStateData();
            if (mySimulationModel != null) {
                mySimulationModel.setStart();
                mySimulationModel.nextStep();

                //mySimulationModel.printMyCells();
            }
            replaceBoardView();

        }
    }
    
    /**
     * Replaces the board by creating a new board
     */
    public void setNewBoard(){
        myBoardView = new BoardView(myBoard,myProperties,this, useGrid, null, color0,color1,color2);
        myRightView  = new RightView(this, myBoardView, new GraphView(myProperties));
        myMainView.setMyBoardView(myBoardView);
    }
    
    

```
The way these methods work is that they are called by classes in View package. So the view class would call these methods that are in controller calss. Then these 
methods would call functions that are in Model package. 

Another inheritance I wish I created was smaller modules that composed the view components that was repeatedly used. In the GUI, I use a lot of Button or a HBox, VBox elements that I could have customized
by extending the javafx class to avoid having to call similar methods over and over again. 

For example :
```java
    private void setHBox0() {
        HBox temp = new HBox();
        Label tempLabel = new Label(myProperties.getString("state_0_color"));
        temp.getChildren().add(tempLabel);
        myColorPicker0 = new ColorPicker();
        temp.getChildren().add(myColorPicker0);
        temp.setAlignment(Pos.CENTER);
        myRoot.getChildren().add(temp);
    }

    private void setHBox1() {
        HBox temp = new HBox();
        Label tempLabel = new Label(myProperties.getString("state_1_color"));
        temp.getChildren().add(tempLabel);
        myColorPicker1 = new ColorPicker();
        myColorPicker1.setValue(Color.BLACK);
        temp.getChildren().add(myColorPicker1);
        temp.setAlignment(Pos.CENTER);
        myRoot.getChildren().add(temp);

    }

    private void setHBox2() {
        HBox temp = new HBox();
        Label tempLabel = new Label(myProperties.getString("state_2_color"));
        temp.getChildren().add(tempLabel);
        myColorPicker2 = new ColorPicker();
        myColorPicker2.setValue(Color.BLUE);
        temp.getChildren().add(myColorPicker2);
        temp.setAlignment(Pos.CENTER);
        myRoot.getChildren().add(temp);

    }
```
Much of the redundant code above could have been shorter if I had used the right encapsulation and made each of the visual elements more modular.

One of the abstractions I thought about making and decided not to implement was a view interface or abstract class. As you can see in our View package, most of the classes are
named somethingView.java. Naturally, all of these View classes do share some common features. However, I have concluded that they have more differences then the parts they share and the part they shared was 
not significant enough to put it in a separate class. For example, the ControlView is mostly consist of buttons and its interactions with the controller to change the state of the program. And The BoardView is consist of
a lot of math and graphical element to make a board and update its outputs. Therefore, these differences made me realize that there isn't a huge merit in abstracting out the views.

However, now that I think about it, I think I could have taken a slightly different approach on abstraction. Instead of trying to abstract out all the common aspects of all view classes, I could have tried to seek
common parts of couple of classes rather then all of the classes. For example, bot the controlView.java and RightView.java has characteristics of a toolbar. I should have
abstracted out a class named toolbar.java that is used in Control and Right so that when I later decide to add a tool bar to the bottom of borderPane I could benefit from the abstraction.

#### Discuss any Design Checklist issues within your code (justify why they do not need to be fixed or describe how they could be fixed if you had more time).

One of the things that my design lack is its absence of inheritance. As I have described above, there are many cases where abstraction would have improved our code in many ways. I deeply regret not thinking enough about this issue.

Also another problem is in BoardView in how I have three different constructors for different kind of board and a boolean to state whether the board uses a grid outline or not. All of these could have been improved if I used abstractions on BoardView and had different variations of BoardViews.
Also I do have some duplicated codes, such as
```java
    private void setHBox0() {
        HBox temp = new HBox();
        Label tempLabel = new Label(myProperties.getString("state_0_color"));
        temp.getChildren().add(tempLabel);
        myColorPicker0 = new ColorPicker();
        temp.getChildren().add(myColorPicker0);
        temp.setAlignment(Pos.CENTER);
        myRoot.getChildren().add(temp);
    }

    private void setHBox1() {
        HBox temp = new HBox();
        Label tempLabel = new Label(myProperties.getString("state_1_color"));
        temp.getChildren().add(tempLabel);
        myColorPicker1 = new ColorPicker();
        myColorPicker1.setValue(Color.BLACK);
        temp.getChildren().add(myColorPicker1);
        temp.setAlignment(Pos.CENTER);
        myRoot.getChildren().add(temp);

    }

    private void setHBox2() {
        HBox temp = new HBox();
        Label tempLabel = new Label(myProperties.getString("state_2_color"));
        temp.getChildren().add(tempLabel);
        myColorPicker2 = new ColorPicker();
        myColorPicker2.setValue(Color.BLUE);
        temp.getChildren().add(myColorPicker2);
        temp.setAlignment(Pos.CENTER);
        myRoot.getChildren().add(temp);

    }
```

#### Describe one feature that you implemented in detail:

One of the features I implemented is enabling the control components of the simulation. These include one step, start, pause, and etc. I have made that
boardView has an animation with given number frames per second. 

Then for every one of these steps, Simulation controller is called to update changes on the model. In helping controling the simulation, I have boolean values
that changes when the button on GUI is pressed. And the animation would be able to .play() only when this boolean is true. This piece connects all three components of the program.
When input is taken in from the GUI (visual component) then the Controller(which acts as a middleware) takes the input then modify the state of the Model. Then as the model has been updated, the Controller 
calls the view components to be updated. 

This is the link to feature mentioned above, committed after I completed debugging this feature. 
https://coursework.cs.duke.edu/compsci307_2019spring/simulation_team11/commit/5677cb5f0994eb1d2e73b38a240f5487908f7373

The dependencies used for this code has been start of many problems, mainly those that are related with failing to implement open-closed principle, observer pattern and etc.
By controller mitigating all the data exchange from Model to Controller, Controller class has gotten larger then it should be and updating the view as we added more feature became too complicated too quickly.
This is also the reason why I was unable to shift to using Observer pattern later in development. Then, I have a bad dependency where Model is dependent on both view and controller, Controller is dependent on model and view, and View is dependent on both
model and controller. This is unnecessary and redundant dependencies.


--------------------------------------------------------
### Alternate Designs

Some of the alternate designs I regret not choosing are :
   1. More encapsulation
   2. Less dependency
   3. Oberserver, Observable interfaces
   4. Abstraction on BoardView
   5. Abstraction on Toolbars
   6. Abstraction on SimulationController
   7. and other alternative designs I suggested above
