package app.model;

import org.junit.jupiter.api.Test;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void updateBoardForConfigThatStaysTheSame() {
        ResourceBundle myProperties = ResourceBundle.getBundle("test");

        Boolean testBool = true;
        Rules myRules = new Rules(myProperties.getString("type_of_game"));
        Board myBoard = new Board(myProperties);
        Cell[][] currentBoard = myBoard.getCells();
        myBoard.updateBoard(myRules);
        Cell[][] newBoard = myBoard.getCells();
        for (int i = 0; i < currentBoard.length; i++) {
            for (int j = 0; j < currentBoard[0].length; j++) {
                if (currentBoard[j][i].getMyState() != newBoard[j][i].getMyState()) {
                    System.out.println(currentBoard[j][i].getMyState());
                    System.out.println(newBoard[i][j].getMyState());

                    testBool = false;
                }
            }
        }

        assertTrue(testBool);
    }


}