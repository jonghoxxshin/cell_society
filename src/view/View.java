package view;

import javafx.application.Application;
import javafx.stage.Stage;

public class View extends Application {
    public static final String TITLE = "SIMULATION";
    @Override
    public void start(Stage stage) throws Exception {
        MainView mv = new MainView();
        stage.setTitle(TITLE);
        stage.setScene(mv.getScene());
        stage.show();

    }
    public static void main(String[] args){
        launch(args);
    }


}
