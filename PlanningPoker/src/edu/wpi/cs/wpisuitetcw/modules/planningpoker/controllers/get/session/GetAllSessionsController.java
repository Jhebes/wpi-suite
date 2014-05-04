/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.get.session;

import java.util.List;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.stash.SessionStash;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ViewEventManager;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.controller.GetRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.ViewEventController;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * A singleton controller that retrieves all sessions from the core
 * and publishes them to the view
 */
public class GetAllSessionsController {

	/** An instance of this controller */
	private static GetAllSessionsController instance = null;

	/**
	 * Instantiates a new controller tied to the specified view.
	 * Private because this is a singleton.
	 */
	private GetAllSessionsController() {}

	/**
	 * @return The singleton instance for this controller.
	 */
	public static GetAllSessionsController getInstance() {
		if (instance == null) {
			instance = new GetAllSessionsController();
		}
		return instance;
	}
	
	/**
	 * Add the given list of PlanningPokerSessions to the SessionStash
	 * @param sessions A list of PlanningPokerSessions that would be
	 * added to SessionStash
	 */
	public void receivedSessions(List<PlanningPokerSession> sessions) {
		SessionStash.getInstance().mergeFromServer(sessions);
		ViewEventManager.getInstance().getOverviewTreePanel().refresh();
		ViewEventManager.getInstance().getOverviewTreePanel().initTreeStates();
		//Assures that the req manager table is updated when pp starts up. This should fix the "wrong ID" bug.
		GetRequirementsController.getInstance().retrieveRequirements();
		ViewEventController.getInstance().refreshTable();
		ViewEventController.getInstance().refreshTree();
		
		System.out.println("the tree has been refreshed");
		
		
	}

	/**
	 * Send a request to retrieve the sessions from the database.
	 */
	public void retrieveSessions() {
		// Send a request to the core to retrieve the sessions
		final Request request = Network.getInstance().makeRequest(
				"planningpoker/session", HttpMethod.GET);
		request.addObserver(new GetAllSessionsRequestObserver(this));
		request.send(); // send the request
	}

}
