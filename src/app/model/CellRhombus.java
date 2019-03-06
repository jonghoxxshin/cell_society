package app.model;

public class CellRhombus extends Cell {
    private static final int[][] NEIGHBORS_RHOM_TYPE1 = {{-1, 0}, {1,0}, {-1, 1}, {1, 1}, {-2, 0}, {2,0}, {0, -1}, {0, 1}};
    private static final int[][] NEIGHBORS_RHOM_TYPE2 = {{-1, 0}, {1,0}, {-1, 1}, {1, 1}};
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


    public CellRhombus (int state, int x, int y, int boardHeight, int boardWidth, int neighborType, int chronons, int energy) {
        super(state,x,y,boardHeight,boardWidth,neighborType,chronons,energy);
        myGridShapeType = GridShapeType.RHOMBUS;
    }
}
