package app.controller;



/**
 * SimulationController class
 * creates instances for the view and model components of the Application and facilitates communication between the two
 * Packages: app.model.board.Board;
 *  app.model.rules.Rules;
 *  app.model.Simulation;
 *  app.view.*;
 *  app.model.*;
 *  app.view.BoardView;
 *  app.view.ControlView;
 *  app.view.MainView;
 *  javafx.animation.KeyFrame;
 *  javafx.animation.Timeline;
 *  javafx.scene.Scene;
 *  javafx.scene.image.Image;
 *  javafx.scene.paint.Color;
 *  javafx.scene.layout.BorderPane;
 *  javafx.util.Duration;
 *  java.util.*;
 * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
 */


import app.model.board.*;
import app.model.rules.Rules;
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
    private final String ANALYSIS_KPH18 = "analysis_KPH18";
    private final String ANALYSIS_JK386 = "analysis_jk386";
    private final String ANALYSIS_JS576 = "analysis_js576";
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
    private boolean startSimulation;
    private ResourceBundle myProperties;
    private int boardType;
    private Color color0;
    private Color color1;
    private Color color2;
    private List<Image> myImageList;
    private boolean useImage;
    private boolean useGrid;
    private List<String> myPropertiesList;


    /**
     * Constructor for Simulation Controller, creates instances for the view and model components of the Application and facilitates communication between the two
     *
     * @param myProperties Properties file
     */
    public SimulationController( ResourceBundle myProperties) {//Will change to instantiating simulation and simulationView inside controller, not as input
        this.myProperties = myProperties;
        boardType = getBoardType();
        getBoard();
        myRules = new Rules(myProperties);
        myBoardView = new BoardView(myBoard, myProperties, this, false, false);
        mySimulationModel = new Simulation(myBoard, myRules);
        useImage = false;
        myImageList = new ArrayList<>();
        initMyPropList();
        myControlView = new ControlView(this);
        myGraphView = new GraphView(myProperties);
        myRightView = new RightView(this, myBoardView,myGraphView);
        getStateData();
        myFramesPerSecond = 1;//magic number that is set for now, need to be changed into form of input later
        mySimulationModel.setStart();
        setUpScene();
        setTimeline();
    }

    /**
     * Called for every step of the Simulation, indicates changes need to be made to both model and view
     */
    public void next() {//need to update model and view for each step
        startSimulation = myControlView.getMyStartBoolean();

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

    /**
     * Takes an input of a configuration as string and opens that configuration, reset the model and replace the view components that need to be changed
     * @param t1
     */
    public void setConfig(String t1){
        this.pauseSimulation();

        myProperties = ResourceBundle.getBundle(t1);
        boardType = getBoardType();
        getBoard();
        myRules = new Rules (myProperties);
        mySimulationModel = new Simulation(myBoard,myRules);
        myBoardView = new BoardView(myBoard , myProperties, this, false, useImage);
        myMainView.setMyBoardView(myBoardView);
        startSimulation = true;
        setTimeline();
        this.restartSimulation();

    }

    /**
     * Changes the Board whenever it needs to be changed
     */
    public void replaceBoardView(){
        if(useImage){
            myMainView.setMyBoardView(
                    new BoardView(mySimulationModel.getMyBoard(),myProperties,this, useGrid, useImage, myImageList));
        }
        else if(color0 != null) myMainView.setMyBoardView(
                new BoardView(mySimulationModel.getMyBoard(), myProperties, this, useGrid, null, color0, color1, color2));
        else if(useGrid){
            myMainView.setMyBoardView(
                    new BoardView(mySimulationModel.getMyBoard(), myProperties, this, useGrid, useImage));
        }
        else myMainView.setMyBoardView(
                    new BoardView(mySimulationModel.getMyBoard(), myProperties, this, useGrid, useImage));
    }

    /**
     * Creates an instance of PropertiesFileWriter and passes down the input. Then it reads in the properties file that is needed
     * @param propertiesFileName
     * @param name
     * @param type
     * @param des
     * @param csv
     * @param gridShape
     * @param edgePolicy
     * @param neighborPolicy
     * @return
     */
    public String createProperties(String propertiesFileName, String name, String type, String des, String csv, String gridShape, String edgePolicy, String neighborPolicy){
        PropertiesFileWriter temp = new PropertiesFileWriter(propertiesFileName, name,type, des, csv, gridShape, edgePolicy, neighborPolicy);
        myPropertiesList.add(temp.getMyPropFile());
        return temp.getMyPropFile();
    }

    /**
     * Get Cell[][] of the Board
     * @return Cell[][] mySimulationModel
     */
    public Simulation getMySimulationModel () {
        return mySimulationModel;
    }

    /**
     * Sets how fast the animation to change
     * @param input
     */
    public void setMyFramesPerSecond(int input) {
        myFramesPerSecond = input;
        setTimeline();
    }

    /**
     * Get List<String> of Properties
     * @return List<String> myPropertiesList
     */
    public List<String> getMyPropertiesList() {
        return myPropertiesList;
    }

    /**
     * Returns Scene myScene
     * @return Scene myScene
     */
    public Scene getMyScene() {
        return myScene;
    }

    /**
     * Returns int value of myFramesPerSecond
     * @return int myFramesPerSecond
     */
    public int getMyFramesPerSecond() {
        return myFramesPerSecond;
    }

    /**
     * Pauses the animation
     */
    public void pauseSimulation () {
        myAnimation.pause();
    }

    /**
     * Play the simulation
     */
    public void restartSimulation(){
        myAnimation.play();
    }

    /**
     * Sets the value of the colors
     * @param c0 Color value
     * @param c1 Color value
     * @param c2 Color value
     */
    public void changeColor(Color c0, Color c1, Color c2){
        color0 = c0;
        color1 = c1;
        color2 = c2;
    }

    /**
     * Replaces the board by creating a new board
     */
    public void setNewBoard(){
        myBoardView = new BoardView(myBoard,myProperties,this, useGrid, null, color0,color1,color2);
        myRightView  = new RightView(this, myBoardView, new GraphView(myProperties));
        myMainView.setMyBoardView(myBoardView);
    }

    /**
     * Changes the boolean value of useGrid and replaces the board with board with grid/without grid
     */
    public void changeGrid(){
        useGrid = !useGrid;
        myBoardView.changeGridStatus();
        //replaceBoardView();
        myMainView.setMyBoardView(myBoardView);

    }

    /**
     * Changes the board to a board with images
     * @param imageList
     */
    public void setImage(List<Image> imageList){
        useImage = true;
        myImageList = imageList;
        myBoardView = new BoardView(myBoard, myProperties, this, false, useImage, myImageList);
        myBoardView.setMyImageArray(myImageList);
        myMainView.setMyBoardView(myBoardView);

    }

    /**
     * receives data about the board from myBoard and updates the GraphView
     * @return
     */
    public Map<Integer, Double> getStateData(){
        Map<Integer, Double> tempMap = new HashMap<>();
        tempMap = myBoard.getCurrentStateData();
        myGraphView.addToData(tempMap);
        return tempMap;
    }

    private void initMyPropList() {
        myPropertiesList = new ArrayList<>();
        for (String game: gameNames){
            myPropertiesList.add(game + 1);
            myPropertiesList.add(game + 2);
            myPropertiesList.add(game + 3);
        }
        myPropertiesList.add(ANALYSIS_KPH18);
        myPropertiesList.add(ANALYSIS_JK386);
        myPropertiesList.add(ANALYSIS_JS576);
    }

    private KeyFrame makeKeyFrame() {
        return new KeyFrame(Duration.millis(1000 / myFramesPerSecond), e -> next());
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
        if (myGame.equalsIgnoreCase("predatorprey")){
            return PREDATORPREY_BOARD;
        } else if (myGame.equalsIgnoreCase("fire")){
            return FIRE_BOARD;
        } else if (myGame.equalsIgnoreCase("segregation")){
            return SEGREGATION_BOARD;
        } else {
            return -1;
        }
    }

    private void setTimeline () {
        if (myAnimation != null) myAnimation.stop();
        myAnimation = new Timeline();
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(this.makeKeyFrame());
        myAnimation.play();
    }

    private void setUpScene(){
        BorderPane tempRoot = new BorderPane();
        myMainView = new MainView(myBoardView, tempRoot,this, myControlView, myRightView, myGraphView);
        startSimulation = myMainView.getMyStartBoolean();
        myScene = myMainView.getScene();
    }

}
