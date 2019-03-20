package app.controller;


import javafx.application.Application;
import javafx.stage.Stage;
import java.util.ResourceBundle;

public class Start extends Application {
    private static final String TITLE = "CELL SIMULATION";
    ResourceBundle myProperties;

    @Override
    public void start(Stage stage) throws Exception {
        myProperties = ResourceBundle.getBundle("example");
        
        SimulationController simulationController = new SimulationController( myProperties);

        stage.setTitle(TITLE);
        stage.setScene(simulationController.getMyScene());
        stage.setResizable(false);
        stage.show();
    }
    public static void main(String[] args){
        launch(args);
    }


}
