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

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.stash.SessionStash;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ViewEventManager;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.tablemanager.RequirementTableManager;

/**
 * Handles push events from new planning poker session.
 */
public class PlanningPokerSessionHandler extends LongPollingHandler<PlanningPokerSession> {

	/**
	 * Handles an updated {@link PlanningPokerSession} from the server.
	 */
	@Override
	public void handle(String json) {
		Gson gson = new Gson();
		PlanningPokerSession receivedSession = gson.fromJson(json, PlanningPokerSession.class); 
		SessionStash.getInstance().update(receivedSession);
		new RequirementTableManager().refreshRequirements(
				receivedSession.getID(), receivedSession.getRequirements());
		ViewEventManager.getInstance().getOverviewTreePanel().refresh();
	}

}
