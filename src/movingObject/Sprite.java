/*
 * Ryan Wang
 * Mrs.Krasteva
 * 2020-06-14
 * Sprite class of the game. This class will be used as a template for the
 * Character, Hazard, InfectedAnimal, and InfectedPerson class. 
 */
package movingObject;

import java.awt.*;
import java.net.URL;
import javax.imageio.ImageIO;
import java.io.*;

public class Sprite {//used as a template for many other classes used in this program
	public int x;
	public int y;
	public int width;
	public int height;
	public boolean visible;
	public Image image;
	
	public Sprite(int x, int y) {//constructor
		this.x = x;
		this.y = y;
		visible = true;
	}
	
	public void loadImage(String imageName) {//loads/stores the image
		URL url = Board.class.getResource("/"+imageName);
		try {
		image = ImageIO.read(url);
		}catch(IOException e) {}
	}
	
	public void activateImageDimensions() {//give width and height values
		width = image.getWidth(null);
		height = image.getHeight(null);
	}
	
	public Image getImage() {//returns the image
		return image;
	}
	
	public int getX() {//returns x coordinate
		return x;
	}
	
	public int getY() {//return y coordinate
		return y;
	}
}
