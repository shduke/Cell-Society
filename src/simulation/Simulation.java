package simulation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cell.Cell;
import grid.Coordinate;
import grid.Grid;
import grid.GridView;
import grid.HexagonGridView;
import grid.Neighbor;
import grid.Neighbors;
import grid.NormalEdgeNeighbors;
import grid.RectangleGridView;
import grid.TriangleGridView;
import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.scene.shape.Shape;


public abstract class Simulation {
    private Shape myCellShape;

    /// these 3 fields could be put in a gridViewController
    private Grid myGrid;
    private GridView myGridView;
    private Neighbors neighbors;
    private String myDefaultState;

    Simulation (Map<String, Map<String, String>> simulationConfig) {
        initializeSimulation(simulationConfig);
    }

    public Grid getGrid () {
        return myGrid;
    }

    public void setGrid (Grid grid) {
        this.myGrid = grid;
    }

    public Shape getMyCellShape () {
        return myCellShape;
    }

    public void setMyCellShape (Shape myCellShape) {
        this.myCellShape = myCellShape;
    }

    public abstract void step ();

    public void initializeSimulation (Map<String, Map<String, String>> simulationConfig) {
        initializeSimulationDetails(simulationConfig.get("SimulationConfig"));
        initializeGrid(simulationConfig);
        generateMap(getGrid().getNumRows(), getGrid().getNumColumns(), getGrid());
        /*
         * setGridView(new RectangleGridView(new Dimension2D(Double
         * .parseDouble(simulationConfig.get("GeneralConfig").get("gridWidth")), Double
         * .parseDouble(simulationConfig.get("GeneralConfig").get("gridHeight"))),
         * getGrid()));
         */
        setGridView(new TriangleGridView(new Dimension2D(Double
                .parseDouble(simulationConfig.get("GeneralConfig").get("gridWidth")), Double
                        .parseDouble(simulationConfig.get("GeneralConfig").get("gridHeight"))),
                                         getGrid()));
        setNeighbors(new NormalEdgeNeighbors());
    }

    public void initializeGrid (Map<String, Map<String, String>> simulationConfig) {
        Map<Coordinate, Cell> cellGrid = new HashMap<Coordinate, Cell>();
        setGrid(new Grid(Integer
                .parseInt(simulationConfig.get("GeneralConfig").get("numberOfRows")), Integer
                        .parseInt(simulationConfig.get("GeneralConfig").get("numberOfRows")),
                         cellGrid));
        for (Map.Entry<String, String> entry : simulationConfig.get("Cells").entrySet()) {
            String[] coordinateStrings = entry.getKey().split("_");
            Cell cell =
                    createCell(new Coordinate(Integer.parseInt(coordinateStrings[1]),
                                              Integer.parseInt(coordinateStrings[2])),
                               entry.getValue());
            cellGrid.put(cell.getMyGridCoordinate(), cell);
        }

        setDefaultState(simulationConfig.get("SimulationConfig").get("default"));
    }

    private void setDefaultState (String state) {
        myDefaultState = state;
    }

    public abstract void initializeSimulationDetails (Map<String, String> simulationConfig);

    public abstract Cell createCell (Coordinate coordinate, String currentState);

    /*
     * public abstract void generateMap (int numberOfRows,
     * int numberOfColumns,
     * Grid cellGrid);
     */

    /** Return the grid view
     * 
     * @return
     */
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
        root.getChildren().clear();
        //myGridView.getRoot().getChildren().clear();
        //root.getChildren().removeAll(myGridView.getRoot().getChildren());
        //root.getChildren().remove(myGridView.getRoot());
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

    /**
     * @param cell
     * @return
     */
    public List<Cell> getSquareNeighbors (Cell cell) {
        return getNeighbors().getNeighbors(Neighbor.SQUARE.getNeighbors(),
                                           cell.getMyGridCoordinate(), getGrid());
    }

    public void generateMap (int numberOfRows, int numberOfColumns, Grid cellGrid) {
        for (int r = 0; r < numberOfRows; r++) {
            for (int c = 0; c < numberOfColumns; c++) {
                Coordinate coordinate = new Coordinate(r, c);
                if (!cellGrid.isCreated(coordinate)) {
                    cellGrid.addCell(createCell(coordinate, myDefaultState));
                }

            }
        }
    }

    // How to go from the inputed XML ShapeType to making RectangleGrid()
}
