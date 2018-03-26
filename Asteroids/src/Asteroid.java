/*
 * @author Nick Foster
 * @date October 2017
 * 
 * Represents asteroid object in "space."
 */

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;

public class Asteroid extends Polygon {
	// Asteroid shape and size ranges
	private static final int MIN_SIDES =  8;
	private static final int MAX_SIDES = 12;
	private static final int MIN_SIZE  = 40;
	private static final int MAX_SIZE  = 80;
	
	// fields
	private Point[] shape;
	private int generation;
	
	// constructors
	public Asteroid() {
		this(randomShape(1), randomPosition(), Math.random() * 360, 1);
	}
	
	public Asteroid(Point[] shape, Point position, double rotation, int generation) {
		super(shape, position, rotation);
		this.shape = shape;
		this.generation = generation;
	}
	
	// methods
	public static ArrayList<Asteroid> nextLevel(int level) {
		ArrayList<Asteroid> nextLevel = new ArrayList<Asteroid>();
		for (int i = 0; i < level + 2; ++i) {
			nextLevel.add(new Asteroid());
		}
		
		return nextLevel;
	}
	
	public int getGeneration() {
		return generation;
	}
	
	public Collection<Asteroid> spawn() {
		if (this.generation < 3) {
			Collection<Asteroid> spawn = new ArrayList<Asteroid>();
			
			double spin;
			for (int i = 0; i < 3; ++i) {
				spin = i * 120;
				spawn.add(new Asteroid(randomShape(generation + 1), Point.distort(this.position), spin, this.generation + 1));
			}
			
			return spawn;
		}
		
		return null;
	}
	
	@Override
	public void paint(Graphics brush, Color color) {
		Point[] points = this.getPoints();
		Point currentPoint = null;
		int[] exes = new int[this.shape.length];
		int[] whys = new int[this.shape.length];
		
		for (int i = 0; i < this.shape.length; ++i) {
			currentPoint = points[i];
			exes[i] = (int)currentPoint.x;
			whys[i] = (int)currentPoint.y;
		}
		
		brush.setColor(color);
		brush.drawPolygon(exes, whys, this.shape.length);
	}

	@Override
	public void move() {
		//rotate(spin);
		
		position.x += 1.2 * Math.cos(Math.toRadians(rotation));
		position.y += 1.2 * Math.sin(Math.toRadians(rotation));
		
	    // wrap horizontal
		if (position.x < 0) position.x = Asteroids.SCREEN_WIDTH;
		if (position.x > Asteroids.SCREEN_WIDTH) position.x = 0;
//	    if (position.x < -(Asteroids.SCREEN_WIDTH * 0.1)) {position.x = (Asteroids.SCREEN_WIDTH * 1.1);}
//	    if (position.x > (Asteroids.SCREEN_WIDTH * 1.1)) {position.x = -(Asteroids.SCREEN_WIDTH * 0.1);}
	    
	    // wrap vertical
		if (position.y < 0) position.y = Asteroids.SCREEN_HEIGHT;
		if (position.y > Asteroids.SCREEN_HEIGHT) position.y = 0;
//	    if (position.y < -(Asteroids.SCREEN_HEIGHT * 0.1)) {position.y = (Asteroids.SCREEN_HEIGHT * 1.1);}
//	    if (position.y > (Asteroids.SCREEN_HEIGHT * 1.1)) {position.y = -(Asteroids.SCREEN_HEIGHT * 0.1);}
	}
	
	/*
	 * PRIVATE METHODS
	 */
	private static Point[] randomShape(int generation) {
		int sides, x, y;
		double size, theta;
		
		// randomly determines a whole number of sides between 8-12 
		sides = MIN_SIDES + (int) (Math.random() * (MAX_SIDES - MIN_SIDES));
		Point[] shape = new Point[sides];
		
		for (int i = 0; i < sides; ++i) {
	        theta = 2 * Math.PI / sides * i;
	        size = MIN_SIZE + (int) (Math.random() * (MAX_SIZE - MIN_SIZE));
	        size *= (generation < 2) ? 1 : Math.pow(0.75, generation);
	        x = (int) -Math.round(size * Math.sin(theta));
	        y = (int)  Math.round(size * Math.cos(theta));
	        shape[i] = new Point(x, y);
		}
		
		return shape;
	}
	
	private static Point randomPosition() {
		int x, y;
		
		// randomly determines x, y somewhere within visible screen
		if (Math.random() < 0.5) {
			if (Math.random() < 0.5) {
				x = (int) (Asteroids.SCREEN_WIDTH * 0.1);
			} else {
				x = (int) (Asteroids.SCREEN_WIDTH * 0.9);
			}
			y = (int) (Math.random() * Asteroids.SCREEN_HEIGHT);
		} else {
			x = (int) (Math.random() * Asteroids.SCREEN_WIDTH);
			if (Math.random() < 0.5) {
				y = (int) (Asteroids.SCREEN_HEIGHT * 0.1);
			} else {
				y = (int) (Asteroids.SCREEN_HEIGHT * 0.9);
			}
		}
		
		return new Point(x, y);
	}
	
//	private static int randomSpin() {
//		double spin = Math.random();
//		
//		if (spin < 0.2) spin = -1.0;
//		else if (spin < 0.4) spin = 1.0;
//		else spin = 0.0;
//		
//		return (int) spin;
//	}
}
