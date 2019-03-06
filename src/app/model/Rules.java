package app.model;

import java.util.List;

public class Rules {
    private List<State> possibleStates;
    private RulesParser myRulesParser;

    //Sets up rules by updating possibleStates ArrayList
    public Rules(String game) {
        rulesSetup(game);
    }

    //Setup rules for the specific game
    private void rulesSetup (String game) {
        myRulesParser = new RulesParser(game);

        this.possibleStates = myRulesParser.getPossibleStates();
    }

    //Get app.model.Rules For a Game
    public List<State> getPossibleStates(){

        return possibleStates;
    }
    public RulesParser getMyRulesParser(){return myRulesParser;}

}
