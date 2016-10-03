package applicationView;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import controller.ApplicationController;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;


public class SimulationToolbar {
    private final ResourceBundle GUIResources;
    ApplicationController myAppController = new ApplicationController();
    Group root;
    private Series<Number, Number> firstSeries= new XYChart.Series<Number, Number>();
    private ArrayList<Integer> myGraphValues;
    
    public SimulationToolbar() {
        GUIResources = ResourceBundle.getBundle("resources/English");
    }
    
    public void initSimToolbar(int height, int width, Scene myScene) {
        root = (Group)myScene.getRoot();
        VBox mySimToolbar = new VBox();
        mySimToolbar.getChildren().add(createGraph(myScene));
        checkRunningSim();
        root.getChildren().add(mySimToolbar);

    }
    
    private LineChart<Number,Number> myLineChart = new LineChart<Number,Number>(new NumberAxis(),new NumberAxis());
    
    private LineChart<Number, Number> createGraph(Scene myScene) {
   
        final NumberAxis x_axis = new NumberAxis();
        final NumberAxis y_axis = new NumberAxis();
        x_axis.setLabel(GUIResources.getString("XAxis"));
        //myLineChart = 
        //        new LineChart<Number,Number>(x_axis,y_axis);
        myLineChart.setTitle(GUIResources.getString("ChartTitle"));
        //firstSeries = new XYChart.Series<Number, Number>();
        myLineChart.setPrefWidth(500);
        myLineChart.setPrefHeight(50);
        myLineChart.setTranslateY(400);
        //myGraphValues = new ArrayList<Integer>();
        //myGraphValues = (ArrayList<Integer>) myAppController.graphCalculations();
        //System.out.println("Vals " + myGraphValues.get(0) + " " + myGraphValues.get(1));
        //firstSeries.getData().add(new XYChart.Data<Number, Number>(myGraphValues.get(0), myGraphValues.get(1)));
        //myLineChart.getData().addAll(firstSeries);
        return myLineChart;
    }
    
    public Series<Number, Number> updateGraph (List<Integer> myOutput) {
        firstSeries.getData().add(new Data<Number, Number>(myOutput.get(0), myOutput.get(1)));
        System.out.println("Hi " + firstSeries.getData());
        myLineChart.getData().add(firstSeries);
        System.out.println("r: " + root);
        //root.getChildren().add(myLineChart);
        return firstSeries;
    }
    
    private void checkRunningSim() {
        //TODO: Fix this entire method
        File myFile = myAppController.getMyFile();
        if (myFile != null) {
        System.out.println(myFile.getPath());
        if (myFile.getPath().contains("Fire")) {
            //System.out.println("Cats");
        }
        }
    }
    
}
