/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers;

import java.util.List;
import java.util.Map;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerDeck;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.pokers.Card;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.session.tabs.SessionDeckPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Controller that creates a new deck when users click 
 * a button that sets this controller as action listener
 */
public class CreateNewDeckController {
	/** A view that exhibits the deck */
	private final SessionDeckPanel view;
	
	/**
	 * Construct the controller by storing the given CreateNewDeckPanel
	 * @param deckPanel A CreateNewDeckPanel that has the deck to be created
	 */
	public CreateNewDeckController(SessionDeckPanel deckPanel) {
		view = deckPanel;
	}

	/**
	 * Create a new deck based on the information in the CreateNewDeckPanel 
	 * and store it in the database
	 */
	public void addDeckToDatabase() {
		// make sure all cards are validated
		if (hasValidInputs()) {
			// all inputs are good
			final String deckName = view.getTextboxName().getText();
			final List<Integer> cardValues = view.getAllCardsValue();
			final int maxSelection = view.getMaxSelectionCards();
			final PlanningPokerDeck deck = new PlanningPokerDeck(deckName, cardValues, maxSelection);

			// send a request
			final Request request = Network.getInstance().makeRequest(
					"planningpoker/deck", HttpMethod.PUT);
			// Set the data to be the session to save (converted to JSON)
			request.setBody(deck.toJSON());
			// Listen for the server's response
			request.addObserver(new AddDeckRequestObserver(this));
			// Send the request on its way
			request.send();

		} else {
			// inputs are not valid
			view.repaint();
		}

	}

	/**
	 * Validate all the inputs by avoiding java's short-circuit boolean
	 * evaluation
	 * 
	 * @return true if valid; false otherwise
	 */
	private boolean hasValidInputs() {
		final boolean areCardsValid = hasValidCardValues();
		final boolean isNameEntered = view.isDeckNameEntered();
		return areCardsValid && isNameEntered;
	}

	/**
	 * Validate all the values for the entire deck of cards
	 * 
	 * @return true if so; false otherwise
	 */
	private boolean hasValidCardValues() {
		boolean isAllInputValid = true;

		final Map<Integer, Card> cards = view.getCards();
		for (Card aCard : cards.values()) {
			if (!aCard.hasValidCardValue()) {
				aCard.markCardInvalid();
				isAllInputValid = false;
			} else {
				aCard.markCardValid();
			}
		}
		return isAllInputValid;
	}
}
