package cell;

import grid.Coordinate;
import simulation.PredatorPreySimulation.PredatorPreyState;


/**
 * An empty cell to fill in the spaces in each simulation's grid that aren't filled in with specific
 * "active" cells
 * 
 * @author Michael Schroeder
 *
 */

public class EmptyCell extends Cell {

    public EmptyCell (Coordinate coordinate) {
        super(PredatorPreyState.EMPTY, coordinate);
    }

    /**
     * Constructs a new EmptyCell at the given coordinate, with the given state.
     * 
     * @param coordinate - the coordinate of the new cell
     * @param state - the starting state of the new cell
     */
    public EmptyCell (Coordinate coordinate, State state) {
        super(state, coordinate);
    }
}
