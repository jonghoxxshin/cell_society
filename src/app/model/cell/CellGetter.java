package app.model.cell;

import app.model.GridShapeType;

import java.io.IOException;

/**
 * Abstract Cell Getter Class
 * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
 */
public abstract class CellGetter {
    private Cell[][] myCells;
    String csvName;
    private String myType;
    private int myHeight;
    private int myWidth;
    private int maxState;
    private int neighborType;
    private GridShapeType myGridShapeType;
    private String gameName;
    private int errorStatus;
    private String errorType;
    private double[] myProbs;
    private double[] myCounts;
    private int edgePolicy;

    /**
     * Cell Getter Constructor
     *
     * @param filename
     * @param type
     * @param gameName
     * @param height
     * @param width
     * @param maxState
     * @param neighborType
     * @param shape
     * @param edgePolicy
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public CellGetter(String filename, String type, String gameName, int height, int width, int maxState, int neighborType, GridShapeType shape, int edgePolicy){
        this.csvName = filename;
        this.myType = type;
        this.gameName = gameName;

        this.myHeight = height;
        this.myWidth = width;
        this.maxState = maxState;
        this.neighborType = neighborType;
        this.myGridShapeType = shape;
        this.edgePolicy = edgePolicy;

        try {
            this.myCells = getCells();
        } catch (IOException e) {
            System.out.println(this.errorType);
            e.printStackTrace();
        }

    }
    /**
     * Get rid of duplicate code of making cell of certain index
     *
     * @param state
     * @param row
     * @param column
     * @return Cell
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public Cell makeCellAtIndex(int state, int row, int column){
        if (gameName.equalsIgnoreCase("predatorprey")) {
            return createNewCellFromProperty(myGridShapeType, state, column, row, myHeight, myWidth, neighborType, 0, 20, edgePolicy);
        } else {
            return createNewCellFromProperty(myGridShapeType, state, column, row, myHeight, myWidth, neighborType, -1, -1, edgePolicy);
        }
    }

    /**
     * Convert String Array to Double Array
     *
     * @param numbers
     * @return double array
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public double[] stringsToDouble(String[] numbers){
        double[] doubles = new double[numbers.length];

        for(int i=0; i<numbers.length; i++){
            doubles[i] = Double.parseDouble(numbers[i]);
        }

        return doubles;
    }

    /**
     * Calculate Sum of Doubles Array
     *
     * @param doubles
     * @return sum
     */
    public double sumOfDoubles(double[] doubles){
        double sum = 0;

        for(double number:doubles){
            sum+=number;
        }

        return sum;
    }

    /**
     * Get Cells
     *
     * @return 2d cell array
     * @throws IOException
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public abstract Cell[][] getCells() throws IOException;


    //==================================
    // GETTING CELLS WITH COUNTS
    //==================================




    //==================================
    // GETTERS
    //==================================


    /**
     * Get CSV Name
     * @return csv name
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public String getCsvName() {
        return csvName;
    }

    /**
     * Get Height
     * @return height
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public int getMyHeight() {
        return myHeight;
    }

    /**
     * get Width
     * @return width
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public int getMyWidth() {
        return myWidth;
    }

    /**
     * Get my grid shape
     *
     * @return gridShape
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public GridShapeType getMyGridShapeType() {
        return myGridShapeType;
    }


    /**
     * get Max state
     *
     * @return max state
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public int getMaxState() {
        return maxState;
    }

    /**
     * get Neighbor Type
     *
     * @return neighbor type
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public int getNeighborType() {
        return neighborType;
    }

    /**
     * Get Game Name
     *
     * @return gameName
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * Create New Instance of Cell SubClass
     * <p>
     *     based on gridshape type create a new cell of shape
     *     rhombus rectangle or hexagon
     * </p>
     *
     * @param gridShapeType
     * @param state
     * @param x
     * @param y
     * @param boardHeight
     * @param boardWidth
     * @param neighborType
     * @param chronons
     * @param energy
     * @param edgeType
     * @return cell
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public Cell createNewCellFromProperty (GridShapeType gridShapeType, int state, int x, int y, int boardHeight, int boardWidth, int neighborType, int chronons, int energy, int edgeType){
        if (gridShapeType == GridShapeType.RHOMBUS) {
            return new RhombusCell(state, x, y, boardHeight, boardWidth, neighborType, chronons, energy, edgeType);
        } else if (gridShapeType == GridShapeType.HEXAGON) {
            return new HexCell(state, x, y, boardHeight, boardWidth, neighborType, chronons, energy, edgeType);
        } else {
            return new RectangleCell(state, x, y, boardHeight, boardWidth, neighborType, chronons, energy, edgeType);
        }
    }

    //==================================
    // GETTERS
    //==================================

    /**
     * Get Type
     *
     * @return type of cell
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public String getMyType() {
        return myType;
    }

    /**
     * Get Error Status
     *
     * @return error status
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public int getErrorStatus() {
        return errorStatus;
    }

    /**
     * Get Error Type
     *
     * @return
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public String getErrorType() {
        return errorType;
    }

    /**
     * Get Cells
     *
     * @return cells
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public Cell[][] getMyCells() {
        return myCells;
    }


    /**
     * Get Probability Array
     *
     * @return probability array
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public double[] getMyProbs() {
        return myProbs;
    }

    /**Set probability array
     *
     * @param args
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public void setMyProbs(double[] args){
        this.myProbs = args;
    }

    /**
     * Get Count
     *
     * @return counts array
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public double[] getMyCounts() {
        return myCounts;
    }

    /**
     * Set counts array
     *
     * @param myCounts
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public void setMyCounts(double[] myCounts) {
        this.myCounts = myCounts;
    }

    /**
     * Set error status
     *
     * @param arg
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public void setErrorStatus(int arg){
        this.errorStatus = arg;
    }

    /**
     * set Error Type
     *
     * @param errorType
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }
}
