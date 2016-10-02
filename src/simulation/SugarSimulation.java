package simulation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
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

    SugarSimulation (Map<String, Map<String, String>> simulationConfig) {
        super(simulationConfig);
        myTicker = 0;
    }

    @Override
    public void countCellsinGrid () {
        // TODO Auto-generated method stub

    }

    @Override
    public void step () {
        updateAgents();
        if (myTicker % mySugarGrowBackInterval == 0) {
            growBackSugar();
        }

    }

    private void updateAgents () {
        List<Cell> cells = new ArrayList<Cell>(getGrid().getCellGrid().values());
        Collections.shuffle(cells);
        Iterator<Cell> cellIter = cells.iterator();
        while (cellIter.hasNext()) {
            SugarPatchCell cell = (SugarPatchCell) cellIter.next();
            SugarAgentCell agent = cell.getAgent();
            if (agent.getMyCurrentState() == State.DEAD) {
                cell.killAgent();
            }
            else if (!(agent.getMyNextState() == State.DEAD)) {
                updateAgent(agent, cell);
            }
        }
    }

    private void updateAgent (SugarAgentCell agent, SugarPatchCell current) {
        List<Cell> neighbors = null;
        // getNeighbors(agent.getMyGridCoordinate(), agent.getVision());
        SugarPatchCell moveTo = agent.findSugar(neighbors, current.getSugar());
        if (moveTo != null) {
            move(agent, moveTo);
            agent.eat(moveTo);
        }
        else {
            agent.eat(current);
        }
        agent.update();
    }

    private void move (SugarAgentCell agent, SugarPatchCell moveTo) {
        SugarAgentCell newAgent = new SugarAgentCell(agent);
        agent.setMyNextState(State.DEAD);
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
        myMaxInitialSugar = Integer.parseInt(simulationConfig.get("initialSugar"));
        myInitialAgents = Integer.parseInt(simulationConfig.get("initialAgents"));
        myMaxPatchSugar = Integer.parseInt(simulationConfig.get("maxPatchSugar"));
    }

    @Override
    public Cell createCell (Coordinate coordinate, String currentState) {
        // int sugarGrowBackRate = new Random().nextInt(mySugarGrowBackRate) + 1;
        // SugarPatchCell cell = ne
        Random r = new Random();
        // int initialSugar = new Random().nextInt(myMaxInitialSugar);
        SugarPatchCell cell = new SugarPatchCell(State.EMPTY, coordinate, myMaxPatchSugar);
        if (r.nextInt(10) < 5 && myInitialAgents > 0) {
            int vision = r.nextInt(myMaxVision) + 1;
            int initialSugar = r.nextInt(myMaxInitialSugar - 5) + 5;
            int metabolism = r.nextInt(myMaxMetabolism);
            SugarAgentCell agent =
                    new SugarAgentCell(State.ALIVE, coordinate, vision, initialSugar, metabolism);
            cell.addAgent(agent);
            myInitialAgents--;
        }
        return cell;
    }

}
