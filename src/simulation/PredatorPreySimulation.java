package simulation;

import grid.*;
import cell.*;
import cell.State;


public class PredatorPreySimulation extends Simulation {

    private int myPreyBreedTime;
    private int myPredatorBreedTime;
    private int myFishEnergy;
    private Grid myGrid;

    @Override
    public void step () {
        
        updateSharks();
        updateFish();
        // TODO Auto-generated method stub

    }

    private void updateSharks () {

        for (int i = 0; i < myGrid.getNumRows(); i++) {
            for (int j = 0; j < myGrid.getNumColumns(); j++) {
                Cell cell = myGrid.getMyCells()[i][j];
                if (cell.getMyCurrentState() == State.SHARK) {
                    if (cell.getMyNextState() == State.DEAD) {
                        kill((SharkCell) cell);
                    }
                    else {
                        updateShark((SharkCell) cell);
                    }
                }
            }
        }
    }
    
    private void updateFish () {

    }

    public void kill (Cell cell) {
        
    }
    
    private void updateShark(SharkCell cell){
        
    }
}
