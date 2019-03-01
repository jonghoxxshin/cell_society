package app.view;

import app.controller.SimulationController;
import app.model.State;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RightView {
    private VBox myRoot;
    private SimulationController mySimulationController;
    private ResourceBundle myProperties;
    private ArrayList<State> myPossibleStates;
    private Button mySubmitButton;
    private ColorPicker myColorPicker0;
    private ColorPicker myColorPicker1;
    private ColorPicker myColorPicker2;

    public RightView(SimulationController sc){
        mySimulationController = sc;
        myPossibleStates = mySimulationController.getMySimulationModel().getMyRules().getPossibleStates();
        myProperties = ResourceBundle.getBundle("english");
        myRoot = new VBox();
        setView();
    }
    public Node getMyRoot(){
        return myRoot;
    }
    private void setView(){
        setHBox0();
        setHBox1();
        setHBox2();
        setSubmitButton();
    }
    private void setSubmitButton(){
        mySubmitButton = new Button(myProperties.getString("submit"));
        mySubmitButton.setOnAction(e ->  this.submitColor());
        myRoot.getChildren().add(mySubmitButton);
    }
    private void submitColor(){

    }
    private void setHBox0(){
        HBox temp = new HBox();
        Label tempLabel = new Label(myProperties.getString("state_0_color"));
        temp.getChildren().add(tempLabel);
        myColorPicker0 = new ColorPicker();
        temp.getChildren().add(myColorPicker0);
        temp.setAlignment(Pos.CENTER);
        myRoot.getChildren().add(temp);
    }
    private void setHBox1(){
        HBox temp = new HBox();
        Label tempLabel = new Label(myProperties.getString("state_1_color"));
        temp.getChildren().add(tempLabel);
        myColorPicker1 = new ColorPicker();
        temp.getChildren().add(myColorPicker1);
        temp.setAlignment(Pos.CENTER);
        myRoot.getChildren().add(temp);

    }
    private void setHBox2(){
        HBox temp = new HBox();
        Label tempLabel = new Label(myProperties.getString("state_2_color"));
        temp.getChildren().add(tempLabel);
        myColorPicker2 = new ColorPicker();
        temp.getChildren().add(myColorPicker2);
        temp.setAlignment(Pos.CENTER);
        myRoot.getChildren().add(temp);

    }



}
