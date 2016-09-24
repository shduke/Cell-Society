package cell;

import grid.Coordinate;

public class EmptyCell extends Cell {

    public EmptyCell(Coordinate coordinate){
        super(State.EMPTY, coordinate);
    }
}
