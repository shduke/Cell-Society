package grid;

import cell.*;

public abstract class Grid {

    Cell[][] myCells;
    
    public Cell[][] getMyCells() {
        return myCells;
    }
    
    public int getNumRows() {
        return myCells.length;
    }
    
    public int getNumColumns() {
        return myCells[0].length;
    }
    
}
