package app.view;

import app.controller.SimulationController;
import javafx.scene.control.Slider;

public class SpeedSlider {
    private Slider mySlider;
    private SimulationController mySimulationController;

    public SpeedSlider(SimulationController sc){
        mySimulationController = sc;
        makeSlider();
    }
    private void makeSlider() {

        mySlider = new Slider();

        mySlider.setShowTickLabels(true);
        mySlider.setMin(1);
        mySlider.setMax(10);
        mySlider.setValue(mySimulationController.getMyFramesPerSecond());
        mySlider.valueProperty().addListener((observableValue, number, t1) -> mySimulationController.setMyFramesPerSecond(t1.intValue()));
    }
    public Slider getMySlider(){return mySlider;}
}
