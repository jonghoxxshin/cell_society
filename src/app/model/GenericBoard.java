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
                                Cell oldCell = super.getCells()[i][j];
                                tempCells[i][j] = createNewCellFromSubClass(oldCell, super.getCells()[i][j].getNextState(rules, this), j, i, super.getMyHeight(), super.getMyWidth(), super.getNeighborType(), -1, -1);
                        }
                }
                super.setCells(tempCells);
                return tempCells;
        }


    }
