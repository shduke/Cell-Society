package grid;

import javafx.geometry.Dimension2D;
import javafx.scene.shape.Rectangle;


public class RectangleGridView extends GridView {

    public RectangleGridView (Dimension2D gridSize, Grid grid) {
        super(gridSize, grid);
    }

    @Override
    public void displayGrid () {
        double cellWidth = myGridSize.getWidth() / myGrid.getNumRows();
        double cellHeight = myGridSize.getHeight() / myGrid.getNumRows();
        for (int r = 0; r < myGrid.getNumRows(); r++) {
            for (int c = 0; c < myGrid.getNumColumns(); c++) {
                Double xView = r * cellWidth;
                Double yView = c * cellHeight;
                Rectangle gridCellDisplay = new Rectangle(cellWidth, cellHeight);
                gridCellDisplay.setX(xView);
                gridCellDisplay.setY(yView);
                configureShape(gridCellDisplay, new Coordinate(r, c));
                addCellToRoot(gridCellDisplay);
            }
        }
    }

}
