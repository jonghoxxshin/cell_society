import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.util.ArrayList;

public class RulesParser {
    private static final String LIFE_RULES = "GameOfLifeRules.txt";
    private static final String PERCOLATE_RULES = "GameOfLifeRules.txt";
    State[] possibleStates;
    private  String gameName;
    ArrayList<Integer> stateArray;
    ArrayList<int[]> rulesArray;

    //set Rule's possibleStates


    private void getInfo(String game) {
        RulesParser myRulesParse = new RulesParser();
        String fileName = null;
        if (game.equals("life")){
            fileName = LIFE_RULES;
        } else if (game.equals("percolate")) {
            fileName = PERCOLATE_RULES;
        }
        InputStream in = myRulesParse.getClass().getClassLoader().getResourceAsStream(fileName);
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
                getRulesFromLine(currentLine);
            }
        } catch (IOException e) {
            System.out.println("Failed to read rules configuration file");
        }
        // open the text file, read in the lines
        // this.posibleStates = Scanner.next();
    }
    private void getRulesFromLine (String line) {
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

    //once we've gotten all the rules from the config file

    public Rules makeRules(){
        return new Rules(possibleStates);
    }


}
