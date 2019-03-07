package app.model;

public class HexCell extends Cell {
    private static final int[][] NEIGHBORS_HEX = {{0, -1}, {-1, -1}, {-1, 0}, {0, 1}, {1, 0}, {1, -1}};
    private static final int[][] NEIGHBORS_HEX_TYPE3 = {{0,1}, {0,-1}, {1,-1}, {1,0}};

    public HexCell(int state, int x, int y, int boardHeight, int boardWidth, int neighborType, int chronons, int energy) {
        super(state,x,y,boardHeight,boardWidth,neighborType,chronons,energy);
        super.setMyGridShapeType(GridShapeType.HEXAGON);

        if(super.getType() == 3){
            super.setNeighbors(findNeighbors(NEIGHBORS_HEX_TYPE3));
        }
        else {
            super.setNeighbors(findNeighbors(NEIGHBORS_HEX));
        }
    }


    @Override
    public int[][] findNeighbors(int[][] NEIGHBORS_HEX){
        int[][] tempNeighbors = getTempNeighborsForType();

        for(int i=0; i<tempNeighbors.length; i++){
            tempNeighbors[i] = getNeighbor(super.getMyX(), super.getMyY(), NEIGHBORS_HEX[i]);
        }

        return tempNeighbors;

    }

    public int[][] getTempNeighborsForType(){
        if(super.getType() == 3){
            return new int[4][2];
        }
        return new int[6][2];
    }

}
