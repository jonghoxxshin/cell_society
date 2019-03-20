package app.model.cell;

import app.model.GridShapeType;
import app.model.cell.Cell;
import app.model.cell.CellGetter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

/**
 * Counts Cell Getter Subclass
 * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
 */
public class CountsCellGetter extends CellGetter {

    /**
     * CountsCellGetterConstructor
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
    public CountsCellGetter(String filename, String type, String gameName, int height, int width, int maxState, int neighborType, GridShapeType shape, int edgeType){
        super(filename, type, gameName, height, width, maxState, neighborType, shape, edgeType);
    }

    /**
     * Get Cells for rules cells on counts
     * @return cells
     * @throws IOException
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public Cell[][] getCells() throws IOException {
        String[] countsStrings = super.getMyType().split("=")[1].split(",");

        super.setMyCounts(stringsToDouble(countsStrings));

        if(super.getMyCounts().length != super.getMaxState() + 1){
            super.setErrorStatus(1);
            super.setErrorType("Counts incorrectly formatted - different number of counts than states!");
            throw new IOException(super.getErrorType());
        }

        double totalPossible = super.getMyHeight() * super.getMyWidth();

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
