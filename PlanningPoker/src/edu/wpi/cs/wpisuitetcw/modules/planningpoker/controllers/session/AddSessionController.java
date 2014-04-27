/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.session;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.CreateNewDeckController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.get.session.GetAllSessionsController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerDeck;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ViewEventManager;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.pokers.CreateDeckPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.session.CreateSessionPanel;
import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;

/**
 * This controller responds when the user clicks the "Create" button by using
 * all entered information to construct a new session and storing in the
 * database
 */
public class AddSessionController implements ActionListener {
	private final CreateSessionPanel view;
	private PlanningPokerSession session;

	/** Allow users editing a session's content or not */
	private boolean isEditMode;

	/**
	 * Construct an AddSessionController for the given view
	 * 
	 * @param view
	 *            the view where the user enters data for the new session
	 * 
	 * @param isEditMode
	 *            the value representing if the panel contains an already
	 *            created session or not
	 */
	public AddSessionController(CreateSessionPanel view, boolean isEditMode) {
		/*
		 * TODO: This should also have a manager for the CreateSessionPanel, so
		 * that errors can be fed back to the panel rather than thrown as
		 * exceptions
		 */

		this.view = view;
		this.isEditMode = isEditMode;
	}

	/**
	 * Construct an AddSessionController for the given view
	 * 
	 * @param view
	 *            the view where the user enters data for the new session
	 * 
	 * @param isEditMode
	 *            the value representing if the panel contains an already
	 *            created session or not
	 * @param session
	 *            the planning poker session being edited
	 */
	public AddSessionController(CreateSessionPanel view, boolean isEditMode,
			PlanningPokerSession session) {

		this.view = view;
		this.isEditMode = isEditMode;
		this.session = session;
	}

	/*
	 * This method is called when the user clicks the "Create" button
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
	 */
	@Override
	public synchronized void actionPerformed(ActionEvent event) {
		// if a name was entered create the session
		// otherwise the button will do nothing
		if ((this.view.hasAllValidInputs() == true)
				&& (this.isEditMode == false)) {

			// Get the inputs from user
			final String name = this.view.getNameTextField().getText();
			final Date d = this.view.getDeadline();
			final String des = this.view.getDescriptionBox().getText();
			final String deckName = (String) this.view.getDeckType()
					.getSelectedItem();

			// Store the new deck if user creates one
			if (this.view.isInCreateMode()) {
				final CreateNewDeckController createDeckController = new CreateNewDeckController(
						view.getDeckPanel());
				createDeckController.addDeckToDatabase();
			}

			// Create a new session and populate its data
			final PlanningPokerSession session = new PlanningPokerSession();
			session.setOwnerUserName(ConfigManager.getConfig().getUserName());
			session.setName(name);
			session.setID(0);
			session.setDeadline(d);
			session.setDescription(des);

			// Associate a deck to the new session if the user does not choose
			// 'No deck'
			if (!this.view.isInNoDeckMode()) {
				// 'Default' option, bind a new Fibonacci deck to the
				// session
				if (deckName.equals("Default")) {
					session.setDeck(new PlanningPokerDeck());
				} else {

					final CreateDeckPanel deckPanel = view.getDeckPanel();
					session.setDeck(new PlanningPokerDeck(deckPanel.getName(),
							deckPanel.getNewDeckValues(), deckPanel
									.getMaxSelectionCards()));
					// session.setDeck(GetAllDecksController
					// .getInstance()
					// .getDeckByName(deckName));
				}
			}

			session.create();
			GetAllSessionsController.getInstance().retrieveSessions();
			ViewEventManager.getInstance().removeTab(this.view);

		} else {
			// user has yet entered all required data
			// TODO: maybe make the warning a pop-up
			this.view.repaint();
		}

	}
}
