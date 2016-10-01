package applicationView;

import java.io.File;
import java.util.ResourceBundle;
import controller.ApplicationController;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;

public class SimulationToolbar {

    private final ResourceBundle GUIResources;
    ApplicationController myAppController = new ApplicationController();
    
    public SimulationToolbar() {
        GUIResources = ResourceBundle.getBundle("resources/English");
    }
    
    public void initSimToolbar(int height, int width, Scene myScene) {
        Group root = (Group)myScene.getRoot();
        VBox mySimToolbar = new VBox();
        mySimToolbar.getChildren().addAll(createGraph(myScene));
        checkRunningSim();
        //root.getChildren().add(mySimToolbar);
    }
    
    private LineChart<Number, Number> createGraph(Scene myScene) {
   
        final NumberAxis x_axis = new NumberAxis();
        final NumberAxis y_axis = new NumberAxis();
        x_axis.setLabel(GUIResources.getString("XAxis"));
        final LineChart<Number,Number> myLineChart = 
                new LineChart<Number,Number>(x_axis,y_axis);
        myLineChart.setTitle(GUIResources.getString("ChartTitle"));
        XYChart.Series firstSeries = new XYChart.Series();
        //root.getChildren().add(myLineChart);
        return myLineChart;
    }
    
    private void checkRunningSim() {
        File myFile = myAppController.getMyFile();
        if (myFile != null) {
        System.out.println(myFile.getPath());
        if (myFile.getPath().contains("Fire")) {
            System.out.println("Cats");
        }
        }
    }
    
}
