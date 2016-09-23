package controller;


import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

public class ApplicationController {
    public static final String TITLE = "Cell Society";
    
    public String getTitle() {
        return TITLE;
    }
    
    private SimulationController simulationController;
    
    //filler code
    public Scene init(int width, int height) {
        Group root = new Group();
        Scene myScene = new Scene(root, width, height, Color.WHITE);
        simulationController = new SimulationController(root);
        return myScene;
    }
    
    public SimulationController getSimulationController() {
        return simulationController;
    }
}
