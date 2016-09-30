package simulation;

import java.util.Iterator;
import java.util.List;
import cell.Ant;
import java.util.Map;
import cell.Cell;
import grid.Coordinate;
import grid.Grid;
import grid.Neighbor;
import cell.ForagingAntCell;
import cell.State;


public class ForagingAntsSimulation extends Simulation {

    private ForagingAntCell myNest;
    private List<Coordinate> foodCells;
    private double myMaxPheromones;

    public ForagingAntsSimulation (Map<String, Map<String, String>> simulationConfig) {
        super(simulationConfig);
    }

    @Override
    public void step () {
        // TODO Auto-generated method stub
        updateAnts();

    }

    private void updateAnts () {
        Iterator<Cell> cells = getGrid().iterator();
        while (cells.hasNext()) {
            ForagingAntCell cell = (ForagingAntCell) cells.next();
            // cell.update();
            for (Ant a : cell.getAnts()) {
                updateAnt(a);
            }
        }
    }

    public void updateAnt (Ant ant) {
        if (isAtFoodSource(ant)) {
            ant.setMyNextState(State.HOMESEARCH);
            ForagingAntCell bestHome = getBestDirection(ant, false);
            ant.setOrientation(bestHome);
            // get neighbors based on orientation
            // pass list to ant.goHome
        }
        else if (isAtNest(ant)) {
            ant.setMyNextState(State.FOODSEARCH);
            ForagingAntCell bestFood = getBestDirection(ant, true);
            ant.setOrientation(bestFood);
            forage(ant);
            // get Neighbors based on ant.getOrientation
            // pass list to ant.forage
        }
        else {
            if (ant.getMyCurrentState() == State.FOODSEARCH) {
                forage(ant);
            }
        }

        // List<Cell> neighbors = getNeighbors().getNeighbors(, coordinate)
    }

    /**
     * @param ant
     */
    private void forage (Ant ant) {
        List<Cell> neighbors = null;
        ForagingAntCell nextCell = ant.forage(neighbors);
        if (nextCell == null) {
            nextCell = ant.forage(getSquareNeighbors(ant));
        }
        if (nextCell != null) {
            dropPheromones(ant, true);
        }
    }

    private void dropPheromones (Ant ant, boolean food) {
        ForagingAntCell cell =
                (ForagingAntCell) getGrid().getCellGrid().get(ant.getMyGridCoordinate());
        if (food) {
            if (isAtNest(ant)) {
                cell.setPheromones(myMaxPheromones, true);
            }
            else {
                cell.addPheromones(getSquareNeighbors(cell), food);
            }
        }
    }

    /**
     * @param cell
     * @return
     */
    private List<Cell> getSquareNeighbors (Cell cell) {
        return getNeighbors().getNeighbors(Neighbor.SQUARE.getNeighbors(),
                                           cell.getMyGridCoordinate(), getGrid());
    }

    private ForagingAntCell getBestDirection (Ant ant, boolean food) {
        List<Cell> neighbors = getSquareNeighbors(ant);
        ForagingAntCell bestCell = null;
        double mostPheromones = 0;
        for (Cell c : neighbors) {
            ForagingAntCell cell = (ForagingAntCell) c;
            if (cell.getPheromones(food) >= mostPheromones) {
                mostPheromones = cell.getPheromones(food);
                bestCell = cell;
            }
        }
        return bestCell;
    }

    private boolean isAtNest (Ant ant) {
        return myNest.getMyGridCoordinate().equals(ant.getMyGridCoordinate());
    }

    private boolean isAtFoodSource (Ant ant) {
        return foodCells.contains(ant.getMyGridCoordinate());
    }

    @Override
    public void initializeSimulationDetails (Map<String, String> simulationConfig) {
        // TODO Auto-generated method stub

    }

    @Override
    public Cell createCell (Coordinate coordinate, String currentState) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void generateMap (int numberOfRows, int numberOfColumns, Grid cellGrid) {
        // TODO Auto-generated method stub

    }

}
