package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ViewEventManager;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.CreateNewDeckPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.CreateSessionPanel;

public class InitNewDeckPanelController implements ActionListener {
	private static InitNewDeckPanelController instance;
	private CreateSessionPanel view;
	private CreateNewDeckPanel deckPanel = null;

	private InitNewDeckPanelController(CreateSessionPanel sessionPanel) {
		this.view = sessionPanel;
	}

	public static InitNewDeckPanelController getInstance(
			CreateSessionPanel sessionPanel) {
		if (instance == null) {
			instance = new InitNewDeckPanelController(sessionPanel);
		}
		return instance;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (this.deckPanel == null) {
			this.deckPanel = new CreateNewDeckPanel(this.view);
			ViewEventManager.getInstance()
			.display(deckPanel, this.view.DISPLAY_MSG);
		} else {
			ViewEventManager.getInstance().getMainview().setSelectedComponent(this.deckPanel);
		}
	}

	public void removeDeckPanel() {
		ViewEventManager.getInstance().removeTab(this.deckPanel);
		this.deckPanel = null;
		ViewEventManager.getInstance().getMainview()
				.setSelectedComponent(this.view);
	}
}
