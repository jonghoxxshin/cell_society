package app.model;

public class Properties {
    private String myName;
    private String myGameType;
    private String myDescription;
    private String myCSV;

    public Properties(String name, String type, String des, String csv){
        myName = name;
        myGameType= type;
        myDescription = des;
        myCSV = csv;
    }



    @Override
    public String toString() {
        return "Properties{" +
                "myName='" + myName + '\'' +
                ", myGameType='" + myGameType + '\'' +
                ", myDescription='" + myDescription + '\'' +
                ", myCSV='" + myCSV + '\'' +
                '}';
    }
}
