package app.test;


/**
 * StateTest Class
 * This class tests if State.java is implemented correctly
 * Packages:
 * import app.model.rules.Rules;
 * import app.model.State;
 * import org.junit.jupiter.api.Test;
 * import java.util.ArrayList;
 * import static org.junit.jupiter.api.Assertions.*;
 * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
 */

import app.model.rules.Rules;
import app.model.State;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StateTest {
    private static final Rules lifeRules  = new Rules("GameOfLife");
    private static final List<State> lifeStates = lifeRules.getPossibleStates();
    private static final Rules percolateRules = new Rules("Percolation");
    private static final List<State> percolateStates = percolateRules.getPossibleStates();
    private Boolean testBool;


    @Test
    void CheckStatesFromGetMyState() {
        testBool = true;
        for (State state : lifeStates) {
            if (state.getMyState() > 2 && state.getMyState() < 0) {
                testBool = false;
            }
        }
        for (State state : percolateStates) {
            if (state.getMyState() > 3 && state.getMyState() < 0) {
                testBool = false;
            }
        }
        assertTrue(testBool);
    }


    @Test
    void CheckRightRulesGetRulesForState() {
        testBool = true;
        for (State state : lifeStates) {
            for (int[] rule : state.getRulesForState()) {
                if (state.getMyState() != rule[0]) {
                    testBool = false;
                }
            }
        }
        for (State state : percolateStates) {
            for (int[] rule : state.getRulesForState()) {
                if (state.getMyState() != rule[0]) {
                    testBool = false;
                }
            }
        }
        assertTrue(testBool);
    }
}