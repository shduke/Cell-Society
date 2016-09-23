package controller;

import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import simulation.FireSimulation;
import simulation.Simulation;
import xml.XMLParser;


public class SimulationController {
<<<<<<< HEAD
    private Simulation mySimulation;
    private XMLParser myXMLParser;
    
    private void initializeSimulation () {
        myXMLParser.buildSimulation();
    }
    
    public void startSimulation() {
        mySimulation.init();
        play();
    }
    private void play() {
        mySimulation.start();
=======
    private Dimension2D simulationViewSize;
    private Simulation simulation;
    private XMLParser readerXML;
    private Group simulationRoot;

    SimulationController(Group simulationRoot) {
        this.simulationRoot = simulationRoot;
        simulation = new FireSimulation();
        simulation.addGridViewSceneGraph(simulationRoot);
    }
    private void initializeSimulation () {
        //readerXML.buildSimulation();
>>>>>>> master
    }

    //for testing
    public FireSimulation getSimulation() {
        return (FireSimulation)simulation;
    }
}
