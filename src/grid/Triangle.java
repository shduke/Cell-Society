package grid;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.shape.Polygon;

public class Triangle extends Polygon {
    private double width;
    private double height;
    private double radius;
    private double effectiveWidth;
    private double widthStretch;
    
    Triangle(double height, double gridWidth, int numberOfColumns) {
        super();
        this.height = height;
        this.radius = calcRadius();
        this.width = calcSide();
        this.effectiveWidth = calcEffectiveWidth();
        this.widthStretch = calcWidthStretch(gridWidth, numberOfColumns);
        this.getPoints().addAll(calcCoordinates());
    }
    
    private List<Double> calcCoordinates() {
        List<Double> triangleCoordinates = new ArrayList<Double>();
        for(int n = 0; n < 3; n++) {
            triangleCoordinates.add(radius * Math.sin(2 * Math.PI * n / 3 ) + getWidthOffSet(n)); //+ height / 2););
            triangleCoordinates.add(radius * Math.cos(2 * Math.PI * n / 3 ));// 
        }
        return triangleCoordinates;
    }
    
    private double getWidthOffSet(int n) {
        if(n == 1) {
            return widthStretch;
        } else if (n == 2){
            return -1 * widthStretch;
        }
        return 0;
    }
    
    private void flipPoint(int index, double direction) {
            getPoints().set(index, getPoints().get(index) + direction * height);
    }
    
    public void flipTriangle() {
            flipPoint(1, -1);
            flipPoint(3, 1); 
            flipPoint(5, 1); 
    }
    
    private double calcEffectiveWidth() {
        return width * 0.5;
    }
    
    private double calcWidthStretch(double gridWidth, int numberOfColumns) {
        return (gridWidth / (1+(numberOfColumns - 1) * 0.5) - width) / 2;
    }
    
    private double calcRadius() {
        return (calcSide() / 2) / Math.cos(Math.PI / 6);
    }
    
    private double calcSide() {
        return (height / Math.sin(Math.PI / 3));
    }
    
    public double getStretchedWidth() {
        return width + widthStretch * 2;
    }
    
    public double getStretchedEffectiveWidth() {
        return getStretchedWidth() * 0.5;
    }
    
    public double getRadius() {
        return radius;
    }
}
