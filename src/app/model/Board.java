package app.model;

import app.model.Cell;


public class Board {
    Cell[][] cells;
    private int myWidth;
    private int myHeight;
    private String myGame;

    //app.model.Board Constructor
    public Board(String game) {
        myGame = game;
        CSVParser parser = new CSVParser(game);
        cells = parser.getCells();
        myHeight = parser.getMyHeight();
        myWidth = parser.getMyWidth();
    }

    //Update board's expectedCells based on current cell configuration
    public void updateBoard(Rules rules) {
        Cell[][] tempCells = new Cell[myWidth][myHeight];
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                int tempX = cell.getMyX();
                int tempY = cell.getMyY();
                Cell tempCell = new Cell(cell.getNextState(rules, this), tempX, tempY, myHeight, myWidth);
                tempCells[tempX][tempY] = tempCell;
            }
        }
        cells = tempCells;
    }

    //get expectedCells array
    public Cell[][] getCells(){
        return cells;
    }


}
