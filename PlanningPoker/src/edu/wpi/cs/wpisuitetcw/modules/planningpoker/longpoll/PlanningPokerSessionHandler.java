/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.longpoll;

import java.util.List;

import javax.swing.DefaultListModel;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.stash.SessionStash;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ViewEventManager;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.session.VotePanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.tablemanager.RequirementTableManager;

/**
 * Handles push events from new planning poker session.
 */
public class PlanningPokerSessionHandler extends
		LongPollingHandler<PlanningPokerSession> {

	/**
	 * Handles an updated {@link PlanningPokerSession} from the server.
	 */
	@Override
	public void handle(String json) {
		Gson gson = new Gson();
		PlanningPokerSession receivedSession = gson.fromJson(json,
				PlanningPokerSession.class);

		// Puts the session into the central storage for the client
		SessionStash.getInstance().update(receivedSession);

		// Forces the requirement table to update.
		new RequirementTableManager().refreshRequirements(
				receivedSession.getID(), receivedSession.getRequirements());

		// Refresh the overview of all sessions
		ViewEventManager.getInstance().getOverviewTreePanel().refresh();

		/* 	
		 * If this session has expired, and a vote panel for it is open, close
		 * it and open a final estimation panel
		 */
		if (receivedSession.isClosed()) {
			List<VotePanel> openPanels = ViewEventManager.getInstance()
					.getVotePanels();
			for (int i = 0; i < openPanels.size(); ++i) {
				PlanningPokerSession s = openPanels.get(i).getSession();
				if (s.getID() == receivedSession.getID()) {
					if (!s.isClosed()) {
						ViewEventManager.getInstance().removeTab(openPanels.get(i));
						ViewEventManager.getInstance().viewSession(
								SessionStash.getInstance().getSessionByID(
										receivedSession.getID()));
					}
					break;
				}
			}
		} else if (receivedSession.isOpen()) {
			List<VotePanel> openPanels = ViewEventManager.getInstance()
					.getVotePanels();
			for (int i = 0; i < openPanels.size(); ++i) {
				VotePanel panel = openPanels.get(i);
				PlanningPokerSession s = panel.getSession();
				if (s.getID() == receivedSession.getID() && !s.isClosed()) {
					panel.updateSession(receivedSession);
					break;
				}
			}
		}

	}

}

