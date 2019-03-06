package app.model;

import java.util.ArrayList;

public class Cell {
    private static final int[][] NEIGHBORS_TYPE1 = {{-1, -1}, {-1, 0}, {-1, +1}, {0, -1}, {0, +1}, {+1, -1}, {+1, 0}, {+1, +1}};
    private static final int[][] NEIGHBORS_TYPE2 = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
    private static final int[][] NEIGHBORS_HEX = {{0, -1}, {-1, -1}, {-1, 0}, {0, 1}, {1, 0}, {1, -1}};

    // NEED TO FINISH RHOMBUS NEIGHBORS
    private static final int[][] NEIGHBORS_RHOM = {{-1, 0}, {1,0}, {-1, -1}, {1, -1}};

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
    private GridShape myGridShape;


    //app.model.Cell constructor - should we be getting board height and width info to the cell some other way than as parameters?
    public Cell(int state, int x, int y, int boardHeight, int boardWidth, int neighborType, int chronons, int energy) {
        myState = state;
        myX = x;
        myY = y;
        this.boardHeight = boardHeight;
        this.boardWidth = boardWidth;
        myGridShape = GridShape.RHOMBUS;
        type = neighborType;
        neighbors = findNeighbors();
        currentChronons = chronons;
        currentEnergyLevel = energy;
        myGridShape = GridShape.RECTANGLE;

        if(myGridShape==GridShape.RECTANGLE) {
            neighbors = findNeighbors();

        } else if(myGridShape == GridShape.HEXAGON){
            neighbors = findNeighborsHex();
        }

    }


    private int[][] findNeighborsHex(){
        int[][] tempNeighbors = new int[6][2];

        for(int i=0; i<tempNeighbors.length; i++){
            tempNeighbors[i] = getNeighbor(myX, myY, NEIGHBORS_HEX[i]);
        }

        return tempNeighbors;

    }



    //get ArrayList of (x,y) coordinates for valid neighbor expectedCells
    private int[][] findNeighbors() {
        // code to get expectedNeighbors based on current cell's coordinates
        int[][] tempNeighbors = getTempNeighborsForType();
        for (int i = 0; i < tempNeighbors.length; i++) {
            if (type == 1) {
                tempNeighbors[i] = getNeighbor(myX, myY, NEIGHBORS_TYPE1[i]);
            } else if (type == 2 || type == 4) {
                tempNeighbors[i] = getNeighbor(myX, myY, NEIGHBORS_TYPE2[i]);
            }
        }
        return tempNeighbors;
    }

    //get neighbor coordinates from offset with respect to toroidal edges
    private int[] getNeighbor(int x, int y, int[] offSet) {
        int tempX;
        int tempY;

        if (x + offSet[0] >= boardWidth) {
            tempX = 0;
        } else if (x + offSet[0] < 0) {
            tempX = boardWidth - 1;
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

    private int[][] getTempNeighborsForType() {
        if (type == 2) {
            int[][] tempNeighbors = new int[4][2];
            return tempNeighbors;
        }
        int[][] returnNeighbors = new int[8][2];
        return returnNeighbors;
    }


    //find number of expectedNeighbors in a given state
    private int findNumberOfNeighborsInState(int state, int[][] neighborsList, Board board) {
        int count = 0;
        for (int[] neighbor : neighborsList) {
            if (board.getCells()[neighbor[0]][neighbor[1]].getMyState() == state) {
                count++;
            }
        }
        return count;
    }

    //get coordinates of neighbors in desired state
    public ArrayList<Cell> findNeighborsInState(int state, int[][] neighborsList, Board board) {
        ArrayList<Cell> neighborsInState = new ArrayList<Cell>();
        for (int[] neighbor : neighborsList) {
            Cell tempCell = board.getCells()[neighbor[0]][neighbor[1]];
            if (tempCell.getMyState() == state) {
                neighborsInState.add(tempCell);
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
                            currentChronons = -1;
                            return rule[4];
                        } else {
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
        return 0;
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

    public GridShape getMyGridShape() {
        return myGridShape;
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
        return "app.model.Cell with state " + this.myState + " and x is " + this.myX + " and y is " + this.myY;
    }
}
