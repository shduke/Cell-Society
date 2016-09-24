package grid;

import cell.Cell;
import cell.State;
import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.*;
import java.util.*;

public abstract class GridView {
    Group root = new Group();
    Dimension2D gridSize;
    Grid grid;

    public GridView (Dimension2D gridSize, Grid grid) {
        this.gridSize = gridSize;
        this.grid = grid;
    }
    
    public abstract void displayGrid();
    
    public void updateView() {
        double cellWidth = gridSize.getWidth() / grid.getNumRows();
        double cellHeight = gridSize.getHeight() / grid.getNumRows();
        for(Node node : root.getChildren()){
            Rectangle shape = (Rectangle) node;
            int r = (int)(shape.getX()/cellWidth);
            int c = (int)(shape.getY()/cellHeight);
            configureShape(shape, new Coordinate(r, c));
            
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
