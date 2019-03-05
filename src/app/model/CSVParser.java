package app.model;

import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.ArrayList;
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

    public CSVParser(String filename){
        this.errorStatus = 0;

        String csvGame = filename;

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
            neighborType = 1;
            maxState = 2;
        } else if (gameType.toLowerCase().equals("rockpaperscissors")) {
            neighborType = 1;
            maxState = 2;
        } else if (gameType.toLowerCase().equals("segregation")){

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

    private ArrayList<String[]> generateStateList(String filename) throws IOException{
        Scanner csvScanner = new Scanner(CSVParser.class.getClassLoader().getResourceAsStream(filename));

        this.gameType = csvScanner.next();

        if(gameType.equals("")){
            errorStatus = 1;
            errorType = "Empty filename";
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
        }

        // catching any kind of throwable because there could be an error in case Integer.parseInt doesn't work
        catch(Throwable e){
            errorStatus = 1;
            errorType = "Invalid dimensions";
            throw new IOException(this.errorType);
        }

        ArrayList<String[]> stateList = new ArrayList<>();

        while(csvScanner.hasNext()) {
            String[] currentRow = csvScanner.next().split(",");
            stateList.add(currentRow);
        }
        return stateList;
    }

    private Cell[][] generateCells(String filename) throws IOException{
        ArrayList<String[]> stateList = new ArrayList<>();
        try {
            stateList = generateStateList(filename);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch(Throwable ee){
            ee.printStackTrace();
            return null;
        }

        Cell[][] cellsGenerated = new Cell[myHeight][myWidth];

        if(stateList.size() != myHeight){
            errorStatus = 1;
            errorType = "Grid incorrectly formatted - height";
            System.out.println("Height of table is " + stateList.size());
            System.out.println("myHeight is " + myHeight);
            throw new IOException(this.errorType);
        }

        for(int i=0; i<stateList.size(); i++){
            String[] currentRow = stateList.get(i);

            if(currentRow.length!=myWidth){
                errorStatus = 1;
                errorType = "Grid incorrectly formatted - width";
                System.out.println("Current row length is " + currentRow.length);
                System.out.println("myWidth is " + myWidth);
                throw new IOException(this.errorType);
            }

            for(int j=0; j<myWidth; j++){
                int currentState = Integer.parseInt(currentRow[j]);

                if(currentState > maxState || currentState < 0){

                    errorStatus = 1;
                    errorType = "Invalid state in grid for given game";
                    throw new IOException(this.errorType);
                }
                cellsGenerated[i][j] = new Cell(Integer.parseInt(currentRow[j]), j, i, myHeight, myWidth, neighborType);
            }

        }
        return cellsGenerated;
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
}
