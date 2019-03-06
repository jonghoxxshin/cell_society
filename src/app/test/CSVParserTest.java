package app.test;


import app.model.CSVParser;
import app.model.Cell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
                    expectedCells[j][i] = new Cell(1, i, j, 5, 5, 1,-1,-1);
                }
                else{
                    expectedCells[j][i] = new Cell(0, i, j, 5, 5, 1,-1,-1);
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

}
