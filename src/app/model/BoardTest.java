package app.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void updateBoardForConfigThatStaysTheSame() {
        Boolean testBool = true;
        Rules myRules = new Rules("GameOfLife");
        Board myBoard = new Board("GameOfLife");
        Cell[][] currentBoard = myBoard.getCells();
        myBoard.updateBoard(myRules);
        Cell[][] newBoard = myBoard.getCells();
        for (int i = 0; i < currentBoard.length; i++) {
            for (int j = 0; j < currentBoard[0].length; j++) {
                if (currentBoard[j][i].getMyState() != newBoard[j][i].getMyState()) {
                    testBool = false;
                }
            }
        }

        assertTrue(testBool);
    }


}