package grid;

import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;

/**
 * An abstract class that gridViews composed of different shapes can inherit from.
 * @author Sean Hudson
 *
 */
public abstract class GridView {
    private Group myRoot = new Group();
    protected Grid myGrid;
    protected Dimension2D myGridSize;

    public GridView (Dimension2D gridSize, Grid grid) {
        this.myGridSize = gridSize;
        this.myGrid = grid;
        displayGrid();
    }

    /**
     * Display the grid
     */
    public abstract void displayGrid ();

    /**
     * Clear the root and display the grid
     */
    public void updateView () {
        myRoot.getChildren().clear();
        displayGrid();
    }

    /**
     * 
     * @return root
     */
    public Group getRoot () {
        return myRoot;
    }

    /**
     * Set the lines around the shape of the grid and set the color of each cell
     * @param shape
     * @param key (coordinate)
     */
    public void configureShape (Shape shape, Coordinate key) {
        shape.setStrokeWidth(2);
        shape.setStroke(Paint.valueOf("BLACK"));
        if (myGrid.getImmutableCellGrid().containsKey(key)) {
            shape.setFill(myGrid.getCell(key).getColor());

        }
    }

    /**
     * Add shape of cell to the root
     * @param shape
     */
    public void addCellToRoot (Shape shape) {
        myRoot.getChildren().add(shape);
    }
}