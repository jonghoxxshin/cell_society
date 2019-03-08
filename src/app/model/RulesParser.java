package app.model;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RulesParser {
    private static final String LIFE_RULES = "GameOfLifeRules.txt";
    private static final String PERCOLATE_RULES = "PercolationRules.txt";
    private static final String RPS_RULES = "RockPaperScissorsRules.txt";
    private static final String SEGREGATION_RULES = "SegregationRules.txt";
    private static final String PREDATORPREY_RULES = "PredatorPreyRules.txt";
    private static final String FIRE_RULES = "FireRules.txt";
    private static final String LIFE = "gameoflife";
    private static final String PERCOLATE = "percolation";
    private static final String RPS = "rockpaperscissors";
    private static final String SEGREGATION = "segregation";
    private static final String PREDATORPREY = "predatorprey";
    private static final String FIRE = "fire";
    private ArrayList<State> possibleStates;
    private String gameName;
    private int type;
    private ArrayList<Integer> stateArray;
    private ArrayList<int[]> rulesArray;
    private static int numberOfNeighbors;

    //NEED TO REPLACE EVENTUALLY
    private double probability = 0.3;




    //Read text file, update possibleStates, gameName, stateArray, and rulesArray from file
    public RulesParser(String game) {
        this.probability = 0.3;
        rulesArray = new ArrayList<int[]>();
        stateArray = new ArrayList<Integer>();
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(getFileName(game));
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String currentLine = null;
        try{
            currentLine = br.readLine();
            if (currentLine != null) {
                gameName = currentLine;
            }
            currentLine = br.readLine();
            if (currentLine != null) {
                type = Integer.parseInt(currentLine.replace("type:", ""));
            }
            currentLine = br.readLine();
            if (currentLine != null) {
                String[] splitLine = currentLine.replaceAll("\\s+","").split(",");
                for (int i = 0; i < splitLine.length; i++){
                    stateArray.add(Integer.parseInt(splitLine[i]));
                }
            }
            while ((currentLine = br.readLine()) != null) {
                getRulesFromLine(currentLine, type);
            }
        } catch (IOException e) {
            System.out.println("Failed to read rules configuration file");
        }
        //printRulesArray();
        makeRules();
    }

    public RulesParser(ResourceBundle properties){
        this(properties.getString("type_of_game"));
        this.probability = Double.parseDouble(properties.getString("probability"));
    }


    private String getFileName(String game){
        if (game.toLowerCase().equals(LIFE)){
            return LIFE_RULES;
        } else if (game.toLowerCase().equals(PERCOLATE)) {
            return PERCOLATE_RULES;
        } else if (game.toLowerCase().equals(RPS)) {
            return RPS_RULES;
        } else if (game.toLowerCase().equals(SEGREGATION)) {
            return SEGREGATION_RULES;
        } else if (game.toLowerCase().equals(PREDATORPREY)) {
            return PREDATORPREY_RULES;
        } else if (game.toLowerCase().equals(FIRE)) {
            return FIRE_RULES;
        }
        return null;
    }

    //Parse line with rule from file
    private void getRulesFromLine (String line, int type) {
        String[] splitByWhiteSpace = line.split("\\s+");
        Integer startState = Integer.parseInt(splitByWhiteSpace[0]);
        Integer endState = -1;
        Integer altEndState = -1;
        if (type == 4 ) {
            endState = Integer.parseInt(splitByWhiteSpace[2].split(",")[0]);
            try {
                altEndState = Integer.parseInt(splitByWhiteSpace[2].split(",")[1]);
            } catch (ArrayIndexOutOfBoundsException ex) {

            }
        } else {
            endState = Integer.parseInt(splitByWhiteSpace[2]);
        }
        String[] splitByColon = splitByWhiteSpace[1].split(":");
        Integer desiredNeighborState = Integer.parseInt(splitByColon[0]);
        String requiredAmount = splitByColon[1];

        if (requiredAmount.equals("x")) {
            for (int i = 0; i < 9; i++) {
                int[] tempRuleArray = {startState, desiredNeighborState, i, endState, altEndState};
                rulesArray.add(tempRuleArray);
            }

        } else  if (requiredAmount.equals("p")){
            int min = getMinFromProbability();
            for (int i = min; i < numberOfNeighbors+1; i++) {
                int[] tempRuleArray = {startState, desiredNeighborState, i, endState, altEndState};
                rulesArray.add(tempRuleArray);
            }

        } else{
            int[] tempRuleArray = {startState, desiredNeighborState, Integer.parseInt(requiredAmount), endState, altEndState};
            rulesArray.add(tempRuleArray);
        }
    }

    //get minimum number of neighbors to satisfy condition for given priority
    private int getMinFromProbability() {
        if (type == 2) {
            numberOfNeighbors = 4;
        } else {
            numberOfNeighbors = 9;
        }
        return (int) Math.ceil(numberOfNeighbors*probability);
    }

    //Add rules that appply to each state to a state object if possibleStates
    private List<State> makeRules(){
        possibleStates = new ArrayList<State>();
        for (Integer state : stateArray) {

            possibleStates.add(new State(state, rulesArray));
        }
        return possibleStates;
    }

    //print RulesArray
    public void printRulesArray() {
        System.out.println("Set of Rules: " + gameName);

        for (int[] rule : rulesArray) {
            System.out.println("Initial: " + rule[0] + " Desired Neighbor: " + rule[1] + " Required Amount: " + rule[2] + " Final: " + rule[3] + " Alt: " + rule[4]) ;
        }
    }


    // return possibleStates, ArrayList of app.model.State objects
    public ArrayList<State> getPossibleStates(){
        return possibleStates;
    }

    public String getGameName() {
        return gameName;
    }

    public List<Integer> getStateArray() {
        return stateArray;
    }

    public List<int[]> getRulesArray() {
        return rulesArray;
    }

    public int getType(){
        return type;
    }

}
