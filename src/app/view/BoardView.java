package app.view;

/*
Authors: Jaiveer Katariya, Jongho Shin, Kyle Harvey

This class is used to generate the view of the board/main simulation based on the array of cells fed into it from the
Board class. It assumes that the user possesses the following dependencies/packages:
app.model.GridShapeType;
app.controller.SimulationController;
javafx.scene.Group;
javafx.scene.Scene;
javafx.scene.image.Image;
javafx.scene.image.ImageView;
javafx.scene.paint.Color;
app.model.cell.Cell;
javafx.scene.shape.Polygon;
javafx.scene.shape.Rectangle;
java.util.ArrayList;
javafx.scene.shape.Shape;
java.util.ResourceBundle;

This class is used in the SimluationController and the MainView classes, and to use it, one would simply need to declare
it with the parameters specified by the constructor.

*/


import app.model.GridShapeType;
import app.controller.SimulationController;
import app.model.board.Board;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import app.model.cell.Cell;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

import javafx.scene.shape.Shape;

import java.util.ResourceBundle;
import java.util.List;


public class BoardView {
    private static final double BOARD_WIDTH = 700;
    private static final double BOARD_HEIGHT = 500;
    private static final int STROKE_WIDTH = 1;

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
    private Board myBoard;
    private GridShapeType myGridShape;

    private List<Image> myImageArray;
    private ImageView[][] myImageViewBoard;
    private Scene myScene;
    private ResourceBundle myProperties;
    private SimulationController mySimulationController;


    /**
     * Constructor to generate a new BoardView with a specified width, height, 2D array of cells, ResourceBundle,
     * SimulationController, and booleans to specify whether images or outlines are to be used, along with a list of
     * images to render
     *
     * @param board Board.java object to make BoardView from
     * @param properties properties file/ResourcesBundle for active simulation
     * @param sc SimulationController for active simulation
     * @param grid boolean to indicate whether or not user wants outlines for their grid
     * @param image boolean to indicate whether or not user wants to use images for their states
     * @param list list of image files to use for states
     */


    public BoardView( Board board, ResourceBundle properties,SimulationController sc, boolean grid, boolean image, List<Image> list){
        this( board, properties, sc, grid, list, Color.WHITE, Color.BLACK, Color.BLUE);
        myImageArray = list;
    }



    /**
     * Constructor to generate a new BoardView with a specified width, height, 2D array of cells, ResourceBundle,
     * SimulationController, and booleans to specify whether images or outlines are to be used
     *
     * @param board Board.java object to make BoardView from
     * @param properties properties file/ResourcesBundle for active simulation
     * @param sc SimulationController for active simulation
     * @param grid boolean to indicate whether or not user wants outlines for their grid
     * @param image boolean to indicate whether or not user wants to use images for their states
     */
    public BoardView(Board board, ResourceBundle properties, SimulationController sc, boolean grid, boolean image){
        this(board, properties, sc, grid, null, Color.WHITE, Color.BLACK, Color.BLUE);
    }



    /**
     * Constructor to generate a new BoardView with a specified width, height, 2D array of cells, ResourceBundle,
     * SimulationController, and booleans to specify whether images or outlines are to be used, along with specified
     * colors for the maximum possible number of states (3)
     *
     * @param board Board.java object to make BoardView from
     * @param properties properties file/ResourcesBundle for active simulation
     * @param sc SimulationController for active simulation
     * @param grid boolean to indicate whether or not user wants outlines for their grid
     * @param c0 color for state 0
     * @param c1 color for state 1
     * @param c2 color for state 2
     */
    public BoardView( Board board, ResourceBundle properties, SimulationController sc, boolean grid, List<Image> images, Color c0, Color c1, Color c2){
        myProperties = properties;
        myBoardWidth = board.getMyWidth();
        myBoardHeight = board.getMyHeight();
        myBoard = board;
        useStroke = grid;
        mySimulationController = sc;
        myImageArray = images;
        myGridShape = myBoard.getCellAtCoordinates(0,0).getMyGridShapeType();
        myColor0 = c0;
        myColor1 = c1;
        myColor2 = c2;
        myStrokeColor = Color.BLACK;
        useImage = false;
        cellHeight = BOARD_HEIGHT/myBoardHeight;
        cellWidth = BOARD_WIDTH/myBoardWidth;
        myImageViewBoard = new ImageView[myBoardWidth][myBoardHeight];
        myColorBoard = new Shape[myBoardWidth][myBoardHeight];
        myRoot = new Group();

        if(myImageArray!=null) {
            myRoot = createImageBoard(myBoardWidth, myBoardHeight);
        } else if (myGridShape == GridShapeType.RECTANGLE) {
            myRoot = createColorBoardRect(myBoardWidth, myBoardHeight);
        } else {
            myRoot = createColorBoardPolygon(myBoardWidth, myBoardHeight);
        }
    }

    /**
     * Method to set the colors of states in the board
     *
     * @param c0 color for state 0
     * @param c1 color for state 1
     * @param c2 color for state 2
     */
    public void setColors(Color c0, Color c1, Color c2){
        myColor0 = c0;
        myColor1 = c1;
        myColor2 = c2;
        updateBoard();
    }

    /**
     * Method to set images for the board
     *
     * @param input ArrayList to use for images to be used in simulation
     */
    public void setMyImageArray(List<Image> input){
        useImage = true;
        this.myImageArray = input;
        updateBoard();
    }


    /**
     * Method to set whether or not outlines are used
     *
     */
    public void changeGridStatus(){
        if(useStroke){
            useStroke = false;
        }else useStroke = true;
        updateBoard();
    }


    /**
     * Method to return root of scene of MainView

     *
     * @return Group object that serves as root of scene MainView
     */
    public Group getMyRoot(){return myRoot;}

    private void updateBoard(){
        myRoot.getChildren().clear();
        mySimulationController.getStateData();

        if(useImage){
            myRoot = createImageBoard(myBoardWidth, myBoardHeight);
        }else if(myGridShape==GridShapeType.RECTANGLE) {
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
                Cell c = myBoard.getCellAtCoordinates(i,j);
                ImageView imageView = new ImageView();
                assignImage(c, imageView);
                myImageViewBoard[i][j] = imageView;
                int[] loc = getLocationRect(i,j,width_num,height_num);
                imageView.setX(loc[0]);
                imageView.setY(loc[1]);
                result.getChildren().add(imageView);
            }
        }
        return result;
    }


    private Group createColorBoardPolygon(int width_num, int height_num){
        var result = new Group();

        for(int i =0; i<width_num;i++){
            for(int j=0; j<height_num;j++){

                Cell c = myBoard.getCellAtCoordinates(i,j);
                Polygon myPoly = new Polygon();
                // add polygon points
                assignColor(c, myPoly);
                myColorBoard[i][j] = myPoly;

                if(myGridShape==GridShapeType.HEXAGON) {
                    myPoly.getPoints().addAll(getHexPoints(j, i));
                }

                else if(myGridShape==GridShapeType.RHOMBUS){
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

        var result = new Group();
        for(int i =0; i<width_num;i++){
            for(int j=0; j<height_num;j++){
                Rectangle r = new Rectangle(cellWidth,cellHeight);

                Cell c = myBoard.getCellAtCoordinates(i, j);
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

}
