package app.view;

import app.controller.SimulationController;
import app.model.GridShape;
import java.util.Observable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import app.model.Cell;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import javafx.scene.shape.Shape;

import java.util.Observer;
import java.util.ResourceBundle;


public class BoardView implements IBoardObserver{
    public static final double BOARD_WIDTH = 800;
    public static final double BOARD_HEIGHT = 500;
    public static final int STROKE_WIDTH = 1;

    protected int myBoardWidth;
    protected int myBoardHeight;
    protected double cellHeight;
    protected double cellWidth;
    private Color myColor0;
    private Color myColor1;
    private Color myColor2;
    private Color myStrokeColor;
    private boolean useImage;
    private boolean useStroke;

    private Group myRoot;
    private Shape[][] myColorBoard;
    private Cell[][] myBoard;
    private ArrayList<Image> myImageArray;
    private ImageView[][] myImageViewBoard;
    private GridShape myGridShape;
    private Scene myScene;
    private ResourceBundle myProperties;
    private SimulationController mySimulationController;

    public BoardView(int width, int height, Cell[][] board, ResourceBundle properties, SimulationController sc){
        this(width, height, board, properties, sc, Color.WHITE, Color.BLACK, Color.BLUE);
    }

    public BoardView(int width, int height, Cell[][] board, ResourceBundle properties, SimulationController sc, Color c0, Color c1, Color c2){
        myProperties = properties;
        myBoardWidth = width;
        myBoardHeight = height;
        myBoard = board;
        mySimulationController = sc;
        myImageArray = new ArrayList<>();
        myGridShape = myBoard[0][0].getMyGridShape();
        myColor0 = c0;
        myColor1 = c1;
        myColor2 = c2;
        myStrokeColor = Color.BLACK;
        useImage = false;
        cellHeight = BOARD_HEIGHT/height;
        cellWidth = BOARD_WIDTH/width;
        myImageViewBoard = new ImageView[myBoardWidth][myBoardHeight];
        myColorBoard = new Shape[myBoardWidth][myBoardHeight];
        myRoot = new Group();
        if(myGridShape==GridShape.RECTANGLE) {
            myRoot = createColorBoardRect(myBoardWidth, myBoardHeight);
        }
        else{
            myRoot = createColorBoardPolygon(myBoardWidth, myBoardHeight);
        }
    }

    public void setColors(Color c0, Color c1, Color c2){
        myColor0 = c0;
        myColor1 = c1;
        myColor2 = c2;
        updateBoard();
    }

    public void setMyImageArray(ArrayList<Image> input){
        useImage = true;
        this.myImageArray = input;
        updateBoard();
        mySimulationController.replaceBoardView();
    }

    public void changeStroke(){
        if(useStroke){
            useStroke = false;
        }else useStroke = true;
    }

    public Group getMyRoot(){return myRoot;}

    private void updateBoard(){
        myRoot.getChildren().clear();
        if(useImage){
            myRoot = createImageBoard(myBoardWidth, myBoardHeight);
        }else if(myGridShape==GridShape.RECTANGLE) {
            myRoot = createColorBoardRect(myBoardWidth, myBoardHeight);
        }
        else{
            myRoot = createColorBoardPolygon(myBoardWidth, myBoardHeight);
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

    private void assignImage(Cell c, ImageView ig){
        int state = c.getMyState();
        if(state==0){
            ig.setImage( myImageArray.get(0));
        }else if(state==1){
            ig.setImage( myImageArray.get(1));
        }else if(state==2){
            ig.setImage( myImageArray.get(2));
        }
        ig.setFitWidth(cellWidth);
        ig.setFitHeight(cellHeight);
    }

    private Group createImageBoard(int width_num, int height_num){
        var result = new Group();
        for(int i = 0; i< width_num; i++){
            for(int j=0; j<height_num;j++){
                Cell c = myBoard[j][i];
                ImageView imageView = new ImageView();
                assignImage(c, imageView);
                myImageViewBoard[i][j] = imageView;
                int[] loc = getLocationRect(i,j,width_num,height_num);
                imageView.setX(loc[0]);
                imageView.setY(loc[1]);
                result.getChildren().add(imageView);
            }
        }
        System.out.println("made it here");
        return result;
    }


    private Group createColorBoardPolygon(int width_num, int height_num){
        var result = new Group();

        for(int i =0; i<width_num;i++){
            for(int j=0; j<height_num;j++){

                Cell c = myBoard[j][i];
                Polygon myPoly = new Polygon();
                // add polygon points
                assignColor(c, myPoly);
                myColorBoard[i][j] = myPoly;
                if(myGridShape==GridShape.HEXAGON) {
                    myPoly.getPoints().addAll(getHexPoints(j, i));
                }
                else if(myGridShape==GridShape.RHOMBUS){
                    myPoly.getPoints().addAll(getRhombusPoints(j, i));
                }
                myPoly.setOnMouseClicked(e->cellClicked(c));
                setStroke(myPoly, myStrokeColor,STROKE_WIDTH);
                result.getChildren().add(myPoly);
            }
        }
        return result;
    }

    private Double[] getRhombusPoints(int rowNumber, int columnNumber){
        Double[] points = new Double[8];

        if(columnNumber % 2 == 0){
            points[0] = ((cellWidth / 2) * columnNumber) + (cellWidth/2);
            points[1] = (rowNumber*cellHeight);

            points[2] = ((cellWidth/2) * columnNumber) + cellWidth;
            points[3] = (rowNumber*cellHeight) + (cellHeight/2);

            points[4] = ((cellWidth / 2) * columnNumber) + (cellWidth/2);
            points[5] = (rowNumber*cellHeight) + cellHeight;

            points[6] = (cellWidth/2) * columnNumber;
            points[7] = (rowNumber*cellHeight) + (cellHeight/2);
        }

        else{
            // offset of cell height / 2
            points[0] = ((cellWidth / 2) * columnNumber) + (cellWidth/2);
            points[1] = (rowNumber*cellHeight) + (cellHeight/2);

            points[2] = ((cellWidth/2) * columnNumber) + cellWidth;
            points[3] = (rowNumber*cellHeight) + (cellHeight/2) + (cellHeight/2);

            points[4] = ((cellWidth / 2) * columnNumber) + (cellWidth/2);
            points[5] = (rowNumber*cellHeight) + cellHeight + (cellHeight/2);

            points[6] = (cellWidth/2) * columnNumber;
            points[7] = (rowNumber*cellHeight) + (cellHeight/2) + (cellHeight/2);

        }

        return points;
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
            points[1] = (rowNumber*cellHeight) + (cellHeight/2);

            points[2] = ((3 * cellWidth / 4) * columnNumber) + (3*cellWidth/4);
            points[3] = (rowNumber*cellHeight) + (cellHeight/2);

            points[4] = ((3 * cellWidth / 4) * columnNumber) + (cellWidth);
            points[5] = (rowNumber*cellHeight) + (cellHeight / 2) + (cellHeight/2);

            points[6] = ((3 * cellWidth / 4) * columnNumber) + (3*cellWidth/4);
            points[7] = (rowNumber*cellHeight) + cellHeight + (cellHeight/2);

            points[8] = ((3 * cellWidth / 4) * columnNumber) + (cellWidth/4);
            points[9] = (rowNumber*cellHeight) + cellHeight + (cellHeight/2);

            points[10] = (3 * cellWidth / 4) * columnNumber;
            points[11] = (rowNumber*cellHeight) + (cellHeight / 2) + (cellHeight/2);

        }
        return points;
    }

    private Group createColorBoardRect(int width_num, int height_num){
        System.out.println("create Color Board rect is invoked");
        var result = new Group();
        for(int i =0; i<width_num;i++){
            for(int j=0; j<height_num;j++){
                Rectangle r = new Rectangle(cellWidth,cellHeight);

                Cell c = myBoard[j][i];
                assignColor(c, r);
                myColorBoard[i][j] = r;
                int[] loc = getLocationRect(i,j,width_num,height_num);
                r.setOnMouseClicked(e->cellClicked(c));
                r.setX(loc[0]);
                r.setY(loc[1]);
                setStroke(r, myStrokeColor, STROKE_WIDTH);
                result.getChildren().add(r);
            }
        }
        return result;
    }

    private void setStroke(Shape shape, Color color, int stroke_width){
        if(useStroke){
            shape.setStroke(color);
            shape.setStrokeWidth(stroke_width);
        }
    }

    private int[] getLocationRect(int i, int j, int width_num, int height_num){
        int[] result = new int[2];
        int xval = (int) BOARD_WIDTH/width_num * i;
        int yval = (int) BOARD_HEIGHT/height_num * j;
        result[0] = xval;
        result[1] = yval;
        return result;
    }

    private void cellClicked(Cell cell){
        int currState = cell.getMyState();
        int nextState = currState+1;
        cell.setMyState(nextState%3);
        updateBoard();
        mySimulationController.replaceBoardView();
    }

    @Override
    public void update(Object o) {

    }
}
