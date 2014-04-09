/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerDeck;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ViewEventManager;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.CreateNewDeckPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.pokers.Card;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class CreateNewDeckController implements ActionListener {

	private CreateNewDeckPanel view;

	public CreateNewDeckController(CreateNewDeckPanel deckPanel) {
		this.view = deckPanel;

	}

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
			// some inputs are not integer
			this.view.repaint();
		}

	}

	// removes the tab
	public void onSuccess(PlanningPokerDeck deck) {
		// close the tab
		ViewEventManager.getInstance().removeTab(this.view);
	}

	/**
	 * Validate all the inputs
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

		ArrayList<Card> cardList = this.view.getCardList();
		for (Card aCard : cardList) {
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
