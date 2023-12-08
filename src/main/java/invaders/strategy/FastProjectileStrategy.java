package invaders.strategy;

import invaders.factory.Projectile;

/**
 * The `FastProjectileStrategy` class represents a fast-moving straight projectile strategy.
 */
public class FastProjectileStrategy implements ProjectileStrategy{

    @Override
    public void update(Projectile p) {
        double newYPos = p.getPosition().getY() + 3;
        p.getPosition().setY(newYPos);
    }

    @Override
    public FastProjectileStrategy Clone() {
        return new FastProjectileStrategy();
    }

    @Override
    public String toString() {
        return "fast_straight";
    }
}
