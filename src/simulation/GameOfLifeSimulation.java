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
        // TODO Auto-generated constructor stub
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

    @Override
    public void setNextState (Cell cell) {
        int numberOfLivingNeighbors =
                livingNeighbors(getNeighbors().getNeighbors(Neighbor.SQUARE.getNeighbors(),
                                                            cell.getMyGridCoordinate()));
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
        // TODO Auto-generated method stub
    }

    @Override
    public Cell createCell (String stringCoordinate, String currentState) {
        String[] coordinateData = stringCoordinate.split("_");
        return new GameOfLifeCell(State.valueOf(currentState.toUpperCase()),
                                  new Coordinate(Double.parseDouble(coordinateData[0]),
                                                 Double.parseDouble(coordinateData[1])));
    }

    @Override
    public void generateMap (int numberOfRows, int numberOfColumns, Grid cellGrid) {
        Random rn = new Random();
        for (int r = 0; r < numberOfRows; r++) {
            for (int c = 0; c < numberOfColumns; c++) {
                Coordinate coordinate = new Coordinate(r, c);
                if (!cellGrid.isCreated(coordinate)) {
                    GameOfLifeCell cell = new GameOfLifeCell(State.EMPTY, coordinate);
                    if (rn.nextInt(2) == 1) {
                        cell.setMyCurrentState(State.LIVING);
                    }
                    cellGrid.addCell(cell);
                }

            }
        }

    }

}
