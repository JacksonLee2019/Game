
/**
 * A class for handling the graphical grid
 * for the Scrolling Game project.
 *
 * @author Dave Feinberg
 * @author M. Allen
 */
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.net.*;
import javax.imageio.*;
import javax.swing.*;

@SuppressWarnings( "serial" )
public class Grid extends JComponent
{
    private Cell[][] cells;
    private JFrame frame;
    private Color lineColor;
    
    /**
     * Constructor for grid of game-cells.
     *
     * @param numRows Number of rows in grid.
     * @param numCols Number of columns in grid.
     */
    public Grid( int numRows, int numCols )
    {
        init( numRows, numCols );
    }
    
    /**
     * Alternative constructor: given an image, will use it as background for
     * the grid (pixelation will depend upon size of image and size of grid).
     *
     * @param imageFileName Name of accessible image-file for background.
     */
    public Grid( String imageFileName )
    {
        BufferedImage image = loadImage( imageFileName );
        init( image.getHeight(), image.getWidth() );
        showImage( image );
        setTitle( imageFileName );
    }
    
    /*
     * Helper method to set up Grid; used by both constructors.
     *
     * @param numRows Number of rows in grid.
     *
     * @param numCols Number of columns in grid.
     */
    private void init( int numRows, int numCols )
    {
        lineColor = null;
        
        cells = new Cell[numRows][numCols];
        for ( int row = 0; row < numRows; row++ )
        {
            for ( int col = 0; col < numCols; col++ )
                cells[row][col] = new Cell();
        }
        
        frame = new JFrame( "Grid" );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        
        int cellSize = Math.max( Math.min( 750 / getNumRows(), 750 / getNumCols() ), 1 );
        setPreferredSize( new Dimension( cellSize * numCols, cellSize * numRows ) );
        frame.getContentPane().add( this );
        
        frame.pack();
        frame.setVisible( true );
    }
    
    /**
     * @return Number of rows in grid.
     */
    public int getNumRows()
    {
        return cells.length;
    }
    
    /**
     * @return Number of columns in grid.
     */
    public int getNumCols()
    {
        return cells[0].length;
    }
    
    /**
     * Sets window title.
     *
     * @param title Title for window.
     */
    public void setTitle( String title )
    {
        frame.setTitle( title );
    }
    
    /**
     * Sets background of Cell at a given location.
     *
     * @param row Row of grid-Cell location.
     * @param col Column of grid-Cell location.
     * @param color Color to set Cell at position (row,col).
     */
    public void setColor( int row, int col, Color color )
    {
        if ( !isValid( row, col ) )
            throw new RuntimeException( "Cannot set color of invalid location " + row + ", " + col +
                                       " to color " + color );
        cells[row][col].setColor( color );
        repaint();
    }
    
    /*
     * Utility used to check whether or not locations are in the grid properly.
     */
    private boolean isValid( int row, int col )
    {
        return 0 <= row && row < getNumRows() && 0 <= col && col < getNumCols();
    }
    
    /**
     * Gets background of Cell at a given location.
     *
     * @param row Row of grid-Cell location.
     * @param col Column of grid-Cell location.
     *
     * @return Color of Cell at position (row,col).
     */
    public Color getColor( int row, int col )
    {
        if ( !isValid( row, col ) )
            throw new RuntimeException( "Cannot get color from invalid location " + row + ", " +
                                       col );
        return cells[row][col].getColor();
    }
    
    /**
     * Sets background of all Cells at once.
     *
     * @param color Background color for all Cells on-screen.
     */
    public void setBackground( Color color )
    {
        for ( int row = 0; row < cells.length; row++ )
            for ( int col = 0; col < cells[row].length; col++ )
                cells[row][col].setColor( color );
    }
    
    /**
     * Sets foreground image of Cell at given location.
     *
     * @param row Row of grid-Cell location.
     * @param col Column of grid-Cell location.
     * @param imageFileName Name of accessible image-file to place in Cell at
     *            position (row, col). Cell will be empty if name == null.
     */
    public void setImage( int row, int col, String imageFileName )
    {
        if ( !isValid( row, col ) )
            throw new RuntimeException( "Cannot set image for invalid location " + row + ", " +
                                       col + " to \"" + imageFileName + "\"" );
        cells[row][col].setImageFileName( imageFileName );
        repaint();
    }
    
    /**
     * Returns name of image-file displayed at given location.
     *
     * @param row Row of grid-Cell location.
     * @param col Column of grid-Cell location.
     *
     * @return Name of image-file for image displayed at location (row, col).
     *         Will be null if Cell appears empty.
     */
    public String getImage( int row, int col )
    {
        if ( !isValid( row, col ) )
            throw new RuntimeException( "Cannot get image for invalid location " + row + ", " +
                                       col );
        return cells[row][col].getImageFileName();
    }
    
    /**
     * Pauses game for a period.
     *
     * @param milliseconds Number of milliseconds for pause
     *            (so 1000 == approx. 1 second).
     */
    public static void pause( int milliseconds )
    {
        try
        {
            Thread.sleep( milliseconds );
        }
        catch ( Exception e )
        {
            // Ignore any Thread exceptions.
        }
    }
    
    /**
     * Loads a file as background to the screen (pixelation will depend upon
     * size of image and size of grid).
     * @param imageFileName
     */
    public void load( String imageFileName )
    {
        showImage( loadImage( imageFileName ) );
        setTitle( imageFileName );
    }
    
    /**
     * Saves an image of the game-screen.
     *
     * @param imageFileName Name of the file to save.
     */
    public void save( String imageFileName )
    {
        try
        {
            BufferedImage bi = new BufferedImage( getWidth(), getHeight(),
                                                 BufferedImage.TYPE_INT_RGB );
            paintComponent( bi.getGraphics() );
            int index = imageFileName.lastIndexOf( '.' );
            if ( index == -1 )
                throw new RuntimeException( "invalid image file name:  " + imageFileName );
            ImageIO.write( bi, imageFileName.substring( index + 1 ), new File( imageFileName ) );
        }
        catch ( IOException e )
        {
            throw new RuntimeException( "unable to save image to file:  " + imageFileName );
        }
    }
    
    /**
     * Passes input listener object on to the enclosed GUI frame, to direct
     * KeyEvents from the user back to that listener (this will be the Game).
     */
    public void addKeyListener( KeyListener listener )
    {
        frame.addKeyListener( listener );
    }
    
    /**
     * Display graphical elements of game. Called by GUI system.
     */
    public void paintComponent( Graphics g )
    {
        for ( int row = 0; row < getNumRows(); row++ )
        {
            for ( int col = 0; col < getNumCols(); col++ )
            {
                Cell cell = cells[row][col];
                
                Color color = cell.getColor();
                g.setColor( color );
                int cellSize = getCellSize();
                int x = col * cellSize;
                int y = row * cellSize;
                g.fillRect( x, y, cellSize, cellSize );
                
                String imageFileName = cell.getImageFileName();
                if ( imageFileName != null )
                {
                    try
                    {
                        File f = new File( imageFileName );
                        URL url = f.toURI().toURL();
                        if ( url == null )
                            System.out.println( "File not found:  " + imageFileName );
                        else
                        {
                            
                            Image image = new ImageIcon( url ).getImage();
                            int width = image.getWidth( null );
                            int height = image.getHeight( null );
                            if ( width > height )
                            {
                                int drawHeight = cellSize * height / width;
                                g.drawImage( image, x, y + ( cellSize - drawHeight ) / 2, cellSize,
                                            drawHeight, null );
                            }
                            else
                            {
                                int drawWidth = cellSize * width / height;
                                g.drawImage( image, x + ( cellSize - drawWidth ) / 2, y, drawWidth,
                                            cellSize, null );
                            }
                        }
                    }
                    catch ( MalformedURLException e )
                    {
                        System.out.println( "File not found:  " + imageFileName );
                        e.printStackTrace();
                    }
                }
                
                if ( lineColor != null )
                {
                    g.setColor( lineColor );
                    g.drawRect( x, y, cellSize, cellSize );
                }
            }
        }
    }
    
    /* Utility method to return size of cells in grid. */
    private int getCellSize()
    {
        int cellWidth = getWidth() / getNumCols();
        int cellHeight = getHeight() / getNumRows();
        return Math.min( cellWidth, cellHeight );
    }
    
    /* Utility method: returns BufferedImage with given file-name (if found). */
    private BufferedImage loadImage( String imageFileName )
    {
        try
        {
            File f = new File( imageFileName );
            URL url = f.toURI().toURL();
            if ( url == null )
                throw new RuntimeException( "cannot find file:  " + imageFileName );
            
            return ImageIO.read( url );
        }
        catch ( IOException e )
        {
            throw new RuntimeException( "unable to read from file:  " + imageFileName );
        }
    }
    
    /* Utility method to display an image (pixelated) in grid */
    private void showImage( BufferedImage image )
    {
        for ( int row = 0; row < getNumRows(); row++ )
        {
            for ( int col = 0; col < getNumCols(); col++ )
            {
                int x = col * image.getWidth() / getNumCols();
                int y = row * image.getHeight() / getNumRows();
                int c = image.getRGB( x, y );
                int red = ( c & 0x00ff0000 ) >> 16;
                int green = ( c & 0x0000ff00 ) >> 8;
                int blue = c & 0x000000ff;
                cells[row][col].setColor( new Color( red, green, blue ) );
            }
        }
        repaint();
    }
}