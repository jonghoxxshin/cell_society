package app.model;

public class GridShape {

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
