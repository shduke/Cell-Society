package grid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import cell.Cell;


public class Neighbors {

    private List<Cell> neighbors = new ArrayList<Cell>();
    private List<Coordinate> uncreatedNeighborCoordinates = new ArrayList<Coordinate>();
    private int widthBound;
    private int heightBound;
    private Boolean isSurrounded;

    Neighbors (int widthBound, int heightBound) {
        this.widthBound = widthBound;
        this.heightBound = heightBound;
    }

    public void addNeighbor (Cell cell) {
        neighbors.add(cell);
    }

    public Boolean getIsSurrounded () {
        return isSurrounded;
    }

    public void setIsSurrounded (Boolean isSurrounded) {
        this.isSurrounded = isSurrounded;
    }

    public Iterator<Coordinate> getUncreatedNeighborCoordinates () {
        return Collections.unmodifiableList(uncreatedNeighborCoordinates).iterator();
    }
    public List<Cell> getNeighbors(){
        return neighbors;
    }
    public void addUncreatedNeighbor (Coordinate uncreatedNeighbors) {
        uncreatedNeighborCoordinates.add(uncreatedNeighbors);
    }

}
