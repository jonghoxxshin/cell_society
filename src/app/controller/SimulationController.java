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
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SimulationController {

    private Simulation mySimulationModel;
    private SimulationView mySimulationView;
    private final String[] gameNames = {"gameOfLife", "percolation", "rockPaperScissors", "segregation", "predatorPrey"};
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

    private ArrayList<Image> myImageList;
    private boolean useImage;


    //properties list, need to be initialized by reading in all the properties we have


    private ArrayList<String> myPropertiesList;
    private Boolean onSwitch = false;
    private BorderPane myRoot;


    public SimulationController(int height, int width, String game, ResourceBundle myProperties) {//Will change to instantiating simulation and simulationView inside controller, not as input
        this.myProperties = myProperties;
        myBoard = new Board(myProperties);
        myRules = new Rules(myProperties);
        myBoardView = new BoardView(myBoard.getMyWidth(), myBoard.getMyHeight(), myBoard.getCells(), myProperties, this);
        mySimulationModel = new Simulation(myBoard, myRules);
        mySimulationView = new SimulationView(myBoardView);
        useImage = false;
        myImageList = new ArrayList<>();
        initMyPropList();
        myControlView = new ControlView(this);
        myRightView = new RightView(this, myBoardView);
        appHeight = height;
        appWidth = width;
        myFramesPerSecond = 1;//magic number that is set for now, need to be changed into form of input later
        mySimulationModel.setStart();
        setUpScene();
        setTimeline();
    }


    private void initMyPropList() {
        myPropertiesList = new ArrayList<String>();
        for (String game: gameNames){
            myPropertiesList.add(game + 1);
            myPropertiesList.add(game + 2);
            myPropertiesList.add(game + 3);
        }
    }

    public List<String> getMyPropertiesList() {
        return myPropertiesList;
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

    public void setConfig(String t1){
        this.pauseSimulation();
        myProperties = ResourceBundle.getBundle(t1);
        myBoard = new Board (myProperties);
        myRules = new Rules (myProperties);
        mySimulationModel = new Simulation(myBoard,myRules);
        myBoardView = new BoardView(myBoard.getMyWidth(), myBoard.getMyHeight(), myBoard.getCells(), myProperties, this);
        myMainView.setMyBoardView(myBoardView);
        startSimulation = true;
        setTimeline();
        this.restartSimulation();

    }

    public void next() {//need to update model and view for each step
        startSimulation = myControlView.getMyStartBoolean();
        System.out.println(myFramesPerSecond);
        if (startSimulation) {
            if (mySimulationModel != null) {
                System.out.println("came in here after loading");
                mySimulationModel.setStart();
                mySimulationModel.nextStep();
                //mySimulationModel.printMyCells();
            }
            replaceBoardView();
        }
    }

    public void replaceBoardView(){
        if(useImage){

        }
        if(color0 != null) myMainView.setMyBoardView(
                new BoardView(myBoard.getMyWidth(), myBoard.getMyHeight(), mySimulationModel.getMyCells(), myProperties, this, color0, color1, color2));
        else myMainView.setMyBoardView(
                new BoardView(myBoard.getMyWidth(), myBoard.getMyHeight(), mySimulationModel.getMyCells(), myProperties, this));
    }


    public String createProperties(String propertiesFileName, String name, String type, String des, String csv){
        PropertiesFileWriter temp = new PropertiesFileWriter(propertiesFileName, name,type, des, csv);
        myPropertiesList.add(temp.getMyPropFile());
        return temp.getMyPropFile();
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


    private void setUpScene(){
        this.myRoot = new BorderPane();
        myMainView = new MainView(myBoardView, myRoot,this, myControlView, myRightView);
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
        System.out.println("set new board is called ");
        myBoardView = new BoardView(myBoard.getMyWidth(),myBoard.getMyHeight(),myBoard.getCells(),myProperties,this,color0,color1,color2);
        mySimulationView = new SimulationView(myBoardView);
        myRightView  = new RightView(this, myBoardView);
        myMainView.setMyBoardView(myBoardView);
    }


}
