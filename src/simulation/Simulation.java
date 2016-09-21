package simulation;

import grid.Grid;
import javafx.scene.shape.Shape;


public abstract class Simulation {
    private Shape simulationShape;
    private Grid grid;

    public abstract void step ();

    // How to go from the inputed XML ShapeType to making RectangleGrid()
}
