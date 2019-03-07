package app.view;

import app.controller.SimulationController;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.layout.HBox;

import java.util.ResourceBundle;

public class BottomView {
    private HBox myRoot;
    private Button myStrokeButton;
    private SimulationController mySimulationController;
    private ResourceBundle myProperties;
    private BoardView myBoardView;

    public BottomView(SimulationController sc, BoardView bv, ResourceBundle properties){
        mySimulationController = sc;
        myBoardView = bv;
        myProperties = properties;
        myRoot = new HBox();
        setView();
    }

    public Node getMyRoot(){return myRoot;}

    private void setView(){
        setButton();

    }

    private void setButton(){
        myStrokeButton = new Button("Use stroke");
        myStrokeButton.setOnAction(e->changeStroke());
        myRoot.getChildren().add(myStrokeButton);
    }

    private void changeStroke(){
        myBoardView.changeStroke();
    }



}
