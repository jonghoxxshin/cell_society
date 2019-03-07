package app.model;

import java.util.ResourceBundle;

public class GenericBoard extends Board{

        public GenericBoard(ResourceBundle myProperties){
            super(myProperties);
        }

        @Override
        public Cell[][] updateBoard(Rules rules) {
                Cell[][] tempCells = new Cell[super.getMyHeight()][super.getMyWidth()];
                for (int i = 0; i < super.getMyHeight(); i++) {
                        for (int j = 0; j < super.getMyWidth(); j++) {
                                tempCells[i][j] = new Cell(super.getCells()[i][j].getNextState(rules, this), j, i, super.getMyHeight(), super.getMyWidth(), super.getNeighborType(), -1, -1, super.getMyGridShapeType());
                        }
                }
                super.setCells(tempCells);
                return tempCells;
        }
    }
