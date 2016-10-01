package cell;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import grid.Coordinate;
import grid.Neighbor;


public class Ant extends Cell {

    private int myLifetime;
    private boolean myMovingStatus;
    private String myOrientation;

    public Ant (Coordinate coordinate, int lifetime) {

        super(State.FOODSEARCH, coordinate);
        myLifetime = lifetime;
    }

    public void update () {
        myMovingStatus = false;
        myLifetime--;
        if (myLifetime == 0) {
            setMyNextState(State.DEAD);
        }
    }

    private void willMove () {
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
                if (random < counter) {

                    this.setOrientation(cell);
                    return cell;
                }
            }
            this.setOrientation(neighbors.get(0));
            return (ForagingAntCell) neighbors.get(0);
        }
        return null;

    }

    public ForagingAntCell goHome (List<Cell> neighbors) {
        if (tryToMove(neighbors)) {
            ForagingAntCell bestNeighbor = getBestNeighbor(neighbors, false);
            setOrientation(bestNeighbor);
            return bestNeighbor;
        }
        return null;
    }

    public ForagingAntCell getBestNeighbor (List<Cell> neighbors, boolean food) {
        ForagingAntCell bestCell = null;
        double mostPheromones = 0;
        for (Cell c : neighbors) {
            ForagingAntCell cell = (ForagingAntCell) c;
            if (cell.getPheromones(food) >= mostPheromones) {
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
        }
        this.willMove();
        return this.myMovingStatus;
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

        String first = "";
        String second = "";
        switch (x) {
            case -1:
                first = "TOP";
                break;
            case 0:
                first = "MID";
                break;
            default:
                first = "BOTTOM";
                break;
        }
        switch (y) {
            case -1:
                second = "LEFT";
                break;
            case 0:
                second = "MID";
                break;
            default:
                second = "RIGHT";
                break;
        }

        StringBuilder orientation = new StringBuilder();

        orientation.append(first);
        orientation.append(second);
        this.myOrientation = orientation.toString();

    }

}
