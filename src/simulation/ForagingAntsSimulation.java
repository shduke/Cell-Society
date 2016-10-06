/**
 * @author Michael Schroeder
 * 
 *         This class implements the Foraging Ants Simulation. There are ForagingAntCells, which are
 *         the patches that contain pheromones, as well as AntCells. The AntCells move around the
 *         ForagingAntCells, searching either for food or the nest. This tracks the number of ants,
 *         as well as the amount of food gathered, for graphing purposes. This class depends on the
 *         Grid class (although it is insulated from its implementation), the ForagingAntCell and
 *         AntCell classes, the NeighborsHandler class, and SimulationToolbar (which it adds its
 *         simulation-specific sliders to).
 * 
 *         This class is used by XMLParser, which returns an initialized simulation, by passing in a
 *         map of configuration variables, which are
 *         set in initializeSimulationDetails.
 */
package simulation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import applicationView.SimulationToolbar;
import cell.AntCell;
import cell.Cell;
import cell.ForagingAntCell;
import grid.Coordinate;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import cell.State;


public class ForagingAntsSimulation extends Simulation {

    private static final double MIN_DIFFUSE_SLIDER = 0.001;
    private static final double MAX_DIFFUSE_SLIDER = 0.2;
    private static final double MIN_LIFETIME_SLIDER = 10;
    private static final double MAX_LIFETIME_SLIDER = 1000;
    private ForagingAntCell myNest;
    private List<Coordinate> myFoodCells;
    private double myMaxPheromones;
    private double myDiffusionRate;
    private double myEvaporationRate;
    private int myAntLifetime;
    private int myMaxAnts;
    private int myTotalAnts;
    private int myFoodGathered;
    private double myK;
    private double myN;

    /**
     * Constructs a ForagingAntSimulation using the configuration detailed in the XML file
     * 
     * @param simulationConfig
     */
    public ForagingAntsSimulation (Map<String, Map<String, String>> simulationConfig) {
        super(simulationConfig);
    }

    /**
     * Called every frame by SimulationController. Every Simulation has a step method, which
     * calculates next states, and updates the grid
     */
    @Override
    public void step () {
        stepNum++;
        diffuse();
        myNest.spawn(myAntLifetime);
        updateAnts();
        updateCells();
        updateGrid();

    }

    private void updateCells () {
        Iterator<Cell> cells = getGrid().iterator();
        while (cells.hasNext()) {
            ForagingAntCell cell = (ForagingAntCell) cells.next();
            cell.update();
        }
    }

    private void updateAnts () {
        myTotalAnts = 0;
        Iterator<Cell> cells = getGrid().iterator();
        while (cells.hasNext()) {
            ForagingAntCell cell = (ForagingAntCell) cells.next();
            for (AntCell a : cell.getAnts()) {
                myTotalAnts++;
                updateAnt(a);
            }
        }
    }

    private void updateAnt (AntCell ant) {
        ant.update();
        if (ant.getMyNextState() == ForagingAntState.DEAD) {
            return;
        }
        if (isAtFoodSource(ant)) {
            if (!ant.hasFood()) {
                ant.pickUpFood();
            }
            ant.setMyCurrentState(ForagingAntState.HOMESEARCH);
            ant.setMyNextState(ForagingAntState.HOMESEARCH);
            ForagingAntCell bestHome = getBestDirection(ant, false);
            if (bestHome != null) {
                ant.setOrientation(bestHome);
            }
            goHome(ant);
        }
        else if (isAtNest(ant)) {
            if (ant.hasFood()) {
                ant.dropFood();
                myFoodGathered++;
            }
            ant.setMyCurrentState(ForagingAntState.FOODSEARCH);
            ant.setMyNextState(ForagingAntState.FOODSEARCH);
            ForagingAntCell bestFood = getBestDirection(ant, true);
            if (bestFood != null) {
                ant.setOrientation(bestFood);
            }
            forage(ant);
        }
        else {
            if (ant.getMyCurrentState() == ForagingAntState.FOODSEARCH) {
                forage(ant);
            }
            else {
                goHome(ant);
            }
            ant.setMyNextState(ant.getMyCurrentState());
        }
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
            ant.willMove();
            nextCell.addAnt(ant);
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
        List<Cell> neighbors =
                getNeighborsHandler().getSurroundingNeighbors(ant.getMyGridCoordinate());
        return ant.getBestNeighbor(neighbors, food);

    }

    private boolean isAtNest (AntCell ant) {
        return myNest.getMyGridCoordinate().equals(ant.getMyGridCoordinate());
    }

    private boolean isAtFoodSource (AntCell ant) {
        return myFoodCells.contains(ant.getMyGridCoordinate());
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
        this.myK = Double.parseDouble(simulationConfig.get("k"));
        this.myN = Double.parseDouble(simulationConfig.get("n"));
        this.myMaxAnts = Integer.parseInt(simulationConfig.get("maxAnts"));
        this.myAntLifetime = Integer.parseInt(simulationConfig.get("antLifetime"));
        this.myFoodCells = new ArrayList<Coordinate>();
        this.myFoodGathered = 0;
    }

    @Override
    public Cell createCell (Coordinate coordinate, State currentState) {
        ForagingAntCell cell =
                new ForagingAntCell(ForagingAntState.EMPTY, coordinate, myMaxPheromones, myMaxAnts,
                                    myK, myN);
        if (currentState == ForagingAntState.NEST) {
            myNest = cell;
            cell.setMaxPheromones(false);
        }
        else if (currentState == ForagingAntState.FOOD) {
            myFoodCells.add(cell.getMyGridCoordinate());
            cell.setMaxPheromones(true);
        }
        return cell;
    }

    /**
     * Counts the number of cells in each state for each step of the simulation
     * @return list of integers for the data of each state
     */
    @Override
    public List<Integer> countCellsinGrid () {
        List<Integer> myOutput = new ArrayList<Integer>();
        myOutput.add(stepNum - 1);
        myOutput.add(myFoodGathered);
        myOutput.add(myTotalAnts);
        return myOutput;
    }

    @Override
    public void initializeSimulationToolbar (SimulationToolbar toolbar) {
        Slider diffusionSlider =
                new Slider(MIN_DIFFUSE_SLIDER, MAX_DIFFUSE_SLIDER, myDiffusionRate);
        diffusionSlider.valueProperty()
                .addListener(e -> myDiffusionRate = diffusionSlider.getValue());
        toolbar.addSlider(diffusionSlider, "myDiffusionRate");
        Slider evaporationSlider =
                new Slider(MIN_DIFFUSE_SLIDER, MAX_DIFFUSE_SLIDER, myEvaporationRate);
        evaporationSlider.valueProperty()
                .addListener(e -> myEvaporationRate = evaporationSlider.getValue());
        toolbar.addSlider(evaporationSlider, "myEvaporationRate");
        Slider antLifetimeSlider =
                new Slider(MIN_LIFETIME_SLIDER, MAX_LIFETIME_SLIDER, myAntLifetime);
        antLifetimeSlider.valueProperty()
                .addListener(e -> myAntLifetime = (int) antLifetimeSlider.getValue());
        toolbar.addSlider(antLifetimeSlider, "antLifetime");
    }

    @Override
    public State[] getSimulationStates () {
        return ForagingAntState.values();
    }

    @Override
    public void getSimulationNames () {
        ResourceBundle GUIResources = ResourceBundle.getBundle("resources/English");
        List<String> myList = new ArrayList<String>();
        myList.add(GUIResources.getString("FoodGathered"));
        myList.add(GUIResources.getString("TotalAnts"));
        mySimulationGraph.addToLegend(myList);
    }

    @Override
    public State getSimulationState (String simulationState) {
        return ForagingAntState.valueOf(simulationState.toUpperCase());
    }

    public enum ForagingAntState implements State {
                                                   FOODSEARCH(Color.GREEN),
                                                   EMPTY(Color.WHITE),
                                                   HOMESEARCH(Color.BLUE),
                                                   DEAD(Color.BLACK),
                                                   FOOD(Color.GREEN),
                                                   NEST(Color.BLUE);

        private final Color myColor;
        private double myProbability;

        ForagingAntState (Color color) {
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
