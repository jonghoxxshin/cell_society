package app.view;

import app.controller.SimulationController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;

import java.util.ResourceBundle;

public class ControlView {
    private SimulationController mySimulationController;
    private HBox myRoot;
    private Slider mySlider;
    private Button stepButton;
    private Button pauseButton;
    private Button newConfigButton;
    private Button startButton;
    private ResourceBundle myProperties;
    private Label mySliderLabel;
    public ControlView (SimulationController sc){
        myProperties = ResourceBundle.getBundle("english");
        mySimulationController = sc;
        myRoot = new HBox();
        setView();
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
        newConfigButton = makeButton(myProperties.getString("new_configuration_button"), e -> this.newConfig);
        myRoot.getChildren().add(newConfigButton);
        startButton = makeButton(myProperties.getString("start_button"), e -> this.start());
        myRoot.getChildren().add(startButton);
    }

    private void setView(){
        setButtons();
        SpeedSlider speedSlider = new SpeedSlider(mySimulationController);
        mySlider = speedSlider.getMySlider();
        mySliderLabel = new Label(myProperties.getString("slider_label"));
        mySliderLabel.setLabelFor(mySlider);
        myRoot.getChildren().add(mySlider);


    }


}
