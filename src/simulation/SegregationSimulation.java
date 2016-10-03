package simulation;

import java.util.stream.Collectors;
import applicationView.SimulationToolbar;
import cell.AgentCell;
import cell.Cell;
import cell.EmptyCell;
import cell.State;
import grid.Coordinate;
import grid.Neighbor;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import java.util.*;


public class SegregationSimulation extends Simulation {

    private double myAgentSatisfiedRatio;

    public SegregationSimulation (Map<String, Map<String, String>> simulationConfig) {
        super(simulationConfig);
        // generateMap(getGrid().getNumRows(), getGrid().getNumColumns(), getGrid());
    }

    @Override
    public void step () {
        countCellsinGrid();
        updateAgents();
        updateGrid();
    }

    private void updateAgents () {
        Iterator<Cell> cells = getGrid().iterator();
        while (cells.hasNext()) {

            Cell cell = cells.next();
            if (cell.getMyCurrentState() == SegregationState.EMPTY &&
                cell.getMyNextState() == null) {
                cell.setMyNextState(SegregationState.EMPTY);
            }
            else if (cell.getMyCurrentState() == SegregationState.X ||
                     cell.getMyCurrentState() == SegregationState.O) {
                if (cell.getMyNextState() != SegregationState.EMPTY)
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
                getNeighborsHandler().getSurroundingNeighbors(cell.getMyGridCoordinate());

        int totalNeighbors = 0;
        int friendlyNeighbors = 0;
        for (Cell c : neighbors) {
            if (c.getMyCurrentState() == SegregationState.EMPTY) {
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
                .filter(p -> (p.getMyCurrentState() == SegregationState.EMPTY &&
                              (p.getMyNextState() == SegregationState.EMPTY ||
                               p.getMyNextState() == null)))
                .collect(Collectors.toCollection( () -> openCells));
        if (openCells.size() == 0)
            return;
        Cell moveTo = openCells.get(new Random().nextInt(openCells.size()));
        AgentCell endCell = new AgentCell(cell, moveTo.getMyGridCoordinate());
        endCell.setMyCurrentState(SegregationState.EMPTY);
        endCell.setMyNextState(cell.getMyCurrentState());
        EmptyCell startCell = new EmptyCell(cell.getMyGridCoordinate());
        startCell.setMyCurrentState(cell.getMyCurrentState());
        startCell.setMyNextState(SegregationState.EMPTY);

        getGrid().getCellGrid().put(endCell.getMyGridCoordinate(), endCell);
        getGrid().getCellGrid().put(startCell.getMyGridCoordinate(), startCell);
    }

    @Override
    public void initializeSimulationDetails (Map<String, String> simulationConfig) {
        this.myAgentSatisfiedRatio =
                Double.parseDouble(simulationConfig.get("myAgentSatisfiedRatio"));
    }

    @Override
    public Cell createCell (Coordinate coordinate, State currentState) {
        if (currentState == SegregationState.EMPTY) {
            return new EmptyCell(coordinate);
        }
        else {
            return new AgentCell(currentState, coordinate, myAgentSatisfiedRatio);
        }

    }

    @Override
    public List<Integer> countCellsinGrid () {
        stepNum = getStepNum();
        System.out.println("Num of steps: " + stepNum);
        int xCount = 0;
        int oCount = 0;
        int emptyCount = 0;
        for (Cell cell : getGrid().getImmutableCellGrid().values()) {
            if (cell.getMyCurrentState().equals(SegregationState.X)) {
                xCount++;
            }
            if (cell.getMyCurrentState().equals(SegregationState.O)) {
                oCount++;
            }
            if (cell.getMyCurrentState().equals(SegregationState.EMPTY)) {
                emptyCount++;
            }
        }
        System.out.println("X:" + xCount);
        System.out.println("O: " + oCount);
        System.out.println("Empty: " + emptyCount);
        stepNum++;
        List<Integer> myOutput = new ArrayList<Integer>();
        myOutput.add(stepNum - 1);
        myOutput.add(xCount);
        myOutput.add(oCount);
        myOutput.add(emptyCount);
        return myOutput;
    }

    @Override
    public void initializeSimulationToolbar (SimulationToolbar toolbar) {
        Slider satisfiedSlider = new Slider(0, 1, myAgentSatisfiedRatio);
        satisfiedSlider.valueProperty()
                .addListener(e -> myAgentSatisfiedRatio = satisfiedSlider.getValue());
        toolbar.addSlider(satisfiedSlider, "satisfiedRatio");

    }

    @Override
    public State[] getSimulationStates () {
        return SegregationState.values();
    }

    @Override
    public State getSimulationState (String simulationState) {

        return SegregationState.valueOf(simulationState.toUpperCase());
    }

    public enum SegregationState implements State {
                                                   EMPTY(Color.WHITE),
                                                   X(Color.RED),
                                                   O(Color.BLUE);

        private final Color myColor;
        private double myProbability;

        SegregationState (Color color) {
            myColor = color;
            myProbability = 0;
        }

        @Override
        public Color getColor () {
            return myColor;
        }

        @Override
        public double getProbability () {
            return myProbability;
        }

        @Override
        public void setProbability (double probability) {
            myProbability = probability;
        }

    }

}
