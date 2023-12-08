package invaders.entities;

import javafx.scene.Node;
import invaders.rendering.Renderable;

/**
 * The `EntityView` interface represents the view of an entity in a game
 */
public interface EntityView {
    void update(double xViewportOffset, double yViewportOffset);

    boolean matchesEntity(Renderable entity);

    void markForDelete();

    Node getNode();

    boolean isMarkedForDelete();
}
