package app.view;

import app.controller.SimulationController;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class RightView {
    private VBox myRoot;
    private Scene myScene;
    private SimulationController mySimulationController;

    public RightView(SimulationController sc){
        mySimulationController = sc;
        myRoot = new VBox();

    }

}
