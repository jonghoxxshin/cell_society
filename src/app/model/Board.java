package app.model;

import app.model.Cell;


public class Board {

    Cell[][] cells;
    private int myWidth;
    private int myHeight;
    private String myGame;
    private int neighborType;



    //app.model.Board Constructor
    public Board(String filename) {
        myGame = filename.split("Config")[0];
        CSVParser parser = new CSVParser(filename);

        cells = parser.getCells();
        myHeight = parser.getMyHeight();
        myWidth = parser.getMyWidth();
    }

    //Update board's expectedCells based on current cell configuration
    public Cell[][] updateBoard(Rules rules) {
        Cell[][] tempCells = new Cell[myWidth][myHeight];
        for(int i =0; i<myHeight;i++){
            for(int j =0; j<myWidth;j++){
                tempCells[i][j] = new Cell(cells[i][j].getNextState(rules, this), i,j,myHeight,myWidth, neighborType);
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
