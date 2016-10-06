package grid;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import cell.Cell;


// abstract class or interface
public abstract class NeighborsHandler {
    // private String myCellShape;
    private String myNeighborsToConsider;
    private Grid myGrid;

    NeighborsHandler (String neighborsToConsider, Grid grid) {
        myNeighborsToConsider = neighborsToConsider;
        myGrid = grid;
    }

    public List<Cell> getVisionNeighbors (Coordinate coordinate, int visionDistance) {
        List<Coordinate> directionNeighbors =
                Neighbor.valueOf(myNeighborsToConsider).getNeighbors();
        Set<Cell> visionNeighbors = new LinkedHashSet<Cell>();
        for (int i = 1; i < visionDistance + 1; i++) {
            for (Coordinate direction : directionNeighbors) {
                Coordinate nextVisionCoordinate = coordinate.add(direction.scale(i));
                if (!getMyGrid().isCreated(nextVisionCoordinate)) {
                    nextVisionCoordinate =
                            handleEdgeCoordinate(coordinate, coordinate.add(direction.scale(i)));
                }
                if (nextVisionCoordinate != null) {
                    visionNeighbors.add(getMyGrid().getCell(nextVisionCoordinate));
                }
            }
        }

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
        return getNeighbors(Neighbor.valueOf(myNeighborsToConsider).getNeighbors(), coordinate);
    }

    public List<Cell> getOrthogonalNeighbors (Coordinate coordinate) {

        return getNeighbors(Neighbor.valueOf(myNeighborsToConsider + "ORTHOGONAL").getNeighbors(),
                            coordinate);

    }

    public List<Cell> getAdjacentNeighbors (Coordinate coordinate,
                                            Coordinate directionNeighborCoordinate) {
        List<Cell> originNeighbors = getSurroundingNeighbors(coordinate);
        List<Cell> directionNeighbors = getSurroundingNeighbors(directionNeighborCoordinate);
        originNeighbors.retainAll(directionNeighbors);
        return originNeighbors;
    }

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

    public Grid getMyGrid () {
        return myGrid;
    }

    public void setMyGrid (Grid grid) {
        this.myGrid = grid;
    }
}
