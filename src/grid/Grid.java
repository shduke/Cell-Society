package grid;

import java.util.*;
import java.util.Map.Entry;
import cell.*;


public abstract class Grid {
    private Map<Coordinate, Cell> cellGrid = new HashMap<Coordinate, Cell>();

    private int numberOfRows;
    private int numberOfColumns;

    Grid (int numberOfRows, int numberOfColumns, Collection<Cell> initCells) {// Might be a collection of states
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        setUpGrid(initCells);
    }
    
    private void setUpGrid(Collection<Cell> initCells) {
        for(Cell cell : initCells) {
            addCell(cell);
        }
    }

    public Map<Coordinate, Cell> getCellGrid () {
        return cellGrid;
    }

    public Map<Coordinate, Cell> getImmutableCellGrid () {
        return Collections.unmodifiableMap(getCellGrid());
    }

    public Iterator<Entry<Coordinate, Cell>> getCellGridIterator () {
        return getImmutableCellGrid().entrySet().iterator();
    }
    
    public Iterator<Entry<Coordinate, Cell>> getMutableCellGridIterator () {
        return getCellGrid().entrySet().iterator();
    }

    public void updateGrid () {
        Iterator<Entry<Coordinate, Cell>> iterCell = getMutableCellGridIterator();
        while (iterCell.hasNext()) {
            Cell cell = iterCell.next().getValue();
            //System.out.println("Turn a " + cell.getMyCurrentState() + " into a " + cell.getMyNextState());
            if(cell.getMyCurrentState()==State.SHARK){
                System.out.println(cell.getMyGridCoordinate().getX() + ", " + cell.getMyGridCoordinate().getY());
            }
            cell.setMyCurrentState(cell.getMyNextState());
            
            cell.setMyNextState(null);
        }
    }
    
    ///How do I do this?
    /*public Cell createCell(String CellType, Coordinate coordinate) {

    }*/
    
    public Boolean isCreated (Coordinate coordinate) {
        return cellGrid.containsKey(coordinate);
        //return coordinate.hashCode() ==
        //return cellGrid.get(coordinate) != null;
    }
    
    public Boolean isInGrid (Coordinate coordinate) {
        return (coordinate.getX() > -1 && coordinate.getX() < getNumRows() ||
                coordinate.getY() > -1 && coordinate.getY() < getNumColumns());
    }
    
    public Cell getCell(Coordinate coordinate) {
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
    public abstract Neighbors getNeighbors (Cell cell, boolean diagonal);

    /**
     * @param cell
     * @param neighbors
     * @param x
     * @param y
     */
    protected void checkAdjacent (Cell cell, Neighbors neighbors, int x, int y) {
        Coordinate coord =
                new Coordinate(x + cell.getMyGridCoordinate().getX(),
                               y + cell.getMyGridCoordinate().getY());
        if (cellGrid.containsKey(coord)) {
            neighbors.addNeighbor(cellGrid.get(coord));
        }
    }

   // abstract public void createNeighbors (Cell cell);

}
