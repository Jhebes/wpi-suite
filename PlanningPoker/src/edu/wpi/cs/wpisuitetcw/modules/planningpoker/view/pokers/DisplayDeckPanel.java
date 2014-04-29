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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerDeck;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.characteristics.CardDisplayMode;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.session.VotePanel;

public class DisplayDeckPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// constants
	private static final int CENTER_PANEL_WIDTH = 350;
	private static final int CENTER_PANEL_HEIGHT = 200;

	// panels for setting up the view
	private PlanningPokerDeck deck;
	private final JPanel cardPanel;
	private final JScrollPane centerPanel;
	private final JPanel container;

	// partent panel
	private VotePanel parentPanel;

	/** cards that are highlighted */
	private List<Card> cards = new ArrayList<Card>();

	// vote value for the deck
	private int voteValue = 0;

	/**
	 * Constructor - creating a panel for displaying a deck of card
	 */
	public DisplayDeckPanel(PlanningPokerDeck deck,
			VotePanel progressPanel) {
		this.parentPanel = progressPanel;
		// setup panel
		container = new JPanel();
		cardPanel = new JPanel();

		container.setLayout(new GridBagLayout());
		container.add(cardPanel);

		centerPanel = new JScrollPane(container);
		centerPanel.setMinimumSize(new Dimension(CENTER_PANEL_WIDTH,
				CENTER_PANEL_HEIGHT));

		// initialize the deck
		this.deck = deck;

		displayDeck();

		// setup the entire panel
		this.add(centerPanel);

	}

	/**
	 * Display a deck of cards for voting.
	 */
	private void displayDeck() {
		for (int value : deck.getDeck()) {
			Card aCard = new Card(CardDisplayMode.DISPLAY, value, this);
			cardPanel.add(aCard);
		}
	}

	/**
	 * Determine if the deck allows multiple selection
	 * 
	 * @return true if deck allows multiple selection
	 */
	private boolean isSingleSelection() {
		System.out.println("Deck selection: " + deck.getMaxSelection());
		return deck.getMaxSelection() == 1;
	}

	/**
	 * update the vote by adding the given value
	 */
	public void addRequirementValue(Card aCard) {
		if (isSingleSelection()) {
			// only one card is able to be selected
			voteValue = aCard.getCardValue();
			// removes highlighted card
			removeHighlight();

		} else {
			voteValue += aCard.getCardValue();
			parentPanel.setVoteTextFieldWithValue(voteValue);
		}
		cards.add(aCard);
	}

	/**
	 * update the vote by subtracting the given value
	 */
	public void subtractRequirementValue(Card aCard) {
		if (isSingleSelection()) {
			// vote is reset to 0
			voteValue = 0;
			// removes highlighted card
			removeHighlight();

		} else {
			voteValue -= aCard.getCardValue();
		}
		cards.add(aCard);
		// parentPanel.setVoteTextFieldWithValue(voteValue);
	}

	/**
	 * removes the highlight for the card
	 */
	public void removeHighlight() {

		for (int i = 0; i < cards.size(); i++) {
			Card aCard = cards.get(i);
			cards.remove(i);

			aCard.setCardValid();
			aCard.setSelected(false);

		}
	}

	/**
	 * 
	 * @return estimate for requirement
	 */
	public int getVoteValue() {
		return voteValue;
	}
}
