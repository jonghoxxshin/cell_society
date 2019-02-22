package app.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void updateBoardForConfigThatStaysTheSame() {
        Boolean testBool = true;
        Rules myRules = new Rules("GameOfLifeConfig.csv");
        Board myBoard = new Board("GameOfLifeConfig.csv");
        Cell[][] currentBoard = myBoard.getCells();
        myBoard.updateBoard(myRules);
        Cell[][] newBoard = myBoard.getCells();

        for (int i = 0; i < currentBoard.length; i++){
            for (int j = 0; j < currentBoard[0].length; j++){
                System.out.print(currentBoard[j][i].getMyState() + " ");
            }
            System.out.println();
        }
        System.out.println("newBoard");

        for (int i = 0; i < currentBoard.length; i++){
            for (int j = 0; j < currentBoard[0].length; j++){
                System.out.print(newBoard[j][i].getMyState() + " ");
            }
            System.out.println();
        }

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