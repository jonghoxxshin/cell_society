package app.test;

import app.model.board.PredatorPreyBoard;
import app.model.cell.Cell;
import app.model.rules.Rules;
import org.junit.jupiter.api.Test;

import java.util.ResourceBundle;


class PredatorPreyBoardTest {
    private String PP_1 = "predatorprey1";
    private String PP_2 = "predatorprey2";
    private String T_O_G = "type_of_game";

    @Test
    void updateBoardTest() {
        Boolean testBool = true;
        ResourceBundle myProperties = ResourceBundle.getBundle(PP_1);
        Rules myRules = new Rules(myProperties.getString(T_O_G));
        PredatorPreyBoard testBoard = new PredatorPreyBoard(myProperties);
        Cell[][] oldCells = testBoard.getCells();
        testBoard.updateBoard(myRules);
        Cell[][] newCells = testBoard.getCells();

    }
}