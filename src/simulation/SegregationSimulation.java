package simulation;

import java.util.stream.Collectors;
import cell.AgentCell;
import cell.Cell;
import cell.EmptyCell;
import cell.State;
import grid.Coordinate;
import grid.Grid;
import grid.Neighbor;
import java.util.*;


public class SegregationSimulation extends Simulation {

    private double myAgentSatisfiedRatio;

    public SegregationSimulation (Map<String, Map<String, String>> simulationConfig) {
        super(simulationConfig);
        generateMap(getGrid().getNumRows(), getGrid().getNumColumns(), getGrid());
    }
    

    @Override
    public void step () {
        updateAgents();
        updateGrid();
        // this.getGridView().updateView();
        // TODO Auto-generated method stub

    }

    private void updateAgents () {
        Iterator<Cell> cells = getGrid().iterator();
        while (cells.hasNext()) {

            Cell cell = cells.next();
            if (cell.getMyCurrentState() == State.EMPTY && cell.getMyNextState() == null) {
                cell.setMyNextState(State.EMPTY);
            }
            else if (cell.getMyCurrentState() == State.X || cell.getMyCurrentState() == State.O) {
                if (cell.getMyNextState() != State.EMPTY)
                    updateAgent((AgentCell) cell);
            }

        }
    }

    private void updateAgent (AgentCell cell) {
        if (isUnsatisfied(cell)) {
            move(cell);
        }
        else {
            cell.setMyNextState(cell.getMyCurrentState());
        }
    }

    private boolean isUnsatisfied (AgentCell cell) {
        List<Cell> neighbors =
                getNeighbors().getNeighbors(Neighbor.SQUARE.getNeighbors(),
                                            cell.getMyGridCoordinate(), getGrid());

        int totalNeighbors = 0;
        int friendlyNeighbors = 0;
        for (Cell c : neighbors) {
            if (c.getMyCurrentState() == State.EMPTY) {
                continue;
            }
            if (c.getMyCurrentState() == cell.getMyCurrentState()) {
                friendlyNeighbors++;
            }
            totalNeighbors++;
        }
        if (totalNeighbors == 0) {
            return false;
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
        if (openCells.size() == 0)
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
    public void initializeSimulationDetails (Map<String, String> simulationConfig) {
        this.myAgentSatisfiedRatio =
                Double.parseDouble(simulationConfig.get("myAgentSatisfiedRatio"));
    }

    @Override
    public Cell createCell (Coordinate coordinate, String currentState) {
        State state = State.valueOf(currentState.toUpperCase());
        if (state == State.EMPTY) {
            return new EmptyCell(coordinate);
        }
        else {
            return new AgentCell(state, coordinate, myAgentSatisfiedRatio);
        }

    }

}
