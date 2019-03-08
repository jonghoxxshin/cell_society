package app.view;

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
import java.util.ResourceBundle;

public class RightView {
    private VBox myRoot;
    private SimulationController mySimulationController;
    private ResourceBundle myProperties;
    private ArrayList<State> myPossibleStates;
    private Button mySubmitButton;
    private Button myLoadImageButton;
    private ColorPicker myColorPicker0;
    private ColorPicker myColorPicker1;
    private ColorPicker myColorPicker2;
    private BoardView myBoardView;
    private ArrayList<Image> myImages;

    public RightView(SimulationController sc, BoardView bv) {
        mySimulationController = sc;
        myBoardView = bv;
        myImages = new ArrayList<>();
        myPossibleStates = mySimulationController.getMySimulationModel().getMyRules().getPossibleStates();
        myProperties = ResourceBundle.getBundle("english");
        myRoot = new VBox();
        setView();
    }

    public Node getMyRoot() {
        return myRoot;
    }

    private void setView() {
        setHBox0();
        setHBox1();
        setHBox2();
        setSubmitButton();
        setLoadImageButton();
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

}
