package invaders.state;
import invaders.entities.Bunker;
import javafx.scene.image.Image;
import java.io.File;

/**
 * represents the yellow state of the bunker
 */
public class YellowState implements BunkerState {
    private Bunker bunker;

    public YellowState(Bunker bunker){
        this.bunker = bunker;
    }

    @Override
    public void takeDamage() {
        bunker.setImage(new Image(new File("src/main/resources/bunkerRed.png").toURI().toString()));
        bunker.setState(new RedState(bunker));
    }

    @Override
    public String toString() {
        return "YellowState";
    }
}