package grid;

public class Coordinate {
    private int x;
    private int y;
    
    public Coordinate(int x, int y) { ///put cell class in grid package?
        this.x = x;
        this.y = y;
    }
    
    @Override
    public boolean equals(Object obj){
        if(obj==this){
            return true;
        }
        if (obj == null || !(obj instanceof Coordinate)) {
            return false;
        }
        return ((x == ((Coordinate)obj).getX()) && (y == ((Coordinate)obj).getY()));
    }
    
    @Override
    public int hashCode() {
        /*int result = x;
        result = 31 * result + y;
        return result;*/
        return x + (y + ( (( x +1 ) / 2) * (( x + 1 ) /2) ) );
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
}
