package grid;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.shape.Polygon;

public class Hexagon extends Polygon {
    private double width;
    private double height;
    private double radius;
    private double effectiveWidth;
    private double widthStrechFactor;
    private double widthStretch;
    
    Hexagon(double height, double gridWidth, int numberOfColumns) {
        super();
        this.height = height;
        this.radius = calcRadius();
        this.width = radius * 2;
        this.effectiveWidth = calcEffectiveWidth();
        this.widthStretch = calcWidthStretch(gridWidth, numberOfColumns);
        this.getPoints().addAll(calcCoordinates());
    }
    
    private List<Double> calcCoordinates() {
        List<Double> hexagonCoordinates= new ArrayList<Double>();
        for(int n = 0; n < 6; n++) {
            hexagonCoordinates.add(radius * Math.cos(2 * Math.PI * n / 6 ) + getWidthOffSet(n));
            hexagonCoordinates.add(radius * Math.sin(2 * Math.PI * n / 6 ) + height / 2);
        }
        return hexagonCoordinates;
    }
    
    private double getWidthOffSet(int n) {
        if(n == 0 || n == 1 || n == 5) {
            return widthStretch;
        }
        return -1 * widthStretch;
    }
    
    private double calcEffectiveWidth() {
        return width - ((height / 2) / Math.tan(Math.PI / 3) );
    }
    
    private double calcWidthStretch(double gridWidth, int numberOfColumns) {
        return (gridWidth - (effectiveWidth * numberOfColumns + (width - effectiveWidth))) / (numberOfColumns*2);
    }
    
    private double calcRadius() {
        return (height / 2) / Math.sin(Math.PI / 3);
    }
    
    public double getStretchedWidth() {
        return width + widthStretch * 2;
    }
    
    public double getStretchedEffectiveWidth() {
        return getStretchedWidth() - ((height / 2) / Math.tan(Math.PI / 3));
    }
    
    public double getEffectiveWidth() {
        return effectiveWidth;
    }
}
