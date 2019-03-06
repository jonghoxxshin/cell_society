package app.model;

import java.util.*;


public class Board {

    Cell[][] cells;
    private int myWidth;
    private int myHeight;
    private String myGame;
    private int neighborType;
    private final int[] orderToReplace = {2, 1, 0};
    private int maxChronons = 10;
    private double threshold = 0.3;
    private CSVParser myParser;
    private int errorStatus;
    private GridShapeType myGridShapeType;


    //app.model.Board Constructor

    public Board(ResourceBundle myProperties) {
        myGame = myProperties.getString("type_of_game");
        myGridShapeType = new GridShape().getShape(myProperties.getString("shape"));
        myParser = new CSVParser(myProperties);

        if(myParser.getErrorStatus() == 1){
            // do something
        }

        neighborType = myParser.getNeighborType();
        cells = myParser.getCells();
        System.out.println("first value in board is " + cells[0][0].toString());

        myHeight = myParser.getMyHeight();
        myWidth = myParser.getMyWidth();
    }



    //Update board's expectedCells based on current cell configuration
    public Cell[][] updateBoard(Rules rules) {
        if (rules.getMyRulesParser().getType() == 4) {
            return updateBoardHelper4(rules);
        } else if (rules.getMyRulesParser().getType() == 3) {
            return updateBoardHelper3(rules);
        }
        Cell[][] tempCells = new Cell[myHeight][myWidth];
        for (int i = 0; i < myHeight; i++) {
            for (int j = 0; j < myWidth; j++) {
                tempCells[i][j] = new Cell(cells[i][j].getNextState(rules, this), j, i, myHeight, myWidth, neighborType, -1, -1, myGridShapeType);
            }
        }
        cells = tempCells;
        print2DBoard(cells);
        return tempCells;
    }


    private int getRandomIntFromBound(int bound) {
        Random newRand = new Random();
        return newRand.nextInt(bound);
    }

    private int[][] initializeUpdateBoard(int[][] temp) {
        for (int i = 0; i < myHeight; i++) {
            for (int j = 0; j < myWidth; j++) {
                temp[i][j] = -1;
            }
        }
        return temp;
    }

    private Cell[][] updateBoardHelper4(Rules rules) {
        print2DBoard(cells);
        Cell[][] tempCells = new Cell[myHeight][myWidth];
        int[][] updateBoard = new int[myHeight][myWidth];
        initializeUpdateBoard(updateBoard);
        print2DArray(updateBoard);
        for (int state : orderToReplace) {
            for (int i = 0; i < myHeight; i++) {
                for (int j = 0; j < myWidth; j++) {
                    if (updateBoard[i][j] == -1) {
                        if (cells[i][j].getMyState() == state) {
                            Cell oldCell = cells[i][j];
                            Cell newCell = new Cell(oldCell.getNextState(rules, this), j, i, myHeight, myWidth, neighborType, oldCell.getCurrentChronons(), oldCell.getEnergyLevel(), myGridShapeType);
                            //check if time for reproduction

                            //update temp cells
                            tempCells[i][j] = newCell;
                            updateBoard[i][j] = newCell.getMyState();
                            int[][] neighborCoordinates = oldCell.getNeighbors();
                            //check if need to place a shark or fish after movement
                            if (newCell.getMyState() == 0 && oldCell.getMyState() != 0) {
                                ArrayList<Cell> neighborCells = oldCell.findNeighborsInState(1, neighborCoordinates, this);
                                //check if there are any fish neighbors if shark
                                if (neighborCells.size() > 0 && oldCell.getMyState() == 2) {
                                    if (oldCell.getMyState() == 2 && oldCell.getEnergyLevel() != 0) {
                                        oldCell.decrementEnergyLevel();
                                        Cell cellToReplace = neighborCells.get(getRandomIntFromBound(neighborCells.size()));
                                        cellToReplace.setMyState(oldCell.getMyState());
                                        cellToReplace.setCurrentChronons(oldCell.getCurrentChronons());
                                        cellToReplace.setCurrentEnergyLevel(oldCell.getEnergyLevel()+10);
                                        tempCells[cellToReplace.getMyY()][cellToReplace.getMyX()] = cellToReplace;
                                        updateBoard[cellToReplace.getMyY()][cellToReplace.getMyX()] = cellToReplace.getMyState();
                                    }
                                } else {
                                    if (oldCell.getMyState() == 2 && oldCell.getEnergyLevel() != 0) {
                                        oldCell.decrementEnergyLevel();
                                        ArrayList<Cell> emptyNeighborCells = newCell.findNeighborsInState(0, neighborCoordinates, this);
                                        Cell cellToReplace = emptyNeighborCells.get(getRandomIntFromBound(emptyNeighborCells.size()));
                                        cellToReplace.setMyState(oldCell.getMyState());
                                        cellToReplace.setCurrentEnergyLevel(oldCell.getEnergyLevel());
                                        tempCells[cellToReplace.getMyY()][cellToReplace.getMyX()] = cellToReplace;
                                        updateBoard[cellToReplace.getMyY()][cellToReplace.getMyX()] = cellToReplace.getMyState();
                                    } else if (oldCell.getMyState() != 2){
                                        ArrayList<Cell> emptyNeighborCells = newCell.findNeighborsInState(0, neighborCoordinates, this);
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
        cells = tempCells;
        return tempCells;
    }

    private Cell[][] updateBoardHelper3(Rules rules) {
        int maxNumDislike = getNumNeighborsToSatisfyThreshold();
        ArrayList<Cell> satisfiedCells = new ArrayList<Cell>();
        Stack<Cell> dissatisfiedCells = new Stack<Cell>();
        for (int i = 0; i < myHeight; i++) {
            for (int j = 0; j < myWidth; j++) {
                Cell tempCell = cells[i][j];
                int[][] neighbors = cells[i][j].getNeighbors();
                if (tempCell.findNeighborsInState(getOppositeState(tempCell.getMyState()), neighbors, this).size() > maxNumDislike) {
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
        cells = tempCells;
        return cells;
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
    public static void shuffleList(List<Cell> a) {
        int n = a.size();
        Random random = new Random();
        random.nextInt();
        for (int i = 0; i < n; i++) {
            int change = i + random.nextInt(n - i);
            swap(a, i, change);
        }
    }

    //SOURCE: https://www.vogella.com/tutorials/JavaAlgorithmsShuffle/article.html
    private static void swap(List<Cell> a, int i, int change) {
        Cell helper = a.get(i);
        a.set(i, a.get(change));
        a.set(change, helper);
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


    public Cell getCellAtCoordinates(int x, int y) {
        return cells[y][x];
    }

    //get expectedCells array
    public Cell[][] getCells() {
        return cells;
    }

    public int getMyWidth() {
        return myWidth;
    }

    public int getMyHeight() {
        return myHeight;
    }

    public int getErrorStatus() {
        return errorStatus;
    }
}
