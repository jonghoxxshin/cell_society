API DISCUSSION:

package app.controller;

public class Start extends Application {
      public void start(Stage stage) throws Exception
      EXTERNAL API
}

package app.controller;
public class SimulationController {
      public SimulationController(int height, int width, String game, ResourceBundle myProperties) //Will change to instantiating simulation and simulationView inside controller, not as input
    INTERNAL
    public void next() //need to update model and view for each step
    INTERNAL
    public void setConfig(String t1)
    INTERNAL
    public void replaceBoardView()
    INTERNAL
    public String createProperties(String propertiesFileName, String name, String type, String des, String csv, String gridShape, String edgePolicy, String neighborPolicy)
    INTERNAL
    public Simulation getMySimulationModel ()
    EXTERNAL
    public void setMyFramesPerSecond(int input)
    INTERNAL
    public ArrayList<String> getMyPropertiesList()
    EXTERNAL
    public Scene getMyScene()
    INTERNAL
    public int getMyFramesPerSecond()
    INTERNAL
    public void pauseSimulation ()
    EXTERNAL
    public void restartSimulation()
    EXTERNAL
    public void changeColor(Color c0, Color c1, Color c2)
    EXTERNAL
    public void setNewBoard()
    EXTERNAL
    public void changeGrid()
    EXTERNAL
    public void setImage(ArrayList<Image> imageList)
    INTERNAL
    public Map<Integer, Double> getStateData()
    EXTERNAL
}

package app.controller;
public class Main {
  }

package app.model;
public class GridShape {
      public GridShapeType getShape(String name)
      EXTERNAL
    public String getNameFromShape(GridShapeType shape)
    EXTERNAL
}

package app.model;
public class Simulation {
      public Simulation(Board board, Rules rule)
      INTERNAL
    public void nextStep()
    EXTERNAL
    public Rules getMyRules()
    EXTERNAL
    public void setStart()
    INTERNAL
    
}

package app.model.board;
public class PredatorPreyBoard extends Board {
      public PredatorPreyBoard(ResourceBundle myProperties)
      INTERNAL
    public Cell[][] updateBoard(Rules rules)
    INTERNAL
}

package app.model.board;
public class GenericBoard extends Board {
          public GenericBoard(ResourceBundle myProperties)
          INTERNAL
        public Cell[][] updateBoard(Rules rules)
        INTERNAL
}

package app.model.board;
public class SegregationBoard extends Board {
      public SegregationBoard(ResourceBundle myProperties)
      INTERNAL
    public Cell[][] updateBoard(Rules rules)
    INTERNAL
}

package app.model.board;
public class FireBoard extends Board {
      public FireBoard(ResourceBundle myProperties)
      INTERNAL
    public Cell[][] updateBoard(Rules rules)
    INTERNAL
}

package app.model.board;
public abstract class Board {
      public Board(ResourceBundle myProperties)
      INTERNAL
    public abstract Cell[][] updateBoard(Rules rules);
    EXTERNAL
    public Cell getCellAtCoordinates(int x, int y)
    EXTERNAL
    public Cell[][] getCells()
    INTERNAL
    public void setCells(Cell[][] newCells)
    INTERNAL
    public int getMyWidth()
    EXTERNAL
    public int getMyHeight()
    EXTERNAL
    public Map<Integer, Double> getCurrentStateData ()
    EXTERNAL
    public Cell createNewCellFromSubClass (Cell cell, int state, int x, int y, int boardHeight, int boardWidth, int neighborType, int chronons, int energy, int edgeType)
    INTERNAL
    public int getNeighborType()
    public int getEdgeType()
}

package app.model.cell;
public abstract class CellGetter {
      public CellGetter(String filename, String type, String gameName, int height, int width, int maxState, int neighborType, GridShapeType shape, int edgePolicy)
      INTERNAL
    public Cell makeCellAtIndex(int state, int row, int column)
    INTERNAL
    public double[] stringsToDouble(String[] numbers)
    INTERNAL
    public double sumOfDoubles(double[] doubles)
    INTERNAL
    public abstract Cell[][] getCells() throws IOException;
    INTERNAL
    public String getCsvName()
    EXTERNAL
    public int getMyHeight()
    EXTERNAL
    public int getMyWidth()
    EXTERNAL
    public GridShapeType getMyGridShapeType()
    EXTERNAL
    public int getMaxState()
    INTERNAL
    public int getNeighborType()
    EXTERNAL
    public String getGameName()
    EXTERNAL
    public Cell createNewCellFromProperty (GridShapeType gridShapeType, int state, int x, int y, int boardHeight, int boardWidth, int neighborType, int chronons, int energy, int edgeType)
    INTERNAL
    public String getMyType()
    EXTERNAL
    public int getErrorStatus()
    EXTERNAL
    public String getErrorType()
    EXTERNAL
    public Cell[][] getMyCells()
    INTERNAL
    public double[] getMyProbs()
    EXTERNAL
    public void setMyProbs(double[] args)
    EXTERNAL
    public double[] getMyCounts()
    EXTERNAL
    public void setMyCounts(double[] myCounts)
    EXTERNAL
    public void setErrorStatus(int arg)
    INTERNAL
    public void setErrorType(String errorType)
    INTERNAL
}

package app.model.cell;
public class HexCell extends Cell {
      public HexCell(int state, int x, int y, int boardHeight, int boardWidth, int neighborType, int chronons, int energy, int edgeType)
      INTERNAL
    public int[][] findNeighbors(int[][] NEIGHBORS_HEX)
    INTERNAL
    public int[][] getTempNeighborsForType()
    INTERNAL
}

package app.model.cell;
public class ProbabilityCellGetter extends CellGetter {  
      public ProbabilityCellGetter(String filename, String type, String gameName, int height, int width, int maxState, int neighborType, GridShapeType shape, int edgeType)
      INTERNAL
    public Cell[][] getCells() throws IOException
    INTERNAL
}

package app.model.cell;
public class RhombusCell extends Cell {
      public RhombusCell(int state, int x, int y, int boardHeight, int boardWidth, int neighborType, int chronons, int energy, int edgeType)
      INTERNAL
}

package app.model.cell;
public class RectangleCell extends Cell {
      public RectangleCell(int state, int x, int y, int boardHeight, int boardWidth, int neighborType, int chronons, int energy, int edgeType)
      INTERNAL
}

package app.model.cell;
public class GridCellGetter extends CellGetter {
      public GridCellGetter(String filename, String type, String gameName, int height, int width, int maxState, int neighborType, GridShapeType shape, int edgeType)
      INTERNAL
    public Cell[][] getCells() throws IOException
    INTERNAL
}

package app.model.cell;
public class CountsCellGetter extends CellGetter {
      public CountsCellGetter(String filename, String type, String gameName, int height, int width, int maxState, int neighborType, GridShapeType shape, int edgeType)
      INTERNAL
    public Cell[][] getCells() throws IOException
    INTERNAL
}

package app.model.cell;
public abstract class Cell{
      public Cell(int state, int x, int y, int boardHeight, int boardWidth, int neighborType, int chronons, int energy, int edgeType)
      EXTERNAL
    public int[][] findNeighbors(int[][] neighborsType)
    INTERNAL
    public int[] getNeighbor(int x, int y, int[] offSet)
    INTERNAL
    public int[] getNeighborFlipped(int x, int y, int[] offSet)
    INTERNAL
    public List<Cell> findNeighborsInState(int state, int[][] neighborsList, Board board)
    INTERNAL
    public int getNextState(Rules currentRules, Board board)
    INTERNAL
    public void setMyState(int state)
    INTERNAL
    public int getMyState()
    INTERNAL
    public int getMyX()
    INTERNAL
    public int getMyY()
    INTERNAL
    public void setMyX(int x)
    INTERNAL
    public void setMyY(int y)
    INTERNAL
    public int[][] getNeighbors()
    INTERNAL
    public void setNeighbors(int[][] newNeighbors)
    INTERNAL
    public int getCurrentChronons()
    INTERNAL
    public void setCurrentChronons(int x)
    INTERNAL
    public void resetCurrentChronons()
    INTERNAL
    public void increaseCurrentChronons()
    INTERNAL
    public int getMaxChronons()
    INTERNAL
    public int getEnergyLevel()
    INTERNAL
    public void decrementEnergyLevel()
    INTERNAL
    public void setCurrentEnergyLevel(int x)
    INTERNAL
    public GridShapeType getMyGridShapeType()
    INTERNAL
    public void  setMyGridShapeType(GridShapeType gridShape)
    INTERNAL
    public int getType()
    EXTERNAL
    public boolean getAndSetReproductionFlag()
    INTERNAL
    public boolean equals(Object obj)
    EXTERNAL
    public String toString()
    EXTERNAL
}

package app.model.rules;
public class CSVParser {
      public CSVParser(ResourceBundle myProperties)
      INTERNAL
    public CSVParser(String filename)
    INTERNAL
    public String getGameType()
    EXTERNAL
    public int getMyHeight()
    INTERNAL
    public int getMyWidth()
    INTERNAL
    public Cell[][] getCells()
    INTERAL
    public int getNeighborType()
    INTERNAL
    public int getErrorStatus()
    EXTERNAL
    public String getErrorType()
    EXTERNAL
    public CellGetter getMyCellGetter()
    INTERNAL
}

package app.model.rules;
public class Rules {
      public Rules(ResourceBundle properties)
      INTERNAL
    public Rules(String game)
    INTERNAL
    public ArrayList<State> getPossibleStates()
    EXTERNAL
    public RulesParser getMyRulesParser()return myRulesParser;}
    INTERNAL
    public ResourceBundle getMyProperties()
    EXTERNAL
}

package app.model.rules;
public class RulesParser {
      public RulesParser(String game)
      INTERNAL
    public RulesParser(ResourceBundle properties)
    INTERNAL
    public ArrayList<State> getPossibleStates()
    INTERNAL
    public int getType()
    INTERNAL
}

package app.model;
public class State {
      public State(int state, List<int[]> rulesList)
      EXTERNAL
    public int getMyState ()
    EXTERNAL
    public List<int[]> getRulesForState()
    INTERNAL
}

package app.model;
dependencies/packages:
public class PropertiesFileWriter {
      public PropertiesFileWriter(String propertiesFileName, String name, String gameType, String description, String csvNumber, String gridShape, String edgePol, String neighborPol)
      INTERNAL
    public String getMyPropFile()
    INTERNAL
}

package app.view;
public class GraphView{
      public GraphView(SimulationController sc, ResourceBundle prop)
      INTERNAL
    public void addToData(Map<Integer, Double> input)
    EXTERNAL
    public Group getMyRoot()
    INTERNAL
    
}

package app.view;
simulation. It assumes that the user possesses the following dependencies/packages:
public class ControlView {
      public ControlView (SimulationController sc)
      INTERNAL
    public HBox getMyRoot()
    INTERNAL
    public boolean getMyStartBoolean()
    INTERNAL
    public void setMyStartBoolean(Boolean b)
    INTERNAL
}

package app.view;
simulation. It assumes that the user possesses the following dependencies/packages:
public class NewConfigView {
      public NewConfigView(SimulationController sc)
      INTERNAL
}

package app.view;
dependencies/packages:
public class RightView {
      public RightView(SimulationController sc, BoardView bv, GraphView gv)
      INTERNAL
    public Node getMyRoot()
    INTERNAL
}

package app.view;
Board class. It assumes that the user possesses the following dependencies/packages:
public class BoardView {
      public BoardView(int width, int height, Cell[][] board, ResourceBundle properties,SimulationController sc, boolean grid, boolean image, ArrayList<Image> list)
      INTERNAL
    public BoardView(int width, int height, Cell[][] board, ResourceBundle properties, SimulationController sc, boolean grid, boolean image)
    INTERNAL
    public BoardView(int width, int height, Cell[][] board, ResourceBundle properties, SimulationController sc, boolean grid, ArrayList<Image> images, Color c0, Color c1, Color c2)
    INTERNAL
    public void setColors(Color c0, Color c1, Color c2)
    INTERNAL
    public void setMyImageArray(ArrayList<Image> input)
    INTERNAL
    public void changeGridStatus()
    EXTERNAL
    public Group getMyRoot()return myRoot;}
    INTERNAL
}

package app.view;
the speed of the simulation. It assumes that the user possesses the following dependencies/packages:
public class SpeedSlider {
      public SpeedSlider(SimulationController sc)
      EXTERNAL
    public Slider getMySlider()return mySlider;}
    EXTERNAL
}

package app.view;
This class is used to generate the main view of the entire simulation using the other classes in the app.view package.
It assumes that the user possesses the following dependencies/packages:
public class MainView {
      public MainView(BoardView bv,BorderPane root, SimulationController sc, ControlView cv, RightView rv, GraphView gv)
      INTERNAL
    public void modifyMainView(BorderPane root, BoardView bv, SimulationController sc, ControlView cv)
    EXTERNAL
    public Scene getScene()
    INTERNAL
    public boolean getMyStartBoolean()return myStartBoolean;}
    INTERNAL
    public void  setMyBoardView(BoardView bv)
    EXTERNAL
}

How can you reduce the number of methods needed to do a task or make them easier to find and understand?
    - We can make the methods needed to do a task easier to find and understand by using appropriate encapsulation and packaging,
    along with using intuitive method names. Also we can reduce the number of methods needed by having wide set of private methods that
    is flexible in terms of bringing change and extending the features.
What can you take as a parameter or return that helps other programmers write their own well designed code?
    -One of the things we can do is have it take in the most generic type of object that is higher in inheritance hierarchy
    so that other programmer can choose different ways (subclasses) to choose to implement the details when they want to change
    the code or build other components based off on it.

 How can you reduce the number of methods needed to do a task or make them easier to find and understand?
 What can you take as a parameter or return that helps other programmers write their own well designed code?
 How can you simplify the expectations for each method so they are easier use (and thus harder to misuse)?
    - Minimize the number of tasks completed in a method so that, each method completes on task, thus
      the purpose of the method will be more clear.
 What can you take as a parameter or return that makes fewer assumptions about the implementation or this specific example?
    - Use the least specific object types possible as arguments so that the user does not have to worry about specific
      subclasses that an argument must take. 



 

