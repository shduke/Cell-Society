
package cell;

import grid.Coordinate;

/**
 * @author Michael Schroeder
 * 
 *         This cell represents the "Agents" in the Segregation Simulation. Its state represents
 *         which population it falls into, and how it will segregate. Every AgentCell has a
 *         mySatisfiedRatio of
 *         similar neighbors to other neighbors, which determines whether or not it will move to a
 *         new location.
 * 
 */
package cell;

import grid.Coordinate;


/**
 * 
 * @author Michael Schroeder
 *
 */
public class AgentCell extends Cell {

    private double mySatisfiedRatio;

    public AgentCell (State currentState, Coordinate coordinate, double satisfied) {
        super(currentState, coordinate);
        mySatisfiedRatio = satisfied;
    }

    /**
     * Creates a new AgentCell at the specified coordinate with the same properties as cell
     * 
     * @param cell - the cell that is being "copied"
     * @param coordinate - the location to "copy" cell to
     */
    public AgentCell (AgentCell cell, Coordinate coordinate) {
        super(cell.getMyCurrentState(), coordinate);
        mySatisfiedRatio = cell.mySatisfiedRatio;
    }

    /**
     * Check whether or not the Agent is satisfied compared to currentRatio
     * 
     * @param currentRatio
     * @return true if Agent is satisfied, false if not
     */
    public boolean isSatisfied (double currentRatio) {
        return mySatisfiedRatio <= currentRatio;
    }

}