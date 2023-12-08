package invaders.builder;

import invaders.entities.Bunker;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable;
import javafx.scene.image.Image;

/**
 * A builder class for constructing Bunker game objects.
 */
public class BunkerBuilder extends Builder{
    private Bunker bunker;

    @Override
    public void buildPosition(Vector2D position) {
        bunker.setPosition(position);
    }

    @Override
    public void buildLives(int lives) {
        bunker.setLives(lives);
    }

    @Override
    public Renderable createRenderable() {
        return (Renderable) bunker;
    }

    /**
     * Reset the bunker builder to its initial state, discarding any previously set properties.
     */
    @Override
    public void reset() {
        bunker = new Bunker();
    }

    public void buildImage(Image image) {
        bunker.setImage(image);
        bunker.setWidth((int) image.getWidth());
        bunker.setHeight((int) image.getHeight());
    }
}
