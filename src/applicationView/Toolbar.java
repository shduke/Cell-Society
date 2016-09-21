package applicationView;

import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;

public class Toolbar {
    
    private int toolbarSize;
    Button pause;
    Button step;
    Button loadXML;
    HBox myToolbar;
    
    protected void initToolbar(int height, int width, Scene myScene, Group root) {
        myScene.setRoot(root);
        myToolbar = new HBox(20.0);
        Slider slider = new Slider(0, 1, 0.5);
        pause = new Button("Pause");
        step = new Button("Step");
        ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList(
                                                                       "Fire", "Game of Life", "Predator-Prey", "Segregation")
                                                                   );
        loadXML = new Button("Load XML File");
        myToolbar.getChildren().addAll(slider, pause, step, cb, loadXML);
        addToRoot(root);
    }
    
    private void addToRoot(Group root) {
        root.getChildren().add(myToolbar);
    }
    
    private void pauseSimulation() {
        
    }
    
}
