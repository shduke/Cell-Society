package grid;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import cell.Cell;
import grid.Neighbor;


// abstract class or interface
public abstract class NeighborsHandler {
    //private String myCellShape;
    private String myNeighborsToConsider;
    private Grid myGrid;

    NeighborsHandler (String neighborsToConsider, Grid grid) {
        myNeighborsToConsider = neighborsToConsider;
        //myCellShape = cellShape.toUpperCase();
        myGrid = grid;
    }
    /*
     * public void addNeighbor (Cell cell) {
     * neighbors.add(cell);
     * }
     */

    /*
     * public Iterator<Coordinate> getNeighborCoordinates () {
     * return Collections.unmodifiableList(allowableNeighbors).iterator();
     * }
     */

    /// getSurroundingNeighbors
    /// getAdjacent
    /// get
    /*
     * public List<Coordinate> getAllowableNeighbors () {
     * return allowableNeighbors;
     * }
     */

    /*
     * public void setAllowableNeighbors (List<Coordinate> allowableNeighbors) {
     * this.allowableNeighbors = allowableNeighbors;
     * }
     */

    public List<Cell> getVisionNeighbors (Coordinate coordinate, int visionDistance) {
        List<Cell> visionNeighbors = new ArrayList<Cell>();
        PriorityQueue<Cell> visionQueue = new PriorityQueue<Cell>(getSurroundingNeighbors(coordinate));
        for(int i = 0; i < visionDistance; i++) {
            int bound = visionQueue.size();
            int index = 0;
            while(index < bound) {
                Cell cell = visionQueue.poll();
                visionNeighbors.add(cell);
                Coordinate nextVisionCoordinate = cell.getMyGridCoordinate().add(coordinate.scale(i));
                if(getMyGrid().isCreated(nextVisionCoordinate)) {
                    visionQueue.add(getMyGrid().getCell(nextVisionCoordinate));
                }
                index++;
            }
        }
        return visionNeighbors;
    }
    
    public List<Cell> getDirectionNeighbors (Coordinate coordinate,
                                             Coordinate directionCoordinate) {
        Coordinate neighborDirectionCoordinate = coordinate.add(directionCoordinate);
        List<Cell> directionNeighbors =
                getAdjacentNeighbors(coordinate, neighborDirectionCoordinate);
        directionNeighbors.retainAll(getOrthogonalNeighbors(neighborDirectionCoordinate));
        return directionNeighbors;

    }

    public List<Cell> getSurroundingNeighbors (Coordinate coordinate) {
        return getNeighbors(Neighbor.valueOf(myNeighborsToConsider).getNeighbors(),
                            coordinate);
    }

    public List<Cell> getOrthogonalNeighbors (Coordinate coordinate) {
        return getNeighbors(Neighbor.valueOf(myNeighborsToConsider + "ORTHOGONAL").getNeighbors(), coordinate);

    }

    public List<Cell> getAdjacentNeighbors (Coordinate coordinate,
                                            Coordinate directionNeighborCoordinate) {
        List<Cell> originNeighbors = getSurroundingNeighbors(coordinate);
        List<Cell> directionNeighbors = getSurroundingNeighbors(directionNeighborCoordinate);
        originNeighbors.retainAll(directionNeighbors);
        return originNeighbors;
    }

    public abstract List<Cell> getVisionNeighbors (Coordinate coordinate);

    public List<Cell> getNeighbors (List<Coordinate> allowableNeighbors,

                                    Coordinate coordinate) {
        List<Cell> neighbors = new ArrayList<Cell>();
        for (Coordinate neighborRelativeCoordinate : allowableNeighbors) {
            Coordinate neighborCoordinate = coordinate.add(neighborRelativeCoordinate);
            if (myGrid.isCreated(neighborCoordinate)) {
                neighbors.add(myGrid.getCell(neighborCoordinate));
            }
            else {
                Coordinate handledCoordinate =
                        handleEdgeCoordinate(coordinate, neighborRelativeCoordinate);
                if (handledCoordinate != null) {
                    neighbors.add(myGrid.getCell(handledCoordinate));
                }
            }
        }
        return neighbors;

    }

    public abstract Coordinate handleEdgeCoordinate (Coordinate coordinate,
                                                     Coordinate neighborRelativeCoordinate);

    // public abstract List<Cell> adjustNeighbor(List<Coordinate> );

    /*
     * public void addUncreatedNeighbor (Coordinate uncreatedNeighbors) {
     * uncreatedNeighborCoordinates.add(uncreatedNeighbors);
     * }
     */

    public Grid getMyGrid () {
        return myGrid;
    }

    public void setMyGrid (Grid myGrid) {
        this.myGrid = myGrid;
    }

}
