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

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerVote;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.SessionInProgressPanel;
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
public class AddVoteController implements ActionListener {

	private SessionInProgressPanel view;
	
	public AddVoteController(SessionInProgressPanel view) {
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
		PlanningPokerVote vote = new PlanningPokerVote();
		//vote.setCardValue(Integer.parseInt(view.getTextField().getText()));
		
		// Send a request to the core to save this message
		// Create the request
		final Request request = Network.getInstance().makeRequest("planningpoker/vote", HttpMethod.PUT);
		// Set the data to be the session to save (converted to JSON)
		request.setBody(vote.toJSON());
		
		// Listen for the server's response
		request.addObserver(new AddVoteRequestObserver(this));
		
		// Send the request on its way
		request.send();
	}
}
