package simulation;

import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import cell.AgentCell;
import cell.Cell;
import cell.EmptyCell;
import cell.FishCell;
import cell.SharkCell;
import cell.State;
import grid.Coordinate;
import grid.Grid;
import grid.GridView;
import grid.Neighbor;
import grid.Neighbors;
import grid.NormalEdgeNeighbors;
import grid.RectangleGridView;
import javafx.geometry.Dimension2D;
import java.util.*;


public class SegregationSimulation extends Simulation {

    private double myAgentSatisfiedRatio;

    public SegregationSimulation (Map<String, Map<String, String>> simulationConfig) {
        super(simulationConfig);
    }
        
        /*
        myAgentSatisfiedRatio = 0.5;
        ArrayList<Cell> input = new ArrayList<Cell>();
        for(int i = 0; i < 3; i++){
            for(int j =0; j < 3; j++){
                if(j+i%2==0){
                    input.add(new AgentCell(State.X, new Coordinate(i, j), myAgentSatisfiedRatio));
                    
                }
                else{
                    input.add(new AgentCell(State.O, new Coordinate(i, j), myAgentSatisfiedRatio));
                }
            }
        }
        //input.add(new AgentCell(State.X, new Coordinate(1, 1), myAgentSatisfiedRatio));
        for(int i = 3; i < 5; i++){
            for(int j = 3; j < 5; j++){
                input.add(new EmptyCell(new Coordinate(i, j)));
            }
        }
        
        initializeSimulation(rootElement);
        // TODO Auto-generated constructor stub
    }*/
    
    private void initializeSimulation (Element rootElement){
        this.myAgentSatisfiedRatio = Double.parseDouble(getStringValue(rootElement, "agentSatisfaction"));
        Map<Coordinate, Cell> cellGrid = new HashMap<Coordinate, Cell>();
        NodeList cells = rootElement.getElementsByTagName("cell");
        for(int i = 0; i < cells.getLength(); i++){
            Cell cell = parseCells(cells.item(i).getFirstChild().getNodeValue());
            cellGrid.put(cell.getMyGridCoordinate(), cell);
        }
        int numRows = Integer.parseInt(getStringValue(rootElement, "numberOfRows"));
        int numCols = Integer.parseInt(getStringValue(rootElement, "numberOfColumns"));
        generateMap(numRows, numCols, cellGrid);
        setGrid(new Grid(numRows, numCols, cellGrid));
        double gridWidth = Double.parseDouble(getStringValue(rootElement, "gridWidth"));
        double gridHeight = Double.parseDouble(getStringValue(rootElement, "gridHeight"));
        // TODO-grid dimensions should come from SimulationController, type of grid will be
        // determined by input as well as edge type
        setGridView(new RectangleGridView(new Dimension2D(gridWidth, gridHeight), getGrid()));
        setNeighbors(new NormalEdgeNeighbors(getGrid()));
    }
    private Cell parseCells (String cellString) {
        String[] cellData = cellString.split("_");
        State st = State.valueOf(cellData[0].toUpperCase());
        Double x = Double.parseDouble(cellData[1]);
        Double y = Double.parseDouble(cellData[2]);
        Coordinate coord = new Coordinate(x, y);
        if(st == State.EMPTY){
            return new EmptyCell(coord);
        }
        else{
            return new AgentCell(st, coord, myAgentSatisfiedRatio);
        }
    }
    public void generateMap (int numberOfRows,
                             int numberOfColumns,
                             Map<Coordinate, Cell> cellGrid) {
        for (int r = 0; r < numberOfRows; r++) {
            for (int c = 0; c < numberOfColumns; c++) {
                Coordinate coordinate = new Coordinate(r, c);
                if (!cellGrid.containsKey(coordinate)) {
                    EmptyCell cell = new EmptyCell(coordinate);
                    
                    cellGrid.put(coordinate, cell);
                }

            }
        }
    }
    private String getStringValue (Element rootElement, String name) {
        return rootElement.getElementsByTagName(name).item(0).getFirstChild().getNodeValue();
    }
    @Override
    public void step () {
        updateAgents();
        updateGrid();
        //this.getGridView().updateView();
        // TODO Auto-generated method stub

    }

    private void updateAgents () {
        Iterator<Cell> cells = getGrid().iterator();
        while (cells.hasNext()) {
            
            Cell cell = cells.next();
            if (cell.getMyCurrentState() == State.EMPTY && cell.getMyNextState() == null) {
                cell.setMyNextState(State.EMPTY);
            }
            else if(cell.getMyCurrentState() == State.X || cell.getMyCurrentState() == State.O) {
                if(cell.getMyNextState()!=State.EMPTY)
                    updateAgent((AgentCell) cell);
            }

        }
    }

    private void updateAgent (AgentCell cell) {
        if (isUnsatisfied(cell)) {
            move(cell);
        }
        else{
            cell.setMyNextState(cell.getMyCurrentState());
        }
    }

    private boolean isUnsatisfied (AgentCell cell) {
        List<Cell> neighbors = getNeighbors().getNeighbors(Neighbor.SQUARE.getNeighbors(),cell.getMyGridCoordinate());
       
        int totalNeighbors = 0;
        int friendlyNeighbors = 0;
        for (Cell c : neighbors) {
            if(c.getMyCurrentState()==State.EMPTY){
                continue;
            }
            if (c.getMyCurrentState() == cell.getMyCurrentState()) {
                friendlyNeighbors++;
            }
            totalNeighbors++;
        }
        if(totalNeighbors == 0){
            return false;
        }
        System.out.println("Num neighbors of " + cell.getMyGridCoordinate() + " is " + totalNeighbors);
        return !cell.isSatisfied((double) (friendlyNeighbors / totalNeighbors));

    }

    private void move (AgentCell cell) {
        List<Cell> openCells = new ArrayList<Cell>();
        getGrid().getCellGrid().values().stream()
                .filter(p -> (p.getMyCurrentState() == State.EMPTY &&
                              (p.getMyNextState() == State.EMPTY || p.getMyNextState() == null)))
                .collect(Collectors.toCollection( () -> openCells));
        System.out.println(openCells.size());
        if(openCells.size()==0)
            return;
        Cell moveTo = openCells.get(new Random().nextInt(openCells.size()));
        AgentCell endCell = new AgentCell(cell, moveTo.getMyGridCoordinate());
        endCell.setMyCurrentState(State.EMPTY);
        endCell.setMyNextState(cell.getMyCurrentState());
        EmptyCell startCell = new EmptyCell(cell.getMyGridCoordinate());
        startCell.setMyCurrentState(cell.getMyCurrentState());
        startCell.setMyNextState(State.EMPTY);
        System.out.println(cell.getMyGridCoordinate() + " is " + cell.getMyCurrentState());
        System.out.println(cell.getMyGridCoordinate() + " will be " + cell.getMyNextState());
        getGrid().getCellGrid().put(endCell.getMyGridCoordinate(), endCell);
        getGrid().getCellGrid().put(startCell.getMyGridCoordinate(), startCell);
    }

    @Override

    public void start () {

    }

    public void createNeighbors (Cell cell) {

        // TODO Auto-generated method stub

    }

    @Override

    public void init () {

    }

    public void setNextState (Cell cell) {

        // TODO Auto-generated method stub

    }

    @Override
    public void initializeSimulationDetails (Map<String, String> simulationConfig) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Cell createCell (String stringCoordinate, String currentState) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void generateMap (int numberOfRows, int numberOfColumns, Grid cellGrid) {
        // TODO Auto-generated method stub
        
    }

}
