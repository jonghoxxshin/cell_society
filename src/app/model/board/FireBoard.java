package app.model.board;

import app.model.cell.Cell;
import app.model.rules.Rules;

import java.util.Random;
import java.util.ResourceBundle;

/**
 * Board for Fire simulation
 * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
 */
public class FireBoard extends Board {
    private double growProbability;
    private double catchProbability;

    /**
     * Fire Board Constructor
     * <p>
     *     Get catch and grow probabilities from properties file
     * </p>
     *
     * @param myProperties
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public FireBoard(ResourceBundle myProperties) {
        super(myProperties);
        growProbability = Double.parseDouble(myProperties.getString("grow_probability"));
        catchProbability = Double.parseDouble(myProperties.getString("probability"));
    }

    /**
     * Update Board
     * <p>
     *     Following the rules from the rules parser update each cell on the board
     *     to simulate the spread of fire. Grow probability is used to simulate tree
     *     growth and catch probability to simulate fire spread if a neighboring cel
     *     is on fire
     * </p>
     * @param rules
     * @return cells
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
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
                if (number < growProbability && currState == 0) {
                    tempCells[i][j] = tempCell;
                } else if ( currState == 1) {
                    if (tempCell.findNeighborsInState(2, tempCell.getNeighbors(),this).size() > 0 && number < catchProbability) {
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
