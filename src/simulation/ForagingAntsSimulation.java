package simulation;

import java.util.ArrayList;
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
    private int totalAnts;
    private double k;
    private double n;

    public ForagingAntsSimulation (Map<String, Map<String, String>> simulationConfig) {
        super(simulationConfig);

    }

    @Override
    public void step () {

        diffuse();
        myNest.spawn(myAntLifetime);
        updateAnts();
        updateCells();
        getGrid().updateGrid();
        this.getGridView().updateView();

    }

    private void updateCells () {
        Iterator<Cell> cells = getGrid().iterator();
        while (cells.hasNext()) {
            ForagingAntCell cell = (ForagingAntCell) cells.next();
            cell.update();
        }
    }

    private void updateAnts () {
        totalAnts = 0;
        Iterator<Cell> cells = getGrid().iterator();
        while (cells.hasNext()) {
            ForagingAntCell cell = (ForagingAntCell) cells.next();
            // cell.update();
            if (cell.getAnts().size() > 0) {
                //System.out.println(cell.getMyGridCoordinate());
            }
            for (Ant a : cell.getAnts()) {
                System.out.println("Cell " + a.getMyGridCoordinate());
                System.out.println("orientation " +a.getMyOrientation());
                totalAnts++;
                updateAnt(a);
            }
        }
        System.out.println("There are " + totalAnts + " ants");
    }

    public void updateAnt (Ant ant) {
        ant.update();
        if (ant.getMyNextState() == State.DEAD) {
            return;
        }
        // dropPheromones(ant, ant.getMyCurrentState()==State.FOODSEARCH);
        if (isAtFoodSource(ant)) {
            ant.setMyNextState(State.HOMESEARCH);
            // dropPheromones(ant, false);
            ForagingAntCell bestHome = getBestDirection(ant, false);
            if (bestHome != null)
                ant.setOrientation(bestHome);
            goHome(ant);
        }
        else if (isAtNest(ant)) {
            ant.setMyNextState(State.FOODSEARCH);
            // dropPheromones(ant, true);
            ForagingAntCell bestFood = getBestDirection(ant, true);
            if (bestFood != null)
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
            ant.setMyNextState(ant.getMyCurrentState());
        }

        // List<Cell> neighbors = getNeighbors().getNeighbors(, coordinate)
    }

    private void goHome (Ant ant) {
        // TODO calculate neighbors based on direction
        List<Cell> neighbors =
                getNeighbors().getDirectionNeighbors(ant.getMyGridCoordinate(),
                                                     ant.getMyOrientation());
        ForagingAntCell nextCell = ant.goHome(neighbors);
        if (nextCell == null) {
            nextCell = ant.goHome(getSquareNeighbors(ant));
        }
        if (nextCell != null) {
            dropPheromones(ant, false);
            ant.willMove();
            nextCell.addAnt(ant);
            // ant.setMyNextGridCoordinate(nextCell.getMyGridCoordinate());
            // ((ForagingAntCell) getGrid().getCell(ant.getMyGridCoordinate())).addAnt(ant);
        }
    }

    /**
     * @param ant
     */
    private void forage (Ant ant) {
        List<Cell> neighbors =
                getNeighbors().getDirectionNeighbors(ant.getMyGridCoordinate(),
                                                     ant.getMyOrientation());
        ForagingAntCell nextCell = ant.forage(neighbors);
        if (nextCell == null) {
            nextCell = ant.forage(getSquareNeighbors(ant));
        }
        if (nextCell != null) {
            dropPheromones(ant, ant.getMyCurrentState() == State.FOODSEARCH);
            System.out.println(nextCell.fullOfAnts());
            System.out.println("Ant will move: " + ant.isDeadOrMoving());
            ant.willMove();
            nextCell.addAnt(ant);
            // ant.setMyNextGridCoordinate(nextCell.getMyGridCoordinate());

        }

    }

    private void dropPheromones (Ant ant, boolean food) {
        ForagingAntCell cell =
                (ForagingAntCell) getGrid().getCellGrid().get(ant.getMyGridCoordinate());
        if (food) {
            if (isAtNest(ant)) {
                cell.setMaxPheromones(false);
            }
            else {
                cell.addPheromones(getSquareNeighbors(cell), false);
            }
        }
        else {
            if (isAtFoodSource(ant)) {
                cell.setMaxPheromones(true);
            }
            else {
                cell.addPheromones(getSquareNeighbors(cell), true);
            }
        }
    }

    private ForagingAntCell getBestDirection (Ant ant, boolean food) {
        List<Cell> neighbors = getNeighbors().getSurroundingNeighbors(ant.getMyGridCoordinate());
        return ant.getBestNeighbor(neighbors, food);

    }

    private boolean isAtNest (Ant ant) {
        return myNest.getMyGridCoordinate().equals(ant.getMyGridCoordinate());
    }

    private boolean isAtFoodSource (Ant ant) {
        if (foodCells.contains(ant.getMyGridCoordinate())) {
            System.out.println("FOUND FOOD");
        }
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
        this.foodCells = new ArrayList<Coordinate>();
    }

    @Override
    public Cell createCell (Coordinate coordinate, String currentState) {
        // State state = State.valueOf(currentState.toUpperCase());
        ForagingAntCell cell =
                new ForagingAntCell(State.EMPTY, coordinate, myMaxPheromones, myMaxAnts, k, n);
        if (currentState.equals("Nest")) {
            myNest = cell;
            cell.setPheromones(myMaxPheromones, false);
        }
        else if (currentState.equals("Food")) {
            foodCells.add(cell.getMyGridCoordinate());
            cell.setPheromones(myMaxPheromones, true);
        }
        return cell;
    }

}
