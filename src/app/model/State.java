package app.model;

import java.util.ArrayList;
import java.util.List;

/**
 * State Class
 */
public class State {
    private int myState;
    private List<int[]> rulesForState;


    /**
     * State Constructor
     * <p>
     *     gets rules that apply for that satte
     * </p>
     * @param state
     * @param rulesList
     */
    public State(int state, List<int[]> rulesList){
        rulesForState = new ArrayList<int[]>();
        myState = state;
        for (int[] rule : rulesList) {
            if (rule[0] == state) {
                rulesForState.add(rule);
            }
        }
    }


    /**
     * Get State
     *
     * @return state
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public int getMyState (){
        return myState;
    }

    /**
     * Get Rules For State
     *
     * @return list of rules that apply to that state
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public List<int[]> getRulesForState() {
        return rulesForState;
    }
}
