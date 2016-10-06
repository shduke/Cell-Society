package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import org.w3c.dom.Element;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import simulation.Simulation;
import xml.XMLParser;
import xml.XMLParserException;


public class SimulationController {

    private static final String GUI_RESOURCES = "resources/English";
    private ResourceBundle myResources = ResourceBundle.getBundle(GUI_RESOURCES);
    private List<Simulation> mySimulations;
    private XMLParser myParser = new XMLParser();

    /**
     * This sets the default simulation to be run and calls to initialize the
     * simulation
     * 
     * @param simulationRoot
     * @param height
     * @param width
     */
    SimulationController (Group simulationRoot, int height, int width) {
        this.mySimulations = new ArrayList<Simulation>();
        File simulationConfig = new File("src/resources/ForagingAnts.xml");
        initializeSimulation(simulationConfig.getAbsolutePath());
    }

    /**
     * Initializes the simulation and catches an error (throwing the error box)
     * when a file in the incorrect format is input
     * 
     * @param xmlFilename
     */
    void initializeSimulation (String xmlFilename) {
        try {
            Element rootElement = myParser.getRootElement(xmlFilename);

            if (mySimulations.size() > 0) {
                mySimulations.remove(mySimulations.size() - 1);
            }
            this.mySimulations.add(0, myParser.createSimulation(rootElement));
            mySimulations.get(mySimulations.size() - 1).countCellsinGrid();
        }
        catch (XMLParserException e) {
            createErrorBox(myResources.getString("XMLExceptionBadConfigFile"));
        }
    }

    /**
     * Gets the current simulation
     * 
     * @return the current simulation
     */
    public Simulation getSimulation () {
        return mySimulations.get(0);
    }

    /**
     * Calls the simulation step method to update the current simulation
     */
    public void updateSimulations () {

        for (Simulation s : mySimulations) {
            s.step();
        }
    }

    private void createErrorBox (String message) {
        Alert errorBox = new Alert(AlertType.ERROR);
        errorBox.setTitle(message);
        errorBox.setContentText(message);
        errorBox.showAndWait();
    }
}
