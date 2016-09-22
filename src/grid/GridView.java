package grid;

import cell.Cell;
import cell.State;
import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class GridView {
    Group root = new Group();
    String gridCellShape;
    Dimension2D gridSize;
    Grid grid;

    public GridView (Dimension2D gridSize, String gridCellShape, Grid grid) {
        this.gridSize = gridSize;
        this.gridCellShape = gridCellShape;
        this.grid = grid;
        displaySquareGrid(gridSize, grid);
    }
    
    public void displaySquareGrid (Dimension2D gridSize, Grid grid) {
        double cellWidth = gridSize.getWidth() / grid.getNumRows();
        double cellHeight = gridSize.getHeight() / grid.getNumRows();
        for (int r = 0; r < grid.getNumRows(); r++) {
            for (int c = 0; c < grid.getNumColumns(); c++) {
                Double xView = r * cellWidth;
                Double yView = c * cellHeight;
                Rectangle gridCellDisplay = new Rectangle(cellWidth, cellHeight);
                gridCellDisplay.setX(xView);
                gridCellDisplay.setY(yView);
                //System.out.println(gridCellDisplay.getX() + " " + gridCellDisplay.getY() + " " + cellWidth + " " + cellHeight);
                configureShape(gridCellDisplay, new Coordinate(r, c));
                addCellToRoot(gridCellDisplay);

            }
        }
    }
    
    public Group getRoot() {
        return root;
    }
    
    private void configureShape(Shape shape, Coordinate key) {
        shape.setStrokeWidth(2);
        shape.setStroke(Paint.valueOf("BLACK"));
        if(grid.getImmutableCellGrid().containsKey(key)) {
            shape.setFill(grid.getCell(key).getMyCurrentState().getColor());
            
        } else{
            shape.setFill(State.EMPTY.getColor()); //Need to hook up default somehow
        }
    }
    
    private void addCellToRoot(Shape shape) {
        root.getChildren().add(shape);
    }
}
