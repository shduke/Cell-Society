package controller;

import java.io.File;
import org.w3c.dom.Element;
import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import simulation.FireSimulation;
import simulation.Simulation;
import xml.XMLParser;
import simulation.*;


public class SimulationController {

    private Dimension2D simulationViewSize;
    private Simulation simulation;
    private XMLParser parser = new XMLParser();
    private Group simulationRoot;

    SimulationController (Group simulationRoot) {
        //File simulationConfig = new File("src/resources/FireSettings.xml");
        File simulationConfig = new File("src/resources/PredatorPrey.xml");
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
    private void initializeSimulation (String xmlFilename) {
        Element rootElement = parser.getRootElement(xmlFilename);
        String simulationType =
                rootElement.getElementsByTagName("SimulationType").item(0).getAttributes()
                        .getNamedItem("Simulation").getNodeValue();
        if (simulationType.equals("Fire")) {
            simulation = new FireSimulation(rootElement);
        }
        else if (simulationType.equals("GameOfLife")) {
            simulation = new GameOfLifeSimulation();
        }
        else if (simulationType.equals("PredatorPrey")) {
            simulation = new PredatorPreySimulation();
        }
        else {
            simulation = new SegregationSimulation();
        }
    }

    // for testing

//    public FireSimulation getSimulation () {
//        return (FireSimulation) simulation;
//    }

    
      public PredatorPreySimulation getSimulation() {
      return (PredatorPreySimulation)simulation;
      }
     
}
