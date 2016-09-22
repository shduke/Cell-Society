package controller;

import simulation.Simulation;
import xml.XMLParser;


public class SimulationController {
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
    }

}
