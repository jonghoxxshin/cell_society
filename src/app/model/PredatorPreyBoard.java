package app.model;

import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class PredatorPreyBoard extends Board{
    Cell[][] cells;
    private int myWidth;
    private int myHeight;
    private String myGame;
    private int neighborType;
    private final int[] orderToReplace = {2, 1, 0};
    private double threshold = 0.3;
    private CSVParser myParser;
    private int errorStatus;
    private GridShapeType myGridShapeType;

    public PredatorPreyBoard(ResourceBundle myProperties){
        super(myProperties);
    }

    @Override
    public Cell[][] updateBoard(Rules rules) {
        print2DBoard(cells);
        Cell[][] tempCells = new Cell[myHeight][myWidth];
        int[][] updateBoard = new int[myHeight][myWidth];
        initializeUpdateBoard(updateBoard);
        print2DArray(updateBoard);
        for (int state : orderToReplace) {
            for (int i = 0; i < myHeight; i++) {
                for (int j = 0; j < myWidth; j++) {
                    if (updateBoard[i][j] == -1) {
                        if (cells[i][j].getMyState() == state) {
                            Cell oldCell = cells[i][j];
                            Cell newCell = new Cell(oldCell.getNextState(rules, this), j, i, myHeight, myWidth, neighborType, oldCell.getCurrentChronons(), oldCell.getEnergyLevel(), myGridShapeType);
                            //check if time for reproduction


                            //update temp cells
                            tempCells[i][j] = newCell;
                            updateBoard[i][j] = newCell.getMyState();
                            int[][] neighborCoordinates = oldCell.getNeighbors();
                            //check if need to place a shark or fish after movement
                            if (newCell.getMyState() == 0 && oldCell.getMyState() != 0) {
                                List<Cell> neighborCells = oldCell.findNeighborsInState(1, neighborCoordinates, this);
                                //check if there are any fish neighbors if shark
                                if (neighborCells.size() > 0 && oldCell.getMyState() == 2) {
                                    if (oldCell.getMyState() == 2 && oldCell.getEnergyLevel() != 0) {
                                        Cell cellToReplace = eatFish(neighborCells, oldCell);
                                        tempCells[cellToReplace.getMyY()][cellToReplace.getMyX()] = cellToReplace;
                                        updateBoard[cellToReplace.getMyY()][cellToReplace.getMyX()] = cellToReplace.getMyState();
                                    }
                                } else {
                                    if (oldCell.getMyState() == 2 && oldCell.getEnergyLevel() != 0) {
                                        Cell cellToReplace = moveSharkToEmptySpace(oldCell, newCell, neighborCoordinates);
                                        tempCells[cellToReplace.getMyY()][cellToReplace.getMyX()] = cellToReplace;
                                        updateBoard[cellToReplace.getMyY()][cellToReplace.getMyX()] = cellToReplace.getMyState();
                                    } else if (oldCell.getMyState() != 2){
                                        List<Cell> emptyNeighborCells = newCell.findNeighborsInState(0, neighborCoordinates, this);
                                        if (emptyNeighborCells.size() >  0) {
                                            Cell cellToReplace = emptyNeighborCells.get(getRandomIntFromBound(emptyNeighborCells.size()));
                                            cellToReplace.setMyState(oldCell.getMyState());
                                            tempCells[cellToReplace.getMyY()][cellToReplace.getMyX()] = cellToReplace;
                                            updateBoard[cellToReplace.getMyY()][cellToReplace.getMyX()] = cellToReplace.getMyState();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        cells = tempCells;
        return tempCells;
    }

    private Cell moveSharkToEmptySpace(Cell oldCell, Cell newCell, int[][] neighborCoordinates) {
        oldCell.decrementEnergyLevel();
        List<Cell> emptyNeighborCells = newCell.findNeighborsInState(0, neighborCoordinates, this);
        Cell cellToReplace = emptyNeighborCells.get(getRandomIntFromBound(emptyNeighborCells.size()));
        cellToReplace.setMyState(oldCell.getMyState());
        cellToReplace.setCurrentEnergyLevel(oldCell.getEnergyLevel());
        return cellToReplace;
    }

    private Cell eatFish(List<Cell> neighborCells, Cell oldCell) {
        oldCell.decrementEnergyLevel();
        Cell cellToReplace = neighborCells.get(getRandomIntFromBound(neighborCells.size()));
        cellToReplace.setMyState(oldCell.getMyState());
        cellToReplace.setCurrentChronons(oldCell.getCurrentChronons());
        cellToReplace.setCurrentEnergyLevel(oldCell.getEnergyLevel()+10);
        return cellToReplace;
    }

    private int[][] initializeUpdateBoard(int[][] temp) {
        for (int i = 0; i < myHeight; i++) {
            for (int j = 0; j < myWidth; j++) {
                temp[i][j] = -1;
            }
        }
        return temp;
    }

    private void print2DArray(int[][] myArray) {
        for (int[] row : myArray) {
            for (int val : row) {
                System.out.print(val + ",");
            }
            System.out.println();
        }
    }

    private void print2DBoard(Cell[][] myArray) {
        for (Cell[] row : myArray) {
            for (Cell val : row) {
                System.out.print("(" + val.getMyX() + "," + val.getMyY() + ")");
            }
            System.out.println();
        }
    }

    private int getRandomIntFromBound(int bound) {
        Random newRand = new Random();
        return newRand.nextInt(bound);
    }
}
