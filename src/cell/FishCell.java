package cell;

import javafx.geometry.Point2D;
import javafx.scene.Node;


public class FishCell extends Cell {

    private int myMaxBreedTime;
    private int myCurrentBreedTime;

    public FishCell (Point2D coordinate, int breedTime, Node node) {
        super(State.FISH, null, coordinate, node);
        myMaxBreedTime = breedTime;
        myCurrentBreedTime = myMaxBreedTime;
    }

    public void update () {
        myCurrentBreedTime = myCurrentBreedTime == 0 ? myMaxBreedTime : myCurrentBreedTime--;
    }

    private void breed () {

    }

}
