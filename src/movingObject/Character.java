/*
 * Ryan Wang
 * Mrs.Krasteva
 * 2020-06-14
 * Character class of the game
 */
package movingObject;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.net.URL;
import javax.imageio.ImageIO;
import java.io.*;

public class Character extends Sprite {
	private int moveX;
	private int moveY;
	private int key;
	private Image f1, f2, f3, f4, f5, f6, f7, f8;
	private boolean kpL, kpR, kpU, kpD;

	public Character(int x, int y) {//character constructor, initializes character
		super(x, y);
		key = 38;
		kpL = false;
		kpR = false;
		kpU = false;
		kpD = false;
		initCharacter();
	}

	public void initCharacter() {//loads images
		try {
			loadImage();
		} catch (IOException e) {
		}
		image = f1;
		activateImageDimensions();
	}

	public void move() {//used to move the character
		/*if (x > 995 && x < 1055 && y >585 && y + moveY < 585) {// can't move up
		} else if (x > 995 && x < 1055 && y<530 && y + moveY > 530) {//can't move down
		} else if (y > 530 && y < 585 && x>1055 && x + moveX < 1055) {//can't move left
		} else if (y > 530 && y < 585 && x<995 && x + moveX > 995) {//can't move right
		} else */
		if (x + moveX < 1220 && y + moveY < 700 && x + moveX > -25 && y + moveY > -25) {
			x += moveX;
			y += moveY;
		}
	}

	public void keyPressed(KeyEvent e) {//detects key presses from the TAdapter class
		//keypresses also update the image of the character
		key = e.getKeyCode();
		if (key == KeyEvent.VK_LEFT) {
			moveX = -4;
			kpL = true;
		}
		if (key == KeyEvent.VK_RIGHT) {
			moveX = 4;
			kpR = true;
		}
		if (key == KeyEvent.VK_UP) {
			moveY = -4;
			kpU = true;
		}
		if (key == KeyEvent.VK_DOWN) {
			moveY = 4;
			kpD = true;
		}
		updateImage();
	}

	public void keyReleased(KeyEvent e) {//detects key releases from the TAdapter class
		//stops movement from that direction and updates the image
		key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {// 39
			moveX = 0;
			kpL = false;
		}

		if (key == KeyEvent.VK_RIGHT) {// 37
			moveX = 0;
			kpR = false;
		}

		if (key == KeyEvent.VK_UP) {// 38
			moveY = 0;
			kpU = false;
		}

		if (key == KeyEvent.VK_DOWN) {// 40
			moveY = 0;
			kpD = false;
		}
		updateMoves();
	}

	public void loadImage() throws IOException {// overloaded method loads all character images
		URL url = Board.class.getResource("/Character - Frame1.png");
		f1 = ImageIO.read(url);
		url = Board.class.getResource("/Character - Frame2.png");
		f2 = ImageIO.read(url);
		url = Board.class.getResource("/Character - Frame3.png");
		f3 = ImageIO.read(url);
		url = Board.class.getResource("/Character - Frame4.png");
		f4 = ImageIO.read(url);
		url = Board.class.getResource("/Character - Frame5.png");
		f5 = ImageIO.read(url);
		url = Board.class.getResource("/Character - Frame6.png");
		f6 = ImageIO.read(url);
		url = Board.class.getResource("/Character - Frame7.png");
		f7 = ImageIO.read(url);
		url = Board.class.getResource("/Character - Frame8.png");
		f8 = ImageIO.read(url);
	}

	public void updateImage() {//updates the image of the Character
		if (moveX < 0 && moveY < 0) {// going up left
			image = f2;
		} else if (moveX < 0 && moveY > 0) {// going down left
			image = f4;
		} else if (moveX > 0 && moveY > 0) {// going down right
			image = f6;
		} else if (moveX > 0 && moveY < 0) {// going up right
			image = f8;
		} else if (moveX < 0) {// left
			image = f3;
		} else if (moveX > 0) {// right
			image = f7;
		} else if (moveY < 0) {// up
			image = f1;
		} else if (moveY > 0) {// down
			image = f5;
		}
	}

	public void updateMoves() {//makes sure that the movement matches the control inputs
		if (kpL) {// 39
			moveX = -4;
		}
		if (kpR) {// 37
			moveX = 4;
		}
		if (kpU) {// 38
			moveY = -4;
		}
		if (kpD) {// 40
			moveY = 4;
		}
		updateImage();
	}
}
