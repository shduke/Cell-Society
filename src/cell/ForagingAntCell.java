package cell;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import grid.Coordinate;
import javafx.scene.paint.Color;


/**
 * 
 * @author Michael Schroeder
 *
 */
public class ForagingAntCell extends Cell {

    private static final int MAX_COLOR_VALUE = 255;
    private List<AntCell> myAnts;
    private List<AntCell> myNextAnts;
    private double myFoodPheromones;
    private double myHomePheromones;
    private double myMaxPheromones;
    private int myMaxAnts;
    private double myK;
    private double myN;

    /**
     * Constructs a new ForagingAntCell with the given parameters
     * 
     * @param myState - the Cell's current state
     * @param myCoordinate - the coordinate to create the Cell at
     * @param maxPheromones - the maximum amount of pheromones this Cell can hold
     * @param maxAnts - the maximum amount of AntCell this Cell can hold
     * @param k - used to calculate the probability of an AntCell moving here
     * @param n - used to calculate the probability of an AntCell moving here
     */
    public ForagingAntCell (State myState,
                            Coordinate myCoordinate,
                            double maxPheromones,
                            int maxAnts,
                            double k,
                            double n) {
        super(myState, myCoordinate);
        myAnts = new ArrayList<AntCell>();
        myNextAnts = new ArrayList<AntCell>();
        myMaxPheromones = maxPheromones;
        myMaxAnts = maxAnts;
        myK = k;
        myN = n;
        myHomePheromones = 0;
        myFoodPheromones = 0;
    }

    /**
     * Updates this cell. Called every step by ForagingAntSimulation
     */
    public void update () {

        updateAnts();
    }

    private void updateAnts () {
        removeDeadAnts();
        addNextAnts();
    }

    private void addNextAnts () {
        for (AntCell a : myNextAnts) {
            myAnts.add(a);
            a.setMyGridCoordinate(this.getMyGridCoordinate());
        }
        myNextAnts.clear();
    }

    /**
     * Adds an ant that will move to this cell on the next step
     * 
     * @param ant
     */
    public void addAnt (AntCell ant) {
        myNextAnts.add(ant);
    }

    private void removeDeadAnts () {
        Iterator<AntCell> iter = myAnts.iterator();
        while (iter.hasNext()) {
            if (iter.next().isDeadOrMoving()) {
                iter.remove();
            }
        }
    }

    /**
     * Returns whether or not this Cell is at its maximum ant capacity
     * 
     * @return - true if this Cell is full of ants
     */
    public boolean fullOfAnts () {
        return (myAnts.size() + myNextAnts.size()) > myMaxAnts;
    }

    /**
     * Returns whether or not this Cell is at its maximum capacity of food pheromones
     * 
     * @return - true if at maximum food capacity, false otherwise
     */
    public boolean foodFull () {
        return myFoodPheromones == myMaxPheromones;
    }

    /**
     * Returns whether or not this Cell is at its maximum capacity of home pheromones
     * 
     * @return - true if at maximum home pheromone capacity, false otherwise
     */
    public boolean homeFull () {
        return myHomePheromones == myMaxPheromones;
    }

    /**
     * Sets this cells pheromones to the maximum value of its neighbors, minus a constant
     * 
     * @param neighbors - this cells neighbors, used to calculate the desired value
     * @param food - true if adding food pheromones, false if adding home pheromones
     */
    public void addPheromones (List<Cell> neighbors, boolean food) {
        double valueToAdd = 0;
        for (Cell c : neighbors) {
            ForagingAntCell cell = (ForagingAntCell) c;
            valueToAdd = Math.max(valueToAdd, cell.getPheromones(food));
        }
        double desired = Math.max(0, valueToAdd - 2);
        setPheromones(Math.max(desired, getPheromones(food)), food);

    }

    private void setPheromones (double value, boolean food) {
        if (food) {
            System.out.println("Food phero " + value);
            myFoodPheromones = value;
        }
        else {
            myHomePheromones = value;
        }
    }

    /**
     * Spawns a new AntCell at this ForagingAntCell
     * 
     * @param antLifetime - the lifetime of the ant
     */
    public void spawn (int antLifetime) {
        System.out.println("SPAWN");
        if (myAnts.size() < myMaxAnts) {
            addAnt(new AntCell(this.getMyGridCoordinate(), antLifetime));
        }
    }

    /**
     * Diffuses and evaporates this cell's pheromones to the surrounding cells
     * 
     * @param neighbors - the cells to diffuse and evaporate to
     * @param diffusionRate - the rate of diffusion
     * @param evaporationRate - the rate of evaporation
     */
    public void diffuseAndEvaporate (List<Cell> neighbors,
                                     double diffusionRate,
                                     double evaporationRate) {
        myFoodPheromones -= myFoodPheromones * evaporationRate;
        myHomePheromones -= myHomePheromones * evaporationRate;
        for (Cell c : neighbors) {
            ForagingAntCell cell = (ForagingAntCell) c;
            cell.myFoodPheromones =
                    Math.min(cell.myFoodPheromones + myFoodPheromones * diffusionRate,
                             myMaxPheromones);
            cell.myHomePheromones =
                    Math.min(cell.myHomePheromones + myHomePheromones * diffusionRate,
                             myMaxPheromones);
            myHomePheromones -= myHomePheromones * diffusionRate;
            myFoodPheromones -= myFoodPheromones * diffusionRate;
        }
    }

    /**
     * Returns this cell's pheromones
     * 
     * @param food - whether or not to return food or home pheromones
     * @return - food or home pheromones
     */
    public double getPheromones (boolean food) {
        return food ? myFoodPheromones : myHomePheromones;
    }

    /**
     * Returns this cell's ants
     * 
     * @return - a list of the AntCells in this Cell
     */
    public List<AntCell> getAnts () {
        return myAnts;
    }

    /**
     * Get the probability of a foraging ant moving here
     * 
     * @return - the probability of an ant moving here
     */
    public double getProb () {
        return Math.pow(myFoodPheromones + myK, myN);
    }

    /**
     * Sets this cells pheromones to the max value
     * 
     * @param food - whether or not to set food or home pheromones
     */
    public void setMaxPheromones (boolean food) {
        if (food) {
            myFoodPheromones = myMaxPheromones;
        }
        else {
            myHomePheromones = myMaxPheromones;
        }
    }

    @Override
    public Color getColor () {

        if (myHomePheromones == myMaxPheromones) {
            return Color.rgb(0, MAX_COLOR_VALUE, 0);
        }
        if (myFoodPheromones == myMaxPheromones) {
            return Color.rgb(0, 0, MAX_COLOR_VALUE);
        }

        int red =
                (int) (((double) Math.min(myAnts.size(), myMaxAnts) / (double) myMaxAnts) *
                       MAX_COLOR_VALUE);
        int green = (int) ((myHomePheromones / myMaxPheromones) * MAX_COLOR_VALUE);
        int blue = (int) ((myFoodPheromones / myMaxPheromones) * MAX_COLOR_VALUE);
        return Color.rgb(red, green, blue);
    }
}
