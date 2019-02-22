package app.model;

import java.util.ArrayList;

public class Rules {
    private static final String LIFE = "GameOfLife";
    private static final String PERCOLATE = "Percolation";
    private ArrayList<State> possibleStates;
    private RulesParser myRulesParser;

    //Sets up rules by updating possibleStates ArrayList
    public Rules(String game) {
        String trimmedGame = game.split("Config")[0];

        if (trimmedGame.equals(LIFE)) {
            rulesSetup(trimmedGame);
        } else if (trimmedGame.equals(PERCOLATE)) {
            rulesSetup(trimmedGame);

        }
    }

    //Setup rules for the specific game
    private void rulesSetup (String game) {
        myRulesParser = new RulesParser(game);

        this.possibleStates = myRulesParser.getPossibleStates();
    }

    //Get app.model.Rules For a Game
    public ArrayList<State> getPossibleStates(){

        return possibleStates;
    }
    public RulesParser getMyRulesParser(){return myRulesParser;}

}
