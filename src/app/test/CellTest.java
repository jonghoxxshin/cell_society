package app.test;


import app.model.Board;
import app.model.Cell;
import app.model.Rules;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {
    Cell testCell;
    int[][] expectedNeighbors;
    ResourceBundle myProperties;
    Board testBoard;
    Rules testRules;

    @BeforeEach
    void setUp(){
        myProperties = ResourceBundle.getBundle("test");
        testBoard = new Board(myProperties);
        testRules = new Rules(myProperties.getString("type_of_game"));

        this.testCell = new Cell(0,0,0,5,5, 1);
        int[] neighbor10 = {1,0};
        int[] neighbor11 = {1,1};
        int[] neighbor01 = {0,1};

        int[] neighbor04 = {0,4};
        int[] neighbor14 = {1,4};
        int[] neighbor40 = {4,0};
        int[] neighbor41 = {4,1};
        int[] neighbor44 = {4,4};

        expectedNeighbors = new int[8][2];

        // add in same order as we do when finding cell expectedNeighbors
        expectedNeighbors[0] = neighbor44;
        expectedNeighbors[1] = neighbor40;
        expectedNeighbors[2] = neighbor41;

        expectedNeighbors[3] = neighbor04;
        expectedNeighbors[4] = neighbor01;

        expectedNeighbors[5] = neighbor14;
        expectedNeighbors[6] = neighbor10;
        expectedNeighbors[7] = neighbor11;

    }

    @Test
    void getNextStateForKnown(){
        Cell testCell = testBoard.getCellAtCoordinates(1,1);
        int expected = 1;
        int actual = testCell.getNextState(testRules,testBoard);
        assertEquals(expected,actual);
        actual = testCell.getNextState(testRules,testBoard);
        assertEquals(expected,actual);
    }

    @Test
    void getNextStateForKnownCornerCase(){
        Cell testCell = testBoard.getCellAtCoordinates(4,4);
        int expected = 1;
        int actual = testCell.getNextState(testRules,testBoard);
        assertEquals(expected,actual);
        actual = testCell.getNextState(testRules,testBoard);
        assertEquals(expected,actual);
    }

    @Test
    void getNeighborCornerTest() {
        boolean myBool = true;
        int[][] testNeighbors = testCell.getNeighbors();

        for(int i=0; i<expectedNeighbors.length; i++){

            if(!Arrays.equals(expectedNeighbors[i], testNeighbors[i])){

                System.out.println("Expected neighbor: " + Arrays.toString(expectedNeighbors[i]));
                System.out.println("Actual test neighbor: " + Arrays.toString(testNeighbors[i]));

                myBool = false;
            }
        }

        assertTrue(myBool);
    }

    @Test
    void getNextStateLife() {
        Rules myRules = new Rules("GameOfLife");
    }

    void getNextStatePercolate() {
        Rules myRules = new Rules("Percolation");
    }

}