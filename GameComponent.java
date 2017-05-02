/**
 *  Program Name : Asteroids Game
 */

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 * Class GameComponent - game component of the program
 * @author Peter Cross
 * @version March 9, 2017
 */
public class GameComponent extends JComponent 
{
	private ShipImpl 		ship;		// Ship object
	private Set<Asteroid> 	asteroids;	// List of asteroids
	
	private Timer 	shipTimer, 			// Ship timer
					asteroidsTimer;		// Asteroids timer
	
	/**
	 * Class constructor
	 */
	public GameComponent()
	{
		// Delay in millisec 
		final int SHIP_DELAY = 8, 			// (1/120 sec)
				  ASTEROIDS_DELAY = 250;	// 250 msec
		
		// Create ship and asteroids timers with specified delay and listener object
		shipTimer = new Timer( SHIP_DELAY, new ShipTimer() );
		asteroidsTimer = new Timer( ASTEROIDS_DELAY, new AsteroidsTimer() );
		
		// Make component focusable
		setFocusable( true );
		
		// Add key listener to the component
		addKeyListener( new ShipKeyListener() );
		
		// Create Set object for asteroids
		asteroids = new HashSet<Asteroid>();
	}

	/**
	 * Displays the graphics
	 */
	@Override
	public void paint( Graphics g )
    {
		paint( (Graphics2D)g );
	}
	
	/**
	 * Displays the graphics
	 */
	private void paint( Graphics2D g )
	{
		// Clear component rectangle area
		g.clearRect( 0, 0, getWidth(), getHeight() );
		
		// If ship object is not created yet
		if ( ship == null )
			// Create ship object
			createShip();
		
		// Draw ship
		ship.draw(g);
		
		// If asteroids are not created yet 
		if ( asteroids.size() == 0 )
			// Create first asteroid
			makeAsteroid();
		
		// For each asteroid object
		for ( Asteroid a : asteroids )
			// Draw asteroid 
			a.draw(g);
	}
	
	/**
	 * Creates ship object
	 */
	private void createShip()
	{
		final int SHIP_HEIGHT = 21,	// Ship height
				  X_OFFSET = 10;	// Offset from the right border
		
		// Create object for generating random numbers
		Random rnd = new Random();
		
		// Get component's width
		int w = getWidth();
		
		// Get component's height minus ship height
		int h = getHeight() - SHIP_HEIGHT;
		
		// Create ship object and place it in random Y position within component's limits
		ship = new ShipImpl( X_OFFSET, rnd.nextInt(h) );
		
		// Create object for specifying movement bounds
		Rectangle2D bounds = new Rectangle2D.Double( 0, SHIP_HEIGHT, w, h - SHIP_HEIGHT );
		
		// Set ship movement bounds
		ship.setMovementBounds( bounds );
	}
	
	public void start()
	{
		// Launch the timers
		shipTimer.start();
		asteroidsTimer.start();
	}
	
	private boolean checkCollisions()
	{
		// Loop for each asteroid
		for ( Asteroid a : asteroids )
			// If asteroid intersects with the ship
			if ( a.intersects( ship ) )
				return true;
		
		return false;
	}
	
	/**
	 * Displays Game Over message and closes the program
	 */
	private void gameOver()
	{
		// Stop timers
		shipTimer.stop();
		asteroidsTimer.stop();
		
		// Remove key listener
		removeKeyListener( getKeyListeners()[0] );
		
		// Display Game Over message
		JOptionPane.showMessageDialog( null, "           Game Over", "", JOptionPane.WARNING_MESSAGE );
		
		// Exit the program with normal return code
		System.exit( 0 );
	}
	
	/**
	 * Updates the component content
	 */
	private void update()
	{
		// Move ship and asteroids
		ship.move();
		moveAsteroids();
		
		// If there is a collision between ship and an asteroid
		if ( checkCollisions() )
			// Finish the program
			gameOver();
		
		// Repaint the content
		repaint();
	}
	
	/**
	 * Makes asteroids
	 */
	private void makeAsteroid()
	{
		final int MIN_SIZE = 10,	// Min asteroid size
				  MAX_SIZE = 40;	// Max asteroid size
		final int MIN_VELOCITY = 1,	// Min asteroid velocity
				  MAX_VELOCITY = 4;	// Max asteroid velocity
		
		int x = getWidth() - MAX_SIZE;	// Get X coordinate to initially display asteroid
		
		// Create object to generate random numbers
		Random rnd = new Random();	
		
		// Get height limit
		int h = getHeight() - MAX_SIZE;
		
		// Get random value for width within specified limits
		int width = rnd.nextInt( MAX_SIZE - MIN_SIZE ) + MIN_SIZE;
		
		// Get random number for height within specified limits
		int height = rnd.nextInt( MAX_SIZE - MIN_SIZE ) + MIN_SIZE;
		
		// Get random velocity number within specified limits
		int velocity = rnd.nextInt( MAX_VELOCITY - MIN_VELOCITY ) + MIN_VELOCITY;
		
		// Create asteroid object with specified parameters
		Asteroid asteroid = new AsteroidImpl( x, rnd.nextInt(h), width, height, velocity );
		
		// Add asteroid object to component
		asteroids.add( asteroid );
	}
	
	/**
	 * Moves asteroids
	 */
	private void moveAsteroids()
	{
		// Loop for each asteroid from copy of asteroids list
		for ( Asteroid a : new HashSet<Asteroid>(asteroids) )
			// If asteroid is visible
			if ( a.isVisible() )
				// Move it
				a.move();
			// Otherwise
			else
				// Remove it from the original list
				asteroids.remove( a );
	}
	
	/**
	 * Class AsteroidsTimer - implements action listener for asteroids
	 * @author Peter Cross
	 * @version March 9, 2017
	 */
	private class AsteroidsTimer implements ActionListener
	{
		/**
		 * Method invoked by timer in specified intervals of time
		 * @param e Event which invoked the action listener
		 */
		@Override
		public void actionPerformed( ActionEvent e ) 
		{
			makeAsteroid();
		}
	}
	
	//****************************************************************************
	/**
	 * Class TimerListener - action listener for timer
	 * @author Peter Cross
	 * @version March 9, 2017
	 */
	private class ShipTimer implements ActionListener
	{
		/**
		 * Method invoked by timer in specified intervals of time
		 * @param e Event which invoked the action listener
		 */
		@Override
		public void actionPerformed( ActionEvent e ) 
		{
			// If ship is created and there is specified direction to move to
			if  ( ship != null && ((ShipImpl)ship).getDirection() != Ship.Direction.NONE )
				// Update the ship location
				update();
		}	
	}
	
	/**
	 * Class ShipKeyListener - Key listener for ship object
	 * @author Peter Cross
	 * @version March 9, 2017
	 */
	private class ShipKeyListener extends KeyAdapter
	{
		/**
		 * Method gets invoked when any key is pressed
		 * @param e Event which invoked the key listener
		 */
		@Override
		public void keyPressed( KeyEvent e )
		{
			// Get Key code for the pressed key and switch
			switch ( e.getKeyCode() )
			{
			// For keys to move Up
			case KeyEvent.VK_UP :		// Up key 
			case KeyEvent.VK_NUMPAD8 :	// 8 key on number pad
			case KeyEvent.VK_W :		// w key on character pad
				// Set ship's direction to move up
				ship.setDirection( Ship.Direction.UP );
				break;
				
			// For keys to move Down
			case KeyEvent.VK_DOWN :		// Down key
			case KeyEvent.VK_NUMPAD2 :	// 2 key on number pad
			case KeyEvent.VK_S :		// s key on character pad
				// Set ship's direction to move down
				ship.setDirection( Ship.Direction.DOWN );
				break;
				
			// For any other key
			default :
				ship.setDirection( Ship.Direction.NONE );
			}
		}
		
		/**
		 * Method gets invoked when any key is released
		 * @param e Event which invoked the key listener
		 */
		@Override
		public void keyReleased( KeyEvent e )
		{
			ship.setDirection( Ship.Direction.NONE );
		}
	}
}