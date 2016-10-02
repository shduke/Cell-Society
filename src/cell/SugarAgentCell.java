package cell;

import java.util.List;
import grid.Coordinate;


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
        super(State.EMPTY, cell.getMyGridCoordinate());
        this.setMyNextState(State.ALIVE);
        myVision = cell.myVision;
        mySugar = cell.mySugar;
        myMetabolism = cell.myMetabolism;
    }

    public SugarPatchCell findSugar (List<Cell> neighbors, int currentSugar) {
        SugarPatchCell bestCell = null;
        int maxSugar = currentSugar;
        for (Cell c : neighbors) {
            SugarPatchCell cell = (SugarPatchCell) c;
            if (cell.getSugar() > maxSugar && cell.isEmpty()) {
                bestCell = cell;
                maxSugar = cell.getSugar();
            }
        }
        return bestCell;
    }

    public void update () {
        mySugar -= myMetabolism;
        if (mySugar <= 0) {
            this.setMyNextState(State.DEAD);
        }
        else {
            this.setMyNextState(State.ALIVE);
        }
    }

    public void eat (SugarPatchCell cell) {
        mySugar += Math.min(myMetabolism, cell.getSugar());
        cell.setSugar(Math.max(0, cell.getSugar() - myMetabolism));
    }
}
