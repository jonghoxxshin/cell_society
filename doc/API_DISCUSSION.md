API DISCUSSION:

package app.controller;
public class Start extends Application {
      public void start(Stage stage) throws Exception
}

package app.controller;
public class SimulationController {
      public SimulationController(int height, int width, String game, ResourceBundle myProperties) //Will change to instantiating simulation and simulationView inside controller, not as input
    public void next() //need to update model and view for each step
    public void setConfig(String t1)
    public void replaceBoardView()
    public String createProperties(String propertiesFileName, String name, String type, String des, String csv, String gridShape, String edgePolicy, String neighborPolicy)
    public Simulation getMySimulationModel ()
    public void setMyFramesPerSecond(int input)
    public ArrayList<String> getMyPropertiesList()
    public Scene getMyScene()
    public int getMyFramesPerSecond()
    public void pauseSimulation ()
    public void restartSimulation()
    public void changeColor(Color c0, Color c1, Color c2)
    public void setNewBoard()
    public void changeGrid()
    public void setImage(ArrayList<Image> imageList)
    public Map<Integer, Double> getStateData()
}

package app.controller;
public class Main {
  }

package app.model;
public class GridShape {
      public GridShapeType getShape(String name)
    public String getNameFromShape(GridShapeType shape)
}

package app.model;
public class Simulation {
      public Simulation(Board board, Rules rule)
    public void nextStep()
    public Rules getMyRules()
    public void setStart()
    public Cell[][] getMyCells()
}

package app.model.board;
public class PredatorPreyBoard extends Board {
      public PredatorPreyBoard(ResourceBundle myProperties)
    public Cell[][] updateBoard(Rules rules)
}

package app.model.board;
public class GenericBoard extends Board {
          public GenericBoard(ResourceBundle myProperties)
        public Cell[][] updateBoard(Rules rules)
}

package app.model.board;
public class SegregationBoard extends Board {
      public SegregationBoard(ResourceBundle myProperties)
    public Cell[][] updateBoard(Rules rules)
}

package app.model.board;
public class FireBoard extends Board {
      public FireBoard(ResourceBundle myProperties)
    public Cell[][] updateBoard(Rules rules)
}

package app.model.board;
public abstract class Board {
      public Board(ResourceBundle myProperties)
    public abstract Cell[][] updateBoard(Rules rules);
    public Cell getCellAtCoordinates(int x, int y)
    public Cell[][] getCells()
    public void setCells(Cell[][] newCells)
    public int getMyWidth()
    public int getMyHeight()
    public Map<Integer, Double> getCurrentStateData ()
    public Cell createNewCellFromSubClass (Cell cell, int state, int x, int y, int boardHeight, int boardWidth, int neighborType, int chronons, int energy, int edgeType)
    public int getNeighborType()
    public int getEdgeType()
}

package app.model.cell;
public abstract class CellGetter {
      public CellGetter(String filename, String type, String gameName, int height, int width, int maxState, int neighborType, GridShapeType shape, int edgePolicy)
    public Cell makeCellAtIndex(int state, int row, int column)
    public double[] stringsToDouble(String[] numbers)
    public double sumOfDoubles(double[] doubles)
    public abstract Cell[][] getCells() throws IOException;
    public String getCsvName()
    public int getMyHeight()
    public int getMyWidth()
    public GridShapeType getMyGridShapeType()
    public int getMaxState()
    public int getNeighborType()
    public String getGameName()
    public Cell createNewCellFromProperty (GridShapeType gridShapeType, int state, int x, int y, int boardHeight, int boardWidth, int neighborType, int chronons, int energy, int edgeType)
    public String getMyType()
    public int getErrorStatus()
    public String getErrorType()
    public Cell[][] getMyCells()
    public double[] getMyProbs()
    public void setMyProbs(double[] args)
    public double[] getMyCounts()
    public void setMyCounts(double[] myCounts)
    public void setErrorStatus(int arg)
    public void setErrorType(String errorType)
}

package app.model.cell;
public class HexCell extends Cell {
      public HexCell(int state, int x, int y, int boardHeight, int boardWidth, int neighborType, int chronons, int energy, int edgeType)
    public int[][] findNeighbors(int[][] NEIGHBORS_HEX)
    public int[][] getTempNeighborsForType()
}

package app.model.cell;
public class ProbabilityCellGetter extends CellGetter {
      public ProbabilityCellGetter(String filename, String type, String gameName, int height, int width, int maxState, int neighborType, GridShapeType shape, int edgeType)
    public Cell[][] getCells() throws IOException
}

package app.model.cell;
public class RhombusCell extends Cell {
      public RhombusCell(int state, int x, int y, int boardHeight, int boardWidth, int neighborType, int chronons, int energy, int edgeType)
}

package app.model.cell;
public class RectangleCell extends Cell {
      public RectangleCell(int state, int x, int y, int boardHeight, int boardWidth, int neighborType, int chronons, int energy, int edgeType)
}

package app.model.cell;
public class GridCellGetter extends CellGetter {
      public GridCellGetter(String filename, String type, String gameName, int height, int width, int maxState, int neighborType, GridShapeType shape, int edgeType)
    public Cell[][] getCells() throws IOException
}

package app.model.cell;
public class CountsCellGetter extends CellGetter {
      public CountsCellGetter(String filename, String type, String gameName, int height, int width, int maxState, int neighborType, GridShapeType shape, int edgeType)
    public Cell[][] getCells() throws IOException
}

package app.model.cell;
public abstract class Cell{
      public Cell(int state, int x, int y, int boardHeight, int boardWidth, int neighborType, int chronons, int energy, int edgeType)
    public int[][] findNeighbors(int[][] neighborsType)
    public int[] getNeighbor(int x, int y, int[] offSet)
    public int[] getNeighborFlipped(int x, int y, int[] offSet)
    public List<Cell> findNeighborsInState(int state, int[][] neighborsList, Board board)
    public int getNextState(Rules currentRules, Board board)
    public void setMyState(int state)
    public int getMyState()
    public int getMyX()
    public int getMyY()
    public void setMyX(int x)
    public void setMyY(int y)
    public int[][] getNeighbors()
    public void setNeighbors(int[][] newNeighbors)
    public int getCurrentChronons()
    public void setCurrentChronons(int x)
    public void resetCurrentChronons()
    public void increaseCurrentChronons()
    public int getMaxChronons()
    public int getEnergyLevel()
    public void decrementEnergyLevel()
    public void setCurrentEnergyLevel(int x)
    public GridShapeType getMyGridShapeType()
    public void  setMyGridShapeType(GridShapeType gridShape)
    public int getType()
    public boolean getAndSetReproductionFlag()
    public boolean equals(Object obj)
    public String toString()
}

package app.model.rules;
public class CSVParser {
      public CSVParser(ResourceBundle myProperties)
    public CSVParser(String filename)
    public String getGameType()
    public int getMyHeight()
    public int getMyWidth()
    public Cell[][] getCells()
    public int getNeighborType()
    public int getErrorStatus()
    public String getErrorType()
    public CellGetter getMyCellGetter()
}

package app.model.rules;
public class Rules {
      public Rules(ResourceBundle properties)
    public Rules(String game)
    public ArrayList<State> getPossibleStates()
    public RulesParser getMyRulesParser()return myRulesParser;}
    public ResourceBundle getMyProperties()
}

package app.model.rules;
public class RulesParser {
      public RulesParser(String game)
    public RulesParser(ResourceBundle properties)
    public ArrayList<State> getPossibleStates()
    public int getType()
}

package app.model;
public class State {
      public State(int state, List<int[]> rulesList)
    public int getMyState ()
    public List<int[]> getRulesForState()
}

package app.model;
dependencies/packages:
public class PropertiesFileWriter {
      public PropertiesFileWriter(String propertiesFileName, String name, String gameType, String description, String csvNumber, String gridShape, String edgePol, String neighborPol)
    public String getMyPropFile()
}

package app.view;
public class GraphView{
      public GraphView(SimulationController sc, ResourceBundle prop)
    public void addToData(Map<Integer, Double> input)
    public Group getMyRoot()
}

package app.view;
simulation. It assumes that the user possesses the following dependencies/packages:
public class ControlView {
      public ControlView (SimulationController sc)
    public HBox getMyRoot()
    public boolean getMyStartBoolean()
    public void setMyStartBoolean(Boolean b)
}

package app.view;
simulation. It assumes that the user possesses the following dependencies/packages:
public class NewConfigView {
      public NewConfigView(SimulationController sc)
}

package app.view;
dependencies/packages:
public class RightView {
      public RightView(SimulationController sc, BoardView bv, GraphView gv)
    public Node getMyRoot()
}

package app.view;
Board class. It assumes that the user possesses the following dependencies/packages:
public class BoardView {
      public BoardView(int width, int height, Cell[][] board, ResourceBundle properties,SimulationController sc, boolean grid, boolean image, ArrayList<Image> list)
    public BoardView(int width, int height, Cell[][] board, ResourceBundle properties, SimulationController sc, boolean grid, boolean image)
    public BoardView(int width, int height, Cell[][] board, ResourceBundle properties, SimulationController sc, boolean grid, ArrayList<Image> images, Color c0, Color c1, Color c2)
    public void setColors(Color c0, Color c1, Color c2)
    public void setMyImageArray(ArrayList<Image> input)
    public void changeGridStatus()
    public Group getMyRoot()return myRoot;}
}

package app.view;
the speed of the simulation. It assumes that the user possesses the following dependencies/packages:
public class SpeedSlider {
      public SpeedSlider(SimulationController sc)
    public Slider getMySlider()return mySlider;}
}

package app.view;
This class is used to generate the main view of the entire simulation using the other classes in the app.view package.
It assumes that the user possesses the following dependencies/packages:
public class MainView {
      public MainView(BoardView bv,BorderPane root, SimulationController sc, ControlView cv, RightView rv, GraphView gv)
    public void modifyMainView(BorderPane root, BoardView bv, SimulationController sc, ControlView cv)
    public Scene getScene()
    public boolean getMyStartBoolean()return myStartBoolean;}
    public void  setMyBoardView(BoardView bv)
}
 