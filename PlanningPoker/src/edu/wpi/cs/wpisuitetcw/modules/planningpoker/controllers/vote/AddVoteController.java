/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.vote;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.GenericPUTRequestObserver;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerVote;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.SessionInProgressPanel;
import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.janeway.config.Configuration;
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

	private PlanningPokerSession session = null;
	private SessionInProgressPanel view;

	private PlanningPokerRequirement req = null;
	
	public AddVoteController(SessionInProgressPanel view, PlanningPokerSession session) {
		this.view = view;
		this.session = session;
	}
	
	

	/*
	 * This method is called when the user clicks the vote button
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		
		System.out.print("Requesting user is: ");
		String username = "NAME?";
		Configuration c = ConfigManager.getConfig();
		username = c.getUserName();
		System.out.println(username);
		
		PlanningPokerVote vote = new PlanningPokerVote(username, view.getVote());
		session = view.getSession();
		String r = view.getSelectedRequirement();
		System.out.println("Attempting to get Req: " + r);
		try{
			this.req = session.getReqByName(r);
		}catch(NullPointerException e){
			System.out.println("No req found by that name!");
			return;
		}
		session.addVoteToRequirement(req, vote);
	
		System.out.println("Added vote to requirement " + req.getName());
		
		//Update the session remotely
		final Request request = Network.getInstance().makeRequest(
				"planningpoker/session/".concat(String.valueOf(session.getID())), HttpMethod.POST);
		// Set the data to be the session to save (converted to JSON)
		request.setBody(session.toJSON());
		// Listen for the server's response
		request.addObserver(new GenericPUTRequestObserver(this));
		// Send the request on its way
		request.send();

		//session.voteStatus();

	}
}
