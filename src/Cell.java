import java.util.ArrayList;

public class Cell {
    private static final int[][] NEIGHBOURS = {{-1, -1}, {-1, 0}, {-1, +1}, { 0, -1}, { 0, +1}, {+1, -1}, {+1, 0}, {+1, +1}};
    private int myX;
    private int myY;
    private ArrayList<int[]> neighbors;
    private int myState;

    //Need to get size from CSV FILE:
    private static final int TEMP_SIZE  = 100;

    //Cell constructor
    public Cell(int state, int x, int y){
        myState = state;
        myX = x;
        myY = y;
        neighbors = findNeighbors();
    }

    //get ArrayList of (x,y) coordinates for valid neighbor cells
    private ArrayList<int[]> findNeighbors() {
        // code to get neighbors based on current cell's coordinates
        ArrayList<int[]> tempNeighbors = new ArrayList<int[]>();
        for (int[] offSet : NEIGHBOURS) {
            if ( myX + offSet[0] > 0 && myX + offSet[0] < TEMP_SIZE &&  myY + offSet[1] > 0 && myY + offSet[1] < TEMP_SIZE) {
                int[] tempArray = {myX + offSet[0], myY + offSet[1]};
                neighbors.add(tempArray);
            }
        }
        return tempNeighbors;
    }

    //find number of neighbors in a given state
    private int findNumberOfNeighborsInState(int state, ArrayList<int[]> neighborsList) {
        return 0;
    }

    //for current cell, get next state based on a given Rules object
    private int getNextState(Rules currentRules) {
        for (State state : currentRules.getPossibleStates()) {
            if (myState == state.getMyState()){
                for (int[] rule : state.getRulesForState()) {
                    int actual = findNumberOfNeighborsInState(rule[1], neighbors);
                    if (actual == rule[2]) {
                        return rule[3];
                    }
                }
            }
        }
        return -1;
    }





}
