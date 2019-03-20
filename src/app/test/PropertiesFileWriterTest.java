package app.test;

import app.model.PropertiesFileWriter;
import org.junit.jupiter.api.Test;

import java.util.ResourceBundle;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class PropertiesFileWriterTest {

    @Test
    public void checkInformationBeingWrittenTest(){
        PropertiesFileWriter tester = new PropertiesFileWriter("madeInTest", "Jaiveer",
                "GameOfLife", "a game of life", "1", "rectangle", "0", "normal");

        Scanner testFileScanner = new Scanner(PropertiesFileWriter.class.getClassLoader().getResourceAsStream("madeInTest.properties"));

        ResourceBundle madeInTest = ResourceBundle.getBundle("madeInTest");

        assertEquals(madeInTest.getString("name_of_creator"), "Jaiveer");
        assertEquals(madeInTest.getString("type_of_game"), "GameOfLife");
        assertEquals(madeInTest.getString("description"), "a game of life");
        assertEquals(madeInTest.getString("name_of_csv"), "GameOfLifeConfig1.csv");
        assertEquals(madeInTest.getString("shape"), "rectangle");
        assertEquals(madeInTest.getString("edge_policy"), "0");
        assertEquals(madeInTest.getString("neighbor_type"), "1");


        /*
            String fullNameString = "name_of_creator=" + myName;
            String fullGameString = "type_of_game=" + myGameType;
            String fullDescString = "description=" + myDescription;
            String csvString = "name_of_csv=" + fullCSVName;
            String shapeString = "shape=" + gridShape;
            String edgePolicyString = "edge_policy=" + edgePol;
            String neighborTypeString = "neighbor_type=" + neighborPol;
         */

    }


}