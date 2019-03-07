package app.model;

import java.lang.annotation.Documented;

public class RectangleCell extends Cell{
    private static final int[][] NEIGHBORS_TYPE1 = {{-1, -1}, {-1, 0}, {-1, +1}, {0, -1}, {0, +1}, {+1, -1}, {+1, 0}, {+1, +1}};
    private static final int[][] NEIGHBORS_TYPE2 = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
    private int type;
    private int myX;
    private int myY;
    private int[][] neighbors;
    private int myState;
    private int boardHeight;
    private int boardWidth;

    private int currentChronons;
    private int maxChronons = 10;
    private int currentEnergyLevel;
    private int edgeType = 0; //0 = torodial, 1 = finite, 2 = not left
    private GridShapeType myGridShapeType;


    public RectangleCell(int state, int x, int y, int boardHeight, int boardWidth, int neighborType, int chronons, int energy) {
        super(state,x,y,boardHeight,boardWidth,neighborType,chronons,energy);
        myGridShapeType = GridShapeType.RECTANGLE;
        if (type == 1) {
            neighbors = findNeighbors(NEIGHBORS_TYPE1);
        } else if (type == 2) {
            neighbors = findNeighbors(NEIGHBORS_TYPE2);
        }
    }

}
