/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.session.tabs;

import javax.swing.JPanel;
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
		deckPanel = new SessionDeckPanel(CardDisplayMode.DISPLAY);
		requirementPanel = new SessionRequirementPanel(session);

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

	/**
	 * @param deck
	 *            panel
	 * @return
	 */
	public void setDeckPanel(SessionDeckPanel deckPanel) {
		// this.remove(this.deckPanel);

		this.deckPanel = deckPanel;
		// int position = this.getTabPlacement();
		this.setComponentAt(0, deckPanel);
		updateUI();
	}

	/*
	 * @return the requirement panel
	 */
	public SessionRequirementPanel getRequirementPanel() {
		return requirementPanel;
	}

}
