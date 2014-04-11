package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerDeck;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class GetAllDecksController implements ActionListener {

	private static GetAllDecksController instance;

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

	public PlanningPokerDeck[] receivedDecks(PlanningPokerDeck[] decks) {
		return decks;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		final Request request = Network.getInstance().makeRequest(
				"planningpoker/deck", HttpMethod.GET);
		request.addObserver(new GetAllDecksRequestObserver(this));
		request.send();
	}

}
