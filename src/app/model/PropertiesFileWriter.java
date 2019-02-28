package app.model;

import java.io.*;

public class PropertiesFileWriter {
    String myName;
    String myGameType;
    String myDescription;
    String fullCSVName;

    public PropertiesFileWriter(String name, String gameType, String description, String csvNumber){
        this.myName = name;
        this.myGameType = gameType;
        this.myDescription = description;
        this.fullCSVName = gameType + "Config" + csvNumber + ".csv";

    }


    public void writePropertiesFile(){
        Properties prop = new Properties();
        InputStream in = getClass().getResourceAsStream("xyz.properties");
        prop.load(in);
    }

    public String getFullCSVName() {
        return fullCSVName;
    }

    /*
    name_of_creator=Ed
type_of_game=GameOfLife
description=simulates cellular behavior
name_of_csv=GameOfLifeConfig1.csv
color0=white
color1=blue


     */



}
