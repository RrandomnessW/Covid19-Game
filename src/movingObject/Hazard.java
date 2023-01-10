/*
 * Ryan Wang
 * Mrs.Krasteva
 * 2020-06-14
 * Hazard class of the game
 */
package movingObject;

public class Hazard extends Sprite{//uses the methods of the sprite class

	public Hazard(int x, int y) {//constructor to initialize the object
		super(x, y);
		initHazard();
	}
	
	public void initHazard() {//loads images
		loadImage("Hazard.png");
		activateImageDimensions();
	}

}
