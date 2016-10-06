package cell;

import grid.Coordinate;
import simulation.PredatorPreySimulation.PredatorPreyState;
/**
 * For use in PredatorPreySimulation (this is the prey)
 * @author Michael Schroeder
 *
 */

public class FishCell extends Cell {

    private int myMaxBreedTime;
    private int myCurrentBreedTime;

    // Constructors

    /**
     * Constructs a new FishCell at coord, with the specified breedTime
     * 
     * @param coord - the coordinate of the new FishCell
     * @param breedTime - the FishCell's breedTime
     */
    public FishCell (Coordinate coord, int breedTime) {
        super(PredatorPreyState.FISH, coord);
        myMaxBreedTime = breedTime;
        myCurrentBreedTime = myMaxBreedTime;
    }

    /**
     * Constructs a new FishCell with the same properties as fish, at coord
     * 
     * @param fish - the FishCell to be "copied"
     * @param coord - the coordinate to "copy" fish to
     */
    public FishCell (FishCell fish, Coordinate coord) {
        super(PredatorPreyState.FISH, coord);
        myMaxBreedTime = fish.myMaxBreedTime;
        myCurrentBreedTime = fish.myCurrentBreedTime;
    }

    /**
     * Update this FishCell's breedTime. Called every step of PredatorPreySimulation
     */
    public void update () {
        myCurrentBreedTime = myCurrentBreedTime == 0 ? myMaxBreedTime : myCurrentBreedTime - 1;
    }

    /**
     * @return - returns true if this FishCell can breed on the current step
     */
    public boolean canBreed () {
        return myCurrentBreedTime == 0;
    }

}
