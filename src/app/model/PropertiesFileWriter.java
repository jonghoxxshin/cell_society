package app.model;

import java.io.*;
import java.net.URISyntaxException;
import java.util.Properties;

public class PropertiesFileWriter {
    private String myPropFileName;
    private String myName;
    private String myGameType;
    private String myDescription;
    private String fullCSVName;
    private String myPropFile;
    private String gridShape;
    private String edgePol;
    private String neighborPol;

    private Properties prop;

    public PropertiesFileWriter(String propertiesFileName, String name, String gameType, String description, String csvNumber, String gridShape, String edgePol, String neighborPol){
        this.myPropFileName = propertiesFileName + ".properties";
        this.myPropFile = propertiesFileName;
        this.myName = name.replaceAll("\\s","");
        this.myGameType = gameType;
        this.myDescription = description;
        this.fullCSVName = gameType + "Config" + csvNumber + ".csv";
        this.gridShape = gridShape.toLowerCase();
        this.edgePol = edgePol;
        this.neighborPol = neighborStringToNumber(neighborPol);

        prop = new Properties();
        setPropertyFileValues();
        writePropertiesFile();
    }

    public String getMyPropFile() {
        return myPropFile;
    }


    private void setPropertyFileValues(){
        prop.setProperty("name_of_creator", myName);
        prop.setProperty("type_of_game", myGameType);
        prop.setProperty("description", myDescription);
        prop.setProperty("name_of_csv", fullCSVName);
        prop.setProperty("shape", gridShape);
        prop.setProperty("edge_policy", edgePol);
        prop.setProperty("neighbor_type", neighborPol);


    }

    private void writePropertiesFile(){
        try {
            File file = new File("data/" + myPropFileName);
            file.createNewFile();

            var output = new BufferedWriter(new FileWriter(file));

            String fullNameString = "name_of_creator=" + myName;
            String fullGameString = "type_of_game=" + myGameType;
            String fullDescString = "description=" + myDescription;
            String csvString = "name_of_csv=" + fullCSVName;
            String shapeString = "shape=" + gridShape;
            String edgePolicyString = "edge_policy=" + edgePol;
            String neighborTypeString = "neighbor_type=" + neighborPol;

            String[] toBeWritten = {fullNameString, fullGameString, fullDescString, csvString, shapeString, edgePolicyString, neighborTypeString};

            for(String value:toBeWritten){
                output.write(value + "\n");
            }

            output.close();


        } catch( IOException e) {
            e.printStackTrace();
        }

    }

    private String neighborStringToNumber(String neighborType){
        if(neighborType.toLowerCase().equals("left-only")){
            return "3";
        }

        else if(neighborType.toLowerCase().equals("cardinal")){
            return "2";
        }

        else{
            return "1";
        }
    }

    public String getFullCSVName() {
        return fullCSVName;
    }

    public Properties getProp() {
        return prop;
    }




}
