package app.view;

/*
Authors: Jaiveer Katariya, Jongho Shin, Kyle Harvey

This class is used to generate the view that appears when users wish to generate a new properties file and corresponding
simulation. It assumes that the user possesses the following dependencies/packages:
app.model.GridShapeType;
app.controller.SimulationController;
javafx.collections.FXCollections;
javafx.collections.ObservableList;
javafx.geometry.Pos;
javafx.scene.Scene;
javafx.scene.control.ComboBox;
javafx.scene.control.TextField;
javafx.scene.layout.HBox;
javafx.scene.layout.VBox;
javafx.scene.text.Text;
javafx.stage.Stage;
javafx.scene.control.Button;

This class is used by the ControlView object to generate this new view once the "New Configuration" button has been pressed
by the user. To use it, one would simply need to declare it with the parameters specified by the constructor.

 */

import app.controller.SimulationController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.ResourceBundle;

public class NewConfigView {
    public static final int PAGE_WIDTH = 400;
    public static final int PAGE_HEIGHT = 500;
    public static final String TITLE = "New Configuration";

    //this part will be replaced by observable model
    //temporary place holder
    private final String[] CSVList = {"1", "2", "3"};
    private final String[] ShapeList = {"Rectangle", "Hexagon", "Rhombus"};
    private final String[] EdgePolicyList = {"Torodial", "Finite", "Flipped"};
    private final String[] NeighborTypeList = {"Complete", "Cardinal", "All but left"};
    private final String[] GameList = {"Game of Life", "Percolation", "Predator and Prey", "Rock Paper Scissors", "Segregation"};
    private ObservableList<String> myCSVOptions;
    private ObservableList<String> myShapeOptions;
    private ObservableList<String> myGameOptions;
    private ObservableList<String> myEdgeOptions;
    private ObservableList<String> myNeighborOptions;
    private Button mySubmitButton;
    private Scene myScene;
    private VBox myRoot;
    private Stage myStage;
    private ComboBox myDropDown;
    private ComboBox myShapes;
    private ComboBox myGames;
    private ComboBox myEdgePolicies;
    private ComboBox myNeighborPolicies;
    private SimulationController mySimulationController;
    private ResourceBundle myProperties;


    private TextField nameField;
    private TextField nameProperties;
    private TextField typeField;
    private TextField des;


    /**
     * Constructor to generate new NewConfigView from active SimulationController
     *
     * @param sc SimulationController object that is used by app to control the simulation
     */
    public NewConfigView(SimulationController sc){
        myProperties = ResourceBundle.getBundle("english");
        mySimulationController = sc;
        myStage = new Stage();
        setScene();
        myStage.setScene(myScene);
        myStage.setTitle(TITLE);
        myStage.show();
    }


    private void setScene(){
        myRoot = new VBox();
        setComponent();
        setGames();
        setShapes();
        setEdgePolicies();
        setNeighborPolicies();
        setDropDown();
        setBottom();
        myRoot.setAlignment(Pos.CENTER);
        myScene = new Scene(myRoot,PAGE_WIDTH, PAGE_HEIGHT);
        myScene.getStylesheets().add(getClass().getResource("/simulationStyle.css").toExternalForm());
    }

    private void setGames(){
        ArrayList<String> gameList = new ArrayList<>();
        for(String game : GameList){
            gameList.add(game);
        }
        myGameOptions = FXCollections.observableArrayList(gameList);
        myGames = new ComboBox(myGameOptions);
        myGames.setPromptText(myProperties.getString("choose_a_game_prompt"));
        myRoot.getChildren().add(myGames);
    }

    private void setShapes(){
        ArrayList<String> shapeList = new ArrayList<>();
        for(String shape : ShapeList){
            shapeList.add(shape);
        }
        myShapeOptions = FXCollections.observableArrayList(shapeList);
        myShapes = new ComboBox(myShapeOptions);
        myShapes.setPromptText(myProperties.getString("choose_a_grid_shape_prompt"));
        myRoot.getChildren().add(myShapes);
    }

    private void setEdgePolicies() {
        ArrayList<String> edgePolicyList = new ArrayList<>();
        for(String edgePolicy : EdgePolicyList){
            edgePolicyList.add(edgePolicy);
        }
        myEdgeOptions = FXCollections.observableArrayList(edgePolicyList);
        myEdgePolicies = new ComboBox(myEdgeOptions);
        myEdgePolicies.setPromptText(myProperties.getString("choose_an_edge_policy_prompt"));
        myRoot.getChildren().add(myEdgePolicies);
    }

    private void setNeighborPolicies() {
        ArrayList<String> neighborPolicyList = new ArrayList<>();
        for(String neighborType : NeighborTypeList){
            neighborPolicyList.add(neighborType);
        }
        myNeighborOptions = FXCollections.observableArrayList(neighborPolicyList);
        myNeighborPolicies = new ComboBox(myNeighborOptions);
        myNeighborPolicies.setPromptText(myProperties.getString("choose_a_neighbor_policy_prompt"));
        myRoot.getChildren().add(myNeighborPolicies);
    }

    //this part need to be refactored, soon enough
    private void setNameComponent(String fieldLabel){
        HBox temp = new HBox();
        Text tempText = new Text(fieldLabel);
        nameField = new TextField();
        temp.getChildren().add(tempText);
        temp.getChildren().add(nameField);
        temp.setAlignment(Pos.CENTER);
        myRoot.getChildren().add(temp);
    }

    private void setTypeComponent(String fieldLabel){
        HBox temp = new HBox();
        Text tempText = new Text(fieldLabel);
        typeField = new TextField();
        temp.getChildren().add(tempText);
        temp.getChildren().add(typeField);
        temp.setAlignment(Pos.CENTER);
        myRoot.getChildren().add(temp);
    }

    private void setDesComponent(String fieldLabel){
        HBox temp = new HBox();
        Text tempText = new Text(fieldLabel);
        des = new TextField();
        temp.getChildren().add(tempText);
        temp.getChildren().add(des);
        temp.setAlignment(Pos.CENTER);
        myRoot.getChildren().add(temp);
    }

    private void setPropNameComponent(String fieldLabel){
        HBox temp = new HBox();
        Text tempText = new Text(fieldLabel);
        nameProperties = new TextField();
        temp.getChildren().add(tempText);
        temp.getChildren().add(nameProperties);
        temp.setAlignment(Pos.CENTER);
        myRoot.getChildren().add(temp);
    }


    private void setDropDown(){
        ArrayList<String> configList = new ArrayList<>();
        for(String csv : CSVList){
            configList.add(csv);
        }
        myCSVOptions = FXCollections.observableArrayList(configList);
        myDropDown = new ComboBox(myCSVOptions);
        myDropDown.setPromptText(myProperties.getString("choose_a_csv_config_prompt"));
        myRoot.getChildren().add(myDropDown);
    }

    private void setBottom(){
        HBox temp = new HBox();
        mySubmitButton = new Button(myProperties.getString("submit"));
        mySubmitButton.setOnAction(e -> this.submit(mySimulationController));
        temp.getChildren().add(mySubmitButton);
        temp.setAlignment(Pos.CENTER);
        myRoot.getChildren().add(temp);
    }

    private void submit(SimulationController sc){//check console to see if it worked!
        String temp = sc.createProperties(nameProperties.getCharacters().toString(), nameField.getCharacters().toString(), myGames.getValue().toString(), des.getCharacters().toString(), myDropDown.getValue().toString(),
                myShapes.getValue().toString(), myEdgePolicies.getValue().toString(), myNeighborPolicies.getValue().toString());
        sc.setConfig(temp);
        myStage.close();
    }

    private void setComponent(){
        setNameComponent(myProperties.getString("creator_name_prompt"));
        setPropNameComponent(myProperties.getString("prop_filename_prompt"));
        setTypeComponent(myProperties.getString("simulation_name_prompt"));
        setDesComponent(myProperties.getString("description_prompt"));
    }





}
