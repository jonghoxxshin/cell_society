package app.model;


import java.util.*;

/**
 * Abstract Board Class
 * Packages: app.view.IBoardObserver; java.util.*;
 * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
 */


public abstract class Board {
    Cell[][] cells;
    private int myWidth;
    private int myHeight;
    private String myGame;
    private int neighborType;
    private final int[] orderToReplace = {2, 1, 0};
    private double threshold = 0.3;
    private CSVParser myParser;
    private int errorStatus;
    private GridShapeType myGridShapeType;
    private String edgePolicy;



    //app.model.Board Constructor

    /**
     * Board Constructor
     * <p>
     *     Sets game name, grid shape and edge policy from properties file.
     *     Create CSVParser object from properties file.
     *     Sets neighbor type, cells, height, and width from newly created CSVParser.
     * </p>
     *
     * @param myProperties
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public Board(ResourceBundle myProperties) {
        myGame = myProperties.getString("type_of_game");
        myGridShapeType = new GridShape().getShape(myProperties.getString("shape"));
        edgePolicy = myProperties.getString("edge_policy");
        myParser = new CSVParser(myProperties);
        if(myParser.getErrorStatus() == 1){

        }
        neighborType = myParser.getNeighborType();
        cells = myParser.getCells();
        System.out.println("first value in board is " + cells[0][0].toString());

        myHeight = myParser.getMyHeight();
        myWidth = myParser.getMyWidth();
    }

    //Update board's expectedCells based on current cell configuration

    /**
     * Update Board
     * <p>
     *     abstract class for board update, implemented by board subclasses
     *     replaces current cells with newly generated cells
     * </p>
     * @param rules
     * @return Cell[][]
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public abstract Cell[][] updateBoard(Rules rules);


    /**
     * Get Cell at Coordinates
     * <p>
     *     Given an x and y coordinate, return the
     *     cell at that position on the current board
     * </p>
     *
     * @param x
     * @param y
     * @return Cell
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public Cell getCellAtCoordinates(int x, int y) {
        return cells[y][x];
    }

    /**
     * Get expectedCells array
     *
     * @return Cell[][]
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public Cell[][] getCells() {
        return cells;
    }

    /**
     * Set Cells
     * <p>
     *     Replace current 2D cell array with new for current board
     * </p>
     *
     * @param newCells
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public void setCells(Cell[][] newCells) {
        cells = newCells;
    }

    /**
     * Get Board Width
     *
     * @return int
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public int getMyWidth() {
        return myWidth;
    }

    /**
     * Get Board Height
     *
     * @return int
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public int getMyHeight() {
        return myHeight;
    }

    /**
     * Get Current State Data
     * <p>
     *     For each state in the rule set of the current board
     *     produce a map with each state as a key and the
     *     percentage of total cells in that state as the value
     * </p>
     *
     * @return Map(Integer, Double)
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public Map<Integer, Double> getCurrentStateData () {
        Map<Integer, Double> dataDict = new HashMap<Integer, Double>();
        for (int state : orderToReplace) {
            double count = 0;
            for (int i = 0; i < myHeight; i++) {
                for (int j = 0; j < myWidth; j++) {
                    if (cells[i][j].getMyState() == state) {
                        count++;
                    }
                }
            }
            dataDict.put(state, count/(myHeight*myWidth));
        }
        return dataDict;
    }

    /**
     * Create New Cell From Subclass
     * <p>
     *     Given all the arguments necessary to create a new cell,
     *     determine the class of the given cell and create a new
     *     cell of the same class with the given arguments
     * </p>
     *
     * @param cell
     * @param state
     * @param x
     * @param y
     * @param boardHeight
     * @param boardWidth
     * @param neighborType
     * @param chronons
     * @param energy
     * @param edgeType
     * @return Cell
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public Cell createNewCellFromSubClass (Cell cell, int state, int x, int y, int boardHeight, int boardWidth, int neighborType, int chronons, int energy, int edgeType){
        if (cell instanceof RhombusCell) {
            return new RhombusCell(state, x, y, boardHeight, boardWidth, neighborType, chronons, energy, edgeType);
        } else if (cell instanceof HexCell) {
            return new HexCell(state, x, y, boardHeight, boardWidth, neighborType, chronons, energy, edgeType);
        } else {
            return new RectangleCell(state, x, y, boardHeight, boardWidth, neighborType, chronons, energy, edgeType);
        }
    }


    public int getNeighborType() {
        return neighborType;
    }

    public int getEdgeType() {
        if (edgePolicy.equals("torodial")) {
            return 0;
        } else if (edgePolicy.equals("finite")) {
            return 1;
        }
        return 2;
    }


}
