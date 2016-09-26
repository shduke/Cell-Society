package simulation;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import cell.Cell;
import cell.FireCell;
import cell.State;
import grid.Coordinate;
import grid.Grid;
import grid.GridView;
import grid.Neighbor;
import grid.Neighbors;
import grid.NormalEdgeNeighbors;
import grid.RectangleGridView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;


public class FireSimulation extends Simulation {
    private double probCatch;
    private int burnTime;

    public FireSimulation (Map<String, Map<String, String>> simulationConfig) {
        super(simulationConfig);
    }

    private String getStringValue (Element rootElement, String name) {
        return rootElement.getElementsByTagName(name).item(0).getFirstChild().getNodeValue();
    }

    // Allows them to specify what coordinates they went, the rest are set to default by a function,
    // gets bounds and starter map from config
    @Override
    public void generateMap (int numberOfRows,
                             int numberOfColumns,
                             Grid cellGrid) {
        for (int r = 0; r < numberOfRows; r++) {
            for (int c = 0; c < numberOfColumns; c++) {
                Coordinate coordinate = new Coordinate(r, c);
                if (!cellGrid.isCreated(coordinate)) {
                    FireCell cell = new FireCell(State.TREE, coordinate);
                    if (r == 0 || c == 0 || r == (numberOfRows - 1) || c == (numberOfColumns - 1)) {
                        cell.setMyCurrentState(State.EMPTY);
                    }
                    cellGrid.addCell(cell);
                }

            }
        }
    }

    // very temporary code, just here to make it work
    @Override
    public void step () {
        getGrid().applyFuncToCell(p -> setNextState(p));
        updateGrid();
    }

    public boolean hasBurningNeighbor (Cell cell) {
        for (Cell neighborCell : getNeighbors().getNeighbors(Neighbor.SQUARE.getNeighbors(),
                                                             cell.getMyGridCoordinate())) {
            if (neighborCell.getMyCurrentState().equals(State.BURNING)) {
                return true;
            }
        }
        return false;
    }

    // is switching on cell state bad?
    @Override
    public void setNextState (Cell cell) {
        // System.out.println(cell.getMyCurrentState());
        if (cell.getMyCurrentState().equals(State.TREE)) {
            cell.setMyNextState(State.TREE);
            Random rn = new Random();
            if (hasBurningNeighbor(cell) && rn.nextInt(100) < probCatch) {
                cell.setMyNextState(State.BURNING);
                ((FireCell) cell).setBurnTimer(burnTime);
            }
        }
        else if (cell.getMyCurrentState().equals(State.BURNING)) {
            ((FireCell) cell).decrementBurnTimer();
            cell.setMyNextState(State.BURNING);
            if (((FireCell) cell).getBurnTimer() == 0) {
                cell.setMyNextState(State.EMPTY);
            }
        }
        else {
            cell.setMyNextState(State.EMPTY);
        }
    }


    @Override
    public void initializeSimulationDetails (Map<String, String> simulationConfig) {
        this.probCatch = Double.parseDouble(simulationConfig.get("probCatch"));
        this.burnTime = Integer.parseInt(simulationConfig.get("burnTime"));
    }

    @Override
    public Cell createCell (String stringCoordinate, String currentState) {
        String[] coordinateData = stringCoordinate.split("_");
        FireCell cell = new FireCell(State.valueOf(currentState.toUpperCase()),
                     new Coordinate(Double.parseDouble(coordinateData[0]),
                                    Double.parseDouble(coordinateData[1])));
        cell.setBurnTimer(burnTime);
        return cell;
    }

}
