package app.view;

import app.model.GridShape;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import app.model.Cell;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

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
    private Shape[][] myColorBoard;
    private Cell[][] myBoard;

    private GridShape myGridShape;

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
        myGridShape = myBoard[0][0].getMyGridShape();
        myColor0 = c0;
        myColor1 = c1;
        myColor2 = c2;
        cellHeight = BOARD_HEIGHT/height;
        cellWidth = BOARD_WIDTH/width;
        myColorBoard = new Shape[myBoardWidth][myBoardHeight];
        myRoot = new Group();

        System.out.println("In constructor, width for arg is " + width);
        System.out.println("In constructor, height for arg is " + height);

        if(myGridShape==GridShape.RECTANGLE) {
            myRoot = createColorBoardRect(width, height);
        }

        else if(myGridShape==GridShape.HEXAGON){
            myRoot = createColorBoardHex(myBoardWidth, myBoardHeight);
        }
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

        if(myGridShape==GridShape.RECTANGLE) {
            myRoot = createColorBoardRect(myBoardWidth, myBoardHeight);
        }

        else if(myGridShape==GridShape.HEXAGON){
            myRoot = createColorBoardHex(myBoardWidth, myBoardHeight);
        }
    }

    private void assignColor(Cell c, Shape shape){
        if(c.getMyState()==0){
            shape.setFill(myColor0);
        }else if(c.getMyState()==1){
            shape.setFill(myColor1);

        }else if(c.getMyState()==2){
            shape.setFill(myColor2);
        }
    }


    private Group createColorBoardHex(int width_num, int height_num){
        var result = new Group();

        System.out.println("height_num is " + height_num);
        System.out.println("width_num is " + width_num);


        for(int i =0; i<width_num;i++){
            for(int j=0; j<height_num;j++){

                Cell c = myBoard[j][i];
                //System.out.println("this is cell state" + c.getMyState());

                Polygon myHex = new Polygon();

                // add polygon points

                assignColor(c, myHex);

                myColorBoard[i][j] = myHex;

                myHex.getPoints().addAll(getHexPoints(j, i));

                myHex.setStroke(Color.BLACK);
                myHex.setStrokeWidth(1);

                result.getChildren().add(myHex);
            }
        }
        return result;
    }

    private Double[] getHexPoints(int rowNumber, int columnNumber){
        Double[] points = new Double[12];

        if(columnNumber % 2 == 0){
            points[0] = ((3 * cellWidth / 4) * columnNumber) + (cellWidth/4);
            points[1] = (rowNumber*cellHeight);

            points[2] = ((3 * cellWidth / 4) * columnNumber) + (3*cellWidth/4);
            points[3] = (rowNumber*cellHeight);

            points[4] = ((3 * cellWidth / 4) * columnNumber) + (cellWidth);
            points[5] = (rowNumber*cellHeight) + (cellHeight / 2);

            points[6] = ((3 * cellWidth / 4) * columnNumber) + (3*cellWidth/4);
            points[7] = (rowNumber*cellHeight) + cellHeight;

            points[8] = ((3 * cellWidth / 4) * columnNumber) + (cellWidth/4);
            points[9] = (rowNumber*cellHeight) + cellHeight;

            points[10] = (3 * cellWidth / 4) * columnNumber;
            points[11] = (rowNumber*cellHeight) + (cellHeight / 2);

        }

        else{
            // offset of cell height / 4
            points[0] = ((3 * cellWidth / 4) * columnNumber) + (cellWidth/4);
            points[1] = (rowNumber*cellHeight) + (cellHeight/4) + (cellWidth/4);

            points[2] = ((3 * cellWidth / 4) * columnNumber) + (3*cellWidth/4);
            points[3] = (rowNumber*cellHeight) + (cellHeight/4) + (cellWidth/4);

            points[4] = ((3 * cellWidth / 4) * columnNumber) + (cellWidth);
            points[5] = (rowNumber*cellHeight) + (3 * cellHeight / 4) + (cellWidth/4);

            points[6] = ((3 * cellWidth / 4) * columnNumber) + (3*cellWidth/4);
            points[7] = (rowNumber*cellHeight) + (5*cellHeight / 4) + (cellWidth/4);

            points[8] = ((3 * cellWidth / 4) * columnNumber) + (cellWidth/4);
            points[9] = (rowNumber*cellHeight) + (5*cellHeight / 4) + (cellWidth/4);

            points[10] = (3 * cellWidth / 4) * columnNumber;
            points[11] = (rowNumber*cellHeight) + (3 * cellHeight / 4) + (cellWidth/4);


        }

        // calculate six point values based on current row and column, along with width and height of entire board -
        // that'll give you height and width dimensions

        return points;
    }


    private Group createColorBoardRect(int width_num, int height_num){
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

                assignColor(c, r);

                myColorBoard[i][j] = r;
                int[] loc = getLocationRect(i,j,width_num,height_num);
                r.setX(loc[0]);
                r.setY(loc[1]);
                result.getChildren().add(r);
            }
        }
        return result;
    }


    private int[] getLocationRect(int i, int j, int width_num, int height_num){
        int[] result = new int[2];
        int xval = (int) BOARD_WIDTH/width_num * i;
        int yval = (int) BOARD_HEIGHT/height_num * j;
        result[0] = xval;
        result[1] = yval;
        return result;
    }

    public Shape[][] getMycolorBoard(){ return myColorBoard; }


}
