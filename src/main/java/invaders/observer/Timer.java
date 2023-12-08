package invaders.observer;

import invaders.engine.GameEngine;

/**
 * One of the observer which is responsible
 * for displaying the time to the screen
 */
public class Timer implements Observer {
    private GameEngine gameEngine;
    private int time;

    public Timer(GameEngine gameEngine){
        // set instance with supplied parameter
        this.gameEngine = gameEngine;
    }

    @Override
    public void Update(){
        // update time with that of gameEngine
        this.time = gameEngine.getTimeRemaining();
    }

    public int getTime(){
        // get current time of timer
        return time;
    }

}
