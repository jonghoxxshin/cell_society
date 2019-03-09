package app.model;

/**
 * Simulation Class: contains all important objects in a simulation
 * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
 */
public class Simulation {

    private Board myBoard;
    private Cell[][] myCells;
    private Rules myRules;
    private boolean start;

    /**
     * Simulation Constructor
     *
     * @param board
     * @param rule
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public Simulation(Board board, Rules rule){
        myBoard = board;
        myCells = board.getCells();
        myRules = rule;
    }

    /**
     * Step Simulation
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public void nextStep(){
        if(start) {
            System.out.println(myBoard.getCurrentStateData());
            myCells = myBoard.updateBoard(myRules);
        }
    }

    /**
     * Get Simulation Rules
     *
     * @return rules
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public Rules getMyRules(){
        return myRules;
    }

    /**
     * Start Simulation
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public void setStart(){
        start = true;
    }

    /**
     * Get current cell config of Simulation
     * @return cells
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public Cell[][] getMyCells(){
        return myCells;
    }


}
