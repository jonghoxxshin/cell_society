package app.model;

import java.io.IOException;
import java.util.Random;

public class ProbabilityCellGetter extends CellGetter{

    public ProbabilityCellGetter(String filename, String type, String gameName, int height, int width, int maxState, int neighborType, GridShapeType shape){
        super(filename, type, gameName, height, width, maxState, neighborType, shape);
    }


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
