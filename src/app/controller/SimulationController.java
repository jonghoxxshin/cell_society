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

    private Scene myScene;

    private Timeline myAnimation;
    private Board myBoard;
    private Rules myRules;
    private MainView myMainView;
    private BoardView myBoardView;

    private int myFramesPerSecond;
    private int appHeight;
    private int appWidth;
    private boolean startSimulation;

    public SimulationController( int height, int width, String game){//Will change to instantiating simulation and simulationView inside controller, not as input
        myBoard = new Board(game);
        myRules = new Rules(game);
        myBoardView = new BoardView(myBoard.getMyWidth(),myBoard.getMyHeight(),myBoard.getCells());
        mySimulationModel = new Simulation(myBoard,myRules);
        mySimulationView = new SimulationView(myBoardView);
        appHeight = height;
        appWidth = width;

        myFramesPerSecond = 1;//magic number that is set for now, need to be changed into form of input later
        mySimulationModel.setStart();
        setUpScene();
        setTimeline();

    }

    public Scene getMyScene(){return myScene;}

    public double getMyFramesPerSecond(){return myFramesPerSecond;}

    public void setMyFramePerSecond(int input){myFramesPerSecond = input;}

    public KeyFrame makeKeyFrame(){
        return new KeyFrame(Duration.millis(1000/myFramesPerSecond), e -> next(1.0/myFramesPerSecond));
    }

    private void next(double elaspedTime) {//need to update model and view for each step
        startSimulation = myMainView.getMyStartBoolean();
        System.out.println(startSimulation);
        if(startSimulation) {
            System.out.println("came in");
            if (mySimulationModel != null) {
                System.out.println("next step");
                mySimulationModel.nextStep();
            }
            myMainView.setMyBoardView(new BoardView(myBoard.getMyWidth(), myBoard.getMyHeight(), mySimulationModel.getMyCells()));
        }

    }


    private void setTimeline(){
        myAnimation = new Timeline();
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(this.makeKeyFrame());
        myAnimation.play();
    }

    private void setUpScene(){
        myMainView = new MainView(myBoardView);
        startSimulation = myMainView.getMyStartBoolean();
        myScene = myMainView.getScene();
    }

    public void pauseSimulation(){
        myAnimation.pause();
    }

    public void restartSimulation(){
        myAnimation.play();
    }










}
