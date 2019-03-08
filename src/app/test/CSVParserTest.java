package app.test;


import app.model.CSVParser;
import app.model.Cell;
import app.model.RectangleCell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

public class CSVParserTest {

    int status = 1;
    int myHeight;
    int myWidth;
    Cell[][] expectedCells;
    String filename;
    CSVParser tester;
    CSVParser readerTester;
    String gameType;

    @BeforeEach
    void setup(){
        ResourceBundle myProperties = ResourceBundle.getBundle("example");
        ResourceBundle readerTestProperties = ResourceBundle.getBundle("test");
        this.filename = "GameOfLife";
        this.tester = new CSVParser(myProperties.getString("name_of_csv"));
        this.readerTester = new CSVParser(readerTestProperties.getString("name_of_csv"));

        // manually create parameters
        this.gameType = "GameOfLife";
        this.myHeight = 20;
        this.myWidth = 25;

        this.expectedCells = new Cell[5][5];

        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                if(i==j){
                    expectedCells[j][i] = new RectangleCell(1, i, j, 5, 5, 1,-1,-1);
                }
                else{
                    expectedCells[j][i] = new RectangleCell(0, i, j, 5, 5, 1,-1,-1);
                }
            }
        }
    }

    @Test
    void readGameTypeTest(){
        assertTrue(this.gameType.equals(tester.getGameType()));
    }

    @Test
    void readDimensionsTest(){
        assertTrue(this.myHeight == tester.getMyHeight() && this.myWidth == tester.getMyWidth());
    }

    @Test
    void readCSVTest(){
        boolean myBool = true;

        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                if(!expectedCells[i][j].equals(readerTester.getCells()[i][j])){
                    System.out.println("Expected cell: " + expectedCells[i][j].toString());
                    System.out.println("Actual cell: " + readerTester.getCells()[i][j].toString());

                    myBool = false;
                }
            }

        }
        assertTrue(myBool);
    }

    @Test
    void invalidNameTest(){
        CSVParser invalidNameTester = new CSVParser("invalidNameTest.csv");

        assertEquals(1, invalidNameTester.getErrorStatus());
        assertEquals("Invalid game name in CSV", invalidNameTester.getErrorType());
    }

    @Test
    void invalidDimensionsStringTest(){
        CSVParser stringDimensionTester = new CSVParser("invalidDimensionsTest.csv");
        assertEquals(1, stringDimensionTester.getErrorStatus());
        assertEquals("Invalid dimensions", stringDimensionTester.getErrorType());
    }

    @Test
    void invalidDimensions3DTest(){
        CSVParser invalidDimensionsTester = new CSVParser("invalidDimensionsTest2.csv");
        assertEquals(1, invalidDimensionsTester.getErrorStatus());
        assertEquals("Invalid dimensions", invalidDimensionsTester.getErrorType());
    }

    @Test
    void invalidHeightTest(){
        CSVParser invalidHeightTester = new CSVParser("invalidHeightTest.csv");
        assertEquals(1, invalidHeightTester.getErrorStatus());
        assertEquals("Grid incorrectly formatted - height", invalidHeightTester.getErrorType());
    }

    @Test
    void invalidWidthTest(){
        CSVParser invalidWidthTester = new CSVParser("invalidWidthTest.csv");
        assertEquals(1, invalidWidthTester.getErrorStatus());
        assertEquals("Grid incorrectly formatted - width", invalidWidthTester.getErrorType());
    }

    @Test
    void invalidStateTest(){
        CSVParser invalidStateTester = new CSVParser("invalidStateTest.csv");
        assertEquals(1, invalidStateTester.getErrorStatus());
        assertEquals("Invalid state in grid for given game", invalidStateTester.getErrorType());
    }

    @Test
    void generateCellsWithProbability(){
        CSVParser probabilityTester = new CSVParser("generateCellsWithProbabilityTest.csv");
        double[] probsArray = {0.4,0.6};

        assertArrayEquals(probsArray, probabilityTester.getMyCellGetter().getMyProbs());
        assertEquals(5, probabilityTester.getCells().length);
        assertEquals(5, probabilityTester.getCells()[0].length);

    }

    @Test
    void generateCellsWithProbabilityWhereTotalTooHigh(){
        CSVParser probabilityTesterHigh = new CSVParser("generateCellsWithProbabilityTestHigh.csv");

        assertEquals(1, probabilityTesterHigh.getErrorStatus());
        assertEquals("Probabilities incorrectly formatted - don't add up to 1", probabilityTesterHigh.getErrorType());

    }

    @Test
    void generateCellsWithProbabilityTooManyStates(){
        CSVParser probabilityTesterState = new CSVParser("generateCellsWithProbabilityTestState.csv");

        assertEquals(1, probabilityTesterState.getErrorStatus());
        assertEquals("Probabilities incorrectly formatted - different number of probabilities than states!", probabilityTesterState.getErrorType());
    }

    @Test
    void generateCellsWithCounts(){
        CSVParser countsTester = new CSVParser("generateCellsWithCountsTest.csv");

        double[] countsArray = {20.0,5.0};

        System.out.println("counts in test read in as " + Arrays.toString(countsTester.getMyCellGetter().getMyCounts()));

        assertArrayEquals(countsArray, countsTester.getMyCellGetter().getMyCounts());

        Cell[][] cells = countsTester.getCells();
        int zeroCounter = 0;
        int oneCounter = 0;

        for(int i=0; i<cells.length; i++){
            for(int j=0; j<cells[0].length; j++){
                if(cells[i][j].getMyState() == 1){
                    oneCounter++;
                }
                else{
                    zeroCounter++;
                }
            }
        }

        assertEquals(20, zeroCounter);
        assertEquals(5, oneCounter);
    }

    @Test
    void generateCellsWithCountsWhereTotalTooHigh(){
        CSVParser countsTesterHigh = new CSVParser("generateCellsWithCountsTestHigh.csv");

        assertEquals(1, countsTesterHigh.getErrorStatus());
        assertEquals("Counts incorrectly formatted - don't add up to total number possible", countsTesterHigh.getErrorType());
    }

    @Test
    void generateCellsWithCountsWhereTooFewStates(){
        CSVParser countsTesterState = new CSVParser("generateCellsWithCountsTestState.csv");

        assertEquals(1, countsTesterState.getErrorStatus());
        assertEquals("Counts incorrectly formatted - different number of counts than states!", countsTesterState.getErrorType());

    }


}
