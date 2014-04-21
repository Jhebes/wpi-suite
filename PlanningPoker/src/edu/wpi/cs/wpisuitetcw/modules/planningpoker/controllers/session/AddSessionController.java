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
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.GetAllDecksController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.get.session.GetAllSessionsController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ViewEventManager;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.CreateSessionPanel;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;

/**
 * This controller responds when the user clicks the "Create" button by using
 * all entered information to construct a new session and storing in the
 * database
 * 
 */
public class AddSessionController implements ActionListener {
	private final CreateSessionPanel view;

	private boolean isEditMode;
	private PlanningPokerSession session;

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
	public void actionPerformed(ActionEvent event) {
		// if a name was entered create the session
		// otherwise the button will do nothing
		if ((this.view.requiredFieldEntered() == true)
				&& (this.isEditMode == false)) {
			// Get the name of the session
			String name = this.view.getNameTextField().getText();

			// TODO Session type should be stored
			Date d = this.view.getDeadline();
			String des = this.view.getDescriptionBox().getText();
			String deckName = (String) this.view.getDeckType()
					.getSelectedItem();

			// Create a new session and populate its data
			PlanningPokerSession session = new PlanningPokerSession();
			session.setOwnerUserName(ConfigManager.getConfig().getUserName());
			session.setName(name);
			session.setDeadline(d);
			session.setDescription(des);
			try {
				session.setDeck(GetAllDecksController.getInstance()
						.getDeckByName(deckName));
			} catch (WPISuiteException e) {
				Logger.getLogger("PlanningPoker").log(Level.SEVERE,
						"Error getting all decks", e);
			}

			session.create();
			ViewEventManager.getInstance().removeTab(this.view);
			ViewEventManager.getInstance().viewSession(session);
		} else {
			// user has yet entered all required data
			// TODO: maybe make the warning a pop-up
			this.view.repaint();
		}

	}

	// removes a tab and opens another
	public void onSuccess(PlanningPokerSession session) {
		// TODO open a session after creating it

		ViewEventManager.getInstance().removeTab(this.view);

		ViewEventManager.getInstance().viewSession(session);
		GetAllSessionsController.getInstance().retrieveSessions();
	}
}
