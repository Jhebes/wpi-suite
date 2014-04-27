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

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerDeck;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * A singleton controller that gets all the decks or their names
 * from the database
 */
public class GetAllDecksController {
	/** A list of PlanningPokerDeck */
	private List<PlanningPokerDeck> decks = null;

	/** An instance of this controller */
	private static GetAllDecksController instance = null;

	private GetAllDecksController() {
	}

	/**
	 * Instantiates a new controller tied to the specified view. Private because
	 * this is a singleton.
	 */
	public static GetAllDecksController getInstance() {
		if (instance == null) {
			instance = new GetAllDecksController();
		}
		return instance;
	}

	/**
	 * Assign the given List of PlanningPokerDecks to the 
	 * GetAllDecksController's 
	 * @param decks A List of PlanningPokerDeck that would be
	 * assigned to the GetAllDecksController
	 */
	public void updateDecks(List<PlanningPokerDeck> decks) {
		this.decks = decks;
	}

	/**
	 * Returns all the names of the available decks in the database
	 * This method is used to exhibit the names of the deck in the
	 * dropdown of the CreateNewSessionPanel
	 * @return Returns an array list of all the names 
	 * of the available decks in the database
	 * @throws InterruptedException
	 */
	public List<String> getAllDeckNames() {
		this.refreshDecks(); // set up the deck
		final List<String> deckNames = new ArrayList<String>();

		// delay the this process since the request fired in refreshDecks()
		// might not be completed
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		
		// Default options
		deckNames.add("Default");
		deckNames.add("Create new deck");
		deckNames.add("No deck");

		// make sure the decks is not null
		if (decks != null) {
			for (PlanningPokerDeck deck : this.decks) {
				deckNames.add(deck.getDeckName());
			}
		}
		return deckNames;
	}

	/**
	 * Send a request to get all the decks back from the database
	 */
	public void refreshDecks() {
		final Request request = Network.getInstance().makeRequest(
				"planningpoker/deck", HttpMethod.GET);
		request.addObserver(new GetAllDecksRequestObserver(this));
		request.send();
	}

	/**
	 * Return the deck that has the given name
	 * @param deckName A String represents the name of a deck
	 * @return Return the deck that has the given name
	 * @throws WPISuiteException
	 */
	public PlanningPokerDeck getDeckByName(String deckName)
			throws WPISuiteException {
		for (PlanningPokerDeck d : decks) {
			if (d.getDeckName() == deckName) {
				return d;
			}
		}
		throw new WPISuiteException();
	}

}
