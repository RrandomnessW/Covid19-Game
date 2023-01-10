/*
 * Ryan Wang
 * Mrs.Krasteva
 * 2020-06-14
 * InfectedAnimal class of the game
 */
package movingObject;

public class InfectedAnimal extends Sprite{//was supposed to moves in circles

	public InfectedAnimal(int x, int y) {//constructor to initialize the object
		super(x, y);
		initIA();
	}
	
	public void initIA() {//loads image
		loadImage("InfectedAnimal.png");
		activateImageDimensions();
	}
}
