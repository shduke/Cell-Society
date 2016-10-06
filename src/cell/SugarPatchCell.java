package cell;

import grid.Coordinate;
import javafx.scene.paint.Color;


/**
 * 
 * @author Michael Schroeder
 *
 */
public class SugarPatchCell extends Cell {

    private int mySugar;
    private int myMaxSugar;
    private SugarAgentCell myAgent;
    private SugarAgentCell myNextAgent;

    public SugarPatchCell (State currentState,
                           Coordinate coordinate,
                           int maxSugar) {
        super(currentState, coordinate);
        myMaxSugar = maxSugar;
        mySugar = myMaxSugar;
        myAgent = null;
        myNextAgent = null;

    }

    /**
     * Update the sugar agent cell, myAgent
     */
    public void update () {
        myAgent = myNextAgent;
        myNextAgent = null;
    }

    /**
     * 
     * @return mySugar
     */
    public int getSugar () {
        return mySugar;
    }

    /**
     * Grows the value of mySugar
     * 
     * @param rate
     */
    public void growBack (int rate) {
        if (mySugar + rate <= myMaxSugar) {
            mySugar += rate;
        }
    }

    /**
     * 
     * @return SugarAgentCell
     */
    public SugarAgentCell getAgent () {
        return myAgent;
    }

    /**
     * Kills the SugarAgentcell
     */
    public void killAgent () {
        myAgent = null;
    }

    /**
     * Sets mySugar
     * 
     * @param sugar
     */
    public void setSugar (int sugar) {
        mySugar = sugar;
    }

    /**
     * 
     * Determines whether my SugarAgentCell and next SugarAgentCell are empty
     */
    public boolean isEmpty () {
        return myAgent == null && myNextAgent == null;
    }

    /**
     * Intializes SugarAgentCell
     * 
     * @param agent
     */
    public void initAgent (SugarAgentCell agent) {
        myAgent = agent;
    }

    /**
     * 
     * @return boolean if myAgent exists
     */
    public boolean hasAgent () {
        return myAgent != null;
    }

    /**
     * Adds new agent
     * 
     * @param agent
     */
    public void addAgent (SugarAgentCell agent) {
        myNextAgent = agent;
    }

    /**
     * Sets the color for the different sugar cells
     */
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
