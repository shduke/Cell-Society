package simulation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import applicationView.SimulationToolbar;
import cell.Cell;
import cell.State;
import cell.SugarAgentCell;
import cell.SugarPatchCell;
import grid.Coordinate;


public class SugarSimulation extends Simulation {

    private int mySugarGrowBackRate;
    private int mySugarGrowBackInterval;
    private int myTicker;
    private int myMaxVision;
    private int myMaxMetabolism;
    private int myMaxInitialSugar;
    private int myInitialAgents;
    private int myMaxPatchSugar;
    private int myNumAgents;
    private int myMovingAgents;

    public SugarSimulation (Map<String, Map<String, String>> simulationConfig) {
        super(simulationConfig);
        myTicker = 0;
        myNumAgents = 0;
        myMovingAgents = 0;
    }

    @Override
    public List<Integer> countCellsinGrid () {
        // TODO Auto-generated method stub
        List<Integer> cellCounts = new ArrayList<Integer>();
        cellCounts.add(myTicker);
        cellCounts.add(myNumAgents);

        return cellCounts;
    }

    @Override
    public void step () {

        updateAgents();
        if (myTicker % mySugarGrowBackInterval == 0) {
            growBackSugar();
        }
        updatePatches();
        countCellsinGrid();
        getGrid().updateGrid();
        this.getGridView().updateView();
        myTicker++;
    }

    private void updatePatches () {
        Iterator<Cell> cellIter = getGrid().iterator();
        while (cellIter.hasNext()) {
            ((SugarPatchCell) cellIter.next()).update();
        }
    }

    private void updateAgents () {
        myMovingAgents = 0;
        myNumAgents = 0;
        List<Cell> cells = new ArrayList<Cell>(getGrid().getCellGrid().values());
        Collections.shuffle(cells);
        Iterator<Cell> cellIter = cells.iterator();
        while (cellIter.hasNext()) {
            SugarPatchCell cell = (SugarPatchCell) cellIter.next();
            if (cell.hasAgent()) {

                SugarAgentCell agent = cell.getAgent();
                System.out.println(agent.getMyCurrentState());
                System.out.println(agent.getMyNextState());
                System.out.println(agent.isDead() + " dead?");
                if (agent.getMyCurrentState() == State.DEAD || agent.isDead()) {
                    cell.killAgent();
                    System.out.println("KILL");
                }
                else if (!(agent.getMyNextState() == State.DEAD)) {
                    if (agent.getMyCurrentState() == State.ALIVE) {
                        // myNumAgents++;
                    }
                    updateAgent(agent, cell);
                }
            }
        }
    }

    private void updateAgent (SugarAgentCell agent, SugarPatchCell current) {
        List<Cell> neighbors = getNeighbors().getOrthogonalNeighbors(agent.getMyGridCoordinate());
        // getNeighbors(agent.getMyGridCoordinate(), agent.getVision());
        SugarPatchCell moveTo = agent.findSugar(neighbors, current.getSugar());
        if (moveTo != null) {
            System.out.println("Moving");
            myMovingAgents++;
            move(agent, moveTo);
            current.killAgent();
            agent.eat(moveTo);
        }
        else {
            System.out.println("not moving");
            agent.eat(current);
        }
        myNumAgents++;
        agent.update();
        if (!agent.isDead()) {
            // myNumAgents++;
        }
    }

    private void move (SugarAgentCell agent, SugarPatchCell moveTo) {
        SugarAgentCell newAgent = new SugarAgentCell(agent);
        moveTo.addAgent(newAgent);
        newAgent.setMyGridCoordinate(moveTo.getMyGridCoordinate());
    }

    private void growBackSugar () {
        Iterator<Cell> cellIter = getGrid().iterator();
        while (cellIter.hasNext()) {
            SugarPatchCell cell = (SugarPatchCell) cellIter.next();
            cell.growBack(mySugarGrowBackRate);
        }
    }

    @Override
    public void initializeSimulationDetails (Map<String, String> simulationConfig) {
        mySugarGrowBackRate = Integer.parseInt(simulationConfig.get("sugarGrowBackRate"));
        mySugarGrowBackInterval = Integer.parseInt(simulationConfig.get("sugarGrowBackInterval"));
        myMaxVision = Integer.parseInt(simulationConfig.get("maxVision"));
        myMaxMetabolism = Integer.parseInt(simulationConfig.get("maxMetabolism"));
        myMaxInitialSugar = Integer.parseInt(simulationConfig.get("maxInitialSugar"));
        myInitialAgents = Integer.parseInt(simulationConfig.get("initialAgents"));
        myMaxPatchSugar = Integer.parseInt(simulationConfig.get("maxPatchSugar"));
    }

    @Override
    public Cell createCell (Coordinate coordinate, String currentState) {
        // int sugarGrowBackRate = new Random().nextInt(mySugarGrowBackRate) + 1;
        // SugarPatchCell cell = ne
        Random r = new Random();
        int vision = r.nextInt(myMaxVision) + 1;
        int initialSugar = r.nextInt(myMaxInitialSugar - 5) + 5;
        int metabolism = r.nextInt(myMaxMetabolism);
        SugarAgentCell agent =
                new SugarAgentCell(State.ALIVE, coordinate, vision, initialSugar, metabolism);
        // int initialSugar = new Random().nextInt(myMaxInitialSugar);
        int maxSugar = r.nextInt(myMaxPatchSugar);
        SugarPatchCell cell = new SugarPatchCell(State.EMPTY, coordinate, maxSugar);
        if (State.valueOf(currentState.toUpperCase()) == State.ALIVE) {
            cell.initAgent(agent);
        }
        else if (r.nextInt(10) < 3 && myInitialAgents > 0) {
            cell.initAgent(agent);
            myInitialAgents--;
        }
        return cell;
    }

    @Override
    public void initializeSimulationToolbar (SimulationToolbar toolbar) {
        // TODO Auto-generated method stub
        
    }

}
