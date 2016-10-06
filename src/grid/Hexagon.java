package grid;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.shape.Polygon;

/**
 * 
 * @author Sean Hudson
 *
 */
public class Hexagon extends Polygon {
    private static final int MY_NUM_SIDES = 6;
    private double myWidth;
    private double myHeight;
    private double myRadius;
    private double myEffectiveWidth;
    private double myWidthStretch;

    public Hexagon (double height, double gridWidth, int numberOfColumns) {
        super();
        this.myHeight = height;
        this.myRadius = calcRadius();
        this.myWidth = myRadius * 2;
        this.myEffectiveWidth = calcEffectiveWidth();
        this.myWidthStretch = calcWidthStretch(gridWidth, numberOfColumns);
        this.getPoints().addAll(calcCoordinates());
    }

    private List<Double> calcCoordinates () {
        List<Double> hexagonCoordinates = new ArrayList<Double>();
        for (int n = 0; n < MY_NUM_SIDES; n++) {
            hexagonCoordinates
                    .add(myRadius * Math.cos(2 * Math.PI * n / MY_NUM_SIDES) + getWidthOffSet(n));
            hexagonCoordinates
                    .add(myRadius * Math.sin(2 * Math.PI * n / MY_NUM_SIDES) + myHeight / 2);
        }
        return hexagonCoordinates;
    }

    private double getWidthOffSet (int n) {
        if (n == 0 || n == 1 || n == 5) {
            return myWidthStretch;
        }
        return -1 * myWidthStretch;
    }

    private double calcEffectiveWidth () {
        return myWidth - ((myHeight / 2) / Math.tan(Math.PI / (MY_NUM_SIDES / 2)));
    }

    private double calcWidthStretch (double gridWidth, int numberOfColumns) {
        return (gridWidth - (myEffectiveWidth * numberOfColumns + (myWidth - myEffectiveWidth))) /
               (numberOfColumns * 2);
    }

    private double calcRadius () {
        return (myHeight / 2) / Math.sin(Math.PI / (MY_NUM_SIDES / 2));
    }

    public double getStretchedWidth () {
        return myWidth + myWidthStretch * 2;
    }

    public double getStretchedEffectiveWidth () {
        return getStretchedWidth() - ((myHeight / 2) / Math.tan(Math.PI / (MY_NUM_SIDES / 2)));
    }

    public double getEffectiveWidth () {
        return myEffectiveWidth;
    }
}
