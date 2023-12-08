package invaders.engine;

import java.util.List;
import java.util.ArrayList;

import invaders.entities.EntityViewImpl;
import invaders.entities.SpaceBackground;
import invaders.observer.Scoreboard;
import invaders.observer.Timer;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

import invaders.entities.EntityView;
import invaders.rendering.Renderable;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

/**
 * The `GameWindow` class is responsible for managing the game's graphical user interface (GUI).
 * It creates and maintains the game window, including the game scene, UI components, and maintains
 * a dependency link with the GameEngine class.
 */
public class GameWindow {
    private final int width;
    private final int height;
    private Scene scene;
    private Pane pane;
    private GameEngine model;
    private List<EntityView> entityViews =  new ArrayList<EntityView>();
    private Renderable background;

    private double xViewportOffset = 0.0;
    private double yViewportOffset = 0.0;
    // private static final double VIEWPORT_MARGIN = 280.0;

    /**
     * the observers of the GameEngine class
     */
    private Timer timer;
    private Scoreboard scoreboard;

    // create UI component to display time and score
    Text timeText = new Text();
    Text scoreText = new Text();
    private Text gameOverText;
    private Text youWinText;

    public GameWindow(GameEngine model){
        this.model = model;
        this.width =  model.getGameWidth();
        this.height = model.getGameHeight();

        pane = new Pane();
        scene = new Scene(pane, width, height);
        this.background = new SpaceBackground(pane);

        KeyboardInputHandler keyboardInputHandler = new KeyboardInputHandler(this.model);

        scene.setOnKeyPressed(keyboardInputHandler::handlePressed);
        scene.setOnKeyReleased(keyboardInputHandler::handleReleased);

        // setup observer and subject here
        scoreboard = new Scoreboard(model);
        timer = new Timer(model);
        model.setScoreboard(scoreboard);
        model.setTimer(timer);

        // create scoreText UI text
        scoreText.setLayoutX(model.getGameWidth()-90);
        scoreText.setLayoutY(model.getGameHeight()-760);
        scoreText.setFill(Color.WHITE);

        // set initial value of scoreText and add to Pane
        scoreText.setText("Score:  " + scoreboard.getScore());
        scoreText.setVisible(true);
        ((Pane) scene.getRoot()).getChildren().add(scoreText);

        // create timeText UI text
        timeText.setLayoutX(model.getGameWidth() - 200);
        timeText.setLayoutY(model.getGameHeight() - 760);
        timeText.setFill(Color.WHITE);
        int timerValue = model.getTimeRemaining();
        String timeTextValue = String.format("Time: %02d:%02d", timerValue / 60, timerValue % 60);
        timeText.setText(timeTextValue);
        timeText.setVisible(true);
        ((Pane) scene.getRoot()).getChildren().add(timeText);

        // game over text
        gameOverText = new Text("You Lose!");
        gameOverText.setStyle("-fx-font-size: 24px; -fx-fill: red;");
        gameOverText.setLayoutX(width / 2 - 50);
        gameOverText.setLayoutY(height / 2);
        gameOverText.setVisible(false);
        pane.getChildren().add(gameOverText);

        // you win text
        youWinText = new Text("You Win!");
        youWinText.setStyle("-fx-font-size: 24px; -fx-fill: green;");
        youWinText.setLayoutX(width / 2 - 50);
        youWinText.setLayoutY(height / 2);
        youWinText.setVisible(false);
        pane.getChildren().add(youWinText);
    }

    public void run() {
        // adjusted this so the game could update every second
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(17), t -> this.draw()));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


    private void draw(){
        // you win!
        if (model.getEnemies().size() == 0){
            updateScoreText();
            displayYouWin();
            return;
        }

        // you lose!
        if (model.isGameOver()) {
            displayGameOver();
            return;
        }

        model.update();

        if (model.getScoreTextUpdated()){
            updateScoreText();
        }

        if (model.getClockTextUpdated()){
            updateTimeText();
        }

        List<Renderable> renderables = model.getRenderables();
        for (Renderable entity : renderables) {
            boolean notFound = true;
            for (EntityView view : entityViews) {
                if (view.matchesEntity(entity)) {
                    notFound = false;
                    view.update(xViewportOffset, yViewportOffset);
                    break;
                }
            }
            if (notFound) {
                EntityView entityView = new EntityViewImpl(entity);
                entityViews.add(entityView);
                pane.getChildren().add(entityView.getNode());
            }
        }

        if (model.isResettingLevel()){
            // mark all object for deletion
            for(EntityView entityView : entityViews){
                entityView.markForDelete();
            }
            // initialize game object back to screen
            model.initializeRenderables();
        }

        if (model.isReverting()){
            // mark all object for deletion
            for(EntityView entityView : entityViews){
                entityView.markForDelete();
            }
            // set the snapshot to the one saved to the caretaker
            model.setLastSnapshot(model.getSnapshotCaretaker().retrieveSnapshot());
            // undo last shot
            model.undoLastShot();
            model.setReverting(false);
            model.getSnapshotCaretaker().saveSnapshot(null);
        }

        if (model.isCheating()){
            // mark all object for deletion
            for(EntityView entityView : entityViews){
                entityView.markForDelete();
            }

            // set the snapshot to the one saved by the caretaker
            model.setCheatingSnapshot(model.getCheatingCaretaker().retrieveSnapshot());
            // cheat & set cheating snapshot to false
            model.cheat();
            model.setCheating(false);
            model.getCheatingCaretaker().saveSnapshot(null);
        }

        for (Renderable entity : renderables){
            if (!entity.isAlive()){
                for (EntityView entityView : entityViews){
                    if (entityView.matchesEntity(entity)){
                        entityView.markForDelete();
                    }
                }
            }
        }

        for (EntityView entityView : entityViews) {
            if (entityView.isMarkedForDelete()) {
                pane.getChildren().remove(entityView.getNode());
            }
        }

        model.getRenderables().removeAll(model.getPendingToRemoveRenderable());
        model.getRenderables().addAll(model.getPendingToAddRenderable());
        model.getPendingToAddRenderable().clear();
        model.getPendingToRemoveRenderable().clear();

        entityViews.removeIf(EntityView::isMarkedForDelete);
    }

    public Scene getScene() {
        return scene;
    }

    public void updateScoreText(){
        // remove previous score text
        ((Pane) scene.getRoot()).getChildren().remove(scoreText);

        // update value of score text
        scoreText.setText("Score:  " + scoreboard.getScore());
        scoreText.setVisible(true);
        ((Pane) scene.getRoot()).getChildren().add(scoreText);

        model.setScoreTextUpdated(false);
    }

    public void updateTimeText(){
        // remove previous timer text
        ((Pane) scene.getRoot()).getChildren().remove(timeText);

        // update value of timer text
        int timerValue = timer.getTime();
        String timeTextValue = String.format("Time: %02d:%02d", timerValue / 60, timerValue % 60);
        timeText.setText(timeTextValue);
        timeText.setVisible(true);
        ((Pane) scene.getRoot()).getChildren().add(timeText);
    }

    private void displayYouWin(){
        youWinText.setVisible(true);
    }
    private void displayGameOver() {
        gameOverText.setVisible(true);
    }
}
