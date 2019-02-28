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

import java.util.ResourceBundle;

public class SimulationController {

    private Simulation mySimulationModel;
    private SimulationView mySimulationView;

    private Scene myScene;
    private Timeline myAnimation;
    private Board myBoard;
    private Rules myRules;
    private MainView myMainView;
    private BoardView myBoardView;
    private KeyFrame myKeyFrame;

    private int myFramesPerSecond;
    private int appHeight;
    private int appWidth;
    private boolean startSimulation;
    private ResourceBundle myProperties;



    public SimulationController( int height, int width, ResourceBundle properties){//Will change to instantiating simulation and simulationView inside controller, not as input
        myProperties = properties;
        String filename = properties.getString("name_of_csv");
        myBoard = new Board(filename);
        myRules = new Rules(filename.split("Config")[0]);
        myBoardView = new BoardView(myBoard.getMyWidth(),myBoard.getMyHeight(), myBoard.getCells(), myProperties);
        mySimulationModel = new Simulation(myBoard,myRules);
        mySimulationView = new SimulationView(myBoardView);
        appHeight = height;
        appWidth = width;

        myFramesPerSecond = 1;//magic number that is set for now, need to be changed into form of input later
        mySimulationModel.setStart();
        setUpScene();
        setTimeline();

    }

    public ResourceBundle getMyProperties() {
        return myProperties;
    }

    public Scene getMyScene(){return myScene;}

    public int getMyFramesPerSecond(){return myFramesPerSecond;}

    public KeyFrame makeKeyFrame(){
        return new KeyFrame(Duration.millis(1000/myFramesPerSecond), e -> next());
    }

    public void setMyFramesPerSecond(int input){
        myFramesPerSecond = input;
        setTimeline();
    }


    public void next() {//need to update model and view for each step
        startSimulation = myMainView.getMyStartBoolean();
        System.out.println(myFramesPerSecond);
        if (startSimulation) {
            if (mySimulationModel != null) {
                //mySimulationModel.printMyCells();
                mySimulationModel.nextStep();
            }
            myMainView.setMyBoardView(new BoardView(myBoard.getMyWidth(), myBoard.getMyHeight(), mySimulationModel.getMyCells(), myProperties));

        }
    }


    private void setTimeline(){
        if(myAnimation != null) myAnimation.stop();
        myAnimation = new Timeline();
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(this.makeKeyFrame());
        myAnimation.play();
    }

    private void setUpScene(){
        myMainView = new MainView(myBoardView, this);
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
