package invaders.memento;

import invaders.rendering.Renderable;
import java.util.ArrayList;
import java.util.List;

/**
 * this would be the memento object itself to store the state
 * of the GameEngine class when a shot is fired by the player
 */
public class LastSnapshot {
    private ArrayList<Renderable> renderableList;
    private int score;
    private int clockTimer;

    public LastSnapshot(List<Renderable> renderables, int score, int clockTimer){
        this.renderableList = new ArrayList<>(renderables);
        this.score = score;
        this.clockTimer = clockTimer;
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

}
