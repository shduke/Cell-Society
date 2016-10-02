package cell;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import grid.Coordinate;
import grid.Neighbor;


public class AntCell extends Cell {

    private int myLifetime;
    private boolean myMovingStatus;
    private Coordinate myOrientation;
    private boolean myFood;

    public AntCell (Coordinate coordinate, int lifetime) {

        super(State.FOODSEARCH, coordinate);
        myLifetime = lifetime;

    }

    public void update () {
        // System.out.println(this.getMyGridCoordinate() + " is moving to " + myNextCoordinate);
        // this.setMyGridCoordinate(myNextCoordinate);
        // myNextCoordinate = this.getMyGridCoordinate();
        System.out.println("This ant does " + (this.hasFood() ? "" : " not ") +
                           " have food and is going " + this.getMyCurrentState());
        if (this.hasFood()) {
            this.setMyCurrentState(State.HOMESEARCH);
            this.setMyNextState(State.HOMESEARCH);
        }
        myMovingStatus = false;
        myLifetime--;
        if (myLifetime == 0) {
            setMyNextState(State.DEAD);
        }
    }

    public void willMove () {
        myMovingStatus = true;
    }

    public boolean isDeadOrMoving () {
        return getMyNextState() == State.DEAD || myMovingStatus;
    }

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
            // this.wontMove();
            return null;

        }
        return null;

    }

    public void doneMoving () {
        this.myMovingStatus = false;
    }

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

    public ForagingAntCell getBestNeighbor (List<Cell> neighbors, boolean food) {
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

    /**
     * @param neighbors
     */
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

    /**
     * @param neighbors
     */
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

    public void setOrientation (Cell otherCell) {
        Coordinate otherCoord = otherCell.getMyGridCoordinate();
        Coordinate thisCoord = this.getMyGridCoordinate();

        int x = (int) (otherCoord.getX() - thisCoord.getX());
        int y = (int) (otherCoord.getY() - thisCoord.getY());
        // return new Coordinate(x, y);
        myOrientation = new Coordinate(x, y);

    }

    public Coordinate getMyOrientation () {
        return myOrientation;
    }

    public boolean hasFood () {
        return myFood;
    }

    public void pickUpFood () {
        myFood = true;
    }

    public void dropFood () {
        myFood = false;
    }

}
