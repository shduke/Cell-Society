package grid;

import java.util.ArrayList;
import java.util.List;
import cell.Cell;


public class NormalEdgeNeighbors extends Neighbors {

    NormalEdgeNeighbors (Grid grid) {
        super(grid);
    }

    //super class call abstract method in sub class
    @Override
    public List<Cell> getNeighbors (List<Coordinate> allowableNeighbors,
                                    Coordinate coordinate,
                                    Grid grid) {
        List<Cell> neighbors = new ArrayList<Cell>();
        for(Coordinate neighborRelativeCoordinate : allowableNeighbors) {
            Coordinate neighborCoordinate = coordinate.add(neighborRelativeCoordinate);
            if(grid.isCreated(neighborCoordinate)){ ///change that method
                neighbors.add(grid.getCell(neighborCoordinate));
            }
        }
        return neighbors;
    }
}
