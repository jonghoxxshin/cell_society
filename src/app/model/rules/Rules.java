package app.model.rules;

import app.model.State;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class Rules {
    private List<State> possibleStates;
    private RulesParser myRulesParser;
    private ResourceBundle myProperties;

    //Sets up rules by updating possibleStates ArrayList

    /**
     * Rules Constructor
     * <p>
     *     to be used to apply rules in cell, uses property file as arg
     * </p>
     * @param properties
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public Rules(ResourceBundle properties) {
        myProperties = properties;
        myRulesParser = new RulesParser(myProperties);
        this.possibleStates = myRulesParser.getPossibleStates();
    }

    /**
     * Rules Constructor
     * <p>
     *      to be used to apply rules in cell, uses game Name as arg
     * </p>
     * @param game
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public Rules(String game) {
        myRulesParser = new RulesParser(game);
        this.possibleStates = myRulesParser.getPossibleStates();
    }



    //Get app.model.rules.Rules For a Game

    /**
     * Get Possible State for Rule Set
     *
     * @return set of possible states
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public List<State> getPossibleStates(){

        return possibleStates;
    }
    public RulesParser getMyRulesParser(){return myRulesParser;}

    public ResourceBundle getMyProperties() {
        return myProperties;
    }
}
