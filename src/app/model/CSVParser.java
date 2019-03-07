package app.model;

import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class CSVParser {
    private int myHeight;
    private int myWidth;
    private String gameType;
    private int neighborType;
    private Color[] myColors;
    private String myDescription;
    private Cell[][] cells;
    private int errorStatus;
    private String errorType;
    private int maxState;
    private GridShapeType myGridShapeType;
    private CellGetter myCellGetter;

    public CSVParser(ResourceBundle myProperties){
        this.errorStatus = 0;

        String csvGame = myProperties.getString("name_of_csv");

        System.out.println("Grid shape read in was " + myProperties.getString("shape"));

        myGridShapeType = new GridShape().getShape(myProperties.getString("shape"));

        try {
            this.cells = generateCells(csvGame);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public CSVParser(String filename){
        // file name constructor lets us keep tests the same
        this.errorStatus = 0;

        String csvGame = filename;
        myGridShapeType = GridShapeType.RECTANGLE;

        try {
            this.cells = generateCells(csvGame);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void determineNeighborType(){
        if (gameType.toLowerCase().equals("gameoflife")) {
            neighborType = 1;
            maxState = 1;
        } else if (gameType.toLowerCase().equals("percolation")) {
            neighborType = 2;
            maxState = 2;
        } else if (gameType.toLowerCase().equals("rockpaperscissors")) {
            neighborType = 1;
            maxState = 2;
        } else if (gameType.toLowerCase().equals("segregation")){
            neighborType = 1;
            maxState = 2;
        }  else if (gameType.toLowerCase().equals("fire")){
            neighborType = 2;
            maxState = 2;
        }  else if (gameType.toLowerCase().equals("predatorprey")) {
            neighborType = 2;
            maxState = 2;
        }

        else{
            errorStatus = 1;
            errorType = "Invalid game name in CSV";
        }
    }

    private Cell[][] generateCells(String filename) throws IOException{
        Scanner csvScanner = new Scanner(CSVParser.class.getClassLoader().getResourceAsStream(filename));

        this.gameType = csvScanner.next();

        if(gameType.equals("")){
            errorStatus = 1;
            errorType = "Empty game name";
            throw new IOException(this.errorType);
            // if parser object's cells == null, throw an exception in the classes that use it so that when board
            // refreshes, we don't execute partially and have an error - instead, just don't refresh at all
        }

        determineNeighborType();

        if(errorStatus==1){
            throw new IOException(this.errorType);
        }

        try {
            String[] dimensions = csvScanner.next().split(",");

            if(dimensions.length != 2){
                errorStatus = 1;
                errorType = "Invalid dimensions";
                throw new IOException(this.errorType);
            }

            this.myWidth = Integer.parseInt(dimensions[1]);
            this.myHeight = Integer.parseInt(dimensions[0]);

            System.out.println("Height from parser is " + this.myHeight);
        }

        // catching any kind of throwable because there could be an error in case Integer.parseInt doesn't work
        catch(Throwable e){
            errorStatus = 1;
            errorType = "Invalid dimensions";
            throw new IOException(this.errorType);
        }

        String csvType = csvScanner.next();

        myCellGetter = new CellGetter(filename, csvType, gameType, myHeight, myWidth, maxState, neighborType, myGridShapeType);

        if(myCellGetter.getErrorStatus() == 1){
            System.out.println("CellGetter caught error");
            this.errorStatus = 1;
            this.errorType = myCellGetter.getErrorType();
            throw new IOException(this.errorType);
        }

        return myCellGetter.getMyCells();
    }


    // TODO: Just finished invalid state. Need to finish checking for missing values, look over checklist one more time
    // TODO: to see if I have everything. Then, make tests on bad CSV's, work on shapes.

//
//        for(int i=0; i<myHeight; i++){
//            String[] currentRow = csvScanner.next().split(",");
//
//            for(int j=0; j<myWidth; j++){
//                cellsGenerated[j][i] = new Cell(Integer.parseInt(currentRow[j]), j, i, myHeight, myWidth, neighborType);
//            }
//        }
//        return cellsGenerated;
//    }



    public String getGameType() {
        return gameType;
    }

    public int getMyHeight() {
        return myHeight;
    }

    public int getMyWidth() {
        return myWidth;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public int getNeighborType() {
        return neighborType;
    }

    public int getErrorStatus() {
        return errorStatus;
    }

    public String getErrorType() {
        return errorType;
    }

    public CellGetter getMyCellGetter() {
        return myCellGetter;
    }
}
