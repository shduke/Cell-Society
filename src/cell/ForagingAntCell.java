package cell;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import grid.Coordinate;
import javafx.scene.paint.Color;


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

    public void update () {

        updateAnts();
    }

    private void updateAnts () {
        removeDeadAnts();
        addNextAnts();

        // moveAnts();
    }

    private void addNextAnts () {
        for (AntCell a : myNextAnts) {
            myAnts.add(a);
            a.setMyGridCoordinate(this.getMyGridCoordinate());
            // a.doneMoving();
        }
        myNextAnts.clear();
    }

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
            ForagingAntCell cell = (ForagingAntCell)c;
            valueToAdd = Math.max(valueToAdd, cell.getPheromones(food));
        }
        double desired = Math.max(0, valueToAdd - 2);
        setPheromones(Math.max(desired, getPheromones(food)), food);

    }

    public void setPheromones (double value, boolean food) {
        if (food) {
            System.out.println("Food phero " + value);
            myFoodPheromones = value;
        }
        else {
            myHomePheromones = value;
        }
    }

    public void spawn (int antLifetime) {
        System.out.println("SPAWN");
        if (myAnts.size() < myMaxAnts) {
            addAnt(new AntCell(this.getMyGridCoordinate(), antLifetime));
        }
    }

    public void diffuseAndEvaporate (List<Cell> neighbors,
                                     double diffusionRate,
                                     double evaporationRate) {
        myFoodPheromones -= myFoodPheromones * evaporationRate;
        myHomePheromones -= myHomePheromones * evaporationRate;
        for (Cell c : neighbors) {
            ForagingAntCell cell = (ForagingAntCell)c;
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

    public List<AntCell> getAnts () {
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

        if (myHomePheromones == myMaxPheromones) {
            return Color.rgb(0, MAX_COLOR_VALUE, 0);
        }
        if (myFoodPheromones == myMaxPheromones) {
            return Color.rgb(0, 0, MAX_COLOR_VALUE);
        }
        /*
         * if (myAnts.size() > 0) {
         * Color color = Color.RED;
         * for (int i = 0; i < myAnts.size() / 2; i++) {
         * color = color.darker();
         * }
         * return color;
         * }
         */
        int red =
                (int)(((double)Math.min(myAnts.size(), myMaxAnts) / (double)myMaxAnts) *
                       MAX_COLOR_VALUE);
        int green = (int)((myHomePheromones / myMaxPheromones) * MAX_COLOR_VALUE);
        int blue = (int)((myFoodPheromones / myMaxPheromones) * MAX_COLOR_VALUE);
        // System.out.println(red + ", " + green +", " + blue);
        return Color.rgb(red, green, blue);
    }
}
