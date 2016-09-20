package cell;

public abstract class Cell {
    
    private State myCurrentState;
    private State myNextState;
    
    public State getMyCurrentState () {
        return myCurrentState;
    }
    public void setMyCurrentState (State myCurrentState) {
        this.myCurrentState = myCurrentState;
    }
    public State getMyNextState () {
        return myNextState;
    }
    public void setMyNextState (State myNextState) {
        this.myNextState = myNextState;
    }
    public void updateState () {
        myCurrentState = myNextState;
        myNextState = null;
    }
    
    
    
    
}
