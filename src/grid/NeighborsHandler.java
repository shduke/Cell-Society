package grid;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
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
        List<Coordinate> directionNeighbors = Neighbor.valueOf(myNeighborsToConsider).getNeighbors();
        Set<Cell> visionNeighbors = new LinkedHashSet<Cell>();
        //List<Cell> visionNeighbors = new ArrayList<Cell>();
        for(int i = 1; i < visionDistance + 1; i++) {
            for(Coordinate direction : directionNeighbors) {
                Coordinate nextVisionCoordinate = coordinate.add(direction.scale(i));
                if(!getMyGrid().isCreated(nextVisionCoordinate)) {
                    nextVisionCoordinate =
                            handleEdgeCoordinate(coordinate, coordinate.add(direction.scale(i)));
                }
                visionNeighbors.add(getMyGrid().getCell(nextVisionCoordinate));
            }
        }
        
        /*PriorityQueue<Cell> visionQueue = new PriorityQueue<Cell>(getSurroundingNeighbors(coordinate));
        for(int i = 0; i < visionDistance; i++) {
            int bound = visionQueue.size();
            int index = 0;
            while(index < bound) {
                Cell cell = visionQueue.poll();
                visionNeighbors.add(cell);
                Coordinate nextVisionCoordinate = cell.getMyGridCoordinate().add(   coordinate.scale(i));
                //Coordinate nextVisionCoordinate = cell.getMyGridCoordinate().add(coordinate.scale(i));
                if(getMyGrid().isCreated(nextVisionCoordinate)) {
                    visionQueue.add(getMyGrid().getCell(nextVisionCoordinate));
                }
                index++;
            }
        }*/
        return new ArrayList<Cell>(visionNeighbors);
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
        List<Coordinate> blah = Neighbor.valueOf(myNeighborsToConsider).getNeighbors();
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
