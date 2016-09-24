package cell;

import grid.Coordinate;
import javafx.geometry.Point2D;
import javafx.scene.Node;


public class SharkCell extends Cell {

    private int myMaxBreedTime;
    private int myCurrentBreedTime;
    private int myMaxHealth;
    private int myCurrentHealth;
    

    public SharkCell (Coordinate coordinate, int breedTime, int maxHealth) {
        
        super(State.SHARK, coordinate);
        myMaxBreedTime = breedTime;
        myCurrentBreedTime = myMaxBreedTime;
        myMaxHealth = maxHealth;
        myCurrentHealth = myMaxHealth;
    }
    
    public SharkCell (SharkCell shark,Coordinate coordinate){
        super(State.SHARK,coordinate);
        myMaxBreedTime = shark.myMaxBreedTime;
        myCurrentBreedTime = shark.myCurrentBreedTime;
        myMaxHealth = shark.myMaxHealth;
        myCurrentHealth = shark.myCurrentHealth;
        
    }
    
    public void eat(FishCell fish){
        myCurrentHealth = myMaxHealth;
        fish.setMyNextState(State.DEAD);
    }
    
    public void update () {
        myCurrentBreedTime = myCurrentBreedTime == 0 ? myMaxBreedTime : myCurrentBreedTime--;
        if(myCurrentHealth == 0){
            this.setMyNextState(State.DEAD);
        }
        else{
            myCurrentHealth--;
        }
    }
    
    public void swim(Cell cell){
        Coordinate currentCoord = this.getMyGridCoordinate();
        Coordinate newCoord = cell.getMyGridCoordinate();
        this.setMyGridCoordinate(newCoord);
        cell.setMyGridCoordinate(currentCoord);
        
    }
    public boolean canBreed () {
        return myCurrentBreedTime == 0;
    }

}
