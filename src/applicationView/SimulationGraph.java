package applicationView;

import java.util.List;
import java.util.ResourceBundle;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.shape.Rectangle;


public class SimulationGraph {
    private final ResourceBundle GUIResources;

    private Series<Number, Number> myFirstSeries = new XYChart.Series<Number, Number>();
    private Series<Number, Number> mySecondSeries = new XYChart.Series<Number, Number>();
    private Series<Number, Number> myThirdSeries = new XYChart.Series<Number, Number>();

    public SimulationGraph () {
        GUIResources = ResourceBundle.getBundle("resources/English");
    }

    public LineChart<Number, Number> createGraph () {
        final NumberAxis x_axis = new NumberAxis();
        final NumberAxis y_axis = new NumberAxis();
        LineChart<Number, Number> myLineChart =
                new LineChart<Number, Number>(x_axis, y_axis);
        x_axis.setLabel(GUIResources.getString("XAxis"));
        myLineChart.setPrefHeight(50);
        myLineChart.getData().addAll(myFirstSeries, mySecondSeries, myThirdSeries);
        // myLineChart.setTitle(GUIResources.getString("ChartTitle"));
        myLineChart.setLegendVisible(true);
        myLineChart.setLegendSide(Side.RIGHT);
        myLineChart.setCreateSymbols(false);
        return myLineChart;
    }
    
    public void addToLegend(List<String> myList) {
        myFirstSeries.setName(myList.get(0));
        if (myList.size() > 1) {
            mySecondSeries.setName(myList.get(1));
        }
        if (myList.size() > 2) {
            myThirdSeries.setName(myList.get(2));
        }
    }

    public Series<Number, Number> updateGraph (List<Integer> myOutput) {
        myFirstSeries.getData().add(new Data<Number, Number>(myOutput.get(0), myOutput.get(1)));
        if (myOutput.size() > 2) {
            mySecondSeries.getData()
                    .add(new Data<Number, Number>(myOutput.get(0), myOutput.get(2)));
        }
        if (myOutput.size() > 3) {
            myThirdSeries.getData().add(new Data<Number, Number>(myOutput.get(0), myOutput.get(3)));
        }
        return myFirstSeries;
    }

}
