package grid;

/**
 * 
 * @author Sean Hudson
 *
 */
public class Coordinate {
    private double myX;
    private double myY;

    public Coordinate (double x, double y) {
        this.myX = x;
        this.myY = y;
    }

    public Coordinate add (Coordinate coordinate) {
        return new Coordinate(myX + coordinate.getX(), myY + coordinate.getY());
    }

    public Coordinate subtract (Coordinate coordinate) {
        return new Coordinate(myX - coordinate.getX(), myY - coordinate.getY());
    }

    public Coordinate scale (double scaleFactor) {
        return new Coordinate(myX * scaleFactor, myY * scaleFactor);
    }

    @Override
    public boolean equals (Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || !(obj instanceof Coordinate)) {
            return false;
        }
        return (myX == ((Coordinate)obj).getX()) && (myY == ((Coordinate)obj).getY());
    }

    @Override
    public int hashCode () {
        /*
         * int result = x;
         * result = 31 * result + y;
         * return result;
         */
        return (int)(myX + (myY + (((myX + 1) / 2) * ((myX + 1) / 2))));
    }

    public double getX () {
        return myX;
    }

    public double getY () {
        return myY;
    }

    @Override
    public String toString () {
        return myX + ", " + myY;
    }

}
