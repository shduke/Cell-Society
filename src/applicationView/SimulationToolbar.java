package applicationView;

import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;

public class SimulationToolbar {

    private final ResourceBundle GUIResources;
    
    public SimulationToolbar() {
        GUIResources = ResourceBundle.getBundle("resources/English");
    }
    
    public void initSimToolbar(int height, int width, Scene myScene) {
        VBox mySimToolbar = new VBox();
        createGraph();
    }
    
    private void createGraph() {
        final NumberAxis x_axis = new NumberAxis();
        final NumberAxis y_axis = new NumberAxis();
        x_axis.setLabel(GUIResources.getString("XAxis"));
        final LineChart<Number,Number> myLineChart = 
                new LineChart<Number,Number>(x_axis,y_axis);
        myLineChart.setTitle(GUIResources.getString("ChartTitle"));
    }
    
}
