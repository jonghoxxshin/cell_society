package app.model;

public class Simulation {

    private Board myBoard;
    private Cell[][] myCells;
    private Rules myRules;
    private boolean start;

    public Simulation(Board board, Rules rule){
        myBoard = board;
        myCells = board.getCells();
        myRules = rule;
    }

    public void nextStep(){
        System.out.println("here");
        if(start) {
            System.out.println("and here");
            myCells = myBoard.updateBoard(myRules);
        }

    }

    public Rules getMyRules(){
        return myRules;
    }

    public void setStart(){
        start = true;
    }

    public Cell[][] getMyCells(){
        return myCells;
    }

    public void printMyCells(){
        for (int i = 0; i < myCells.length; i++) {
            for (int j = 0; j < myCells[i].length; j++){
                System.out.print(myCells[j][i].getMyState() + ",");
            }
            System.out.println();
        }
        System.out.println();
    }





    double timeTillChange; // note - represent in seconds?
    double probability; // probability we successfully move from one state to another on an event defined in app.model.Rules.
                        // In a normal game, this would be 100%, but in another game, user defined
                        // so, we'd want smulation





    // if we're using java fx, e

    // control how fast/slow board changes the state

    // once board has changed the state, we want to take the board and display it on Main


    // use the app.model.Rules object created by app.model.RulesParser to dictate changes in app.model.Board
}
