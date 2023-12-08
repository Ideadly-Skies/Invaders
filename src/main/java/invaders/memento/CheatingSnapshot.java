package invaders.memento;

import invaders.factory.EnemyProjectile;
import invaders.entities.Enemy;
import invaders.rendering.Renderable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * this would be the CheatingMemento which stores the state
 * of the game during each update loop of the GameEngine class
 */
public class CheatingSnapshot {
    private ArrayList<Renderable> renderableList;
    private int score;
    private int clockTimer;
    private boolean isRemoveAlien;
    private boolean isRemoveFast;

    public CheatingSnapshot(List<Renderable> renderables, int score, int clockTimer){
        this.renderableList = new ArrayList<>(renderables);
        this.score = score;
        this.clockTimer = clockTimer;
    }

    public void loadCheat(){
        /* load cheat */

        if (isRemoveAlien){
            removeAliens(isRemoveFast);
        } else {
            removeProjectiles(isRemoveFast);
        }
    }

    public void setRemoveAlien(boolean value){
        this.isRemoveAlien = value;
    }

    public void setRemoveFast(boolean value){
        this.isRemoveFast = value;
    }

    public List<Renderable> getRenderableList(){
        return this.renderableList;
    }

    public int getScore(){
        return this.score;
    }

    public int getClockTimer(){
        return this.clockTimer;
    }

    // helper function
    public void removeAliens(boolean fastType){
        Iterator<Renderable> iterator = renderableList.iterator();
        while (iterator.hasNext()) {
            Renderable renderable = iterator.next();

            if (renderable.getRenderableObjectName().equals("Enemy")) {
                if (fastType) {
                    if (((Enemy) renderable).getEnemyType().equals("fast_alien")) {
                        // remove fast alien from the list
                        score += 4;
                        iterator.remove();
                    }
                } else {
                    if (((Enemy) renderable).getEnemyType().equals("slow_alien")) {
                        // remove slow alien from the list
                        score += 3;
                        iterator.remove();
                    }
                }
            }
        }
    }

    public void removeProjectiles(boolean fastType){
        Iterator<Renderable> iterator = renderableList.iterator();
        while (iterator.hasNext()) {
            Renderable renderable = iterator.next();

            if (renderable.getRenderableObjectName().equals("EnemyProjectile")) {
                if (fastType) {
                    if (((EnemyProjectile) renderable).toString().equals("fast_straight")) {
                        // remove fast straight projectile
                        score += 2;
                        iterator.remove();
                    }
                } else {
                    if (((EnemyProjectile) renderable).toString().equals("slow_straight")) {
                        // remove slow straight projectile
                        score += 1;
                        iterator.remove();
                    }
                }

            }
        }
    }


}
