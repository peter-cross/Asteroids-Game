/**
 *  Program Name : Asteroids Game
 */

/**
 * Interface Asteroid - for creating asteroids
 * @author Peter Cross
 * @version March 9, 2017
 */
public interface Asteroid extends Sprite 
{
	/**
	 * Checks if asteroid is visible
	 * @return True id visible, or false otherwise
	 */
	public boolean isVisible();
}
