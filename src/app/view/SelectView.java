package app.view;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;

public class SelectView {
    public static final int PAGE_WIDTH = 300;
    public static final int PAGE_HEIGHT = 500;
    public static final String TITLE = "New Configuration";

    private Scene myScene;
    private VBox myRoot;

    public SelectView(){

    }

    private void setInputField(){

    }

    private void setScene(){
        myRoot = new VBox();


    }

    public Scene getMyScene(){
        myScene = new Scene(myRoot, PAGE_WIDTH, PAGE_HEIGHT);

        return myScene;
    }

}
