package controller;

import javafx.animation.Animation.Status;
import javafx.event.EventHandler;
import java.io.File;
import java.util.ResourceBundle;
import applicationView.Toolbar;
import exceptions.XMLException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;


public class ApplicationController {
    private static final String TITLE = "Cell Society";
    private static final int TOOLBAR_HEIGHT = 30;
    private static final int TOOLBAR_WIDTH = 650;
    private static final double SECOND_DELAY = 1000.0;
    private Toolbar myToolbar;
    private Timeline myTimeline;
    private final ResourceBundle myGUIResources;
    private Scene myScene;
    private Group myRoot;
    private SimulationController mySimulationController;
    private File myFile;
    private BorderPane myApplicationView;

    /**
     * Sets and calls the vital aspects of the program, such as the application
     * view and the timeline that updates the frame as it steps through the
     * program
     * 
     */
    public ApplicationController () {
        myApplicationView = new BorderPane();
        myGUIResources = ResourceBundle.getBundle("resources/English");
        myToolbar = new Toolbar();
        KeyFrame frame = new KeyFrame(Duration.millis(SECOND_DELAY),
                                      e -> update());
        myTimeline = new Timeline();
        myTimeline.setCycleCount(Timeline.INDEFINITE);
        myTimeline.getKeyFrames().add(frame);

    }

    /**
     * Get the title for the top of the screen of the program
     * @return The title of the program
     */
    public String getTitle () {
        return TITLE;
    }

    /**
     * Initializes the scene of the program
     * 
     * @param width
     * @param height
     * @return the scene to be displayed for the duration of the application
     */
    public Scene init (int width, int height) {
        mySimulationController = new SimulationController(myRoot, height, width);
        myRoot = new Group();
        myRoot.getChildren().add(myApplicationView);
        myScene = new Scene(myRoot, width, height, Color.BLACK);
        myToolbar.initToolbar(TOOLBAR_HEIGHT, width);
        resetApplicationView();
        handleEvents();
        return myScene;
    }

    /**
     * Play or pause the simulation based off of Running status
     */
    public void play () {
        if (myTimeline.getStatus() == Status.RUNNING) {
            myTimeline.pause();
            myToolbar.getPause().setText(myGUIResources.getString("PlayCommand"));
        }
        else {
            myTimeline.play();
            myToolbar.getPause().setText(myGUIResources.getString("PauseCommand"));
        }
    }

    /**
     * Step through the simulation based off of running status
     */
    public void step () {
        if (myTimeline.getStatus() == Status.RUNNING) {
            myTimeline.stop();
        }
        mySimulationController.updateSimulations();
    }

    /**
     * Set the speed based off of the input from the main toolbar slider
     */
    public void setSpeed () {
        myTimeline.setRate(myToolbar.getSpeed());
    }

    /**
     * Pause the simulation
     */
    public void pause () {
        myTimeline.pause();
    }

    /**
     * Get the file chosen by the user
     * 
     * @return File chosen by the user
     */
    public File getMyFile () {
        return myFile;
    }

    /**
     * Opens file chooser for the user to select a file to run in the program
     * 
     * @param file
     * @throws XMLException
     */
    public void openFile (File file) throws XMLException {
        try {
            String filePath = file.getAbsolutePath();
            myToolbar.removeToolbar(myRoot);
            mySimulationController.initializeSimulation(filePath);
            myToolbar.initToolbar(TOOLBAR_HEIGHT, TOOLBAR_WIDTH);
            resetApplicationView();
            handleEvents();
        }
        catch (XMLException xmlexcept) {
            throw new XMLException(xmlexcept, myGUIResources.getString("XMLException"));
        }
    }

    /**
     * Gets the instance of simulation controller
     * 
     * @return the instance of simulation controller
     */
    public SimulationController getSimulationController () {
        return mySimulationController;
    }

    private void resetApplicationView () {
        myApplicationView.setCenter(mySimulationController.getSimulation().getGridView().getRoot());
        myApplicationView.setRight(mySimulationController.getSimulation().getSimulationToolbar());
        myApplicationView.setTop(myToolbar.getToolbar());
        myApplicationView
                .setBottom(mySimulationController.getSimulation().getGraphView().createGraph());

    }

    private void update () {
        setSpeed();
        getSimulationController().updateSimulations();

    }

    private void openFileChooser (FileChooser chooseFile) {
        myFile = chooseFile.showOpenDialog(new Stage());
        if (myFile != null) {
            openFile(myFile);
        }
    }

    private void handleEvents () {
        EventHandler<MouseEvent> pause = new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent m) {
                play();
            }
        };
        EventHandler<MouseEvent> step = new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent m) {
                step();
            }
        };
        EventHandler<MouseEvent> loadXML = new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent m) {
                pause();
                openFileChooser(new FileChooser());
            }
        };
        myToolbar.setPauseButton(pause);
        myToolbar.setStepButton(step);
        myToolbar.setXMLFileButton(loadXML);
    }

}
