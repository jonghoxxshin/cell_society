package app.view;

import app.controller.SimulationController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.util.ArrayList;

public class NewConfigView {
    public static final int PAGE_WIDTH = 400;
    public static final int PAGE_HEIGHT = 500;
    public static final String TITLE = "New Configuration";

    //this part will be replaced by observable model
    //temporary place holder
    private String[] CSVList = {"GameOfLifeConfig1.csv", "GameOfLifeConfig2.csv", "GameOfLifeConfig3.csv"};
    private ObservableList<String> myCSVOptions;
    private Button mySubmitButton;
    private Scene myScene;
    private VBox myRoot;
    private Stage myStage;
    private ComboBox myDropDown;
    private SimulationController mySimulationController;


    private TextField nameField;
    private TextField nameProperties;
    private TextField typeField;
    private TextField des;



    public NewConfigView(SimulationController sc){
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
        setDropDown();
        setBottom();
        myRoot.setAlignment(Pos.CENTER);
        myScene = new Scene(myRoot,PAGE_WIDTH, PAGE_HEIGHT);
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
        myDropDown.setPromptText("Choose CSV configuration");
        myRoot.getChildren().add(myDropDown);
    }

    private void setBottom(){
        HBox temp = new HBox();
        mySubmitButton = new Button("submit");
        mySubmitButton.setOnAction(e -> this.submit(mySimulationController));
        temp.getChildren().add(mySubmitButton);
        temp.setAlignment(Pos.CENTER);
        myRoot.getChildren().add(temp);
    }

    private void submit(SimulationController sc){//check console to see if it worked!
        sc.createProperties(nameField.getCharacters().toString(),nameProperties.getCharacters().toString(),typeField.getCharacters().toString(), des.getCharacters().toString(), myDropDown.getValue().toString());
        myStage.close();
    }

    private void setComponent(){
        setNameComponent("Creator's name");
        setPropNameComponent("Name of the property file");
        setTypeComponent("Simulation name");
        setDesComponent("Description");
    }





}
