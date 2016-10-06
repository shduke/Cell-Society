package cell;

import grid.Coordinate;
import simulation.PredatorPreySimulation.PredatorPreyState;

/**
 * 
 * @author Michael Schroeder
 *
 */
public class SharkCell extends Cell {

    private int myMaxBreedTime;
    private int myCurrentBreedTime;
    private int myMaxHealth;
    private int myCurrentHealth;

    /**
     * Creates a new SharkCell with the given parameters
     * 
     * @param coordinate - the coordinate of the new cell
     * @param breedTime - how long it takes this shark to breed
     * @param maxHealth - how long this Shark can survive without eating a Fish
     */
    public SharkCell (Coordinate coordinate, int breedTime, int maxHealth) {

        super(PredatorPreyState.SHARK, coordinate);
        myMaxBreedTime = breedTime;
        myCurrentBreedTime = myMaxBreedTime;
        myMaxHealth = maxHealth;
        myCurrentHealth = myMaxHealth;
    }

    /**
     * Creates a new SharkCell with the properties of shark
     * 
     * @param shark - the SharkCell to be "copied" to the new cell
     * @param coordinate - the coordinate that the cell will be "copied" to
     */
    public SharkCell (SharkCell shark, Coordinate coordinate) {
        super(PredatorPreyState.SHARK, coordinate);
        myMaxBreedTime = shark.myMaxBreedTime;
        myCurrentBreedTime = shark.myCurrentBreedTime;
        myMaxHealth = shark.myMaxHealth;
        myCurrentHealth = shark.myCurrentHealth;

    }

    /**
     * Eats and kills the fish and set health to max
     * 
     * @param fish - the fish to be eaten
     */
    public void eat (FishCell fish) {
        myCurrentHealth = myMaxHealth;
        fish.setMyNextState(PredatorPreyState.DEAD);
    }

    /**
     * Update this shark's breed time and health. Called every step by PredatorPreySimulation
     */
    public void update () {
        myCurrentBreedTime = myCurrentBreedTime == 0 ? myMaxBreedTime : myCurrentBreedTime - 1;
        if (myCurrentHealth == 0) {
            this.setMyNextState(PredatorPreyState.DEAD);
        }
        else {
            myCurrentHealth--;
        }
    }

    /**
     * Returns whether or not this Shark can breed
     * 
     * @return - true if this shark can breed on the current step
     */
    public boolean canBreed () {
        return myCurrentBreedTime == 0;
    }

}
