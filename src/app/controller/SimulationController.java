package app.controller;

import app.model.*;
import app.view.BoardView;
import app.view.ControlView;
import app.view.MainView;
import app.view.SimulationView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;

public class SimulationController {

    private Simulation mySimulationModel;
    private SimulationView mySimulationView;
    private final String[] gameNames = {"gameOfLife", "percolation"};
    private Scene myScene;
    private Timeline myAnimation;
    private Board myBoard;
    private Rules myRules;
    private MainView myMainView;
    private BoardView myBoardView;
    private ControlView myControlView;
    private int myFramesPerSecond;
    private int appHeight;
    private int appWidth;
    private boolean startSimulation;
    private ResourceBundle myProperties;
    private ArrayList<String> myPropertiesList;



    public SimulationController(int height, int width, String game, ResourceBundle myProperties){//Will change to instantiating simulation and simulationView inside controller, not as input
        this.myProperties = myProperties;
        myBoard = new Board(myProperties);
        myRules = new Rules(game);
        myBoardView = new BoardView(myBoard.getMyWidth(),myBoard.getMyHeight(), myBoard.getCells(), myProperties);
        mySimulationModel = new Simulation(myBoard,myRules);
        mySimulationView = new SimulationView(myBoardView);
        initMyPropList();
        myControlView = new ControlView(this);
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
        myPropertiesList.add("rockPaperScissors1");
    }

    public ArrayList<String> getMyPropertiesList() {
        return myPropertiesList;
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


        public void next() {
            startSimulation = myControlView.getMyStartBoolean();
            System.out.println(myFramesPerSecond);
            if (startSimulation) {
                if (mySimulationModel != null) {
                    mySimulationModel.nextStep();
                    mySimulationModel.printMyCells();
                }
                myMainView.setMyBoardView(new BoardView(myBoard.getMyWidth(), myBoard.getMyHeight(), mySimulationModel.getMyCells(), myProperties));

            }
        }

        public String createProperties(String propertiesFileName, String name, String type, String des, String csv){
            PropertiesFileWriter temp = new PropertiesFileWriter(propertiesFileName, name,type, des, csv);
            myPropertiesList.add(temp.getMyPropFile());
            return temp.getMyPropFile();
        }

        private void setTimeline(){
            if(myAnimation != null) myAnimation.stop();
            myAnimation = new Timeline();
            myAnimation.setCycleCount(Timeline.INDEFINITE);
            myAnimation.getKeyFrames().add(this.makeKeyFrame());
            myAnimation.play();
        }

        private void setUpScene(){
            myMainView = new MainView(myBoardView, this, myControlView);
            startSimulation = myMainView.getMyStartBoolean();
            myScene = myMainView.getScene();
        }

        public void pauseSimulation(){
            myAnimation.pause();
        }

        public void restartSimulation(){
            myAnimation.play();
        }

        public void restartSimulationWithNewConfig(String props) {
            System.out.println("gets to load point");
            SimulationController newSim = new SimulationController(appHeight, appWidth, props.replaceAll("\\d",""), ResourceBundle.getBundle(props));
            this.mySimulationModel = newSim.mySimulationModel;
            this.mySimulationView = newSim.mySimulationView;
            this.myScene = newSim.myScene;
            this.myAnimation = newSim.myAnimation;
            this.myBoard = newSim.myBoard;
            this.myRules = newSim.myRules;
            this.myMainView = newSim.myMainView ;
            this.myMainView.setMyBoardView(newSim.myBoardView);
            this.myBoardView = newSim.myBoardView;
            this.myControlView = newSim.myControlView;
            this.myFramesPerSecond = newSim.myFramesPerSecond;
            this.startSimulation = newSim.startSimulation;
            this.myProperties = newSim.myProperties;
            this.mySimulationModel.setStart();
            setUpScene();
            setTimeline();
            this.myRules.getMyRulesParser().printRulesArray();
            myAnimation.play();
            next();
        }










    }
