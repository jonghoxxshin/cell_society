package app.model.board;

import app.model.cell.Cell;
import app.model.rules.Rules;

import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Board Subclass for Predator Prey Simulation
 * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
 */
public class PredatorPreyBoard extends Board {
    Cell[][] cells;
    private final int[] orderToReplace = {2, 1, 0};

    /**
     * Board Constructor, same as super
     * @param myProperties
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public PredatorPreyBoard(ResourceBundle myProperties){
        super(myProperties);
    }

    /**
     * Update Board for Predator Prey
     * <p>
     *     Cells are replaced in order of state so that all sharks are updated first,
     *     then fish, then empty spaces. With the previous updates impacting the next
     *     ones, energy level is incremented when fish are eaten and sharks die when
     *     energy level reaches zero. For Sharks and Fish, first the new cell is generated,
     *     then depending on the new cell that is generated, the animal will either
     *     move, reproduce or stay still if unable to move
     * </p>
     *
     * @param rules
     * @return cells
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    @Override
    public Cell[][] updateBoard(Rules rules) {
//        print2DBoard(cells);
        Cell[][] tempCells = new Cell[super.getMyHeight()][super.getMyWidth()];
        int[][] updateBoard = new int[super.getMyHeight()][super.getMyWidth()];
        initializeUpdateBoard(updateBoard);
        for (int state : orderToReplace) {
            for (int i = 0; i < super.getMyHeight(); i++) {
                for (int j = 0; j < super.getMyWidth(); j++) {
                    if (updateBoard[i][j] == -1) {
                        if (super.getCells()[i][j].getMyState() == state) {
                            Cell oldCell = super.getCells()[i][j];
                            Cell newCell = createNewCellFromSubClass(oldCell, oldCell.getNextState(rules, this), j, i, super.getMyHeight(), super.getMyWidth(), super.getNeighborType(), 0, 20, super.getEdgeType());

                            //update temp cells
                            tempCells[i][j] = newCell;
                            updateBoard[i][j] = newCell.getMyState();
                            int[][] neighborCoordinates = oldCell.getNeighbors();
                            //check if need to place a shark or fish after movement
                            if (newCell.getMyState() == 0 && oldCell.getMyState() != 0 || oldCell.getAndSetReproductionFlag()) {
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
        super.setCells(tempCells);
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
        for (int i = 0; i < super.getMyHeight(); i++) {
            for (int j = 0; j < super.getMyWidth(); j++) {
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
