package grid;

import javafx.geometry.Dimension2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;


public class TriangleGridView extends GridView {

    public TriangleGridView (Dimension2D gridSize, Grid grid) {
        super(gridSize, grid);
    }

    @Override
    public void displayGrid () {
        double cellHeight = (gridSize.getHeight() / grid.getNumRows());
        /*Triangle gridCellDisplay = new Triangle(cellHeight, gridSize.getWidth(), grid.getNumRows());
        gridCellDisplay.flipTriangle();
        double xView = 0 * gridCellDisplay.getStretchedEffectiveWidth() +  gridCellDisplay.getStretchedWidth() / 2;
        double yView = 0 * cellHeight + cellHeight / 2;
        gridCellDisplay.setLayoutX(200);
        gridCellDisplay.setLayoutY(200);
        addCellToRoot(gridCellDisplay);*/
        for (int r = 0; r < grid.getNumRows(); r++) {
            for (int c = 0; c < grid.getNumColumns(); c++) {
                Triangle gridCellDisplay = new Triangle(cellHeight, gridSize.getWidth(), grid.getNumColumns());
                if(c % 2 == 1 && r % 2 == 0 || c % 2 == 0 && r % 2 == 1) { 
                    gridCellDisplay.flipTriangle();
                }
                double xView = c * gridCellDisplay.getStretchedEffectiveWidth() +  gridCellDisplay.getStretchedWidth() / 2;
                double yView = r * cellHeight + (cellHeight - gridCellDisplay.getRadius());
                gridCellDisplay.setLayoutX(xView);
                gridCellDisplay.setLayoutY(yView);
                configureShape(gridCellDisplay, new Coordinate(r, c));
                addCellToRoot(gridCellDisplay);
            }
        }
    }

}

