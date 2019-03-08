package app.model;

import java.util.Random;
import java.util.ResourceBundle;

public class FireBoard extends Board {
    private double growProbability;
    private double catchProbability;

    public FireBoard(ResourceBundle myProperties) {
        super(myProperties);
        growProbability = Double.parseDouble(myProperties.getString("grow_probability"));
        catchProbability = Double.parseDouble(myProperties.getString("probability"));
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
                        tempCells[i][j] = createNewCellFromSubClass(tempCell, nextState, j, i, super.getMyHeight(), super.getMyWidth(), super.getNeighborType(), -1, -1, super.getEdgeType());
                    } else {
                        tempCells[i][j] = tempCell;
                    }
                }else {
                    tempCells[i][j] = createNewCellFromSubClass(tempCell, nextState, j, i, super.getMyHeight(), super.getMyWidth(), super.getNeighborType(), -1, -1, super.getEdgeType());
                }
            }
        }
        super.setCells(tempCells);
        return tempCells;
    }
}
