package app.controller;

import javafx.application.Application;
import javafx.stage.Stage;
import app.model.Board;
import app.view.BoardView;
import app.view.MainView;

public class Start extends Application {
    public static final String TITLE = "SIMULATION";
    @Override
    public void start(Stage stage) throws Exception {
        Board b = new Board("GameOfLifeConfig.csv");
        MainView mv = new MainView(new BoardView(b.getMyWidth(),b.getMyHeight(),b.getCells()));
        stage.setTitle(TITLE);
        stage.setScene(mv.getScene());
        stage.setResizable(false);
        stage.show();

    }
    public static void main(String[] args){
        launch(args);
    }


}
