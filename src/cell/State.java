package cell;

import javafx.scene.paint.Color;

/**
 * 
 * @author Sean Hudson
 *
 */
public interface State {
    Color getColor ();

    double getProbability ();

    void setProbability (double probability);

    String name ();
}
