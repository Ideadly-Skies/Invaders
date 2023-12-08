package invaders.factory;

import invaders.physics.Vector2D;
import invaders.rendering.Renderable;
import javafx.scene.image.Image;

/**
 * The `Projectile` class represents a projectile in the game, such as a player or enemy projectile.
 */
public abstract class Projectile implements Renderable {
    private int lives = 1;
    private Vector2D position;
    private final Image image;

    public Projectile(Vector2D position, Image image) {
        this.position = position;
        this.image = image;
    }

    @Override
    public Vector2D getPosition() {
        return position;
    }

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public Layer getLayer() {
        return Layer.FOREGROUND;
    }

    @Override
    public double getWidth() {
        return 10;
    }

    @Override
    public double getHeight() {
        return 10;
    }

    @Override
    public void takeDamage(double amount) {
        this.lives-=1;
    }

    @Override
    public double getHealth() {
        return this.lives;
    }

    @Override
    public boolean isAlive() {
        return this.lives>0;
    }

}
