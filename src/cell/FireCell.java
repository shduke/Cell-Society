package cell;

import grid.Coordinate;

/**
 * 
 * @author Sean Hudson
 *
 */
public class FireCell extends Cell {
    private int myBurnTimer;

    public FireCell (State currentState, Coordinate coordinate) {
        super(currentState, coordinate);
    }

    /**
     * Decreases the burn timer by one
     */
    public void decrementBurnTimer () {
        myBurnTimer--;
    }

    /**
     * Returns the burn timer
     * @return burn timer
     */
    public int getBurnTimer () {
        return myBurnTimer;
    }

    /**
     * Sets the burn timer
     * @param burnTimer
     */
    public void setBurnTimer (int burnTimer) {
        this.myBurnTimer = burnTimer;
    }

}
