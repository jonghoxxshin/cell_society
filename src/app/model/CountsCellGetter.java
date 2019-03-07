package app.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class CountsCellGetter extends CellGetter{

    public CountsCellGetter(String filename, String type, String gameName, int height, int width, int maxState, int neighborType, GridShapeType shape){
        super(filename, type, gameName, height, width, maxState, neighborType, shape);
    }

    public Cell[][] getCells() throws IOException {
        String[] countsStrings = super.getMyType().split("=")[1].split(",");

        super.setMyCounts(stringsToDouble(countsStrings));

        double totalPossible = super.getMyHeight() * super.getMyWidth();

        if(super.getMyCounts().length != super.getMaxState() + 1){
            super.setErrorStatus(1);
            super.setErrorType("Counts incorrectly formatted - different number of counts than states!");
            throw new IOException(super.getErrorType());
        }

        if(sumOfDoubles(super.getMyCounts()) != totalPossible){
            super.setErrorStatus(1);
            super.setErrorType("Counts incorrectly formatted - don't add up to total number possible");
            throw new IOException(super.getErrorType());
        }

        return makeCellsFromCounts();
    }

    private Cell[][] makeCellsFromCounts(){
        double[] copyOfCounts = Arrays.copyOf(super.getMyCounts(), super.getMyCounts().length);

        Cell[][] cells = new Cell[super.getMyHeight()][super.getMyWidth()];

        for(int i=0; i<super.getMyHeight(); i++){
            for(int j=0; j<super.getMyWidth(); j++){
                int randState = new Random().nextInt(super.getMaxState()+1);

                // potentially O(infinity)... should we change this? Instead, once it gets to zero, just remove
                // that index from consideration?

                while(copyOfCounts[randState] == 0){
                    randState = new Random().nextInt(super.getMaxState()+1);
                }

                copyOfCounts[randState] = copyOfCounts[randState]-1;

                cells[i][j] = makeCellAtIndex(randState, i, j);
            }
        }

        return cells;
    }

}