package invaders.factory;

import invaders.engine.GameEngine;
import invaders.physics.Vector2D;
import invaders.strategy.ProjectileStrategy;
import javafx.scene.image.Image;

/**
 * The `EnemyProjectile` class represents a projectile fired by an enemy in the game.
 * It extends the `Projectile` class and incorporates a projectile strategy for behavior.
 */
public class EnemyProjectile extends Projectile{
    private ProjectileStrategy strategy;

    public EnemyProjectile(Vector2D position, ProjectileStrategy strategy, Image image) {
        super(position,image);
        this.strategy = strategy;
    }

    public EnemyProjectile(EnemyProjectile enemyProjectile){
        super(enemyProjectile.getPosition().Clone(), enemyProjectile.getImage());
        this.strategy = enemyProjectile.strategy.Clone();
    }

    @Override
    public void update(GameEngine model) {
        strategy.update(this);

        if(this.getPosition().getY()>= model.getGameHeight() - this.getImage().getHeight()){
            this.takeDamage(1);
        }

    }
    @Override
    public String getRenderableObjectName() {
        return "EnemyProjectile";
    }

    @Override
    public String toString(){
        return strategy.toString();
    }

    @Override
    public EnemyProjectile Clone() {
        return new EnemyProjectile(this);
    }

}
