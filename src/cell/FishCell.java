package cell;

import grid.Coordinate;
import javafx.geometry.Point2D;
import javafx.scene.Node;


public class FishCell extends Cell {

    private int myMaxBreedTime;
    private int myCurrentBreedTime;

    public FishCell (int row, int col, int breedTime) {
        super(State.FISH, new Coordinate(row, col));
        myMaxBreedTime = breedTime;
        myCurrentBreedTime = myMaxBreedTime;
    }

    public void update () {
        myCurrentBreedTime = myCurrentBreedTime == 0 ? myMaxBreedTime : myCurrentBreedTime--;
    }

}
