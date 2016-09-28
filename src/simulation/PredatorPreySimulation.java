package simulation;

import grid.*;
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
    private int counter = 1;

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

        System.out.println(counter);

        counter++;
        // TODO Auto-generated method stub

    }

    private void updateSharks () {
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
                    updateShark((SharkCell) cell);
                }
            }
        }
    }

    private void updateFishes () {

        Iterator<Cell> cells = getGrid().iterator();
        while (cells.hasNext()) {

            Cell cell = cells.next();
            if (cell.getMyCurrentState() == State.FISH) {
                if (cell.getMyNextState() == State.DEAD) {
                    kill((FishCell) cell);
                }
                else if (cell.getMyNextState() != State.EMPTY) {
                    System.out.println("fish is at " + cell.getMyGridCoordinate().getX() + ", " +
                                       cell.getMyGridCoordinate().getY());
                    updateFish((FishCell) cell);
                }
            }
        }
    }

    public void kill (Cell cell) {
        if (cell.getMyCurrentState() == State.FISH) {
            EmptyCell empty = new EmptyCell(cell.getMyGridCoordinate());
            empty.setMyCurrentState(State.FISH);
            empty.setMyNextState(State.EMPTY);
            // getGrid().getCellGrid().remove(cell.getMyGridCoordinate());
            getGrid().getCellGrid().put(empty.getMyGridCoordinate(), empty);
        }
        else if (cell.getMyCurrentState() == State.SHARK) {
            EmptyCell empty = new EmptyCell(cell.getMyGridCoordinate());
            empty.setMyCurrentState(State.SHARK);
            empty.setMyNextState(State.EMPTY);
            // getGrid().getCellGrid().remove(cell.getMyGridCoordinate());
            getGrid().getCellGrid().put(empty.getMyGridCoordinate(), empty);
        }
    }

    private void updateShark (SharkCell shark) {
        List<Cell> neighbors =
                getNeighbors().getNeighbors(myNeighborType.getNeighbors(),
                                            shark.getMyGridCoordinate());
        System.out.println("there are " + neighbors.size() + " neighbors");
        List<Cell> canMoveOrBreed = getOpenCells(neighbors);
        System.out.println("there are " + canMoveOrBreed.size() + " open cells");
        System.out.println("Shark can " + (shark.canBreed() ? "" : "not ") + "breed");
        List<Cell> foodCells = getEdibleCells(neighbors);
        shark.update();
        if (shark.getMyNextState() == State.DEAD) {
            kill(shark);
        }
        else if (foodCells.size() > 0) {
            FishCell food = (FishCell) foodCells.get(new Random().nextInt(foodCells.size()));
            System.out.println("Shark just ate a fish");
            shark.eat(food);
            shark.setMyNextState(shark.getMyCurrentState());
            // SharkCell baby = (SharkCell)
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
                getNeighbors().getNeighbors(myNeighborType.getNeighbors(),
                                            fish.getMyGridCoordinate());
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
            System.out.println(c.getMyCurrentState());
            System.out.println(c.getMyNextState());
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

        if (openCells.size() == 0)
            return;

        if (cell.getMyCurrentState() == State.SHARK) {
            if (((SharkCell) cell).canBreed()) {
                System.out.println("BREED");
                Cell newShark = openCells.get(new Random().nextInt(openCells.size()));
                makeNewShark((SharkCell) cell, newShark);// newShark = new
                                                         // SharkCell(cell.getMyGridCoordinate().getX(),cell.getMyGridCoordinate().getY(),myPredatorBreedTime,new
                                                         // Node(this.getMyShape()));
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
        System.out.println("Breeding fish at " + babyFish.getMyGridCoordinate().getX() + ", " +
                           babyFish.getMyGridCoordinate().getY());
        // return babyShark;
        getGrid().getCellGrid().put(babyFish.getMyGridCoordinate(), babyFish);
    }

    private void makeNewShark (SharkCell oldShark, Cell newShark) {

        SharkCell babyShark =
                new SharkCell(newShark.getMyGridCoordinate(), myPredatorBreedTime,
                              mySharkMaxHealth);

        babyShark.setMyCurrentState(State.EMPTY);
        babyShark.setMyNextState(State.SHARK);
        System.out.println("Breeding shark at " + babyShark.getMyGridCoordinate().getX() + ", " +
                           babyShark.getMyGridCoordinate().getY());
        // return babyShark;
        getGrid().getCellGrid().put(babyShark.getMyGridCoordinate(), babyShark);
    }

    private void move (Cell cell, List<Cell> openCells) {

        if (openCells.size() == 0) {
            System.out.println("No open cells");
            return;
        }
        if (cell.getMyCurrentState() == State.SHARK) {
            System.out.println("Swim");
            Cell moveTo = openCells.get(new Random().nextInt(openCells.size()));
            SharkCell shark = (SharkCell) cell;
            // shark.setMyGridCoordinate(moveTo.getMyGridCoordinate());
            SharkCell endCell = new SharkCell(shark, moveTo.getMyGridCoordinate());
            endCell.setMyCurrentState(State.EMPTY);
            endCell.setMyNextState(State.SHARK);
            EmptyCell startCell = new EmptyCell(shark.getMyGridCoordinate());
            startCell.setMyCurrentState(State.SHARK);
            startCell.setMyNextState(State.EMPTY);
            // getGrid().getCellGrid().remove(shark);
            // getGrid().getCellGrid().remove(moveTo);
            getGrid().getCellGrid().put(endCell.getMyGridCoordinate(), endCell);
            getGrid().getCellGrid().put(startCell.getMyGridCoordinate(), startCell);
        }
        else if (cell.getMyCurrentState() == State.FISH) {
            System.out.println("Swim");
            Cell moveTo = openCells.get(new Random().nextInt(openCells.size()));
            FishCell fish = (FishCell) cell;
            FishCell endCell = new FishCell(fish, moveTo.getMyGridCoordinate());
            endCell.setMyCurrentState(State.EMPTY);
            endCell.setMyNextState(State.FISH);
            EmptyCell startCell = new EmptyCell(fish.getMyGridCoordinate());
            startCell.setMyCurrentState(State.FISH);
            startCell.setMyNextState(State.EMPTY);
            getGrid().getCellGrid().put(endCell.getMyGridCoordinate(), endCell);
            getGrid().getCellGrid().put(startCell.getMyGridCoordinate(), startCell);
        }
    }

    @Override
    public void setNextState (Cell cell) {
        // TODO Auto-generated method stub

    }

    @Override
    public void initializeSimulationDetails (Map<String, String> simulationConfig) {
        this.myNeighborType = Neighbor.SQUARE;
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

}
