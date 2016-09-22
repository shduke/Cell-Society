package controller;

import javafx.geometry.Dimension2D;


public class ApplicationController {

    private static final Dimension2D DEFAULT_APPLICATION_SIZE = new Dimension2D(1000, 800);
    private SimulationController simulationController;

    ApplicationController() {
        this.simulationController = new SimulationController(calcSimulationSize());
    }

    /**
     * TODO--Calculate the simulation view size. This might need to go in a different location
     * 
     * @return
     */
    private Dimension2D calcSimulationSize () {
        return new Dimension2D(DEFAULT_APPLICATION_SIZE.getWidth(),
                               DEFAULT_APPLICATION_SIZE.getHeight());
    }
}
