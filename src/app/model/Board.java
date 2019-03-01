package app.model;

import app.model.Cell;

import java.util.ResourceBundle;


public class Board {

    Cell[][] cells;
    private int myWidth;
    private int myHeight;
    private String myGame;
    private int neighborType;



    //app.model.Board Constructor

    public Board(ResourceBundle myProperties) {
        myGame = myProperties.getString("type_of_game");
        CSVParser parser = new CSVParser(myProperties.getString("name_of_csv"));
        neighborType = parser.getNeighborType();
        cells = parser.getCells();
        myHeight = parser.getMyHeight();
        myWidth = parser.getMyWidth();
    }

    //Update board's expectedCells based on current cell configuration
    public Cell[][] updateBoard(Rules rules) {
        Cell[][] tempCells = new Cell[myWidth][myHeight];
        for(int i =0; i<myHeight;i++){
            for(int j =0; j<myWidth;j++){
                tempCells[j][i] = new Cell(cells[j][i].getNextState(rules, this), i, j, myHeight, myWidth, neighborType);
            }
        }
        cells = tempCells;
        return tempCells;
    }

    public Cell getCellAtCoordinates(int x, int y) {
        return cells[y][x];
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
