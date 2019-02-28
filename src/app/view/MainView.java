package app.view;

import app.controller.SimulationController;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import java.util.ResourceBundle;


public class MainView {
    public static final int VIEW_WIDTH = 1000;
    public static final int VIEW_HEIGHT = 600;

    private BoardView myBoardView;
    private BorderPane myRoot;
    private ControlView myControlView;
    private Scene myScene;
    private Label myLabel;
    private SimulationController mySimulationController;
    private ResourceBundle myProperties;
    private boolean myStartBoolean;

    public MainView(BoardView bv, SimulationController sc, ControlView cv) {
        myProperties = ResourceBundle.getBundle("english");
        mySimulationController = sc;
        myStartBoolean = false;
        myBoardView = bv;
        myControlView = cv;
        myRoot = new BorderPane();
        myRoot.setTop(this.makeTop());
        myRoot.setBottom(this.makeBottom());
        myRoot.setCenter(this.makeCenter());
        myScene = new Scene(myRoot, VIEW_WIDTH, VIEW_HEIGHT);
    }

    public Scene getScene(){return myScene;}

    public boolean getMyStartBoolean(){return myStartBoolean;}

    private Node makeTop(){
        return myControlView.getMyRoot();
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

    public void setMyBoardView(BoardView bv){
        myBoardView = bv;
        myRoot.setCenter(bv.getMyRoot());
    }

    public BoardView getMyBoardView(){
        return myBoardView;
    }

}
