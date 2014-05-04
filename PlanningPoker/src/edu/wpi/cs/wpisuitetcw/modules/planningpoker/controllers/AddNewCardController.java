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

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.session.tabs.SessionDeckPanel;

/**
 * Controller that adds images of cards to the CreateNewDeckPanel
 */
public class AddNewCardController implements ActionListener {
	/** A View that exhibits the images of cards */
	private SessionDeckPanel view;

	/**
	 * Construct an AddNewCardController controller by
	 * storing the given CreateNewDeckPanel
	 * @param deckPanel A CreateNewDeckPanel that would be stored
	 */
	public AddNewCardController(SessionDeckPanel deckPanel) {
		view = deckPanel;
	}

	/**
	 * Add a new image of card to the view
	 * {@inheritDoc}
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		view.addNewCard();
		view.updateUI();
		// check if all required inputs are entered
		view.getSessionPanel().checkSessionValidation();
		view.getSessionPanel().checkSessionChanges();
	}

}
