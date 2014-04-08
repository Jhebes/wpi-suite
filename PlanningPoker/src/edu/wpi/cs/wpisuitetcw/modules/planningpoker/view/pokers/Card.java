/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.pokers;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author troyling, Rob
 * 
 */
public class Card extends JPanel {
	private int value = 0;
	private JTextField txtboxValue;
		
	public Card() {
		txtboxValue = new JTextField();
	}
	
	/**
	 * Get the value of the card
	 * 
	 * @return the value of the card
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Set the value of card
	 * 
	 * @param value
	 *            of the card
	 */
	public void setValue(int value) {
		this.value = value;
	}
	
	/**
	 * @return the image of the card
	 * @throws IOException 
	 */
	public Image getCardPicture() throws IOException {
		return ImageIO.read(getClass().getResource("card.png"));
	}
	
	public void setContext() {
		
	}
}
