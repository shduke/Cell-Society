package cell;

import javafx.geometry.Point2D;
import javafx.scene.Node;


public abstract class Cell {

    private State myCurrentState;
    private State myNextState;
    private Node myNode;

    public Cell (State currentState, Point2D coordinate, Node node) {
        myCurrentState = currentState;
        myNextState = null;
        myNode = node;
    }

    /* Getters and Setters */

    public Point2D getMyCoordinate () {
        return new Point2D(myNode.getLayoutX(), myNode.getLayoutY());
    }

    public void setMyCoordinate (Point2D myCoordinate) {
        myNode.setLayoutX(myCoordinate.getX());
        myNode.setLayoutX(myCoordinate.getY());
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
