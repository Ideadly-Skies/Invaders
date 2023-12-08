package invaders.factory;

import invaders.physics.Vector2D;
import invaders.strategy.ProjectileStrategy;
import javafx.scene.image.Image;

/**
 * The `ProjectileFactory` interface defines the contract for creating projectile objects in the game.
 */
public interface ProjectileFactory {
    public Projectile createProjectile(Vector2D position, ProjectileStrategy strategy, Image image);
    public ProjectileFactory Clone();
}
