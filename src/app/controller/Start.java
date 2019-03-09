package app.controller;


import javafx.application.Application;
import javafx.stage.Stage;
import java.util.ResourceBundle;

public class Start extends Application {
    public static final String TITLE = "CELL SIMULATION";
    public static final int APP_WIDTH = 1000;
    public static final int APP_HEIGHT = 900;
    ResourceBundle myProperties;

    @Override
    public void start(Stage stage) throws Exception {
        myProperties = ResourceBundle.getBundle("example");

        String game = myProperties.getString("type_of_game");


        SimulationController simulationController = new SimulationController(APP_WIDTH,APP_HEIGHT, game, myProperties);

        stage.setTitle(TITLE);
        stage.setScene(simulationController.getMyScene());
        stage.setResizable(false);
        stage.show();
    }
    public static void main(String[] args){
        launch(args);
    }


}
