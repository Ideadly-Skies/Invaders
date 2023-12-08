package invaders.strategy;

import invaders.factory.Projectile;

/**
 * The `SlowProjectileStrategy` class implements a projectile strategy for slow, straight-line movement.
 */
public class SlowProjectileStrategy implements ProjectileStrategy{
    @Override
    public void update(Projectile p) {
        double newYPos = p.getPosition().getY() + 1;
        p.getPosition().setY(newYPos);
    }

    @Override
    public SlowProjectileStrategy Clone() {
        return new SlowProjectileStrategy();
    }

    @Override
    public String toString() {
        return "slow_straight";
    }
}
