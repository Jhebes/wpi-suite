/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ViewEventManager;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.pokers.CreateDeckPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.session.CreateSessionPanel;

/**
 * A singleton controller that sets the main view to CreateNewDeckPanel
 *
 */
public class InitNewDeckPanelController implements ActionListener {
	/** A CreateSessionPanel */
	private CreateSessionPanel view;
	
	/** A CreateNewDeckPanel */
	private CreateDeckPanel deckPanel = null;
	
	/** An instance of this controller */
	private static InitNewDeckPanelController instance = null;

	/*
	 * Store the given CreateSessionPanel 
	 */
	private InitNewDeckPanelController(CreateSessionPanel sessionPanel) {
		this.view = sessionPanel;
	}

	/**
	 * Return an InitNewDeckPanelController instance
	 * 
	 * @param sessionPanel A CreateSessionPanel
	 * @return Return an InitNewDeckPanelController instance
	 */
	public static InitNewDeckPanelController getInstance(
											CreateSessionPanel sessionPanel) {
		if (instance == null) {
			instance = new InitNewDeckPanelController(sessionPanel);
		}
		return instance;
	}

	/**
	 * Switch the view to the CreateNewDeckPanel
	 * {@inheritDoc}
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			ViewEventManager.getInstance().getMainview()
					.setSelectedComponent(this.deckPanel);
		} catch (IllegalArgumentException error) {
			// this.deckPanel = new CreateNewDeckPanel();
			ViewEventManager.getInstance().display(deckPanel,
					CreateSessionPanel.DISPLAY_MSG);
		}
	}

	/**
	 * Removes a deckpanel from the mainview and 
	 * go back to the create session panel
	 */
	public void removeDeckPanel() {
		ViewEventManager.getInstance().removeTab(this.deckPanel);
		this.deckPanel = null;
		Logger.getLogger("PlanningPoker").log(Level.INFO, this.view.toString());
		ViewEventManager.getInstance().getMainview()
				.setSelectedComponent(this.view);
	}
}
