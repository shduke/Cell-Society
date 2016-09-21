package cell;

import javafx.geometry.Point2D;
import javafx.scene.Node;


public abstract class Cell {

    private State myCurrentState;
    private State myNextState;
    private int myRow, myCol;
    private Node myNode;

    public Cell (State currentState, int row, int col, Node node) {
        myCurrentState = currentState;
        myNextState = null;
        myRow = row;
        myCol = col;
        myNode = node;
    }

    /* Getters and Setters */


    public Node getMyNode () {
        return myNode;
    }

    public int getMyRow () {
        return myRow;
    }

    public void setMyRow (int myRow) {
        this.myRow = myRow;
    }

    public int getMyCol () {
        return myCol;
    }

    public void setMyCol (int myCol) {
        this.myCol = myCol;
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
