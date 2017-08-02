/**
 * Abstract class for the Scrolling Game project.
 * This class must be extended, with all abstract methods
 * at least given bare shells, so that game will compile and run.
 *
 * @author Dave Feinberg
 * @author M. Allen
 */
public abstract class AbstractGame
{
    // Variables for the game: these are inherited by any descendant class,
    // and can be accessed by such a class (because they are protected).
    
    // Handles most of the graphics routines.
    protected Grid grid;
    // Tracks user's row (user's column always == 1).
    protected int userRow;
    
    // Can be used to keep track of how many times the user has hit one of the
    // objects they want to get, (increasing points in the game).
    protected int timesGet;
    
    // Can be used to keep track of how many times the user has hit one of the
    // objects they want to avoid (decreasing points in the game).
    protected int timesAvoid;
    
    protected String userImg, avoidImg, getImg;
    
    // Used to control speed of game. Setting waitTime to lower values speeds it
    // up; higher values slows it down.
    protected int waitTime;
    protected int msElapsed;
    
    // Used to check if user-icon is to be moved reset to 0 after each step of
    // play().
    protected int direction;
    
    // The following are pre-defined constants for directions.
    public static final int UP = -1;
    public static final int DOWN = 1;
    
    /**
     * Basic game constructor: sets up grid of given size, initializes
     * elementary game parameters (these can be changed in the descendant class
     * if user wants to make the game look/act differently).
     *
     * @param rows Number of rows in the game-screen.
     * @param cols Number of columns in the game-screen.
     */
    public AbstractGame( int rows, int cols )
    {
        grid = new Grid( rows, cols );
        userRow = 0;
        msElapsed = 0;
        timesGet = 0;
        timesAvoid = 0;
        updateTitle();
        waitTime = 400;
        userImg = "ship.gif";
        avoidImg = "asteroid.gif";
        getImg = "burger.gif";
        grid.setImage( userRow, 1, userImg );
    }
    
    /**
     * Method that is called by the system to make the game-play happen.
     */
    public void play()
    {
        while ( !isGameOver() )
        {
            Grid.pause( 100 );
            grid.setImage( userRow, 1, null );
            move();
            direction = 0;
            grid.setImage( userRow, 1, userImg );
            if ( msElapsed % waitTime == 0 )
            {
                scrollLeft();
                populateRightEdge();
            }
            updateTitle();
            msElapsed += 100;
        }
    }
    
    // displays game score in title bar of window
    public void updateTitle()
    {
        grid.setTitle( "Game Score:  " + getScore() );
    }
    
    // Moves the user-icon up and down
    // (must be over-ridden in descendant).
    public abstract void move();
    
    // Adds new objects to right side of screen at random
    // (must be over-ridden in descendant).
    public abstract void populateRightEdge();
    
    // Moves all objects (except user) left one cell
    // (must be over-ridden in descendant).
    public abstract void scrollLeft();
    
    // Given the row and column, checks if there is any object in that location,
    // and updates timesGet or timesAvoid counter depending upon what that
    // object is; should be called whenever the user enters a square in grid
    // already containing some object, or when such an object moves left into
    // the user's current location (must be over-ridden in descendant).
    public abstract void handleCollision( int row, int col );
    
    // Calculates and returns score based on game parameters
    // (must be over-ridden in descendant).
    public abstract int getScore();
    
    // Returns true when game is completed (returns false otherwise)
    // (must be over-ridden in descendant).
    public abstract boolean isGameOver();
}