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
    private RightView myRightView;
    private Scene myScene;
    private Label myLabel;
    private SimulationController mySimulationController;
    private ResourceBundle myProperties;
    private boolean myStartBoolean;

    public MainView(BoardView bv,BorderPane root, SimulationController sc, ControlView cv, RightView rv) {
        myProperties = ResourceBundle.getBundle("english");
        mySimulationController = sc;
        myStartBoolean = false;
        myBoardView = bv;
        myRightView = rv;
        myControlView = cv;
        this.myRoot = root;

        myRoot.setTop(this.makeTop());
        myRoot.setCenter(this.makeCenter());
        myRoot.setRight(this.makeRight());
        myScene = new Scene(myRoot, VIEW_WIDTH, VIEW_HEIGHT);
        myScene.getStylesheets().add(getClass().getResource("/simulationStyle.css").toExternalForm());
    }

    public void modifyMainView(BorderPane root, BoardView bv, SimulationController sc, ControlView cv) {
        myProperties = ResourceBundle.getBundle("english");
        mySimulationController = sc;
        myStartBoolean = false;
        myBoardView = bv;
        myControlView = cv;
    }

    public Scene getScene(){
        return myScene;
    }

    public boolean getMyStartBoolean(){return myStartBoolean;}

    private Node makeTop(){
        return myControlView.getMyRoot();
    }

    private Node makeRight(){
        return myRightView.getMyRoot();
    }

    private Node makeCenter(){
        var result = new Group();
        result = myBoardView.getMyRoot();
        return result;
    }

    public void  setMyBoardView(BoardView bv){
        myBoardView = bv;
        myRoot.setCenter(bv.getMyRoot());
    }

    public BoardView getMyBoardView(){
        return myBoardView;
    }

}
