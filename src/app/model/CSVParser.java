package app.model;

import java.util.Scanner;

public class CSVParser {

    private int myHeight;
    private int myWidth;
    private String gameType;

    private Cell[][] cells;

    public CSVParser(String filename){
        this.cells = generateCells(filename);
    }

    private Cell[][] generateCells(String filename){

        Scanner csvScanner = new Scanner(CSVParser.class.getClassLoader().getResourceAsStream(filename));

        this.gameType = csvScanner.next();

        String[] dimensions = csvScanner.next().split(",");

        this.myWidth = Integer.parseInt(dimensions[0]);
        this.myHeight = Integer.parseInt(dimensions[1]);

        Cell[][] cellsGenerated = new Cell[myWidth][myHeight];

        for(int i=0; i<myHeight; i++){
            String[] currentRow = csvScanner.next().split(",");

            for(int j=0; j<myWidth; j++){
                cellsGenerated[i][j] = new Cell(Integer.parseInt(currentRow[j]), j, i, myHeight, myWidth);
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


}
