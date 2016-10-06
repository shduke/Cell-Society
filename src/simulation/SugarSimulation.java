package simulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import applicationView.SimulationToolbar;
import cell.Cell;
import cell.State;
import cell.SugarAgentCell;
import cell.SugarPatchCell;
import grid.Coordinate;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;

/**
 * 
 * @author Michael Schroeder
 *
 */
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

    public SugarSimulation (Map<String, Map<String, String>> simulationConfig) {
        super(simulationConfig);
        myTicker = 0;
        myNumAgents = 0;
    }

    @Override
    public List<Integer> countCellsinGrid () {
        List<Integer> cellCounts = new ArrayList<Integer>();
        cellCounts.add(myTicker);
        cellCounts.add(myNumAgents);

        return cellCounts;
    }

    @Override
    public void getSimulationNames () {
        ResourceBundle GUIResources = ResourceBundle.getBundle("resources/English");
        List<String> myList = new ArrayList<String>();
        myList.add(GUIResources.getString("NumAgents"));
        mySimulationGraph.addToLegend(myList);
    }

    @Override
    public void step () {
        updateAgents();
        if (myTicker % mySugarGrowBackInterval == 0) {
            growBackSugar();
        }
        updatePatches();
        countCellsinGrid();
        updateGrid();
        myTicker++;
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
    public Cell createCell (Coordinate coordinate, State currentState) {
        Random r = new Random();
        int vision = r.nextInt(myMaxVision) + 1;
        int initialSugar = r.nextInt(myMaxInitialSugar - 5) + 5;
        int metabolism = r.nextInt(myMaxMetabolism);
        SugarAgentCell agent =
                new SugarAgentCell(SugarState.ALIVE, coordinate, vision, initialSugar, metabolism);
        int maxSugar = r.nextInt(myMaxPatchSugar);
        SugarPatchCell cell;
        if (currentState == SugarState.SUGAR) {
            cell = new SugarPatchCell(SugarState.EMPTY, coordinate, myMaxPatchSugar);
        }
        else {
            cell = new SugarPatchCell(SugarState.EMPTY, coordinate, maxSugar);
        }
        if (currentState == SugarState.ALIVE) {
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
        Slider intervalSlider = new Slider(0, 10, 1);
        intervalSlider.setMajorTickUnit(1);
        intervalSlider.valueProperty()
                .addListener(e -> mySugarGrowBackInterval = (int) intervalSlider.getValue());
        toolbar.addSlider(intervalSlider, "sugarGrowBackInterval");
        Slider rateSlider = new Slider(0, 10, 1);
        rateSlider.setMajorTickUnit(1);
        rateSlider.valueProperty()
                .addListener(e -> mySugarGrowBackRate = (int) rateSlider.getValue());
        toolbar.addSlider(rateSlider, "sugarGrowBackRate");

    }

    @Override
    public State[] getSimulationStates () {
        return SugarState.values();
    }

    @Override
    public State getSimulationState (String simulationState) {
        return SugarState.valueOf(simulationState.toUpperCase());
    }

    public enum SugarState implements State {
                                             ALIVE(Color.RED),
                                             DEAD(Color.BLACK),
                                             EMPTY(Color.WHITE),
                                             SUGAR(Color.ORANGE);

        private final Color myColor;
        private double myProbability;

        SugarState (Color color) {
            myColor = color;
            myProbability = 0;
        }

        @Override
        public Color getColor () {
            return myColor;
        }

        @Override
        public double getProbability () {
            return myProbability;
        }

        @Override
        public void setProbability (double probability) {
            myProbability = probability;

        }

    }

    private void updatePatches () {
        Iterator<Cell> cellIter = getGrid().iterator();
        while (cellIter.hasNext()) {
            ((SugarPatchCell) cellIter.next()).update();
        }
    }

    private void updateAgents () {
        myNumAgents = 0;
        List<Cell> cells = new ArrayList<Cell>(getGrid().getCellGrid().values());
        Collections.shuffle(cells);
        Iterator<Cell> cellIter = cells.iterator();
        while (cellIter.hasNext()) {
            SugarPatchCell cell = (SugarPatchCell) cellIter.next();
            if (cell.hasAgent()) {

                SugarAgentCell agent = cell.getAgent();
                if (agent.getMyCurrentState() == SugarState.DEAD || agent.isDead()) {
                    cell.killAgent();
                    System.out.println("KILL");
                }
                else if (!(agent.getMyNextState() == SugarState.DEAD)) {
                    updateAgent(agent, cell);
                }
            }
        }
    }

    private void updateAgent (SugarAgentCell agent, SugarPatchCell current) {
        List<Cell> neighbors =
                getNeighborsHandler().getVisionNeighbors(agent.getMyGridCoordinate(),
                                                         agent.getVision());
        SugarPatchCell moveTo = agent.findSugar(neighbors, current.getSugar());
        if (moveTo != null) {
            move(agent, moveTo);
            current.killAgent();
            agent.eat(moveTo);
        }
        else {
            agent.eat(current);
        }
        myNumAgents++;
        agent.update();
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

}
