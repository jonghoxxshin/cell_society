package app.model;

import javafx.scene.paint.Color;

import java.util.Scanner;

public class CSVParser {
    private int myHeight;
    private int myWidth;
    private String gameType;
    private static final String LIFE_1 = "GameOfLifeConfig1.csv";
    private static final String LIFE_2 = "GameOfLifeConfig2.csv";
    private static final String LIFE_3 = "GameOfLifeConfig3.csv";
    private static final String PERCOLATION_1 = "PercolationConfig1.csv";
    private static final String PERCOLATION_2 = "PercolationConfig2.csv";
    private static final String PERCOLATION_3 = "PercolationConfig3.csv";
    private static final String RPS_1 = "RockPaperScissorsConfig1.csv";
    //Fix once we create new config files
    private static final String FIRE_1 = "RockPaperScissorsConfig1.csv";
    private static final String SEGREGATION_1 = "RockPaperScissorsConfig1.csv";
    private static final String PREDATORPREY_1 = "RockPaperScissorsConfig1.csv";
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
