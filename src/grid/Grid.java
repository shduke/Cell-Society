package grid;

import java.util.*;
import java.util.function.Consumer;
import cell.*;


/**
 * The generic grid class that iterates through the cells
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

    /**
     * 
     * @return grid of cells
     */
    public Map<Coordinate, Cell> getCellGrid () {
        return myCellGrid;
    }

    /**
     * 
     * @return immutable grid of cells
     */
    public Map<Coordinate, Cell> getImmutableCellGrid () {
        return Collections.unmodifiableMap(getCellGrid());
    }

    @Override
    public Iterator<Cell> iterator () {
        return getImmutableCellGrid().values().iterator();
    }

    /**
     * Applies particular function to cell
     * 
     * @param func
     */
    public void applyFuncToCell (Consumer<Cell> func) {
        getImmutableCellGrid().values().forEach(func);
    }

    /**
     * Iterates through each cell and sets the next state, effectively
     * updating the grid
     */
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

    /**
     * add cell to the grid of cells
     * 
     * @param cell
     */
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
}
