package app.test;


/**
 * RulesTest Class
 * This class tests of Rules.java is implemented correctly
 * Packages:
 *import app.model.Rules;
 * import app.model.State;
 * import org.junit.jupiter.api.Test;
 * import java.util.ArrayList;
 * import static org.junit.jupiter.api.Assertions.*;
 * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
 */


import app.model.Rules;
import app.model.State;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class RulesTest {

    private static final String LIFE = "GameOfLife";
    private static final String PERCOLATE = "Percolation";


    @Test
    void getPossibleStatesLife() {
        Rules myRules = new Rules(LIFE);
        Boolean myTestBool = true;
        ArrayList<State> stateArray = myRules.getPossibleStates();
        int expected = 2;
        int actual = stateArray.size();
        assertEquals(expected, actual);
        for (State state: stateArray){
            if (state.getRulesForState().size() == 0) {
                myTestBool = false;
            }
            if (state.getMyState() >= expected) {
                myTestBool = false;
            }
        }
        assertTrue(myTestBool);
    }

    @Test
    void getPossibleStatesPercolate() {
        Rules myRules = new Rules(PERCOLATE);
        Boolean myTestBool = true;
        ArrayList<State> stateArray = myRules.getPossibleStates();
        int expected = 3;
        int actual = stateArray.size();
        assertEquals(expected, actual);
        for (State state: stateArray){
            if (state.getRulesForState().size() == 0) {
                myTestBool = false;
            }
            if (state.getMyState() >= expected) {
                myTestBool = false;
            }
        }
        assertTrue(myTestBool);
    }
}