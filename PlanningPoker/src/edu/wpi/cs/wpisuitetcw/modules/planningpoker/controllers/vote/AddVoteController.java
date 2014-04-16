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
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerVote;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.SessionInProgressPanel;
import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.janeway.config.Configuration;

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
		String username = "NAME?";
		Configuration c = ConfigManager.getConfig();
		username = c.getUserName();
		System.out.println(username);

		PlanningPokerVote vote = new PlanningPokerVote(username, view.getVote());
		session = view.getSession();
		String r = view.getSelectedRequirement();
		try {
			this.req = session.getReqByName(r);
		} catch (NullPointerException e) {
			Logger.getLogger("PlanningPoker").log(Level.WARNING,
					"Could not find requirement by name: " + r, e);
			return;
		}
		session.addVoteToRequirement(req, vote, username);

		//Update the session remotely
		session.update();
	}
}
