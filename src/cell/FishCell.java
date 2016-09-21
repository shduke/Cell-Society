package cell;

import javafx.geometry.Point2D;
import javafx.scene.Node;


public class FishCell extends Cell {

    private int myMaxBreedTime;
    private int myCurrentBreedTime;

    public FishCell (int row, int col, int breedTime, Node node) {
        super(State.FISH, row, col, node);
        myMaxBreedTime = breedTime;
        myCurrentBreedTime = myMaxBreedTime;
    }

    public void update () {
        myCurrentBreedTime = myCurrentBreedTime == 0 ? myMaxBreedTime : myCurrentBreedTime--;
    }

}
