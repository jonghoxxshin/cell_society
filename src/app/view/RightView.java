package app.view;

import app.controller.SimulationController;
import app.model.State;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RightView {
    private VBox myRoot;
    private Scene myScene;
    private SimulationController mySimulationController;
    private ResourceBundle myProperties;
    private ArrayList<State> myPossibleStates;

    public RightView(SimulationController sc){
        mySimulationController = sc;
        myPossibleStates = mySimulationController.getMySimulationModel().getMyRules().getPossibleStates();
        myProperties = ResourceBundle.getBundle("english");
        myRoot = new VBox();
        setView();
    }

    private void setView(){
        for(int i =0; i<myPossibleStates.size();i++){
            setEntry(i);

        }
    }

    private void setEntry(int i){


    }

}
