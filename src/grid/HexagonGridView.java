package grid;

import javafx.geometry.Dimension2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;


public class HexagonGridView extends GridView {

    public HexagonGridView (Dimension2D gridSize, Grid grid) {
        super(gridSize, grid);
    }

    @Override
    public void displayGrid () {
        double cellHeight = (gridSize.getHeight() / (grid.getNumRows() + 0.5));
        for (int r = 0; r < grid.getNumRows(); r++) {
            for (int c = 0; c < grid.getNumColumns(); c++) {
                Hexagon gridCellDisplay = new Hexagon(cellHeight, gridSize.getWidth(), grid.getNumRows());
                double xView = c * gridCellDisplay.getStretchedEffectiveWidth() +  gridCellDisplay.getStretchedWidth() / 2;//+ (cellHeight / 2);
                double yView = r * cellHeight - ((cellHeight / 2) * (c % 2)) + cellHeight / 2;
                gridCellDisplay.setLayoutX(xView);
                gridCellDisplay.setLayoutY(yView);
                configureShape(gridCellDisplay, new Coordinate(r, c));
                addCellToRoot(gridCellDisplay);
            }
        }
    }

}

