package applicationView;

import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.*;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.input.*;

public class Toolbar {
    
    private int toolbarSize;
    private final ResourceBundle GUIResources;
    Slider slider;
    Button pause;
    Button step;
    Button loadXMLbutton;
    
    public Toolbar() {
        GUIResources = ResourceBundle.getBundle("resources/English");
    }
    
    public void initToolbar(int height, int width, Scene myScene) {
        Group root = (Group)myScene.getRoot();
        
        myScene.setRoot(root);
        HBox myToolbar = new HBox(30.0);
        slider = new Slider(0.5, 2, 1);
        pause = new Button(GUIResources.getString("PauseCommand"));
        step = new Button(GUIResources.getString("StepCommand"));
        loadXMLbutton = new Button(GUIResources.getString("LoadXML"));
        myToolbar.getChildren().addAll(slider, pause, step, loadXMLbutton);
        root.getChildren().add(myToolbar);
    }
    
    public double getSpeed(){
        return slider.getValue();
    }
    public void setPauseButton(EventHandler<MouseEvent> event){
        pause.setOnMouseClicked(event);
      
    }
    
    public void setStepButton(EventHandler<MouseEvent> event) {
        step.setOnMouseClicked(event);
    }
    
    public void setXMLFileButton(EventHandler<MouseEvent> event) {
        loadXMLbutton.setOnMouseClicked(event);
    }
    
   
    
}
