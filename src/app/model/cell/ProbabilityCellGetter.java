package app.model.cell;

import app.model.GridShapeType;
import app.model.cell.Cell;
import app.model.cell.CellGetter;

import java.io.IOException;
import java.util.Random;

/**
 * Probability Cell Getter Class for config based on given probabilities
 * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
 */
public class ProbabilityCellGetter extends CellGetter {

    /**
     * Probability Cell Getter Constructor
     *
     * @param filename
     * @param type
     * @param gameName
     * @param height
     * @param width
     * @param maxState
     * @param neighborType
     * @param shape
     * @param edgeType
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public ProbabilityCellGetter(String filename, String type, String gameName, int height, int width, int maxState, int neighborType, GridShapeType shape, int edgeType){
        super(filename, type, gameName, height, width, maxState, neighborType, shape, edgeType);
    }

    /**
     * Get Cells
     *
     * @return 2d cells array
     * @throws IOException
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     *
     */
    public Cell[][] getCells() throws IOException {
        String[] probStrings = super.getMyType().split("=")[1].split(",");
        super.setMyProbs(stringsToDouble(probStrings));

        if(super.getMyProbs().length != super.getMaxState() + 1){
            super.setErrorStatus(1);
            super.setErrorType("Probabilities incorrectly formatted - different number of probabilities than states!");
            throw new IOException(super.getErrorType());
        }

        if(sumOfDoubles(super.getMyProbs()) != 1){
            super.setErrorStatus(1);
            super.setErrorType("Probabilities incorrectly formatted - don't add up to 1");
            throw new IOException(super.getErrorType());
        }

        Cell[][] cells = new Cell[super.getMyHeight()][super.getMyWidth()];

        for(int i=0; i<super.getMyHeight(); i++){
            for(int j=0; j<super.getMyWidth(); j++){

                int randState = generateNumberWithProbs();
                cells[i][j] = makeCellAtIndex(randState, i, j);
            }
        }

        return cells;
    }


    private int generateNumberWithProbs(){
        int[] allStates = new int[100];

        int currentIndex=0;

        for(int j=0; j<super.getMyProbs().length; j++){
            for(int k=0; k<(super.getMyProbs()[j]*100); k++){
                allStates[currentIndex] = j;
                currentIndex++;
            }
        }

        int randIndex = new Random().nextInt(100);
        return allStates[randIndex];
    }

}
