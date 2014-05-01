package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.session.tabs;

import javax.swing.JTabbedPane;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.characteristics.CardDisplayMode;

public class SessionTabsPanel extends JTabbedPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* Deck panel */
	private SessionDeckPanel deckPanel;

	/* Requirement panel */
	private SessionRequirementPanel requirementPanel;

	/**
	 * Constructor for creating a JPanel that contains the tabs of the deck
	 * panel and requirement panel
	 * 
	 */
	public SessionTabsPanel(PlanningPokerSession session) {
		// set up panels
		this.deckPanel = new SessionDeckPanel(CardDisplayMode.DISPLAY);
		this.requirementPanel = new SessionRequirementPanel(session);

		// set up tabs
		this.addTab("Deck", null, deckPanel);
		this.addTab("Requirement", null, requirementPanel);
	}

	/**
	 * 
	 * @return the deck panel
	 */
	public SessionDeckPanel getDeckPanel() {
		return deckPanel;
	}

	/*
	 * @return the requirement panel
	 */
	public SessionRequirementPanel getRequirementPanel() {
		return requirementPanel;
	}

}
