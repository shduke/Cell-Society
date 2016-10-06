package simulation;

import grid.*;
import java.util.*;
import applicationView.SimulationToolbar;
import cell.*;
import javafx.scene.control.Slider;
import javafx.scene.paint.*;


/**
 * 
 * @author Michael Schroeder
 *
 */
public class PredatorPreySimulation extends Simulation {

    private int myPreyBreedTime;
    private int myPredatorBreedTime;
    private int mySharkMaxHealth;
    private int mySharkCount;
    private int myFishCount;

    public PredatorPreySimulation (Map<String, Map<String, String>> simulationConfig) {
        super(simulationConfig);
    }

    @Override
    public void step () {

        updateSharks();
        updateFishes();
        updateGrid();

    }

    private void updateSharks () {
        mySharkCount = 0;
        Iterator<Cell> cells = getGrid().iterator();

        while (cells.hasNext()) {
            Cell cell = cells.next();

            if (cell.getMyCurrentState() == PredatorPreyState.EMPTY &&
                cell.getMyNextState() == null) {
                cell.setMyNextState(PredatorPreyState.EMPTY);
            }

            if (cell.getMyCurrentState() == PredatorPreyState.SHARK) {

                if (cell.getMyNextState() == PredatorPreyState.DEAD) {
                    kill((SharkCell) cell);
                }
                else if (cell.getMyNextState() != PredatorPreyState.EMPTY) {
                    mySharkCount++;
                    updateShark((SharkCell) cell);
                }
            }
        }
    }

    private void updateFishes () {
        myFishCount = 0;
        Iterator<Cell> cells = getGrid().iterator();
        while (cells.hasNext()) {

            Cell cell = cells.next();
            if (cell.getMyCurrentState() == PredatorPreyState.FISH) {

                if (cell.getMyNextState() == PredatorPreyState.DEAD) {
                    kill((FishCell) cell);
                }
                else if (cell.getMyNextState() != PredatorPreyState.EMPTY) {
                    myFishCount++;
                    updateFish((FishCell) cell);
                }
            }
        }
    }

    public void kill (Cell cell) {
        EmptyCell empty = new EmptyCell(cell.getMyGridCoordinate());
        empty.setMyCurrentState(cell.getMyCurrentState());
        empty.setMyNextState(PredatorPreyState.EMPTY);
        getGrid().getCellGrid().put(empty.getMyGridCoordinate(), empty);
    }

    private void updateShark (SharkCell shark) {
        List<Cell> neighbors =
                getNeighborsHandler().getSurroundingNeighbors(shark.getMyGridCoordinate());

        List<Cell> canMoveOrBreed = getOpenCells(neighbors);
        List<Cell> foodCells = getEdibleCells(neighbors);
        shark.update();
        if (shark.getMyNextState() == PredatorPreyState.DEAD) {

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
        if (shark.getMyNextState() == null) {
            shark.setMyNextState(shark.getMyCurrentState());
        }
    }

    private void updateFish (FishCell fish) {
        fish.update();
        List<Cell> neighbors =
                getNeighborsHandler().getSurroundingNeighbors(fish.getMyGridCoordinate());

        List<Cell> canMoveOrBreed = getOpenCells(neighbors);
        breed(fish, canMoveOrBreed);
        move(fish, canMoveOrBreed);
        if (fish.getMyNextState() == null) {
            fish.setMyNextState(fish.getMyCurrentState());
        }
    }

    private List<Cell> getOpenCells (List<Cell> neighbors) {
        List<Cell> openCells = new ArrayList<Cell>();
        for (Cell c : neighbors) {
            if (c.getMyCurrentState() == PredatorPreyState.EMPTY &&
                (c.getMyNextState() == PredatorPreyState.EMPTY ||
                 c.getMyNextState() == null)) {
                openCells.add(c);
            }
        }
        return openCells;
    }

    private List<Cell> getEdibleCells (List<Cell> neighbors) {
        List<Cell> edibleCells = new ArrayList<Cell>();
        for (Cell c : neighbors) {
            if (c.getMyCurrentState() == PredatorPreyState.FISH) {
                edibleCells.add(c);
            }
        }
        return edibleCells;
    }

    private void breed (Cell cell, List<Cell> openCells) {

        if (openCells.size() == 0) {
            return;
        }

        if (cell.getMyCurrentState() == PredatorPreyState.SHARK) {
            if (((SharkCell) cell).canBreed()) {
                Cell newShark = openCells.get(new Random().nextInt(openCells.size()));
                makeNewShark((SharkCell) cell, newShark);
            }
        }
        else if (cell.getMyCurrentState() == PredatorPreyState.FISH) {

            if (((FishCell) cell).canBreed()) {
                Cell newFish = openCells.get(new Random().nextInt(openCells.size()));
                makeNewFish((FishCell) cell, newFish);
            }
        }

    }

    private void makeNewFish (FishCell oldFish, Cell newFish) {

        FishCell babyFish =
                new FishCell(newFish.getMyGridCoordinate(), myPreyBreedTime);

        babyFish.setMyCurrentState(PredatorPreyState.EMPTY);
        babyFish.setMyNextState(PredatorPreyState.FISH);
        getGrid().getCellGrid().put(babyFish.getMyGridCoordinate(), babyFish);
    }

    private void makeNewShark (SharkCell oldShark, Cell newShark) {

        SharkCell babyShark =
                new SharkCell(newShark.getMyGridCoordinate(), myPredatorBreedTime,
                              mySharkMaxHealth);

        babyShark.setMyCurrentState(PredatorPreyState.EMPTY);
        babyShark.setMyNextState(PredatorPreyState.SHARK);
        getGrid().getCellGrid().put(babyShark.getMyGridCoordinate(), babyShark);
    }

    private void move (Cell cell, List<Cell> openCells) {

        if (openCells.size() == 0) {
            return;
        }
        Cell moveTo = openCells.get(new Random().nextInt(openCells.size()));

        if (cell.getMyCurrentState() == PredatorPreyState.SHARK) {
            SharkCell shark = (SharkCell) cell;
            SharkCell endCell = new SharkCell(shark, moveTo.getMyGridCoordinate());
            swapCells(cell, endCell);
        }
        else if (cell.getMyCurrentState() == PredatorPreyState.FISH) {
            FishCell fish = (FishCell) cell;
            FishCell endCell = new FishCell(fish, moveTo.getMyGridCoordinate());
            swapCells(cell, endCell);
        }
    }

    /**
     * @param endCell
     * @param startCell
     */
    private void swapCells (Cell cell, Cell endCell) {
        endCell.setMyCurrentState(PredatorPreyState.EMPTY);
        endCell.setMyNextState(cell.getMyCurrentState());
        EmptyCell startCell = new EmptyCell(cell.getMyGridCoordinate());
        startCell.setMyCurrentState(cell.getMyCurrentState());
        startCell.setMyNextState(PredatorPreyState.EMPTY);
        getGrid().getCellGrid().put(endCell.getMyGridCoordinate(), endCell);
        getGrid().getCellGrid().put(startCell.getMyGridCoordinate(), startCell);
    }

    @Override
    public void initializeSimulationDetails (Map<String, String> simulationConfig) {
        this.myPreyBreedTime = Integer.parseInt(simulationConfig.get("preyBreedTime"));
        this.myPredatorBreedTime = Integer.parseInt(simulationConfig.get("predatorBreedTime"));
        this.mySharkMaxHealth = Integer.parseInt(simulationConfig.get("sharkMaxHealth"));

    }

    @Override
    public Cell createCell (Coordinate coordinate, State currentState) {
        // State st = currentState;
        if (currentState == PredatorPreyState.EMPTY) {
            return new EmptyCell(coordinate);
        }
        else if (currentState == PredatorPreyState.FISH) {
            return new FishCell(coordinate, myPreyBreedTime);
        }
        else {
            return new SharkCell(coordinate, myPredatorBreedTime, mySharkMaxHealth);
        }
    }

    @Override
    public List<Integer> countCellsinGrid () {
        stepNum = getStepNum();
        int emptyCount = 0;
        emptyCount = getGrid().getNumRows()*(getGrid().getNumColumns())-myFishCount-mySharkCount;
        stepNum++;
        List<Integer> myOutput = new ArrayList<Integer>();
        myOutput.add(stepNum - 1);
        myOutput.add(emptyCount);
        myOutput.add(mySharkCount);
        myOutput.add(myFishCount);
        return myOutput;
    }

    @Override
    public void initializeSimulationToolbar (SimulationToolbar toolbar) {
        Slider preyBreedSlider = new Slider(1, 10, myPreyBreedTime);
        preyBreedSlider.valueProperty()
                .addListener(e -> myPreyBreedTime = (int) preyBreedSlider.getValue());
        toolbar.addSlider(preyBreedSlider, "preyBreedTime");
    }

    @Override
    public State[] getSimulationStates () {
        return PredatorPreyState.values();
    }

    @Override
    public void getSimulationNames () {
        List<String> myList = new ArrayList<String>();
        for (State n : getSimulationStates()) {
            myList.add(n.name());
        }
        addToLegend(myList);
    }

    @Override
    public State getSimulationState (String simulationState) {
        return PredatorPreyState.valueOf(simulationState.toUpperCase());
    }

    public enum PredatorPreyState implements State {
                                                    EMPTY(Color.BLUE),
                                                    SHARK(Color.RED),
                                                    FISH(Color.YELLOW),
                                                    DEAD(Color.BLUE);

        private final Color myColor;
        private double myProbability;

        PredatorPreyState (Color color) {
            myColor = color;
            myProbability = 0;
        }

        public Color getColor () {
            return myColor;
        }

        public double getProbability () {
            return myProbability;
        }

        public void setProbability (double probability) {
            myProbability = probability;
        }
    }

}
