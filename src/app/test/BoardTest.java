package app.test;
/**
 * BoardTest Class
 * This class tests the non-visual functionalities of the Board.java
 * Packages:
 * import app.model.board.Board;
 * import app.model.cell.Cell;
 * import app.model.board.GenericBoard;
 * import app.model.rules.Rules;
 * import org.junit.jupiter.api.Test;
 * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
 */

import app.model.board.Board;
import app.model.cell.Cell;
import app.model.board.GenericBoard;
import app.model.rules.Rules;
import org.junit.jupiter.api.Test;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void updateBoardForConfigThatStaysTheSame() {
        ResourceBundle myProperties = ResourceBundle.getBundle("test");

        Boolean testBool = true;


        Rules myRules = new Rules(myProperties.getString("type_of_game"));
        Board myBoard = new GenericBoard(myProperties);


        Cell[][] currentBoard = myBoard.getCells();
        myBoard.updateBoard(myRules);
        Cell[][] newBoard = myBoard.getCells();

        for (int i = 0; i < currentBoard.length; i++) {
            for (int j = 0; j < currentBoard[0].length; j++) {
                //System.out.print(currentBoard[j][i].getMyState());
                // System.out.print(newBoard[j][i].getMyState());
                if (currentBoard[j][i].getMyState() != newBoard[j][i].getMyState()) {
                    testBool = false;
                }
            }
            System.out.println();
        }

        assertTrue(testBool);
    }

    @Test
    void updateBoardForKnownConfig_Toad() {
        ResourceBundle myProperties = ResourceBundle.getBundle("test2");
        Boolean testBool = true;

        Rules myRules = new Rules(myProperties.getString("type_of_game"));
        Board myBoard = new GenericBoard(myProperties);

        Cell[][] currentBoard = myBoard.getCells();
        myBoard.updateBoard(myRules);
        Cell[][] newBoard = myBoard.getCells();

        ResourceBundle propertiesOfExpected = ResourceBundle.getBundle("test3");
        Board expectedB = new GenericBoard(propertiesOfExpected);
        Cell[][] expectedBoard = expectedB.getCells();

        for (int i = 0; i < currentBoard.length; i++) {
            for (int j = 0; j < currentBoard[0].length; j++) {
                if (expectedBoard[j][i].getMyState() != newBoard[j][i].getMyState()) {
                    testBool = false;
                }
            }
        }

        assertTrue(testBool);
    }

}