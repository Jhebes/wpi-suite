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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.characteristics.CardDisplayMode;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.pokers.Card;

/**
 * A view to create a new deck
 */
public class CreateNewDeckPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	// ########################### CONSTANTS ##############################
	private static final String NO_DECK_MSG = 
			"<html><font color='red'>You will be entering a value when voting on a requirement.</font></html>";
	private final String TEXTBOX_PLACEHOLDER = "Deck "
			+ new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
	private final String NAME_ERR_MSG = 
			"<html><font color='red'>REQUIRES</font></html>";
	private final String NO_CARD_ERR_MSG = 
			"<html><font color='red'>A deck must contain </br >at least one card. </font></html>";
	private final String DECK_NAME_LABEL 		= "Name *";
	private final String CARD_COUNT_LABEL 		= "# of Cards: ";
	private final String ADD_CARD_LABEL 		= "[+] New Card";
	private final String CARD_SELECTION_LABEL	= "Card selection *";
	private static final String MULTIPLE_SELECT = "Multiple selection";
	private static final String SINGLE_SELECT 	= "Single selection";
	private final int CARD_WIDTH = 146;
	private static final int CENTER_PANEL_WIDTH  = 350;
	private static final int CENTER_PANEL_HEIGHT = 250;

	// ########################### Top UI Components ######################
	/** A container holding all the top UI components */ 
	private JPanel topPanel;
	
	/** Text field to type deck's name in */
	private JLabel labelName;
	private JTextField textboxName;

	/** Dropdown to choose single OR multiple card selection */
	private JLabel labelCardSelection;
	private JComboBox<String> deckOption;

	/** Group of button and labels to add new card */
	private JButton btnAddCard;
	private JLabel labelCount;
	private JLabel labelNumCards;
	private JLabel labelNameErr;
	
	// ######################### Center UI Components #####################
	/** A container holding all the center UI component */
	private JScrollPane centerPanel;

	/** A scroll panel having all the cards */
	private JPanel cardPanel;
	private JPanel errorPanel;
	
	// ############################### DATA ###############################
	private final HashMap<Integer, Card> cards;
	
	/** Mode for the panel */
	private final CardDisplayMode mode;

	// subject to change
	// private final JTextField textboxVal;
	public CreateNewDeckPanel(CardDisplayMode mode) {
		// Assign mode for the panel
		this.mode = mode;
		
		cards = new HashMap<Integer, Card>();

		setupTopPanel();

		// add a new panel to the scrollpane, since cards are panels which
		// cannot be drawn on scrollpane directly
		cardPanel = new JPanel();
		cardPanel.add(errorPanel);

		JPanel container = new JPanel();
		container.setLayout(new GridBagLayout());
		container.add(cardPanel);
		
		centerPanel = new JScrollPane(container);
		centerPanel.setMinimumSize(new Dimension(CENTER_PANEL_WIDTH, CENTER_PANEL_HEIGHT));		

		// setup the entire layout
		this.setLayout(new MigLayout("insets 0", "", ""));
		this.add(topPanel, "dock north");
		this.add(centerPanel, "dock center");	
		
		// determine what type of mode the panel is
		if (mode.equals(CardDisplayMode.CREATE)) {
			// create mode allows users to enter values
			setInitialCard();
		} else if (mode.equals(CardDisplayMode.DISPLAY)) {
			// display mode should display the cards in a deck and disallow any
			// modification
			disableAllInputFields();
		} else if (mode.equals(CardDisplayMode.NO_DECK)) {
			displayNoDeckPanel();
		}
	}

	/*
	 * Construct the UI components of the top panel:
	 * text field of deck name, dropdown for card selection mode,
	 * button to add new card, number of card labels, and JLabels
	 * associated with each component above
	 */
	private void setupTopPanel() {
		topPanel = new JPanel();

		// Create text field for deck's name
		this.labelName = new JLabel(DECK_NAME_LABEL);
		this.textboxName = new JTextField(18);
		this.textboxName.setText(TEXTBOX_PLACEHOLDER);
		
		// Create card selection dropdown
		this.labelCardSelection = new JLabel(CARD_SELECTION_LABEL);
		this.deckOption = new JComboBox<String>();
		deckOption.addItem(SINGLE_SELECT);
		deckOption.addItem(MULTIPLE_SELECT);

		// Create a label to keep track number of card
		this.labelCount = new JLabel(CARD_COUNT_LABEL);
		this.labelNumCards = new JLabel("1");

		// Create a label to inform invalid deck name
		this.labelNameErr = new JLabel(NAME_ERR_MSG);
		this.labelNameErr.setVisible(false);

		// Create add card button and bind an action listener to it
		this.btnAddCard = new JButton(ADD_CARD_LABEL);
		btnAddCard.addActionListener(new AddNewCardController(this));
		
		// Create Error Panel
		errorPanel = new JPanel();
		errorPanel.add(new JLabel(NO_CARD_ERR_MSG));
		errorPanel.setVisible(false);

		// Add sets of buttons that modify the deck to a panel
		addModifyDeckButtons(topPanel);		
	}

	/**
	 * Display a panel with message when no deck of deck is selected to display
	 */
	private void displayNoDeckPanel() {
		// clear the screen
		this.removeAll();

		// panel for display a message
		JPanel msgPanel = new JPanel();
		msgPanel.setLayout(new MigLayout());

		// No deck message
		JLabel msgLabel = new JLabel(NO_DECK_MSG, JLabel.CENTER);

		msgPanel.add(msgLabel, "center");
		this.add(msgLabel, "dock center");

	}

	/**
	 * Add a initial card to the panel
	 */
	private void setInitialCard() {
		// cards
		Card starterCard = new Card(this.mode);
		int key = starterCard.hashCode();
		cards.put(key, starterCard);
		this.addRemoveCardListener(starterCard, this);
		this.cardPanel.add(starterCard);
	}

	/**
	 * Add a new card to both the storing hashmap and the view
	 */
	public void addNewCard() {
		Card aCard = new Card(this.mode);
		int key = aCard.hashCode();
		cards.put(key, aCard);
		this.addRemoveCardListener(aCard, this);

		this.cardPanel.add(aCard);
		validateNumCards();
		this.updateNumCard();

		// This yet moves to the rightmost position when a new card is added.
		this.centerPanel.getHorizontalScrollBar().setValue(
				(int) (this.cardPanel.getBounds().getWidth() + CARD_WIDTH));
	}

	/**
	 * Disables all input fields in the panel
	 */
	public void disableAllInputFields() {
		this.textboxName.setEnabled(false);
		this.deckOption.setEnabled(false);
		this.btnAddCard.setEnabled(false);
	}

	/**
	 * Remove a card from the view and the hashmap
	 */
	public void removeCardWithKey(int key) {
		cards.remove(key);
		validateNumCards();
		updateNumCard();
	}

	/**
	 * Removes all card from the panel
	 */
	public void removeAllCard() {
		Map<Integer, Card> map = this.cards;
		for (Card aCard : map.values()) {
			removeCardWithKey(aCard.hashCode());
		}
		this.updateUI();
	}

	/**
	 * check the number of cards in the panel, render proper error message if
	 * necessary
	 */
	private void validateNumCards() {
		if (this.cards.size() == 0) {
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

	/**
	 * TODO display the default fibonacci deck
	 */
	public void displayDefaultDeck() {
		// clear the panel
		removeAllCard();
		// display default deck
		int[] defaultDeck = { 0, 1, 1, 2, 3, 5, 8, 13 };
		for (int i = 0; i < defaultDeck.length; i++) {
			Card aCard = new Card(this.mode, defaultDeck[i]);
			int key = aCard.hashCode();
			cards.put(key, aCard);
			this.addRemoveCardListener(aCard, this);
			this.cardPanel.add(aCard);
			validateNumCards();
			this.updateNumCard();
		}
		this.updateUI();
	}

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
		Collections.sort(deckValues);
		return deckValues;
	}

	/**
	 * 
	 * @return the center panel of this deck creation instance
	 */
	public JScrollPane getCenterPanel() {
		return this.centerPanel;
	}

	/**
	 * @return a hashmap of cards
	 */
	public HashMap<Integer, Card> getCards() {
		return cards;
	}

	/*
	 * Add text field, dropdown card selection, create new card button, number
	 * of card label, and their corresponding labels to the given panel
	 * 	|labelName labelNameErr | labelCardSelection | labelCount labelNumCards |
	 * 	-------------------------------------------------------------------------
	 * 	|textboxName			| deckOption		 | 			btnAddCard		|
	 */
	private void addModifyDeckButtons(JPanel centerTopPanel) {
		// Set the layout for given panel
		centerTopPanel.setLayout(new MigLayout("", "push[]push[]push[]push", ""));

		// 1ST ROW
		centerTopPanel.add(labelName, "left, split2");
		centerTopPanel.add(labelNameErr, "center");
		centerTopPanel.add(labelCardSelection, "left");
		centerTopPanel.add(labelCount, "split2, center");
		centerTopPanel.add(labelNumCards, "wrap");
		
		// 2ND ROW
		centerTopPanel.add(textboxName, "left");
		centerTopPanel.add(deckOption, "left");
		centerTopPanel.add(btnAddCard, "center");
		
	}
	
	/**
	 * Return the number of cards that can be selected
	 * @return Return the number of cards that can be selected
	 */
	public int getMaxSelectionCards() {
		if (deckOption.getSelectedItem().equals(SINGLE_SELECT)) {
			return 1;
		} else if (deckOption.getSelectedItem().equals(MULTIPLE_SELECT)) { 
			return cards.values().size();
		} else {
			return 0;
		}
	}
}
