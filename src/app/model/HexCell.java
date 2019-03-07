package app.model;

public class HexCell extends Cell {
    private static final int[][] NEIGHBORS_HEX = {{0, -1}, {-1, -1}, {-1, 0}, {0, 1}, {1, 0}, {1, -1}};

    public HexCell(int state, int x, int y, int boardHeight, int boardWidth, int neighborType, int chronons, int energy) {
        super(state,x,y,boardHeight,boardWidth,neighborType,chronons,energy);
        super.setMyGridShapeType(GridShapeType.HEXAGON);
        super.setNeighbors(findNeighbors(NEIGHBORS_HEX));
    }


    @Override
    public int[][] findNeighbors(int[][] NEIGHBORS_HEX){
        int[][] tempNeighbors = new int[6][2];

        for(int i=0; i<tempNeighbors.length; i++){
            tempNeighbors[i] = getNeighbor(super.getMyX(), super.getMyY(), NEIGHBORS_HEX[i]);
        }

        return tempNeighbors;

    }
}
