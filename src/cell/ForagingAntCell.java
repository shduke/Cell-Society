package cell;

import java.util.Iterator;
import java.util.List;
import grid.Coordinate;


public class ForagingAntCell extends Cell {

    private List<Ant> myAnts;
    private List<Ant> myNextAnts;
    private double myFoodPheromones;
    private double myHomePheromones;
    private double maxPheromones;
    private int myMaxAnts;
    private double k;
    private double n;
    public ForagingAntCell (State myState, Coordinate myCoordinate) {
        super(myState, myCoordinate);
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
        //moveAnts();
    }

    private void addNextAnts () {
        for (Ant a : myNextAnts) {
            myAnts.add(a);
        }
        myNextAnts.clear();
    }
    
    private void removeDeadAnts () {
        Iterator<Ant> iter = myAnts.iterator();
        while (iter.hasNext()) {
            if (iter.next().isDeadOrMoving()) {
                iter.remove();
            }
        }
    }
 
    public boolean fullOfAnts() {
        return myAnts.size() > myMaxAnts;
    }
    public boolean foodFull () {
        return myFoodPheromones == maxPheromones;
    }

    public boolean homeFull () {
        return myHomePheromones == maxPheromones;
    }

    public void addPheromones (List<ForagingAntCell> neighbors, boolean food) {
        double valueToAdd = 0;
        for (ForagingAntCell cell : neighbors) {
            valueToAdd = Math.max(valueToAdd, cell.getPheromones(food));
        }
        double desired = valueToAdd - 2;
        setPheromones(Math.max(desired, getPheromones(food)), food);

    }

    public void setPheromones (double value, boolean food) {

    }

    public double getPheromones (boolean food) {
        return food ? myFoodPheromones : myHomePheromones;
    }
    
    public List<Ant> getAnts() {
        return myAnts;
    }
    public double getProb() {
        return Math.pow(myFoodPheromones + k, n);
    }
    public void setMaxPheromones (boolean food) {
        if (food) {
            myFoodPheromones = maxPheromones;
        }
        else {
            myHomePheromones = maxPheromones;
        }
    }
}
