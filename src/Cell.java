import java.util.ArrayList;

public class Cell {
    private static final int[][] NEIGHBOURS = {{-1, -1}, {-1, 0}, {-1, +1}, { 0, -1}, { 0, +1}, {+1, -1}, {+1, 0}, {+1, +1}};
    private int myX;
    private int myY;
    private ArrayList<int[]> neighbors;
    private int myState;
    private int boardHeight;
    private int boardWidth;

    //Need to get size from CSV FILE:
    private static final int TEMP_SIZE  = 100;

    //Cell constructor - should we be getting board height and width info to the cell some other way than as parameters?
    public Cell(int state, int x, int y, int boardHeight, int boardWidth){
        myState = state;
        myX = x;
        myY = y;
        this.boardHeight = boardHeight;
        this.boardWidth = boardWidth;

        neighbors = findNeighbors();

    }

    //get ArrayList of (x,y) coordinates for valid neighbor cells
    private ArrayList<int[]> findNeighbors() {
        // code to get neighbors based on current cell's coordinates
        ArrayList<int[]> tempNeighbors = new ArrayList<int[]>();
        for (int[] offSet : NEIGHBOURS) {
            if ( myX + offSet[0] > 0 && myX + offSet[0] < boardWidth &&  myY + offSet[1] > 0 && myY + offSet[1] < boardHeight) {
                int[] tempArray = {myX + offSet[0], myY + offSet[1]};
                tempNeighbors.add(tempArray);
            }
        }
        return tempNeighbors;
    }

    //find number of neighbors in a given state
    private int findNumberOfNeighborsInState(int state, ArrayList<int[]> neighborsList, Board board) {
        int count = 0;
        for (int[] neighbor : neighborsList) {
            if (board.getCells()[neighbor[0]][neighbor[1]].getMyState() == state) {
                count ++;
            }
        }
        return count;
    }

    //for current cell, get next state based on a given Rules object
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

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Cell){
            Cell tempCell = (Cell) obj;

            if(tempCell.myState == this.myState && tempCell.myX == this.myX && tempCell.myY == this.myY){
                return true;
            }
        }
        return false;
    }
}
