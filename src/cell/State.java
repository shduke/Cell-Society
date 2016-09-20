package cell;
import javafx.scene.paint.Color;
public enum State {
    
    EMPTY(0,Color.BLUE),
    FISH(1,Color.YELLOW),
    SHARK(2, Color.RED),
    DEAD(3, Color.WHITE),
    FIRE(4, Color.RED),
    BURNING(5, Color.ORANGE);
    
    
    private final int myValue;
    private final Color myColor;
    State(int value, Color color){
        myValue = value;
        myColor = color;
    }
}
