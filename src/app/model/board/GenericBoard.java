package app.model.board;

import app.model.cell.Cell;
import app.model.rules.Rules;

import java.util.ResourceBundle;

/**
 * Generic Board Class
 * Used for Game of Life, Percolation, and Rock Paper Scissors
 * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
 */
public class GenericBoard extends Board {

        /**
         * Generic board constructor
         *
         * @param myProperties
         * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
         */
        public GenericBoard(ResourceBundle myProperties){
            super(myProperties);
        }

        /**
         * Update Board
         * <p>
         *     Apply rules to each cell updating each cell in a temp board
         *     and then updating the current boards cell boar to be the temp board
         * </p>
         *
         * @param rules
         * @return cells
         * @author Kyle Harvey, Jaiveer Katariya, Jognho Shin
         */
        @Override
        public Cell[][] updateBoard(Rules rules) {
                Cell[][] tempCells = new Cell[super.getMyHeight()][super.getMyWidth()];
                for (int i = 0; i < super.getMyHeight(); i++) {
                        for (int j = 0; j < super.getMyWidth(); j++) {
                                Cell oldCell = super.getCells()[i][j];
                                tempCells[i][j] = createNewCellFromSubClass(oldCell, super.getCells()[i][j].getNextState(rules, this), j, i, super.getMyHeight(), super.getMyWidth(), super.getNeighborType(), -1, -1, super.getEdgeType());
                        }
                }
                super.setCells(tempCells);
                return tempCells;
        }


    }
