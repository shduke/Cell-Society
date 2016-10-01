package grid;

import java.util.List;
import cell.Cell;

public class ToroidalEdgeNeighborsHandler extends NeighborsHandler{

    public ToroidalEdgeNeighborsHandler (String myCellShape, Grid grid) {
        super(myCellShape, grid);
    }

    @Override
    public List<Cell> getVisionNeighbors (Coordinate coordinate) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Coordinate handleEdgeCoordinate (Coordinate coordinate,
                                      Coordinate neighborRelativeCoordinate) {
        double x = coordinate.getX() - neighborRelativeCoordinate.getX() * (getMyGrid().getNumColumns() - 1);
        double y = coordinate.getY() - neighborRelativeCoordinate.getY() * (getMyGrid().getNumColumns() - 1);
        return new Coordinate(x, y);
    }

}
