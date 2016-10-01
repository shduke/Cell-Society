package controller;

import javafx.animation.Animation.Status;
import javafx.event.EventHandler;
import java.io.File;
import java.util.ResourceBundle;
import Exceptions.XMLException;
import applicationView.SimulationToolbar;
import applicationView.Toolbar;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import xml.XMLParser;
import xml.XMLParserException;


public class ApplicationController {
    public static final String TITLE = "Cell Society";
    private static final double SECOND_DELAY = 1000.0;
    private Toolbar myToolbar;
    Timeline myTimeline;
    XMLParser myParser = new XMLParser();
    private final ResourceBundle GUIResources;
    private Scene myScene;
    private Group root;
    private File myFile;

    public ApplicationController () {
        GUIResources = ResourceBundle.getBundle("resources/English");
        myToolbar = new Toolbar();
        KeyFrame frame = new KeyFrame(Duration.millis(SECOND_DELAY),
                                      e -> update());
        myTimeline = new Timeline();
        myTimeline.setCycleCount(Timeline.INDEFINITE);
        myTimeline.getKeyFrames().add(frame);
        System.out.println("My rate " + myTimeline.getRate());
    }

    public String getTitle () {
        return TITLE;
    }
    
    public Scene init (int width, int height) {
        root = new Group();
        myScene = new Scene(root, width, height, Color.WHITE);
        simulationController = new SimulationController(root);
        myToolbar.initToolbar(30, width, myScene);
        SimulationToolbar mySimToolbar = new SimulationToolbar();
        mySimToolbar.initSimToolbar(height, 50, myScene);
        handleEvents(width, root);
        return myScene;
    }

    private void update () {
        setSpeed();
        getSimulationController().getSimulation().step();

    }

    private SimulationController simulationController;

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
        simulationController.getSimulation().step();
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

    public void openFile (File myFile) throws XMLException{
        try {
            String filePath = myFile.getAbsolutePath();
            simulationController.initializeSimulation(filePath);
            myToolbar.initToolbar(30, 500, myScene);
            handleEvents(500, root);
        }
        catch (XMLException xmlexcept) {
            throw new XMLException(xmlexcept, GUIResources.getString("XMLException"));
        }
    }

    private void handleEvents (int width, Group root) {
        EventHandler<MouseEvent> event = new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent m) {
                play();
            }
        };
        EventHandler<MouseEvent> eventTwo = new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent m) {
                step();
            }
        };
        EventHandler<MouseEvent> eventThree = new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent m) {
                pause();
                openFileChooser(new FileChooser());
            }
        };
        myToolbar.setPauseButton(event);
        myToolbar.setStepButton(eventTwo);
        myToolbar.setXMLFileButton(eventThree);
    }

    public SimulationController getSimulationController () {
        return simulationController;
    }
}
