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
        if (rules.getMyRulesParser().getType() == 4){
            return updateBoardHelper(rules);
        }
        Cell[][] tempCells = new Cell[myHeight][myWidth];
        for(int i =0; i<myHeight;i++){
            for(int j =0; j<myWidth;j++){
                tempCells[i][j] = new Cell(cells[i][j].getNextState(rules, this), j, i, myHeight, myWidth, neighborType);
            }
        }
        cells = tempCells;
        print2DBoard(cells);
        return tempCells;
    }

    private int getRandomIntFromBound(int bound) {
        Random newRand = new Random();
        return newRand.nextInt(bound);
    }

    private int[][] initializeUpdateBoard(int[][] temp){
        for(int i =0; i<myHeight;i++) {
            for (int j = 0; j < myWidth; j++) {
                temp[i][j] = -1;
            }
        }
        return temp;
    }

    private Cell[][] updateBoardHelper(Rules rules) {
        print2DBoard(cells);
        Cell[][] tempCells = new Cell[myHeight][myWidth];
        int[][] updateBoard = new int[myHeight][myWidth];
        initializeUpdateBoard(updateBoard);
        print2DArray(updateBoard);
        for (int state : orderToReplace) {
            for(int i =0; i<myHeight;i++) {
                for(int j =0; j<myWidth;j++){
                    if (updateBoard[i][j] == -1) {
                        if (cells[i][j].getMyState() == state) {
                            Cell newCell = new Cell(cells[i][j].getNextState(rules, this), j, i, myHeight, myWidth, neighborType);
                            tempCells[i][j] = newCell;
                            updateBoard[i][j] = newCell.getMyState();
                            int[][] neighborCoordinates = cells[i][j].getNeighbors();
                            System.out.println("updated board with rule:");
                            print2DArray(updateBoard);
                            if (newCell.getMyState() == 0 && cells[i][j].getMyState() != 0) {
                                ArrayList<Cell> neighborCells = newCell.findNeighborsInState(1, neighborCoordinates, this);
                                if (neighborCells.size() > 0) {
                                    Cell cellToReplace = neighborCells.get(getRandomIntFromBound(neighborCells.size()));
                                    cellToReplace.setMyState(cells[i][j].getMyState());
                                    tempCells[cellToReplace.getMyY()][cellToReplace.getMyX()] = cellToReplace;
                                    updateBoard[cellToReplace.getMyY()][cellToReplace.getMyX()] = cellToReplace.getMyState();
                                    System.out.println("updated board with fish:");
                                    print2DArray(updateBoard);
                                } else {
                                    ArrayList<Cell> emptyNeighborCells = newCell.findNeighborsInState(0, neighborCoordinates, this);
                                    Cell cellToReplace = emptyNeighborCells.get(getRandomIntFromBound(emptyNeighborCells.size()));
                                    cellToReplace.setMyState(cells[i][j].getMyState());
                                    tempCells[cellToReplace.getMyY()][cellToReplace.getMyX()] = cellToReplace;
                                    updateBoard[cellToReplace.getMyY()][cellToReplace.getMyX()] = cellToReplace.getMyState();
                                    System.out.println("updated board no fish:");
                                    print2DArray(updateBoard);
                                }
                            }
                        }
                    }
                }
            }
        }
        cells = tempCells;
        System.out.println("Updated Board:");
        print2DBoard(cells);
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
                System.out.print("(" + val.getMyX() + "," + val.getMyY() + ")");
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
