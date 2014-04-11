package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ViewEventManager;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.CreateNewDeckPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.CreateSessionPanel;

public class InitNewDeckPanelController implements ActionListener {

	private CreateSessionPanel view;

	public InitNewDeckPanelController(CreateSessionPanel sessionPanel) {
		this.view = sessionPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		CreateNewDeckPanel deckPanel = new CreateNewDeckPanel(this.view);
		// setup connections between newDeckPanel and SessionPanel
		// this.view.addCreateDeckListener(deckPanel, this.view);
		// display the newDeckPanel
		ViewEventManager.getInstance()
				.display(deckPanel, this.view.DISPLAY_MSG);
		// ViewEventManager.getInstance().createDeck();
	}
}
