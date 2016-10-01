package controller;

import java.io.File;
import org.w3c.dom.Element;
import javafx.scene.Group;
import simulation.Simulation;
import xml.XMLParser;


public class SimulationController {

    private Simulation simulation;
    private XMLParser parser = new XMLParser();
    private Group simulationRoot;

    SimulationController (Group simulationRoot) {
        this.simulationRoot = simulationRoot;
        File simulationConfig = new File("src/resources/Segregation.xml");
        initializeSimulation(simulationConfig.getAbsolutePath());
        
    }

    /**
     * 
     * @param xmlFilename
     */
    void initializeSimulation (String xmlFilename) {
        Element rootElement = parser.getRootElement(xmlFilename);
        this.simulation = parser.createSimulation(rootElement);
        simulation.removeGridViewSceneGraph(simulationRoot);
        simulation.addGridViewSceneGraph(simulationRoot);
        simulation.countCellsinGrid();
    }

    public Simulation getSimulation () {
        return simulation;
    }
    
    public void updateSimulations() {
        simulation.step();
    }
}
