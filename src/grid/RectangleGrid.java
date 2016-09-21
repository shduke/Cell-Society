package grid;

import java.util.Collection;
import cell.Cell;
import javafx.scene.Group;


public class RectangleGrid extends Grid {

    // This class might not be necessary, If this is just updating how the images are displayed then
    // this
    // maybe should be a view class.
    // where does it get the initial cell setup from/how are they stored?
    RectangleGrid (int numberOfRows, int numberOfColumns, Collection<Cell> initCells) {
        super(numberOfRows, numberOfColumns, initCells);

    }
}
