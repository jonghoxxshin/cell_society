package view;

import javafx.scene.Group;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import model.Cell;
import javafx.scene.shape.Rectangle;


public class BoardView {
    public static final double BOARD_WIDTH = 600;
    public static final double BOARD_HEIGHT = 400;

    protected int myBoardWidth;
    protected int myBoardHeight;
    protected double cellHeight;
    protected double cellWidth;
    private Group myRoot;
    private Rectangle[][] myColorBoard;
    private Cell[][] myBoard;
    private Scene myScene;

    public BoardView(int width, int height, Cell[][] board){
        myBoardWidth = width;
        myBoardHeight = height;
        myBoard = board;
        cellHeight = BOARD_HEIGHT/height;
        cellWidth = BOARD_WIDTH/width;
        myColorBoard = new Rectangle[myBoardWidth][myBoardHeight];
        myRoot = new Group();
        myRoot = createColorBoard(width,height);
    }

    public Rectangle[][] getMyBoard(){return myColorBoard;}
    public Group getMyRoot(){return myRoot;}

    private Group createColorBoard(int width_num, int height_num){
        var result = new Group();
        for(int i =0; i<width_num;i++){
            for(int j=0; j<height_num;j++){
                Rectangle r = new Rectangle(cellWidth,cellHeight);
                
                Cell c = myBoard[i][j];
                if(c.getMyState()==0){
                    r.setFill(Color.WHITE);
                }else{
                    r.setFill(Color.BLACK);
                }
                myColorBoard[i][j] = r;
                r.setX(BOARD_WIDTH/(i+1));
                r.setY(BOARD_HEIGHT/(j+1));
                result.getChildren().add(r);
            }
        }

        return result;
    }

    public Rectangle[][] getMycolorBoard(){return myColorBoard;}


}

