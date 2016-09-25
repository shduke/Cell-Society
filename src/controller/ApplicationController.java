package controller;


import javafx.animation.Animation.Status;
import javafx.event.EventHandler;
import java.io.File;
import java.util.EventListener;
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
import javafx.event.*;

public class ApplicationController {
    public static final String TITLE = "Cell Society";
    private static final double SECOND_DELAY = 1000.0;
    private Toolbar myToolbar;
    Timeline myTimeline;
    XMLParser myParser = new XMLParser();
    
    public ApplicationController() {
        myToolbar = new Toolbar();
        KeyFrame frame = new KeyFrame(Duration.millis(SECOND_DELAY),
                                      e -> update());
        myTimeline = new Timeline();
        myTimeline.setCycleCount(Timeline.INDEFINITE);
        myTimeline.getKeyFrames().add(frame);
        System.out.println("My rate " + myTimeline.getRate());
    }
    public String getTitle() {
        return TITLE;
    }
    private void update(){
        setSpeed();
        getSimulationController().getSimulation().step();
        
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
    
    public void setSpeed(){
        
        myTimeline.setRate(myToolbar.getSpeed());
    }
    
    public void pause(){
        myTimeline.pause();
    }
    
    public void loadFile() {
        myTimeline.pause();
    }
    
    public void openFile(File myFile) {
        try {
            String filePath = myFile.getAbsolutePath();
            simulationController.initializeSimulation(filePath);
        }
        //TODO: create XML Exception
        catch (XMLParserException xmlexcept){
            
        }
    }
    
    private void openFileChooser(FileChooser chooseFile) {
        File myFile = chooseFile.showOpenDialog(new Stage());
        if (myFile != null) {
            openFile(myFile);
        }
    }
    
    //filler code
    public Scene init(int width, int height) {
        Group root = new Group();
        Scene myScene = new Scene(root, width, height, Color.WHITE);
        EventHandler<MouseEvent> event = new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent m){
                play();
            }
        };
        EventHandler<MouseEvent> eventTwo = new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent m){
                step();
            }
        };
        EventHandler<MouseEvent> eventThree = new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent m){
                openFileChooser(new FileChooser());
            }
        };
        
        
        simulationController = new SimulationController(root);
        myToolbar.initToolbar(20,width,myScene);
        myToolbar.setPauseButton(event);
        myToolbar.setStepButton(eventTwo);
        myToolbar.setXMLFileButton(eventThree);

        return myScene;
    }
    
    public SimulationController getSimulationController() {
        return simulationController;
    }
}
