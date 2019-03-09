package app.model;

import java.util.*;



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
    public abstract Cell[][] updateBoard(Rules rules);

    private void print2DArray(int[][] myArray) {
        for (int[] row : myArray) {
            for (int val : row) {
                System.out.print(val + ",");
            }
            System.out.println();
        }
    }

    private void print2DBoard(Cell[][] myArray) {
        for (Cell[] row : myArray) {
            for (Cell val : row) {
                System.out.print("(" + val.getMyX() + "," + val.getMyY() + ")");
            }
            System.out.println();
        }
    }

    public Cell getCellAtCoordinates(int x, int y) {
        return cells[y][x];
    }

    //get expectedCells array
    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] newCells) {
        cells = newCells;
    }

    public int getMyWidth() {
        return myWidth;
    }

    public int getMyHeight() {
        return myHeight;
    }

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

    public Cell createNewCellFromSubClass (Cell cell, int state, int x, int y, int boardHeight, int boardWidth, int neighborType, int chronons, int energy, int edgeType){
        if (cell instanceof RhombusCell) {
            return new RhombusCell(state, x, y, boardHeight, boardWidth, neighborType, chronons, energy, edgeType);
        } else if (cell instanceof HexCell) {
            return new HexCell(state, x, y, boardHeight, boardWidth, neighborType, chronons, energy, edgeType);
        } else {
            return new RectangleCell(state, x, y, boardHeight, boardWidth, neighborType, chronons, energy, edgeType);
        }
    }


    public int getErrorStatus() {
        return errorStatus;
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

    public GridShapeType getMyGridShapeType() {
        return myGridShapeType;
    }




}
