/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.AddNewCardController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.InitNewDeckPanelController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.pokers.Card;

/**
 * A view to create a new deck
 */
public class CreateNewDeckPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	// constants
	private final String TEXTBOX_PLACEHOLDER = "Deck "
			+ new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar
					.getInstance().getTime());
	private final String NAME_ERR_MSG = "<html><font color='red'>REQUIRES</font></html>";
	private final String NO_CARD_ERR_MSG = "<html><font color='red'>A deck must contain </br >at least one card. </font></html>";
	private final int CARD_WIDTH = 146;
	private final String CARD_COUNT_LABEL = "# of Cards: ";
	private final String ADD_CARD_LABEL = "[+] New Card";
	private final String CARD_SELECTION_LABEL = "Selection type *";
	// private final String CREATE_LABEL_STRING = "Create";
	// private final String CANCEL_LABEL_STRING = "Cancel";
	private final String DECK_NAME_LABEL = "Name *";

	// instance fields
	private final JLabel labelName;
	private final JLabel labelCount;
	private final JLabel labelNumCards;
	private final JLabel labelNameErr;
	private final JLabel labelCardSelection;
	private final JButton btnAddCard;
	// private final JButton btnCreate;
	// private final JButton btnCancel;
	private final JTextField textboxName;
	private final JPanel topPanel;
	private final JPanel centerPanel;
	private final JPanel bottomPanel;
	private final HashMap<Integer, Card> cards;
	private final JComboBox<String> deckOption;

	private final JPanel cardPanel;
	private final JScrollPane cardSP;
	private final JPanel errorPanel;

	// subject to change
	// private final JTextField textboxVal;

	public CreateNewDeckPanel() {

		// sub panels
		topPanel = new JPanel();
		centerPanel = new JPanel();
		bottomPanel = new JPanel();
		errorPanel = new JPanel();

		errorPanel.add(new JLabel(NO_CARD_ERR_MSG));
		errorPanel.setVisible(false);

		cards = new HashMap<Integer, Card>();

		// text labels
		this.labelCardSelection = new JLabel(CARD_SELECTION_LABEL);
		this.labelName = new JLabel(DECK_NAME_LABEL);
		this.labelCount = new JLabel(CARD_COUNT_LABEL);
		this.labelNumCards = new JLabel("1");
		this.labelNameErr = new JLabel(NAME_ERR_MSG);

		this.labelNameErr.setVisible(false);

		// dropdown
		this.deckOption = new JComboBox<String>();
		deckOption.addItem("Single select");
		deckOption.addItem("Multiple select");

		// textfields
		this.textboxName = new JTextField(18);
		this.textboxName.setText(TEXTBOX_PLACEHOLDER);

		// cards
		Card starterCard = new Card();
		int key = starterCard.hashCode();
		cards.put(key, starterCard);
		this.addRemoveCardListener(starterCard, this);

		// buttons
		this.btnAddCard = new JButton(ADD_CARD_LABEL);
		// this.btnCreate = new JButton(CREATE_LABEL_STRING);
		// this.btnCancel = new JButton(CANCEL_LABEL_STRING);

		// action listeners
		btnAddCard.addActionListener(new AddNewCardController(this));
		// btnCreate.addActionListener(new CreateNewDeckController(this));
		// this.addAction(btnCancel, this);

		// set up the top panel
		// JPanel topContainer = new JPanel();
		// topContainer.setLayout(new MigLayout());
		// topContainer.add(labelNameErr, "center, span, split3");
		// topContainer.add(labelName);
		// topContainer.add(textboxName);
		// topPanel.add(topContainer);

		// setup centerPanel
		// centerPanel includes the add button and additional cards
		centerPanel.setLayout(new MigLayout());

		// sub panels
		JPanel centerTopPanel = new JPanel();
		centerTopPanel.setLayout(new MigLayout());

		// setup top panel for input
		centerTopPanel.add(labelNameErr, "center, span, split3");
		centerTopPanel.add(labelName);
		centerTopPanel.add(textboxName, "wrap");

		centerTopPanel.add(labelCardSelection, "center");
		centerTopPanel.add(deckOption, "wrap");
		centerTopPanel.add(btnAddCard, "center, split3");
		centerTopPanel.add(labelCount);
		centerTopPanel.add(labelNumCards);

		// add a new panel to the scrollpane, since cards are panels which
		// cannot be drawn on scrollpane directly
		cardPanel = new JPanel();

		JPanel container = new JPanel();
		container.setLayout(new GridBagLayout());

		cardSP = new JScrollPane(container);

		// removes cards
		cardPanel.add(errorPanel);
		cardPanel.add(starterCard);
		container.add(cardPanel);

		// add sub panels to center panels
		centerPanel.add(centerTopPanel, "dock north, center");
		centerPanel.add(cardSP, "dock center");

		// setup bottomPanel
		// bottomPanel.add(btnCreate);
		// bottomPanel.add(btnCancel);

		// setup the entire layout
		this.setLayout(new MigLayout("", "", ""));
		this.add(topPanel, "dock north");
		this.add(centerPanel, "dock center");
		this.add(bottomPanel, "center, dock south");
	}

	// /**
	// * return the invoking panel
	// *
	// * @return the invoking panel
	// */
	// public CreateSessionPanel getInvokingPanel() {
	// return invokingPanel;
	// }

	/**
	 * Add a new card to both the storing hashmap and the view
	 */
	public void addNewCard() {
		Card aCard = new Card();
		int key = aCard.hashCode();
		cards.put(key, aCard);
		this.addRemoveCardListener(aCard, this);

		this.cardPanel.add(aCard);
		validateNumCards();
		this.updateNumCard();

		// This yet moves to the rightmost position when a new card is added.
		this.cardSP.getHorizontalScrollBar().setValue(
				(int) (this.cardPanel.getBounds().getWidth() + CARD_WIDTH));
	}

	/**
	 * Remove a card from the view and the hashmap
	 */
	public void removeCardWithKey(int key) {
		System.out.println("Executed");
		cards.remove(key);

		validateNumCards();
		updateNumCard();
	}

	/**
	 * check the number of cards in the panel, render proper error message if
	 * necessary
	 */
	private void validateNumCards() {
		if (this.cards.size() == 0) {
			// this.btnCreate.setEnabled(false);
			// display error message
			errorPanel.setVisible(true);

		} else {
			// this.btnCreate.setEnabled(true);
			errorPanel.setVisible(false);
		}
	}

	/**
	 * update the total number of cards
	 */
	private void updateNumCard() {
		this.labelNumCards.setText(Integer.toString(this.cards.size()));
	}

	/**
	 * Adds listener to button
	 * 
	 * @param button
	 * @param panel
	 */
	public void addAction(JButton button, final CreateNewDeckPanel panel) {
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				InitNewDeckPanelController.getInstance(null).removeDeckPanel();
			}
		});
	}

	/**
	 * This function extracts all values from textboxes
	 * 
	 * @return an array list with the card values user enters
	 */
	public ArrayList<Integer> getNewDeckValues() {
		ArrayList<Integer> cardValues = new ArrayList<Integer>();
		Map<Integer, Card> map = this.cards;
		for (Card aCard : map.values()) {
			cardValues.add(Integer.parseInt(aCard.getTxtboxValue().getText()));
		}
		return cardValues;
	}

	/**
	 * determine if the deck name textbox is empty
	 * 
	 * @return true if so; false otherwise
	 */
	public boolean isDeckNameEntered() {
		System.out.println("Hi here.");
		if (this.textboxName.getText().equals("")) {
			// nothing is entered
			System.out.println("Name not entered");
			this.labelNameErr.setVisible(true);
			return false;
		} else {
			System.out.println("Name entered");
			this.labelNameErr.setVisible(false);
			return true;
		}
	}

	// to be deleted
	/**
	 * puts indicators for users to identify non-integer card values
	 */
	public void informInvalidCardValue() {
		System.out.println("It's working!");
	}

	// /**
	// * Confirm that all values entered in textboxes are integers
	// */
	// public boolean validateCardValues() {
	// // TODO implement this or equivalent validater
	// return false;
	// }

	/**
	 * notify createNewDeckPanel when a Card is discarded, so that it removes
	 * the card from the cards HashMap
	 */
	public void addRemoveCardListener(Card aCard, final CreateNewDeckPanel panel) {
		// remove a card
		aCard.addComponentListener(new ComponentListener() {

			@Override
			public void componentShown(ComponentEvent e) {
			}

			@Override
			public void componentResized(ComponentEvent e) {
			}

			@Override
			public void componentMoved(ComponentEvent e) {
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				Card aCard = (Card) e.getComponent();
				System.out.println("Card removed");
				panel.removeCardWithKey(aCard.hashCode());
				panel.updateUI();
			}
		});
	}

	/**
	 * @return the textbox field for name of the deck
	 */
	public JTextField getTextboxName() {
		return textboxName;
	}

	/**
	 * get a deck of cards with values
	 * 
	 * @return an arraylist of card values
	 */
	public ArrayList<Integer> getAllCardsValue() {
		ArrayList<Integer> deckValues = new ArrayList<Integer>();
		Map<Integer, Card> map = this.cards;
		for (Card aCard : map.values()) {
			deckValues.add(aCard.getValue());
		}
		return deckValues;
	}

	/**
	 * 
	 * @return the center panel of this deck creation instance
	 */
	public JPanel getCenterPanel() {
		return this.centerPanel;
	}

	/**
	 * @return a hashmap of cards
	 */
	public HashMap<Integer, Card> getCards() {
		return cards;
	}
}
