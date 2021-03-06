package grid;

import javafx.geometry.Dimension2D;

/**
 * 
 * @author Sean Hudson
 *
 */
public class HexagonGridView extends GridView {

    public HexagonGridView (Dimension2D gridSize, Grid grid) {
        super(gridSize, grid);
    }

    @Override
    public void displayGrid () {
        double cellHeight = myGridSize.getHeight() / (myGrid.getNumRows() + 0.5);
        for (int r = 0; r < myGrid.getNumRows(); r++) {
            for (int c = 0; c < myGrid.getNumColumns(); c++) {
                Hexagon gridCellDisplay =
                        new Hexagon(cellHeight, myGridSize.getWidth(), myGrid.getNumColumns());
                double xView =
                        c * gridCellDisplay.getStretchedEffectiveWidth() +
                               gridCellDisplay.getStretchedWidth() / 2;
                double yView = r * cellHeight - ((cellHeight / 2) * (c % 2)) + cellHeight / 2;
                gridCellDisplay.setLayoutX(xView);
                gridCellDisplay.setLayoutY(yView);
                configureShape(gridCellDisplay, new Coordinate(r, c));
                addCellToRoot(gridCellDisplay);
            }
        }
    }

}
