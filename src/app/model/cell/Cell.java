package app.model.cell;

import app.model.GridShape;
import app.model.GridShapeType;
import app.model.rules.Rules;
import app.model.State;
import app.model.board.Board;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract Cell Class
 *
 * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
 */
public abstract class Cell{
    private int type;
    private int myX;
    private int myY;
    private int[][] neighbors;
    private int myState;
    private int boardHeight;
    private int boardWidth;
    private int currentChronons;
    private int maxChronons = 10;
    private int currentEnergyLevel;
    private int edgeType = 0; //0 = torodial, 1 = finite, 2 = torodial-flipped
    private GridShapeType myGridShapeType;
    private boolean reproductionFlag = false;
    private final int NUM_CARDINAL_NEIGHBORS = 4;
    private final int NUM_ALL_BUT_LEFT_NEIGHBORS = 5;
    private final int NUM_NORMAL_NEIGHBORS = 8;
    private final int CARDINAL = 2;
    private final int ALL_BUT_LEFT = 3;
    private final int RULES_TYPE_WITH_CHRONONS = 4;


    /**
     * Cell Constuctor
     *
     * @param state
     * @param x
     * @param y
     * @param boardHeight
     * @param boardWidth
     * @param neighborType
     * @param chronons
     * @param energy
     * @param edgeType
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public Cell(int state, int x, int y, int boardHeight, int boardWidth, int neighborType, int chronons, int energy, int edgeType) {
        myState = state;
        myX = x;
        myY = y;
        this.boardHeight = boardHeight;
        this.boardWidth = boardWidth;
        myGridShapeType = GridShapeType.RECTANGLE;
        type = neighborType;
        currentChronons = chronons;
        currentEnergyLevel = energy;
        this.edgeType = edgeType;
    }

    //get ArrayList of (x,y) coordinates for valid neighbor expectedCells

    /**
     * Find Neighbors
     * <p>
     *     Given an array of the offsets appropriate for the current cell's
     *     neighbor type, return an int[][] of the corresponding neighbor
     *     coordinates
     * </p>
     *
     *
     * @param neighborsType
     * @return int[][]
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public int[][] findNeighbors(int[][] neighborsType) {
        // code to get expectedNeighbors based on current cell's coordinates
        int[][] tempNeighbors = getTempNeighborsForType();
        for (int i = 0; i < tempNeighbors.length; i++) {
            tempNeighbors[i] = chooseAppropriateNeighbors(neighborsType, i);
        }
        return tempNeighbors;
    }

    //Use edge type to determine the appropriate neighbors
    private int[] chooseAppropriateNeighbors(int[][] neighborsType, int i) {
        if (edgeType == 0) {
            return getNeighbor(myX, myY, neighborsType[i]);
        } else if (edgeType == 1) {
            return getNeighborFinite(myX, myY, neighborsType[i]);
        }
        return getNeighborFlipped(myX, myY, neighborsType[i]);
    }


    /**
     * Get Neighbors Torodial
     *
     * get neighbor coordinates from offset with respect to toroidal edges
     *
     * @param x
     * @param y
     * @param offSet
     * @return int[]
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public int[] getNeighbor(int x, int y, int[] offSet) {
        int tempX;
        int tempY;

        if (x + offSet[0] >= boardWidth) {
            tempX = x + offSet[0] - boardWidth;
        } else if (x + offSet[0] < 0) {
            tempX = boardWidth + x + offSet[0];
        } else {
            tempX = x + offSet[0];
        }

        if (y + offSet[1] >= boardHeight) {
            tempY = 0;
        } else if (y + offSet[1] < 0) {
            tempY = boardHeight - 1;
        } else {
            tempY = y + offSet[1];
        }

        int[] toBeReturned = {tempY, tempX};
        return toBeReturned;
    }

    /**
     * Get Neighbors Finite
     *
     * get neighbor coordinates from offset with respect to finite edges
     *
     * @param x
     * @param y
     * @param offSet
     * @return int[]
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    private int[] getNeighborFinite(int x, int y, int[] offSet) {
        int tempX;
        int tempY;

        if (x + offSet[0] >= boardWidth) {
            tempX = -1;
        } else if (x + offSet[0] < 0) {
            tempX = -1;
        } else {
            tempX = x + offSet[0];
        }

        if (y + offSet[1] >= boardHeight) {
            tempY = -1;
        } else if (y + offSet[1] < 0) {
            tempY = -1;
        } else {
            tempY = y + offSet[1];
        }

        int[] toBeReturned = {tempY, tempX};
        return toBeReturned;
    }

    /**
     * Get Neighbors Flippe
     *
     * get neighbor coordinates from offset with respect to flipped edges
     *
     * @param x
     * @param y
     * @param offSet
     * @return int[]
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public int[] getNeighborFlipped(int x, int y, int[] offSet) {
        int tempX;
        int tempY;

        if (x + offSet[0] >= boardWidth) {
            tempX = boardWidth - (x + offSet[0] - boardWidth + 1);
        } else if (x + offSet[0] < 0) {
            tempX = (0 - (x + offSet[0]));
        } else {
            tempX = x + offSet[0];
        }

        if (y + offSet[1] >= boardHeight) {
            tempY = boardHeight - (y + offSet[1] - boardHeight + 1);
        } else if (y + offSet[1] < 0) {
            tempY = (0 - (y + offSet[1]));
        } else {
            tempY = y + offSet[1];
        }

        int[] toBeReturned = {tempY, tempX};
        return toBeReturned;
    }

    /**
     * Get Temp Neighbors For Type
     *
     * get neighbor coordinates from offset with respect to toroidal edges
     *
     * @param
     * @return int[][]
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    private int[][] getTempNeighborsForType() {
        if (type == CARDINAL) {
            int[][] tempNeighbors = new int[NUM_CARDINAL_NEIGHBORS][2];
            return tempNeighbors;
        }
        else if(type == ALL_BUT_LEFT){
            int[][] tempNeighbors = new int[NUM_ALL_BUT_LEFT_NEIGHBORS][2];
            return tempNeighbors;
        }

        int[][] returnNeighbors = new int[NUM_NORMAL_NEIGHBORS][2];
        return returnNeighbors;
    }



    //find number of expectedNeighbors in a given state

    /**
     * Find Number of Neighbors In State
     * <p>
     *     for the current cell given a state, a neighbor offset list, and a
     *     board fins the number of neighbors of that cell in the given state
     * </p>
     *
     * @param state
     * @param neighborsList
     * @param board
     * @return int
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    private int findNumberOfNeighborsInState(int state, int[][] neighborsList, Board board) {
        int count = 0;
        for (int[] neighbor : neighborsList) {
            if (neighbor[0] != -1 && neighbor[1] != -1 && board.getCells()[neighbor[0]][neighbor[1]].getMyState() == state) {
                count++;
            }
        }
        return count;
    }

    /**
     * Find Neighbors In State
     * <p>
     *     for the current cell given a state, a neighbor offset list, and a
     *     board fins neighbors of that cell in the given state
     * </p>
     *
     * @param state
     * @param neighborsList
     * @param board
     * @return List(Cell)
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public List<Cell> findNeighborsInState(int state, int[][] neighborsList, Board board) {
        List<Cell> neighborsInState = new ArrayList<>();
        for (int[] neighbor : neighborsList) {
            if (neighbor[0] != -1 && neighbor[1] != -1) {
                Cell tempCell = board.getCells()[neighbor[0]][neighbor[1]];
                if (tempCell.getMyState() == state) {
                    neighborsInState.add(tempCell);
                }
            }
        }
        return neighborsInState;
    }

    //for current cell, get next state based on a given app.model.rules.Rules object

    /**
     * Get Next State
     * <p>
     *     For the current cell, find the next state given the current rules and \
     *     the current board
     * </p>
     *
     * @param currentRules
     * @param board
     * @return int
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public int getNextState(Rules currentRules, Board board) {
        for (State state : currentRules.getPossibleStates()) {
            if (myState == state.getMyState()) {
                for (int[] rule : state.getRulesForState()) {
                    int actual = findNumberOfNeighborsInState(rule[1], neighbors, board);
                    if (currentRules.getMyRulesParser().getType() == RULES_TYPE_WITH_CHRONONS) {
                        if (currentChronons == maxChronons){
                            currentChronons = 0;
                            reproductionFlag = true;
                            return rule[4];
                        } else {
                            currentChronons ++;
                            return rule[3];
                        }
                    } else {
                        if (actual == rule[2]) {
                            return rule[3];
                        }
                    }
                }
            }
        }
        if (currentRules.getMyRulesParser().getType() == RULES_TYPE_WITH_CHRONONS) {
            currentChronons++;
        }
        return this.getMyState();
    }

    /**
     * Set Cell State
     *
     * @param state
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public void setMyState(int state) {
        myState = state;
    }

    /**
     * Get Cell State
     *
     * @return int
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public int getMyState() {
        return myState;
    }


    /**
     *Get X of Current Cell
     *
     * @return int
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public int getMyX() {
        return myX;
    }

    /**
     *Get Y of Current Cell
     *
     * @return int
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public int getMyY() {
        return myY;
    }

    /**
     * Set X of Current Cell
     *
     * @param x
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public void setMyX(int x) {
        myX = x;
    }

    /**
     * Set Y of Current Cell
     *
     * @param y
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public void setMyY(int y) {
        myY = y;
    }

    /**
     * Get Neighbors of Current Cell
     *
     * @return int[][]
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public int[][] getNeighbors() {
        return neighbors;
    }


    /**
     * Set Neighbors of Current Cell
     * @param newNeighbors
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public void setNeighbors(int[][] newNeighbors) {
        neighbors = newNeighbors;
    }


    //get chronons

    /**
     * Get Current Chronons of Cell
     *
     * @return int
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public int getCurrentChronons() {
        return currentChronons;
    }

    /**
     * Set chronons of current cell
     *
     * @param x
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public void setCurrentChronons(int x) {
        currentChronons = x;
    }

    /**
     * Reset Current Chronons to 0
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public void resetCurrentChronons() {
        currentChronons = 0;
    }

    /**
     * Increment Current Chronons by 1
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public void increaseCurrentChronons() {
        currentChronons++;
    }

    /**
     * Get Max Chronons
     *
     * @return int
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public int getMaxChronons() {
        return maxChronons;
    }

    /**
     * Get Energy Level of Cell
     *
     * @return int
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public int getEnergyLevel() {
        return currentEnergyLevel;
    }

    /**
     * Decrement energy level of cell by 1
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public void decrementEnergyLevel(){
        currentEnergyLevel--;
    }

    /**
     * Set Energy Level of Current Cell
     * @param x
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public void setCurrentEnergyLevel(int x){
        currentEnergyLevel= x;
    }

    /**
     * Get Grid Shape Type of Current Cell
     * @return GridShapeType
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public GridShapeType getMyGridShapeType() {
        return myGridShapeType;
    }

    /**
     * Set Grid Shape Type
     *
     * @param gridShape
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public void  setMyGridShapeType(GridShapeType gridShape) {
        myGridShapeType = gridShape;
    }

    /**
     * Get Type of Cell
     *
     * @return int
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public int getType() {
        return type;
    }

    /**
     * Get and Set Reproduction Flag
     * <p>
     *     once reproduction should occur reproduction flag gets set to true
     *     after that this method sets the flag back to false and returns true
     * </p>
     * @return boolean
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public boolean getAndSetReproductionFlag() {
        boolean tempFlag = reproductionFlag;
        reproductionFlag = ! reproductionFlag;
        return tempFlag;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass().equals(this.getClass())){
            Cell tempCell = (Cell) obj;

            if (tempCell.myState == this.myState && tempCell.myX == this.myX && tempCell.myY == this.myY) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    //convert cell data to easily readable string
    public String toString() {
        return "app.model.cell.Cell with state " + this.myState + " and x is " + this.myX + " and y is " + this.myY + " with shape " + new GridShape().getNameFromShape(myGridShapeType);
    }
}
