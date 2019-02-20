public class Board {
    Cell[][] cells;
    private int myWidth;
    private int myHeight;

    //Board Constructor
    public Board(int height, int width) {
        myHeight = height;
        myWidth = width;
        cells = new Cell[width][height];
        //setUpBoardFromCSV();
    }


    //Update board's cells based on current cell configuration
    private void updateBoard(Rules rules) {
        Cell[][] tempCells = new Cell[myWidth][myHeight];
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                int tempX = cell.getMyX();
                int tempY = cell.getMyY();
                Cell tempCell = new Cell(cell.getNextState(rules, this), tempX, tempY, myHeight, myWidth);
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
