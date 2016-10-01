package simulation;

import java.util.Iterator;
import java.util.List;
import cell.Ant;
import java.util.Map;
import cell.Cell;
import cell.EmptyCell;
import grid.Coordinate;
import grid.Grid;
import grid.Neighbor;
import cell.ForagingAntCell;
import cell.State;


public class ForagingAntsSimulation extends Simulation {

    private ForagingAntCell myNest;
    private List<Coordinate> foodCells;
    private double myMaxPheromones;
    private double myDiffusionRate;
    private double myEvaporationRate;
    private int myAntLifetime;
    private int myMaxAnts;
    private double k;
    private double n;

    public ForagingAntsSimulation (Map<String, Map<String, String>> simulationConfig) {
        super(simulationConfig);
    }

    @Override
    public void step () {
        // TODO Auto-generated method stub
        updateAnts();
        diffuse();
        myNest.spawn(myAntLifetime);

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
        ant.update();
        if (ant.getMyNextState() == State.DEAD) {
            return;
        }
        if (isAtFoodSource(ant)) {
            ant.setMyNextState(State.HOMESEARCH);
            ForagingAntCell bestHome = getBestDirection(ant, false);
            ant.setOrientation(bestHome);
            goHome(ant);
        }
        else if (isAtNest(ant)) {
            ant.setMyNextState(State.FOODSEARCH);
            ForagingAntCell bestFood = getBestDirection(ant, true);
            ant.setOrientation(bestFood);
            forage(ant);
        }
        else {
            if (ant.getMyCurrentState() == State.FOODSEARCH) {
                forage(ant);
            }
            else {
                goHome(ant);
            }
        }

        // List<Cell> neighbors = getNeighbors().getNeighbors(, coordinate)
    }

    private void goHome (Ant ant) {
        // TODO calculate neighbors based on direction
        List<Cell> neighbors = null;
        ForagingAntCell nextCell = ant.goHome(neighbors);
        if (nextCell == null) {
            nextCell = ant.goHome(getSquareNeighbors(ant));
        }
        if (nextCell != null) {
            dropPheromones(ant, false);
            ((ForagingAntCell) getGrid().getCell(ant.getMyGridCoordinate())).addAnt(ant);
        }
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

    private ForagingAntCell getBestDirection (Ant ant, boolean food) {
        List<Cell> neighbors = getSquareNeighbors(ant);
        return ant.getBestNeighbor(neighbors, food);

    }

    private boolean isAtNest (Ant ant) {
        return myNest.getMyGridCoordinate().equals(ant.getMyGridCoordinate());
    }

    private boolean isAtFoodSource (Ant ant) {
        return foodCells.contains(ant.getMyGridCoordinate());
    }

    private void diffuse () {
        Iterator<Cell> cells = getGrid().iterator();
        while (cells.hasNext()) {
            ForagingAntCell cell = (ForagingAntCell) cells.next();
            cell.diffuseAndEvaporate(getSquareNeighbors(cell), myDiffusionRate, myEvaporationRate);
        }
    }

    @Override
    public void initializeSimulationDetails (Map<String, String> simulationConfig) {
        this.myMaxPheromones = Double.parseDouble(simulationConfig.get("maxPheromones"));
        this.myDiffusionRate = Double.parseDouble(simulationConfig.get("diffusionRate"));
        this.myEvaporationRate = Double.parseDouble(simulationConfig.get("evaporationRate"));
        this.k = Double.parseDouble(simulationConfig.get("k"));
        this.n = Double.parseDouble(simulationConfig.get("n"));
        this.myMaxAnts = Integer.parseInt(simulationConfig.get("maxAnts"));
        this.myAntLifetime = Integer.parseInt(simulationConfig.get("antLifetime"));
    }

    @Override
    public Cell createCell (Coordinate coordinate, String currentState) {
        State state = State.valueOf(currentState.toUpperCase());
        ForagingAntCell cell =
                new ForagingAntCell(state, coordinate, myMaxPheromones, myMaxAnts, k, n);
        if (currentState.equals("Nest")) {
            myNest = cell;
        }
        return cell;
    }

    

}
