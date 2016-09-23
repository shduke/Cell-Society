package cell;

import grid.Coordinate;
import javafx.scene.Node;


public class FireCell extends Cell {
    /*public enum FireCellStates {
                                EMPTY,
                                TREE,
                                BURNING
    };*/

    public FireCell (State currentState, Coordinate coordinate) {
        super(currentState, coordinate);
    }

}
