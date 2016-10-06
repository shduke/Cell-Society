package applicationView;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

/**
 * 
 * @author Kayla Schulz
 * @author Michael Schroeder
 *
 */
public class SimulationToolbar {

    private List<Slider> mySliders;
    private VBox myToolbar;

    public SimulationToolbar () {
        myToolbar = new VBox();
        mySliders = new ArrayList<Slider>();
    }

    public void addSlider (Slider slider, String label) {
        mySliders.add(slider);
        Label sliderLabel = new Label(label);
        sliderLabel.setTextFill(Color.WHITE);
        myToolbar.getChildren().addAll(slider, sliderLabel);
    }

    public VBox getRoot () {
        return myToolbar;
    }

}
