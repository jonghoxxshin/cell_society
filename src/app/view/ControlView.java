package app.view;

import app.controller.SimulationController;
import app.model.CSVParser;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControlView {
    private ComboBox myDropDown;
    private ObservableList<String> myConfigOptions;

    private SimulationController mySimulationController;
    private NewConfigView myNewConfigView;
    private HBox myRoot;
    private Slider mySlider;
    private Button stepButton;
    private Button pauseButton;
    private Button newConfigButton;
    private Button startButton;
    private ResourceBundle myProperties;
    private Label mySliderLabel;
    private ArrayList<String> myPropertiesList;

    private boolean myStartBoolean;

    public ControlView (SimulationController sc){
        myProperties = ResourceBundle.getBundle("english");
        myStartBoolean = false;
        mySimulationController = sc;
        myRoot = new HBox();
        myPropertiesList = sc.getMyPropertiesList();
        setView();
    }

    public HBox getMyRoot(){
        return myRoot;
    }

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
        stepButton = makeButton(myProperties.getString("one_step_button"), e->this.oneStep());
        myRoot.getChildren().add(stepButton);
        pauseButton = makeButton(myProperties.getString("pause_button"), e -> this.pause());
        myRoot.getChildren().add(pauseButton);
        newConfigButton = makeButton(myProperties.getString("new_configuration_button"), e -> this.newConfig());
        myRoot.getChildren().add(newConfigButton);
        startButton = makeButton(myProperties.getString("start_button"), e -> this.start());
        myRoot.getChildren().add(startButton);
    }

    private void setView(){
        makeDropDown();
        setButtons();
        SpeedSlider speedSlider = new SpeedSlider(mySimulationController);
        mySlider = speedSlider.getMySlider();
        mySliderLabel = new Label(myProperties.getString("slider_label"));
        mySliderLabel.setLabelFor(mySlider);
        myRoot.getChildren().add(mySlider);

    }

    private void pause(){
        mySimulationController.pauseSimulation();
        //myStartBoolean = false;
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
        myConfigOptions = FXCollections.observableArrayList(myPropertiesList);
        myDropDown = new ComboBox(myConfigOptions);
        myDropDown.setPromptText("Load Configuration");
        myDropDown.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                loadConfig((String) t1);
            }
        });
        myRoot.getChildren().add(myDropDown);
    }

    private void loadConfig(String t1) {
        pause();
        mySimulationController.restartSimulationWithNewConfig(t1);

    }

    private void addToDropDown(String config) {
        CSVParser newConfig = new CSVParser(config);
        myConfigOptions.add(config);
    }

    private void createNewConfig(){
        // need to change, currently placeholder to check functionality
        addToDropDown("a new config");
    }

//    private Node makeCreatorText(){
//        // NOTE - JUST A PLACEHOLDER FOR NOW
//        String name = mySimulationController.getMyProperties().getString("name_of_creator");
//        String full = name + "'s Simulation";
//
//        Text nameText = new Text(full);
//
//        nameText.setX(VIEW_WIDTH - (2 * nameText.getBoundsInParent().getWidth()));
//
//        return nameText;
//
//    }



}
