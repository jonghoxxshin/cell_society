package app.view;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
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


    private Button mySubmitButton;
    private TextField myGameType;
    private TextField myCreatorName;
    private TextField myDescription;
    private TextField myCSV;
    private Scene myScene;
    private VBox myRoot;
    private Stage myStage;

    public NewConfigView(){
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

    private void setHorizontalComponent(String fieldLabel){
        HBox temp = new HBox();
        Text tempText = new Text(fieldLabel);
        TextField tempField = new TextField();
        temp.getChildren().add(tempText);
        temp.getChildren().add(tempField);
        temp.setAlignment(Pos.CENTER);
        myRoot.getChildren().add(temp);
    }

    private void setDropDown(){


    }
    private void setBottom(){
        HBox temp = new HBox();
        Button tempButton = new Button("submit");
        temp.getChildren().add(tempButton);
        temp.setAlignment(Pos.CENTER);
        myRoot.getChildren().add(temp);
    }

    private void setComponent(){
        setHorizontalComponent("Creator's name");
        setHorizontalComponent("Simulation name");
        setHorizontalComponent("Description");
    }





}
