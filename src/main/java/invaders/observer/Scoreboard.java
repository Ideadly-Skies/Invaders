package invaders.observer;

import invaders.engine.GameEngine;

/**
 * one of the observer which is responsible for
 * displaying the score to the screen
 */
public class Scoreboard implements Observer {
    private GameEngine gameEngine;
    private int score;

    public Scoreboard(GameEngine gameEngine){
        // set instance with supplied parameter
        this.gameEngine = gameEngine;
    }

    @Override
    public void Update(){
        // update the score with that from gameEngine
        this.score = gameEngine.getScore();
    }

    public int getScore(){
        // get score of gameEngine class
        return this.score;
    }
}
