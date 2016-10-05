package cell;

import grid.Coordinate;
import javafx.scene.paint.Color;


/**
 * This is the base class for every "cell" in every simulation. Every cell has a coordinate, a
 * state, and a next state. The subclasses add extra methods, such as update (if they have
 * persistent data), as well as other simulation-specific calculations. See AntCell or SharkCell for
 * an example of how to extend this class.
 * 
 * @author Michael Schroeder
 *
 */
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

    public Coordinate getMyGridCoordinate () {
        return this.myGridCoordinate;
    }

    public void setMyGridCoordinate (Coordinate gridCoordinate) {
        this.myGridCoordinate = gridCoordinate;
    }

    public State getMyCurrentState () {
        return myCurrentState;
    }

    public void setMyCurrentState (State currentState) {
        this.myCurrentState = currentState;
    }

    public State getMyNextState () {
        return myNextState;
    }

    public void setMyNextState (State nextState) {
        this.myNextState = nextState;
    }

    public void updateState () {
        myCurrentState = myNextState;
        myNextState = null;
    }

    public Color getColor () {
        return this.getMyCurrentState().getColor();
    }

}
