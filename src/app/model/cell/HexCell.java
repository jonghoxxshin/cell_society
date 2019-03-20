package app.model.cell;

import app.model.GridShapeType;


/**
 * Hex Cell Class subclass of Cell
 * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
 */
public class HexCell extends Cell {
    private static final int[][] NEIGHBORS_HEX = {{0, -1}, {-1, -1}, {-1, 0}, {0, 1}, {1, 0}, {1, -1}};
    private static final int[][] NEIGHBORS_HEX_TYPE3 = {{0,1}, {0,-1}, {1,-1}, {1,0}};

    /**
     * Hex Cell Constructor
     * <p>
     *     same constructor as super, but set shape to hexagon
     *     and get neighbor type corresponding to hexagon
     * </p>
     * @param state
     * @param x
     * @param y
     * @param boardHeight
     * @param boardWidth
     * @param neighborType
     * @param chronons
     * @param energy
     * @param edgeType
     */
    public HexCell(int state, int x, int y, int boardHeight, int boardWidth, int neighborType, int chronons, int energy, int edgeType) {
        super(state,x,y,boardHeight,boardWidth,neighborType,chronons,energy, edgeType);
        super.setMyGridShapeType(GridShapeType.HEXAGON);

        if(super.getType() == 3){
            super.setNeighbors(findNeighbors(NEIGHBORS_HEX_TYPE3));
        }
        else {
            super.setNeighbors(findNeighbors(NEIGHBORS_HEX));
        }
    }

    /**
     * Find Neighbors
     *
     * @param NEIGHBORS_HEX
     * @return array of neighbor coordinates
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    @Override
    public int[][] findNeighbors(int[][] NEIGHBORS_HEX){
        int[][] tempNeighbors = getTempNeighborsForType();

        for(int i=0; i<tempNeighbors.length; i++){
            tempNeighbors[i] = getNeighbor(super.getMyX(), super.getMyY(), NEIGHBORS_HEX[i]);
        }

        return tempNeighbors;

    }

    /**
     * Get Empty Array for Neighbor Coordinates
     *
     * @return empty array for neighbor coordinates
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public int[][] getTempNeighborsForType(){
        if(super.getType() == 3){
            return new int[4][2];
        }
        return new int[6][2];
    }

}
