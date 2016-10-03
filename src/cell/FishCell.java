package cell;

import grid.Coordinate;
import simulation.PredatorPreySimulation.PredatorPreyState;


public class FishCell extends Cell {

    private int myMaxBreedTime;
    private int myCurrentBreedTime;

    public FishCell (int row, int col, int breedTime) {
        super(PredatorPreyState.FISH, new Coordinate(row, col));
        myMaxBreedTime = breedTime;
        myCurrentBreedTime = myMaxBreedTime;
    }
    public FishCell(Coordinate coord, int breedTime) {
        super(PredatorPreyState.FISH, coord);
        myMaxBreedTime = breedTime;
        myCurrentBreedTime = myMaxBreedTime;
    }
    public FishCell(FishCell fish, Coordinate coord){
        super(PredatorPreyState.FISH, coord);
        myMaxBreedTime = fish.myMaxBreedTime;
        myCurrentBreedTime = fish.myCurrentBreedTime;
    }
    public void update () {
        myCurrentBreedTime = myCurrentBreedTime == 0 ? myMaxBreedTime : myCurrentBreedTime - 1;
    }

    public boolean canBreed () {
        return myCurrentBreedTime == 0;
    }

}
