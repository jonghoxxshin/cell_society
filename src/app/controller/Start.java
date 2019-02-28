package app.controller;

import app.model.Rules;
import app.model.Simulation;
import app.view.SimulationView;
import javafx.application.Application;
import javafx.stage.Stage;
import app.model.Board;
import app.view.BoardView;

import java.util.ResourceBundle;

public class Start extends Application {
    public static final String TITLE = "CELL SIMULATION";
    public static final int APP_WIDTH = 800;
    public static final int APP_HEIGHT = 600;

    private String filename;
    private String game;
    private ResourceBundle myProperties;




    public Start(){
        this.myProperties = ResourceBundle.getBundle("example");
        this.game = myProperties.getString("type_of_game");
        this.filename = myProperties.getString("name_of_csv");
    }

    @Override
    public void start(Stage stage) throws Exception {
        String game = this.filename.split("Config")[0];

        Board b = new Board(filename);
        Rules r = new Rules(game);

        BoardView bv = new BoardView(b.getMyWidth(),b.getMyHeight(),b.getCells(), myProperties);
        SimulationController simulationController = new SimulationController(APP_WIDTH,APP_HEIGHT, myProperties);
        stage.setTitle(TITLE);
        stage.setScene(simulationController.getMyScene());
        stage.setResizable(false);
        stage.show();
    }
    public static void main(String[] args){
        launch(args);
    }


}
