package app.controller;

import app.model.Board;
import app.model.Rules;
import app.model.Simulation;
import app.view.BoardView;
import app.view.MainView;
import app.view.SimulationView;
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
    private Board myBoard;
    private Rules myRules;
    private MainView myMainView;
    private BoardView myBoardView;

    public SimulationController( int height, int width){//Will change to instantiating simulation and simulationView inside controller, not as input
        myBoard = new Board("GameOfLifeConfig2.csv");
        myRules = new Rules("GameOfLifeConfig2.csv");
        myBoardView = new BoardView(myBoard.getMyWidth(),myBoard.getMyHeight(),myBoard.getCells());
        mySimulationModel = new Simulation(myBoard,myRules);
        mySimulationView = new SimulationView(myBoardView);
        appHeight = height;
        appWidth = width;
        myFramesPerSecond = 1;//magic number that is set for now, need to be changed into form of input later
        setUpScene();
        setTimeline();
        mySimulationModel.startSimulation();
    }

    public Scene getMyScene(){return myScene;}

    public double getMyFramesPerSecond(){return myFramesPerSecond;}

    public void setMyFramePerSecond(int input){myFramesPerSecond = input;}

    public KeyFrame makeKeyFrame(){
        return new KeyFrame(Duration.millis(1000/myFramesPerSecond), e -> next(1.0/myFramesPerSecond));
    }

    private void next(double elaspedTime) {//need to update model and view for each step
        if (mySimulationModel != null) {
            mySimulationModel.nextStep();
            System.out.println(mySimulationModel.getMyCells());
        }
        myMainView.setMyBoardView(new BoardView(myBoard.getMyWidth(), myBoard.getMyHeight(), mySimulationModel.getMyCells()));

    }

    private void setTimeline(){
        myAnimation = new Timeline();
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(this.makeKeyFrame());
        myAnimation.play();
    }

    private void setUpScene(){
        myMainView = new MainView(myBoardView);
        myScene = myMainView.getScene();
    }

    public void pauseSimulation(){
        myAnimation.pause();
    }

    public void restartSimulation(){
        myAnimation.play();
    }










}
