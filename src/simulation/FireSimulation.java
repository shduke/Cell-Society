package simulation;

import java.util.Map;
import java.util.Random;
import cell.Cell;
import cell.FireCell;
import cell.State;
import grid.Coordinate;
import grid.Grid;
import grid.Neighbor;


public class FireSimulation extends Simulation {
    private double probCatch;
    private int burnTime;

    public FireSimulation (Map<String, Map<String, String>> simulationConfig) {
        super(simulationConfig);
    }

    // very temporary code, just here to make it work
    @Override
    public void step () {
        getGrid().applyFuncToCell(p -> setNextState(p));
        updateGrid();
    }

    public boolean hasBurningNeighbor (Cell cell) {
        for (Cell neighborCell : getNeighbors()
                .getSurroundingNeighbors(cell.getMyGridCoordinate())) {
            if (neighborCell.getMyCurrentState().equals(State.BURNING)) {
                return true;
            }
        }
        return false;
    }

    // is switching on cell state bad?
    public void setNextState (Cell cell) {
        // System.out.println(cell.getMyCurrentState());
        if (cell.getMyCurrentState().equals(State.TREE)) {
            cell.setMyNextState(State.TREE);
            Random rn = new Random();
            if (hasBurningNeighbor(cell) && rn.nextInt(100) < probCatch) {
                cell.setMyNextState(State.BURNING);
                ((FireCell) cell).setBurnTimer(burnTime);
            }
        }
        else if (cell.getMyCurrentState().equals(State.BURNING)) {
            ((FireCell) cell).decrementBurnTimer();
            cell.setMyNextState(State.BURNING);
            if (((FireCell) cell).getBurnTimer() == 0) {
                cell.setMyNextState(State.EMPTY);
            }
        }
        else {
            cell.setMyNextState(State.EMPTY);
        }
    }

    @Override
    public void initializeSimulationDetails (Map<String, String> simulationConfig) {
        this.probCatch = Double.parseDouble(simulationConfig.get("probCatch"));
        this.burnTime = Integer.parseInt(simulationConfig.get("burnTime"));
    }

    @Override
    public Cell createCell (Coordinate coordinate, String currentState) {
        FireCell cell = new FireCell(State.valueOf(currentState.toUpperCase()), coordinate);
        int r = (int) coordinate.getX();
        int c = (int) coordinate.getY();
        if (r == 0 || c == 0 || r == (getGrid().getNumRows() - 1) ||
            c == (getGrid().getNumColumns() - 1)) {
            cell.setMyCurrentState(State.EMPTY);
        }
        cell.setBurnTimer(burnTime);
        return cell;
    }

}
