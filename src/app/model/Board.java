package app.model;

import app.model.Cell;

import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;


public class Board {

    Cell[][] cells;
    private int myWidth;
    private int myHeight;
    private String myGame;
    private int neighborType;
    private final int[] orderToReplace = {2,1,0};



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
        System.out.println("board being updated");
        Cell[][] tempCells = new Cell[myHeight][myWidth];
        for(int i =0; i<myHeight;i++){
            for(int j =0; j<myWidth;j++){
                tempCells[i][j] = new Cell(cells[i][j].getNextState(rules, this), j, i, myHeight, myWidth, neighborType);
            }
        }
        cells = tempCells;
        return tempCells;
    }

    private void print2DArray (int[][] myArray) {
        for (int[] row : myArray) {
            for (int val : row) {
                System.out.print(val + ",");
            }
            System.out.println();
        }
    }

    private void print2DBoard (Cell[][] myArray) {
        for (Cell[] row : myArray) {
            for (Cell val : row) {
                System.out.print(val.getMyState() + ",");
            }
            System.out.println();
        }
    }

    private int[][] setTempStateBoard(int[][] tempStateBoard) {
        for (int i = 0; i < myHeight; i++) {
            for (int j = 0; j < myWidth; j++) {
                tempStateBoard[i][j] = -1;
            }
        }
        return tempStateBoard;
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
