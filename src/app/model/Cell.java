package app.model;

import java.util.ArrayList;
import java.util.List;

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


    //app.model.Cell constructor - should we be getting board height and width info to the cell some other way than as parameters?
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
    public int[][] findNeighbors(int[][] neighborsType) {
        // code to get expectedNeighbors based on current cell's coordinates
        int[][] tempNeighbors = getTempNeighborsForType();
        for (int i = 0; i < tempNeighbors.length; i++) {
            tempNeighbors[i] = chooseAppropriateNeighbors(neighborsType, i);
        }
        return tempNeighbors;
    }

    private int[] chooseAppropriateNeighbors(int[][] neighborsType, int i) {
        if (edgeType == 0) {
            return getNeighbor(myX, myY, neighborsType[i]);
        } else if (edgeType == 1) {
            return getNeighborFinite(myX, myY, neighborsType[i]);
        }
        return getNeighborFlipped(myX, myY, neighborsType[i]);
    }

    //get neighbor coordinates from offset with respect to toroidal edges
    // modified this to handle when x is out of bounds by more than one
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

    private int[][] getTempNeighborsForType() {
        if (type == 2) {
            int[][] tempNeighbors = new int[4][2];
            return tempNeighbors;
        }
        else if(type == 3){
            int[][] tempNeighbors = new int[5][2];
            return tempNeighbors;
        }

        int[][] returnNeighbors = new int[8][2];
        return returnNeighbors;
    }


    //find number of expectedNeighbors in a given state
    private int findNumberOfNeighborsInState(int state, int[][] neighborsList, Board board) {
        int count = 0;
        for (int[] neighbor : neighborsList) {
            if (neighbor[0] != -1 && neighbor[1] != -1) {
                if (board.getCells()[neighbor[0]][neighbor[1]].getMyState() == state) {
                    count++;
                }
            }
        }
        return count;
    }

    //get coordinates of neighbors in desired state
    public List<Cell> findNeighborsInState(int state, int[][] neighborsList, Board board) {
        List<Cell> neighborsInState = new ArrayList<Cell>();
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

    //for current cell, get next state based on a given app.model.Rules object
    public int getNextState(Rules currentRules, Board board) {
        for (State state : currentRules.getPossibleStates()) {
            if (myState == state.getMyState()) {
                for (int[] rule : state.getRulesForState()) {
                    int actual = findNumberOfNeighborsInState(rule[1], neighbors, board);
                    if (currentRules.getMyRulesParser().getType() == 4) {
                        if (currentChronons == maxChronons){
                            currentChronons = 0;
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
        if (currentRules.getMyRulesParser().getType() == 4) {
            currentChronons++;
        }
        return this.getMyState();
    }

    //set cell state
    public void setMyState(int state) {
        myState = state;
    }

    //get state
    public int getMyState() {
        return myState;
    }

    //get myX
    public int getMyX() {
        return myX;
    }

    //get myY
    public int getMyY() {
        return myY;
    }

    //set myX
    public void setMyX(int x) {
        myX = x;
    }

    //set myY
    public void setMyY(int y) {
        myY = y;
    }

    // get neighbors
    public int[][] getNeighbors() {
        return neighbors;
    }


    // set neighbors
    public void setNeighbors(int[][] newNeighbors) {
        neighbors = newNeighbors;
    }


    //get chronons
    public int getCurrentChronons() {
        return currentChronons;
    }

    //set chronons
    public void setCurrentChronons(int x) {
        currentChronons = x;
    }

    //reset chronons
    public void resetCurrentChronons() {
        currentChronons = 0;
    }

    //increment chronons
    public void increaseCurrentChronons() {
        currentChronons++;
    }

    public int getMaxChronons() {
        return maxChronons;
    }

    public int getEnergyLevel() {
        return currentEnergyLevel;
    }

    public void decrementEnergyLevel(){
        currentEnergyLevel--;
    }

    public void setCurrentEnergyLevel(int x){
        currentEnergyLevel= x;
    }

    public GridShapeType getMyGridShapeType() {
        return myGridShapeType;
    }

    public void  setMyGridShapeType(GridShapeType gridShape) {
        myGridShapeType = gridShape;
    }

    public int getType() {
        return type;
    }

    @Override
    //compare two cells for equality
    public boolean equals(Object obj) {
        if (obj instanceof Cell) {
            Cell tempCell = (Cell) obj;

            if (tempCell.myState == this.myState && tempCell.myX == this.myX && tempCell.myY == this.myY) {
                return true;
            }
        }
        return false;
    }

    @Override
    //convert cell data to easily readable string
    public String toString() {
        return "app.model.Cell with state " + this.myState + " and x is " + this.myX + " and y is " + this.myY + " with shape " + new GridShape().getNameFromShape(myGridShapeType);
    }
}
