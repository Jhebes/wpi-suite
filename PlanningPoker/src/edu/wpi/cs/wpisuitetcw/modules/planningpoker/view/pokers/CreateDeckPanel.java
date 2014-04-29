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

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.AddNewCardController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.GetAllDecksController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.InitNewDeckPanelController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerDeck;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.characteristics.CardDisplayMode;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.session.CreateSessionPanel;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;

/**
 * A view to create a new deck
 */
public class CreateDeckPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	// ########################### CONSTANTS ##############################
	private static final String NO_DECK_MSG = "<html><font color='red'>You will be entering a value when voting on a requirement.</font></html>";
	private final String TEXTBOX_PLACEHOLDER = "Deck "
			+ new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar
					.getInstance().getTime());
	private final String NO_CARD_ERR_MSG = "<html><font color='red'>A deck must contain </br >at least one card. </font></html>";
	private final String DECK_NAME_LABEL = "Name *";
	private final String CARD_COUNT_LABEL = "# of Cards: ";
	private final String ADD_CARD_LABEL = "[+] New Card";
	private final String CARD_SELECTION_LABEL = "Card selection *";
	private static final String MULTIPLE_SELECT = "Multiple selection";
	private static final String SINGLE_SELECT = "Single selection";
	private static final String DEFAULT_DECK = "Default";
	private static final int CARD_WIDTH = 146;
	private static final int CENTER_PANEL_WIDTH = 350;
	private static final int CENTER_PANEL_HEIGHT = 250;
	private static final int BUTTON_HEIGHT = 26;

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

	/** Create session panel for error indication */
	private CreateSessionPanel sessionPanel;

	// subject to change
	// private final JTextField textboxVal;

	/**
	 * This is the constructor for deck panel that allows creation of a new deck
	 * of cards. The session panel is given for dynamic error validation and
	 * indication
	 * 
	 * @param mode
	 * @param sessionPanel
	 */
	public CreateDeckPanel(CardDisplayMode mode, CreateSessionPanel sessionPanel) {
		this(mode);
		this.sessionPanel = sessionPanel;
	}

	/**
	 * This constructor for deck panel is the basic one for display a deck of
	 * cards
	 * 
	 * @param mode
	 *            DISPLAY or NO_DECK
	 */
	public CreateDeckPanel(CardDisplayMode mode) {
		// Assign mode for the panel
		this.mode = mode;

		cards = new HashMap<Integer, Card>();

		setupTopPanel();

		// add a new panel to the scrollpane, since cards are panels which
		// cannot be drawn on scrollpane directly
		cardPanel = new JPanel();
		cardPanel.add(errorPanel);

		final JPanel container = new JPanel();
		container.setLayout(new GridBagLayout());
		container.add(cardPanel);

		centerPanel = new JScrollPane(container);
		centerPanel.setMinimumSize(new Dimension(CENTER_PANEL_WIDTH,
				CENTER_PANEL_HEIGHT));

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
	 * Construct the UI components of the top panel: text field of deck name,
	 * dropdown for card selection mode, button to add new card, number of card
	 * labels, and JLabels associated with each component above
	 */
	private void setupTopPanel() {
		topPanel = new JPanel();

		// Create text field for deck's name
		labelName = new JLabel(DECK_NAME_LABEL);
		textboxName = new JTextField(18);
		textboxName.setText(TEXTBOX_PLACEHOLDER);

		// Create card selection dropdown
		labelCardSelection = new JLabel(CARD_SELECTION_LABEL);
		deckOption = new JComboBox<String>();
		deckOption.addItem(SINGLE_SELECT);
		deckOption.addItem(MULTIPLE_SELECT);

		// Create a label to keep track number of card
		labelCount = new JLabel(CARD_COUNT_LABEL);
		labelNumCards = new JLabel("1");

		// Create add card button and bind an action listener to it
		btnAddCard = new JButton(ADD_CARD_LABEL);
		btnAddCard.addActionListener(new AddNewCardController(this));

		// Create Error Panel
		errorPanel = new JPanel();
		errorPanel.add(new JLabel(NO_CARD_ERR_MSG));
		errorPanel.setVisible(false);

		// Add sets of buttons that modify the deck to a panel
		addModifyDeckButtons(topPanel);
		addTextInputValidation(textboxName);
	}

	/**
	 * Display a panel with message when no deck of deck is selected to display
	 */
	private void displayNoDeckPanel() {
		// clear the screen
		this.removeAll();

		// panel for display a message
		final JPanel msgPanel = new JPanel();
		msgPanel.setLayout(new MigLayout());

		// No deck message
		final JLabel msgLabel = new JLabel(NO_DECK_MSG, JLabel.CENTER);

		msgPanel.add(msgLabel, "center");
		this.add(msgLabel, "dock center");

	}

	/**
	 * Add a initial card to the panel
	 */
	private void setInitialCard() {
		// cards
		final Card starterCard = new Card(this.mode, this);
		final int key = starterCard.hashCode();
		cards.put(key, starterCard);
		this.addRemoveCardListener(starterCard, this);
		this.cardPanel.add(starterCard);
	}

	/**
	 * Add a new card to both the storing hashmap and the view
	 */
	public void addNewCard() {
		final Card aCard = new Card(this.mode, this);
		final int key = aCard.hashCode();
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
		final Map<Integer, Card> map = this.cards;
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
	public void addAction(JButton button, final CreateDeckPanel panel) {
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
	public List<Integer> getNewDeckValues() {
		final List<Integer> cardValues = new ArrayList<Integer>();
		final Map<Integer, Card> map = this.cards;
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
		if (textboxName.getText().equals("")) {
			// nothing is entered
			Logger.getLogger("PlanningPoker").log(Level.FINE,
					"Name not entered");
			return false;
		} else {
			Logger.getLogger("PlanningPoker").log(Level.FINE, "Name entered");
			return true;
		}
	}

	/**
	 * display the default fibonacci deck
	 */
	public void displayDefaultDeck() {
		// clear the panel
		removeAllCard();
		// display default deck
		final int[] defaultDeck = { 0, 1, 1, 2, 3, 5, 8, 13 };
		for (int i = 0; i < defaultDeck.length; i++) {
			Card aCard = new Card(mode, defaultDeck[i]);
			int key = aCard.hashCode();
			cards.put(key, aCard);
			this.addRemoveCardListener(aCard, this);
			cardPanel.add(aCard);
			validateNumCards();
			this.updateNumCard();
		}

		this.textboxName.setText(DEFAULT_DECK);
		this.updateUI();
	}

	/**
	 * Displays a selected deck
	 * 
	 * @param deckName
	 *            Name of the deck to be shown
	 * @throws WPISuiteException
	 */
	public void displayDeck(String deckName) throws WPISuiteException {
		// clear the panel
		removeAllCard();
		// display default deck

		final PlanningPokerDeck deck = GetAllDecksController.getInstance()
				.getDeckByName(deckName);

		final List<Integer> deckValues = deck.getDeck();
		for (int value : deckValues) {
			Card aCard = new Card(mode, value);
			int key = aCard.hashCode();
			cards.put(key, aCard);
			this.addRemoveCardListener(aCard, this);
			cardPanel.add(aCard);
			validateNumCards();
			this.updateNumCard();
		}

		// set other instance variables
		this.textboxName.setText(deck.getDeckName());

		this.deckOption.removeAllItems();
		final int selection = deck.getMaxSelection();

		if (selection == 1) {
			this.deckOption.addItem(SINGLE_SELECT);
		} else {
			this.deckOption.addItem(MULTIPLE_SELECT);
		}

		this.updateUI();
	}

	/**
	 * notify createNewDeckPanel when a Card is discarded, so that it removes
	 * the card from the cards HashMap
	 */
	public void addRemoveCardListener(Card aCard, final CreateDeckPanel panel) {
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
				final Card aCard = (Card) e.getComponent();
				Logger.getLogger("PlanningPoker").log(Level.INFO,
						"Card removed");
				panel.removeCardWithKey(aCard.hashCode());
				panel.updateUI();
				// validate all inputs in the create session panel
				sessionPanel.checkSessionValidation();
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
	 * @return a list of card values
	 */
	public List<Integer> getAllCardsValue() {
		final List<Integer> deckValues = new ArrayList<Integer>();
		final Map<Integer, Card> map = this.cards;
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
	public Map<Integer, Card> getCards() {
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
		centerTopPanel.setLayout(new MigLayout("inset 20 20 20 20, fill",
											   "push[]push[]push[]push", ""));

		// 1ST ROW
		centerTopPanel.add(labelName, "gapleft 5, left");
		centerTopPanel.add(labelCardSelection, "gapleft 5, left");
		centerTopPanel.add(labelCount, "gapleft 5, split2, center");
		centerTopPanel.add(labelNumCards, "wrap");

		// 2ND ROW
		centerTopPanel.add(textboxName, "gapleft 5, left, height " + BUTTON_HEIGHT + "px!");
		centerTopPanel.add(deckOption, "gapleft 5, left, height " + BUTTON_HEIGHT + "px!");
		centerTopPanel.add(btnAddCard, "gapleft 5, center, height " + BUTTON_HEIGHT + "px!");
	}

	/**
	 * Return the number of cards that can be selected
	 * 
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
	
	/**
	 * Trigger dynamic input validation when the given input is entered in the
	 * given textfield
	 */
	private void addTextInputValidation(JTextComponent element) {
		element.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				sessionPanel.checkSessionValidation();
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
	}
	

	/**
	 * @return create session panel
	 */
	public CreateSessionPanel getSessionPanel() {
		return sessionPanel;
	}

}
