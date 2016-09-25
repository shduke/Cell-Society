package simulation;

import cell.Cell;
import grid.Grid;
import grid.GridView;
import grid.Neighbors;
import javafx.scene.Group;
import javafx.scene.shape.Shape;
import xml.XMLParser;


public abstract class Simulation {
    /// SimulationConfig
    /// GridViewConfig
    private Shape myCellShape;

    /// these 3 fields could be put in a gridViewController
    private Grid myGrid;
    private GridView myGridView;
    private Neighbors neighbors;

    public Grid getGrid () {
        return myGrid;
    }

    public void setGrid (Grid grid) {
        this.myGrid = grid;
    }

    public abstract void setNextState (Cell cell);

    public Shape getMyCellShape () {
        return myCellShape;
    }

    public void setMyCellShape (Shape myCellShape) {
        this.myCellShape = myCellShape;
    }

    public abstract void step ();

    public abstract void start ();

    public abstract void init ();

    // public abstract void initializeCells();

    public GridView getGridView () {
        return myGridView;
    }

    public void setGridView (GridView gridView) {
        this.myGridView = gridView;
    }

    public void addGridViewSceneGraph (Group root) {
        root.getChildren().add(myGridView.getRoot());
    }

    public void removeGridViewSceneGraph (Group root) {
        root.getChildren().remove(myGridView.getRoot());
    }

    /// if laggy change order
    public void updateGrid () {
        myGrid.updateGrid();
        // addGridViewSceneGraph();
        myGridView.updateview();

    }

    public Neighbors getNeighbors () {
        return neighbors;
    }

    public void setNeighbors (Neighbors neighbors) {
        this.neighbors = neighbors;
    }

    // How to go from the inputed XML ShapeType to making RectangleGrid()
}
