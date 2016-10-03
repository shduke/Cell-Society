package grid;

import javafx.geometry.Dimension2D;


public class TriangleGridView extends GridView {

    public TriangleGridView (Dimension2D gridSize, Grid grid) {
        super(gridSize, grid);
    }

    @Override
    public void displayGrid () {
        double cellHeight = myGridSize.getHeight() / myGrid.getNumRows();
        for (int r = 0; r < myGrid.getNumRows(); r++) {
            for (int c = 0; c < myGrid.getNumColumns(); c++) {
                Triangle gridCellDisplay =
                        new Triangle(cellHeight, myGridSize.getWidth(), myGrid.getNumColumns());
                if (c % 2 == 1 && r % 2 == 0 || c % 2 == 0 && r % 2 == 1) {
                    gridCellDisplay.flipTriangle();
                }
                double xView =
                        c * gridCellDisplay.getStretchedEffectiveWidth() +
                               gridCellDisplay.getStretchedWidth() / 2;
                double yView = r * cellHeight + (cellHeight - gridCellDisplay.getRadius());
                gridCellDisplay.setLayoutX(xView);
                gridCellDisplay.setLayoutY(yView);
                configureShape(gridCellDisplay, new Coordinate(r, c));
                addCellToRoot(gridCellDisplay);
            }
        }
    }

}
