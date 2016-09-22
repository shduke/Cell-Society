package simulation;

import grid.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import cell.*;
import cell.State;
import javafx.scene.shape.*;


public class PredatorPreySimulation extends Simulation {

    private int myPreyBreedTime;
    private int myPredatorBreedTime;
    private int myFishEnergy;
    private Grid myGrid;

    @Override
    public void step () {

        updateSharks();
        updateFishes();
        // TODO Auto-generated method stub

    }

    private void updateSharks () {

        for (int i = 0; i < myGrid.getNumRows(); i++) {
            for (int j = 0; j < myGrid.getNumColumns(); j++) {
                Cell cell = myGrid.getCellGrid()[i][j];
                if (cell.getMyCurrentState() == State.SHARK) {
                    if (cell.getMyNextState() == State.DEAD) {
                        kill((SharkCell) cell);
                    }
                    else {
                        updateShark((SharkCell) cell);
                    }
                }
            }
        }
    }

    private void updateFishes () {
        for (int i = 0; i < myGrid.getNumRows(); i++) {
            for (int j = 0; j < myGrid.getNumColumns(); j++) {
                Cell cell = myGrid.getCellGrid()[i][j];
                if (cell.getMyCurrentState() == State.FISH) {
                    if (cell.getMyNextState() == State.DEAD) {
                        kill((FishCell) cell);
                    }
                    else {
                        updateFish((FishCell) cell);
                    }
                }
            }
        }
    }

    public void kill (Cell cell) {
        cell.setMyNextState(State.EMPTY);
    }

    private void updateShark (SharkCell shark) {
        List<Cell> neighbors = myGrid.getNeighbors(shark.getMyGridCoordinate());
        List<Cell> canMoveOrBreed = getOpenCells(neighbors);
        List<Cell> foodCells = getEdibleCells(neighbors);
        
        if(shark.getMyNextState()==State.DEAD){
            kill(shark);
        }
        else if(foodCells.size() > 0){
            FishCell food = (FishCell)foodCells.get(new Random().nextInt(foodCells.size()));
            shark.eat(food);
            shark.setMyNextState(shark.getMyCurrentState());
            breed(shark, canMoveOrBreed);
        }
        else{
            breed(shark, canMoveOrBreed);
            move(shark);
        }
    }

    private void updateFish (FishCell fish) {

    }

    private List<Cell> getOpenCells (List<Cell> neighbors) {
        List<Cell> openCells = new ArrayList<Cell>();
        for(Cell c : neighbors){
            if(c.getMyCurrentState()==State.EMPTY && c.getMyNextState() == null){
                openCells.add(c);
            }
        }
        return openCells;
    }
    
    private List<Cell> getEdibleCells(List<Cell> neighbors) {
        List<Cell> edibleCells = new ArrayList<Cell>();
        for(Cell c : neighbors) {
            if(c.getMyCurrentState() == State.FISH){
                edibleCells.add(c);
            }
        }
        return edibleCells;
    }
    
    private void breed(Cell cell, List<Cell> openCells) {
        
        if(cell.getMyCurrentState() == State.SHARK){
            if(((SharkCell)cell).canBreed()){
                Cell newShark = openCells.get(new Random().nextInt(openCells.size()));
                makeNewShark((SharkCell)cell, newShark);//newShark = new SharkCell(cell.getMyGridCoordinate().getX(),cell.getMyGridCoordinate().getY(),myPredatorBreedTime,new Node(this.getMyShape()));
            }
        }
    }
    
    private void makeNewShark(SharkCell oldShark, Cell newShark){
        newShark = new SharkCell(oldShark.getMyGridCoordinate(),myPredatorBreedTime, getMyCellShape());
    }
    private void move(Cell cell) {
        
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
