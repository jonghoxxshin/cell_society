package app.view;

/*
Authors: Jaiveer Katariya, Jongho Shin, Kyle Harvey

This class is used to generate the visual components that allow the user to configure their simulation with the colors
or images they wish to use to represent specific states. It assumes tha the FileChooser configured with JavaFX for Mac
will also work for Windows Computers; this is not something that we were able to test. The class's implementation also
assumes that the user possesses the following
dependencies/packages:
app.controller.SimulationController;
app.model.State;
javafx.geometry.Pos;
javafx.scene.Node;
javafx.scene.control.Button;
javafx.scene.control.ColorPicker;
javafx.scene.control.Label;
javafx.scene.image.Image;
javafx.scene.layout.HBox;
javafx.scene.layout.VBox;
javafx.scene.paint.Color;
javafx.stage.FileChooser;
java.io.File;
java.util.ArrayList;
java.util.ResourceBundle;


This class is used in the SimluationController and the MainView classes, and to use it, one would simply need to declare
it with the parameters specified by the constructor.

 */

import app.controller.SimulationController;
import app.model.State;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RightView {
    private VBox myRoot;
    private SimulationController mySimulationController;
    private ResourceBundle myProperties;
    private List<State> myPossibleStates;
    private Button mySubmitButton;
    private Button myLoadImageButton;
    private ColorPicker myColorPicker0;
    private ColorPicker myColorPicker1;
    private ColorPicker myColorPicker2;
    private BoardView myBoardView;
    private GraphView myGraphView;
    private List<Image> myImages;


    /**
     * Constructor to generate new RightView object based on the current/active SimulationController and a a specified
     * BoardView object
     *
     * @param sc SimulationController object to generate corresponding RightView object for
     * @param bv the BoardView object that this RightView object will generate GUI components to allow user to modify
     */
    public RightView(SimulationController sc, BoardView bv, GraphView gv) {

        mySimulationController = sc;
        myBoardView = bv;
        myGraphView = gv;
        myImages = new ArrayList<>();
        myPossibleStates = mySimulationController.getMySimulationModel().getMyRules().getPossibleStates();
        myProperties = ResourceBundle.getBundle("english");
        myRoot = new VBox();
        setView();
    }

    // Method to return the RightView's root containing GUI components to modify the simulation's state
    public Node getMyRoot() {
        return myRoot;
    }

    private void setView() {
        setHBox0();
        setHBox1();
        setHBox2();
        setSubmitButton();
        setLoadImageButton();
        setGraph();
    }

    private void setSubmitButton() {
        mySubmitButton = new Button(myProperties.getString("submit"));
        mySubmitButton.setOnAction(e -> this.submitColor());
        myRoot.getChildren().add(mySubmitButton);
    }

    private void submitColor() {
        myBoardView.setColors(myColorPicker0.getValue(), myColorPicker1.getValue(), myColorPicker2.getValue());
        mySimulationController.changeColor(myColorPicker0.getValue(), myColorPicker1.getValue(), myColorPicker2.getValue());
        mySimulationController.setNewBoard();

    }

    private void setHBox0() {
        HBox temp = new HBox();
        Label tempLabel = new Label(myProperties.getString("state_0_color"));
        temp.getChildren().add(tempLabel);
        myColorPicker0 = new ColorPicker();
        temp.getChildren().add(myColorPicker0);
        temp.setAlignment(Pos.CENTER);
        myRoot.getChildren().add(temp);
    }

    private void setHBox1() {
        HBox temp = new HBox();
        Label tempLabel = new Label(myProperties.getString("state_1_color"));
        temp.getChildren().add(tempLabel);
        myColorPicker1 = new ColorPicker();
        myColorPicker1.setValue(Color.BLACK);
        temp.getChildren().add(myColorPicker1);
        temp.setAlignment(Pos.CENTER);
        myRoot.getChildren().add(temp);

    }

    private void setHBox2() {
        HBox temp = new HBox();
        Label tempLabel = new Label(myProperties.getString("state_2_color"));
        temp.getChildren().add(tempLabel);
        myColorPicker2 = new ColorPicker();
        myColorPicker2.setValue(Color.BLUE);
        temp.getChildren().add(myColorPicker2);
        temp.setAlignment(Pos.CENTER);
        myRoot.getChildren().add(temp);

    }

    private void setLoadImageButton() {
        myLoadImageButton = new Button();
        myLoadImageButton.setText(myProperties.getString("load_image_button"));
        myLoadImageButton.setOnAction(e -> this.loadImage());
        myRoot.getChildren().add(myLoadImageButton);
    }


    private void loadImage() {//need to add appropriate exception catcher
        mySimulationController.pauseSimulation();
        for (int i = 0; i < 3; i++) {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(null);
            if (file != null) {
                myImages.add(new Image(this.getClass().getClassLoader().getResourceAsStream(file.getName())));
            }
        }
        mySimulationController.setImage(myImages);
        //mySimulationController.setNewBoard();

    }
    private void setGraph(){
        Node temp = myGraphView.getMyRoot();
        myRoot.getChildren().add(temp);

    }

}
