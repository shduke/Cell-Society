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
 * This class creates the main toolbar for the simulation. It holds the functionality
 * to play, pause, speed up, and load a new XML file into the simulation frame.
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

    /**
     * Initializes the resource bundle that will be used to set the text in the program
     */
    public Toolbar () {
        String initFile = "resources";
        String fileName = "/English";
        myGUIResources = ResourceBundle.getBundle(initFile + fileName);
    }

    /**
     * Gets the pause button
     * 
     * @return the pause button
     */
    public Button getPause () {
        return myPause;
    }

    /**
     * Initializes an HBox for the toolbar and creates the buttons and sliders to be
     * added onto the toolbar
     * 
     * @param height
     * @param width
     */
    public void initToolbar (int height, int width) {
        myToolbar = new HBox(height);
        mySlider = new Slider(MIN_SLIDER_VALUE, MAX_SLIDER_VALUE, 1);
        myPause = new Button(myGUIResources.getString("PlayCommand"));
        myStep = new Button(myGUIResources.getString("StepCommand"));
        myLoadXMLbutton = new Button(myGUIResources.getString("LoadXML"));
        myToolbar.getChildren().addAll(mySlider, myPause, myStep, myLoadXMLbutton);
    }

    /**
     * Returns the main toolbar
     * 
     * @return the main toolbar
     */
    public Node getToolbar () {
        return myToolbar;
    }

    /**
     * Removes the toolbar from root
     * 
     * @param The main root containing toolbar
     */
    public void removeToolbar (Group root) {
        root.getChildren().remove(myToolbar);
    }

    /**
     * Gets the speed set by the user on the slider
     * 
     * @return value on the slider
     */
    public double getSpeed () {
        return mySlider.getValue();
    }

    /**
     * Sets the pause button to handle an event
     * 
     * @param event when pause is pressed
     */
    public void setPauseButton (EventHandler<MouseEvent> event) {
        myPause.setOnMouseClicked(event);
    }

    /**
     * Sets the step button to handle an event
     * 
     * @param event when step is pressed
     */
    public void setStepButton (EventHandler<MouseEvent> event) {
        myStep.setOnMouseClicked(event);
    }

    /**
     * Sets the XML file button to handle an event
     * 
     * @param event when 'Load XML File' button is pressed
     */
    public void setXMLFileButton (EventHandler<MouseEvent> event) {
        myLoadXMLbutton.setOnMouseClicked(event);
    }
}
