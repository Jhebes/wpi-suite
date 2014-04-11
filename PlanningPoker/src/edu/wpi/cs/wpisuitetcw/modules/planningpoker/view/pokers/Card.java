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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

/**
 * 
 */
public class Card extends JPanel {
	// constants
	private final String ERROR_MSG = "<html><font color='red'>Please enter a <br />positive integer</font></html>";
	private final Dimension CARD_DIMENSION = new Dimension(146, 194);

	private final JTextField txtboxValue;
	private final JLabel labelError;

	private Image cardPicture = null;

	public Card() {
		txtboxValue = new JTextField(3);
		labelError = new JLabel(ERROR_MSG);

		labelError.setVisible(false);

		// load background image
		try {
			Image img = ImageIO.read(getClass().getResource("new_card.png"));
			ImageIcon icon = new ImageIcon(img);
			this.cardPicture = icon.getImage();
		} catch (IOException ex) {
		}

		// setup the card panel
		this.setLayout(new GridBagLayout());

		JPanel container = new JPanel();

		container.setLayout(new MigLayout());
		container.add(txtboxValue, "center, wrap");
		container.add(labelError);
		container.setBackground(Color.WHITE);

		this.add(container);
		this.setPreferredSize(CARD_DIMENSION);

		// set border
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
	 * determine if user enters pure integer value
	 * 
	 * @return true if so, else otherwise
	 */
	public boolean validateCardValue() {
		String inputValue = this.txtboxValue.getText();

		if (inputValue.equals("")) {
			return false;
		} else {
			return this.isPositiveInteger(inputValue);
		}
	}

	/**
	 * Determine if the String contains positive integer
	 * 
	 * @param String
	 *            input
	 * @return true if so; false otherwise
	 */
	private boolean isPositiveInteger(String s) {
		try {
			int value = Integer.parseInt(s);
			if (value > 0) {
				return true;
			} else {
				return false;
			}
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * makes the card invalid by changing the color
	 */
	public void setCardInvalid() {
		this.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		this.labelError.setVisible(true);
	}

	/**
	 * card is valid, set the border back to black
	 */
	public void setCardValid() {
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		this.labelError.setVisible(false);
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
	 * return the value of the card
	 * 
	 * @return
	 */
	public int getValue() {
		int value;
		try {
			value = Integer.parseInt(this.txtboxValue.getText());
		} catch (NumberFormatException e) {
			value = 0;
		}
		return value;
	}

}
