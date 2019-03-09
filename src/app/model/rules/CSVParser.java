package app.model.rules;

import app.model.GridShape;
import app.model.GridShapeType;
import app.model.cell.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * CSV Parser Class, used to read CSV configs
 * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
 */
public class CSVParser {
    private static final String LIFE = "gameoflife";
    private static final String PERCOLATE = "percolation";
    private static final String RPS = "rockpaperscissors";
    private static final String FIRE = "fire";
    private static final String PRED = "predatorprey";
    private static final String SEG = "segregation";
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
    private int edgeType;
    private boolean usingCSV;

    /**
     * CSV Parser Constructor
     * <p>
     *     Get several instance variables from poroperties file, then try
     *     to generate cells
     * </p>
     *
     * @param myProperties
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public CSVParser(ResourceBundle myProperties){
        this.usingCSV = false;

        this.neighborType = Integer.parseInt(myProperties.getString("neighbor_type"));
        this.errorStatus = 0;

        String csvGame = myProperties.getString("name_of_csv");
        String edgePolicy = myProperties.getString("edge_policy");
        getEdgeType(edgePolicy);

        System.out.println("Grid shape read in was " + myProperties.getString("shape"));

        myGridShapeType = new GridShape().getShape(myProperties.getString("shape"));

        try {
            this.cells = generateCells(csvGame);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * CSV Parser Constuctor from Filename
     *
     * @param filename
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public CSVParser(String filename){
        this.usingCSV = true;
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

    //given game type from properties file determine neighbor type for simulation
    private void determineNeighborType(){
        if (gameType.toLowerCase().equals(LIFE)) {
            if(usingCSV) {
                neighborType = 1;
            }
            maxState = 1;
        } else if (gameType.toLowerCase().equals(PERCOLATE)) {
            if(usingCSV) {
                neighborType = 1;
            }
            maxState = 2;
        } else if (gameType.toLowerCase().equals(RPS)) {
            if(usingCSV) {
                neighborType = 1;
            }
            maxState = 2;
        } else if (gameType.toLowerCase().equals(SEG)){
            if(usingCSV) {
                neighborType = 1;
            }
            maxState = 2;
        }  else if (gameType.toLowerCase().equals(FIRE)){
            if(usingCSV) {
                neighborType = 2;
            }
            maxState = 2;
        }  else if (gameType.toLowerCase().equals(PRED)) {
            if(usingCSV) {
                neighborType = 2;
            }
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


        // if using csv and not properties file, determine neighbors type from name of game
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
        if(csvType.startsWith("counts=")){
            myCellGetter = new CountsCellGetter(filename, csvType, gameType, myHeight, myWidth, maxState, neighborType, myGridShapeType, edgeType);
        }
        else if(csvType.startsWith("probability=")){
            myCellGetter = new ProbabilityCellGetter(filename, csvType, gameType, myHeight, myWidth, maxState, neighborType, myGridShapeType, edgeType);
        }
        else{
            myCellGetter = new GridCellGetter(filename, csvType, gameType, myHeight, myWidth, maxState, neighborType, myGridShapeType, edgeType);
        }

        if(myCellGetter.getErrorStatus() == 1){
            System.out.println("CellGetter caught error");
            this.errorStatus = 1;
            this.errorType = myCellGetter.getErrorType();
            throw new IOException(this.errorType);
        }

        return myCellGetter.getMyCells();
    }

    private void getEdgeType(String edgePolicy) {
        if (edgePolicy.toLowerCase().equals("torodial")) {
            edgeType = 0;
        } else if (edgePolicy.toLowerCase().equals("finite")) {
            edgeType = 1;
        } else if (edgePolicy.toLowerCase().equals("flipped")) {
            edgeType = 2;
        }
    }

    /**
     * Get Game Type
     * @return gameType String
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public String getGameType() {
        return gameType;
    }

    /**
     * Get Config Height
     * @return height
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public int getMyHeight() {
        return myHeight;
    }

    /**
     * Get Config Width
     * @return width
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public int getMyWidth() {
        return myWidth;
    }

    /**
     * Get Cells of config
     *
     * @return cells
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public Cell[][] getCells() {
        return cells;
    }

    /**
     * Get neighbor type of config
     *
     * @return neighbor type
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public int getNeighborType() {
        return neighborType;
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
     * @return errortype
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public String getErrorType() {
        return errorType;
    }

    /**
     *
     * @return
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public CellGetter getMyCellGetter() {
        return myCellGetter;
    }
}
