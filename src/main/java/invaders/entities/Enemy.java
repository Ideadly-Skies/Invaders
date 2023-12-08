package invaders.entities;

import invaders.engine.GameEngine;
import invaders.factory.EnemyProjectileFactory;
import invaders.factory.Projectile;
import invaders.factory.ProjectileFactory;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable;
import invaders.strategy.ProjectileStrategy;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Random;

/**
 * The `Enemy` class represents an enemy unit in the game.
 */
public class Enemy implements Renderable {
    private Vector2D position;
    private int lives = 1;
    private Image image;
    private int xVel = -1;
    private ArrayList<Projectile> enemyProjectile;
    private ArrayList<Projectile> pendingToDeleteEnemyProjectile;
    private ProjectileStrategy projectileStrategy;
    private ProjectileFactory projectileFactory;
    private Image projectileImage;
    private Random random = new Random(12345);
    private String EnemyType;

    public Enemy(Vector2D position) {
        this.position = position;
        this.projectileFactory = new EnemyProjectileFactory();
        this.enemyProjectile = new ArrayList<>();
        this.pendingToDeleteEnemyProjectile = new ArrayList<>();
    }

    public Enemy(Enemy enemy){
        this.position = enemy.getPosition().Clone();
        this.projectileFactory = enemy.getProjectileFactory().Clone();
        this.enemyProjectile = (ArrayList<Projectile>) enemy.getEnemyProjectile().clone();
        this.pendingToDeleteEnemyProjectile = (ArrayList<Projectile>) enemy.getPendingToDeleteEnemyProjectile().clone();
        this.image = enemy.getImage();
        this.projectileImage = enemy.projectileImage;
        this.EnemyType = enemy.getEnemyType();
        this.setProjectileStrategy(enemy.getProjectileStrategy().Clone());
        this.xVel = enemy.xVel;
    }

    public String getEnemyType(){
        return this.EnemyType;
    }

    public ProjectileFactory getProjectileFactory(){
        return this.projectileFactory;
    }

    public ArrayList<Projectile> getEnemyProjectile(){
        return (ArrayList<Projectile>) this.enemyProjectile.clone();
    }

    public ArrayList<Projectile> getPendingToDeleteEnemyProjectile(){
        return (ArrayList<Projectile>) this.pendingToDeleteEnemyProjectile.clone();
    }

    @Override
    public void update(GameEngine engine) {
        if(enemyProjectile.size()<3){
            if(this.isAlive() &&  random.nextInt(120)==20){
                Projectile p = projectileFactory.createProjectile(new Vector2D(position.getX() + this.image.getWidth() / 2, position.getY() + image.getHeight() + 2),projectileStrategy, projectileImage);
                enemyProjectile.add(p);
                engine.getPendingToAddRenderable().add(p);
            }
        }else{
            pendingToDeleteEnemyProjectile.clear();
            for(Projectile p : enemyProjectile){
                if(!p.isAlive()){
                    engine.getPendingToRemoveRenderable().add(p);
                    pendingToDeleteEnemyProjectile.add(p);
                }
            }

            for(Projectile p: pendingToDeleteEnemyProjectile){
                enemyProjectile.remove(p);
            }
        }

        if(this.position.getX()<=this.image.getWidth() || this.position.getX()>=(engine.getGameWidth()-this.image.getWidth()-1)){
            this.position.setY(this.position.getY()+25);
            xVel*=-1;
        }

        this.position.setX(this.position.getX() + xVel);

        if((this.position.getY()+this.image.getHeight())>=engine.getPlayer().getPosition().getY()){
            engine.getPlayer().takeDamage(Integer.MAX_VALUE);
        }

        /*
        Logic TBD
         */

    }

    @Override
    public Image getImage() {
        return this.image;
    }

    @Override
    public double getWidth() {
        return this.image.getWidth();
    }

    @Override
    public double getHeight() {
       return this.image.getHeight();
    }

    @Override
    public Vector2D getPosition() {
        return this.position;
    }

    @Override
    public Layer getLayer() {
        return Layer.FOREGROUND;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setProjectileImage(Image projectileImage) {
        this.projectileImage = projectileImage;
    }

    @Override
    public void takeDamage(double amount) {
        this.lives-=1;
    }

    @Override
    public double getHealth() {
        return this.lives;
    }

    @Override
    public String getRenderableObjectName() {
        return "Enemy";
    }

    @Override
    public boolean isAlive() {
        return this.lives>0;
    }

    public void setProjectileStrategy(ProjectileStrategy projectileStrategy) {
        this.projectileStrategy = projectileStrategy;
    }

    /**
     * return the type of the enemy
     * @return, string representation of that enemy
     */
    @Override
    public String toString(){
        return EnemyType;
    }

    /**
     * set enemy type with the supplied param
     * @param type, type of the enemy
     */
    public void setEnemyType(String type){
        this.EnemyType = type;
    }

    @Override
    public Enemy Clone() {
        return new Enemy(this);
    }

    /**
     * getter method for projectile strategy
     * @return, a projectile strategy instance
     */
    public ProjectileStrategy getProjectileStrategy(){
        return this.projectileStrategy;
    }

}
