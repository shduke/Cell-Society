package controller;

import simulation.Simulation;
import xml.XMLParser;


public class SimulationController {
    private Simulation simulation;
    private XMLParser readerXML;

    private void initializeSimulation () {
        readerXML.buildSimulation();
    }

}
