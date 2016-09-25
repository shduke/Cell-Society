package grid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import cell.Cell;

//abstract class or interface
public abstract class Neighbors {
    //private List<Cell> neighbors = new ArrayList<Cell>();
    private Grid grid;

    Neighbors (Grid grid) {
        this.setGrid(grid);
    }

   /* public void addNeighbor (Cell cell) {
        neighbors.add(cell);
    }*/

    /*
     * public Iterator<Coordinate> getNeighborCoordinates () {
     * return Collections.unmodifiableList(allowableNeighbors).iterator();
     * }
     */

    /*public List<Coordinate> getAllowableNeighbors () {
        return allowableNeighbors;
    }*/

   /* public void setAllowableNeighbors (List<Coordinate> allowableNeighbors) {
        this.allowableNeighbors = allowableNeighbors;
    }*/

    abstract public List<Cell> getNeighbors (List<Coordinate> allowableNeighbors, Coordinate coordinate);

    public Grid getGrid () {
        return grid;
    }

    public void setGrid (Grid grid) {
        this.grid = grid;
    }
    
    
    
    /*
     * public void addUncreatedNeighbor (Coordinate uncreatedNeighbors) {
     * uncreatedNeighborCoordinates.add(uncreatedNeighbors);
     * }
     */

}
