public class Board {
    Cell[][] cells;
    private int myWidth;
    private int myHeight;

    //Board Constructor
    public Board(int height, int width) {
        myHeight = height;
        myWidth = width;
        cells = new Cell[width][height];
        setUpBoardFromCSV();
    }

    //add cell to each cell in cells
    private void setUpBoardFromCSV() {
        for (int i = 0; i < myHeight; i++) {
            for (int j = 0; j < myWidth; j++) {
                cells[j][i] = makeCellFromCSV(i,j);
            }
        }
        cells = cells;
    }

    //create new cell to be returned, based on config in CSV file
    private Cell makeCellFromCSV(int x, int y) {
        //Need to implement creation of new cell based on configuration in CSV file
        return new Cell (0,x,y);
    }

    //Update board's cells based on current cell configuration
    private void updateBoard(Rules rules) {
        Cell[][] tempCells = new Cell[myWidth][myHeight];
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                int tempX = cell.getMyX();
                int tempY = cell.getMyY();
                Cell tempCell = new Cell(cell.getNextState(rules, this), tempX, tempY);
                tempCells[tempX][tempY] = tempCell;
            }
        }
        cells = tempCells;
    }

    //get cells array
    public Cell[][] getCells(){
        return cells;
    }


}
