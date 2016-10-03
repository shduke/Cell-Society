package simulation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import cell.AntCell;
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
    private int myFoodGathered;
    private double k;
    private double n;

    public ForagingAntsSimulation (Map<String, Map<String, String>> simulationConfig) {
        super(simulationConfig);

    }

    @Override
    public void step () {
        System.out.println("Food gathered: " + myFoodGathered);
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
                // //Systemout.println(cell.getMyGridCoordinate());
            }
            for (AntCell a : cell.getAnts()) {
                // Systemout.println("Cell " + a.getMyGridCoordinate());
                // Systemout.println("orientation " + a.getMyOrientation());
                totalAnts++;
                updateAnt(a);
            }
        }
        // Systemout.println("There are " + totalAnts + " ants");
    }

    public void updateAnt (AntCell ant) {
        ant.update();
        if (ant.getMyNextState() == State.DEAD) {
            return;
        }
        // dropPheromones(ant, ant.getMyCurrentState()==State.FOODSEARCH);
        if (isAtFoodSource(ant)) {
            System.out.println("Found food");
            if (!ant.hasFood()) {
                ant.pickUpFood();
            }
            ant.setMyCurrentState(State.HOMESEARCH);
            ant.setMyNextState(State.HOMESEARCH);
            // dropPheromones(ant, false);
            ForagingAntCell bestHome = getBestDirection(ant, false);
            if (bestHome != null)
                ant.setOrientation(bestHome);
            goHome(ant);
        }
        else if (isAtNest(ant)) {
            System.out.println("At nest");
            if (ant.hasFood()) {
                System.out.println("Drop food");
                ant.dropFood();
                myFoodGathered++;
            }
            ant.setMyCurrentState(State.FOODSEARCH);
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

    private void goHome (AntCell ant) {
        List<Cell> neighbors =
                getNeighborsHandler().getDirectionNeighbors(ant.getMyGridCoordinate(),
                                                     ant.getMyOrientation());
        ForagingAntCell nextCell = ant.goHome(neighbors);
        if (nextCell == null) {
            nextCell = ant.goHome(getSquareNeighbors(ant));
        }
        if (nextCell != null) {
            dropPheromones(ant, false);
            ant.willMove();
            nextCell.addAnt(ant);
        }
    }

    /**
     * @param ant
     */
    private void forage (AntCell ant) {
        List<Cell> neighbors =
                getNeighborsHandler().getDirectionNeighbors(ant.getMyGridCoordinate(),
                                                     ant.getMyOrientation());
        ForagingAntCell nextCell = ant.forage(neighbors);
        if (nextCell == null) {
            nextCell = ant.forage(getSquareNeighbors(ant));
        }
        if (nextCell != null) {
            dropPheromones(ant, true);
            // Systemout.println(nextCell.fullOfAnts());
            // Systemout.println("Ant will move: " + ant.isDeadOrMoving());
            ant.willMove();
            nextCell.addAnt(ant);
            // ant.setMyNextGridCoordinate(nextCell.getMyGridCoordinate());

        }

    }

    private void dropPheromones (AntCell ant, boolean food) {
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
                System.out.println("Add food pheromones");
                cell.addPheromones(getSquareNeighbors(cell), true);
            }
        }
    }

    private ForagingAntCell getBestDirection (AntCell ant, boolean food) {
        List<Cell> neighbors = getNeighbors().getSurroundingNeighbors(ant.getMyGridCoordinate());
        return ant.getBestNeighbor(neighbors, food);

    }

    private boolean isAtNest (AntCell ant) {
        return myNest.getMyGridCoordinate().equals(ant.getMyGridCoordinate());
    }

    private boolean isAtFoodSource (AntCell ant) {
        if (foodCells.contains(ant.getMyGridCoordinate())) {
            // Systemout.println("FOUND FOOD");
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
        this.myFoodGathered = 0;
    }

    @Override
    public Cell createCell (Coordinate coordinate, String currentState) {
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

    @Override
    public void countCellsinGrid () {
        System.out.println("Gathered food: " + myFoodGathered);
        // TODO Auto-generated method stub

    }

}
