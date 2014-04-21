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

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerVote;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.SessionInProgressPanel;
import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.janeway.config.Configuration;

/**
 * A controller that adds a new vote to a particular requirement.
 */
public class AddVoteController implements ActionListener {
	/** A PlanningPokerSession whose PlanningPokerRequirement has a new vote */
	private PlanningPokerSession session = null;
	
	private SessionInProgressPanel view;

	/** A PlanningPokerRequirement that has a new vote */
	private PlanningPokerRequirement req = null;

	/**
	 * Construct the controller
	 * @param view The SessionInProgressPanel
	 * @param session A PlanningPokerSession 
	 * whose PlanningPokerRequirement has a new vote
	 */
	public AddVoteController(SessionInProgressPanel view,
			PlanningPokerSession session) {
		this.view = view;
		this.session = session;
	}

	/**
	 * Adds a new vote to the PlanningPokerRequirement.
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		// FIXME: most of this could be moved into the model.
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
					"Could not find requirement by name", e);
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

		session.addVoteToRequirement(req, vote, username);
		view.setNumVotesLabel(session.getNumVotes(req));

		System.out.println(session.getNumVotes(req));
		System.out.println("Added vote to requirement " + req.getName());
		session.setHasVoted(true);
		view.disableEditSession();

		// Update the session remotely
		session.save();

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
