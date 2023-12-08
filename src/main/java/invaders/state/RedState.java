package invaders.state;
import invaders.entities.Bunker;

/**
 * represents the red state of the bunker
 */
public class RedState implements BunkerState {
    private Bunker bunker;

    public RedState(Bunker bunker){
        this.bunker = bunker;
    }

    @Override
    public void takeDamage() {
        // bunker.

    }

    @Override
    public String toString() {
        return "RedState";
    }
}
