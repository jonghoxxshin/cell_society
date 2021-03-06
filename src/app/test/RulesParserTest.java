package app.test;


/**
 * RulesParserTest Class
 * This class checks if RulesParser is implemented correctly
 * Packages:
 * import app.model.rules.RulesParser;
 * import app.model.State;
 * import org.junit.jupiter.api.Test;
 * import java.util.ArrayList;
 * import static org.junit.jupiter.api.Assertions.*;
 * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
 */


import app.model.rules.RulesParser;
import app.model.State;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RulesParserTest {


    @Test
    void getPossibleStatesForLife() {
        RulesParser myRulesParser = new RulesParser("GameOfLife");
        Boolean myTestBool = true;
        List<Integer> expectedStates = new ArrayList<>();
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
        RulesParser myRulesParser = new RulesParser("Percolation");
        Boolean myTestBool = true;
        List<Integer> expectedStates = new ArrayList<>();
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