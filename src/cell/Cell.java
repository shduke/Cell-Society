package cell;

import javafx.geometry.Point2D;
import javafx.scene.Node;


public abstract class Cell {

    private State myCurrentState;
    private State myNextState;
    private Point2D myCoordinate;
    private Node myNode;

    public Cell (State currentState, Point2D coordinate, Node node) {
        myCurrentState = currentState;
        myNextState = null;
        myCoordinate = coordinate;
        myNode = node;
    }

    /* Getters and Setters */

    public Point2D getMyCoordinate () {
        return myCoordinate;
    }

    public void setMyCoordinate (Point2D myCoordinate) {
        this.myCoordinate = myCoordinate;
    }

    public Node getMyNode () {
        return myNode;
    }

    public void setMyNode (Node myNode) {
        this.myNode = myNode;
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

}
