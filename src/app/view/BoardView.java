package app.view;

import app.model.Board;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import app.model.Cell;
import javafx.scene.shape.Rectangle;
import java.util.ResourceBundle;


public class BoardView {
    public static final double BOARD_WIDTH = 600;
    public static final double BOARD_HEIGHT = 400;

    protected int myBoardWidth;
    protected int myBoardHeight;
    protected double cellHeight;
    protected double cellWidth;

    private Color myColor0;
    private Color myColor1;
    private Color myColor2;

    private Group myRoot;
    private Rectangle[][] myColorBoard;
    private Cell[][] myBoard;

    private Scene myScene;
    private ResourceBundle myProperties;

    public BoardView(int width, int height, Cell[][] board, ResourceBundle properties){
        this(width, height, board, properties, Color.WHITE, Color.BLACK, Color.BLUE);
    }

    public BoardView(int width, int height, Cell[][] board, ResourceBundle properties, Color c0, Color c1, Color c2){
        myProperties = properties;
        myBoardWidth = width;
        myBoardHeight = height;
        myBoard = board;
        myColor0 = c0;
        myColor1 = c1;
        myColor2 = c2;
        cellHeight = BOARD_HEIGHT/height;
        cellWidth = BOARD_WIDTH/width;
        myColorBoard = new Rectangle[myBoardWidth][myBoardHeight];
        myRoot = new Group();

        System.out.println("In constructor, width for arg is " + width);
        System.out.println("In constructor, height for arg is " + height);

        myRoot = createColorBoard(width,height);
    }

    public void setColors(Color c0, Color c1, Color c2){
        myColor0 = c0;
        myColor1 = c1;
        myColor2 = c2;
        updateBoard();

    }

    public Group getMyRoot(){return myRoot;}

    private void updateBoard(){
        myRoot.getChildren().clear();

        myRoot = createColorBoard(myBoardWidth,myBoardHeight);
    }

    private Group createColorBoard(int width_num, int height_num){
        var result = new Group();

        System.out.println("height_num is " + height_num);
        System.out.println("width_num is " + width_num);


        for(int i =0; i<width_num;i++){
            for(int j=0; j<height_num;j++){
                Rectangle r = new Rectangle(cellWidth,cellHeight);

                System.out.println("my board height in length is " + myBoard.length);
                System.out.println("my board width in length of first one is " + myBoard[0].length);

                System.out.println("j is " + j);
                System.out.println("i is " + i);


                Cell c = myBoard[j][i];
                //System.out.println("this is cell state" + c.getMyState());

                if(c.getMyState()==0){
                    r.setFill(myColor0);
                }else if(c.getMyState()==1){
                    r.setFill(myColor1);

                }else if(c.getMyState()==2){
                    r.setFill(myColor2);
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
