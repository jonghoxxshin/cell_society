package app.controller;

import app.model.Simulation;
import app.view.MainView;
import app.view.SimulationView;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.util.Duration;

public class SimulationController {
    private Simulation mySimulationModel;
    private SimulationView mySimulationView;
    private int appHeight;
    private int appWidth;
    private Scene myScene;
    private Timeline myAnimation;
    private int myFramesPerSecond;
    private MainView myMainView;

    public SimulationController(Simulation simulationModel, SimulationView simulationView, int height, int width){//Will change to instantiating simulation and simulationView inside controller, not as input
        mySimulationModel = simulationModel;
        mySimulationView = simulationView;
        appHeight = height;
        appWidth = width;
        myFramesPerSecond = 2;//magic number that is set for now, need to be changed into form of input later
        setUpScene();
        setTimeline();
    }

    public Scene getMyScene(){return myScene;}

    public double getMyFramesPerSecond(){return myFramesPerSecond;}
    public void setMyFramePerSecond(int input){myFramesPerSecond = input;}


    public KeyFrame makeKeyFrame(){
        return new KeyFrame(Duration.millis(1000/myFramesPerSecond), e -> next(1.0/myFramesPerSecond));
    }

    private void next(double elaspedTime){
        if(mySimulationModel!=null){mySimulationModel.nextStep();}
    }

    private void setTimeline(){
        myAnimation = new Timeline();
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(this.makeKeyFrame());
        myAnimation.play();
    }

    private Scene setUpScene(){


        myScene =
    }


    public void pauseSimulation(){
        myAnimation.pause();
    }

    public void restartSimulation(){
        myAnimation.play();
    }










}
