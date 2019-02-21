package app.controller;

import app.model.Simulation;
import app.view.SimulationView;
import javafx.animation.Timeline;
import javafx.scene.Scene;

public class SimulationController {
    private Simulation mySimulationModel;
    private SimulationView mySimulationView;
    private int appHeight;
    private int appWidth;
    private Scene myScene;
    private Timeline myAnimation;
    private int myFrameSpeed;

    public SimulationController(Simulation simulationModel, SimulationView simulationView, int height, int width){//Will change to instantiating simulation and simulationView inside controller, not as input
        mySimulationModel = simulationModel;
        mySimulationView = simulationView;
        appHeight = height;
        appWidth = width;
    }

    public Scene getMyScene(){return myScene;}

    public double getMyFrameSpeed(){return myFrameSpeed;}
    public void setMyFrameSpeed(int input){myFrameSpeed = input;}










}
