/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Chris Casola
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ViewEventController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.CreateSessionPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller responds when the user clicks the "Create" button by using
 * all entered information to construct a new session and storing in the
 * database
 * 
 * @author Josh Hebert
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
		// Get the name of the session
		String name = this.view.nameField.getText();

		// Dummy Data
		// Date fields with some dummy data
		// String year = "1";
		// String month = "1";
		// String day = "1";

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date d;
		try {
			// d = sdf.parse(String.format("%s/%s/%s", day, month, year));
			d = sdf.parse(this.view.deadlineField.getText());

		} catch (ParseException e) {
			// The user put in bad date data and we should return an error on
			// the gui

			// Not this
			e.printStackTrace();

			return;
		}

		// Create a new session and populate its data
		PlanningPokerSession session = new PlanningPokerSession();
		session.setName(name);
		session.setEndTime(d);

		// Add all checked requirements
		// ArrayList<PlanningPokerRequirement> reqs = view.getRequirements();
		// session.addRequirements(reqs);

		this.saveSession(session);
		
	}
	/*
	 * Send a request to the core to save this message
	 */
	public void saveSession(PlanningPokerSession session){
		// Create the request
		final Request request = Network.getInstance().makeRequest(
				"planningpoker/session", HttpMethod.PUT);
		// Set the data to be the session to save (converted to JSON)
		request.setBody(session.toJSON());
		// Listen for the server's response
		request.addObserver(new AddSessionRequestObserver(this));
		// Send the request on its way
		request.send();
		
	}
	//removes a tab and opens another
	public void onSuccess(PlanningPokerSession session){
		ViewEventController.getInstance().removeTab(session.panel);
		ViewEventController.openSessionView(session.getID());
	}
}
