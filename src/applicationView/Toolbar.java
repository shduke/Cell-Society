package applicationView;

import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.input.*;

/**
 * 
 * @author Kayla Schulz
 *
 */
public class Toolbar {

    private static final double MIN_SLIDER_VALUE = 0.5;
    private static final double MAX_SLIDER_VALUE = 2.0;
    private final ResourceBundle myGUIResources;
    private Slider mySlider;
    private Button myPause;
    private Button myStep;
    private Button myLoadXMLbutton;
    private HBox myToolbar;

    public Toolbar () {
        String initFile = "resources";
        String fileName = "/English";
        myGUIResources = ResourceBundle.getBundle(initFile + fileName);
    }

    public Button getPause () {
        return myPause;
    }

    public void initToolbar (int height, int width) {
        myToolbar = new HBox(height);
        mySlider = new Slider(MIN_SLIDER_VALUE, MAX_SLIDER_VALUE, 1);
        myPause = new Button(myGUIResources.getString("PlayCommand"));
        myStep = new Button(myGUIResources.getString("StepCommand"));
        myLoadXMLbutton = new Button(myGUIResources.getString("LoadXML"));
        myToolbar.getChildren().addAll(mySlider, myPause, myStep, myLoadXMLbutton);
    }

    public Node getToolbar () {
        return myToolbar;
    }

    public void removeToolbar (Group root) {
        root.getChildren().remove(myToolbar);
    }

    public double getSpeed () {
        return mySlider.getValue();
    }

    public void setPauseButton (EventHandler<MouseEvent> event) {
        myPause.setOnMouseClicked(event);
    }

    public void setStepButton (EventHandler<MouseEvent> event) {
        myStep.setOnMouseClicked(event);
    }

    public void setXMLFileButton (EventHandler<MouseEvent> event) {
        myLoadXMLbutton.setOnMouseClicked(event);
    }
}
