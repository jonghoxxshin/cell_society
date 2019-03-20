package app.model;

/**
 * Grid Shape Class to allow multiple Gridshapes
 */
public class GridShape {

    /**
     * Get Shape based on shape name
     *
     * @param name
     * @return shape
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public GridShapeType getShape(String name){
        if(name.toLowerCase().equals("rhombus")){
            return GridShapeType.RHOMBUS;
        }
        else if(name.toLowerCase().equals("hexagon")){
            return GridShapeType.HEXAGON;
        }
        else{
            return GridShapeType.RECTANGLE;
        }
    }

    /**
     * Get Shape name on shape shape
     *
     * @param shape
     * @return name
     * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
     */
    public String getNameFromShape(GridShapeType shape){
        if(shape==GridShapeType.RHOMBUS){
            return "rhombus";
        }

        if(shape==GridShapeType.HEXAGON){
            return "hexagon";
        }

        else{
            return "rectangle";
        }
    }

}
