package simulation;

import grid.*;
import java.awt.Color;
import java.util.*;
import cell.*;


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
    private Neighbor myNeighborType;
    private int myPreyBreedTime;
    private int myPredatorBreedTime;
    private int mySharkMaxHealth;
    private int mySharkCount;
    private int myFishCount;
    public PredatorPreySimulation (Map<String, Map<String, String>> simulationConfig) {
        super(simulationConfig);
    }

    @Override
    public void generateMap (int numberOfRows,
                             int numberOfColumns,
                             Grid cellGrid) {
        for (int r = 0; r < numberOfRows; r++) {
            for (int c = 0; c < numberOfColumns; c++) {
                Coordinate coordinate = new Coordinate(r, c);
                if (!cellGrid.isCreated(coordinate)) {
                    EmptyCell cell = new EmptyCell(coordinate);

                    cellGrid.addCell(cell);
                }

            }
        }
    }

    @Override
    public void step () {

        updateSharks();
        updateFishes();
        getGrid().updateGrid();
        this.getGridView().updateView();
        countCellsinGrid();

    }

    private void updateSharks () {
        mySharkCount = 0;
        Iterator<Cell> cells = getGrid().iterator();

        while (cells.hasNext()) {
            Cell cell = cells.next();

            if (cell.getMyCurrentState() == State.EMPTY && cell.getMyNextState() == null) {
                cell.setMyNextState(State.EMPTY);
            }

            if (cell.getMyCurrentState() == State.SHARK) {
                
                if (cell.getMyNextState() == State.DEAD) {
                    kill((SharkCell) cell);
                }
                else if (cell.getMyNextState() != State.EMPTY) {
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
            if (cell.getMyCurrentState() == State.FISH) {
               
                if (cell.getMyNextState() == State.DEAD) {
                    kill((FishCell) cell);
                }
                else if (cell.getMyNextState() != State.EMPTY) {
                    myFishCount++;
                    updateFish((FishCell) cell);
                }
            }
        }
    }

    public void kill (Cell cell) {
        EmptyCell empty = new EmptyCell(cell.getMyGridCoordinate());
        empty.setMyCurrentState(cell.getMyCurrentState());
        empty.setMyNextState(State.EMPTY);
        getGrid().getCellGrid().put(empty.getMyGridCoordinate(), empty);
    }

    private void updateShark (SharkCell shark) {
        List<Cell> neighbors =
                getNeighbors().getSurroundingNeighbors(shark.getMyGridCoordinate());
                                            
        List<Cell> canMoveOrBreed = getOpenCells(neighbors);
        List<Cell> foodCells = getEdibleCells(neighbors);
        shark.update();
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
        if (shark.getMyNextState() == null) {
            shark.setMyNextState(shark.getMyCurrentState());
        }
    }

    private void updateFish (FishCell fish) {
        fish.update();
        List<Cell> neighbors =
                getNeighbors().getSurroundingNeighbors(fish.getMyGridCoordinate());
                                         
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
            if (c.getMyCurrentState() == State.EMPTY && (c.getMyNextState() == State.EMPTY ||
                                                         c.getMyNextState() == null)) {
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

        if (openCells.size() == 0) {
            return;
        }

        if (cell.getMyCurrentState() == State.SHARK) {
            if (((SharkCell) cell).canBreed()) {
                Cell newShark = openCells.get(new Random().nextInt(openCells.size()));
                makeNewShark((SharkCell) cell, newShark);
            }
        }
        else if (cell.getMyCurrentState() == State.FISH) {

            if (((FishCell) cell).canBreed()) {
                Cell newFish = openCells.get(new Random().nextInt(openCells.size()));
                makeNewFish((FishCell) cell, newFish);
            }
        }

    }

    private void makeNewFish (FishCell oldFish, Cell newFish) {
        
        FishCell babyFish =
                new FishCell(newFish.getMyGridCoordinate(), myPreyBreedTime);

        babyFish.setMyCurrentState(State.EMPTY);
        babyFish.setMyNextState(State.FISH);
        getGrid().getCellGrid().put(babyFish.getMyGridCoordinate(), babyFish);
    }

    private void makeNewShark (SharkCell oldShark, Cell newShark) {
        
        SharkCell babyShark =
                new SharkCell(newShark.getMyGridCoordinate(), myPredatorBreedTime,
                              mySharkMaxHealth);

        babyShark.setMyCurrentState(State.EMPTY);
        babyShark.setMyNextState(State.SHARK);
        getGrid().getCellGrid().put(babyShark.getMyGridCoordinate(), babyShark);
    }

    private void move (Cell cell, List<Cell> openCells) {

        if (openCells.size() == 0) {
            return;
        }
        Cell moveTo = openCells.get(new Random().nextInt(openCells.size()));

        if (cell.getMyCurrentState() == State.SHARK) {
            SharkCell shark = (SharkCell) cell;
            SharkCell endCell = new SharkCell(shark, moveTo.getMyGridCoordinate());
            swapCells(cell, endCell);
        }
        else if (cell.getMyCurrentState() == State.FISH) {
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
        endCell.setMyCurrentState(State.EMPTY);
        endCell.setMyNextState(cell.getMyCurrentState());
        EmptyCell startCell = new EmptyCell(cell.getMyGridCoordinate());
        startCell.setMyCurrentState(cell.getMyCurrentState());
        startCell.setMyNextState(State.EMPTY);
        getGrid().getCellGrid().put(endCell.getMyGridCoordinate(), endCell);
        getGrid().getCellGrid().put(startCell.getMyGridCoordinate(), startCell);
    }


    @Override
    public void initializeSimulationDetails (Map<String, String> simulationConfig) {
        //this.myNeighborType = Neighbor.SQUARE;
        this.myPreyBreedTime = Integer.parseInt(simulationConfig.get("preyBreedTime"));
        this.myPredatorBreedTime = Integer.parseInt(simulationConfig.get("predatorBreedTime"));
        this.mySharkMaxHealth = Integer.parseInt(simulationConfig.get("sharkMaxHealth"));

    }

    @Override
    public Cell createCell (Coordinate coordinate, String currentState) {
        State st = State.valueOf(currentState.toUpperCase());
        if (st == State.EMPTY) {
            return new EmptyCell(coordinate);
        }
        else if (st == State.FISH) {
            return new FishCell(coordinate, myPreyBreedTime);
        }
        else {
            return new SharkCell(coordinate, myPredatorBreedTime, mySharkMaxHealth);
        }
    }

    public enum PredatorPreyStates {
                                    EMPTY(0, Color.BLUE, "Empty"),
                                    FISH(1, Color.YELLOW, "Fish"),
                                    SHARK(2, Color.RED, "Shark"),
                                    DEAD(3, Color.WHITE, "Empty");

        private final int myValue;
        private final Color myColor;
        private final String myName;

        PredatorPreyStates (int value, Color color, String name) {
            myName = name;
            myValue = value;
            myColor = color;
        }

        public int getIntValue () {
            return myValue;
        }

        public Color getColor () {
            return myColor;
        }

        public String getName () {
            return myName;
        }
    }

    @Override
    public void countCellsinGrid () {
        // TODO Auto-generated method stub
        stepNum = getStepNum();
        System.out.println("Num of steps: " + stepNum);
        int fishCount = 0;
        int sharkCount = 0;
        int emptyCount = 0;
        for (Cell cell : getGrid().getImmutableCellGrid().values()) {
            if(cell.getMyCurrentState().equals(State.FISH)) {
                fishCount++;
            }
            if(cell.getMyCurrentState().equals(State.SHARK)) {
                sharkCount++;
            }
            if(cell.getMyCurrentState().equals(State.EMPTY)) {
                emptyCount++;
            }
        }
        System.out.println("Fish: " + fishCount);
        System.out.println("Shark: " + sharkCount);
        System.out.println("Empty: " + emptyCount);
        System.out.println("Running fish " + myFishCount);
        System.out.println("Running shark " + mySharkCount);
        stepNum++;
    }

}
