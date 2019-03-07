package app.model;

import java.util.ResourceBundle;

public class GenericBoard extends Board{
        Cell[][] cells;
        private int myWidth;
        private int myHeight;
        private int neighborType;
        private GridShapeType myGridShapeType;

        public GenericBoard(ResourceBundle myProperties){
            super(myProperties);
        }

        @Override
        public Cell[][] updateBoard(Rules rules) {
                Cell[][] tempCells = new Cell[myHeight][myWidth];
                for (int i = 0; i < myHeight; i++) {
                        for (int j = 0; j < myWidth; j++) {
                                tempCells[i][j] = new Cell(super.getCells()[i][j].getNextState(rules, this), j, i, myHeight, myWidth, neighborType, -1, -1, myGridShapeType);
                        }
                }
                super.setCells(tempCells);
                return tempCells;
        }
    }
