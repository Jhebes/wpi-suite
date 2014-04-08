/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.pokers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author troyling, Rob
 * 
 */
public class Card extends JPanel {
	// constants
	private final Dimension CARD_DIMENSION = new Dimension(146, 194);

	private int value = 0;
	private final JTextField txtboxValue;

	private Image cardPicture = null;

	public Card() {
		txtboxValue = new JTextField(3);

		// load background image
		try {
			Image img = ImageIO.read(getClass().getResource("new_card.png"));
			ImageIcon icon = new ImageIcon(img);
			this.cardPicture = icon.getImage();
		} catch (IOException ex) {
		}

		// setup the card panel
		this.setLayout(new GridBagLayout());
		this.add(txtboxValue);
		this.setPreferredSize(CARD_DIMENSION);

		// delete this
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (cardPicture != null) {
			g.drawImage(cardPicture, -3, 0, this);
		}
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
