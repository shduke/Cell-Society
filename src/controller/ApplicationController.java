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

    public String getTitle () {
        return TITLE;
    }

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
            myToolbar.getPause().setText(myGUIResources.getString("PlayCommand"));
        }
        else {
            myTimeline.play();
            myToolbar.getPause().setText(myGUIResources.getString("PauseCommand"));
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

    public SimulationController getSimulationController () {
        return mySimulationController;
    }

}
