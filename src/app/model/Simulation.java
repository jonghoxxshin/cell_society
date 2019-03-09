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
        if(start) {
            System.out.println(myBoard.getCurrentStateData());
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


}
