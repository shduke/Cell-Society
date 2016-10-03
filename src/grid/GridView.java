package grid;

import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;


public abstract class GridView {
    private Group myRoot = new Group();
    protected Grid myGrid;
    protected Dimension2D myGridSize;

    public GridView (Dimension2D gridSize, Grid grid) {
        this.myGridSize = gridSize;
        this.myGrid = grid;
        displayGrid();
    }

    public abstract void displayGrid ();

    public void updateView () {
        // root = new Group();
        myRoot.getChildren().clear();
        displayGrid();
    }

    public Group getRoot () {
        return myRoot;
    }

    public void configureShape (Shape shape, Coordinate key) {
        shape.setStrokeWidth(2);
        shape.setStroke(Paint.valueOf("BLACK"));
        if (myGrid.getImmutableCellGrid().containsKey(key)) {
            shape.setFill(myGrid.getCell(key).getColor());

        } /*
           * else{
           * shape.setFill(State.EMPTY.getColor()); //Need to hook up default somehow
           * }
           */
    }

    public void addCellToRoot (Shape shape) {
        // System.out.println(root.getChildren().size());
        myRoot.getChildren().add(shape);
    }
}
