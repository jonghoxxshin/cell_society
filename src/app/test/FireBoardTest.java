package app.test;

import app.model.board.FireBoard;
import app.model.board.PredatorPreyBoard;
import app.model.cell.Cell;
import app.model.rules.Rules;
import org.junit.jupiter.api.Test;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class FireBoardTest {
    private String FIRE_1 = "fire1";
    private String T_O_G = "type_of_game";

    @Test
    void TestFireSpreads() {
        Boolean testBool = true;
        int count = 0;
        ResourceBundle myProperties = ResourceBundle.getBundle(FIRE_1);
        Rules myRules = new Rules(myProperties.getString(T_O_G));
        FireBoard testBoard = new FireBoard(myProperties);
        testBoard.updateBoard(myRules);
        Cell[][] newCells = testBoard.getCells();
        for (int i = 0; i < newCells.length; i++) {
            for (int j = 0; j < newCells[0].length; j++) {
                if (newCells[j][i].getMyState() == 2) {
                    count ++;
                }
            }
        }
        if (count < 1) {
            testBool = !testBool;
        }
        assertTrue(testBool);

    }
}