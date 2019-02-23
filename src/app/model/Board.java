package app.model;

import app.model.Cell;


public class Board {

    Cell[][] cells;
    private int myWidth;
    private int myHeight;
    private String myGame;
    private static final String LIFE_1 = "GameOfLifeConfig1.csv";
    private static final String LIFE_2 = "GameOfLifeConfig2.csv";
    private static final String LIFE_3 = "GameOfLifeConfig3.csv";
    private static final String PERCOLATION_1 = "PercolationConfig1.csv";
    private static final String PERCOLATION_2 = "PercolationConfig2.csv";
    private static final String PERCOLATION_3 = "PercolationConfig3.csv";


    //app.model.Board Constructor
    public Board(String game, int config) {
        myGame = game;
        String csvGame = "";
        if (game.equals("GameOfLife")) {
            if (config == 1) {
                csvGame = LIFE_1;
            } else if (config == 2) {
                csvGame = LIFE_2;
            }else if (config == 3) {
                csvGame = LIFE_3;
            }
        } else if (game.equals("Percolation")) {
            if (config == 1) {
                csvGame = PERCOLATION_1;
            } else if (config == 2) {
                csvGame = PERCOLATION_2;
            }else if (config == 3) {
                csvGame = PERCOLATION_3;
            }
        }
        CSVParser parser = new CSVParser(csvGame);
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
