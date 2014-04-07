/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		System.out.println("You hit me");
	}
}
