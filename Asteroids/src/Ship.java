/*
 * @author Nick Foster
 * @date October 2017
 * 
 * Represents asteroid object in "space."
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Ship extends Polygon implements KeyListener {
	
	// fields
	private static final int SHIP_WIDTH = 40;
	private static final int SHIP_HEIGHT = 25;
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	static boolean isAlive;
	
	// constructors
    private static final Point[] SHIP_SHAPE = {
    		new Point(0, 0),
            new Point(SHIP_WIDTH * 0.3, SHIP_HEIGHT * 0.5),
            new Point(0, SHIP_HEIGHT),
            new Point(SHIP_WIDTH, SHIP_HEIGHT * 0.5)
    };
	
	public static boolean forward;
	private boolean left;
	private boolean right;
//	private boolean pressFire;
	
	public Ship() {
		this(SHIP_SHAPE, new Point(400, 300), 0.0);
	}
	
	public Ship(Point[] shape, Point position, double rotation) {
		super(shape, position, rotation);
		forward = false;
		left = false;
		right = false;
		
		isAlive = true;
	}
	
	// methods
	public ArrayList<Bullet> getBullets() {
		return bullets;
	}
	
	@Override
	public void paint(Graphics brush, Color color) {
		Point[] pts = getPoints();
		int[] xpts = new int[pts.length];
		int[] ypts = new int[pts.length];
		int npts = pts.length;

		for (int i = 0; i < npts; i++) {
			xpts[i] = (int)pts[i].x;
			ypts[i] = (int)pts[i].y;
		}

		brush.setColor(color);
		brush.drawPolygon(xpts, ypts, npts);
		brush.fillPolygon(xpts, ypts, npts);
	}

	@Override
	public void move() {
		if (forward) {
			this.position.x += 3 * Math.cos(Math.toRadians(rotation));
			this.position.y += 3 * Math.sin(Math.toRadians(rotation));
		}
		
		if (left) rotate(-2);
		if (right) rotate(2);
		
	    // ship wrap horizontal
	    if (this.position.x < -5) {this.position.x = Asteroids.SCREEN_WIDTH;}
	    if (this.position.x > (Asteroids.SCREEN_WIDTH + 5)) {this.position.x = -5;}
	    
	    // ship wrap vertical
	    if (this.position.y < -5) {this.position.y = Asteroids.SCREEN_HEIGHT;}
	    if (this.position.y > (Asteroids.SCREEN_HEIGHT + 5)) {this.position.y = -5;}
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (isAlive) {
			if (e.getKeyCode() == KeyEvent.VK_UP) forward = true;
			if (e.getKeyCode() == KeyEvent.VK_LEFT) left = true;
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) right = true;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		if (isAlive) {
			if (e.getKeyCode() == KeyEvent.VK_UP) forward = false;
			if (e.getKeyCode() == KeyEvent.VK_LEFT) left = false;
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) right = false;
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				--Asteroids.score;
				bullets.add(new Bullet(getPoints()[1], this.rotation));
			}
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

}
