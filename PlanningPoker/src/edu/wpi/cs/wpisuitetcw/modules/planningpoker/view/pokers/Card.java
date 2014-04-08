/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.pokers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

/**
 * @author troyling, Rob
 * 
 */
public class Card extends JPanel {
	// const
	private final Dimension CARD_DIMENSION = new Dimension(292, 388);

	private int value = 0;
	private final JTextField txtboxValue;
	private Image cardPicture = null;

	public Card() {
		txtboxValue = new JTextField(3);
		try {
			cardPicture = ImageIO.read(getClass().getResource("card.png"));
		} catch (IOException ex) {

		}

		// setup the panel - card representation
		this.setSize(CARD_DIMENSION);
		this.setLayout(new MigLayout());
		this.add(txtboxValue, "dock center");
		this.setBorder(BorderFactory.createLineBorder(Color.BLUE));
	}

	@Override
	public void paintComponent(final Graphics g) {
		System.out.println("Hi I am here.");
		super.paintComponent(g);
		if (this.cardPicture != null)
			g.drawImage(cardPicture, 0, 0, null);
	}

	/**
	 * getTxtboxValue
	 * 
	 * @return the textbox for setting the value
	 */
	public JTextField getTxtboxValue() {
		return txtboxValue;
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

}
