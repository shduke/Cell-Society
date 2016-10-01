package grid;

import java.util.ArrayList;
import java.util.List;
import cell.Cell;


public class NormalEdgeNeighborsHandler extends NeighborsHandler {
    public NormalEdgeNeighborsHandler (String myCellShape, Grid grid) {
        super(myCellShape, grid);
    }

    @Override
    public List<Cell> getVisionNeighbors (Coordinate coordinate) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Coordinate handleEdgeCoordinate (Coordinate coordinate, Coordinate neighborRelativeCoordinate) {
        return null;
    }

}
