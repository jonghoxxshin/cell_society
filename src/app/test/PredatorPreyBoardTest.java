package app.test;

import app.model.board.PredatorPreyBoard;
import app.model.cell.Cell;
import app.model.rules.Rules;
import org.junit.jupiter.api.Test;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertTrue;


class PredatorPreyBoardTest {
    private String PP_4 = "predatorprey4";
    private String PP_5 = "predatorprey5";
    private String T_O_G = "type_of_game";

    @Test
    void TestSharkDiesWithoutFishToFeedOn() {
        Boolean testBool = true;
        ResourceBundle myProperties = ResourceBundle.getBundle(PP_4);
        Rules myRules = new Rules(myProperties.getString(T_O_G));
        PredatorPreyBoard testBoard = new PredatorPreyBoard(myProperties);
        for (int i = 0; i < 21; i++) {
            testBoard.updateBoard(myRules);
        }
        Cell[][] newCells = testBoard.getCells();
        for (int i = 0; i < newCells.length; i++) {
            for (int j = 0; j < newCells[0].length; j++) {
                if (newCells[j][i].getMyState() == 2) {
                    testBool = false;
                }
            }
        }
        assertTrue(testBool);
    }

    @Test
    void TestSharkEatsFish() {
        Boolean testBool = true;
        ResourceBundle myProperties = ResourceBundle.getBundle(PP_5);
        Rules myRules = new Rules(myProperties.getString(T_O_G));
        PredatorPreyBoard testBoard = new PredatorPreyBoard(myProperties);
        testBoard.updateBoard(myRules);
        Cell[][] newCells = testBoard.getCells();
        for (int i = 0; i < newCells.length; i++) {
            for (int j = 0; j < newCells[0].length; j++) {
                if (newCells[j][i].getMyState() == 1) {
                    testBool = false;
                }
            }
        }
        assertTrue(testBool);
    }
}