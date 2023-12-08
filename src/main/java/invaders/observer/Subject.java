package invaders.observer;

/**
 * notify the observers with the current state
 * of the subject
 */
public interface Subject {
    void Notify();
    void setTimer(Timer timer);
    void setScoreboard(Scoreboard scoreboard);
}
