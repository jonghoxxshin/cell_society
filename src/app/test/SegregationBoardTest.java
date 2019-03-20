package app.test;

import app.model.board.SegregationBoard;
import app.model.cell.Cell;
import app.model.rules.Rules;
import org.junit.jupiter.api.Test;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class SegregationBoardTest {
    private String SEG_1 = "segregation1";
    private String SEG_2 = "segregation2";
    private String T_O_G = "type_of_game";

    @Test
    void updateBoardTestSatisfiedBoard() {
        Boolean testBool = true;
        ResourceBundle myProperties = ResourceBundle.getBundle(SEG_1);
        Rules myRules = new Rules(myProperties.getString(T_O_G));
        SegregationBoard testBoard = new SegregationBoard(myProperties);
        Cell[][] oldCells = testBoard.getCells();
        testBoard.updateBoard(myRules);
        Cell[][] newCells = testBoard.getCells();

        for (int i = 0; i < oldCells.length; i++) {
            for (int j = 0; j < oldCells[0].length; j++) {
                if (oldCells[j][i].getMyState() != newCells[j][i].getMyState()) {
                    testBool = false;
                }
            }
        }

        assertTrue(testBool);
    }

    @Test
    void updateBoardTestDissatisfiedBoard() {
        Boolean testBool = false;
        ResourceBundle myProperties = ResourceBundle.getBundle(SEG_2);
        Rules myRules = new Rules(myProperties.getString(T_O_G));
        SegregationBoard testBoard = new SegregationBoard(myProperties);
        Cell[][] oldCells = testBoard.getCells();
        testBoard.updateBoard(myRules);
        Cell[][] newCells = testBoard.getCells();

        for (int i = 0; i < oldCells.length; i++) {
            for (int j = 0; j < oldCells[0].length; j++) {
                if (oldCells[j][i].getMyState() != newCells[j][i].getMyState()) {
                    testBool = true;
                }
            }
        }

        assertTrue(testBool);
    }
}