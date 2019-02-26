package app.model;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class RulesParser {
    private static final String LIFE_RULES = "GameOfLifeRules.txt";
    private static final String PERCOLATE_RULES = "PercolationRules.txt";
    private static final String RPS_RULES = "RockPaperScissorsRules.txt";
    private static final String SEGREGATION_RULES = "SegregationRules.txt";
    private static final String PREDATORPREY_RULES = "PredatorPreyRules.txt";
    private static final String FIRE_RULES = "FireRules.txt";
    private ArrayList<State> possibleStates;
    private String gameName;
    private int type;
    private ArrayList<Integer> stateArray;
    private ArrayList<int[]> rulesArray;


    //Read text file, update possibleStates, gameName, stateArray, and rulesArray from file
    public RulesParser(String game) {
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
        makeRules();
    }

    private String getFileName(String game){
        if (game.equals("GameOfLife")){
            return LIFE_RULES;
        } else if (game.equals("Percolation")) {
            return PERCOLATE_RULES;
        } else if (game.equals("RockPaperScissors")) {
            return RPS_RULES;
        } else if (game.equals("Segregation")) {
            return SEGREGATION_RULES;
        } else if (game.equals("PredatorPrey")) {
            return PREDATORPREY_RULES;
        } else if (game.equals("Fire")) {
            return FIRE_RULES;
        }
        return null;
    }

    //Parse line with rule from file
    private void getRulesFromLine (String line, int type) {
        String[] splitByWhiteSpace = line.split("\\s+");
        Integer startState = Integer.parseInt(splitByWhiteSpace[0]);
        Integer endState = Integer.parseInt(splitByWhiteSpace[2]);
        String[] splitByColon = splitByWhiteSpace[1].split(":");
        Integer desiredNeighborState = Integer.parseInt(splitByColon[0]);
        String requiredAmount = splitByColon[1];
        if (requiredAmount.equals("x")) {
            for (int i = 0; i < 9; i++) {
                int[] tempRuleArray = {startState, desiredNeighborState, i, endState};
                rulesArray.add(tempRuleArray);
            }
        }
        else{
            int[] tempRuleArray = {startState, desiredNeighborState, Integer.parseInt(requiredAmount), endState};
            rulesArray.add(tempRuleArray);
        }
    }

    //Add rules that appply to each state to a state object if possibleStates
    private  ArrayList<State> makeRules(){
        possibleStates = new ArrayList<State>();
        for (Integer state : stateArray) {

            possibleStates.add(new State(state, rulesArray));
        }
        return possibleStates;
    }



    // return possibleStates, ArrayList of app.model.State objects
    public ArrayList<State> getPossibleStates(){
        return possibleStates;
    }

    public String getGameName() {
        return gameName;
    }

    public ArrayList<Integer> getStateArray() {
        return stateArray;
    }

    public ArrayList<int[]> getRulesArray() {
        return rulesArray;
    }

}
