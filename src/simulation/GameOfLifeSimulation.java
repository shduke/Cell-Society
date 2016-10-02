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
import javafx.scene.paint.Color;


public class GameOfLifeSimulation extends Simulation {

    public GameOfLifeSimulation (Map<String, Map<String, String>> simulationConfig) {
        super(simulationConfig);
    }

    @Override
    public void step () {
        getGrid().applyFuncToCell(p -> setNextState(p));
        updateGrid();
        countCellsinGrid();
    }

    private int livingNeighbors (List<Cell> neighbors) {
        int count = 0;
        for (Cell cell : neighbors) {
            if (cell.getMyCurrentState().equals(GameOfLifeState.LIVING)) {
                count++;
            }
        }
        return count;
    }

    public void setNextState (Cell cell) {
        int numberOfLivingNeighbors =
                livingNeighbors(getMyNeighborsHandler()
                        .getSurroundingNeighbors(cell.getMyGridCoordinate()));
        if (numberOfLivingNeighbors == 3) {
            cell.setMyNextState(GameOfLifeState.LIVING);
        }
        else if (numberOfLivingNeighbors < 2 || numberOfLivingNeighbors > 3) {
            cell.setMyNextState(GameOfLifeState.EMPTY);
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
                new GameOfLifeCell(GameOfLifeState.valueOf(currentState.toUpperCase()), coordinate);
        if (new Random().nextInt(2) == 1) {
            cell.setMyCurrentState(GameOfLifeState.LIVING);
        }
        return cell;
    }

    @Override
    public void countCellsinGrid () {
        // TODO Auto-generated method stub
        stepNum = getStepNum();
        System.out.println("Num of steps: " + stepNum);
        int livingCount = 0;
        int emptyCount = 0;
        for (Cell cell : getGrid().getImmutableCellGrid().values()) {
            if (cell.getMyCurrentState().equals(GameOfLifeState.LIVING)) {
                livingCount++;
            }
            if (cell.getMyCurrentState().equals(GameOfLifeState.EMPTY)) {
                emptyCount++;
            }
        }
        System.out.println("Living:" + livingCount);
        System.out.println("Empty: " + emptyCount);
        stepNum++;

    }

    private enum GameOfLifeState implements State {

                                                  EMPTY(Color.GHOSTWHITE),
                                                  LIVING(Color.DARKGREEN);

        private final Color myColor;

        GameOfLifeState (Color color) {
            myColor = color;
        }

        public Color getColor () {
            return myColor;
        }
    }

}
