package cell;

import javafx.geometry.Point2D;
import javafx.scene.Node;

public class SharkCell extends Cell {
    
    private int myMaxBreedTime;
    private int myCurrentBreedTime;
    
    public SharkCell (Point2D coordinate, int breedTime, Node node) {
        super(State.SHARK, null, coordinate, node);
        myMaxBreedTime = breedTime;
        myCurrentBreedTime = myMaxBreedTime;
    }

}
