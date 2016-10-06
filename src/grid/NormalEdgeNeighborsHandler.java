package grid;

/**
 * 
 * @author Sean Hudson
 *
 */
public class NormalEdgeNeighborsHandler extends NeighborsHandler {
    public NormalEdgeNeighborsHandler (String myCellShape, Grid grid) {
        super(myCellShape, grid);
    }

    @Override
    public Coordinate handleEdgeCoordinate (Coordinate coordinate,
                                            Coordinate neighborRelativeCoordinate) {
        return null;
    }

}
