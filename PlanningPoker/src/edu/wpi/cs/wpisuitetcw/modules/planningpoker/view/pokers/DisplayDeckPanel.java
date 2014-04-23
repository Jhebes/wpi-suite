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

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerDeck;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.characteristics.CardDisplayMode;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.SessionInProgressPanel;

public class DisplayDeckPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// constants
	private static final int CENTER_PANEL_WIDTH = 350;
	private static final int CENTER_PANEL_HEIGHT = 250;

	// panels for setting up the view
	private PlanningPokerDeck deck;
	private final JPanel cardPanel;
	private final JScrollPane centerPanel;
	private final JPanel container;

	// partent panel
	private SessionInProgressPanel parentPanel;

	// vote value for the deck
	private int voteValue = 0;

	/**
	 * Constructor - creating a panel for displaying a deck of card
	 */
	public DisplayDeckPanel(PlanningPokerDeck deck,
			SessionInProgressPanel progressPanel) {
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
	public void addRequirementValue(int value) {
		if (isSingleSelection()) {
			// only one card is able to be selected
			voteValue = value;
		} else {
			voteValue += value;
		}
	}

	/**
	 * update the vote by subtracting the given value
	 */
	public void subtractRequirementValue(int value) {
		if (isSingleSelection()) {
			// vote is reset to 0
			voteValue = 0;
		} else {
			voteValue -= value;
		}
	}
	
	public int getVoteValue() {
		return voteValue;
	}
}
