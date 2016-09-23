package simulation;

import cell.Cell;
import grid.Grid;
import grid.GridView;
import grid.Neighbors;
import javafx.scene.Group;
import javafx.scene.shape.Shape;


public abstract class Simulation {
    
    private Shape myCellShape;
    public Shape getMyCellShape () {
        return myCellShape;
    }

    ///these 3 fields could be put in a gridViewController
    private Grid myGrid;
    private GridView myGridView;
    
    public Grid getGrid() {
        return myGrid;
    }
    
    public void setGrid(Grid grid) {
        this.myGrid = grid;
    }
    
    public abstract void setNextState(Cell cell);


    public void setMyCellShape (Shape myCellShape) {
        this.myCellShape = myCellShape;
    }



    public abstract void step ();
    
    public abstract void start();
    
    public abstract void init();
    
    //public abstract void initializeCells(); 

    public abstract void createNeighbors(Cell cell);

    public GridView getGridView () {
        return myGridView;
    }

    public void setGridView (GridView gridView) {
        this.myGridView = gridView;
    }
    
    public void addGridViewSceneGraph(Group root) {
        root.getChildren().add(myGridView.getRoot());
    }

    
    // How to go from the inputed XML ShapeType to making RectangleGrid()
}
