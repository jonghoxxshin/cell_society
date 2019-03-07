package app.model;

import java.util.ArrayList;
import java.util.List;

public class State {
    private int myState;
    private List<int[]> rulesForState;

    //app.model.State Constructor, gets rules that apply when that state is initial state
    public State(int state, List<int[]> rulesList){
        rulesForState = new ArrayList<int[]>();
        myState = state;
        for (int[] rule : rulesList) {
            if (rule[0] == state) {
                rulesForState.add(rule);
            }
        }
    }

    //Get state
    public int getMyState (){
        return myState;
    }

    //Get rules for that state
    public List<int[]> getRulesForState() {
        return rulesForState;
    }
}
