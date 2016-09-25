package main;

import controller.ApplicationController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
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
    
    /**
     * Sets the Game up at the beginning.
     * 
     * @return nothing
     */
    @Override///Almost all of this should go in ApplicationController
    public void start (Stage s) {
        applicationController = new ApplicationController();
        s.setTitle(applicationController.getTitle());
        
       
        Scene scene = applicationController.init(SIZE, SIZE);
        
       
        s.setScene(scene);
        s.show();

  
    }
    public static void main (String[] args) {
        launch(args);
    }
}
