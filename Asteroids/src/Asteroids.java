/*
 * @author Nick Foster
 * @date October 2017
 * 
 * Modified from original code provided for assignment.
 */

/*
CLASS: AsteroidsGame
DESCRIPTION: Extending Game, Asteroids is all in the paint method.
NOTE: This class is the metaphorical "main method" of your program,
      it is your control center.
Original code by Dan Leyzberg and Art Simon
 */
import java.awt.*;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Asteroids extends Game {
	// fields
	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 600;
	private static final int STARTING_SHIELDS = 3;
	static int counter = 2000;
	private int damageTimer = 0;
	
	static int level = 0;
	ArrayList<Asteroid> roids;
	static int score = 0;
	
	Star[] stars;
	Ship ship;
	int shields;
	
	// constructors
	public Asteroids() {
		super("Asteroids!", SCREEN_WIDTH, SCREEN_HEIGHT);
		this.setFocusable(true);
		this.requestFocus();
		
		stars = new Star[300];
		for (int i = 0; i < stars.length; ++i) {
			stars[i] = new Star();
		}
		
		roids = Asteroid.nextLevel(++level);
		ship = new Ship();
		shields = STARTING_SHIELDS;
		this.addKeyListener(ship);
	}
	
	// methods
	public void paint(Graphics brush) {
		brush.setColor(Color.black);
		brush.fillRect(0,0,width,height);
		
		// sample code for printing message for debugging
		// counter is incremented and this message printed
		// each time the canvas is repainted
		brush.setColor(Color.white);
		brush.drawString("Score: " + score,50,10);
		brush.drawString("Shields: " + shields,200,10);
		brush.drawString("Current wave: " + level,350,10);
		brush.drawString("Asteroids: " + roids.size(),500,10);
		brush.drawString("Next wave: " + counter,650,10);
		
		// paints each of the stars
		for (Star el : stars) {
			el.paint(brush, Color.GRAY);
			el.move();
		}
		
		// paints each of the asteroids
		if (Ship.isAlive) {
			--counter;
			score -= (roids.size() < level) ? 1 : 0;
			score += (roids.size() > 10) ? (int) (roids.size() * 0.1) : 0;
			
			ArrayList<Bullet> removeList = new ArrayList<Bullet>();
			ArrayList<Asteroid> deadRoids = new ArrayList<Asteroid>();
			
			for (Asteroid el : roids) {
				for (Bullet em : ship.getBullets()) {
					if (em.collided(el)) {
						removeList.add(em);
						deadRoids.add(el);
					}
				}
				if (deadRoids.contains(el) != true) {
					el.move();
					el.paint(brush, Color.WHITE);
					if (el.collision(ship) && damageTimer < 1) {
						if (shields > 1) {
							--shields;
							damageTimer = 100;
						} else {
							Ship.isAlive = false;
						}
						
					}
				}
			}
			
			if ((ship.getBullets()).size() > 0) {
				// iterate through bullets; move & paint
				for (Bullet el : ship.getBullets()) {
					if (el.isOutOfBounds()) {
						removeList.add(el);
					} else {
						el.move();
						el.paint(brush, Color.RED);
					}
				}
			}
			
			// remove dead asteroids and bullets
			for (Asteroid em : deadRoids) {
				if (em.spawn() != null) roids.addAll(em.spawn());
				score += em.getGeneration() * 5;
				roids.remove(em);
			}
			for (Bullet el : removeList) {(ship.getBullets()).remove(el);}
			
			
			if (damageTimer < 1) {
				ship.paint(brush, Color.RED);
			} else if (damageTimer % 2 == 0){
				ship.paint(brush, Color.WHITE);
				--damageTimer;
			} else if (damageTimer % 2 == 1){
				ship.paint(brush, Color.RED);
				--damageTimer;
			}
			
			if (Ship.isAlive) ship.move();
			
			if (roids.isEmpty() || counter < 1) {
				roids = Asteroid.nextLevel(++level);
				score += 100 * shields;
				score += counter * level;
				
				// reset stage counter
				counter = (level <= 5) ? 1200 * level : 6000;
			}
		} else {
			shields = 0;
			counter = 0;
			brush.setColor(Color.RED);
			brush.drawString("GAME OVER",380,280);
		}
	}
	
	// main method
	public static void main (String[] args) {
		Asteroids a = new Asteroids();
		a.repaint();
	}
}