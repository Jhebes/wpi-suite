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

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.CreateSessionPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This retrieves all sessions from the core and publishes them to the view
 * 
 */
public class GetAllSessionsController implements ActionListener {

	// The view the sessions should be pushed to
	private CreateSessionPanel view;

	/**
	 * Instantiates a new controller tied to the specified view
	 * 
	 * @param view
	 *            The view to which the returned sessions should be pushed to
	 */
	public GetAllSessionsController(CreateSessionPanel view) {
		this.view = view;
	}

	/**
	 * Initiate the request to the server on click
	 * 
	 * @param e
	 *            The triggering event
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Send a request to the core to retrieve the sessions
		final Request request = Network.getInstance().makeRequest(
				"planningpoker/session", HttpMethod.GET);
		request.addObserver(new GetAllSessionsRequestObserver(this));
		request.send(); // send the request
	}

	/**
	 * Add the retrieved sessions to the view
	 * 
	 * @param sessions
	 *            An array of session received from the server
	 */
	public void receivedSessions(PlanningPokerSession[] sessions) {
		// Uncomment when view is updated to receive this
		// this.view.receiveSessionList(sessions);
	}
}
