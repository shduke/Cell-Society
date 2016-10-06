package cell;

import java.util.List;
import grid.Coordinate;
import simulation.SugarSimulation.SugarState;


/**
 * 
 * @author Michael Schroeder
 *
 */
public class SugarAgentCell extends Cell {

    private int myVision;
    private int mySugar;
    private int myMetabolism;

    public SugarAgentCell (State currentState,
                           Coordinate coordinate,
                           int vision,
                           int sugar,
                           int metabolism) {
        super(currentState, coordinate);
        myVision = vision;
        mySugar = sugar;
        myMetabolism = metabolism;
    }

    public SugarAgentCell (SugarAgentCell cell) {
        super(SugarState.EMPTY, cell.getMyGridCoordinate());
        this.setMyNextState(SugarState.ALIVE);
        myVision = cell.myVision;
        mySugar = cell.mySugar;
        myMetabolism = cell.myMetabolism;
    }

    /**
     * Allows the cells to look at their neighbors in search of food
     * 
     * @param neighbors
     * @param currentSugar
     * @return bestCell
     */
    public SugarPatchCell findSugar (List<Cell> neighbors, int currentSugar) {
        SugarPatchCell bestCell = null;
        int maxSugar = -1;
        for (Cell c : neighbors) {
            SugarPatchCell cell = (SugarPatchCell) c;
            if (cell.getSugar() > maxSugar && cell.isEmpty()) {
                bestCell = cell;
                maxSugar = cell.getSugar();
            }
        }
        return bestCell;
    }

    /**
     * Update mySugar and set state as Dead if sugar is at zero or below
     */
    public void update () {
        mySugar -= myMetabolism;
        if (mySugar <= 0) {
            this.setMyNextState(SugarState.DEAD);
        }
    }

    /**
     * 
     * @return whether or not cell is dead
     */
    public boolean isDead () {
        return this.mySugar <= 0;
    }

    /**
     * 
     * @return myVision
     */
    public int getVision () {
        return myVision;
    }

    /**
     * Increments mySugar and sets the sugar in a cell (based off of metabolism)
     * 
     * @param SugarPatchCell
     */
    public void eat (SugarPatchCell cell) {
        mySugar += Math.min(myMetabolism, cell.getSugar());
        cell.setSugar(Math.max(0, cell.getSugar() - myMetabolism));
    }
}
