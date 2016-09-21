package grid;

import cell.*;
import javafx.geometry.Point2D;
import javafx.scene.Group;

public abstract class Grid {
    Group cellGroup;
    Cell[][] cellGrid;
    
    Grid(int numberOfRows, int numberOfColumns) {
        cellGroup = new Group();
        cellGrid = new Cell[numberOfRows][numberOfColumns];
    }
    
    public Cell[][] getCellGrid() {
        return cellGrid;
    }
    
    public int getNumRows() {
        return cellGrid.length;
    }
    
    public int getNumColumns() {
        return cellGrid[0].length;
    }
    
    public void swapCellInGrid(Cell cell) {
        int rowIndex = (int)cell.getMyCoordinate().getY();
        int columnIndex = (int)cell.getMyCoordinate().getX();
        cellGroup.getChildren().remove(getCell(rowIndex, columnIndex));
        cellGrid[rowIndex][columnIndex] = cell;
        cellGroup.getChildren().add(cell.getMyNode());     
    }
    
    public Cell getCell(int rowIndex, int columnIndex) {
        return cellGrid[rowIndex][columnIndex];
    }
    
}
