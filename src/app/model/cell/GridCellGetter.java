package app.model.cell;

import app.model.GridShapeType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Grid Sell Getter Class, implements cells based on predefined grid
 * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
 */
public class GridCellGetter extends CellGetter {

    /**
     * Grid Cell Getter Constructor
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
    public GridCellGetter(String filename, String type, String gameName, int height, int width, int maxState, int neighborType, GridShapeType shape, int edgeType){
        super(filename, type, gameName, height, width, maxState, neighborType, shape, edgeType);
    }


    private ArrayList<String[]> generateStateList(){
        System.out.println("csvName is " + getCsvName());
        Scanner csvScanner = new Scanner(GridCellGetter.class.getClassLoader().getResourceAsStream(getCsvName()));
        csvScanner.next();
        csvScanner.next();
        csvScanner.next();

        ArrayList<String[]> stateList = new ArrayList<>();

        while(csvScanner.hasNext()) {
            String nextLine = csvScanner.next();
            String[] currentRow = nextLine.split(",");
            stateList.add(currentRow);
        }
        return stateList;
    }

    /**
     * Get Cells
     *
     * @return Cells
     * @throws IOException
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public Cell[][] getCells() throws IOException {
        ArrayList<String[]> stateList = generateStateList();

        Cell[][] cellsGenerated = new Cell[getMyHeight()][getMyWidth()];

        if(stateList.size() != getMyHeight()){
            super.setErrorStatus(1);
            super.setErrorType("Grid incorrectly formatted - height");
            System.out.println("Height of table is " + stateList.size());
            System.out.println("myHeight is " + getMyHeight());
            throw new IOException(super.getErrorType());
        }

        for(int i=0; i<stateList.size(); i++){
            String[] currentRow = stateList.get(i);

            if(currentRow.length!=getMyWidth()){
                super.setErrorStatus(1);
                super.setErrorType("Grid incorrectly formatted - width");
                throw new IOException(super.getErrorType());
            }

            for(int j=0; j<getMyWidth(); j++){
                int currentState = Integer.parseInt(currentRow[j]);
                if(currentState > super.getMaxState() || currentState < 0){
                    super.setErrorStatus(1);
                    super.setErrorType("Invalid state in grid for given game");
                    throw new IOException(super.getErrorType());
                }

                cellsGenerated[i][j] = super.makeCellAtIndex(Integer.parseInt(currentRow[j]), i, j);
            }

        }
        return cellsGenerated;
    }
}
