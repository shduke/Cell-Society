package grid;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.*;
import cell.*;
import javafx.geometry.Point2D;
import javafx.scene.Group;


public abstract class Grid {
    private Group cellGroup;
    private Cell[][] cellGrid; /// Might have to change to a Collection structure

    private int numberOfRows;
    private int numberOfColumns;

    Grid (int numberOfRows, int numberOfColumns, Collection<Cell> initCells) {// Might be a
                                                                              // collection of
                                                                              // States
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        cellGroup = new Group();
        cellGrid = new Cell[numberOfRows][numberOfColumns];
        setCellGrid(initCells); // should this go here or in an init method?
    }
    // Assumes #initCells = numberOfRows * numberOfColumns
    private void setCellGrid (Collection<Cell> initCells) {
        Iterator<Cell> iter = initCells.iterator();
        for (int r = 0; r < numberOfRows; r++) {
            for (int c = 0; c < numberOfColumns; c++) {
                getCell(r, c).setMyCurrentState(iter.next().getMyCurrentState());
            }
        }
    }

    /// Make immutable?
    public Cell[][] getCellGrid () {
        return cellGrid;
    }

    // DUPLICATE CODE: Passing in a function to apply so I don't have to use a double for loop all
    // the time
    // e.g. setting cells to next, changing current state
    // switch to iterator setup?
    public void updateGrid () {
        for (int r = 0; r < numberOfRows; r++) {
            for (int c = 0; c < numberOfColumns; c++) {
                getCell(r, c).setMyNextState(getCell(r, c).getMyNextState());
            }
        }
    }

    public int getNumRows () {
        return cellGrid.length;
    }

    public int getNumColumns () {
        return cellGrid[0].length;
    }

    // might be unnecessary
   /* public void swapCellInGrid (Cell cell) {
        int rowIndex = (int) cell.getMyCoordinate().getY();
        int columnIndex = (int) cell.getMyCoordinate().getX();
        cellGroup.getChildren().remove(getCell(rowIndex, columnIndex));
        cellGrid[rowIndex][columnIndex] = cell;
        cellGroup.getChildren().add(cell.getMyNode());
    }*/

    public Cell getCell (int rowIndex, int columnIndex) {
        return cellGrid[rowIndex][columnIndex];
    }

    public void setCellGrid (Cell[][] cellGrid) {
        this.cellGrid = cellGrid;
    }

    /**
     * TODO--return neighbors of the cell at this location
     * @param row
     * @param col
     * @return
     */
    public List<Cell> getNeighbors(Point2D gridCoordinate){
        return null;
    }
    
}
