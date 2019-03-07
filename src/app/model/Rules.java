package app.model;

import java.util.List;
import java.util.ResourceBundle;


public class Rules {
    private List<State> possibleStates;
    private RulesParser myRulesParser;
    private ResourceBundle myProperties;

    //Sets up rules by updating possibleStates ArrayList
    public Rules(ResourceBundle properties) {
        myProperties = properties;
        myRulesParser = new RulesParser(myProperties);
        this.possibleStates = myRulesParser.getPossibleStates();
    }

    public Rules(String game) {
        myRulesParser = new RulesParser(game);
        this.possibleStates = myRulesParser.getPossibleStates();
    }



    //Get app.model.Rules For a Game
    public List<State> getPossibleStates(){

        return possibleStates;
    }
    public RulesParser getMyRulesParser(){return myRulesParser;}

}
