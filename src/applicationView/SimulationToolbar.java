package applicationView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import controller.ApplicationController;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.event.ActionEvent;


public class SimulationToolbar {
    private final ResourceBundle GUIResources;
    ApplicationController myAppController = new ApplicationController();
    Group root;
    List<Slider> mySliders;
    private Series<Number, Number> firstSeries = new XYChart.Series<Number, Number>();
    private ArrayList<Integer> myGraphValues;
    VBox myToolbar;

    public SimulationToolbar () {
        myToolbar = new VBox();
        mySliders = new ArrayList<Slider>();
        GUIResources = ResourceBundle.getBundle("resources/English");
    }

    public void initSimToolbar (int height, int width) {
        // root = new Group();
        // VBox mySimToolbar = new VBox();
        // mySimToolbar.getChildren().add(createGraph(myScene));
        // checkRunningSim();
        // for(Slider s : mySliders){
        // mySimToolbar.getChildren().add(s);
        // }
        // root.getChildren().add(mySimToolbar);

    }

    private LineChart<Number, Number> myLineChart =
            new LineChart<Number, Number>(new NumberAxis(), new NumberAxis());

    private LineChart<Number, Number> createGraph (Scene myScene) {

        final NumberAxis x_axis = new NumberAxis();
        final NumberAxis y_axis = new NumberAxis();
        x_axis.setLabel(GUIResources.getString("XAxis"));
        // myLineChart.setTitle(GUIResources.getString("ChartTitle"));
        myLineChart.setPrefWidth(500);
        myLineChart.setPrefHeight(40);
        myLineChart.setTranslateY(370);
        return myLineChart;
    }

    public Series<Number, Number> updateGraph (List<Integer> myOutput) {
        firstSeries.getData().add(new Data<Number, Number>(myOutput.get(0), myOutput.get(1)));
        System.out.println("Hi " + firstSeries.getData());
        myLineChart.getData().add(firstSeries);
        return firstSeries;
    }

    private void checkRunningSim () {
        // TODO: Fix this entire method
        File myFile = myAppController.getMyFile();
        if (myFile != null) {
            System.out.println(myFile.getPath());
            if (myFile.getPath().contains("Fire")) {
                // System.out.println("Cats");
            }
        }
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
