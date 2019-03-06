package app.model;

import java.io.*;
import java.net.URISyntaxException;
import java.util.Properties;

public class PropertiesFileWriter {
    String myPropFileName;
    String myName;
    String myGameType;
    String myDescription;
    String fullCSVName;
    String myPropFile;
    Properties prop;

    public PropertiesFileWriter(String propertiesFileName, String name, String gameType, String description, String csvNumber){
        this.myPropFileName = propertiesFileName + ".properties";
        this.myPropFile = propertiesFileName;
        this.myName = name;
        this.myGameType = gameType;
        this.myDescription = description;
        this.fullCSVName = gameType + "Config" + csvNumber + ".csv";
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

            String[] toBeWritten = {fullNameString, fullGameString, fullDescString, csvString};

            for(String value:toBeWritten){
                output.write(value + "\n");
            }

            output.close();


        } catch( IOException e) {
            e.printStackTrace();
        }

    }

    public String getFullCSVName() {
        return fullCSVName;
    }

    public Properties getProp() {
        return prop;
    }




}
