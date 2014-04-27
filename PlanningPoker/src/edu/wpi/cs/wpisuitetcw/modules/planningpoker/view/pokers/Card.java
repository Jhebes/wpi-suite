/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.pokers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.characteristics.CardDisplayMode;

/**
 * A card is a GUI component that imitates a real card.
 */
public class Card extends JPanel {
	private static final long serialVersionUID = 8830282477028926730L;

	// VVVVVVVVVVVVVVVVVVVVVVVV Constants VVVVVVVVVVVVVVVVVVVVVVVVVVVV
	private final String ERROR_MSG = 
			"<html><font color='red'>Positive integer only</font></html>";
	private final String BUTTON_TEXT = "\u2716";
	private final Dimension CARD_DIMENSION = new Dimension(146, 194);
	
	// VVVVVVVVVVVVVVVVVVVVVV GUI components VVVVVVVVVVVVVVVVVVVVVVVVV
	/** A container holding all the GUI components of the card */
	private JPanel container;
	
	/** A picture of the card */
	private Image cardPicture = null;

	/** A text box to enter a value of a card in */
	private final JTextField txtboxValue;
	
	/** An error label to inform invalid value */
	private final JLabel labelError;
	
	/** A button to delete the card */
	private final JButton closeButton;
	
	// VVVVVVVVVVVVVVVVVVVVVVVVVV DATA VVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
	/** A flag determining the validity of the value in text box*/
	private boolean isValueValid;
	
	/** A flag determining if a mouse is hovering the card */
	private boolean isMouseovered;

	/** A flag determining if card is selected or not */
	private boolean isSelected;
	
	/** Card value */
	private int cardValue;

	/** Display mode for the card */
	private CardDisplayMode mode;

	/** Parent panel that contains the card */
	private DisplayDeckPanel parentPanel;

	/** 
	 * Construct a card 
	 * 
	 * @param mode A CardDisplayMode (Create/Display)
	 * @param value An integer representing value of the card
	 * @param deckPanel A DisplayDeckPanel exhibits this card
	 */
	public Card(CardDisplayMode mode, int value, DisplayDeckPanel deckPanel) {
		this(mode, value);
		this.parentPanel = deckPanel;
		if (mode.equals(CardDisplayMode.DISPLAY)) {
			this.addSelectionListener(this);
		}
	}

	/**
	 * Construct a card
	 * 
	 * @param mode A CardDisplayMode (Create/Display)
	 * @param value An integer representing value of the card
	 */
	public Card(CardDisplayMode mode, int value) {
		this(mode);
		this.cardValue = value;
		this.displayCardValue();
	}
	
	/** 
	 * Construct a card 
	 * 
	 * @param mode A CardDisplayMode (Create/Display)
	 */
	public Card(CardDisplayMode mode) {
		// Set the card's mode
		this.mode = mode;

		// Set card's background image
		try {
			Image img = ImageIO.read(getClass().getResource("new_card.png"));
			ImageIcon icon = new ImageIcon(img);
			this.cardPicture = icon.getImage();
		} catch (IOException e) {
			Logger.getLogger("PlanningPoker").log(Level.INFO,
					"Could not load the image for planning poker cards", e);
		}

		// Create the text field
		txtboxValue = new JTextField(3);

		// Create error JLabel
		labelError = new JLabel(ERROR_MSG);
		labelError.setVisible(false);

		// Create close button
		closeButton = new JButton(BUTTON_TEXT);
		closeButton.setFont(closeButton.getFont().deriveFont((float) 8));
		closeButton.setMargin(new Insets(0, 0, 0, 0));
		closeButton.setVisible(false); // button is not visible until user
										// mouseover it

		// Set card initial statuses
		isValueValid = true;
		isMouseovered = false;
		isSelected = false;
		
		// Set default value for newly created card to -1
		cardValue = -1;

		// Create the container to hold the card's components
		container = new JPanel();
		addComponentsToContainer();
		
		setLayout(new MigLayout());
		this.add(closeButton, "right, wrap");
		this.add(container, "center, wrap, span");
		this.setPreferredSize(CARD_DIMENSION);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

		// Add highlight feature to the card
		this.addMouseoverHightlight(this, this);

		// display selective elements based on the mode it's in
		if (mode.equals(CardDisplayMode.DISPLAY)) {
			disableEditableFields();
		} else if (mode.equals(CardDisplayMode.CREATE)) {
			// add listener
			this.addListenerToValueTextBox(txtboxValue, this);
			this.addListenerToCloseButton(closeButton, this);

			// add highlight feature to the card
			this.addMouseoverHightlight(closeButton, this);
			this.addMouseoverHightlight(txtboxValue, this);
			this.addMouseoverHightlight(labelError, this);
		} else if (mode.equals(CardDisplayMode.NO_DECK)) {
			// this should never be executed
			disableEditableFields();
		}
	}

	/*
	 * Add text field and label error to the card container
	 */
	private void addComponentsToContainer() {
		container.setLayout(new MigLayout());
		container.add(txtboxValue, "center, wrap");
		container.add(labelError, "center");
		
		container.setBackground(Color.WHITE);
		container.setOpaque(false);
	}

	/**
	 * Display the card with the given value
	 */
	private void displayCardValue() {
		// containing panel
		JPanel valuePanel = new JPanel();
		valuePanel.setLayout(new MigLayout());
		valuePanel.setBackground(Color.WHITE);
		valuePanel.setOpaque(false);

		// label for displaying value
		JLabel valueLabel = new JLabel(Integer.toString(cardValue),
				JLabel.CENTER);
		valueLabel.setFont(new Font("Serif", Font.BOLD, 48));

		// set up the main panel
		valuePanel.add(valueLabel, "dock center");
		this.add(valuePanel, "dock center");
	}

	/**
	 * Only in DISPLAY mode. Disable all editable fields
	 */
	private void disableEditableFields() {
		this.remove(closeButton);
		this.remove(container);

		// this.closeButton.setVisible(false);
		// this.container.setVisible(false);
	}

	/**
	 * Set the image as the background of the panel
	 */
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
			this.isValueValid = false;
			return false;
		} else {
			this.isValueValid = this.isPositiveInteger(inputValue);
			return this.isValueValid;
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
	 * highlights the card
	 */
	public void setCardHighlighted() {
		this.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
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
	 * card is selected, set the border to green
	 */
	public void setCardSelected() {
		this.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
	}

	/**
	 * change the card's layout
	 */
	public void changeCardLayout() {
		// toogle closebutton
		closeButton.setVisible(this.isMouseovered);

		// change the border of the card
		if (isSelected) {
			// card is selected
			setCardSelected();
		} else {
			// card is not selected
			if (this.isMouseovered) {
				if (this.isValueValid) {
					this.setCardHighlighted();
				} else {
					this.setCardInvalid();
				}
			} else {
				if (this.isValueValid) {
					this.setCardValid();
				} else {
					this.setCardInvalid();
				}
			}
		}

	}

	/**
	 * listener for dynamically validate the textfield input
	 */
	private void addListenerToValueTextBox(JTextField textbox, final Card aCard) {
		textbox.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (aCard.validateCardValue()) {
					aCard.setCardValid();
				} else {
					aCard.setCardInvalid();
				}
			}
		});
	}

	/**
	 * add selection listener for the card. Notify the parent panel when is card
	 * is selected
	 */
	private void addSelectionListener(final Card aCard) {
		aCard.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (!isSelected) {
					// selecting the card
					isSelected = true;
					selectCard();
				} else {
					// card is being unseleted
					isSelected = false;
					unselectCard();
				}
			}
		});
	}

	/**
	 * Selecting the card and update the vote in the parent panel
	 */
	private void selectCard() {
		// highlight the card
		setCardSelected();
		// update the vote
		parentPanel.addRequirementValue(this);
	}

	/**
	 * Unselecting the card and update the vote in the parent panel
	 */
	private void unselectCard() {
		// remove the highlight
		setCardValid();
		// update the vote
		parentPanel.subtractRequirementValue(this);
	}

	/**
	 * Allows a card to be closed
	 * 
	 * @param closeButton
	 * @param card
	 *            object
	 */
	private void addListenerToCloseButton(JButton closeButton, final Card aCard) {
		closeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				aCard.discardThisCard();
			}
		});

	}

	/**
	 * Discard the card by hiding it from the view
	 */
	private void discardThisCard() {
		this.setVisible(false);
	}

	/**
	 * adds mouse over feature to the card
	 * 
	 * @param Card
	 *            object
	 */
	private void addMouseoverHightlight(JComponent item, final Card aCard) {
		item.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// card existed
				aCard.isMouseovered = false;
				changeCardLayout();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// highlight the card
				aCard.isMouseovered = true;
				changeCardLayout();

			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
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

	/**
	 * return what mode the card is on
	 * 
	 * @return mode the card is on
	 */
	public CardDisplayMode getMode() {
		return this.mode;
	}
	
	/**
	 * setter for isSelected
	 * @param isSelected
	 */
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	/** 
	 * getter for cardValue
	 */
	public int getCardValue() {
		return cardValue;
	}
}
