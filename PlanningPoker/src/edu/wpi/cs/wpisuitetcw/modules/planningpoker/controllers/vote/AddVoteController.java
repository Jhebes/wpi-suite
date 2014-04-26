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
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.session.VotePanel;
import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.janeway.config.Configuration;

/**
 * A controller that adds a new vote to a particular requirement.
 */
public class AddVoteController implements ActionListener {
	/** A PlanningPokerSession whose PlanningPokerRequirement has a new vote */
	private PlanningPokerSession session = null;
	
	/** A view that users vote requirements */
	private VotePanel voteView;

	/** A PlanningPokerRequirement that has a new vote */
	private PlanningPokerRequirement req = null;

	/**
	 * Construct the controller
	 * @param view The SessionInProgressPanel
	 * @param session A PlanningPokerSession 
	 * whose PlanningPokerRequirement has a new vote
	 */
	public AddVoteController(VotePanel voteView,
			PlanningPokerSession session) {
		this.voteView = voteView;
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

		PlanningPokerVote vote = new PlanningPokerVote(username, view.getVote());
		String r = view.getSelectedRequirement();

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
			if (v.getUser().equals(username)) {
				toRemove.add(v);
			}
		}

		for (PlanningPokerVote v : toRemove) {
			req.deleteVote(v);
		}
		
		// Add vote to the requirement
		PlanningPokerVote vote = new PlanningPokerVote(username, voteView.getVote());
		session.addVoteToRequirement(req, vote, username);

		Logger.getLogger("PlanningPoker").log(Level.INFO, "Added vote to requirement " + req.getName());
		session.setHasVoted(true);
		voteView.disableEditSession();

		// Update the session remotely
		session.save();

		// Much hack! Very broke!
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			Logger.getLogger("PlanningPoker").log(Level.SEVERE,
					"Sleeping was interrupted.", e);
		}

		this.view.setVoteTextFieldWithValue(vote.getCardValue());
		this.view.updateUI();
		
		GetRequirementsVotesController getVotes = new GetRequirementsVotesController(
				view, session);
		getVotes.actionPerformed(new ActionEvent(getVotes, 0, r));	
	}
}
