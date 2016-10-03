package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Element;
import applicationView.SimulationToolbar;
import javafx.scene.Group;
import simulation.Simulation;
import xml.XMLParser;


public class SimulationController {

    private List<Simulation> mySimulations;
    private XMLParser parser = new XMLParser();
    private Group simulationRoot;
    

    SimulationController (Group simulationRoot, int height, int width) {
        this.mySimulations = new ArrayList<Simulation>();
        this.simulationRoot = simulationRoot;
        File simulationConfig = new File("src/resources/GameOfLife.xml");
        initializeSimulation(simulationConfig.getAbsolutePath());
    }

    /**
     * 
     * @param xmlFilename
     */
    void initializeSimulation (String xmlFilename) {
        Element rootElement = parser.getRootElement(xmlFilename);

        if (mySimulations.size() > 0) {
            //mySimulations.get(mySimulations.size() - 1).getSimulationView().getChildren().clear();
            mySimulations.remove(mySimulations.size() - 1);
        }
        this.mySimulations.add(0, parser.createSimulation(rootElement));
        //simulationRoot.getChildren().add(mySimulations.get(0).getSimulationView());
        mySimulations.get(mySimulations.size() - 1).countCellsinGrid();
    }
    
  /*  public void graphCalculations() {
        
        List<Integer> myOutput = new ArrayList<Integer>();
        myOutput = getSimulation().countCellsinGrid();
        mySimToolbar.updateGraph(myOutput);
    }*/

    public Simulation getSimulation () {
        return mySimulations.get(0);
    }

    
    
    //public void setMySimToolbar (SimulationToolbar mySimToolbar) {
    //    this.mySimToolbar = mySimToolbar;
    //}

    public void updateSimulations () {
        
        for (Simulation s : mySimulations) {
            //mySimToolbar.updateGraph(s.countCellsinGrid());
            s.step();
        }
    }
}
