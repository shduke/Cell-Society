package cell;

import grid.Coordinate;
import javafx.scene.Node;


public class FireCell extends Cell {
    private int burnTimer;

    public FireCell (State currentState, Coordinate coordinate) {
        super(currentState, coordinate);
    }

    public void decrementBurnTimer() {
        burnTimer--;
    }
    
    public int getBurnTimer () {
        return burnTimer;
    }

    public void setBurnTimer (int burnTimer) {
        this.burnTimer = burnTimer;
    }
    

}
