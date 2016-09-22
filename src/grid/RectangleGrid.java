package grid;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;
import cell.Cell;
import javafx.scene.Group;


public class RectangleGrid extends Grid {

    // This class might not be necessary, If this is just updating how the images are displayed then
    // this
    // maybe should be a view class.
    // where does it get the initial cell setup from/how are they stored?
    public RectangleGrid (int numberOfRows, int numberOfColumns, Collection<Cell> initCells) {
        super(numberOfRows, numberOfColumns, initCells);

    }

    // How to reduce if statements
    // using enums with Square neighbor? TOP_LEFT... relating the Cell and (-1, 1). Want to put this in Neighbors
    @Override
    public Neighbors getNeighbors (Cell cell) {
        Neighbors neighbors = new Neighbors(getNumRows(), getNumColumns());
        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                Coordinate coordinate = new Coordinate(x + cell.getMyGridCoordinate().getX(),
                                                       y + cell.getMyGridCoordinate().getY());
                boolean istest = isCreated(coordinate);
                boolean zerotest = !(x == 0 && y == 0);
                Cell test = getImmutableCellGrid().get(coordinate);
                if (isCreated(coordinate) && !(x == 0 && y == 0)) {
                    Cell cellNeighbor =
                            getCell(new Coordinate(x + cell.getMyGridCoordinate().getX(),
                                                   y + cell.getMyGridCoordinate().getY()));
                    neighbors.addNeighbor(cellNeighbor);
                }
                else {
                    if (isInGrid(coordinate) && !(x == 0 && y == 0)) {
                        neighbors.addUncreatedNeighbor(coordinate);
                    }
                }
            }
        }
        return neighbors;
    }

    /*@Override 
    public void createNeighbors (Cell cell) {
        Neighbors neighbors = getNeighbors(cell);
        Iterator<Coordinate> iterNewCell = neighbors.getUncreatedNeighborCoordinates();
        while (iterNewCell.hasNext()) {
            Coordinate coordinate = iterNewCell.next();
            addCell(cell);
        }
    }*/

}
