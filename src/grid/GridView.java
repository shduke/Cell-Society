package grid;

import cell.State;
import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;

public abstract class GridView {
    Group root = new Group();
    Dimension2D gridSize;
    Grid grid;
//change to take in Grid Config object
    public GridView (Dimension2D gridSize, Grid grid) {
        this.gridSize = gridSize;
        this.grid = grid;
        displayGrid();
    }
    
    public abstract void displayGrid();
    
    public void updateView(){
        //root = new Group();
        root.getChildren().clear();
        displayGrid();  
    }
    
    public Group getRoot() {
        return root;
    }
    
    public void configureShape(Shape shape, Coordinate key) {
        shape.setStrokeWidth(2);
        shape.setStroke(Paint.valueOf("BLACK"));
        if(grid.getImmutableCellGrid().containsKey(key)) {
            shape.setFill(grid.getCell(key).getMyCurrentState().getColor());
            
        } else{
            shape.setFill(State.EMPTY.getColor()); //Need to hook up default somehow
        }
    }
    
    public void addCellToRoot(Shape shape) {
        root.getChildren().add(shape);
    }
}
