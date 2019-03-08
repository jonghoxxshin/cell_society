package app.model;

public class RhombusCell extends Cell {
    private static final int[][] NEIGHBORS_TYPE1 = {{-1, 0}, {1,0}, {-1, 1}, {1, 1}, {-2, 0}, {2,0}, {0, -1}, {0, 1}};
    private static final int[][] NEIGHBORS_TYPE2 = {{-1, 0}, {1,0}, {-1, 1}, {1, 1}};
    private static final int[][] NEIGHBORS_TYPE3 = {{0,-1}, {0,1}, {1,-1}, {1, 0}, {2, 0}};



    public RhombusCell(int state, int x, int y, int boardHeight, int boardWidth, int neighborType, int chronons, int energy, int edgeType) {
        super(state,x,y,boardHeight,boardWidth,neighborType,chronons,energy,edgeType);
        super.setMyGridShapeType(GridShapeType.RHOMBUS);
        if (super.getType() == 1) {
            super.setNeighbors(findNeighbors(NEIGHBORS_TYPE1));
        } else if (super.getType() == 2) {
            super.setNeighbors(findNeighbors(NEIGHBORS_TYPE2));
        } else if (super.getType() == 3) {
            super.setNeighbors(findNeighbors(NEIGHBORS_TYPE3));
        }
    }
}
