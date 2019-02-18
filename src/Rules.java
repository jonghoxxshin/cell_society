import java.util.ArrayList;

public class Rules {
    private static final String LIFE = "life";
    private static final String PERCOLATE = "percolate";
    ArrayList<State> possibleStates;


    public Rules(String game) {
        if (game.equals(LIFE)) {
            rulesSetup(game);
        } else if (game.equals(PERCOLATE)) {
            rulesSetup(game);
        }
    }

    private void rulesSetup (String game) {
        RulesParser myRulesParser = new RulesParser(game);
        this.possibleStates = myRulesParser.getPossibleStates();
    }

}
