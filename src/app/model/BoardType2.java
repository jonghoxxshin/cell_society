package app.model;

import java.util.Random;
import java.util.ResourceBundle;

public class BoardType2 extends Board {
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
    private double growProbability = 0.5;
    private double catchProbability = 0.005;

    public BoardType2(ResourceBundle myProperties) {
        super(myProperties);
    }

    @Override
    public Cell[][] updateBoard(Rules rules) {
        Random generator = new Random();
        double number;
        Cell[][] tempCells = new Cell[myHeight][myWidth];
        for (int i = 0; i < myHeight; i++) {
            for (int j = 0; j < myWidth; j++) {
                Cell tempCell = cells[i][j];
                number = generator.nextDouble();
                int currState = tempCell.getMyState();
                int nextState = tempCell.getNextState(rules, this);
                if (number > growProbability && currState == 0) {
                    tempCells[i][j] = tempCell;
                } else if (number > catchProbability && currState == 1) {
                    if (tempCell.findNeighborsInState(2, tempCell.getNeighbors(),this).size() > 0) {
                        tempCells[i][j] = new Cell(nextState, j, i, myHeight, myWidth, neighborType, -1, -1, myGridShapeType);
                    } else {
                        tempCells[i][j] = tempCell;
                    }
                }else {
                    tempCells[i][j] = new Cell(nextState, j, i, myHeight, myWidth, neighborType, -1, -1, myGridShapeType);
                }
            }
        }
        cells = tempCells;
        return tempCells;
    }
}
