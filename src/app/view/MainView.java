package app.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;


public class MainView {
    public static final int VIEW_WIDTH = 1000;
    public static final int VIEW_HEIGHT = 600;
    private BoardView myBoardView;
    private BorderPane myRoot;
    private Scene myScene;
    private Button myButton1;
    private Button myButton2;
    private Button myButton3;
    private TextField myTextInput;
    private Label myLabel;
    private ComboBox<String> myCombobox;
    private Button mySubmitButton;



    public MainView(BoardView bv) {
        myBoardView = bv;
        myRoot = new BorderPane();
        myRoot.setTop(this.makeTop());
        myRoot.setBottom(this.makeBottom());
        myRoot.setCenter(this.makeCenter());

        myScene = new Scene(myRoot, VIEW_WIDTH, VIEW_HEIGHT);


    }

    public Scene getScene(){return myScene;}


    private void back(){}

    private Node makeTop(){
        var result = new HBox();
        result.getChildren().add(makeNavigationPane());
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

    private TextField makeTextInput(int width, EventHandler<ActionEvent> handler){
        var result = new TextField();
        result.setPrefColumnCount(width);
        result.setOnAction(handler);
        return result;
    }



    private Node makeNavigationPane(){
        var result = new HBox();
        myButton2 = makeButton("next", e->this.back());
        result.getChildren().add(myButton2);
        myButton3 = makeButton("before", e->this.back());
        result.getChildren().add(myButton3);
        myTextInput = makeTextInput(40, e->this.back());
        result.getChildren().add(myTextInput);
        myButton1 = makeButton("submit", e->this.back());
        result.getChildren().add(myButton1);
        return result;
    }


    public void setMyBoardView(BoardView bv){
        myBoardView = bv;
        myRoot.setCenter(bv.getMyRoot());
    }

    public BoardView getMyBoardView(){
        return myBoardView;
    }








}
