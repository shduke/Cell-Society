package grid;

import javafx.geometry.Dimension2D;
import javafx.scene.shape.Rectangle;


public class RectangleGridView extends GridView {

    public RectangleGridView (Dimension2D gridSize, Grid grid) {
        super(gridSize, grid);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void displayGrid () {
        double cellWidth = gridSize.getWidth() / grid.getNumRows();
        double cellHeight = gridSize.getHeight() / grid.getNumRows();
        for (int r = 0; r < grid.getNumRows(); r++) {
            for (int c = 0; c < grid.getNumColumns(); c++) {
                Double xView = r * cellWidth;
                Double yView = c * cellHeight;
                Rectangle gridCellDisplay = new Rectangle(cellWidth, cellHeight);
                gridCellDisplay.setX(xView);
                gridCellDisplay.setY(yView);
                // System.out.println(gridCellDisplay.getX() + " " + gridCellDisplay.getY() + " " +
                // cellWidth + " " + cellHeight);
                configureShape(gridCellDisplay, new Coordinate(r, c));
                addCellToRoot(gridCellDisplay);

            }
        }
    }

}
