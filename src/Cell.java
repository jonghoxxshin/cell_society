import java.util.ArrayList;

public class Cell {
    private static final int[][] NEIGHBOURS = {{-1, -1}, {-1, 0}, {-1, +1}, { 0, -1}, { 0, +1}, {+1, -1}, {+1, 0}, {+1, +1}};
    private int myX;
    private int myY;
    private ArrayList<int[]> neighbors;
    private int myState;

    //Need to get size from CSV FILE:
    private static final int TEMP_SIZE  = 100;

    public Cell(int state, int x, int y){
        myState = state;
        myX = x;
        myY = y;
        neighbors = findNeighbors();
    }

    private ArrayList<int[]> findNeighbors(){
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






}
