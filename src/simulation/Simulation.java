package simulation;

import grid.Grid;
import javafx.scene.shape.Shape;


public abstract class Simulation {
    
    private Shape myCellShape;
    public Shape getMyCellShape () {
        return myCellShape;
    }

    public void setMyCellShape (Shape myCellShape) {
        this.myCellShape = myCellShape;
    }

    private Grid myGrid;

    public abstract void step ();
    
    public abstract void start();
    
    public abstract void init();
    
    //public abstract void initializeCells(); 
    
    // How to go from the inputed XML ShapeType to making RectangleGrid()
}
