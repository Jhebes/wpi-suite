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

	/**
	 * Create a new deckPanel controller
	 * 
	 * @param sessionPanel
	 * @return InitNewDeckPanelController instance
	 */
	public static InitNewDeckPanelController getInstance(
			CreateSessionPanel sessionPanel) {
		if (instance == null) {
			instance = new InitNewDeckPanelController(sessionPanel);
		}
		return instance;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			ViewEventManager.getInstance().getMainview()
					.setSelectedComponent(this.deckPanel);
		} catch (IllegalArgumentException error) {
			this.deckPanel = new CreateNewDeckPanel(this.view);
			ViewEventManager.getInstance().display(deckPanel,
					this.view.DISPLAY_MSG);
		}
	}

	/**
	 * removes a deckpanel from the mainview and go back to the create session
	 * panel
	 */
	public void removeDeckPanel() {
		ViewEventManager.getInstance().removeTab(this.deckPanel);
		this.deckPanel = null;
		System.out.println(this.view.toString());
		ViewEventManager.getInstance().getMainview()
				.setSelectedComponent(this.view);
	}
}
