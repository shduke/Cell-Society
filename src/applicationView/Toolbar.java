package applicationView;

import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
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

    public void initToolbar (int height, int width, Scene myScene) {
        Group root = (Group) myScene.getRoot();
        myScene.setRoot(root);
        HBox myToolbar = new HBox(height);
        slider = new Slider(0.5, 2, 1);
        pause = new Button(GUIResources.getString("PlayCommand"));
        step = new Button(GUIResources.getString("StepCommand"));
        loadXMLbutton = new Button(GUIResources.getString("LoadXML"));
        myToolbar.getChildren().addAll(slider, pause, step, loadXMLbutton);
        root.getChildren().add(myToolbar);
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
