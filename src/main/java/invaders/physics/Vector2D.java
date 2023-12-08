package invaders.physics;

/**
 * A utility class for storing position information
 */
public class Vector2D {

	private double x;
	private double y;

	public Vector2D(double x, double y){
		this.x = x;
		this.y = y;
	}

	public Vector2D(Vector2D vector2D){
		this.x = vector2D.getX();
		this.y = vector2D.getY();
	}
	public double getX(){
		return this.x;
	}

	public double getY(){
		return this.y;
	}

	public void setX(double x){
		this.x = x;
	}

	public void setY(double y){
		this.y = y;
	}

	public Vector2D Clone(){
		return new Vector2D(this);
	}
}
