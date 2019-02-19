import java.util.ArrayList;

public class State {
    private int myState;
    private ArrayList<int[]> rulesForState;

    //State Constructor, gets rules that apply when that state is initial state
    public State(int state, ArrayList<int[]> rulesList){
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
    public ArrayList<int[]> getRulesForState() {
        return rulesForState;
    }
}
