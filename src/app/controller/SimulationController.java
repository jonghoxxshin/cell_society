package app.controller;

import app.model.Board;
import app.model.Rules;
import app.model.Simulation;
import app.view.*;
import app.model.*;
import app.view.BoardView;
import app.view.ControlView;
import app.view.MainView;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.util.*;

public class SimulationController {

    private Simulation mySimulationModel;
    private final String[] gameNames = {"gameOfLife", "percolation", "rockPaperScissors", "fire", "segregation", "predatorPrey"};
    private final int PREDATORPREY_BOARD = 2;
    private final int SEGREGATION_BOARD = 3;
    private final int FIRE_BOARD = 4;
    private Scene myScene;
    private Timeline myAnimation;
    private Board myBoard;
    private Rules myRules;
    private MainView myMainView;
    private BoardView myBoardView;
    private ControlView myControlView;
    private RightView myRightView;
    private GraphView myGraphView;

    private int myFramesPerSecond;
    private int appHeight;
    private int appWidth;
    private boolean startSimulation;
    private ResourceBundle myProperties;
    private int boardType;

    private Color color0;
    private Color color1;
    private Color color2;

    private ArrayList<Image> myImageList;
    private boolean useImage;
    private boolean useGrid;


    //properties list, need to be initialized by reading in all the properties we have


    private ArrayList<String> myPropertiesList;
    private Boolean onSwitch = false;
    private BorderPane myRoot;


    public SimulationController(int height, int width, String game, ResourceBundle myProperties) {//Will change to instantiating simulation and simulationView inside controller, not as input
        this.myProperties = myProperties;
        boardType = getBoardType();
        getBoard();
        myRules = new Rules(myProperties);
        myBoardView = new BoardView(myBoard.getMyWidth(), myBoard.getMyHeight(), myBoard.getCells(), myProperties, this, false, false);
        mySimulationModel = new Simulation(myBoard, myRules);
        useImage = false;
        myImageList = new ArrayList<>();
        initMyPropList();
        myControlView = new ControlView(this);
        myGraphView = new GraphView(this, myProperties);
        myRightView = new RightView(this, myBoardView,myGraphView);
        getStateData();
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

    public ArrayList<String> getMyPropertiesList() {
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

    private void getBoard(){
        if (boardType == PREDATORPREY_BOARD) {
            myBoard = new PredatorPreyBoard(myProperties);
        } else if (boardType == SEGREGATION_BOARD) {
            myBoard = new SegregationBoard(myProperties);
        } else if (boardType == FIRE_BOARD) {
            myBoard = new FireBoard(myProperties);
        } else {
            myBoard = new GenericBoard(myProperties);
        }
    }

    private int getBoardType(){
        String myGame = myProperties.getString("type_of_game");
        if (myGame.toLowerCase().equals("predatorprey")){
            return PREDATORPREY_BOARD;
        } else if (myGame.toLowerCase().equals("fire")){
            return FIRE_BOARD;
        } else if (myGame.toLowerCase().equals("segregation")){
            return SEGREGATION_BOARD;
        } else {
            return -1;
        }
    }

    public void setConfig(String t1){
        this.pauseSimulation();

        myProperties = ResourceBundle.getBundle(t1);
        boardType = getBoardType();
        getBoard();
        myRules = new Rules (myProperties);
        mySimulationModel = new Simulation(myBoard,myRules);
        myBoardView = new BoardView(myBoard.getMyWidth(), myBoard.getMyHeight(), myBoard.getCells(), myProperties, this, false, useImage);
        myMainView.setMyBoardView(myBoardView);
        startSimulation = true;
        setTimeline();
        this.restartSimulation();

    }

    public void next() {//need to update model and view for each step
        startSimulation = myControlView.getMyStartBoolean();
        System.out.println(myFramesPerSecond);
        if (startSimulation) {
            getStateData();
            if (mySimulationModel != null) {
                mySimulationModel.setStart();
                mySimulationModel.nextStep();

                //mySimulationModel.printMyCells();
            }
            replaceBoardView();

        }
    }

    public void replaceBoardView(){
        System.out.println("useImage boolean value is" + useImage);
        if(useImage){
            System.out.println("came in here");
            myMainView.setMyBoardView(
                    new BoardView(myBoard.getMyWidth(),myBoard.getMyHeight(),mySimulationModel.getMyCells(),myProperties,this, useGrid, useImage, myImageList));
        }
        if(color0 != null) myMainView.setMyBoardView(
                new BoardView(myBoard.getMyWidth(), myBoard.getMyHeight(), mySimulationModel.getMyCells(), myProperties, this, useGrid, useImage, color0, color1, color2));
        else if(useGrid){
            myMainView.setMyBoardView(
                    new BoardView(myBoard.getMyWidth(), myBoard.getMyHeight(), mySimulationModel.getMyCells(), myProperties, this, useGrid, useImage));
        }
        else myMainView.setMyBoardView(
                new BoardView(myBoard.getMyWidth(), myBoard.getMyHeight(), mySimulationModel.getMyCells(), myProperties, this, useGrid, useImage));
    }


    public String createProperties(String propertiesFileName, String name, String type, String des, String csv, String gridShape, String edgePolicy, String neighborPolicy){
        PropertiesFileWriter temp = new PropertiesFileWriter(propertiesFileName, name,type, des, csv, gridShape, edgePolicy, neighborPolicy);
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
        myMainView = new MainView(myBoardView, myRoot,this, myControlView, myRightView, myGraphView);
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

        myBoardView = new BoardView(myBoard.getMyWidth(),myBoard.getMyHeight(),myBoard.getCells(),myProperties,this, useImage, useGrid, color0,color1,color2);
        myRightView  = new RightView(this, myBoardView, new GraphView(this, myProperties));
        myMainView.setMyBoardView(myBoardView);
    }

    public void changeGrid(){
        if(useGrid){
            useGrid = false;
        }else useGrid = true;
        myBoardView.changeGridStatus();
        //replaceBoardView();
        myMainView.setMyBoardView(myBoardView);

    }

    public void setImage(ArrayList imageList){
        useImage = true;
        myImageList = imageList;
        myBoardView = new BoardView(myBoard.getMyWidth(),myBoard.getMyHeight(),myBoard.getCells(),myProperties, this, false, useImage, myImageList);
        myBoardView.setMyImageArray(myImageList);
        myMainView.setMyBoardView(myBoardView);

    }

    public Map<Integer, Double> getStateData(){
        Map<Integer, Double> tempMap = new HashMap<>();
        tempMap = myBoard.getCurrentStateData();
        myGraphView.addToData(tempMap);
        return tempMap;
    }


}
