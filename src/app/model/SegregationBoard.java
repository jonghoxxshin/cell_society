package app.model;

import java.util.*;

public class SegregationBoard extends Board{
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

    public SegregationBoard(ResourceBundle myProperties){
        super(myProperties);
    }

    @Override
    public Cell[][] updateBoard(Rules rules) {
        int maxNumDislike = getNumNeighborsToSatisfyThreshold();
        ArrayList<Cell> satisfiedCells = new ArrayList<Cell>();
        Stack<Cell> dissatisfiedCells = new Stack<Cell>();
        for (int i = 0; i < myHeight; i++) {
            for (int j = 0; j < myWidth; j++) {
                Cell tempCell = super.getCells()[i][j];
                int[][] neighbors = super.getCells()[i][j].getNeighbors();
                if (tempCell.findNeighborsInState(getOppositeState(tempCell.getMyState()), neighbors, this).size() > maxNumDislike) {
                    System.out.println(tempCell.getMyState() + "," + tempCell.getMyX() + "," + tempCell.getMyY());
                    dissatisfiedCells.push(tempCell);
                } else {
                    satisfiedCells.add(tempCell);
                }
            }
        }
        Cell[][] tempCells = new Cell[myHeight][myWidth];
        int[][] updateBoard = new int[myHeight][myWidth];
        initializeUpdateBoard(updateBoard);
        for (Cell satisfied : satisfiedCells) {
            tempCells[satisfied.getMyY()][satisfied.getMyX()] = satisfied;
            updateBoard[satisfied.getMyY()][satisfied.getMyX()] = 1;
        }
        shuffleStack(dissatisfiedCells);
        for (int i = 0; i < myHeight; i++) {
            for (int j = 0; j < myWidth; j++) {
                if (updateBoard[i][j] == -1) {
                    updateBoard[i][j] = 1;
                    Cell newCell = dissatisfiedCells.pop();
                    newCell.setMyX(j);
                    newCell.setMyY(i);
                    tempCells[i][j] = newCell;
                }
            }
        }
        super.setCells(tempCells);
        return tempCells;
    }

    private int getNumNeighborsToSatisfyThreshold() {
        double maxNumOfDislikeNeighbors = 0;
        if (neighborType == 1) {
            maxNumOfDislikeNeighbors = threshold * 8;
        } else if (neighborType == 2) {
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
    public void shuffleList(List<Cell> a) {
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

}
