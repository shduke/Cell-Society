package cell;

import grid.Coordinate;
import javafx.scene.paint.Color;


public abstract class Cell {

    private State myCurrentState;
    private State myNextState;
    private Coordinate myGridCoordinate;

    public Cell (State currentState, Coordinate coordinate) {
        myCurrentState = currentState;
        myNextState = null;
        myGridCoordinate = coordinate;
    }

    /* Getters and Setters */


    public Coordinate getMyGridCoordinate() {
        return this.myGridCoordinate;
    }
    
    public void setMyGridCoordinate(Coordinate myGridCoordinate) {
        this.myGridCoordinate = myGridCoordinate;
    }

    public State getMyCurrentState () {
        return myCurrentState;
    }

    public void setMyCurrentState (State myCurrentState) {
        this.myCurrentState = myCurrentState;
    }

    public State getMyNextState () {
        return myNextState;
    }

    public void setMyNextState (State myNextState) {
        this.myNextState = myNextState;
    }

    public void updateState () {
        myCurrentState = myNextState;
        myNextState = null;
    }
    
    public Color getColor() {
        return this.getMyCurrentState().getColor();
    }

}
