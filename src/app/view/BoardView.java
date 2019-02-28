package app.view;

import app.model.Board;
import javafx.scene.Group;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import app.model.Cell;
import javafx.scene.paint.Paint;
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

    public void updateBoard(Board board){
        myBoard = board.getCells();
        myRoot = createColorBoard(myBoardWidth,myBoardHeight);
    }

    private Group createColorBoard(int width_num, int height_num){
        var result = new Group();
        for(int i =0; i<width_num;i++){
            for(int j=0; j<height_num;j++){
                Rectangle r = new Rectangle(cellWidth,cellHeight);
                Cell c = myBoard[i][j];
                //System.out.println("this is cell state" + c.getMyState());
                if(c.getMyState()==0){
                    r.setFill(Color.WHITE);
                }else if(c.getMyState()==1){
                    r.setFill(Color.BLACK);
                }else if(c.getMyState()==2){
                    r.setFill(Color.BLUE);
                }
                myColorBoard[i][j] = r;
                int[] loc = getLocation(i,j,width_num,height_num);
                r.setX(loc[0]);
                r.setY(loc[1]);
                result.getChildren().add(r);
            }
        }
        return result;
    }


    private int[] getLocation(int i, int j, int width_num, int height_num){
        int[] result = new int[2];
        int xval = (int) BOARD_WIDTH/width_num * i;
        int yval = (int) BOARD_HEIGHT/height_num * j;
        result[0] = xval;
        result[1] = yval;
        return result;
    }

    public Rectangle[][] getMycolorBoard(){return myColorBoard;}


}

