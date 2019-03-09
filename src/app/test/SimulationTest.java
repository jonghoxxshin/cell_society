package app.test;

/**
 * SimulationTest Class
 * This class tests if Simulation.java is implemented correctly
 * Packages:
 * import app.model.*;
 * import org.junit.jupiter.api.Test;
 * import java.util.ResourceBundle;
 * import static org.junit.jupiter.api.Assertions.*;
 * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
 */

import app.model.*;
import app.model.board.Board;
import app.model.board.GenericBoard;
import app.model.cell.Cell;
import app.model.rules.Rules;
import org.junit.jupiter.api.Test;
import java.util.ResourceBundle;
import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {

    @Test
    void nextStepForConfigThatStaysTheSame() {
        ResourceBundle testProperties = ResourceBundle.getBundle("test");
        Board testBoard = new GenericBoard(testProperties);
        Rules testRules = new Rules(testProperties.getString("type_of_game"));
        Boolean testBool = true;
        Simulation testSim  = new Simulation(testBoard, testRules);
        Cell[][] before = testSim.getMyCells();
        testSim.nextStep();
        Cell[][] after = testSim.getMyCells();
        for (int i = 0; i < before.length; i++) {
            for (int j = 0; j < before[i].length; j++) {
                if (!before[j][i].equals(after[j][i])) {
                    testBool = false;
                }
            }
        }
        assertTrue(testBool);
    }
    @Test
    void nextStepForKnownConfig() {
        ResourceBundle testProperties = ResourceBundle.getBundle("test2");
        Board testBoard = new GenericBoard(testProperties);
        Rules testRules = new Rules(testProperties.getString("type_of_game"));
        Simulation testSim  = new Simulation(testBoard, testRules);
        ResourceBundle test2Properties = ResourceBundle.getBundle("test2");
        Board test2Board = new GenericBoard(testProperties);
        Rules test2Rules = new Rules(testProperties.getString("type_of_game"));
        Simulation test2Sim  = new Simulation(test2Board, test2Rules);
        Boolean testBool = true;
        Cell[][] expected = test2Sim.getMyCells();
        testSim.nextStep();
        Cell[][] after = testSim.getMyCells();
        for (int i = 0; i < after.length; i++) {
            for (int j = 0; j < after[i].length; j++) {
                if (!after[j][i].equals(expected[j][i])) {
                    testBool = false;
                }
            }
        }
        assertTrue(testBool);
    }
}