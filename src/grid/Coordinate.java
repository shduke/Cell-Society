package grid;

public class Coordinate {
    private double x;
    private double y;

    public Coordinate (double x, double y) { /// put cell class in grid package?
        this.x = x;
        this.y = y;
    }

    public Coordinate add (Coordinate coordinate) {
        return new Coordinate(x + coordinate.getX(), y + coordinate.getY());
    }
    
    public Coordinate subtract (Coordinate coordinate) {
        return new Coordinate(x - coordinate.getX(), y - coordinate.getY());
    }

    @Override
    public boolean equals (Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || !(obj instanceof Coordinate)) {
            return false;
        }
        return ((x == ((Coordinate) obj).getX()) && (y == ((Coordinate) obj).getY()));
    }

    @Override
    public int hashCode () {
        /*
         * int result = x;
         * result = 31 * result + y;
         * return result;
         */
        return (int) (x + (y + (((x + 1) / 2) * ((x + 1) / 2))));
    }

    public double getX () {
        return x;
    }

    public double getY () {
        return y;
    }
    
    @Override
    public String toString(){
        return x + ", " + y;
    }

}
