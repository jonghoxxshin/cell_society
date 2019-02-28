package app.model;

import javafx.scene.paint.Color;

import java.util.Scanner;

public class CSVParser {
    private int myHeight;
    private int myWidth;
    private String gameType;
    private int neighborType;
    private Color[] myColors;
    private String myDescription;
    private Cell[][] cells;

    public CSVParser(String filename){
        String csvGame = filename;

        this.cells = generateCells(csvGame);
    }

    private Cell[][] generateCells(String filename){

        Scanner csvScanner = new Scanner(CSVParser.class.getClassLoader().getResourceAsStream(filename));

        this.gameType = csvScanner.next();

        String[] dimensions = csvScanner.next().split(",");

        this.myWidth = Integer.parseInt(dimensions[1]);
        this.myHeight = Integer.parseInt(dimensions[0]);

        Cell[][] cellsGenerated = new Cell[myWidth][myHeight];

        for(int i=0; i<myHeight; i++){
            String[] currentRow = csvScanner.next().split(",");

            for(int j=0; j<myWidth; j++){
                cellsGenerated[j][i] = new Cell(Integer.parseInt(currentRow[j]), j, i, myHeight, myWidth, neighborType);
            }
        }
        return cellsGenerated;
    }

    public String getGameType() {
        return gameType;
    }

    public int getMyHeight() {
        return myHeight;
    }

    public int getMyWidth() {
        return myWidth;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public int getNeighborType() {
        return neighborType;
    }


}
