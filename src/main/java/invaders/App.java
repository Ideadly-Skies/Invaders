package invaders;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import invaders.engine.GameEngine;
import invaders.engine.GameWindow;

/**
 * The `App` class represents the main application for the Space Invaders game.
 * It initializes the game engine, sets up the game window, and provides user
 * interaction through buttons for changing difficulty levels, performing undo actions,
 * and enabling cheat modes to remove aliens and projectiles.
 */
public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GameEngine model = new GameEngine("src/main/resources/config_easy.json");

        // Create a difficulty button
        Button easyButton = new Button("Easy Difficulty");
        Button mediumButton = new Button("Medium Difficulty");
        Button hardButton = new Button("Hard Difficulty");

        // Create an Undo button
        Button undoButton = new Button("Undo");

        // Create button for cheat actions
        Button removeFastAlienButton = new Button("Remove Fast Alien");
        Button removeSlowAlienButton = new Button("Remove Slow Alien");
        Button removeFastProjectileButton = new Button("Remove Fast Projectile");
        Button removeSlowProjectileButton = new Button("Remove Slow Projectile");

        // Set the layout for the buttons on the left side of the screen
        double buttonX = 20; // X-coordinate for the buttons
        double buttonSpacing = 40; // Spacing between buttons
        double buttonY = model.getGameHeight() - 300; // Initial Y-coordinate for the first button

        // add button to the game window
        GameWindow window = new GameWindow(model);

        // setup position of difficulty level buttons
        easyButton.setLayoutX(model.getGameWidth()-150);
        easyButton.setLayoutY(model.getGameHeight()-250);

        mediumButton.setLayoutX(model.getGameWidth()-150);
        mediumButton.setLayoutY(model.getGameHeight()-200);

        hardButton.setLayoutX(model.getGameWidth()-150);
        hardButton.setLayoutY(model.getGameHeight()-150);

        undoButton.setLayoutX(model.getGameWidth() - 150);
        undoButton.setLayoutY(model.getGameHeight() - 100);

        // Position the buttons
        removeFastAlienButton.setLayoutX(buttonX);
        removeFastAlienButton.setLayoutY(buttonY);
        buttonY += removeFastAlienButton.getHeight() + buttonSpacing;

        removeSlowAlienButton.setLayoutX(buttonX);
        removeSlowAlienButton.setLayoutY(buttonY);
        buttonY += removeSlowAlienButton.getHeight() + buttonSpacing;

        removeFastProjectileButton.setLayoutX(buttonX);
        removeFastProjectileButton.setLayoutY(buttonY);
        buttonY += removeFastProjectileButton.getHeight() + buttonSpacing;

        removeSlowProjectileButton.setLayoutX(buttonX);
        removeSlowProjectileButton.setLayoutY(buttonY);

        // set the difficulty buttons click action
        easyButton.setOnAction(e -> {
            model.changeDifficultyLevel("easy");
            (window.getScene().getRoot()).requestFocus();
        });

        mediumButton.setOnAction(e -> {
            model.changeDifficultyLevel("medium");
            (window.getScene().getRoot()).requestFocus();
        });

        hardButton.setOnAction(e -> {
            model.changeDifficultyLevel("hard");
            (window.getScene().getRoot()).requestFocus();
        });

        // Set up the "Undo" button click action
        undoButton.setOnAction(e -> {
            // Call the undo method from the GameEngine class
            model.undo();

            (window.getScene().getRoot()).requestFocus();
        });

        // Set up the "Cheat" button click action
        removeFastAlienButton.setOnAction(e -> {
            model.enableCheat(true, true); // Remove fast aliens
            (window.getScene().getRoot()).requestFocus();
        });

        removeSlowAlienButton.setOnAction(e -> {
            model.enableCheat(true, false); // Remove slow aliens
            (window.getScene().getRoot()).requestFocus();
        });

        removeFastProjectileButton.setOnAction(e -> {
            model.enableCheat(false, true); // Remove fast projectiles
            (window.getScene().getRoot()).requestFocus();
        });

        removeSlowProjectileButton.setOnAction(e -> {
            model.enableCheat(false, false); // Remove slow projectiles
            (window.getScene().getRoot()).requestFocus();
        });

        addButtonAndRequestFocus((Pane) window.getScene().getRoot(), easyButton);
        addButtonAndRequestFocus((Pane) window.getScene().getRoot(), mediumButton);
        addButtonAndRequestFocus((Pane) window.getScene().getRoot(), hardButton);
        addButtonAndRequestFocus((Pane) window.getScene().getRoot(), undoButton);
        addButtonAndRequestFocus((Pane) window.getScene().getRoot(), removeFastAlienButton);
        addButtonAndRequestFocus((Pane) window.getScene().getRoot(), removeSlowAlienButton);
        addButtonAndRequestFocus((Pane) window.getScene().getRoot(), removeFastProjectileButton);
        addButtonAndRequestFocus((Pane) window.getScene().getRoot(), removeSlowProjectileButton);

        window.run();

        primaryStage.setTitle("Space Invaders");
        primaryStage.setScene(window.getScene());
        primaryStage.show();
        window.run();
    }

    private void addButtonAndRequestFocus(Pane pane, Button button) {
        pane.getChildren().add(button);
        pane.requestFocus();
    }
}
