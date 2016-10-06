package grid;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.shape.Polygon;

/**
 * 
 * @author Sean Hudson
 *
 */
public class Triangle extends Polygon {
    private static final int MY_NUM_SIDES = 3;
    private double myWidth;
    private double myHeight;
    private double myRadius;
    private double myWidthStretch;

    Triangle (double height, double gridWidth, int numberOfColumns) {
        super();
        this.myHeight = height;
        this.myRadius = calcRadius();
        this.myWidth = calcSide();

        this.myWidthStretch = calcWidthStretch(gridWidth, numberOfColumns);
        this.getPoints().addAll(calcCoordinates());
    }

    private List<Double> calcCoordinates () {
        List<Double> triangleCoordinates = new ArrayList<Double>();
        for (int n = 0; n < MY_NUM_SIDES; n++) {
            triangleCoordinates
                    .add(myRadius * Math.sin(2 * Math.PI * n / MY_NUM_SIDES) + getWidthOffSet(n));
            triangleCoordinates.add(myRadius * Math.cos(2 * Math.PI * n / MY_NUM_SIDES));
        }
        return triangleCoordinates;
    }

    private double getWidthOffSet (int n) {
        if (n == 1) {
            return myWidthStretch;
        }
        else if (n == 2) {
            return -1 * myWidthStretch;
        }
        return 0;
    }

    private void flipPoint (int index, double direction) {
        getPoints().set(index, getPoints().get(index) + direction * myHeight);
    }

    public void flipTriangle () {
        flipPoint(1, -1);
        flipPoint(3, 1);
        flipPoint(5, 1);
    }

    private double calcWidthStretch (double gridWidth, int numberOfColumns) {
        return (gridWidth / (1 + (numberOfColumns - 1) * 0.5) - myWidth) / 2;
    }

    private double calcRadius () {
        return (calcSide() / 2) / Math.cos(Math.PI / 6);
    }

    private double calcSide () {
        return myHeight / Math.sin(Math.PI / MY_NUM_SIDES);
    }

    public double getStretchedWidth () {
        return myWidth + myWidthStretch * 2;
    }

    public double getStretchedEffectiveWidth () {
        return getStretchedWidth() * 0.5;
    }

    public double getRadius () {
        return myRadius;
    }
}
