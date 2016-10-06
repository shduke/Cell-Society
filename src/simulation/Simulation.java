package simulation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import cell.Cell;
import cell.State;
import grid.Coordinate;
import grid.Grid;
import grid.GridView;
import grid.HexagonGridView;
import grid.NeighborsHandler;
import grid.NormalEdgeNeighborsHandler;
import grid.RectangleGridView;
import grid.ToroidalEdgeNeighborsHandler;
import grid.TriangleGridView;
import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import applicationView.SimulationGraph;
import applicationView.SimulationToolbar;

/**
 * 
 * @author Sean Hudson
 * @author Kayla Schulz
 * @author Michael Schroeder
 *
 */
public abstract class Simulation {
    private String myCellShape = "Square";
    private BorderPane myView;
    private Group myRoot;
    private SimulationToolbar mySimulationToolbar;
    private Grid myGrid;
    private GridView myGridView;
    private NeighborsHandler myNeighborsHandler;
    private State myDefaultState;
    private String myNeighborsToConsider;
    private String myEdgeType;
    private Dimension2D myGridSize;
    
    protected int stepNum;
    protected SimulationGraph mySimulationGraph;

    public Simulation (Map<String, Map<String, String>> simulationConfig) {
        initializeView();
        initializeSimulation(simulationConfig);
        getSimulationNames();
    }

    private void initializeView () {
        mySimulationGraph = new SimulationGraph();
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

    public String getCellShape () {
        return myCellShape;
    }

    public void setCellShape (String cellShape) {
        handleCellShape(cellShape);
        this.myCellShape = cellShape;
    }

    public abstract void step ();

    public void initializeSimulation (Map<String, Map<String, String>> simulationConfig) {
        initializeSimulationDetails(simulationConfig.get("SimulationConfig"));
        initializeGrid(simulationConfig);
        initializeSimulationToolbar(mySimulationToolbar);

        initializeGeneralDetails(simulationConfig.get("GeneralConfig"));
    }

    public void initializeGrid (Map<String, Map<String, String>> simulationConfig) {
        Map<Coordinate, Cell> cellGrid = new HashMap<Coordinate, Cell>();
        setGrid(new Grid(Integer
                .parseInt(simulationConfig.get("GeneralConfig").get("numberOfRows")), Integer
                        .parseInt(simulationConfig.get("GeneralConfig").get("numberOfRows")),
                         cellGrid));
        setDefaultState(getSimulationState(simulationConfig.get("GeneralConfig")
                .get("defaultState")));
        myEdgeType = simulationConfig.get("GeneralConfig").get("edgeType");
        populateGridWithSpecificValues(simulationConfig.get("Cells"));
        handleMapGeneration(simulationConfig.get("GeneralConfig"));
        generateMap();
    }

    public void populateGridWithSpecificValues (Map<String, String> cells) {
        for (Map.Entry<String, String> entry : cells.entrySet()) {
            String[] coordinateStrings = entry.getKey().split("_");
            Cell cell =
                    createCell(new Coordinate(Integer.parseInt(coordinateStrings[1]),
                                              Integer.parseInt(coordinateStrings[2])),
                               getSimulationState(entry.getValue()));
            myGrid.addCell(cell);
        }
    }

    private void initializeGeneralDetails (Map<String, String> generalConfig) {
        setGridSize(new Dimension2D(Double.parseDouble(generalConfig.get("gridWidth")),
                                    Double.parseDouble(generalConfig.get("gridHeight"))));
        setCellShape(generalConfig.get("cellShape"));
        setNeighborsToConsider(generalConfig.get("neighborsToConsider"));
        setEdgeType(generalConfig.get("edgeType"));
    }

    private void setDefaultState (State state) {
        myDefaultState = state;
    }

    public State getDefaultState () {
        return myDefaultState;
    }

    public abstract void initializeSimulationDetails (Map<String, String> simulationConfig);

    public abstract Cell createCell (Coordinate coordinate, State currentState);

    public abstract void initializeSimulationToolbar (SimulationToolbar toolbar);

    /**
     * Return the grid view
     * 
     * @return
     */

    public BorderPane getSimulationView () {
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

    public void updateGrid () {
        myGrid.updateGrid();
        myGridView.updateView();
        mySimulationGraph.updateGraph(countCellsinGrid());
    }

    public NeighborsHandler getNeighborsHandler () {
        return myNeighborsHandler;
    }

    public void setNeighborsHandler (NeighborsHandler neighbors) {
        this.myNeighborsHandler = neighbors;
    }

    /**
     * @param cell
     * @return
     */
    public List<Cell> getSquareNeighbors (Cell cell) {
        return getNeighborsHandler().getSurroundingNeighbors(cell.getMyGridCoordinate());
    }

    public void generateMap () {
        for (int r = 0; r < myGrid.getNumRows(); r++) {
            for (int c = 0; c < myGrid.getNumColumns(); c++) {
                Coordinate coordinate = new Coordinate(r, c);
                if (!myGrid.isCreated(coordinate)) {
                    myGrid.addCell(createCell(coordinate, stateGenerator()));
                }

            }
        }
    }

    public void handleCellShape (String cellShape) {
        if (cellShape.equals("Rectangle")) {
            setGridView(new RectangleGridView(myGridSize, myGrid));
        }
        else if (cellShape.equals("Triangle")) {
            setGridView(new TriangleGridView(myGridSize, myGrid));
        }
        else if (cellShape.equals("Hexagon")) {
            setGridView(new HexagonGridView(myGridSize, myGrid));
        }
    }

    public void handleEdgeType (String edgeType) {
        if (edgeType.equals("Normal")) {
            setNeighborsHandler(new NormalEdgeNeighborsHandler(myNeighborsToConsider, myGrid));
        }
        else if (edgeType.equals("Toroidal")) {
            setNeighborsHandler(new ToroidalEdgeNeighborsHandler(myNeighborsToConsider, myGrid));
        }
    }

    public abstract State[] getSimulationStates ();

    public abstract State getSimulationState (String simulationState);

    private State stateGenerator () {
        Random rn = new Random();
        double spawnRandomNumber = rn.nextDouble() * 100;
        double currentProbability = 0;
        for (State state : getSimulationStates()) {
            currentProbability += state.getProbability();
            if (spawnRandomNumber < currentProbability) {
                return state;
            }
        }
        return getDefaultState();
    }

    public void handleMapGeneration (Map<String, String> generalConfig) {
        String generationType = generalConfig.get("generationType");
        if (generationType.equals("Random")) {
            for (State state : getSimulationStates()) {
                state.setProbability(100.0 / getSimulationStates().length);
            }
        }
        else if (generationType.equals("Probability")) {
            for (State state : getSimulationStates()) {
                System.out.println(generalConfig.get(state.name() + "_Probability"));
                state.setProbability(Double
                        .parseDouble(generalConfig.get(state.name() + "_Probability")));
            }
        }
        else if (generationType.equals("Specific")) {
            for (State state : getSimulationStates()) {
                state.setProbability(0);
            }
        }

    }

    public Dimension2D getGridSize () {
        return myGridSize;
    }

    public void setGridSize (Dimension2D myGridSize) {
        this.myGridSize = myGridSize;
    }

    public String getEdgeType () {
        return myEdgeType;
    }

    public void setEdgeType (String edgeType) {
        handleEdgeType(edgeType);
        this.myEdgeType = edgeType;
    }

    public String getNeighborsToConsider () {
        return myNeighborsToConsider;
    }

    public void setNeighborsToConsider (String neighborsToConsider) {
        myNeighborsToConsider = neighborsToConsider.toUpperCase();
    }

    public SimulationGraph getGraphView () {
        return mySimulationGraph;
    }

    public Node getSimulationToolbar () {
        return mySimulationToolbar.getRoot();
    }

    public abstract void getSimulationNames ();
}
