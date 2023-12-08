package invaders.entities;

import invaders.engine.GameEngine;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * The `SpaceBackground` class represents the background of the game space in which other game elements are placed.
 * It creates a black rectangular background to fill the game window (pane).
 */
public class SpaceBackground implements Renderable {
	private Rectangle space;

	public SpaceBackground(Pane pane){
		double width = pane.getWidth();
		double height = pane.getHeight();
		space = new Rectangle(0, 0, width, height);
		space.setFill(Paint.valueOf("BLACK"));
		space.setViewOrder(1000.0);

		pane.getChildren().add(space);
	}

	/**
	 * copy constructor, called when cloning
	 * @param spaceBackground, instance from renderables list
	 */
	public SpaceBackground(SpaceBackground spaceBackground){
		space = new Rectangle(0, 0, spaceBackground.getWidth(), spaceBackground.getHeight());
		space.setFill(Paint.valueOf("BLACK"));
		space.setViewOrder(1000.0);
	}

	public Image getImage() {
		return null;
	}

	@Override
	public double getWidth() {
		return 0;
	}

	@Override
	public double getHeight() {
		return 0;
	}

	@Override
	public Vector2D getPosition() {
		return null;
	}

	@Override
	public Layer getLayer() {
		return Layer.BACKGROUND;
	}

	@Override
	public boolean isAlive() {
		return true;
	}

	@Override
	public void takeDamage(double amount) {}

	@Override
	public double getHealth() {
		return 0;
	}

	@Override
	public String getRenderableObjectName() {
		return "background";
	}

	@Override
	public SpaceBackground Clone() {
		return new SpaceBackground(this);
	}

	@Override
	public void update(GameEngine model) {
		// spacebackground does not get updated
	}
}
