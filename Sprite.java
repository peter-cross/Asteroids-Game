/**
 *  Program Name : Asteroids Game
 */

import java.awt.Graphics2D;
import java.awt.Shape;

/**
 * Interface Sprite - basic methods for asteroids and ship
 * @author Peter Cross
 * @version March 9, 2017
 */
public interface Sprite 
{
	// Draws in the window
	public void draw( Graphics2D g );
	
	// Moves objects in the window
	public void move();
	
	// Gets reference on Shape object
	public Shape getShape(); // used by intersects
	
	// Verifies if one shape intersects with another
	public boolean intersects( Sprite other );
}