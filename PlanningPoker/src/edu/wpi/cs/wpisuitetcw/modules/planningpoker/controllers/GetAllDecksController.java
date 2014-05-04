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
import java.util.logging.Level;
import java.util.logging.Logger;

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
	/** constans */
	private static final String CREATE_DECK = "Create deck";
	private static final String DEFAULT_DECK = "Default";
	private static final String NO_DECK = "No deck";
	
	/** A list of PlanningPokerDeck */
	private List<PlanningPokerDeck> decks = null;

	/** An instance of this controller */
	private static GetAllDecksController instance = null;

	/**
	 * Constructs a controller to get all decks. Private because it's a singleton.
	 */
	private GetAllDecksController() {
	}

	/**
	 * Instantiates a new controller tied to the specified view. Private because
	 * this is a singleton.
	 * @return GetAllDecksController instance
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
	 */
	public List<String> getAllDeckNames() {
		this.refreshDecks(); // set up the deck
		final List<String> deckNames = new ArrayList<String>();

		// delay the this process since the request fired in refreshDecks()
		// might not be completed
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
			Logger.getLogger("GetAllDecksController").log(Level.INFO,
					"Could get the name for all decks", e);
		}
		
		// Default options
		deckNames.add(DEFAULT_DECK);
		deckNames.add(CREATE_DECK);
		deckNames.add(NO_DECK);

		// make sure the decks is not null
		if (decks != null) {
			for (PlanningPokerDeck deck : decks) {
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
			if (d.getDeckName().equals(deckName)) {
				return d;
			}
		}
		throw new WPISuiteException();
	}

}
