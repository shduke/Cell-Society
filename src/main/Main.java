package main;

import controller.ApplicationController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class for creating an animated scene.
 * 
 */
public class Main extends Application {
    public static final int SIZE = 500;
    public static final int FRAMES_PER_SECOND = 1;


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
