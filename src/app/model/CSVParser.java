package app.model;

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

    private Cell[][] cells;

    public CSVParser(String filename, int config){
        String csvGame = "";
        if (filename.equals("GameOfLife")) {
            if (config == 1) {
                csvGame = LIFE_1;
            } else if (config == 2) {
                csvGame = LIFE_2;
            }else if (config == 3) {
                csvGame = LIFE_3;
            }
        } else if (filename.equals("Percolation")) {
            if (config == 1) {
                csvGame = PERCOLATION_1;
            } else if (config == 2) {
                csvGame = PERCOLATION_2;
            }else if (config == 3) {
                csvGame = PERCOLATION_3;
            }
        }
        this.cells = generateCells(csvGame);
        //System.out.println(csvGame);
    }

    private Cell[][] generateCells(String filename){
        System.out.println(filename);
        Scanner csvScanner = new Scanner(CSVParser.class.getClassLoader().getResourceAsStream(filename));

        this.gameType = csvScanner.next();

        String[] dimensions = csvScanner.next().split(",");

        this.myWidth = Integer.parseInt(dimensions[1]);
        this.myHeight = Integer.parseInt(dimensions[0]);

        Cell[][] cellsGenerated = new Cell[myWidth][myHeight];

        for(int i=0; i<myHeight; i++){
            String[] currentRow = csvScanner.next().split(",");

            for(int j=0; j<myWidth; j++){
                cellsGenerated[j][i] = new Cell(Integer.parseInt(currentRow[j]), j, i, myHeight, myWidth);
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
