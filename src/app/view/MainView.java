package app.view;

/*
Authors: Jaiveer Katariya, Jongho Shin, Kyle Harvey

This class is used to generate the main view of the entire simulation using the other classes in the app.view package.
It assumes that the user possesses the following dependencies/packages:
app.controller.SimulationController;
javafx.scene.Group;
javafx.scene.Node;
javafx.scene.Scene;
javafx.scene.layout.BorderPane;
javafx.scene.control.Label;
java.util.ResourceBundle;

This class is used in the SimluationController to generate the main view of the entire app, and is also recreated upon
different changes made to the simluation. To use it, one would simply need to declare it with the parameters specified
by the constructor.

 */

import app.controller.SimulationController;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import java.util.ResourceBundle;


public class MainView {
    private static final int VIEW_WIDTH = 1000;
    private static final int VIEW_HEIGHT = 600;

    private BoardView myBoardView;
    private BorderPane myRoot;
    private ControlView myControlView;
    private RightView myRightView;
    private GraphView myGraphView;
    private Scene myScene;
    private Label myLabel;
    private SimulationController mySimulationController;
    private ResourceBundle myProperties;
    private boolean myStartBoolean;


    /**
     * Constructor to generate a MainView object from a specified BorderPane root, a BoardView object, a
     * SimulationController, a ControlView object, and a RightView object.
     *
     * @param bv Boardview to be used in MainView
     * @param root BorderPane that is used to hold and assign locations to different components of MainView
     * @param sc the active SimulationController object associated with Mainview
     * @param cv ControlView to be used in MainView
     * @param rv RightView to be used in MainView
     * @param gv GraphView to be used in MainView
     */
    public MainView(BoardView bv,BorderPane root, SimulationController sc, ControlView cv, RightView rv, GraphView gv) {
        myProperties = ResourceBundle.getBundle("english");
        mySimulationController = sc;
        myStartBoolean = false;
        myBoardView = bv;
        myRightView = rv;
        myControlView = cv;
        myGraphView = gv;
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

    /**
     * Method to return scene object of main view
     *
     * @return scene object of main view
     */

    public Scene getScene(){
        return myScene;
    }

    /**
     * Method to return boolean that indicates whether or not simulation has been started/is running
     *
     * @return a boolean that indicates whether or not simulation has been started/is running
     */
    public boolean getMyStartBoolean(){return myStartBoolean;}

    // Method to get ControlView object to be used as top of BorderPane root object
    private Node makeTop(){
        return myControlView.getMyRoot();
    }

    // Method to get RightView object to be used as right part of BorderPane root object
    private Node makeRight(){
        return myRightView.getMyRoot();
    }

    // Method to get BoardView object to be used as center of BorderPane root object
    private Node makeCenter(){
        var result = new Group();
        result = myBoardView.getMyRoot();
        return result;
    }


    /**
     * Method to set new BoardView object as MainView's board, which, for visual purposes, regenerates the board
     * to a different one
     *
     * @param bv new BoardView object to render in MainView
     */
    public void  setMyBoardView(BoardView bv){
        myBoardView = bv;
        myRoot.setCenter(bv.getMyRoot());
    }

}
