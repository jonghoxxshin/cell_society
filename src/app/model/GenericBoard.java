package app.model;

import java.util.ResourceBundle;

public class GenericBoard extends Board{
        Cell[][] cells;
        private int myWidth;
        private int myHeight;
        private String myGame;
        private int neighborType;
        private final int[] orderToReplace = {2, 1, 0};
        private double threshold = 0.3;
        private CSVParser myParser;
        private int errorStatus;
        private GridShapeType myGridShapeType;

        public GenericBoard(ResourceBundle myProperties){
            super(myProperties);
        }

        @Override
        public Cell[][] updateBoard(Rules rules) {
                Cell[][] tempCells = new Cell[myHeight][myWidth];
                for (int i = 0; i < myHeight; i++) {
                        for (int j = 0; j < myWidth; j++) {
                                tempCells[i][j] = new Cell(cells[i][j].getNextState(rules, this), j, i, myHeight, myWidth, neighborType, -1, -1, myGridShapeType);
                        }
                }
                cells = tempCells;
                return tempCells;
        }
    }
