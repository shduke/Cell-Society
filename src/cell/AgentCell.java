package cell;

import grid.Coordinate;


public class AgentCell extends Cell {

    private double mySatisfiedRatio;

    public AgentCell (State currentState, Coordinate coordinate, double satisfied) {
        super(currentState, coordinate);
        mySatisfiedRatio = satisfied;

        // TODO Auto-generated constructor stub
    }
    
    public AgentCell (AgentCell cell, Coordinate coordinate){
        super(cell.getMyCurrentState(), coordinate);
        mySatisfiedRatio = cell.mySatisfiedRatio;
    }
    public boolean isSatisfied (double currentRatio) {
        return mySatisfiedRatio <= currentRatio;
    }

}
