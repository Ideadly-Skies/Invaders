package invaders.factory;

import invaders.engine.GameEngine;
import invaders.physics.Vector2D;
import invaders.strategy.ProjectileStrategy;
import javafx.scene.image.Image;

import java.io.File;

/**
 * The `PlayerProjectile` class represents projectiles fired by the player in the game. These projectiles
 * are responsible for causing damage to enemy entities.
 */
public class PlayerProjectile extends Projectile {
    private ProjectileStrategy strategy;

    public PlayerProjectile(Vector2D position, ProjectileStrategy strategy) {
        super(position, new Image(new File("src/main/resources/player_shot.png").toURI().toString(), 10, 10, true, true));
        this.strategy = strategy;
    }

    public PlayerProjectile(PlayerProjectile playerProjectile){
        super(playerProjectile.getPosition().Clone(), playerProjectile.getImage());
        this.strategy = playerProjectile.strategy.Clone();
    }

    @Override
    public void update(GameEngine model) {
        strategy.update(this);

        if(this.getPosition().getY() <= this.getImage().getHeight()){
            this.takeDamage(1);
        }
    }
    @Override
    public String getRenderableObjectName() {
        return "PlayerProjectile";
    }

    @Override
    public PlayerProjectile Clone() {
        return new PlayerProjectile(this);
    }
}
