/*
 * Ryan Wang
 * Mrs.Krasteva
 * 2020-06-14
 * Infected Person class of the game
 */
package movingObject;

import java.awt.Image;
import javax.swing.ImageIcon;

public class InfectedPerson extends Sprite{//was supposed to move left-right, or up-down
	@SuppressWarnings("unused")
	private Image f1, f2, f3, f4, f5, f6, f7, f8;

	public InfectedPerson(int x, int y, String name) {//constructor to initialize the object
		super(x, y);
		initIP(name);
	}
	
	public void initIP(String s) {//loads images
		loadImages();
		loadImage(s);
		activateImageDimensions();
	}
	
	public void loadImages() {//loads all images
		ImageIcon ii = new ImageIcon("InfectedPerson - Frame1.png");
		f1 = ii.getImage();
		ii = new ImageIcon("InfectedPerson - Frame2.png");
		f2 = ii.getImage();
		ii = new ImageIcon("InfectedPerson - Frame3.png");
		f3 = ii.getImage();
		ii = new ImageIcon("InfectedPerson - Frame4.png");
		f4 = ii.getImage();
		ii = new ImageIcon("InfectedPerson - Frame5.png");
		f5 = ii.getImage();
		ii = new ImageIcon("InfectedPerson - Frame6.png");
		f6 = ii.getImage();
		ii = new ImageIcon("InfectedPerson - Frame7.png");
		f7 = ii.getImage();
		ii = new ImageIcon("InfectedPerson - Frame8.png");
		f8 = ii.getImage();
	}

}
