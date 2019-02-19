package app;

import app.Rules;

public class RulesParser {
    State[] possibleStates;

    //set Rule's possibleStates


    private void getInfo(){
        // open the text file, read in the lines
        // this.posibleStates = Scanner.next();
    }

    //once we've gotten all the rules from the config file

    public Rules makeRules(){
        return new Rules(possibleStates);
    }


}
