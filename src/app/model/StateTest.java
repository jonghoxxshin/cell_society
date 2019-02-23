package app.model;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StateTest {
    private static Rules lifeRules  = new Rules("GameOfLife");
    private static ArrayList<State> lifeStates = lifeRules.getPossibleStates();
    private static Rules percolateRules = new Rules("Percolation");
    private static ArrayList<State> percolateStates = percolateRules.getPossibleStates();
    private  Boolean testBool;


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