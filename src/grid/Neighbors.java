package grid;

import java.util.List;
import cell.Cell;

//abstract class or interface
public interface Neighbors {

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

    abstract public List<Cell> getNeighbors (List<Coordinate> allowableNeighbors, Coordinate coordinate, Grid grid);
    
    
    
    /*
     * public void addUncreatedNeighbor (Coordinate uncreatedNeighbors) {
     * uncreatedNeighborCoordinates.add(uncreatedNeighbors);
     * }
     */

}
