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

    }
