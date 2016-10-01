package simulation;

import java.util.List;
import java.util.Map;
import java.util.Random;
import cell.Cell;
import cell.GameOfLifeCell;
import cell.State;
import grid.Coordinate;
import grid.Grid;
import grid.Neighbor;


public class GameOfLifeSimulation extends Simulation {

    public GameOfLifeSimulation (Map<String, Map<String, String>> simulationConfig) {
        super(simulationConfig);
    }

    @Override
    public void step () {
        getGrid().applyFuncToCell(p -> setNextState(p));
        updateGrid();
    }

    private int livingNeighbors (List<Cell> neighbors) {
        int count = 0;
        for (Cell cell : neighbors) {
            if (cell.getMyCurrentState().equals(State.LIVING)) {
                count++;
            }
        }
        return count;
    }

    public void setNextState (Cell cell) {
        int numberOfLivingNeighbors =
                livingNeighbors(getNeighbors().getNeighbors(Neighbor.SQUARE.getNeighbors(),
                                                            cell.getMyGridCoordinate(), getGrid()));
        if (numberOfLivingNeighbors == 3) {
            cell.setMyNextState(State.LIVING);
        }
        else if (numberOfLivingNeighbors < 2 || numberOfLivingNeighbors > 3) {
            cell.setMyNextState(State.EMPTY);
        }
        else {
            cell.setMyNextState(cell.getMyCurrentState());
        }
    }

    @Override
    public void initializeSimulationDetails (Map<String, String> simulationConfig) {

    }

    @Override
    public Cell createCell (Coordinate coordinate, String currentState) {
        GameOfLifeCell cell =
                new GameOfLifeCell(State.valueOf(currentState.toUpperCase()), coordinate);
        if (new Random().nextInt(2) == 1) {
            cell.setMyCurrentState(State.LIVING);
        }
        return cell;
    }

}
