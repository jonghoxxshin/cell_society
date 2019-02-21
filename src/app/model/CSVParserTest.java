package app.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CSVParserTest {

    int status = 1;
    int myHeight;
    int myWidth;
    Cell[][] expectedCells;
    String filename;
    CSVParser tester;
    String gameType;

    @BeforeEach
    void setup(){
        // generate the object to be tested
        this.filename = "GameOfLifeConfig.csv";
        this.tester = new CSVParser(filename);

        // manually create parameters
        this.gameType = "GameOfLife";
        this.myHeight = 5;
        this.myWidth = 5;

        this.expectedCells = new Cell[myHeight][myWidth];

        for(int i=0; i<myHeight; i++){
            for(int j=0; j<myWidth; j++){
                if(i==j){
                    expectedCells[i][j] = new Cell(1, j, i, myHeight, myWidth);
                }
                else{
                    expectedCells[i][j] = new Cell(0, j, i, myHeight, myWidth);
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

        for(int i=0; i<myHeight; i++){
            for(int j=0; j<myWidth; j++){
                if(!expectedCells[i][j].equals(tester.getCells()[i][j])){
                    System.out.println("Expected cell: " + expectedCells[i][j].toString());
                    System.out.println("Actual cell: " + tester.getCells()[i][j].toString());

                    myBool = false;
                }
            }

        }

        assertTrue(myBool);

    }

}
