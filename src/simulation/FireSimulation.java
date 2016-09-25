package simulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import cell.Cell;
import cell.FireCell;
import cell.State;
import grid.Coordinate;
import grid.Grid;
import grid.GridView;
import grid.Neighbors;
import grid.RectangleGridView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class FireSimulation extends Simulation {
    Map<Coordinate, Cell> input = new HashMap<Coordinate, Cell>();   
    
    public FireSimulation () {
        

        input.put(new Coordinate(2, 2), new FireCell(State.BURNING,
                               new Coordinate(2, 2)));
        //input.add(new FireCell(State.TREE, new Coordinate(3,3)));
        generateMap();
        setGrid(new Grid(5, 5, input));
        setGridView(new RectangleGridView(new Dimension2D(500, 500), getGrid()));
    }
    
    /*public void printMap() {
        for(Cell cell : input.values()) {
            System.out.println(cell.getMyGridCoordinate().getX() + " " + cell.getMyGridCoordinate().getY());
        }
    }*/
    
//Allows them to specify what coordinates they went, the rest are set to default by a function, gets bounds and starter map from config
    public void generateMap() {
       for(int r = 0; r < 5; r++) {
           for(int c = 0; c < 5; c++) {
               Coordinate coordinate = new Coordinate(r,c);
               if(!input.containsKey(coordinate)) {
                   FireCell cell = new FireCell(State.TREE, coordinate);
                   if(r == 0 || c == 0 || r == 4 || c == 4) {
                       cell.setMyCurrentState(State.EMPTY);
                   }
                   input.put(coordinate, cell);
               }

           }
       }
    }
    
    //very temporary code, just here to make it work
    @Override
    public void step () {
        //getGrid().applyFuncToCell(FireSimulation::setNextState);      HOW DO I DO THIS?
        for(Cell cell : getGrid().getImmutableCellGrid().values()) {
            setNextState(cell);
        }
        updateGrid();
        
    }

    // test filler code
    @Override
    public void setNextState (Cell cell) {
        //System.out.println(cell.getMyCurrentState());
        if (cell.getMyCurrentState().equals(State.EMPTY)) {
            cell.setMyNextState(State.TREE);
        }
        else if (cell.getMyCurrentState().equals(State.TREE)) {
            cell.setMyNextState(State.BURNING);
        }
        else {
            cell.setMyNextState(State.EMPTY);
        }
    }

    @Override
    public void start () {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void init () {
        // TODO Auto-generated method stub
        
    }

}
