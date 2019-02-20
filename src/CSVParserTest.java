import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;

public class CSVParserTest {

    int status = 1;
    int myHeight;
    int myWidth;
    Cell[][] cells;
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

        this.cells = new Cell[myHeight][myWidth];

        for(int i=0; i<myHeight; i++){
            for(int j=0; j<myWidth; j++){
                if(i==j){
                    cells[i][j] = new Cell(1, j, i, myHeight, myWidth);
                }
                else{
                    cells[i][j] = new Cell(0, j, i, myHeight, myWidth);
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
        assertArrayEquals(this.cells, tester.cells);

    }

}
