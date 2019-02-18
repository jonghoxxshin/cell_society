import java.util.ArrayList;

public class State {
    private int myState;
    private ArrayList<int[]> rulesForState;

    //State Constructor
    public State(int state, ArrayList<int[]> rulesList){
        myState = state;
        for (int[] rule : rulesList) {
            if (rule[0] == state) {
                rulesForState.add(rule);
            }
        }
    }

    //Get state
    private int getMyState (){
        return myState;
    }

    //Get rules for that state
    private ArrayList<int[]> getRulesForState() {
        return rulesForState;
    }
}
