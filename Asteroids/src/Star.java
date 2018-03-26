/*
 * @author Nick Foster
 * @date October 2017
 * 
 * Represents stars of varying depths.
 */

import java.awt.Color;
import java.awt.Graphics;

public class Star extends Circle {
	// fields
	private enum Distance {NEAR, MEDIUM, CLOSE, FAR}
	public double rotation;
	
	private Distance distance;
	private Point origin;
	private boolean twinkle;
	
	// constructors
	public Star() {
		this(randomPosition(), (int) 3);
	}
		
	public Star(Point center, int radius) {
		super(center, radius);
		this.origin = center;
		rotation = Math.random() * 360;
		
		double n = Math.random();
		if (n < 0.6) distance = Distance.FAR;
		else if (n < 0.85) distance = Distance.MEDIUM;
		else distance = Distance.NEAR;
		
		twinkle = (Math.random() < 0.5) ? true : false;
	}

	// methods
	private void twinkle() {
		if (twinkle) {
			if (Math.random() < 0.5) twinkle = false;
		} else {
			if (Math.random() < 0.5) twinkle = true;
		}
	}
	
	@Override
	public void paint(Graphics brush, Color color) {
		this.twinkle();
		
		if (distance == Distance.FAR) {
			//if (this.twinkle)
			brush.setColor(Color.DARK_GRAY);
			//else brush.setColor(Color.BLACK);
		} else if (distance == Distance.MEDIUM) {
			//if (this.twinkle)
			brush.setColor(Color.GRAY);
			//else brush.setColor(Color.BLACK);
		} else {
			if (this.twinkle) brush.setColor(Color.WHITE);
			else brush.setColor(Color.BLUE);
		}
		
		brush.fillOval((int) (origin.x), (int) (origin.y), radius, radius);		
	}

	@Override
	public void move() {
		// TODO
	}
	
	private static Point randomPosition() {
		double x = Math.random() * Asteroids.SCREEN_WIDTH;
		double y = Math.random() * Asteroids.SCREEN_WIDTH;
		
		return new Point(x,y);
	}
}
