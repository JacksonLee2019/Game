/*
 * Programmer: Jackson Lee
 * Purpose: Create a simple side scroller where the user gets and avoid certain objects
 * Date:3/30/16
 */
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
public class Game extends AbstractGame implements KeyListener {
    //main method
    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
    //constructor
    public Game() {
        super(10, 15);
        grid.addKeyListener(this);
    }
    //moves the image up or down depending on the direction
    public void move() {
        if(direction == 1) {
            if(userRow < grid.getNumRows() - 1) {
                userRow++;
                handleCollision(userRow, 1);
            }
        } else if(direction == -1) {
            if(userRow > 0) {
                userRow--;
                handleCollision(userRow, 1);
            }
        }
    }
    //randomly spawns an object in the right edge
    public void populateRightEdge() {
        int row = (int)(Math.random() * grid.getNumRows());
        int obj = (int)(Math.random() * 10);
        
        if(obj < 4) {
            grid.setImage(row, grid.getNumCols() - 1, avoidImg);
        } else if(obj > 3 && obj < 6) {
            grid.setImage(row, grid.getNumCols() - 1, getImg);
        }
    }
    //moves all images on the screen left, except for the user's character
    public void scrollLeft() {
        for(int i = 0; i < grid.getNumRows(); i++) {
            for(int j = 0; j < grid.getNumCols(); j++) {
                if(grid.getImage(i, j) == avoidImg) {
                    if(j != 0) {
                        grid.setImage(i, j, null);
                        grid.setImage(i, j - 1, avoidImg);
                    } else {
                        grid.setImage(i, j, null);
                    }
                } else if(grid.getImage(i, j) == getImg) {
                    if(j != 0) {
                        grid.setImage(i, j, null);
                        grid.setImage(i, j - 1, getImg);
                    } else {
                        grid.setImage(i, j, null);
                    }
                }
            }
        }
        
        handleCollision(userRow, 1);
    }
    //determines what happens when the user collides with the "avoid" or "get" images
    public void handleCollision(int row, int col) {
        if(grid.getImage(row, col) == getImg) {
            timesGet++;
        } else if(grid.getImage(row, col) == avoidImg) {
            timesAvoid++;
        }
    }
    //returns the score
    public int getScore() {
        return timesGet;
    }
    //ends the game when certain criteria are met
    public boolean isGameOver() {
        if(timesAvoid == 3) {
            return true;
        } else {
            return false;
        }
    }
    //sets the direction based on keyboard input from the user
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        if(key == KeyEvent.VK_UP) {
            direction = UP;
        } else if(key == KeyEvent.VK_DOWN) {
            direction = DOWN;
        }
    }
    //blank method
    public void keyReleased(KeyEvent e) {
        
    }
    //blank method
    public void keyTyped(KeyEvent e) {
        
    }
}