package app.controller;

import app.model.Rules;
import app.model.Simulation;
import app.view.SimulationView;
import javafx.application.Application;
import javafx.stage.Stage;
import app.model.Board;
import app.view.BoardView;

public class Start extends Application {
    public static final String TITLE = "CELL SIMULATION";
    public static final int APP_WIDTH = 800;
    public static final int APP_HEIGHT = 600;
    public static int configNumber = 3;

    @Override
    public void start(Stage stage) throws Exception {
        String game = "GameOfLife";

        Board b = new Board(game, configNumber);
        Rules r = new Rules(game);

        BoardView bv = new BoardView(b.getMyWidth(),b.getMyHeight(),b.getCells());
        SimulationController simulationController = new SimulationController(APP_WIDTH,APP_HEIGHT,game, configNumber);
        stage.setTitle(TITLE);
        stage.setScene(simulationController.getMyScene());
        stage.setResizable(false);
        stage.show();
    }
    public static void main(String[] args){
        launch(args);
    }


}
