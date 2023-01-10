/*
 * Ryan Wang && Agith Maniventhan
 * Mrs.Krasteva
 * 2020-06-14
 * Min has helped me with this project. He helped me make the jar file and implement images with the URL class.
 * Sources used:
 * http://zetcode.com/tutorials/javagamestutorial/movingsprites/
 * http://zetcode.com/tutorials/javagamestutorial/collision/
 * 
 * Covid19Game is the main class of the program. 
 */
package movingObject;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class Covid19Game extends JFrame{
	
	public Covid19Game() {
		gameUI();
	}

	private void gameUI() {//initializes the JFrame
		add(new Board());
		setTitle("RUN FROM COVID-19");
		setSize(1300, 800);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {//this where the program runs
		EventQueue.invokeLater(() -> {
			Covid19Game cg = new Covid19Game();
            cg.setVisible(true);
        });	
	}
}
