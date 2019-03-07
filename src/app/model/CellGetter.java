package app.model;

import app.model.Cell;

import java.io.IOException;
import java.util.*;

public class CellGetter {
    private Cell[][] myCells;
    private String csvName;
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


        if(myType.equals("grid")){
            try {
                this.myCells = getCellsFromGrid();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(myType.split("=")[0].equals("probability")){
            try {
                this.myCells = getCellsWithProb();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(myType.split("=")[0].equals("counts")){
            try{
                this.myCells = getCellsWithCounts();
            } catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    // Get rid of duplicate code of making cell of certain index
    private Cell makeCellAtIndex(int state, int row, int column){
        if (gameName.toLowerCase().equals("predatorprey")) {
            return new Cell(state, column, row, myHeight, myWidth, neighborType, 0, 20, myGridShapeType);
        } else {
            return new Cell(state, column, row, myHeight, myWidth, neighborType, -1, -1, myGridShapeType);
        }
    }


    //==================================
    // GETTING CELLS WITH COUNTS
    //==================================

    private Cell[][] getCellsWithCounts() throws IOException{
        String[] countsStrings = myType.split("=")[1].split(",");
        myCounts = stringsToDouble(countsStrings);

        double totalPossible = myHeight * myWidth;

        if(myCounts.length != maxState + 1){
            errorStatus = 1;
            errorType = "Counts incorrectly formatted - different number of counts than states!";
            throw new IOException(errorType);
        }

        if(sumOfDoubles(myCounts) != totalPossible){
            errorStatus = 1;
            errorType = "Counts incorrectly formatted - don't add up to total number possible";
            throw new IOException(errorType);
        }

        return makeCellsFromCounts();
    }

    private Cell[][] makeCellsFromCounts(){
        double[] copyOfCounts = Arrays.copyOf(myCounts, myCounts.length);

        Cell[][] cells = new Cell[myHeight][myWidth];

        for(int i=0; i<myHeight; i++){
            for(int j=0; j<myWidth; j++){
                int randState = new Random().nextInt(maxState+1);

                // potentially O(infinity)... should we change this? Instead, once it gets to zero, just remove
                // that index from consideration?

                while(copyOfCounts[randState] !=0 ){
                    randState = new Random().nextInt(maxState+1);
                }

                copyOfCounts[randState] = copyOfCounts[randState]-1;

                cells[i][j] = makeCellAtIndex(randState, i, j);
            }
        }

        return cells;
    }


    //==================================
    // GETTING CELLS WITH PROBABILITIES
    //==================================

    private double[] stringsToDouble(String[] numbers){
        double[] doubles = new double[numbers.length];

        for(int i=0; i<numbers.length; i++){
            doubles[i] = Double.parseDouble(numbers[i]);
        }

        return doubles;
    }

    private Cell[][] getCellsWithProb() throws IOException{
        String[] probStrings = myType.split("=")[1].split(",");
        myProbs = stringsToDouble(probStrings);

        if(myProbs.length != maxState + 1){
            errorStatus = 1;
            errorType = "Probabilities incorrectly formatted - different number of probabilities than states!";
            throw new IOException(errorType);
        }

        if(sumOfDoubles(myProbs) != 1){
            errorStatus = 1;
            errorType = "Probabilities incorrectly formatted - don't add up to 1";
            throw new IOException(errorType);
        }

        Cell[][] cells = new Cell[myHeight][myWidth];

        for(int i=0; i<myHeight; i++){
            for(int j=0; j<myWidth; j++){

                int randState = generateNumberWithProbs();
                cells[i][j] = makeCellAtIndex(randState, i, j);
            }
        }

        return cells;
    }

    private double sumOfDoubles(double[] doubles){
        double sum = 0;

        for(double number:doubles){
            sum+=number;
        }

        return sum;
    }

    private int generateNumberWithProbs(){
        int[] allStates = new int[100];

        int currentIndex=0;

        for(int j=0; j<myProbs.length; j++){
            for(int k=0; k<(myProbs[j]*100); k++){
                allStates[currentIndex] = j;
                currentIndex++;
            }
        }

        int randIndex = new Random().nextInt(100);
        return allStates[randIndex];
    }

    //==================================
    // GETTING CELLS WITH GRID
    //==================================

    private ArrayList<String[]> generateStateList(){
        Scanner csvScanner = new Scanner(CellGetter.class.getClassLoader().getResourceAsStream(csvName));
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

    private Cell[][] getCellsFromGrid() throws IOException {
        ArrayList<String[]> stateList = generateStateList();
        System.out.println("File read into CellGetter is " + csvName);


        Cell[][] cellsGenerated = new Cell[myHeight][myWidth];

        System.out.println("According to CellGetter, in csv file " + csvName + ", stateList size is " + stateList.size());

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
                if (gameName.toLowerCase().equals("predatorprey")) {
                    cellsGenerated[i][j] = new Cell(Integer.parseInt(currentRow[j]), j, i, myHeight, myWidth, neighborType, 0, 20, myGridShapeType);
                } else {
                    cellsGenerated[i][j] = new Cell(Integer.parseInt(currentRow[j]), j, i, myHeight, myWidth, neighborType, -1, -1, myGridShapeType);
                }
            }

        }
        return cellsGenerated;
    }

    //==================================
    // GETTERS
    //==================================


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
}
