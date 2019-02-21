package controller;

import javafx.application.Application;
import javafx.stage.Stage;
import model.Board;
import view.BoardView;
import view.MainView;

public class Start extends Application {
    public static final String TITLE = "SIMULATION";
    public static final int APP_WIDTH = 800;
    public static final int APP_HEIGHT = 600;
    @Override
    public void start(Stage stage) throws Exception {
        Board b = new Board("GameOfLifeConfig.csv");
        MainView mv = new MainView(new BoardView(5,5,b.getCells()));
        stage.setTitle(TITLE);
        stage.setScene(mv.getScene());
        stage.setResizable(false);
        stage.show();

    }
    public static void main(String[] args){
        launch(args);
    }


}
