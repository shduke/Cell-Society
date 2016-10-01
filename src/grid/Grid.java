package grid;

import java.util.*;
import java.util.function.Consumer;
import cell.*;


public class Grid implements Iterable<Cell> {
    private Map<Coordinate, Cell> cellGrid = new HashMap<Coordinate, Cell>();

    private int numberOfRows;
    private int numberOfColumns;

    public Grid (int numberOfRows, int numberOfColumns, Map<Coordinate, Cell> initCells) {// Might be a
                                                                              // collection of
                                                                              // states
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        this.cellGrid = initCells;
    }

    public Map<Coordinate, Cell> getCellGrid () {
        return cellGrid;
    }

    public Map<Coordinate, Cell> getImmutableCellGrid () {
        return Collections.unmodifiableMap(getCellGrid());
    }

    @Override
    public Iterator<Cell> iterator () {
        return getImmutableCellGrid().values().iterator();
    }

    /*
     * public Iterator<Entry<Coordinate, Cell>> getMutableCellGridIterator () {
     * return getCellGrid().entrySet().iterator();
     * }
     */
    //Cell:funcName();
    public void applyFuncToCell(Consumer<Cell> func) {
        getImmutableCellGrid().values().forEach(func);
    }

    //update (put in cell class)
    public void updateGrid () {
        /*int burningCount = 0;
        int treeCount = 0;
        int emptyCount = 0;*/
        for (Cell cell : getImmutableCellGrid().values()) {
            /*if(cell.getMyNextState().equals(State.BURNING)) {
                burningCount++;
            }
            if(cell.getMyNextState().equals(State.TREE)) {
                treeCount++;
            }
            if(cell.getMyNextState().equals(State.EMPTY)) {
                emptyCount++;
            }*/
            cell.setMyCurrentState(cell.getMyNextState());
            cell.setMyNextState(null);
        }
        //System.out.println("Burning:" + burningCount);
        //System.out.println("Tree: " + treeCount);
        //System.out.println("Empty: " + emptyCount);
    }

    public Boolean isCreated (Coordinate coordinate) {
        return cellGrid.containsKey(coordinate);
    }

    public Boolean isInGrid (Coordinate coordinate) {
        return (coordinate.getX() > -1 && coordinate.getX() < getNumRows() ||
                coordinate.getY() > -1 && coordinate.getY() < getNumColumns());
    }

    public Cell getCell (Coordinate coordinate) {
        return cellGrid.get(coordinate);
    }

    public void addCell (Cell cell) {
        cellGrid.put(cell.getMyGridCoordinate(), cell);
    }

    public int getNumRows () {
        return numberOfRows;
    }

    public int getNumColumns () {
        return numberOfColumns;
    }

    public void setCellGrid (Map<Coordinate, Cell> cellGrid) {
        this.cellGrid = cellGrid;
    }

    /**
     * TODO--return neighbors of the cell at this location
     * 
     * @param row
     * @param col
     * @return
     */
    //public abstract Neighbors getNeighbors (Cell cell, boolean diagonal);

    /**
     * @param cell
     * @param neighbors
     * @param x
     * @param y
     */
    /*protected void checkAdjacent (Cell cell, Neighbors neighbors, int x, int y) {
        Coordinate coord =
                new Coordinate(x + cell.getMyGridCoordinate().getX(),
                               y + cell.getMyGridCoordinate().getY());
        if (cellGrid.containsKey(coord)) {
            neighbors.addNeighbor(cellGrid.get(coord));
        }
    }*/

    // abstract public void createNeighbors (Cell cell);

}
