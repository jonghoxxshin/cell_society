package app.view;

import app.controller.SimulationController;
import javafx.scene.control.Slider;

public class SpeedSlider {
    public static final int MIN_FRAMES = 1;
    public static final int MAX_FRAMES = 10;
    private Slider mySlider;
    private SimulationController mySimulationController;

    public SpeedSlider(SimulationController sc){
        mySimulationController = sc;
        makeSlider();
    }
    private void makeSlider() {

        mySlider = new Slider();

        mySlider.setShowTickLabels(true);
        mySlider.setMin(MIN_FRAMES);
        mySlider.setMax(MAX_FRAMES);
        mySlider.setValue(mySimulationController.getMyFramesPerSecond());
        mySlider.valueProperty().addListener((observableValue, number, t1) -> mySimulationController.setMyFramesPerSecond(t1.intValue()));
    }
    public Slider getMySlider(){return mySlider;}
}
