package app.model;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RulesParserTest {


    @Test
    void getPossibleStatesForLife() {
        RulesParser myRulesParser = new RulesParser("life");
        Boolean myTestBool = true;
        ArrayList<Integer> expectedStates = new ArrayList<Integer>();
        expectedStates.add(0);
        expectedStates.add(1);
        for (State state : myRulesParser.getPossibleStates()){
            if (!expectedStates.contains(state.getMyState())){
                myTestBool = false;
            }
            if (state.getRulesForState().size() == 0) {
                myTestBool = false;
            }
        }
        assertTrue(myTestBool);
    }

    @Test
    void getPossibleStatesForPercolate() {
        RulesParser myRulesParser = new RulesParser("percolate");
        Boolean myTestBool = true;
        ArrayList<Integer> expectedStates = new ArrayList<Integer>();
        expectedStates.add(0);
        expectedStates.add(1);
        expectedStates.add(2);
        for (State state : myRulesParser.getPossibleStates()){
            if (!expectedStates.contains(state.getMyState())){
                myTestBool = false;
            }
            if (state.getRulesForState().size() == 0) {
                myTestBool = false;
            }
        }
        assertTrue(myTestBool);
    }
}