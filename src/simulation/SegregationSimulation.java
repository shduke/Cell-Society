package simulation;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import cell.AgentCell;
import cell.Cell;
import cell.EmptyCell;
import cell.State;
import grid.Coordinate;
import grid.GridView;
import grid.Neighbors;
import grid.RectangleGrid;
import javafx.geometry.Dimension2D;
import java.util.*;


public class SegregationSimulation extends Simulation {

    private double myAgentSatisfiedRatio;

    public SegregationSimulation () {
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
        setGrid(new RectangleGrid(5,5,input));
        setGridView(new GridView(new Dimension2D(500, 500), "Rectangle", getGrid()));
        // TODO Auto-generated constructor stub
    }

    @Override
    public void step () {
        updateAgents();
        getGrid().updateGrid();
        this.getGridView().updateView();
        // TODO Auto-generated method stub

    }

    private void updateAgents () {
        Iterator<Map.Entry<Coordinate, Cell>> cells = getGrid().getCellGridIterator();
        while (cells.hasNext()) {
            Entry<Coordinate, Cell> entry = cells.next();
            Cell cell = entry.getValue();
            if (cell.getMyCurrentState() == State.EMPTY && cell.getMyNextState() == null) {
                cell.setMyNextState(State.EMPTY);
            }
            else {
                updateAgent((AgentCell) cell);
            }

        }
    }

    private void updateAgent (AgentCell cell) {
        if (isUnsatisfied(cell)) {
            move(cell);
        }
    }

    private boolean isUnsatisfied (AgentCell cell) {
        Neighbors neighbors = getGrid().getNeighbors(cell, true);
        List<Cell> neighborCells = neighbors.getNeighbors();
        int totalNeighbors = 0;
        int friendlyNeighbors = 0;
        for (Cell c : neighborCells) {
            if (c.getMyCurrentState() == cell.getMyCurrentState()) {
                friendlyNeighbors++;
            }
            totalNeighbors++;
        }

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

}
