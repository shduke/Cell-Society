package controller;

import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import simulation.FireSimulation;
import simulation.Simulation;
import xml.XMLParser;
import simulation.*;

public class SimulationController {

    private Dimension2D simulationViewSize;
    private Simulation simulation;
    private XMLParser readerXML;
    private Group simulationRoot;

    SimulationController(Group simulationRoot) {
        this.simulationRoot = simulationRoot;
        //simulation = new FireSimulation();
        simulation = new PredatorPreySimulation();
        simulation.addGridViewSceneGraph(simulationRoot);
    }
    private void initializeSimulation () {

    }

    //for testing
    /*
    public FireSimulation getSimulation() {
        return (FireSimulation)simulation;
    }*/
    
    public PredatorPreySimulation getSimulation() {
        return (PredatorPreySimulation)simulation;
    }
}
