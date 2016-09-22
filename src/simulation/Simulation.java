package simulation;

import cell.Cell;
import grid.Grid;
import grid.GridView;
import grid.Neighbors;
import javafx.scene.Group;
import javafx.scene.shape.Shape;


public abstract class Simulation {
    
    ///these 3 fields could be put in a gridViewController
    private Grid grid;
    private GridView gridView;
    
    public Grid getGrid() {
        return grid;
    }
    
    public void setGrid(Grid grid) {
        this.grid = grid;
    }
    
    public abstract void setNextState(Cell cell);

    public abstract void step ();

    public abstract void createNeighbors(Cell cell);

    public GridView getGridView () {
        return gridView;
    }

    public void setGridView (GridView gridView) {
        this.gridView = gridView;
    }
    
    public void addGridViewSceneGraph(Group root) {
        root.getChildren().add(gridView.getRoot());
    }
    
    // How to go from the inputed XML ShapeType to making RectangleGrid()
}
