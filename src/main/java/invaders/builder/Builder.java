package invaders.builder;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable;

/**
 * The abstract base class for the builder pattern used to create game objects.
 * Builders implement methods to set various properties of the game object and create the final object.
 */
public abstract class Builder {
    public abstract void buildPosition(Vector2D position);
    public abstract void buildLives(int live);
    public abstract Renderable createRenderable();

    /**
     * Reset builder to its initial state, discarding any previous set properties.
     */
    public abstract void reset();
}
