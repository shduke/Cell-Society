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

    /**
     * Makes a copy of the parameter cell
     * 
     * @param cell - the cell to copy
     */
    public SugarAgentCell (SugarAgentCell cell) {
        super(SugarState.EMPTY, cell.getMyGridCoordinate());
        this.setMyNextState(SugarState.ALIVE);
        myVision = cell.myVision;
        mySugar = cell.mySugar;
        myMetabolism = cell.myMetabolism;
    }

    /**
     * Finds the best location to move to, in terms of sugar
     * 
     * @param neighbors - list of all the neighboring SugarPatchCells
     * @param currentSugar
     * @return
     */
    public SugarPatchCell findSugar (List<Cell> neighbors) {
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
     * Updates this cell's amount of sugar every step
     */
    public void update () {
        mySugar -= myMetabolism;
        if (mySugar <= 0) {
            this.setMyNextState(SugarState.DEAD);
        }
    }

    /**
     * 
     * @return - true if this cell is dead
     */
    public boolean isDead () {
        return this.mySugar <= 0;
    }

    /**
     * Distance this cell can see
     * 
     * @return - number of cells away this cell can "see"
     */
    public int getVision () {
        return myVision;
    }

    /**
     * Eat the sugar at the given cell
     * 
     * @param cell - cell to take sugar from
     */
    public void eat (SugarPatchCell cell) {
        mySugar += Math.min(myMetabolism, cell.getSugar());
        cell.setSugar(Math.max(0, cell.getSugar() - myMetabolism));
    }
}
