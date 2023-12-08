package invaders.builder;

import invaders.entities.Enemy;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable;
import invaders.strategy.FastProjectileStrategy;
import invaders.strategy.SlowProjectileStrategy;
import javafx.scene.image.Image;

import java.io.File;

/**
 * The EnemyBuilder class is responsible for constructing Enemy game objects with various properties and strategies.
 */
public class EnemyBuilder extends Builder{
    private Enemy enemy;

    @Override
    public void buildPosition(Vector2D position) {
        enemy.setPosition(position);
    }

    @Override
    public void buildLives(int live) {
        enemy.setLives(live);
    }

    @Override
    public Renderable createRenderable() {
        return enemy;
    }

    @Override
    public void reset(){
        enemy = new Enemy(new Vector2D(0,0));
    }

    public void buildImageAndStrategy(String strategy){
        if(strategy.equals("fast_straight")){
            enemy.setProjectileStrategy(new FastProjectileStrategy());
            enemy.setImage(new Image(new File("src/main/resources/fast_alien.png").toURI().toString(), 20, 20, true, true));
            enemy.setProjectileImage(new Image(new File("src/main/resources/alien_shot_fast.png").toURI().toString(), 10, 10, true, true));
            enemy.setEnemyType("fast_alien"); // set enemy type to fast
        }else if(strategy.equals("slow_straight")){
            enemy.setProjectileStrategy(new SlowProjectileStrategy());
            enemy.setImage(new Image(new File("src/main/resources/slow_alien.png").toURI().toString(), 20, 20, true, true));
            enemy.setProjectileImage(new Image(new File("src/main/resources/alien_shot_slow.png").toURI().toString(), 10, 10, true, true));
            enemy.setEnemyType("slow_alien"); // set enemy type to slow
        }else{
            enemy.setProjectileStrategy(null);
            enemy.setImage(null);
        }
    }
}
