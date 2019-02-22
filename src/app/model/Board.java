package app.model;

import app.model.Cell;


public class Board {
    public static final String csvName = "GameOfLifeConfig1.csv";

    Cell[][] cells;
    private int myWidth;
    private int myHeight;
    private String myGame;



    //app.model.Board Constructor
    public Board(String game) {
        myGame = game;
        CSVParser parser = new CSVParser(csvName);
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
                Cell tempCell = new Cell(thisCell.getNextState(rules, this), i,j,myHeight,myWidth);
                tempCells[i][j] = tempCell;
            }
        }
        for(int i=0;i<5;i++){
            for(int j =0; j<5; j++){
                System.out.println("At " + i + " " + j);
                System.out.println(tempCells[i][j].getMyState());
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
