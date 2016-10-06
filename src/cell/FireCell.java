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

    public void decrementBurnTimer () {
        myBurnTimer--;
    }

    public int getBurnTimer () {
        return myBurnTimer;
    }

    public void setBurnTimer (int burnTimer) {
        this.myBurnTimer = burnTimer;
    }

}
