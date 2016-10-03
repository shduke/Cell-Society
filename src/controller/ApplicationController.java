package controller;

import javafx.animation.Animation.Status;
import javafx.event.EventHandler;
import java.util.List;
import java.io.File;
import java.util.ArrayList;
import java.util.ResourceBundle;
import Exceptions.XMLException;
import applicationView.SimulationToolbar;
import applicationView.Toolbar;
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
    public static final String TITLE = "Cell Society";
    private static final double SECOND_DELAY = 1000.0;
    private Toolbar myToolbar;
    private Timeline myTimeline;
    private final ResourceBundle GUIResources;
    private Scene myScene;
    private Group root;
    private SimulationController mySimulationController;
    private File myFile;
    private BorderPane myApplicationView;

    public ApplicationController () {
        myApplicationView = new BorderPane();
        GUIResources = ResourceBundle.getBundle("resources/English");
        myToolbar = new Toolbar();
        KeyFrame frame = new KeyFrame(Duration.millis(SECOND_DELAY),
                                      e -> update());
        myTimeline = new Timeline();
        myTimeline.setCycleCount(Timeline.INDEFINITE);
        myTimeline.getKeyFrames().add(frame);

    }

    public String getTitle () {
        return TITLE;
    }

    public Scene init (int width, int height) {

        mySimulationController = new SimulationController(root, height, width);
        root = new Group();
        root.getChildren().add(myApplicationView);
        myScene = new Scene(root, width, height, Color.BLACK);
        myToolbar.initToolbar(30, width);
        resetApplicationView();

        // myApplicationView.setBottom();
        // root.relocate(0, 0);
        // root.getChildren().add(root2);
        // mySimToolbar = new SimulationToolbar();
        // mySimToolbar.initSimToolbar(height, 50, myScene);
        // simulationController.setMySimToolbar(mySimToolbar);

        handleEvents(width, root);
        return myScene;
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

    public void play () {
        if (myTimeline.getStatus() == Status.RUNNING) {
            myTimeline.pause();
            myToolbar.getPause().setText(GUIResources.getString("PlayCommand"));
        }
        else {
            myTimeline.play();
            myToolbar.getPause().setText(GUIResources.getString("PauseCommand"));
        }

    }

    public void step () {
        if (myTimeline.getStatus() == Status.RUNNING) {
            myTimeline.stop();
        }
        mySimulationController.updateSimulations();
    }

    public void setSpeed () {
        myTimeline.setRate(myToolbar.getSpeed());
    }

    public void pause () {
        myTimeline.pause();
    }

    private void openFileChooser (FileChooser chooseFile) {
        myFile = chooseFile.showOpenDialog(new Stage());
        if (myFile != null) {
            openFile(myFile);
        }
    }

    public File getMyFile () {
        return myFile;
    }

    public void openFile (File myFile) throws XMLException {
        try {
            String filePath = myFile.getAbsolutePath();
            myToolbar.removeToolbar(root);
            mySimulationController.initializeSimulation(filePath);
            myToolbar.initToolbar(30, 500);
            resetApplicationView();
            handleEvents(500, root);
        }
        catch (XMLException xmlexcept) {
            throw new XMLException(xmlexcept, GUIResources.getString("XMLException"));
        }
    }

    private void handleEvents (int width, Group root) {
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

    public SimulationController getSimulationController () {
        return mySimulationController;
    }

}
