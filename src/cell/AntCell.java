package cell;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import grid.Coordinate;
import simulation.ForagingAntsSimulation.ForagingAntState;


public class AntCell extends Cell {

    private int myLifetime;
    private boolean myMovingStatus;
    private Coordinate myOrientation;
    private boolean myFood;

    /**
     * Construct an AntCell at the given coordinate, with the given lifetime
     * 
     * @param coordinate - the coordinate where the ant will be created
     * @param lifetime - this ant's lifetime
     */
    public AntCell (Coordinate coordinate, int lifetime) {

        super(ForagingAntState.FOODSEARCH, coordinate);
        myLifetime = lifetime;

    }

    /**
     * Called every step by ForagingAntSimulation to update the ants state and lifetime
     */
    public void update () {
        if (this.hasFood()) {
            this.setMyCurrentState(ForagingAntState.HOMESEARCH);
            this.setMyNextState(ForagingAntState.HOMESEARCH);
        }
        myMovingStatus = false;
        myLifetime--;
        if (myLifetime == 0) {
            setMyNextState(ForagingAntState.DEAD);
        }
    }

    /**
     * Sets this ants moving status to true (meaning it will move next update)
     */
    public void willMove () {
        myMovingStatus = true;
    }

    /**
     * Used to tell which ants should be removed from a ForagingAntCell after each step
     * 
     * @return true if the ant is moving or has died, false otherwise
     */
    public boolean isDeadOrMoving () {
        return getMyNextState() == ForagingAntState.DEAD || myMovingStatus;
    }

    /**
     * Randomly chooses the next cell an ant will move to when foraging for food based on its
     * probability of being chosen
     * 
     * @param neighbors - list of neighbor cells to check
     * @return - the next ForagingAntCell the ant will occupy, or null (if they are all full)
     */
    public ForagingAntCell forage (List<Cell> neighbors) {
        if (tryToMove(neighbors)) {
            double total = getProbSum(neighbors);
            double random = new Random().nextDouble() * total;
            double counter = 0;
            for (Cell c : neighbors) {
                ForagingAntCell cell = (ForagingAntCell) c;
                counter += cell.getProb();
                if (random < counter && !cell.fullOfAnts()) {

                    this.setOrientation(cell);
                    return cell;
                }
            }
            return null;
        }
        return null;

    }

    /**
     * Sets this ants moving status to false
     */
    public void doneMoving () {
        this.myMovingStatus = false;
    }

    /**
     * Chooses the neighboring cell with the most home pheromones
     * 
     * @param neighbors - list of neighboring cells to choose from
     * @return - ForagingAntCell that the ant will move to next turn, or null
     */
    public ForagingAntCell goHome (List<Cell> neighbors) {
        if (tryToMove(neighbors)) {
            ForagingAntCell bestNeighbor = getBestNeighbor(neighbors, false);
            if (bestNeighbor != null) {
                setOrientation(bestNeighbor);
            }
            else {
                this.wontMove();
            }
            return bestNeighbor;
        }
        return null;
    }

    /**
     * Returns the neighbor that this AntCell should move to
     * 
     * @param neighbors - list of neighbors to check
     * @param food - whether or not the ant is looking for food
     * @return - ForagingAntCell that the Ant will move to (if not null)
     */
    public ForagingAntCell getBestNeighbor (List<Cell> neighbors, boolean food) {
        Collections.shuffle(neighbors);
        ForagingAntCell bestCell = null;
        double mostPheromones = -1;
        for (Cell c : neighbors) {
            ForagingAntCell cell = (ForagingAntCell) c;
            if (cell.getPheromones(food) >= mostPheromones && !cell.fullOfAnts()) {
                mostPheromones = cell.getPheromones(food);
                bestCell = cell;
            }
        }
        return bestCell;
    }

    private boolean tryToMove (List<Cell> neighbors) {
        removeFullCells(neighbors);
        if (neighbors.size() == 0) {
            this.wontMove();
            return false;
        }
        this.willMove();
        return true;
    }

    private void wontMove () {
        this.myMovingStatus = false;
    }

    private void removeFullCells (List<Cell> neighbors) {
        Iterator<Cell> iter = neighbors.iterator();
        while (iter.hasNext()) {
            if (((ForagingAntCell) iter.next()).fullOfAnts()) {
                iter.remove();
            }
        }
    }

    private double getProbSum (List<Cell> neighbors) {
        double sum = 0;
        for (Cell cell : neighbors) {
            sum += ((ForagingAntCell) cell).getProb();
        }
        return sum;
    }

    /**
     * Sets this ants orientation to face otherCell
     * 
     * @param otherCell - the cell that this ant should face
     */
    public void setOrientation (Cell otherCell) {
        Coordinate otherCoord = otherCell.getMyGridCoordinate();
        Coordinate thisCoord = this.getMyGridCoordinate();

        int x = (int) (otherCoord.getX() - thisCoord.getX());
        int y = (int) (otherCoord.getY() - thisCoord.getY());
        // return new Coordinate(x, y);
        myOrientation = new Coordinate(x, y);

    }

    /**
     * Returns this ants orientation
     * 
     * @return
     */
    public Coordinate getMyOrientation () {
        return myOrientation;
    }

    /**
     * Returns whether or not this ant has food
     * 
     * @return - true if the ant has food, false otherwise
     */
    public boolean hasFood () {
        return myFood;
    }

    /**
     * Makes the ant have food
     */
    public void pickUpFood () {
        myFood = true;
    }

    /**
     * Sets the ants food to false
     */
    public void dropFood () {
        myFood = false;
    }

}
