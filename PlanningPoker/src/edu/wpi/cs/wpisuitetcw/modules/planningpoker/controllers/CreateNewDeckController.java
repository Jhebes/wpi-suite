/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.CreateNewDeckPanel;

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
		//System.out.println("You hit me");
		// TODO firgure out a way to represent the card object
		String deckName = this.view.getTextboxName().getText();
		ArrayList<Integer> newDeckValues = this.view.getNewDeckValues();
		
		System.out.println("Name is: " + deckName);
		for(int i : newDeckValues) {
			System.out.println("Valu is: " + i);
		}
	}
}
