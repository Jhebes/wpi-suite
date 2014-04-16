/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.vote;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
 */
public class AddVoteController implements ActionListener {

	private PlanningPokerSession session = null;
	private SessionInProgressPanel view;

	private PlanningPokerRequirement req = null;

	public AddVoteController(SessionInProgressPanel view,
			PlanningPokerSession session) {
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
		session = view.getSession();

		if (!session.isActive())
			return;

		System.out.print("Requesting user is: ");
		Configuration c = ConfigManager.getConfig();
		String username = c.getUserName();

		System.out.println(username);

		PlanningPokerVote vote = new PlanningPokerVote(username, view.getVote());
		String r = view.getSelectedRequirement();
		System.out.println("Attempting to get Req: " + r);
		try {
			this.req = session.getReqByName(r);
		} catch (NullPointerException e) {
			Logger.getLogger("PlanningPoker").log(Level.WARNING,
					"Could not find requirement by name: " + r, e);
			return;
		}

		ArrayList<PlanningPokerVote> toRemove = new ArrayList<PlanningPokerVote>();

		// checking list of votes to see if user has already voted
		for (PlanningPokerVote v : this.req.getVotes()) {
			System.out.println(v.getUser());
			if (v.getUser().equals(username)) {
				toRemove.add(v);
			}
		}

		for (PlanningPokerVote v : toRemove) {
			req.deleteVote(v);
		}

		session.addVoteToRequirement(req, vote);
		view.setNumVotesLabel(session.getNumVotes(req));

		System.out.println(session.getNumVotes(req));
		System.out.println("Added vote to requirement " + req.getName());

		// Update the session remotely
		final Request request = Network.getInstance()
				.makeRequest(
						"planningpoker/session/".concat(String.valueOf(session
								.getID())), HttpMethod.POST);
		// Set the data to be the session to save (converted to JSON)
		request.setBody(session.toJSON());
		// Listen for the server's response
		request.addObserver(new GenericPUTRequestObserver(this));
		// Send the request on its way
		request.send();

		// Much hack! Very broke!
		// TODO: FIX PLZZZZZZ!
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			Logger.getLogger("PlanningPoker").log(Level.SEVERE,
					"Sleeping was interrupted.", e);
		}

		GetRequirementsVotesController getVotes = new GetRequirementsVotesController(
				view, session);
		getVotes.actionPerformed(new ActionEvent(getVotes, 0, r));
	}
}
