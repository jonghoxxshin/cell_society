package app.view;

import app.model.Board;

public class SimulationView {
    private BoardView myBoardView;

    public SimulationView(BoardView bv){
        myBoardView = bv;
    }

    public void updateView(Board board){
        myBoardView.updateBoard(board);

    }
}
