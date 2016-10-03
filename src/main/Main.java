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
    public static final int SIZE = 650;
    public static final int FRAMES_PER_SECOND = 1;

    private ApplicationController myApplicationController;

    /**
     * Sets the Game up at the beginning.
     * 
     * @return nothing
     */
    @Override
    public void start (Stage s) {
        myApplicationController = new ApplicationController();
        s.setTitle(myApplicationController.getTitle());

        Scene scene = myApplicationController.init(SIZE, SIZE);

        s.setScene(scene);
        s.show();

    }

    public static void main (String[] args) {
        launch(args);
    }
}
