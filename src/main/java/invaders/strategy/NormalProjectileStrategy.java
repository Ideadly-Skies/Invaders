package invaders.strategy;

import invaders.factory.Projectile;

/**
 * The `NormalProjectileStrategy` class represents a normal straight projectile strategy.
 */
public class NormalProjectileStrategy implements ProjectileStrategy{
    @Override
    public void update(Projectile p) {
        double newYPos = p.getPosition().getY() - 2;
        p.getPosition().setY(newYPos);
    }

    @Override
    public NormalProjectileStrategy Clone() {
        return new NormalProjectileStrategy();
    }
}
