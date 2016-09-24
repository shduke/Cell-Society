package controller;


import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class ApplicationController {
    public static final String TITLE = "Cell Society";
    private static final double SECOND_DELAY = 100.0;
    Timeline myTimeline;
    public ApplicationController() {
        KeyFrame frame = new KeyFrame(Duration.millis(SECOND_DELAY),
                                      e -> getSimulationController().getSimulation().step());
        myTimeline = new Timeline();
        myTimeline.setCycleCount(Timeline.INDEFINITE);
        myTimeline.getKeyFrames().add(frame);
    }
    public String getTitle() {
        return TITLE;
    }
    
    private SimulationController simulationController;
    
    public void play(){
        if(myTimeline.getStatus() == Status.RUNNING){
            myTimeline.pause();
        }
        else{
            myTimeline.play();
        }
        
    }
    public void step(){
        if(myTimeline.getStatus() == Status.RUNNING){
            myTimeline.stop();
            
        }
        simulationController.getSimulation().step();
    }
    public void pause(){
        myTimeline.pause();
    }
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
