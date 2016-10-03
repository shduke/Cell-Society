package simulation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cell.Cell;
import grid.Coordinate;
import grid.Grid;
import grid.GridView;
import grid.NeighborsHandler;
import grid.RectangleGridView;
import grid.ToroidalEdgeNeighborsHandler;
import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import applicationView.SimulationToolbar;


public abstract class Simulation {
    private String myCellShape = "Square";

    /// these 3 fields could be put in a gridViewController
    private BorderPane myView;
    private Group myRoot;
    private SimulationToolbar mySimulationToolbar;
    private Grid myGrid;
    private GridView myGridView;
    private NeighborsHandler myNeighborsHandler;
    private String myDefaultState;
    protected int stepNum;

    public Simulation (Map<String, Map<String, String>> simulationConfig) {
        
        initializeView();
        
        initializeSimulation(simulationConfig);
   
        myView.setCenter(myGridView.getRoot());
        myView.setRight(mySimulationToolbar.getRoot());

    }

    private void initializeView() {
        myView = new BorderPane();
        mySimulationToolbar = new SimulationToolbar();
    }
    public int getStepNum () {
        return stepNum;
    }

    public abstract List<Integer> countCellsinGrid ();

    public Grid getGrid () {
        return myGrid;
    }

    public void setGrid (Grid grid) {
        this.myGrid = grid;
    }

    public String getMyCellShape () {
        return myCellShape;
    }

    public void setMyCellShape (String myCellShape) {
        this.myCellShape = myCellShape;
    }

    public abstract void step ();

    public void initializeSimulation (Map<String, Map<String, String>> simulationConfig) {
        initializeSimulationDetails(simulationConfig.get("SimulationConfig"));
        initializeGrid(simulationConfig);
        generateMap(getGrid().getNumRows(), getGrid().getNumColumns(), getGrid());
        // temp calculations to mess with grid size
        Double myWidth = Double.parseDouble(simulationConfig.get("GeneralConfig").get("gridWidth"));
        Double myHeight =
                Double.parseDouble(simulationConfig.get("GeneralConfig").get("gridHeight"));
        myWidth = myWidth / 2;
        myHeight = myHeight / 2;
        initializeSimulationToolbar(mySimulationToolbar);
        setGridView(new RectangleGridView(new Dimension2D(myWidth, myHeight), getGrid()));
        setNeighbors(new ToroidalEdgeNeighborsHandler(myCellShape, myGrid));
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

    public abstract void initializeSimulationToolbar (SimulationToolbar toolbar);

    /**
     * Return the grid view
     * 
     * @return
     */
    public Node getSimulationView () {

        /// ****THIS ONE******
        return this.myView;
    }

    public GridView getGridView () {
        return myGridView;
    }

    public void setGridView (GridView gridView) {
        myRoot = new Group();
        this.myGridView = gridView;
        this.myRoot.getChildren().add(myGridView.getRoot());
    }

    public void addGridViewSceneGraph (Group root) {
        root.getChildren().add(myGridView.getRoot());
    }

    public void removeGridViewSceneGraph (Group root) {
        root.getChildren().clear();
    }

    /// if laggy change order
    public void updateGrid () {
        myGrid.updateGrid();
        myGridView.updateView();

    }

    public NeighborsHandler getNeighbors () {
        return myNeighborsHandler;
    }

    public void setNeighbors (NeighborsHandler neighbors) {
        this.myNeighborsHandler = neighbors;
    }

    /**
     * @param cell
     * @return
     */
    public List<Cell> getSquareNeighbors (Cell cell) {
        return getNeighbors().getSurroundingNeighbors(cell.getMyGridCoordinate());
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

    public void handleMapGeneration (String generationType) {

    }

    // How to go from the inputed XML ShapeType to making RectangleGrid()
}
