package grid;

import java.util.ArrayList;
import java.util.List;
import cell.Cell;
import grid.Neighbor;


// abstract class or interface
public abstract class NeighborsHandler {
    private String myCellShape;
    private Grid myGrid;

    NeighborsHandler (String cellShape, Grid grid) {
        myCellShape = cellShape.toUpperCase();
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

    public List<Cell> getDirectionNeighbors (Coordinate coordinate,
                                             Coordinate directionCoordinate) {
        Coordinate neighborDirectionCoordinate = coordinate.add(directionCoordinate);
        List<Cell> directionNeighbors = getAdjacentNeighbors(coordinate, neighborDirectionCoordinate);
        if (!myCellShape.equals("HEXAGON") &&
            Neighbor.ORTHOGONAL.getNeighbors().contains(directionCoordinate) && getMyGrid().isCreated(neighborDirectionCoordinate)) {
            directionNeighbors.removeAll(Neighbor.ORTHOGONAL.getNeighbors());
            directionNeighbors.add(myGrid.getCell(neighborDirectionCoordinate));
        }
        return directionNeighbors;

    }

    public List<Cell> getSurroundingNeighbors (Coordinate coordinate) {
        return getNeighbors(Neighbor.valueOf(myCellShape + "SURROUNDING").getNeighbors(),
                            coordinate);
    }

    public List<Cell> getOrthogonalNeighbors (Coordinate coordinate) {
        return getNeighbors(Neighbor.valueOf(myCellShape + "ORTHOGONAL").getNeighbors(),
                            coordinate);
    }

    public List<Cell> getAdjacentNeighbors (Coordinate coordinate, Coordinate neighborCoordinate) {
        List<Cell> originNeighbors = getSurroundingNeighbors(coordinate);
        List<Cell> directionNeighbors = getSurroundingNeighbors(neighborCoordinate);
        originNeighbors.retainAll(directionNeighbors);
        System.out.println("Neighbors " + originNeighbors.size());
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
                    neighbors.add(myGrid.getCell(neighborCoordinate));
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
