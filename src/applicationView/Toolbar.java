package applicationView;

import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.input.*;


public class Toolbar {

    private final ResourceBundle GUIResources;
    Slider slider;
    Button pause;
    Button step;
    Button loadXMLbutton;

    public Button getPause () {
        return pause;
    }

    public Toolbar () {
        String initFile = "resources";
        String fileName = "/English";
        GUIResources = ResourceBundle.getBundle(initFile + fileName);
    }

    private HBox myToolbar;
    
    public void initToolbar (int height, int width) {
        //Group root = (Group) myScene.getRoot();
        //myScene.setRoot(root);
        myToolbar = new HBox(height);
        slider = new Slider(0.5, 2, 1);
        pause = new Button(GUIResources.getString("PlayCommand"));
        step = new Button(GUIResources.getString("StepCommand"));
        loadXMLbutton = new Button(GUIResources.getString("LoadXML"));
        myToolbar.getChildren().addAll(slider, pause, step, loadXMLbutton);
        //root.getChildren().add(myToolbar);
    }
    public Node getToolbar(){
        return myToolbar;
    }
    public void removeToolbar(Group root) {
        root.getChildren().remove(myToolbar);
    }

    public double getSpeed () {
        return slider.getValue();
    }

    public void setPauseButton (EventHandler<MouseEvent> event) {
        pause.setOnMouseClicked(event);
    }

    public void setStepButton (EventHandler<MouseEvent> event) {
        step.setOnMouseClicked(event);
    }

    public void setXMLFileButton (EventHandler<MouseEvent> event) {
        loadXMLbutton.setOnMouseClicked(event);
    }
}
