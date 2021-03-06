package app.view;

/*
Authors: Jaiveer Katariya, Jongho Shin, Kyle Harvey




This class is used to generate the view that contains the different components by which the user would control the
simulation. It assumes that the user possesses the following dependencies/packages:
app.controller.SimulationController;
javafx.beans.value.ChangeListener;
javafx.beans.value.ObservableValue;
javafx.collections.FXCollections;
javafx.collections.ObservableList;
javafx.event.ActionEvent;
javafx.event.EventHandler;
javafx.scene.control.*;
javafx.scene.control.Button;
javafx.scene.layout.HBox;
java.util.ArrayList;
java.util.ResourceBundle;

This class is used in the SimluationController and the MainView classes, and to use it, one would simply need to declare
it with the parameters specified by the constructor.

 */

import app.controller.SimulationController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import java.util.List;
import java.util.ResourceBundle;

public class ControlView {

    private SimulationController mySimulationController;
    private NewConfigView myNewConfigView;
    private HBox myRoot;
    private Button gridOutline;
    private ResourceBundle myProperties;
    private List<String> myPropertiesList;
    private boolean myStartBoolean;

    /**
     * Constructor to generate new Control view based on a SimulationController object
     *
     * @param sc SimulationController object to associate with this ControlView object
     */
    public ControlView (SimulationController sc){
        myProperties = ResourceBundle.getBundle("english");
        myStartBoolean = false;
        mySimulationController = sc;
        myRoot = new HBox();
        myPropertiesList = sc.getMyPropertiesList();
        setView();

    }

    /**
     * Function to return root of the Control View
     *
     * @return root/HBox base of ControlView that contains visual components to control the flow/animation of the
     * simulation
     *
     */

    public HBox getMyRoot(){
        return myRoot;
    }

    /**
     * Function to return boolean as to whether or not start has been pressed
     *
     * @return myStartBoolean, which indicates whether or not animation is running
     */
    public boolean getMyStartBoolean(){
        return myStartBoolean;
    }

    private Button makeButton(String name, EventHandler<ActionEvent> handler){
        var result = new Button();
        var text = name;
        result.setText(text);
        result.setOnAction(handler);
        return result;
    }

    private void setButtons(){
        Button tempOneStepButton = makeButton(myProperties.getString("one_step_button"), e->this.oneStep());
        myRoot.getChildren().add(tempOneStepButton);
        Button tempPauseButton = makeButton(myProperties.getString("pause_button"), e -> this.pause());
        myRoot.getChildren().add(tempPauseButton);
        Button tempConfigButton = makeButton(myProperties.getString("new_configuration_button"), e -> this.newConfig());
        myRoot.getChildren().add(tempConfigButton);
        Button tempStartButton = makeButton(myProperties.getString("start_button"), e -> this.start());
        myRoot.getChildren().add(tempStartButton);
    }

    private void setView(){
        makeDropDown();
        setButtons();
        SpeedSlider speedSlider = new SpeedSlider(mySimulationController);
        Slider tempSlider = speedSlider.getMySlider();
        Label tempLabel = new Label(myProperties.getString("slider_label"));
        tempLabel.setLabelFor(tempSlider);
        myRoot.getChildren().add(tempSlider);
        createGridOutlineButton();
    }

    private void pause(){
        mySimulationController.pauseSimulation();
    }

    private void oneStep(){
        myStartBoolean = true;
        mySimulationController.next();
        myStartBoolean = false;
    }

    private void newConfig(){
        if(myNewConfigView==null){
            myNewConfigView = new NewConfigView(mySimulationController);
        }
    }

    private void start(){
        myStartBoolean = true;
        mySimulationController.restartSimulation();
    }

    private void makeDropDown() {
        ObservableList<String> tempConfigOptions = FXCollections.observableArrayList(myPropertiesList);
        ComboBox tempDropDown = new ComboBox(tempConfigOptions);

        tempDropDown.setPromptText(myProperties.getString("load_configuration_button"));
        tempDropDown.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                loadConfig((String) t1);
            }
        });
        myRoot.getChildren().add(tempDropDown);
    }

    private void loadConfig(String t1) {
        this.pause();
        mySimulationController.setConfig(t1);


    }

    private void createGridOutlineButton(){
        gridOutline = makeButton(myProperties.getString("grid_on"), e-> gridChange(gridOutline));
        myRoot.getChildren().add(gridOutline);
    }
    private void gridChange(Button grid) {
        if(grid.getText() == myProperties.getString("grid_on")) {
            grid.setText(myProperties.getString("grid_off"));
        }
        else {
            grid.setText(myProperties.getString("grid_on"));
        }

        mySimulationController.changeGrid();
    }



}
