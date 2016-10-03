package cell;

import grid.Coordinate;
import simulation.PredatorPreySimulation.PredatorPreyState;


public class EmptyCell extends Cell {

    public EmptyCell (Coordinate coordinate) {
        super(PredatorPreyState.EMPTY, coordinate);
    }
}
