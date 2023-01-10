/*
 * Ryan Wang
 * Mrs.Krasteva
 * 2020-06-14
 * Board class of the game. This is where everything is displayed which includes the menu and the game.
 */
package movingObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.IOException;
import java.net.URL;
//import movingObject.MovingAnObject;

@SuppressWarnings("serial")
public class Board extends JPanel implements ActionListener {
	private final int POSX = 1000;
	private final int POSY = 690;
	private final int DELAY = 10;
	private Timer timer;
	private Character player;
	private Image park, life, continueButton, iTitle, iStart, iInstructions, iExit, iDescription, iBack, iLearning,
			iTesting, iCursor, iPresenting, iLogo, iBackground, iReturn, igInstructions, sDiffOpts, iDialogue,
			iContinueButton;
	private boolean inGame, winGame, endGame, invulnerable, testing, splashScreen, mainMenu, instructions,
			chooseDifficulty, dialogue;
	@SuppressWarnings("unused")
	private Hazard[] ha;
	private Ellipse2D charHit;
	private InfectedPerson[] aIP;
	private Hazard[] aHaz;
	private InfectedAnimal[] aIA;
	private Ellipse2D[] ipHit;
	private Polygon[] hazHit;
	private Ellipse2D[] iaHit;
	private Ellipse2D[] fHit;
	private Rectangle[] checkpoints;
	private boolean[] checked;
	private int lives, tInvul, splashTime, menuLocation;
	private boolean[] death;// determines which death you had
	private Image[] endScreens;
	// death: 0 - person, 1 - hazard, 2 - animal, 3 - fence, 4 - pond
	// Min has helped me with this project. He helped me make the jar file and
	// implement images with the URL class.

	public Board() {// initializes the board/menu
		initBoard();
	}

	private void initBoard() {// initializes the variables for both the board and the game for the first time.
		// also initializes images and starts a timer
		setBackground(Color.BLACK);
		initMainMenu();
		addKeyListener(new TAdapter());
		setFocusable(true);
		endScreens = new Image[6];
		timer = new Timer(DELAY, this);
		timer.start();

		inGame = false;// not in game

		initGame();
		try {
			initializeImages();
		} catch (IOException e) {
		}
	}

	private void initMainMenu() {// initializes the mainMenu and splashscreen
		splashScreen = true;
		mainMenu = false;
		instructions = false;
		chooseDifficulty = false;
		splashTime = 0;
		menuLocation = 0;
		testing = false;
		dialogue = false;
	}

	private void initGame() {// used to initialize/reinitialize the game
		winGame = false;
		endGame = false;

		player = new Character(POSX, POSY);
		charHit = new Ellipse2D.Double(player.getX() + 20, player.getY() + 20, 60, 60);
		lives = 3;

		checked = new boolean[4];
		for (int i = 0; i < 4; i++) {
			checked[i] = false;
		}
		death = new boolean[5];
		for (int i = 0; i < 5; i++) {
			death[i] = false;
		}
		initializeArrays(testing);
		initializeIP(testing);
		initializeHaz(testing);
		initializeIA(testing);
		updateHitboxes(testing);
		tInvul = 0;
		invulnerable = false;
	}

	private void initializeImages() throws IOException {// used to initialize game images.
		// Min Kang helped
		URL url = Board.class.getResource("/theMapV2.png");
		park = ImageIO.read(url);
		url = Board.class.getResource("/heart.png");
		life = ImageIO.read(url);
		url = Board.class.getResource("/coughDeath.png");// person
		endScreens[0] = ImageIO.read(url);
		url = Board.class.getResource("/bleedHazard.png");// hazard
		endScreens[1] = ImageIO.read(url);
		url = Board.class.getResource("/bleedAnimal.png");// animal
		endScreens[2] = ImageIO.read(url);
		url = Board.class.getResource("/WIRECUT.png");// fence
		endScreens[3] = ImageIO.read(url);
		url = Board.class.getResource("/drown.png");// pond
		endScreens[4] = ImageIO.read(url);
		url = Board.class.getResource("/vicSreeen.png");// victory
		endScreens[5] = ImageIO.read(url);
		url = Board.class.getResource("/press enter.png");
		continueButton = ImageIO.read(url);
		url = Board.class.getResource("/Title.png");
		iTitle = ImageIO.read(url);
		url = Board.class.getResource("/start.png");
		iStart = ImageIO.read(url);
		url = Board.class.getResource("/Instructions.png");
		iInstructions = ImageIO.read(url);
		url = Board.class.getResource("/Exit.png");
		iExit = ImageIO.read(url);
		url = Board.class.getResource("/Description.png");
		iDescription = ImageIO.read(url);
		url = Board.class.getResource("/back.png");
		iBack = ImageIO.read(url);
		url = Board.class.getResource("/learning.png");
		iLearning = ImageIO.read(url);
		url = Board.class.getResource("/testing.png");
		iTesting = ImageIO.read(url);
		url = Board.class.getResource("/cursor.png");
		iCursor = ImageIO.read(url);
		url = Board.class.getResource("/presenting.png");
		iPresenting = ImageIO.read(url);
		url = Board.class.getResource("/logo.png");
		iLogo = ImageIO.read(url);
		url = Board.class.getResource("/background.png");
		iBackground = ImageIO.read(url);
		url = Board.class.getResource("/return to menu.png");
		iReturn = ImageIO.read(url);
		url = Board.class.getResource("/Game Instructions.png");
		igInstructions = ImageIO.read(url);
		url = Board.class.getResource("/select difficulty options.png");
		sDiffOpts = ImageIO.read(url);
		url = Board.class.getResource("/dialogue.png");
		iDialogue = ImageIO.read(url);
		url = Board.class.getResource("/press space.png");
		iContinueButton = ImageIO.read(url);
	}

	@Override
	public void paintComponent(Graphics g) {// This is where everthing is painted and displayed.
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		if (splashScreen) { // draws the splashScreen
			splashScreen(g);
		}
		if (mainMenu) {// draws the main menu
			g2d.setColor(Color.BLACK);
			g2d.drawImage(iBackground, 0, 0, this);
			drawMainMenu(g);
		}

		if (instructions) {// draws the instructions
			drawInstructions(g);
		}

		if (chooseDifficulty) {// draws the choose difficulty screen
			drawChooseDif(g);
		}

		if (dialogue) {// draws the dialogue screen
			drawDialogue(g);
		}

		if (inGame) {// draws the game board
			doDrawing(g);
		} else if (endGame) {// draws an end game screen of winning or dying
			if (winGame) {
				drawGameOver(g, 5);
			} else {
				if (death[0]) {
					drawGameOver(g, 0);
				} else if (death[1]) {
					drawGameOver(g, 1);
				} else if (death[2]) {
					drawGameOver(g, 2);
				} else if (death[3]) {
					drawGameOver(g, 3);
				} else if (death[4]) {
					drawGameOver(g, 4);
				}
			}
		}
		Toolkit.getDefaultToolkit().sync();
	}

	private void doDrawing(Graphics g) {// used to draw the game board which includes your character and the hazards.
		Graphics2D g2d = (Graphics2D) g;

		g2d.drawImage(park, 0, 0, this);// background
		g2d.drawImage(player.getImage(), player.getX(), player.getY(), this);// character

		for (int i = 0; i < lives; i++) {// draws lives
			g2d.drawImage(life, 1120 + i * 50, 0, this);
		}

		if (lives == 0) {
			inGame = false;
			endGame = true;
		}

		charHit = new Ellipse2D.Double(player.getX() + 20, player.getY() + 20, 60, 60);// circle hitbox

		if (invulnerable) {
			tInvul++;
		}
		if (tInvul == 100) {
			invulnerable = false;
			tInvul = 0;
		}

		boolean win = true;
		for (int i = 0; i < 4; i++) {
			if (!checked[i]) {
				win = false;
				break;
			}
		}
		if (win) {// win
			endGame = true;
			winGame = true;
			inGame = false;
		}

		/*
		 * Rectangle r = new Rectangle(995, 530, 60, 55); g2d.draw(r);
		 */

		drawIP(testing, g2d);
		drawHaz(testing, g2d);
		drawIA(testing, g2d);

	}

	private void drawMainMenu(Graphics g) {// used to draw the mainMenu
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(iTitle, 150, 0, this);
		g2d.drawImage(iStart, 400, 200, this);
		g2d.drawImage(iInstructions, 400, 300, this);
		g2d.drawImage(iExit, 400, 400, this);
		g2d.drawImage(iDescription, 200, 500, this);
		if (menuLocation == 0) {
			g2d.drawImage(iCursor, 300, 200, this);
		} else if (menuLocation == 1) {
			g2d.drawImage(iCursor, 300, 300, this);
		} else if (menuLocation == 2) {
			g2d.drawImage(iCursor, 300, 400, this);
		}

	}

	private void splashScreen(Graphics g) {// used to draw the splashScreen
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(iLogo, 450, 100, this);
		g2d.drawImage(iPresenting, 100, 400, this);
		if (splashScreen) {
			splashTime++;
		}
		if (splashTime == 150) {
			splashScreen = false;
			mainMenu = true;
		}
	}

	private void drawInstructions(Graphics g) {// used to draw the instructions
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(iBack, 1000, 650, this);
		g2d.drawImage(iCursor, 900, 650, this);
		g2d.drawImage(iReturn, 100, 650, this);
		g2d.drawImage(igInstructions, 100, 100, this);

	}

	private void drawChooseDif(Graphics g) {// used to draw the choose difficulty screen
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(iLearning, 150, 400, this);
		g2d.drawImage(iTesting, 800, 400, this);
		g2d.drawImage(sDiffOpts, 100, 100, this);
		if (!testing) {
			g2d.drawImage(iCursor, 50, 400, this);
		} else {
			g2d.drawImage(iCursor, 750, 400, this);
		}
	}

	private void drawDialogue(Graphics g) {// used to draw the dialogue screen.
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(iDialogue, 45, 20, this);
		g.drawImage(iContinueButton, 314, 528, this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {// updates the screen
		if (inGame) {
			updateCharacter();
			updateHitboxes(testing);
			collisionCheck();
		}
		repaint();
	}

	private void collisionCheck() {// used to check if there is any collision between the character and hazards.
		// check if character hitbox intersects with hazards hitbox
		if (!invulnerable) {
			for (Ellipse2D e2 : ipHit) {// person

				if (charHit.intersects(e2.getX(), e2.getY(), 60, 60)) {
					lives--;
					if (lives == 0) {
						death[0] = true;
					}
					invulnerable = true;
				}
			}

			for (Polygon p : hazHit) {// hazard
				if (polygonCollide(charHit, p)) {
					lives--;
					if (lives == 0) {
						death[1] = true;
					}
					invulnerable = true;
				}
			}

			for (Ellipse2D e2 : iaHit) {// animal
				if (charHit.intersects(e2.getX(), e2.getY(), 51, 51)) {
					lives--;
					if (lives == 0) {
						death[2] = true;
					}
					invulnerable = true;
				}
			}

			for (Ellipse2D e2 : fHit) {// fence
				if (charHit.intersects(e2.getX(), e2.getY(), 20, 20)) {
					lives--;
					if (lives <= 0) {
						death[3] = true;
					}
					invulnerable = true;
				}
			}

			for (int i = 0; i < 4; i++) {// checkpoints
				if (charHit.intersects(checkpoints[i])) {
					if (i > 0 && !checked[i - 1]) {
					} else {
						checked[i] = true;
					}
				}
			}

			Rectangle r = new Rectangle(280, 275, 450, 195);// pond
			if (charHit.intersects(r)) {
				lives = 0;
				death[4] = true;
			}

		}
	}

	private boolean polygonCollide(Ellipse2D e1, Polygon p) {// used to check if there is collision between a green
																// hazard and the character
		int[] pX = p.xpoints;
		int[] pY = p.ypoints;

		Rectangle r = new Rectangle(pX[5], pY[5], 51, 8);
		if (e1.intersects(r)) {
			return true;
		}
		r = new Rectangle(pX[0], pY[0], 3, 20);
		if (e1.intersects(r)) {
			return true;
		}

		return false;
	}

	private void updateCharacter() {// updates the characters coordinates.
		player.move();
	}

	private void initializeIP(boolean t) {// used to assign the location and images to the InfectedPerson array
		aIP[0] = new InfectedPerson(1200, 300, "InfectedPerson - Frame1.png");
		aIP[1] = new InfectedPerson(800, 50, "InfectedPerson - Frame2.png");
		aIP[2] = new InfectedPerson(75, 100, "InfectedPerson - Frame3.png");
		aIP[3] = new InfectedPerson(200, 600, "InfectedPerson - Frame4.png");
		aIP[4] = new InfectedPerson(800, 450, "InfectedPerson - Frame5.png");
		if (t) {
			aIP[5] = new InfectedPerson(400, 150, "InfectedPerson - Frame6.png");
			aIP[6] = new InfectedPerson(1000, 150, "InfectedPerson - Frame7.png");
			aIP[7] = new InfectedPerson(450, 650, "InfectedPerson - Frame8.png");
			aIP[8] = new InfectedPerson(150, 400, "InfectedPerson - Frame1.png");
			aIP[9] = new InfectedPerson(900, 675, "InfectedPerson - Frame2.png");
		}
	}

	private void initializeHaz(boolean t) {// used to assign the location to the Hazard array
		aHaz[0] = new Hazard(25, 300);
		aHaz[1] = new Hazard(650, 650);
		aHaz[2] = new Hazard(500, 10);
		aHaz[3] = new Hazard(1200, 400);
		if (t) {
			aHaz[4] = new Hazard(950, 150);
			aHaz[5] = new Hazard(0, 550);
			aHaz[6] = new Hazard(250, 50);
			aHaz[7] = new Hazard(175, 500);
		}
	}

	private void initializeIA(boolean t) {// used to assign the location to the InfectedAnimal array
		aIA[0] = new InfectedAnimal(275, 250);
		aIA[1] = new InfectedAnimal(700, 200);
		aIA[2] = new InfectedAnimal(450, 450);
		if (t) {
			aIA[3] = new InfectedAnimal(900, 300);
			aIA[4] = new InfectedAnimal(100, 700);
			aIA[5] = new InfectedAnimal(100, 10);
		}
	}

	private void drawGameOver(Graphics g, int d) {// used to draw the game over screens.
		setBackground(Color.BLACK);
		g.drawImage(endScreens[d], 45, 20, this);
		g.drawImage(continueButton, 424, 600, this);
	}

	private void drawIP(boolean t, Graphics2D gg) {// used to draw each object in the InfectedPerson array
		for (int i = 0; i < 5; i++) {
			gg.drawImage(aIP[i].getImage(), aIP[i].getX(), aIP[i].getY(), this);
		}
		if (t) {
			for (int i = 5; i < 10; i++) {
				gg.drawImage(aIP[i].getImage(), aIP[i].getX(), aIP[i].getY(), this);
			}
		}
	}

	private void drawHaz(boolean t, Graphics2D gg) {// used to draw each object in the Hazard array
		for (int i = 0; i < 4; i++) {
			gg.drawImage(aHaz[i].getImage(), aHaz[i].getX(), aHaz[i].getY(), this);
		}
		if (t) {
			for (int i = 4; i < 8; i++) {
				gg.drawImage(aHaz[i].getImage(), aHaz[i].getX(), aHaz[i].getY(), this);
			}
		}
	}

	private void drawIA(boolean t, Graphics2D gg) {// used to draw each object in the InfectedAnimal array
		for (int i = 0; i < 3; i++) {
			gg.drawImage(aIA[i].getImage(), aIA[i].getX(), aIA[i].getY(), this);
		}
		if (t) {
			for (int i = 3; i < 6; i++) {
				gg.drawImage(aIA[i].getImage(), aIA[i].getX(), aIA[i].getY(), this);
			}
		}
	}

	private void initializeArrays(boolean t) {// initializes the hazard arrays and their hitboxes
		if (t) {// initializes a different amount of hazards
			aIP = new InfectedPerson[10];// ten people
			aHaz = new Hazard[8];// eight hazards
			aIA = new InfectedAnimal[6];// six animals
			ipHit = new Ellipse2D[10];
			hazHit = new Polygon[8];
			iaHit = new Ellipse2D[6];
		} else {// learning will have
			aIP = new InfectedPerson[5];// 5 people
			aHaz = new Hazard[4];// 4 hazards
			aIA = new InfectedAnimal[3];// 3 animals
			ipHit = new Ellipse2D[5];
			hazHit = new Polygon[4];
			iaHit = new Ellipse2D[3];
		}
		fHit = new Ellipse2D[11];
		for (int i = 0; i < 11; i++) {
			fHit[i] = new Ellipse2D.Double(1070 + 21 * i, 20 * i, 20, 20);
		}
		checkpoints = new Rectangle[4];
		checkpoints[0] = new Rectangle(950, 0, 300, 300);
		checkpoints[1] = new Rectangle(80, 0, 300, 300);
		checkpoints[2] = new Rectangle(80, 500, 300, 300);
		checkpoints[3] = new Rectangle(950, 500, 300, 300);
	}

	private void updateHitboxes(boolean t) {// update the positions of hitboxes
		int x;
		int y;
		int[] ax;
		int[] ay;
		for (int i = 0; i < 5; i++) {// people hitboxes
			ipHit[i] = new Ellipse2D.Double(aIP[i].getX(), aIP[i].getY(), 60, 60);
		}
		for (int i = 0; i < 4; i++) {// hazards hitboxes
			x = aHaz[i].getX();
			y = aHaz[i].getY();
			ax = new int[] { 22 + x, 48 + x, 51 + x, 51 + x, 0 + x, 0 + x, 22 + x }; // use for reference
			ay = new int[] { 0 + y, 20 + y, 20 + y, 28 + y, 28 + y, 18 + y, 18 + y };
			hazHit[i] = new Polygon(ax, ay, 7);
		}
		for (int i = 0; i < 3; i++) {// animal hitboxes
			iaHit[i] = new Ellipse2D.Double(aIA[i].getX(), aIA[i].getY(), 51, 51);
		}

		if (t) {
			for (int i = 5; i < 10; i++) {
				ipHit[i] = new Ellipse2D.Double(aIP[i].getX(), aIP[i].getY(), 60, 60);
			}
			for (int i = 4; i < 8; i++) {
				x = aHaz[i].getX();
				y = aHaz[i].getY();
				ax = new int[] { 22 + x, 48 + x, 51 + x, 51 + x, 0 + x, 0 + x, 22 + x }; // use for reference
				ay = new int[] { 0 + y, 20 + y, 20 + y, 28 + y, 28 + y, 20 + y, 20 + y };
				hazHit[i] = new Polygon(ax, ay, 7);
			}
			for (int i = 3; i < 6; i++) {
				iaHit[i] = new Ellipse2D.Double(aIA[i].getX(), aIA[i].getY(), 51, 51);
			}
		}
	}

	private class TAdapter extends KeyAdapter {// This class is used to detect any keypresses from the keyboard.

		@Override
		public void keyReleased(KeyEvent e) {// detects keyreleases. used for the movement of your character
			player.keyReleased(e);
		}

		@Override
		public void keyPressed(KeyEvent e) {// detects keypresses.
			player.keyPressed(e);
			int key = e.getKeyCode();
			if (key == KeyEvent.VK_ENTER && endGame) {
				inGame = false;
				mainMenu = true;
				endGame = false;
				winGame = false;
			}
			if (mainMenu) {
				mainMenuSelect(e);
			}
			if (instructions) {
				instructionBack(e);
			}
			if (chooseDifficulty) {
				diffSelect(e);
			}
			if (dialogue) {
				startGame(e);
			}
		}

		public void mainMenuSelect(KeyEvent e) {// detects key presses while in the main menu
			int key = e.getKeyCode();
			if (key == KeyEvent.VK_UP) {
				if (menuLocation - 1 < 0) {
					menuLocation = 2;
				} else {
					menuLocation--;
				}
			} else if (key == KeyEvent.VK_DOWN) {
				if (menuLocation + 1 > 2) {
					menuLocation = 0;
				} else {
					menuLocation++;
				}
			}

			if (key == KeyEvent.VK_SPACE) {
				mainMenu = false;
				if (menuLocation == 0) {
					// start game
					chooseDifficulty = true;
				} else if (menuLocation == 1) {
					// instructions
					instructions = true;
				} else if (menuLocation == 2) {
					// exit
					System.exit(1);
				}
			}
		}

		public void instructionBack(KeyEvent e) {// detects key presses while in the instructions
			int key = e.getKeyCode();
			if (key == KeyEvent.VK_BACK_SPACE) {
				instructions = false;
				mainMenu = true;
			}
		}

		public void diffSelect(KeyEvent e) {// detects key presses while in the in the difficulty selection
			int key = e.getKeyCode();
			if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_LEFT) {
				testing = !testing;
			}
			if (key == KeyEvent.VK_ENTER) {
				chooseDifficulty = false;
				dialogue = true;
				initGame();
			}
		}

		public void startGame(KeyEvent e) {// detects key presses while in the dialogue screen.
			int key = e.getKeyCode();
			if (key == KeyEvent.VK_SPACE) {
				dialogue = false;
				inGame = true;
			}
		}

	}

}
