package cell;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import grid.Coordinate;
import javafx.scene.paint.Color;


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
        myAnts = new ArrayList<Ant>();
        myNextAnts = new ArrayList<Ant>();
        myMaxPheromones = maxPheromones;
        myMaxAnts = maxAnts;
        myK = k;
        myN = n;
        myHomePheromones = 0;
        myFoodPheromones = 0;
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
            a.setMyGridCoordinate(this.getMyGridCoordinate());
            a.doneMoving();
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
                System.out.println("REMOVE ANT");
            }
        }
    }

    public boolean fullOfAnts () {
        return (myAnts.size() + myNextAnts.size()) > myMaxAnts;
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
        double desired = Math.max(0,valueToAdd - 2);
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
        System.out.println("SPAWN");
        if (myAnts.size() < myMaxAnts) {
            addAnt(new Ant(this.getMyGridCoordinate(), antLifetime));
        }
    }

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
        // System.out.println(myFoodPheromones);
        // System.out.println(myHomePheromones);
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

    @Override
    public Color getColor () {
       
        if(myHomePheromones == myMaxPheromones){
            return Color.rgb(0, 255, 0);
        }
        if(myFoodPheromones == myMaxPheromones){
            return Color.rgb(0, 0, 255);
        }
        /*
        if (myAnts.size() > 0) {
            Color color = Color.RED;
            for (int i = 0; i < myAnts.size() / 2; i++) {
                color = color.darker();
            }
            return color;
        }*/
        int red = (int) ((double) (myAnts.size() / myMaxAnts) * 255);
        int green = (int) ((myHomePheromones / myMaxPheromones) * 255);
        int blue = (int) ((myFoodPheromones / myMaxPheromones) * 255);
        // System.out.println(red + ", " + green +", " + blue);
        return Color.rgb(red, green, blue);
    }
}
