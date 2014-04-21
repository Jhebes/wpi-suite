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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerDeck;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.CreateNewDeckPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.pokers.Card;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Controller that creates a new deck when users click 
 * a button that sets this controller as action listener
 */
public class CreateNewDeckController implements ActionListener {
	/** A view that exhibits the deck */
	private CreateNewDeckPanel view;
	
	/**
	 * Construct the controller by storing the given CreateNewDeckPanel
	 * @param deckPanel A CreateNewDeckPanel that has the deck to be created
	 */
	public CreateNewDeckController(CreateNewDeckPanel deckPanel) {
		this.view = deckPanel;
	}

	/**
	 * Stored the deck in the database
	 * {@inheritDoc}
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// String deckName = this.view.getTextboxName().getText();

		// make sure all cards are validated
		if (validateAllInputs()) {
			// all inputs are good
			String deckName = this.view.getTextboxName().getText();
			ArrayList<Integer> cardValues = this.view.getAllCardsValue();

			PlanningPokerDeck deck = new PlanningPokerDeck(deckName, cardValues);

			// send a request
			final Request request = Network.getInstance().makeRequest(
					"planningpoker/deck", HttpMethod.PUT);
			// Set the data to be the session to save (converted to JSON)
			request.setBody(deck.toJSON());
			// Listen for the server's response
			request.addObserver(new AddDeckRequestObserver(this));
			// Send the request on its way
			request.send();
			// System.out.println("DeckName is: " + deckName);
			// System.out.println("Card value: " + deckName.toString());

		} else {
			// inputs are not valid
			this.view.repaint();
		}

	}

	/**
	 * TODO WHAT DOES THIS FUNCTION SAY?
	 * Removes the tab
	 * @param deck
	 */
	public void onSuccess(PlanningPokerDeck deck) {
		// close the tab
		// this.view.getInvokingPanel().setupDeckDropdown();
		// InitNewDeckPanelController.getInstance(null).removeDeckPanel();
	}

	/**
	 * Validate all the inputs by avoiding java's short-circuit boolean
	 * evaluation
	 * 
	 * @return true if valid; false otherwise
	 */
	private boolean validateAllInputs() {
		boolean areCardsValid = validateCardValues();
		boolean isNameEntered = this.view.isDeckNameEntered();
		return areCardsValid && isNameEntered;
	}

	/**
	 * Validate all the values for the entire deck of cards
	 * 
	 * @return true if so; false otherwise
	 */
	private boolean validateCardValues() {
		boolean isAllInputValid = true;

		Map<Integer, Card> cards = this.view.getCards();
		for (Card aCard : cards.values()) {
			if (!aCard.validateCardValue()) {
				aCard.setCardInvalid();
				isAllInputValid = false;
			} else {
				aCard.setCardValid();
			}
		}
		return isAllInputValid;
	}
}
