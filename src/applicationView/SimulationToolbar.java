package applicationView;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;


/**
 * This class creates the toolbar that will be a part of every simulation.
 * It determines what parameters will have the ability to be shifted in the
 * middle of the simulation.
 * 
 * @author Kayla Schulz
 * @author Michael Schroeder
 *
 */
public class SimulationToolbar {

    private List<Slider> mySliders;
    private VBox myToolbar;

    /**
     * Sets the toolbar and sliders
     */
    public SimulationToolbar () {
        myToolbar = new VBox();
        mySliders = new ArrayList<Slider>();
    }

    /**
     * Creates a slider with a corresponding label. Typically called from
     * a particular simulation.
     * 
     * @param slider
     * @param label
     */
    public void addSlider (Slider slider, String label) {
        mySliders.add(slider);
        Label sliderLabel = new Label(label);
        sliderLabel.setTextFill(Color.WHITE);
        myToolbar.getChildren().addAll(slider, sliderLabel);
    }

    /**
     * Returns the simulation toolbar
     * 
     * @return simulation toolbar
     */
    public VBox getRoot () {
        return myToolbar;
    }

}
