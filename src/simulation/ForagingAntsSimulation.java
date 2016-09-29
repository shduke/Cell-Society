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

    public ForagingAntsSimulation (Map<String, Map<String, String>> simulationConfig) {
        super(simulationConfig);
    }

    @Override
    public void setNextState (Cell cell) {
        // TODO Auto-generated method stub

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
            ForagingAntCell bestFood = getBestFood(ant);
            ant.setOrientation(bestFood);
            
            // get Neighbors based on ant.getOrientation
            // pass list to ant.forage
            
            //List<ForagingAntCell> forwardNeighbors = getNeighbors().getNeighbors(allowableNeighbors, coordinate)
            //ant.forage(neighbors)
        }
        else if (isAtNest(ant)) {

        }

        // List<Cell> neighbors = getNeighbors().getNeighbors(, coordinate)
    }

    private ForagingAntCell getBestFood (Ant ant) {
        List<Cell> neighbors =
                getNeighbors().getNeighbors(Neighbor.SQUARE.getNeighbors(),
                                            ant.getMyGridCoordinate());
        ForagingAntCell bestCell = null;
        double mostPheromones = 0;
        for (Cell c : neighbors) {
            ForagingAntCell cell = (ForagingAntCell) c;
            if (cell.getPheromones(true) >= mostPheromones) {
                mostPheromones = cell.getPheromones(true);
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
