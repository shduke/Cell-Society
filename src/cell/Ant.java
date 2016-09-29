package cell;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import grid.Coordinate;
import grid.Neighbor;
public class Ant extends Cell {

    private int myLifetime;
    private boolean isMoving;
    public Ant (Coordinate coordinate, int lifetime){
        
        super(State.FOODSEARCH, coordinate);
        myLifetime = lifetime;
    }
    
    
    public void update(){
        isMoving = false;
        myLifetime--;
        if(myLifetime==0){
           setMyNextState(State.DEAD);
        }
    }
    
    private void willMove() {
        isMoving = true;
    }
    public boolean isDeadOrMoving(){
        return getMyNextState() == State.DEAD || isMoving;
    }
    
    public ForagingAntCell move(List<ForagingAntCell> neighbors){
        Iterator<ForagingAntCell> iter = neighbors.iterator();
        while(iter.hasNext()){
            if(iter.next().fullOfAnts()){
                iter.remove();
            }
        }
        if(neighbors.size()==0){
            return null;
        }
        this.willMove();
        double total = getProbSum(neighbors);
        double random = new Random().nextDouble()*total;
        double counter = 0;
        for(ForagingAntCell cell : neighbors) {
            counter+=cell.getProb();
            if(random < counter){
                
                return cell;
            }
        } 
        return neighbors.get(0);
    }
    
    private double getProbSum(List<ForagingAntCell> neighbors){
        double sum = 0;
        for(ForagingAntCell cell : neighbors){
            sum+=cell.getProb();
        }
        return sum;
    }
    
}
