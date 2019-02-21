package app.model;

public class Cell {
    private static final int[][] NEIGHBORS = {{-1, -1}, {-1, 0}, {-1, +1}, { 0, -1}, { 0, +1}, {+1, -1}, {+1, 0}, {+1, +1}};
    private int myX;
    private int myY;
    private int[][] neighbors;
    private int myState;
    private int boardHeight;
    private int boardWidth;

    //Need to get size from CSV FILE:
    private static final int TEMP_SIZE  = 100;

    //app.model.Cell constructor - should we be getting board height and width info to the cell some other way than as parameters?
    public Cell(int state, int x, int y, int boardHeight, int boardWidth){
        myState = state;
        myX = x;
        myY = y;
        this.boardHeight = boardHeight;
        this.boardWidth = boardWidth;

        neighbors = findNeighbors();

    }

    //get ArrayList of (x,y) coordinates for valid neighbor expectedCells
    private int[][] findNeighbors() {
        // code to get expectedNeighbors based on current cell's coordinates
        int[][] tempNeighbors = new int[8][2];
        for (int i=0; i<tempNeighbors.length; i++) {
            tempNeighbors[i] = getNeighbor(myX, myY, NEIGHBORS[i]);
        }
        return tempNeighbors;
    }

    //get neighbor coordinates from offset with respect to toroidal edges
    private int[] getNeighbor(int x, int y, int[] offSet){
        int tempX;
        int tempY;

        if(x + offSet[0] >= boardWidth){
            tempX = 0;
        }
        else if(x + offSet[0] < 0){
            tempX = boardWidth-1;
        }
        else{
            tempX = x + offSet[0];
        }

        if(y + offSet[1] >= boardHeight){
            tempY = 0;
        }
        else if(y + offSet[1] < 0){
            tempY = boardHeight-1;
        }
        else{
            tempY = y + offSet[1];
        }

        int[] toBeReturned = {tempX, tempY};
        return toBeReturned;
    }


    //find number of expectedNeighbors in a given state
    private int findNumberOfNeighborsInState(int state, int[][] neighborsList, Board board) {
        int count = 0;
        for (int[] neighbor : neighborsList) {
            if (board.getCells()[neighbor[0]][neighbor[1]].getMyState() == state) {
                count ++;
            }
        }
        return count;
    }

    //for current cell, get next state based on a given app.model.Rules object
    public int getNextState(Rules currentRules, Board board) {
        for (State state : currentRules.getPossibleStates()) {
            if (myState == state.getMyState()){
                for (int[] rule : state.getRulesForState()) {
                    int actual = findNumberOfNeighborsInState(rule[1], neighbors, board);
                    if (actual == rule[2]) {
                        return rule[3];
                    }
                }
            }
        }
        return -1;
    }

    //set cell state
    public void setMyState(int state){
        myState = state;
    }

    //get state
    public int getMyState(){
        return myState;
    }

    //get myX
    public int getMyX(){
        return myX;
    }

    //get myY
    public int getMyY(){
        return myX;
    }

    // get neighbors
    public int[][] getNeighbors() {
        return neighbors;
    }

    @Override
    //compare two cells for equality
    public boolean equals(Object obj) {
        if(obj instanceof Cell){
            Cell tempCell = (Cell) obj;

            if(tempCell.myState == this.myState && tempCell.myX == this.myX && tempCell.myY == this.myY){
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