package app.test;

/**
 * HexCellTest Class
 * This class tests if HexCell.java is implemented correctly
 * Packages:
 * import app.model.board.Board;
 * import app.model.board.GenericBoard;
 * import app.model.cell.HexCell;
 * import app.model.rules.Rules;
 * import org.junit.jupiter.api.Test;
 *
 * import java.util.ResourceBundle;
 *
 * import static org.junit.jupiter.api.Assertions.*;
 * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
 */


import app.model.board.Board;
import app.model.board.GenericBoard;
import app.model.cell.HexCell;
import app.model.rules.Rules;
import org.junit.jupiter.api.Test;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class HexCellTest {
    HexCell hexCell;
    ResourceBundle myProperties;
    Board testBoard;
    Rules testRules;


    @Test
    void findNeighbors() {
        int[] expectedNeighbors1 = {1,2};
        int[] expectedNeighbors2 = {1,1};
        int[] expectedNeighbors3 = {2,1};
        int[] expectedNeighbors4 = {3,2};
        int[] expectedNeighbors5 = {2,3};
        int[] expectedNeighbors6 = {1,3};
        int[][] expectedNeighbors = {expectedNeighbors1, expectedNeighbors2, expectedNeighbors3, expectedNeighbors4, expectedNeighbors5, expectedNeighbors6};
        myProperties = ResourceBundle.getBundle("test3");
        testBoard = new GenericBoard(myProperties);
        testRules = new Rules(myProperties.getString("type_of_game"));
        hexCell = (HexCell) testBoard.getCells()[2][2];

        int[][]myNeighbors = hexCell.getNeighbors();
        boolean testBool = true;
        for (int i = 0; i < expectedNeighbors.length; i++) {
            if (expectedNeighbors[i][0] != myNeighbors[i][0] || expectedNeighbors[i][1] != myNeighbors[i][1]) {
                testBool = false;
            }
        }
        assertTrue(testBool);
    }
}