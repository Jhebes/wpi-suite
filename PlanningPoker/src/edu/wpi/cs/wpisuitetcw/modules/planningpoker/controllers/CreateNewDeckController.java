/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ViewEventManager;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.CreateNewDeckPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.pokers.Card;

/**
 * @author troyling, Rob
 * 
 */
public class CreateNewDeckController implements ActionListener {

	private CreateNewDeckPanel view;

	public CreateNewDeckController(CreateNewDeckPanel deckPanel) {
		this.view = deckPanel;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// System.out.println("You hit me");
		// TODO firgure out a way to represent the card object
		String deckName = this.view.getTextboxName().getText();
		// ArrayList<Integer> newDeckValues = this.view.getNewDeckValues();
		//
		// System.out.println("Name is: " + deckName);
		// for(int i : newDeckValues) {
		// System.out.println("Valu is: " + i);
		// }

		// make sure all cards are validated
		if(validateCardValues()) {
			//inputs are good
			// TODO store in database
			
			// close the tab
			ViewEventManager.getInstance().removeTab(this.view);
			
		} else {
			// some inputs are not integer
			this.view.repaint();
		}
		
	}
	
	/**
	 * Validate all the values for the entire deck of cards
	 * @return true if so; false otherwise
	 */
	private boolean validateCardValues() {
		boolean isAllInputValid = true;
		
		ArrayList<Card> cardList = this.view.getCardList();
		for (Card aCard : cardList) {
			if(!aCard.validateCardValue()) {
				aCard.setCardInvalid();
				isAllInputValid = false;
			} else {
				aCard.setCardValid();
			}
		}
		return isAllInputValid;
	}
}
