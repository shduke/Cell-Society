package controller;

import java.util.Collection;
import javafx.geometry.Dimension2D;
import simulation.Simulation;
import xml.XMLParser;


public class SimulationController {
    private Dimension2D simulationViewSize;
    private Simulation simulation;
    private XMLParser readerXML;

    SimulationController(Dimension2D simulationViewSize) {
        this.simulationViewSize = simulationViewSize;
    }
    
    private void initializeSimulation () {
        Collection simulationConfigData = readerXML.buildSimulation();
        
    }

}
