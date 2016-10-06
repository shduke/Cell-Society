package grid;

import java.util.*;
import java.util.function.Consumer;
import cell.*;

/**
 * 
 * @author Sean Hudson
 *
 */
public class Grid implements Iterable<Cell> {
    private Map<Coordinate, Cell> myCellGrid = new HashMap<Coordinate, Cell>();

    private int myNumberOfRows;
    private int myNumberOfColumns;

    public Grid (int numberOfRows, int numberOfColumns, Map<Coordinate, Cell> initCells) {
        this.myNumberOfRows = numberOfRows;
        this.myNumberOfColumns = numberOfColumns;
        this.myCellGrid = initCells;
    }

    public Map<Coordinate, Cell> getCellGrid () {
        return myCellGrid;
    }

    public Map<Coordinate, Cell> getImmutableCellGrid () {
        return Collections.unmodifiableMap(getCellGrid());
    }

    @Override
    public Iterator<Cell> iterator () {
        return getImmutableCellGrid().values().iterator();
    }

    public void applyFuncToCell (Consumer<Cell> func) {
        getImmutableCellGrid().values().forEach(func);
    }

    // update (put in cell class)
    public void updateGrid () {
        for (Cell cell : getImmutableCellGrid().values()) {
            cell.setMyCurrentState(cell.getMyNextState());
            cell.setMyNextState(null);
        }
    }

    public Boolean isCreated (Coordinate coordinate) {
        return myCellGrid.containsKey(coordinate);
    }

    public Boolean isInGrid (Coordinate coordinate) {
        return coordinate.getX() > -1 && coordinate.getX() < getNumRows() ||
               coordinate.getY() > -1 && coordinate.getY() < getNumColumns();
    }

    public Cell getCell (Coordinate coordinate) {
        return myCellGrid.get(coordinate);
    }

    public void addCell (Cell cell) {
        myCellGrid.put(cell.getMyGridCoordinate(), cell);
    }

    public int getNumRows () {
        return myNumberOfRows;
    }

    public int getNumColumns () {
        return myNumberOfColumns;
    }

    public void setCellGrid (Map<Coordinate, Cell> cellGrid) {
        this.myCellGrid = cellGrid;
    }

    /**
     * TODO--return neighbors of the cell at this location
     * 
     * @param row
     * @param col
     * @return
     */
    // public abstract Neighbors getNeighbors (Cell cell, boolean diagonal);

    /**
     * @param cell
     * @param neighbors
     * @param x
     * @param y
     */
    /*
     * protected void checkAdjacent (Cell cell, Neighbors neighbors, int x, int y) {
     * Coordinate coord =
     * new Coordinate(x + cell.getMyGridCoordinate().getX(),
     * y + cell.getMyGridCoordinate().getY());
     * if (cellGrid.containsKey(coord)) {
     * neighbors.addNeighbor(cellGrid.get(coord));
     * }
     * }
     */

    // abstract public void createNeighbors (Cell cell);

}
