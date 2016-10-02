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
    
    public double wrapCoordinate(double value, int bound) {
        if(value < 0) {
            return value + bound;
        }
        else if (value >= bound) {
            return 0;
        }
        else {
            return value;
        }
        
    }

    @Override
    public Coordinate handleEdgeCoordinate (Coordinate coordinate,
                                      Coordinate neighborRelativeCoordinate) {
        Coordinate offGridCoordinate = coordinate.add(neighborRelativeCoordinate);
        //double x = Math.max(0, coordinate.getX() - neighborRelativeCoordinate.getX() * (getMyGrid().getNumColumns() - 1));
        double x = wrapCoordinate(offGridCoordinate.getX(), getMyGrid().getNumColumns());
        double y = wrapCoordinate(offGridCoordinate.getY(), getMyGrid().getNumRows());
        return new Coordinate(x, y);
    }

}
