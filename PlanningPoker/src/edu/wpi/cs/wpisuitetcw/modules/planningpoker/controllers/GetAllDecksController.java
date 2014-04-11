package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers;

import java.util.ArrayList;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerDeck;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class GetAllDecksController {

	private static GetAllDecksController instance;
	private PlanningPokerDeck[] decks = null;

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

	public void updateDecks(PlanningPokerDeck[] decks) {
		this.decks = decks;
	}

	/**
	 * returns all the names of available decks in our database
	 * 
	 * @return ArrayList of deck names
	 * @throws InterruptedException 
	 */
	public ArrayList<String> getAllDeckNames() {
		this.refreshDecks(); // set up the deck
		ArrayList<String> deckNames = new ArrayList<String>();
		
		// delay the this process since the request fired in refreshDecks() might not be completed
		try {
			Thread.sleep(50); 
		} catch (InterruptedException e) {
		}
		
		deckNames.add("Default");
		deckNames.add("No deck");
		
		// make sure the decks is not null
		if(decks != null) {
			for (PlanningPokerDeck deck : this.decks) {
				deckNames.add(deck.getDeckName());
			}
		}
		return deckNames;
	}
	
	/**
	 * retrieve decks from database
	 */
	public void refreshDecks() {
		final Request request = Network.getInstance().makeRequest(
				"planningpoker/deck", HttpMethod.GET);
		request.addObserver(new GetAllDecksRequestObserver(this));
		request.send();
	}

}
