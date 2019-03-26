CompSci 307: Simulation Project Design (Team 11)
================================================



## Design Review


### Design Goals: provides the high-level design goals of your project




### How to Add New Features: explains, in detail, how to add new features to your project
There are a wide range of potential features that could be implemented in our project: new cell shapes, new edge policies,
new neighbor arrangements, new simulation types, and new visual features to name a few. 

* New Cell Shape:
Adding a new cell shape requires a little bit of changes in both view component and in the model component. We would need to
create another simulation model that deals with that specific cell shape because different cell shapes have different numbers of
neighbors so we need to accommodate the rules of the game with that cell shape. When this is done then we move on to making changes in the
visual component. Also we need to add the name of the shape that we want to add to the Enum GridSHapeType.java in model, shown below.

```java
package app.model;

public enum GridShapeType {
    RECTANGLE,
    HEXAGON,
    RHOMBUS

}
```

Then we would need to write out a cell type class that extends the abstract cell class which is the basis for all of the cells that we use.
An example of this is shown below.

```java
package app.model.cell;

import app.model.GridShapeType;

/**
 * Cell subclass for Rectangle Cells
 * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
 */
public class RectangleCell extends Cell {
    private static final int[][] NEIGHBORS_TYPE1 = {{-1, -1}, {-1, 0}, {-1, +1}, {0, -1}, {0, +1}, {+1, -1}, {+1, 0}, {+1, +1}};
    private static final int[][] NEIGHBORS_TYPE2 = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
    private static final int[][] NEIGHBORS_TYPE3 = {{0,1}, {0,-1}, {1, 1}, {1,0}, {1,-1}};


    /**
     * Rectangle Cell Constructor
     * <p>
     *     same as super but set gridshapetype to rectangle
     * </p>
     * @param state
     * @param x
     * @param y
     * @param boardHeight
     * @param boardWidth
     * @param neighborType
     * @param chronons
     * @param energy
     * @param edgeType
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public RectangleCell(int state, int x, int y, int boardHeight, int boardWidth, int neighborType, int chronons, int energy, int edgeType) {
        super(state,x,y,boardHeight,boardWidth,neighborType,chronons,energy,edgeType);
        super.setMyGridShapeType(GridShapeType.RECTANGLE);
        if (super.getType() == 1) {
            super.setNeighbors(findNeighbors(NEIGHBORS_TYPE1));
        } else if (super.getType() == 2) {
            super.setNeighbors(findNeighbors(NEIGHBORS_TYPE2));
        } else if (super.getType() == 3) {
            super.setNeighbors(findNeighbors(NEIGHBORS_TYPE3));
        }
    }
}
```
Adding another cell would require this to clear how the rules of the game would be applied to each of the cell types because different cells have different numbers of
neighbors. So we need to clarify which of the cells we see as one cell's neighbor and which ones to not think of as a neighboring cell.

The way we handle visualization of different cell shapes is through the view component. We use the Shape class polygon to to implement
different shapes. Therefore, in adding a different shape of cell we would create a method in creating boardView.java that would handle each of the cases.
This would be just different in how we calculate the coordinates that makes up each of the coordinates we use. 

For example, 
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
This method in BoardView.java is a method that is responsible for figuring out where each points of an hexagon need to be placed
on the boardview object. To add another shape, we would need to figure out how many points this shape has and how to calculate them and write a
function that has this responsibility. 

Then
```java
    private Group createColorBoardPolygon(int width_num, int height_num){
        var result = new Group();

        for(int i =0; i<width_num;i++){
            for(int j=0; j<height_num;j++){

                Cell c = myBoard.getCellAtCoordinates(i,j);
                Polygon myPoly = new Polygon();
                // add polygon points
                assignColor(c, myPoly);
                myColorBoard[i][j] = myPoly;

                if(myGridShape==GridShapeType.HEXAGON) {
                    myPoly.getPoints().addAll(getHexPoints(j, i));
                }

                else if(myGridShape==GridShapeType.RHOMBUS){
                    myPoly.getPoints().addAll(getRhombusPoints(j, i));
                }

                myPoly.setOnMouseClicked(e->cellClicked(c));
                setStroke(myPoly, myStrokeColor,STROKE_WIDTH);
                result.getChildren().add(myPoly);
            }
        }
        return result;
    }
```
As we can see in this method, we do a type check for which grid this board requires.(The GridShapeType )

myGridShape==GridShapeType.RHOMBUS

Therefore, adding a new cell shape would require another if statement in specifiying input points into the Polygon object.

* New Edge Policy:
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

* New Neighbor Arrangement:


* New Simulation Types:
To implement a new simulation such as the cellular automata, Seeds, you must first identify a few
characteristics about the new simulation. First you must write a new txt file to specify the rules of the new simulation.
The rules files are formatted with three initial lines, and then all subsequent lines represent the rules for that given
simulation. The first three lines are as follows:
    Simulation Name
    type:n (the type of Rules to Parse)
    0,1,2,...  (a comma seperated list of all the states)
Type is used in the method getRulesFromLine() in RulesParser to be able to correctly parse the rules. Currently, there
are four options in use, 1,2,3,4. For example game of life which will only have one destination state, so it's rules
are in format 1: (destination_state required_neighbor_state:number_in_required_neighbor_state destination_state); whereas,
PredatorPrey is in format 4: (destination_state required_neighbor_state:number_in_required_neighbor_state destination_state1,destination_state2)
So when adding a new simulation one must first identify which rules format will be sufficient and if none of the current
formats are sufficient, create a new format, and modify getRulesFromLine() accordingly to parse the rules.

In addition to the new rules txt file that must be created, one must also create a new csv config file for the new simulation
as well as a new properties file for the simulation. The CSV files are all the same format regardless of the type of game:
    Simulation Name
    height,width
    grid
which is then followed by a comma delineated 2D array, that represent the starting state of each of the cells in the grid.
The properties files also follow a common format, some of which may or may not be necessary for the new simulation:
    name_of_creator
    type_of_game
    description
    name_of_csv
    color0
    color1
    color2
    shape
    probability
    edge_policy
    neighbor_type
All of the board types are determined based on the type_of_game string, and then correspond to a subclass of the abstract
class Board. One of two options will be appropriate: either one of the existing classes will suffice for the new simulation
or a new class must be made, that is an extension of the Board class. The subclasses of Board mainly are used to distinguish
how the simulation's board will be updated. For example since Seeds is very similar to Game of Life, it is likely that one
would be able to use the same board class that Game of Life uses, GenericBoard. If there is no existing simulation that has
update rules that will suffice for it, a new subclass must be made. If a new subclass was created the method getBoardType()
and getBoard() in SimulationController must be modified to function with the new type of board.
name_of_creator, type_of_game, description, name_of_csv, color0, color1, color2 are self explanatory and take the according
strings. edge_policy and neighbor_type can be selected from the existing options based on what is satisfactory for the simulation.
For example currently if the simulation require torodial or finite edges, one may use edge_policy=torodial, and edge_policy=finite
respectively. For neighbor type, we have enumerated our options with 1 representing a moore neighborhood, 2 being the von
Neumann neighborhood and 3 being a modified version of the moore neighborhood.


* New Visual Features:
In developing our program we tried to maintain a strict separation between visual component of the app from other parts of the application.
And within developing teh visual components, we tried to incorporate composite pattern by layering and nesting the views. This had made
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

If we were to add another visual component to the top part, then in the setView(){} method, we will have to 
add another helper method that either creates or instantiates another component and add it to the HBox root of the ControlView.

For example, lets say we want to have a display screen that shows the status of each of the cells. This would be taking the data that goes into 
the line chart and displaying them numerically, like 

"70 % of the cells are in state 0"

"10 % of the cells are in state 1"

"20 % of the cells are in state 2"

To do this, we would first create a pane that would be able to hold these information. Then we would get the information that goes into 
these panes from the model component of the program. The we would add this component to the part that can fit this display area, for example
the bottom component of the Border Pane.


### Justification of Design: justifies major design choices, including trade-offs (i.e., pros and cons), made in your project

One design choice we implemented was the use of abstract classes for the Board and Cell objects. By creating classes to
extend board and cell, we were able to reduce the amount of code in each class to that code that is directly specific to
that type of class, whether it be cell shape, or game type. By extending the cell class, and using that implementation,
it allowed us to more easily add new game types, by either using one of the existing types if applicable,
and if not by creating a new subclass that only overrides the necessary functions. This will also address many of the
instances of duplicate code as adding subclasses will eliminate the need for functions that are very similar but produce
different results. For example, in Cell there are various methods for getting neighbors according to the current shape
of the cell, to mitigate this, by creating subclasses for each of the cell shapes, we were able to simply override the
getNeighbors() method with the appropriate values for that given shape. Similarly in board, the update board
functionality had multiple methods to allow for the different game rules, by adding subclasses of the board that are
specific to game type we were able to reduce the amount of code in board in general, and reduce the amount of code in
certain methods especially the number of conditionals, by using the variables we define explicitly in the subclasses.


### Assumptions: states any assumptions or decisions made to simplify or resolve ambiguities in your the project's functionality