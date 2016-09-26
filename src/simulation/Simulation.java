package simulation;

import java.util.HashMap;
import java.util.Map;
import cell.Cell;
import grid.Coordinate;
import grid.Grid;
import grid.GridView;
import grid.Neighbors;
import grid.NormalEdgeNeighbors;
import grid.RectangleGridView;
import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.scene.shape.Shape;


public abstract class Simulation {
    /// SimulationConfig
    /// GridViewConfig
    

    //private Map<String, Map<String, String>> simulationConfig;
    private Shape myCellShape;

    /// these 3 fields could be put in a gridViewController
    private Grid myGrid;
    private GridView myGridView;
    private Neighbors neighbors;

    Simulation(Map<String, Map<String, String>> simulationConfig) {
        //this.simulationConfig = simulationConfig;
        initializeSimulation(simulationConfig);
    }
    
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




    public void initializeSimulation(Map<String, Map<String, String>> simulationConfig) {
        initializeSimulationDetails(simulationConfig.get("SimulationDetails"));
        initializeGrid(simulationConfig);
        generateMap(getGrid().getNumRows(), getGrid().getNumColumns(), getGrid());
        setGridView(new RectangleGridView(new Dimension2D(Double.parseDouble(simulationConfig.get("GridConfig").get("gridWidth")), Double.parseDouble(simulationConfig.get("GridConfig").get("gridHeight"))), getGrid()));
        setNeighbors(new NormalEdgeNeighbors(getGrid()));
    }
    
    public abstract void initializeSimulationDetails(Map<String, String> simulationConfig);
    
    public void initializeGrid(Map<String, Map<String, String>> simulationConfig) {
        Map<Coordinate, Cell> cellGrid = new HashMap<Coordinate, Cell>();
        for(Map.Entry<String,String> entry : simulationConfig.get("Cells").entrySet()) {
            Cell cell = createCell(entry.getKey(), entry.getValue());
            cellGrid.put(cell.getMyGridCoordinate(), cell);
        }
        setGrid(new Grid(Integer.parseInt(simulationConfig.get("GridConfig").get("numberOfRows")), Integer.parseInt(simulationConfig.get("GridConfig").get("numberOfRows")), cellGrid));
    }
    
    public abstract Cell createCell(String stringCoordinate, String currentState);
    public abstract void generateMap(int numberOfRows,
                                     int numberOfColumns,
                                     Grid cellGrid);
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
        myGridView.updateView();

    }

    public Neighbors getNeighbors () {
        return neighbors;
    }

    public void setNeighbors (Neighbors neighbors) {
        this.neighbors = neighbors;
    }

    // How to go from the inputed XML ShapeType to making RectangleGrid()
}
