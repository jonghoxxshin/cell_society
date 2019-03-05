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
    private static final int[][] neighborsOffset = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};



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
        if (rules.getMyRulesParser().getType() == 4) {
            return updateBoardForRulesType4(rules);
        }
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
    private int[][] setTempStateBoard(int[][] tempStateBoard) {
        for (int i = 0; i < myHeight; i++) {
            for (int j = 0; j < myWidth; j++) {
                tempStateBoard[i][j] = -1;
            }
        }
        return tempStateBoard;
    }



    private Cell[][] updateBoardForRulesType4(Rules rules) {
        //Set New Temporary Boards and Initialize
        int count = 0;
        Cell[][] tempCells = new Cell[myWidth][myHeight];
        int[][] tempStateBoard = new int[myWidth][myHeight];
        tempStateBoard = setTempStateBoard(tempStateBoard);
        print2DArray(tempStateBoard);
        //Loop through current board in order of state
        for (int state : orderToReplace) {
            for (int i = 0; i < myHeight; i++) {
                for (int j = 0; j < myWidth; j++) {
                    if (tempStateBoard[i][j] == -1) {
                        System.out.println(cells[i][j].getMyState() + "," + j + "," + i );
                        if (cells[j][i].getMyState() == state) {
                            Cell tempCell = new Cell(cells[j][i].getNextState(rules, this), j, i, myHeight, myWidth, neighborType);
                            tempCells[j][i] = tempCell;
                            tempStateBoard[i][j] = tempCell.getMyState();
                            print2DArray(tempStateBoard);
                            if (tempCell.getMyState() == 0) {
                                ArrayList<Cell> fishNeighbors = cells[j][i].findNeighborsInState(1, cells[j][i].getNeighbors(), this);
                                if (fishNeighbors.size() > 0) {
                                    System.out.println("has fish neighbor");
                                    Random myRandom = new Random();
                                    int myRandomInt = myRandom.nextInt(fishNeighbors.size());
                                    Cell newShark = fishNeighbors.get(myRandomInt);
                                    tempCells[newShark.getMyY()][newShark.getMyX()] = newShark;
                                    tempStateBoard[newShark.getMyY()][newShark.getMyX()] = newShark.getMyState();
                                    print2DArray(tempStateBoard);
                                } else {
                                    System.out.println("has no fish neighbors");
                                    Random myRandom = new Random();
                                    int myRandomInt = myRandom.nextInt(5);
                                    int[] offset = neighborsOffset[myRandomInt];
                                    Cell newShark = cells[j][i];
                                    tempCells[newShark.getMyX()+offset[0]][newShark.getMyY()+offset[1]] = newShark;
                                    tempStateBoard[newShark.getMyY()+offset[1]][newShark.getMyX()+offset[0]] = newShark.getMyState();
                                    print2DArray(tempStateBoard);
                                }
                            } else if (tempCell.getMyState() == 2 || tempCell.getMyState() == 1) {
                                //check if reproduction occurred
                            }
                        }
                    }
                }
                System.out.println();
            }
        }
        print2DArray(tempStateBoard);
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
