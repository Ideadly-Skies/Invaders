package invaders.factory;

import invaders.physics.Vector2D;
import invaders.strategy.ProjectileStrategy;
import javafx.scene.image.Image;

/**
 * The `PlayerProjectileFactory` class is responsible for creating player projectiles in the game.
 */
public class PlayerProjectileFactory implements ProjectileFactory{
    @Override
    public Projectile createProjectile(Vector2D position, ProjectileStrategy strategy, Image image) {
        return new PlayerProjectile(new Vector2D(position.getX(),position.getY()),strategy);
    }
    @Override
    public PlayerProjectileFactory Clone() {
        return new PlayerProjectileFactory();
    }
}
