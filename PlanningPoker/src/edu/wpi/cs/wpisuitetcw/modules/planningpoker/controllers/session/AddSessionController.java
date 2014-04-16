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

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ViewEventManager;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.CreateSessionPanel;
import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;

/**
 * This controller responds when the user clicks the "Create" button by using
 * all entered information to construct a new session and storing in the
 * database
 * 
 */
public class AddSessionController implements ActionListener {
	private final CreateSessionPanel view;

	/**
	 * Construct an AddSessionController for the given view
	 * 
	 * @param view
	 *            the view where the user enters data for the new session
	 */
	public AddSessionController(CreateSessionPanel view) {
		/*
		 * TODO: This should also have a manager for the CreateSessionPanel, so
		 * that errors can be fed back to the panel rather than thrown as
		 * exceptions
		 */

		this.view = view;
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
		if (this.view.requiredFieldEntered() == true) {
			// Get the name of the session
			String name = this.view.getNameTextField().getText();

			// Dummy Data
			// Date fields with some dummy data
			// String year = "1";
			// String month = "1";
			// String day = "1";

			// TODO Session type should be stored
			Date d = this.view.getDeadline();
			String des = this.view.getDescriptionBox().getText();

			// Create a new session and populate its data
			PlanningPokerSession session = new PlanningPokerSession();
			session.setOwnerUserName(ConfigManager.getConfig().getUserName());
			session.setName(name);
			session.setDeadline(d);
			session.setDescription(des);
			
			session.write();
			ViewEventManager.getInstance().removeTab(this.view);
		} else {
			// user has yet entered all required data
			//TODO: maybe make the warning a pop-up
			this.view.repaint();
		}

	}
}
