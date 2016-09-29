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
    
    Hexagon(double height, double gridWidth, int numberOfRows) {
        super();
        this.height = height;
        this.radius = calcRadius();
        this.width = radius * 2;
        this.effectiveWidth = calcEffectiveWidth();
        this.widthStrechFactor = calcWidthStretchFactor(gridWidth, numberOfRows);
        this.widthStretch = calcWidthStretch(gridWidth, numberOfRows);
        this.getPoints().addAll(calcCoordinates());
    }
    
    private List<Double> calcCoordinates() {
        List<Double> hexagonCoordinates= new ArrayList<Double>();
        for(int n = 0; n < 6; n++) {
            hexagonCoordinates.add(radius * Math.cos(2 * Math.PI * n / 6 ) + getWidthOffset(n));
            hexagonCoordinates.add(radius * Math.sin(2 * Math.PI * n / 6 ) + height / 2);
        }
        return hexagonCoordinates;
    }
    
    private double getWidthOffset(int n) {
        if(n == 0 || n == 1 || n == 5) {
            return widthStretch;
        }
        return -1 * widthStretch;
    }
    
    private double calcEffectiveWidth() {
        return width - ((height / 2) / Math.tan(Math.PI / 3) );
    }
    
    private double calcWidthStretch(double gridWidth, int numberOfRows) {
        return (gridWidth - (effectiveWidth * numberOfRows + (width - effectiveWidth))) / (numberOfRows*2);
    }
    
    private double calcWidthStretchFactor(double gridWidth, int numberOfRows) {
        System.out.println(gridWidth - (effectiveWidth * numberOfRows + (width - effectiveWidth)));
        return ((gridWidth - (effectiveWidth * numberOfRows + (width - effectiveWidth))) / numberOfRows + width) / width;
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
