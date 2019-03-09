package app.model;

import java.util.*;

/**
 * Board subclass to implement segregation simulation
 * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
 */
public class SegregationBoard extends Board{
    private double threshold;

    /**
     * Segregation Board Constructor
     * <p>
     *     same as super constructor but set threshold
     *     to probability from resource file too
     * </p>
     * @param myProperties
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public SegregationBoard(ResourceBundle myProperties){
        super(myProperties);
        threshold = Double.parseDouble(myProperties.getString("probability"));
    }

    /**
     * Update Board For Segregation
     * <p>
     *     After calculating the maximum number of dislike neighbors allowed for a cell to be satisfied
     *     parse through the current board. For each cell determine if it is satisfied or dissatisfied.
     *     If satisfied, add to arraylist of satisfied cells, if not add to stack of dissatisfied cells.
     *     Once all cells have been moved to one of these collections, add all satisfied cells to temp board
     *     in the same location as before, then randomly place each of dissatisfied cells in the remaining board.
     *     Randomness is simulated by shuffling the contents of the dissatisfied stack.
     * </p>
     * @param rules
     * @return newly updated board
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    @Override
    public Cell[][] updateBoard(Rules rules) {
        int maxNumDislike = getNumNeighborsToSatisfyThreshold();
        System.out.println(maxNumDislike);
        ArrayList<Cell> satisfiedCells = new ArrayList<Cell>();
        Stack<Cell> dissatisfiedCells = new Stack<Cell>();
        for (int i = 0; i < super.getMyHeight(); i++) {
            for (int j = 0; j < super.getMyWidth(); j++) {
                Cell tempCell = super.getCells()[i][j];
                int[][] neighbors = tempCell.getNeighbors();
                if (tempCell.findNeighborsInState(getOppositeState(tempCell.getMyState()), neighbors, this).size() > maxNumDislike) {
                    dissatisfiedCells.push(tempCell);
                } else {
                    satisfiedCells.add(tempCell);
                }
            }
        }
        Cell[][] tempCells = new Cell[super.getMyHeight()][super.getMyWidth()];
        int[][] updateBoard = new int[super.getMyHeight()][super.getMyWidth()];
        initializeUpdateBoard(updateBoard);
        for (Cell satisfied : satisfiedCells) {
            tempCells[satisfied.getMyY()][satisfied.getMyX()] = satisfied;
            updateBoard[satisfied.getMyY()][satisfied.getMyX()] = 1;
        }
        shuffleStack(dissatisfiedCells);
        for (int i = 0; i < super.getMyHeight(); i++) {
            for (int j = 0; j < super.getMyWidth(); j++) {
                if (updateBoard[i][j] == -1) {
                    updateBoard[i][j] = 1;
                    Cell newCell = dissatisfiedCells.pop();
                    newCell.setMyX(j);
                    newCell.setMyY(i);
                    newCell.setNeighbors(super.getCells()[i][j].getNeighbors());
                    tempCells[i][j] = newCell;
                }
            }
        }
        super.setCells(tempCells);
        return tempCells;
    }

    private int getNumNeighborsToSatisfyThreshold() {
        double maxNumOfDislikeNeighbors = 0;
        if (super.getNeighborType() == 1) {
            maxNumOfDislikeNeighbors = threshold * 8;
        } else if (super.getNeighborType() == 2) {
            maxNumOfDislikeNeighbors = threshold * 4;
        }

        return (int) Math.floor(maxNumOfDislikeNeighbors);
    }

    private int getOppositeState(int n) {
        if (n == 1) {
            return 2;
        } else if (n == 2) {
            return 1;
        }
        return 0;
    }

    private Stack<Cell> shuffleStack(Stack<Cell> pq) {
        ArrayList<Cell> tempList = new ArrayList<Cell>();
        while (!pq.isEmpty()) {
            tempList.add(pq.pop());
        }
        shuffleList(tempList);
        while (tempList.size() > 0) {
            pq.push(tempList.remove(0));
        }
        return pq;
    }

    //SOURCE: https://www.vogella.com/tutorials/JavaAlgorithmsShuffle/article.html
    private void shuffleList(List<Cell> a) {
        int n = a.size();
        Random random = new Random();
        random.nextInt();
        for (int i = 0; i < n; i++) {
            int change = i + random.nextInt(n - i);
            swap(a, i, change);
        }
    }

    //SOURCE: https://www.vogella.com/tutorials/JavaAlgorithmsShuffle/article.html
    private void swap(List<Cell> a, int i, int change) {
        Cell helper = a.get(i);
        a.set(i, a.get(change));
        a.set(change, helper);
    }

    private int[][] initializeUpdateBoard(int[][] temp) {
        for (int i = 0; i < super.getMyHeight(); i++) {
            for (int j = 0; j < super.getMyWidth(); j++) {
                temp[i][j] = -1;
            }
        }
        return temp;
    }


}
