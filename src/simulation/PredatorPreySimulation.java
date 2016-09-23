package simulation;

import grid.*;
import javafx.geometry.Dimension2D;
import java.util.*;
import cell.*;
import javafx.scene.shape.*;


/**
 * Commented out just so I can run/test the program
 */
public class PredatorPreySimulation extends Simulation {

    /*
     * PredatorPreySimulation (Dimension2D gridViewSize) {
     * super(gridViewSize);
     * // TODO Auto-generated constructor stub
     * }
     */
    private boolean diagonalNeighbors;
    private int myPreyBreedTime;
    private int myPredatorBreedTime;
    private int mySharkMaxHealth;
    private int myFishEnergy;
    private int counter = 1;

    public PredatorPreySimulation () {
        diagonalNeighbors = false;
        myPredatorBreedTime = 1000;
        mySharkMaxHealth = 500000;
        ArrayList<Cell> input = new ArrayList<Cell>();

        input.add(new SharkCell(
                                new Coordinate(2, 2), myPredatorBreedTime, mySharkMaxHealth));
        input.add(new SharkCell(new Coordinate(3, 3), myPredatorBreedTime, mySharkMaxHealth));
        for(int i = 1; i <= 5; i++){
            for(int j = 1; j<=5; j++){
                if(!(i==2 && j==2) && !(i==3 && j==3)){
                    EmptyCell cell = new EmptyCell(new Coordinate(i,j));
                    
                   
                    input.add(cell);
                    
                }
            }
        }
        
        setGrid(new RectangleGrid(5, 5, input));
        setGridView(new GridView(new Dimension2D(500, 500), "Rectangle", getGrid()));
    }

    @Override
    public void step () {
        if (counter % 1000 == 0){
        updateSharks();
        updateFishes();
        getGrid().updateGrid();
        System.out.println(counter);
        
            this.getGridView().updateView();
        }
        counter++;
        // TODO Auto-generated method stub

    }

    private void updateSharks () {
        Iterator<Map.Entry<Coordinate, Cell>> cells = getGrid().getCellGridIterator();
        while (cells.hasNext()) {
            Cell cell = cells.next().getValue();
            
            if(cell.getMyCurrentState() == State.EMPTY && cell.getMyNextState() == null){
                System.out.println("This cell is empty: " + cell.getClass());
                cell.setMyNextState(State.EMPTY);
            }
            
            if (cell.getMyCurrentState() == State.SHARK) {
                System.out.println(cell.getMyCurrentState() + " is in state " + cell.getMyCurrentState());
                if (cell.getMyNextState() == State.DEAD) {
                    kill((SharkCell) cell);
                }
                else {
                    updateShark((SharkCell) cell);
                }
            }
        }
    }

    private void updateFishes () {
        Iterator<Map.Entry<Coordinate, Cell>> cells = getGrid().getCellGridIterator();
        while (cells.hasNext()) {
            Cell cell = cells.next().getValue();
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

    public void kill (Cell cell) {
        cell.setMyNextState(State.EMPTY);
    }

    private void updateShark (SharkCell shark) {
        Neighbors neighbors = getGrid().getNeighbors(shark, diagonalNeighbors);
        List<Cell> canMoveOrBreed = getOpenCells(neighbors.getNeighbors());
        List<Cell> foodCells = getEdibleCells(neighbors.getNeighbors());

        if (shark.getMyNextState() == State.DEAD) {
            kill(shark);
        }
        else if (foodCells.size() > 0) {
            FishCell food = (FishCell) foodCells.get(new Random().nextInt(foodCells.size()));
            shark.eat(food);
            shark.setMyNextState(shark.getMyCurrentState());
            breed(shark, canMoveOrBreed);
        }
        else {
            breed(shark, canMoveOrBreed);
            move(shark, canMoveOrBreed);
        }
        if(shark.getMyNextState()==null){
            shark.setMyNextState(shark.getMyCurrentState());
        }
    }

    private void updateFish (FishCell fish) {

    }

    private List<Cell> getOpenCells (List<Cell> neighbors) {
        List<Cell> openCells = new ArrayList<Cell>();
        for (Cell c : neighbors) {
            if (c.getMyCurrentState() == State.EMPTY && c.getMyNextState() == State.EMPTY || c.getMyNextState() == null) {
                openCells.add(c);
            }
        }
        return openCells;
    }

    private List<Cell> getEdibleCells (List<Cell> neighbors) {
        List<Cell> edibleCells = new ArrayList<Cell>();
        for (Cell c : neighbors) {
            if (c.getMyCurrentState() == State.FISH) {
                edibleCells.add(c);
            }
        }
        return edibleCells;
    }

    private void breed (Cell cell, List<Cell> openCells) {
        if(openCells.size()==0)
            return;
        if (cell.getMyCurrentState() == State.SHARK) {
            if (((SharkCell) cell).canBreed()) {
                Cell newShark = openCells.get(new Random().nextInt(openCells.size()));
                makeNewShark((SharkCell) cell, newShark);// newShark = new
                                                         // SharkCell(cell.getMyGridCoordinate().getX(),cell.getMyGridCoordinate().getY(),myPredatorBreedTime,new
                                                         // Node(this.getMyShape()));
            }
        }
    }

    private void makeNewShark (SharkCell oldShark, Cell newShark) {
        newShark = new SharkCell(oldShark.getMyGridCoordinate(), myPredatorBreedTime, mySharkMaxHealth);
    }

    private void move (Cell cell, List<Cell> openCells) {
        
        if(openCells.size()==0){
            System.out.println("No open cells");
            return;
        }
        if (cell.getMyCurrentState() == State.SHARK) {
            System.out.println("Swim");
            Cell moveTo = openCells.get(new Random().nextInt(openCells.size()));
            SharkCell shark = (SharkCell) cell;
            //shark.setMyGridCoordinate(moveTo.getMyGridCoordinate());
            SharkCell endCell = new SharkCell(shark, moveTo.getMyGridCoordinate());
            endCell.setMyCurrentState(State.EMPTY);
            endCell.setMyNextState(State.SHARK);
            EmptyCell startCell = new EmptyCell(shark.getMyGridCoordinate());
            startCell.setMyCurrentState(State.SHARK);
            startCell.setMyNextState(State.EMPTY);
            getGrid().getCellGrid().remove(shark);
            getGrid().getCellGrid().remove(moveTo);
            getGrid().getCellGrid().put(endCell.getMyGridCoordinate(), endCell);
            getGrid().getCellGrid().put(startCell.getMyGridCoordinate(), startCell);
            
            //moveTo = new SharkCell(moveTo.getMyGridCoordinate(),myPredatorBreedTime, mySharkMaxHealth);
            //moveTo.setMyNextState(State.SHARK);
            
            //moveTo = shark;//new SharkCell(moveTo.getMyGridCoordinate(),myPredatorBreedTime, mySharkMaxHealth);
            
            //shark.setMyNextState(State.EMPTY);
            
            //cell.setMyNextState(State.EMPTY);
            //((SharkCell) cell).setMyNextState(State.SHARK);
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

    @Override
    public void setNextState (Cell cell) {
        // TODO Auto-generated method stub

    }

    @Override
    public void createNeighbors (Cell cell) {
        // TODO Auto-generated method stub

    }
}
