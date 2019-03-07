package app.model;

import app.model.Cell;

import java.io.IOException;
import java.util.*;

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


    public CellGetter(String filename, String type, String gameName, int height, int width, int maxState, int neighborType, GridShapeType shape){
        this.csvName = filename;
        this.myType = type;
        this.gameName = gameName;

        this.myHeight = height;
        this.myWidth = width;
        this.maxState = maxState;
        this.neighborType = neighborType;
        this.myGridShapeType = shape;

        try {
            this.myCells = getCells();
        } catch (IOException e) {
            System.out.println(this.errorType);
            e.printStackTrace();
        }

    }



    // Get rid of duplicate code of making cell of certain index
    public Cell makeCellAtIndex(int state, int row, int column){
        if (gameName.toLowerCase().equals("predatorprey")) {
            return new Cell(state, column, row, myHeight, myWidth, neighborType, 0, 20, myGridShapeType);
        } else {
            return new Cell(state, column, row, myHeight, myWidth, neighborType, -1, -1, myGridShapeType);
        }
    }

    public double[] stringsToDouble(String[] numbers){
        double[] doubles = new double[numbers.length];

        for(int i=0; i<numbers.length; i++){
            doubles[i] = Double.parseDouble(numbers[i]);
        }

        return doubles;
    }

    public double sumOfDoubles(double[] doubles){
        double sum = 0;

        for(double number:doubles){
            sum+=number;
        }

        return sum;
    }

    public abstract Cell[][] getCells() throws IOException;


    //==================================
    // GETTING CELLS WITH COUNTS
    //==================================




    //==================================
    // GETTERS
    //==================================


    public String getCsvName() {
        return csvName;
    }

    public int getMyHeight() {
        return myHeight;
    }

    public int getMyWidth() {
        return myWidth;
    }

    public GridShapeType getMyGridShapeType() {
        return myGridShapeType;
    }

    public int getMaxState() {
        return maxState;
    }

    public int getNeighborType() {
        return neighborType;
    }

    public String getGameName() {
        return gameName;
    }

    public String getMyType() {
        return myType;
    }

    public int getErrorStatus() {
        return errorStatus;
    }

    public String getErrorType() {
        return errorType;
    }

    public Cell[][] getMyCells() {
        return myCells;
    }

    public double[] getMyProbs() {
        return myProbs;
    }

    public void setMyProbs(double[] args){
        this.myProbs = args;
    }

    public double[] getMyCounts() {
        return myCounts;
    }

    public void setMyCounts(double[] myCounts) {
        this.myCounts = myCounts;
    }

    public void setErrorStatus(int arg){
        this.errorStatus = arg;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }
}
