package app.view;

import app.controller.SimulationController;
import app.model.CSVParser;
import app.model.Simulation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.ResourceBundle;


public class MainView {
    public static final int VIEW_WIDTH = 1000;
    public static final int VIEW_HEIGHT = 600;
    private String[] gameNames = {"Game of Life: ", "Percolation: ", "Rock Paper Scissors: ", "Segregation: ", "Fire: ", "Predator Prey: "};
    private BoardView myBoardView;
    private BorderPane myRoot;
    private Scene myScene;
    private Button myButton1;
    private Button myButton2;
    private Button myButton3;
    private Button myButton4;
    private Button myButton7;
    private Button myButton8;
    private Slider mySlider;
    private Label mySliderLabel;
    private ComboBox myDropDown;
    private ObservableList<String> myConfigOptions;
    private TextField myTextInput;
    private Label myLabel;
    private SimulationController mySimulationController;
    private ResourceBundle myProperties;
    private NewConfigView myNewConfigView;
    private boolean myStartBoolean;

    private ControlView myControlView;




    public MainView(BoardView bv, SimulationController sc) {
        myProperties = ResourceBundle.getBundle("english");
        mySimulationController = sc;
        myStartBoolean = false;
        myBoardView = bv;
        myRoot = new BorderPane();
        myRoot.setTop(this.makeTop());
        myRoot.setBottom(this.makeBottom());
        myRoot.setCenter(this.makeCenter());
        myScene = new Scene(myRoot, VIEW_WIDTH, VIEW_HEIGHT);
    }

    public Scene getScene(){return myScene;}

    public boolean getMyStartBoolean(){return myStartBoolean;}


    private void start(){
        myStartBoolean = true;
        mySimulationController.restartSimulation();

    }

    private Node makeTop(){
        var result = new HBox();
        result.getChildren().add(makeControlPane());
        return result;
    }

    private Node makeBottom(){
        myLabel = new Label("hi");
        myLabel.setText("hello");
        return myLabel;
    }

    private Node makeCenter(){
        var result = new Group();
        result = myBoardView.getMyRoot();
        return result;
    }

    private Button makeButton(String name, EventHandler<ActionEvent> handler){
        var result = new Button();
        var text = name;
        result.setText(text);
        result.setOnAction(handler);
        return result;
    }

    private Node makeControlPane(){
        var result = new HBox();
        myButton2 = makeButton(myProperties.getString("one_step_button"), e->this.oneStep());
        result.getChildren().add(myButton2);
        myButton3 = makeButton(myProperties.getString("pause_button"), e->this.pause());
        result.getChildren().add(myButton3);
        myButton1 = makeButton(myProperties.getString("new_configuration_button"), e->this.createNewConfig());
        result.getChildren().add(myButton1);
        myButton4 = makeButton(myProperties.getString("start_button"), e->this.start());
        result.getChildren().add(myButton4);
//        myButton5 = makeButton(myProperties.getString("speed_up_button"), e-> this.speedUp());
//        result.getChildren().add(myButton5);
//        myButton6 = makeButton(myProperties.getString("speed_down_button"), e-> this.speedDown());
//        result.getChildren().add(myButton6);
//        myButton7 = makeButton(myProperties.getString("load_configuration_button"), e-> this.loadConfig());
//        result.getChildren().add(myButton7);
        myButton8 = makeButton("new config", e->this.newConfig());
        makeDropDown();
        result.getChildren().add(myDropDown);
        SpeedSlider speedSlider = new SpeedSlider(mySimulationController);
        mySlider = speedSlider.getMySlider();
        mySliderLabel = new Label("Simulation FPS:");
        mySliderLabel.setLabelFor(mySlider);
        result.getChildren().add(mySlider);
        result.getChildren().add(myButton8);
        return result;
    }


    private void makeDropDown() {
        ArrayList<String> configList = new ArrayList<String>();
        for (String game: gameNames){
            configList.add(game + 1);
            configList.add(game + 2);
            configList.add(game + 3);
        }
        myConfigOptions = FXCollections.observableArrayList(configList);
        myDropDown = new ComboBox(myConfigOptions);
        myDropDown.setPromptText("Load Configuration");
        myDropDown.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                loadConfig((String) t1);
            }
        });
    }

    //load config so simulation shows selected configuration
    private void loadConfig(String t1) {
       //load config from model
        System.out.println(t1);
    }

    private void newConfig(){
        if(myNewConfigView==null){
            myNewConfigView = new NewConfigView(mySimulationController);
        }
    }

    private void addToDropDown(String config) {
        CSVParser newConfig = new CSVParser(config, 1);
        myConfigOptions.add(config);
    }

    private void createNewConfig(){
        // need to change, currently placeholder to check functionality
        addToDropDown("a new config");
    }

    private void pause(){
        mySimulationController.pauseSimulation();
        //myStartBoolean = false;

    }

    private void oneStep(){
        myStartBoolean = true;
        mySimulationController.next();
        myStartBoolean = false;
    }


    public void setMyBoardView(BoardView bv){
        myBoardView = bv;
        myRoot.setCenter(bv.getMyRoot());
    }

    public BoardView getMyBoardView(){
        return myBoardView;
    }





}
