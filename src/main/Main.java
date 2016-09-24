package main;

import controller.ApplicationController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import applicationView.Toolbar;

/**
 * Main class for creating an animated scene.
 * 
 */
public class Main extends Application {
    public static final int SIZE = 500;
    public static final int FRAMES_PER_SECOND = 1;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    //private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 100.0;


    private ApplicationController applicationController;
    private Toolbar myToolbar;
    /**
     * Sets the Game up at the beginning.
     * 
     * @return nothing
     */
    @Override
    public void start (Stage s) {
        applicationController = new ApplicationController();
        s.setTitle(applicationController.getTitle());
        myToolbar = new Toolbar();
       
        Scene scene = applicationController.init(SIZE, SIZE);
        EventHandler<MouseEvent> event = new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent m){
                applicationController.play();
            }
        };
        EventHandler<MouseEvent> eventTwo = new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent m){
                applicationController.step();
            }
        };
        myToolbar.initToolbar(20,500,scene);
        myToolbar.setPauseButton(event);
        myToolbar.setStepButton(eventTwo);
        s.setScene(scene);
        s.show();

  
    }
    public static void main (String[] args) {
        launch(args);
    }
}
