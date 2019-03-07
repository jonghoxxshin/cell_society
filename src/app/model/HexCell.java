package app.model;

public class HexCell extends Cell {
    private static final int[][] NEIGHBORS_HEX = {{0, -1}, {-1, -1}, {-1, 0}, {0, 1}, {1, 0}, {1, -1}};
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

    public HexCell(int state, int x, int y, int boardHeight, int boardWidth, int neighborType, int chronons, int energy) {
        super(state,x,y,boardHeight,boardWidth,neighborType,chronons,energy);
        myGridShapeType = GridShapeType.HEXAGON;
    }

    @Override
    public int[][] findNeighbors(int[][] NEIGHBORS_HEX){
        int[][] tempNeighbors = new int[6][2];

        for(int i=0; i<tempNeighbors.length; i++){
            tempNeighbors[i] = getNeighbor(myX, myY, NEIGHBORS_HEX[i]);
        }

        return tempNeighbors;

    }
}
