package app.test;

import app.model.Board;
import app.model.Cell;
import app.model.Rules;
import org.junit.jupiter.api.Test;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void updateBoardForConfigThatStaysTheSame() {
        ResourceBundle myProperties = ResourceBundle.getBundle("test");

        Boolean testBool = true;
<<<<<<< HEAD:src/app/test/BoardTest.java
        Rules myRules = new Rules("GameOfLife");
        Board myBoard = new Board("GameOfLifeConfig4.csv");
=======
        Rules myRules = new Rules(myProperties.getString("type_of_game"));
        Board myBoard = new Board(myProperties);
>>>>>>> kph18:src/app/model/BoardTest.java
        Cell[][] currentBoard = myBoard.getCells();
        myBoard.updateBoard(myRules);
        Cell[][] newBoard = myBoard.getCells();

        for (int i = 0; i < currentBoard.length; i++) {
            for (int j = 0; j < currentBoard[0].length; j++) {
                //System.out.print(currentBoard[j][i].getMyState());
                System.out.print(newBoard[j][i].getMyState());
                if (currentBoard[j][i].getMyState() != newBoard[j][i].getMyState()) {
                    System.out.println(currentBoard[j][i].getMyState());
                    System.out.println(newBoard[i][j].getMyState());

                    testBool = false;
                }
            }
            System.out.println();
        }

        assertTrue(testBool);
    }


}