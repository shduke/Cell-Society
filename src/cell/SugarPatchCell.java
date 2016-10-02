package cell;

import grid.Coordinate;
import javafx.scene.paint.Color;


public class SugarPatchCell extends Cell {

    private int mySugar;
    private int myMaxSugar;
    // private int myGrowBackRate;
    private SugarAgentCell myAgent;

    public SugarPatchCell (State currentState,
                           Coordinate coordinate,
                           int maxSugar) {
        super(currentState, coordinate);
        myMaxSugar = maxSugar;
        mySugar = myMaxSugar;

    }

    public int getSugar () {
        return mySugar;
    }

    public void growBack (int rate) {
        mySugar += rate;
    }

    public SugarAgentCell getAgent () {
        return myAgent;
    }

    public void killAgent () {
        myAgent = null;
    }

    public void setSugar (int sugar) {
        mySugar = sugar;
    }

    public boolean isEmpty () {
        return myAgent == null;
    }

    public void addAgent (SugarAgentCell agent) {
        myAgent = agent;
    }

    @Override
    public Color getColor () {
        if (!isEmpty()) {
            return Color.RED;
        }
        Color orange = Color.ORANGE;
        for (int i = 0; i < mySugar; i++) {
            orange = orange.darker();
        }
        return orange;
    }
}
