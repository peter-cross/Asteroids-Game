/**
 *  Program Name : Asteroids Game
 */

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

/**
 * Class 
 * @author Peter Cross
 * @version March 9, 2017
 */
public class ShipImpl implements Ship 
{
	private final static Color 	FILL = Color.GREEN,		// Ship fill color
								BORDER = Color.BLACK;	// Ship border color

	private final static int 	HIGHEST_I = 0, // the array position of the top
								LOWEST_I = 1,  // the array position of the bottom
								HEIGHT = 20,	// Ship's height
								WIDTH = HEIGHT;	// Ship's width
	
	private final Polygon shape;	// Ship object
	private Direction 	d;			// Ship movement direction
	private Rectangle2D bounds;		// Bounds for ship movement
	
	/**
	 * Class constructor
	 * @param x Initial X coordinate
	 * @param y Initial Y coordinate
	 */
	public ShipImpl( int x, int y ) 
	{
		// Create ship Polygon object
		shape = new Polygon( new int[] {0, 0, WIDTH}, 			// top left, bottom left, front middle
							 new int[] {0, HEIGHT, HEIGHT/2}, 	// top left, bottom left, front middle
							 3 );
		
		// Move ship into specified position
		shape.translate( x, y );
		
		// Set movement direction to NONE
		d = Direction.NONE;
	}
	
	/**
	 * Gets movement direction
	 * @return Movement direction
	 */
	public Direction getDirection()
	{
		return d;
	}

	/**
	 * Draws ship in the window
	 * @param g Graphic context
	 */
	public void draw( Graphics2D g ) 
	{
		// Set color for border
		g.setColor( BORDER );
		
		// Draw ship outline
		g.drawPolygon( shape );
		
		// Set color for ship
		g.setColor( FILL );
		
		// Draw filled polygon for ship
		g.fillPolygon( shape );
	}

	/**
	 * Set ship's movement direction
	 * @param D Movement direction
	 */
	public void setDirection( Direction d ) 
	{
		this.d = d;
	}

	/**
	 * Sets movement bounds
	 * @param movementBounds Rectangle area for movement bounds
	 */
	public void setMovementBounds( Rectangle2D movementBounds ) 
	{
		bounds = movementBounds;
	}

	/**
	 * Moves ship
	 */
	public void move() 
	{
		// If movement direction is specified
		if ( d.dy != 0 ) 
		{
			// Move ship to specified direction by 1 px
			shape.translate( 0, d.dy );
			
			// Check if ship did not move beyond the bounds
			if ( bounds != null && !shape.intersects( bounds ) )
				// Move it back if it went beyond movement bounds
				shape.translate( 0, -d.dy );
		}
	}

	/**
	 * Updates ship position
	 */
	public void update()
	{
		move();
	}
	
	/**
	 * Gets ship shape object
	 */
	public Shape getShape() 
	{
		return shape;
	}

	/**
	 * Checks if ship intersects with specified object
	 * @param other Object to check intersection with
	 * @return True if ship intersects with the object, or false othewise
	 */
	public boolean intersects( Sprite other ) 
	{
		// If object is asteroid
		if( other instanceof Asteroid ) 
		{
			// Create Area object for asteroid
			Area area = new Area( other.getShape() );
			
			// If asteroid intersects with the ship roughly
			if ( area.intersects( shape.getBounds2D() ) )
			{
				// Get intersection precise area of asteroid with ship
				area.intersect( new Area(shape) );
				
				// Return True if it's not empty, or false otherwise
				return !area.isEmpty();
			}
			// If asteroid does not intersect with ship even roughly
			else
				return false;
		} 
		// If it's not asteroid object
		else 
		{
			// Throw exception
			throw new UnsupportedOperationException();
		}
	}
}