package simulation;

import grid.Grid;
import grid.GridView;
import javafx.geometry.Dimension2D;
import javafx.scene.shape.Shape;


public abstract class Simulation {
    
    ///these 3 fields could be put in a gridViewController
    private Grid grid;
    private String cellShapeType;
    private GridView gridView;
    private Dimension2D gridViewSize;

    Simulation(Dimension2D gridViewSize) {
        this.gridViewSize = gridViewSize;
        this.gridView = new GridView(gridViewSize, cellShapeType, grid);
    }
    
    public abstract void step ();

    // How to go from the inputed XML ShapeType to making RectangleGrid()
}
