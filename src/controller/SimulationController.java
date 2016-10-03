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

    SimulationController (Group simulationRoot, int height, int width) {
        this.mySimulations = new ArrayList<Simulation>();
        this.simulationRoot = simulationRoot;
        File simulationConfig = new File("src/resources/Sugar.xml");
        initializeSimulation(simulationConfig.getAbsolutePath());
    }

    /**
     * 
     * @param xmlFilename
     */
    void initializeSimulation (String xmlFilename) {
        try {
            Element rootElement = myParser.getRootElement(xmlFilename);

            if (mySimulations.size() > 0) {
                // mySimulations.get(mySimulations.size() -
                // 1).getSimulationView().getChildren().clear();
                mySimulations.remove(mySimulations.size() - 1);
            }
            this.mySimulations.add(0, myParser.createSimulation(rootElement));
            // simulationRoot.getChildren().add(mySimulations.get(0).getSimulationView());
            mySimulations.get(mySimulations.size() - 1).countCellsinGrid();
        }
        catch (XMLParserException e) {
            createErrorBox(myResources.getString("XMLExceptionBadConfigFile"));
        }
    }

    private void createErrorBox (String message) {
        Alert errorBox = new Alert(AlertType.ERROR);
        errorBox.setTitle(message);
        errorBox.setContentText(message);
        errorBox.showAndWait();
    }

    public Simulation getSimulation () {
        return mySimulations.get(0);
    }

    // public void setMySimToolbar (SimulationToolbar mySimToolbar) {
    // this.mySimToolbar = mySimToolbar;
    // }

    public void updateSimulations () {

        for (Simulation s : mySimulations) {
            // mySimToolbar.updateGraph(s.countCellsinGrid());
            s.step();
        }
    }
}