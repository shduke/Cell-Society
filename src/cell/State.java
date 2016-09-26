package cell;
import javafx.scene.paint.Color;
public enum State {
    
    EMPTY(0,Color.BLUE),
    FISH(1,Color.YELLOW),
    SHARK(2, Color.RED),
    DEAD(3, Color.WHITE),
    FIRE(4, Color.RED),
    BURNING(5, Color.ORANGE), 
    TREE(6, Color.GREEN),
    X(7, Color.RED),
    O(8, Color.GREEN),
    LIVING(9, Color.DARKGREEN);

    
    
    private final int myValue;
    private final Color myColor;
    State(int value, Color color){
        myValue = value;
        myColor = color;
    }
    
    public int getIntValue() {
        return myValue;
    }
    
    public Color getColor() {
        return myColor;
    }
}
