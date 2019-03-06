package app.view;

import app.model.Board;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import app.model.Cell;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
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
    private ArrayList<Image> myImageArray;
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
        myImageArray = new ArrayList<>();
        myColor0 = c0;
        myColor1 = c1;
        myColor2 = c2;
        cellHeight = BOARD_HEIGHT/height;
        cellWidth = BOARD_WIDTH/width;
        myColorBoard = new Rectangle[myBoardWidth][myBoardHeight];
        myRoot = new Group();
        myRoot = createColorBoard(width,height);
    }

    public void setColors(Color c0, Color c1, Color c2){
        myColor0 = c0;
        myColor1 = c1;
        myColor2 = c2;
        updateBoard();

    }
    public void setMyImageArray(ArrayList<Image> input){
        System.out.println(input.size());
        this.myImageArray = input;
        updateBoard();
        System.out.println(myImageArray.size());
    }

    public Group getMyRoot(){return myRoot;}

    private void updateBoard(){
        myRoot.getChildren().clear();
        myRoot = createColorBoard(myBoardWidth,myBoardHeight);
    }

    private Group createColorBoard(int width_num, int height_num){
        var result = new Group();
        for(int i =0; i<width_num;i++){
            for(int j=0; j<height_num;j++) {

                Cell c = myBoard[i][j];
                int[] loc = getLocation(i, j, width_num, height_num);
                //System.out.println("this is cell state" + c.getMyState());

                if (myImageArray.size() == 3) {
                    ImageView iv = new ImageView();
                    if (c.getMyState() == 0) {
                        System.out.println();
                         iv.setImage(myImageArray.get(0));
                    }else if(c.getMyState()==1){
                        iv.setImage(myImageArray.get(1));
                    }else if(c.getMyState()==2){
                        iv.setImage(myImageArray.get(2));
                    }
                    iv.setX(loc[0]);
                    iv.setY(loc[1]);
                    result.getChildren().add(iv);
                } else {
                    Rectangle r = new Rectangle(cellWidth, cellHeight);
                    if (c.getMyState() == 0) {
                        r.setFill(myColor0);
                    } else if (c.getMyState() == 1) {
                        r.setFill(myColor1);

                    } else if (c.getMyState() == 2) {
                        r.setFill(myColor2);
                    }

                    myColorBoard[i][j] = r;
                    r.setX(loc[0]);
                    r.setY(loc[1]);
                    result.getChildren().add(r);

                }

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
