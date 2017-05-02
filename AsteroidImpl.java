/**
 *  Program Name : Asteroids Game
 */

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

/**
 * Class 
 * @author Peter Cross
 * @version March 9, 2017
 */
public class AsteroidImpl implements Asteroid 
{
	private final static int 	WIDTH_DEFAULT = 10;			// Default asteroid width
	private final static Color 	COLOR = Color.DARK_GRAY;	// Default asteroid color

	private final Ellipse2D.Double shape;	// Asteroid shape object
	private final int velocity;	// Asteroids velocity
	private final int width;	// Asteroids width

	/**
	 * Class constructor
	 * @param x Initial X coordinate
	 * @param y Initial Y coordinate
	 * @param width Asteroid's width
	 * @param height Asteroid's height
	 * @param velocity Asteroid's velocity
	 */
	public AsteroidImpl( int x, int y, int width, int height, int velocity ) 
	{
		// Create ellipse shape for asteroid
		shape = new Ellipse2D.Double( x, y, width, height );
		
		// Save velocity and width to instance variables
		this.velocity = velocity;
		this.width = width;
	}
	
	/**
	 * Default Class constructor
	 * @param x Initial X coordinate
	 * @param y Initial Y coordinate
	 */
	public AsteroidImpl( int x, int y ) 
	{
		// Invoke another constructor with default values
		this( x, y, WIDTH_DEFAULT, WIDTH_DEFAULT, 1 );
	}

	/**
	 * Moves asteroid
	 */
	public void move() 
	{
		double 	x = shape.getX() - velocity,	// X coordinate to move to
				y = shape.getY();				// Y coordinate to move to
		
		// Set new position for shape object
		shape.setFrame( x, y, shape.getWidth(), shape.getHeight() );
	}

	/**
	 * Checks if asteroid is visible
	 */
	public boolean isVisible() 
	{
		final int X_OFFSET = 5;
		
		// If the shape beyond the specified offset
		if ( shape.getX() < X_OFFSET )
			return false;
		else
			return true;
	}

	/**
	 * Draws asteroid in the window
	 */
	public void draw( Graphics2D g ) 
	{
		// Set color to draw
		g.setColor( COLOR );
		
		// Draw filled shape
		g.fill( shape );
	}

	/**
	 * Gets reference to asteroid shape
	 * @return rReference to Shape object
	 */
	public Shape getShape() 
	{
		return shape;
	}

	/**
	 * Checks if asteroid intersects with another object
	 * @param other Other object
	 * @return True if intersects, or false otherwise
	 */
	public boolean intersects( Sprite other ) 
	{
		// If object is a Ship
		if( other instanceof Ship ) 
		{
			// Create area object for the ship
			Area area = new Area( other.getShape() );
			
			// If ship roughly intersects with the asteroid
			if ( area.intersects( shape.getBounds2D() ) )
			{
				// Get precise intersection area between ship and asteroid
				area.intersect( new Area(shape) );
				
				// Return true if intersection area is not empty, or false otherwise
				return !area.isEmpty();
			}
			else
				return false;
		} 
		// If it's not a Ship
		else 
		{
			// Throw exception
			throw new UnsupportedOperationException();
		}
	}
}