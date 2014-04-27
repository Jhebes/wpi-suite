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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;

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
		session = voteView.getSession();

		// Terminate voting process if the session is not active
		if (!session.isActive()) return;
		
		// Get the requirement that has been selected from VotePanel
		try {
			this.req = voteView.getRequirementList().getSelectedValue();
		} catch (NullPointerException e) {
			Logger.getLogger("PlanningPoker").log(Level.WARNING,
					"Could not find requirement by name", e);
			return;
		}

		// checking list of votes to see if user has already voted
		final List<PlanningPokerVote> toRemove = new ArrayList<PlanningPokerVote>();

		System.out.print("Requesting user is: ");
		final Configuration c = ConfigManager.getConfig();
		final String username = c.getUserName();
		
		for (PlanningPokerVote v : this.req.getVotes()) {
			if (v.getUser().equals(username)) {
				toRemove.add(v);
			}
		}

		for (PlanningPokerVote v : toRemove) {
			req.deleteVote(v);
		}
		
		// Add vote to the requirement
		final PlanningPokerVote vote = new PlanningPokerVote(username, voteView.getVote());
		session.addVoteToRequirement(req, vote, username);

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
		
		final GetRequirementsVotesController getVotes = new GetRequirementsVotesController(
				voteView, session);
		getVotes.actionPerformed(new ActionEvent(getVotes, 0, req.getName()));	
		
		// Update the vote panel
		updateVoteIcon();
		this.voteView.setVoteTextFieldWithValue(vote.getCardValue());
		this.voteView.updateUI();
	}

	/*
	 * Change the vote icon by updating the list's model
	 */
	@SuppressWarnings("unchecked")
	private void updateVoteIcon() {
		final int index = voteView.getRequirementList().getSelectedIndex();
		((DefaultListModel<PlanningPokerRequirement>) voteView
														.getRequirementList()
														.getModel())
															.set(index, req);
		voteView.getRequirementList().setSelectedIndex(index);		
	}
}
