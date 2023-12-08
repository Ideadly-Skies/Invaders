package invaders.factory;

import invaders.physics.Vector2D;
import invaders.strategy.ProjectileStrategy;
import javafx.scene.image.Image;

/**
 * The `EnemyProjectileFactory` class is responsible for creating enemy projectiles in the game.
 */
public class EnemyProjectileFactory implements ProjectileFactory{
    @Override
    public Projectile createProjectile(Vector2D position, ProjectileStrategy strategy, Image image) {
        return new EnemyProjectile(position,strategy,image);
    }

    @Override
    public EnemyProjectileFactory Clone() {
        return new EnemyProjectileFactory();
    }
}
