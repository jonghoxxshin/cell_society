package app.controller;

import app.model.Board;
import app.model.Rules;
import app.model.Simulation;
import app.view.*;
import app.model.*;
import app.view.BoardView;
import app.view.ControlView;
import app.view.MainView;
import app.view.SimulationView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Properties;
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
    private ControlView myControlView;
    private RightView myRightView;
    private int myFramesPerSecond;
    private int appHeight;
    private int appWidth;
    private boolean startSimulation;
    private ResourceBundle myProperties;

    private Color color0;
    private Color color1;
    private Color color2;


    //properties list, need to be initialized by reading in all the properties we have
    private ArrayList<Properties> propList;


    public SimulationController(int height, int width, String game, ResourceBundle myProperties) {//Will change to instantiating simulation and simulationView inside controller, not as input
        this.myProperties = myProperties;
        myBoard = new Board(myProperties);
        myRules = new Rules(game);
        myBoardView = new BoardView(myBoard.getMyWidth(), myBoard.getMyHeight(), myBoard.getCells(), myProperties);
        mySimulationModel = new Simulation(myBoard, myRules);
        mySimulationView = new SimulationView(myBoardView);
        myControlView = new ControlView(this);
        myRightView = new RightView(this, myBoardView);
        appHeight = height;
        appWidth = width;
        propList = new ArrayList<>();
        myFramesPerSecond = 1;//magic number that is set for now, need to be changed into form of input later
        mySimulationModel.setStart();
        setUpScene();
        setTimeline();
    }

    public ResourceBundle getMyProperties() {
        return myProperties;
    }

    public Scene getMyScene() {
        return myScene;
    }

    public int getMyFramesPerSecond() {
        return myFramesPerSecond;
    }

    public KeyFrame makeKeyFrame() {
        return new KeyFrame(Duration.millis(1000 / myFramesPerSecond), e -> next());
    }

    public void setMyFramesPerSecond(int input) {
        myFramesPerSecond = input;
        setTimeline();
    }

    public MainView getMyMainView(){
        return myMainView;
    }


    public void next() {//need to update model and view for each step
        startSimulation = myControlView.getMyStartBoolean();
        System.out.println(myFramesPerSecond);
        if (startSimulation) {
            if (mySimulationModel != null) {
                mySimulationModel.nextStep();
                mySimulationModel.printMyCells();
            }

            if(color0 != null) myMainView.setMyBoardView(
                    new BoardView(myBoard.getMyWidth(), myBoard.getMyHeight(), mySimulationModel.getMyCells(), myProperties, color0, color1, color2));
            else myMainView.setMyBoardView(
                    new BoardView(myBoard.getMyWidth(), myBoard.getMyHeight(), mySimulationModel.getMyCells(), myProperties));

        }
    }

        public void createProperties (String propertiesFileName, String name, String type, String des, String csv){
            PropertiesFileWriter temp = new PropertiesFileWriter(propertiesFileName, name, type, des, csv);

            propList.add(temp.getProp());
        }

        public Simulation getMySimulationModel () {
            return mySimulationModel;
        }

        private void setTimeline () {
            if (myAnimation != null) myAnimation.stop();
            myAnimation = new Timeline();
            myAnimation.setCycleCount(Timeline.INDEFINITE);
            myAnimation.getKeyFrames().add(this.makeKeyFrame());
            myAnimation.play();
        }

        private void setUpScene () {
            myMainView = new MainView(myBoardView, this, myControlView, myRightView);
            startSimulation = myMainView.getMyStartBoolean();
            myScene = myMainView.getScene();
        }

        public void pauseSimulation () {
            myAnimation.pause();
        }

        public void restartSimulation(){
            myAnimation.play();
        }

        public void changeColor(Color c0, Color c1, Color c2){
            color0 = c0;
            color1 = c1;
            color2 = c2;
        }

        public void setNewBoard(){
            myBoardView = new BoardView(myBoard.getMyWidth(),myBoard.getMyWidth(),myBoard.getCells(),myProperties,color0,color1,color2);
            mySimulationView = new SimulationView(myBoardView);
            myRightView  = new RightView(this, myBoardView);
            myMainView.setMyBoardView(myBoardView);
        }



    }
