package app.test;

import app.model.board.Board;
import app.model.board.GenericBoard;
import app.model.cell.HexCell;
import app.model.cell.RhombusCell;
import app.model.rules.Rules;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class RhombusCellTest {
    RhombusCell rhombusCell;
    ResourceBundle myProperties;
    Board testBoard;
    Rules testRules;

    private int[][] getNeighborsFromPropFile(ResourceBundle myProperties){
        testBoard = new GenericBoard(myProperties);
        testRules = new Rules(myProperties.getString("type_of_game"));

        rhombusCell = (RhombusCell) testBoard.getCells()[1][2];
        int[][]myNeighbors = rhombusCell.getNeighbors();

        return myNeighbors;
    }


    @Test
    void findNeighbors() {
        int[] expectedNeighbors1 = {1,1};
        int[] expectedNeighbors2 = {1,3};
        int[] expectedNeighbors3 = {2,1};
        int[] expectedNeighbors4 = {2,3};
        int[] expectedNeighbors5 = {1,0};
        int[] expectedNeighbors6 = {1,4};
        int[] expectedNeighbors7 = {0,2};
        int[] expectedNeighbors8 = {2,2};

        int[][] expectedNeighbors = {expectedNeighbors1, expectedNeighbors2, expectedNeighbors3, expectedNeighbors4, expectedNeighbors5, expectedNeighbors6, expectedNeighbors7, expectedNeighbors8};

        myProperties = ResourceBundle.getBundle("test4");
        int[][] myNeighbors = getNeighborsFromPropFile(myProperties);

        assertArrayEquals(expectedNeighbors, myNeighbors);
    }

    @Test
    void findNeighborsType2() {
        int[] expectedNeighbors1 = {1, 1};
        int[] expectedNeighbors2 = {1, 3};
        int[] expectedNeighbors3 = {2, 1};
        int[] expectedNeighbors4 = {2, 3};

        int[][] expectedNeighbors = {expectedNeighbors1, expectedNeighbors2, expectedNeighbors3, expectedNeighbors4};

        myProperties = ResourceBundle.getBundle("test5");
        int[][] myNeighbors = getNeighborsFromPropFile(myProperties);

        assertArrayEquals(expectedNeighbors, myNeighbors);
    }

    @Test
    void findNeighborsType3(){
        int[] expectedNeighbors1 = {0,2};
        int[] expectedNeighbors2 = {2,2};
        int[] expectedNeighbors3 = {0,3};
        int[] expectedNeighbors4 = {1,3};
        int[] expectedNeighbors5 = {1,4};

        int[][] expectedNeighbors = {expectedNeighbors1, expectedNeighbors2, expectedNeighbors3, expectedNeighbors4, expectedNeighbors5};

        myProperties = ResourceBundle.getBundle("test6");
        int[][] myNeighbors = getNeighborsFromPropFile(myProperties);

        assertArrayEquals(expectedNeighbors, myNeighbors);

    }
}