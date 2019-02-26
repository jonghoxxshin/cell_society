package app.model;

import app.model.Cell;


public class Board {

    Cell[][] cells;
    private int myWidth;
    private int myHeight;
    private String myGame;
    private int neighborType;



    //app.model.Board Constructor
    public Board(String game, int config) {
        myGame = game;
        CSVParser parser = new CSVParser(game, config);
        neighborType = parser.getNeighborType();
        cells = parser.getCells();
        myHeight = parser.getMyHeight();
        myWidth = parser.getMyWidth();


    }

    //Update board's expectedCells based on current cell configuration
    public Cell[][] updateBoard(Rules rules) {
        Cell[][] tempCells = new Cell[myWidth][myHeight];
        for(int i =0; i<myWidth;i++){
            for(int j =0; j<myHeight;j++){
                Cell thisCell = cells[i][j];
                Cell tempCell = new Cell(thisCell.getNextState(rules, this), i,j,myHeight,myWidth, neighborType);
                tempCells[i][j] = tempCell;
            }
        }
        cells = tempCells;
        return tempCells;
    }

    //get expectedCells array
    public Cell[][] getCells(){
        return cells;
    }

    public int getMyWidth() {
        return myWidth;
    }

    public int getMyHeight() {
        return myHeight;
    }


}
