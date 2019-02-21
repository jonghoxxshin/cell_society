package app.controller;

import app.model.Rules;
import app.model.Simulation;
import app.view.SimulationView;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import app.model.Board;
import app.view.BoardView;
import app.view.MainView;

public class Start extends Application {
    public static final String TITLE = "CELL SIMULATION";
    public static final int APP_WIDHT = 800;
    public static final int APP_HEIGHT = 600;



    private String myGame;
    @Override
    public void start(Stage stage) throws Exception {
        Board b = new Board("GameOfLifeConfig.csv");
        Rules r = new Rules("GameOfLifeConfig.csv");
        BoardView bv = new BoardView(b.getMyWidth(),b.getMyHeight(),b.getCells());
        SimulationController simulationController = new SimulationController(new Simulation(b,r),new SimulationView(bv),APP_WIDHT,APP_HEIGHT);
        //MainView mv = new MainView(new BoardView(b.getMyWidth(),b.getMyHeight(),b.getCells())); this responsibility will be moved to simulationController
        stage.setTitle(TITLE);
        stage.setScene(simulationController.getMyScene());
        stage.setResizable(false);
        stage.show();
    }
    public static void main(String[] args){
        launch(args);
    }


}
