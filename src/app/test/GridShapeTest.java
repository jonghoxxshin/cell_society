package app.test;

import app.model.GridShape;
import app.model.GridShapeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GridShapeTest {

    GridShape myShapeConverter;

    @BeforeEach
    public void setup(){
        myShapeConverter = new GridShape();
    }

    @Test
    void getShapeRhombus() {
        assertEquals(myShapeConverter.getShape("rhombus"), GridShapeType.RHOMBUS);
    }

    @Test
    void getShapeRectangle() {
        assertEquals(myShapeConverter.getShape("rectangle"), GridShapeType.RECTANGLE);
    }

    @Test
    void getShapeHex() {

    }

    @Test
    void getNameFromShape() {

    }
}