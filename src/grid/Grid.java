package grid;

import java.util.*;
import cell.*;

public abstract class Grid {

    private Cell[][] myCells;
    
    public Cell[][] getMyCells() {
        return myCells;
    }
    
    public int getNumRows() {
        return myCells.length;
    }
    
    public int getNumColumns() {
        return myCells[0].length;
    }
    
    /**
     * TODO--return neighbors of the cell at this location
     * @param row
     * @param col
     * @return
     */
    public List<Cell> getNeighbors(int row, int col){
        return null;
    }
    
    
}
