
/**
 * Creates the basic data cell for the Scrolling Game project.
 *
 * @author Dave Feinberg
 * @author M. Allen
 */
import java.awt.Color;

public class Cell
{
    private Color color;
    private String imageFileName;
    
    /**
     * Basic constructor; creates a black cell, with no image.
     */
    public Cell()
    {
        color = Color.black;
        imageFileName = null;
    }
    
    /**
     * Sets color of Cell.
     *
     * @param c Background color for this Cell.
     */
    public void setColor( Color c )
    {
        color = c;
    }
    
    /**
     * @return The background color of this Cell.
     */
    public Color getColor()
    {
        return color;
    }
    
    /**
     * @return Name of image-file for image in this Cell.
     */
    public String getImageFileName()
    {
        return imageFileName;
    }
    
    /**
     * Sets the image-file for the Cell.
     *
     * @param fileName Name of image-file to place in this Cell.
     *            Image must be in available location.
     */
    public void setImageFileName( String fileName )
    {
        imageFileName = fileName;
    }
}