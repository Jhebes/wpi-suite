/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.session;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.entitymanagers.ViewSessionTableManager;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.OverviewTableSessionTableModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This retrieves all sessions from the core and publishes them to the view
 * 
 */
public class GetAllSessionsController implements ActionListener {

	private static GetAllSessionsController instance;
	
	/**
	 * Instantiates a new controller tied to the specified view.
	 * Private because this is a singleton.
	 */
	private GetAllSessionsController() {
	}
	
	public static GetAllSessionsController getInstance() {
		if (instance == null) {
			instance = new GetAllSessionsController();
		}
		return instance;
	}
	

	public void receivedSessions(PlanningPokerSession[] sessions) {
		for(PlanningPokerSession s : sessions){
			
			ViewSessionTableManager a = new ViewSessionTableManager();
			a.fetch(s.getID());
		}
		OverviewTableSessionTableModel.getInstance().refreshSessions(sessions);
	}

	/**
	 * Initiate the request to the server on click
	 * 
	 * @param e
	 *            The triggering event
	 */
	@Override
	public void actionPerformed(ActionEvent e) {		
		// Send a request to the core to retrieve the sessions
		final Request request = Network.getInstance().makeRequest(
				"planningpoker/session", HttpMethod.GET);
		request.addObserver(new GetAllSessionsRequestObserver(this));
		request.send(); // send the request
		
	
	}

	/**
	 * Retrieves the sessions from the database.
	 */
	public void retrieveSessions() {
		actionPerformed(null);
	}
	
	
}
