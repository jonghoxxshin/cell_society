package model;

import java.util.ArrayList;

public class Rules {
    private static final String LIFE = "life";
    private static final String PERCOLATE = "percolate";
    private ArrayList<State> possibleStates;
    private RulesParser myRulesParser;

    //Sets up rules by updating possibleStates ArrayList
    public Rules(String game) {
        if (game.equals(LIFE)) {
            rulesSetup(game);
        } else if (game.equals(PERCOLATE)) {
            rulesSetup(game);
        }
    }

    //Setup rules for the specific game
    private void rulesSetup (String game) {
        myRulesParser = new RulesParser(game);
        this.possibleStates = myRulesParser.getPossibleStates();
    }

    //Get model.Rules For a Game
    public ArrayList<State> getPossibleStates(){
        return possibleStates;
    }
    public RulesParser getMyRulesParser(){return myRulesParser;}

}
