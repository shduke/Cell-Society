package cell;

import java.util.Iterator;
import java.util.List;
import grid.Coordinate;


public class ForagingAntCell extends Cell {

    private List<Ant> myAnts;
    private List<Ant> myNextAnts;

    private double myFoodPheromones;
    private double myHomePheromones;
    private double myMaxPheromones;
    private int myMaxAnts;
    private double myK;
    private double myN;

    public ForagingAntCell (State myState,
                            Coordinate myCoordinate,
                            double maxPheromones,
                            int maxAnts,
                            double k,
                            double n) {
        super(myState, myCoordinate);
        myMaxPheromones = maxPheromones;
        myMaxAnts = maxAnts;
        myK = k;
        myN = n;
    }

    public void breed (int numAnts, int lifetime) {
        for (int i = 0; i < numAnts; i++) {
            myNextAnts.add(new Ant(this.getMyGridCoordinate(), lifetime));
        }
    }

    public void update () {
        updateAnts();
    }

    private void updateAnts () {
        removeDeadAnts();
        addNextAnts();
        // moveAnts();
    }

    private void addNextAnts () {
        for (Ant a : myNextAnts) {
            myAnts.add(a);
        }
        myNextAnts.clear();
    }

    public void addAnt (Ant ant) {
        myNextAnts.add(ant);
    }

    private void removeDeadAnts () {
        Iterator<Ant> iter = myAnts.iterator();
        while (iter.hasNext()) {
            if (iter.next().isDeadOrMoving()) {
                iter.remove();
            }
        }
    }

    public boolean fullOfAnts () {
        return myAnts.size() > myMaxAnts;
    }

    public boolean foodFull () {
        return myFoodPheromones == myMaxPheromones;
    }

    public boolean homeFull () {
        return myHomePheromones == myMaxPheromones;
    }

    public void addPheromones (List<Cell> neighbors, boolean food) {
        double valueToAdd = 0;
        for (Cell c : neighbors) {
            ForagingAntCell cell = (ForagingAntCell) c;
            valueToAdd = Math.max(valueToAdd, cell.getPheromones(food));
        }
        double desired = valueToAdd - 2;
        setPheromones(Math.max(desired, getPheromones(food)), food);

    }

    public void setPheromones (double value, boolean food) {
        if (food) {
            myFoodPheromones = value;
        }
        else {
            myHomePheromones = value;
        }
    }

    public void spawn (int antLifetime) {
        addAnt(new Ant(this.getMyGridCoordinate(), antLifetime));
    }

    public void diffuseAndEvaporate (List<Cell> neighbors,
                                     double diffusionRate,
                                     double evaporationRate) {
        myFoodPheromones -= myFoodPheromones * evaporationRate;
        myHomePheromones -= myHomePheromones * evaporationRate;
        for (Cell c : neighbors) {
            ForagingAntCell cell = (ForagingAntCell) c;
            cell.myFoodPheromones += myFoodPheromones * diffusionRate;
            cell.myHomePheromones += myHomePheromones * diffusionRate;
            myHomePheromones -= myHomePheromones * diffusionRate;
            myFoodPheromones -= myFoodPheromones * diffusionRate;
        }
    }

    public double getPheromones (boolean food) {
        return food ? myFoodPheromones : myHomePheromones;
    }

    public List<Ant> getAnts () {
        return myAnts;
    }

    public double getProb () {
        return Math.pow(myFoodPheromones + myK, myN);
    }

    public void setMaxPheromones (boolean food) {
        if (food) {
            myFoodPheromones = myMaxPheromones;
        }
        else {
            myHomePheromones = myMaxPheromones;
        }
    }
}
