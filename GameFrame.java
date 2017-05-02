/**
 *  Program Name : Asteroids Game
 */

import javax.swing.JFrame;

/**
 * Class GameFrame - Window Frame
 * @author Peter Cross
 * @version March 9, 2017
 */
public class GameFrame extends JFrame 
{
	public final static int WIDTH = 800,	// Window width
							HEIGHT = 600;	// Window height
	
	static private GameComponent comp;	// To store Game Component reference
	
	/**
	 * Class constructor
	 */
	GameFrame()
	{
		// Set window width and height
		setSize( WIDTH, HEIGHT );
		
		// Set window title
		setTitle( "Asteroids Game" );
		
		// Make exit on close a default window close operation
		setDefaultCloseOperation( EXIT_ON_CLOSE );	
		
		// Make it non-resizeable
		setResizable( false );
		
		// Position window in the middle of the screen
		setLocationRelativeTo( null );
		
		// Create GameComponent object
		comp = new GameComponent();
	    
		// Add game Component to window frame
		add( comp );
		
		// Start the game Component
		comp.start();
	}
	
	/**
	 * Makes window visible
	 */
	private void setVisible()
	{
		setVisible( true );
	}

	/**
	 * Starts the program
	 * @param args Command line arguments
	 * @throws InterruptedException
	 */
	public static void main( String[] args ) throws InterruptedException 
	{
		// Create window frame and make it visible
		new GameFrame().setVisible();
	}
}