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
    private String myOrientation;
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
    
    public ForagingAntCell forage(List<ForagingAntCell> neighbors){
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
                this.setOrientation(cell);
                return cell;
            }
        } 
        this.setOrientation(neighbors.get(0));
        return neighbors.get(0);
    }
    
    public ForagingAntCell goHome(List<ForagingAntCell> neighbors) {
        Iterator<ForagingAntCell> iter = neighbors.iterator();
        while(iter.hasNext()){
            if(iter.next().fullOfAnts()){
                iter.remove();
            }
        }
        
        return null;
    }
    private double getProbSum(List<ForagingAntCell> neighbors){
        double sum = 0;
        for(ForagingAntCell cell : neighbors){
            sum+=cell.getProb();
        }
        return sum;
    }
    
    public void setOrientation(Cell otherCell){
        Coordinate otherCoord = otherCell.getMyGridCoordinate();
        Coordinate thisCoord = this.getMyGridCoordinate();
        
        int x = (int) (otherCoord.getX() - thisCoord.getX());
        int y = (int)(otherCoord.getY() - thisCoord.getY());
        
        String first = "";
        String second = "";
        switch(x) {
            case -1:
                first = "TOP";
                break;
            case 0:
                first = "MID";
                break;
            default:
                first = "BOTTOM";
                break;
        }
        switch(y){
            case -1:
                second = "LEFT";
                break;
            case 0:
                second = "MID";
                break;
            default:
                second = "RIGHT";
                break;
        }
      
        StringBuilder orientation = new StringBuilder();
        
        orientation.append(first);
        orientation.append(second);
        this.myOrientation = orientation.toString();
        
    }
    
}
