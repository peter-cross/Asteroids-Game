/**
 *  Program Name : Asteroids Game
 */

import java.awt.geom.Rectangle2D;

/**
 * Interface Ship
 * @author Peter Cross
 * @version March 9, 2017
 */
public interface Ship extends Sprite 
{
	/**
	 * Enumeration Direction 
	 * @author Peter Cross
	 * @version March 9, 2017
	 */
	public enum Direction 
	{
		NONE(0), UP(-1), DOWN(1);
		
		public final int dy; // Direction integer value
		
		/**
		 * Enumeration constructor
		 * @param aDy Integer value for direction
		 */
		Direction( int aDy ) 
		{ 
			dy = aDy; 
		}
	}

	/**
	 * Sets ship direction
	 * @param d Direction
	 */
	public void setDirection( Direction d );
	
	/**
	 * Sets ship movement bounds
	 * @param bounds Bounds rectangle area
	 */
	public void setMovementBounds( Rectangle2D bounds );
}