package app.model;

import java.util.Random;
import java.util.ResourceBundle;

public class FireBoard extends Board {
    private double growProbability = 0.5;
    private double catchProbability = 0.005;

    public FireBoard(ResourceBundle myProperties) {
        super(myProperties);
    }

    @Override
    public Cell[][] updateBoard(Rules rules) {
        Random generator = new Random();
        double number;
        Cell[][] tempCells = new Cell[super.getMyHeight()][super.getMyWidth()];
        for (int i = 0; i < super.getMyHeight(); i++) {
            for (int j = 0; j < super.getMyWidth(); j++) {
                Cell tempCell = super.getCells()[i][j];
                number = generator.nextDouble();
                int currState = tempCell.getMyState();
                int nextState = tempCell.getNextState(rules, this);
                if (number > growProbability && currState == 0) {
                    tempCells[i][j] = tempCell;
                } else if (number > catchProbability && currState == 1) {
                    if (tempCell.findNeighborsInState(2, tempCell.getNeighbors(),this).size() > 0) {
                        tempCells[i][j] = new Cell(nextState, j, i, super.getMyHeight(), super.getMyWidth(), super.getNeighborType(), -1, -1, super.getMyGridShapeType());
                    } else {
                        tempCells[i][j] = tempCell;
                    }
                }else {
                    tempCells[i][j] = new Cell(nextState, j, i, super.getMyHeight(), super.getMyWidth(), super.getNeighborType(), -1, -1, super.getMyGridShapeType());
                }
            }
        }
        super.setCells(tempCells);
        return tempCells;
    }
}
