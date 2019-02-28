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

        // generate the object to be tested
        this.filename = "GameOfLife";
        this.tester = new CSVParser(myProperties.getString("name_of_csv"));
        this.readerTester = new CSVParser(readerTestProperties.getString("name_of_csv"));


        // manually create parameters
        this.gameType = "GameOfLife";
        this.myHeight = 20;
        this.myWidth = 20;

        this.expectedCells = new Cell[5][5];

        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                if(i==j){
                    expectedCells[j][i] = new Cell(1, j, i, myHeight, myWidth, 1);
                }
                else{
                    expectedCells[j][i] = new Cell(0, j, i, myHeight, myWidth, 1);
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

}
