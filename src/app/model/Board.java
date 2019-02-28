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
<<<<<<< HEAD

=======
<<<<<<< HEAD
    public Board(String filename) {
        myGame = filename.split("Config")[0];
        CSVParser parser = new CSVParser(filename);

=======
>>>>>>> 9f4aef4d778506e964c94c73da98fdb0bbbcba69
    public Board(ResourceBundle myProperties) {
        myGame = myProperties.getString("type_of_game");
        CSVParser parser = new CSVParser(myProperties.getString("name_of_csv"));
        neighborType = parser.getNeighborType();
<<<<<<< HEAD
=======
>>>>>>> kph18
>>>>>>> 9f4aef4d778506e964c94c73da98fdb0bbbcba69
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
