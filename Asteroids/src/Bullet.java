/*
 * @author Nick Foster
 * @date November 2017
 * 
 * Represents bullet fired by ship.
 */

import java.awt.Color;
import java.awt.Graphics;

public class Bullet extends Circle {
	private double rotation;
	private boolean outOfBounds;
	
	public Bullet(Point center, double rotation) {
		this(center, 5, rotation);
	}
	
	public Bullet(Point center, int radius, double rotation) {
		super(center, radius);
		this.rotation = rotation;
		outOfBounds = false;
	}
	
	// methods
	public boolean isOutOfBounds() {
		return outOfBounds;
	}
	
	public boolean collided(Asteroid roid) {
		return roid.contains(this.center);  
	}
	
	@Override
	public void paint(Graphics brush, Color color) {
		brush.setColor(color);
		brush.fillOval((int) (center.x), (int) (center.y), radius, radius);
	}
	
	@Override
	public void move() {
		this.center.x += 8 * Math.cos(Math.toRadians(rotation));
		this.center.y += 8 * Math.sin(Math.toRadians(rotation));
		
	    // out of bounds horizontal
	    if (this.center.x < 0) this.outOfBounds = true;
	    if (this.center.x > Asteroids.SCREEN_WIDTH) this.outOfBounds = true;
	    
	    // out of bounds vertical
	    if (this.center.y < 0) this.outOfBounds = true;
	    if (this.center.y > Asteroids.SCREEN_HEIGHT) this.outOfBounds = true;

	}
	
}
