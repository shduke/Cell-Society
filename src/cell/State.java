package cell;

import javafx.scene.paint.Color;


/**
 * Sets the interface for state that a cell can have
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
