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
    private static final int[][] NEIGHBORS_TYPE1 = {{-1, 0}, {1,0}, {-1, 1}, {1, 1}, {-2, 0}, {2,0}, {0, -1}, {0, 1}};
    RhombusCell rhombusCell;
    ResourceBundle myProperties;
    Board testBoard;
    Rules testRules;


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
        testBoard = new GenericBoard(myProperties);
        testRules = new Rules(myProperties.getString("type_of_game"));

        rhombusCell = (RhombusCell) testBoard.getCells()[1][2];
        int[][]myNeighbors = rhombusCell.findNeighbors(NEIGHBORS_TYPE1);

        boolean testBool = true;
        for (int i = 0; i < expectedNeighbors.length; i++) {
            if (expectedNeighbors[i][0] != myNeighbors[i][0] || expectedNeighbors[i][1] != myNeighbors[i][1]) {
                System.out.println("Expected neighbor: " + Arrays.toString(expectedNeighbors[i]));
                System.out.println("Actual neighbor: " + Arrays.toString(myNeighbors[i]));

                testBool = false;
            }
        }
        assertTrue(testBool);
    }
}