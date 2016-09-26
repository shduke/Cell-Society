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
        // File simulationConfig = new File("src/resources/FireSettings.xml");
        File simulationConfig = new File("src/resources/Fire.xml");
        initializeSimulation(simulationConfig.getAbsolutePath());
        this.simulationRoot = simulationRoot;
        // simulation = new PredatorPreySimulation();
        simulation.addGridViewSceneGraph(simulationRoot);
    }

    /**
     * TODO-How to make more concise?
     * TODO-add in xml exceptions, might have to refactor into other classes like a factory one
     * 
     * @param xmlFilename
     */
    void initializeSimulation (String xmlFilename) {
        Element rootElement = parser.getRootElement(xmlFilename);
        this.simulation = parser.createSimulation(rootElement);
    }


    public Simulation getSimulation () {
        return simulation;
    }
}
