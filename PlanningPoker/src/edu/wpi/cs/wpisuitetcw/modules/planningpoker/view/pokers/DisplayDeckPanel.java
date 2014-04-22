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

import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerDeck;

public class DisplayDeckPanel extends JPanel {
	private final PlanningPokerDeck deck;

	/**
	 * Constructor - creating a panel for displaying a deck of card
	 */
	public DisplayDeckPanel(PlanningPokerDeck deck) {
		//
		System.out.println(deck);
		this.deck = deck;
		displayDeck();
	}

	/**
	 * Display a deck of cards for voting.
	 */
	public void displayDeck() {
		for (int value : this.deck.getDeck()) {
			// TODO: implement
		}
	}

}
