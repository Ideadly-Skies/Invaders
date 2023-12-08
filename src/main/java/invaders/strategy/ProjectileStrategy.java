package invaders.strategy;

import invaders.factory.Projectile;

/**
 * The `ProjectileStrategy` interface defines a strategy for updating the behavior of projectiles.
 */
public interface ProjectileStrategy {
   public void update(Projectile p);

   public ProjectileStrategy Clone();
}
