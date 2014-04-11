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

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.OverviewPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.OverviewTableSessionTableModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller responds when the users click on "Closed Session" button by
 * displaying all the closed planning poker sessions on the overview panel
 * 
 * @author Hoang Ngo
 * 
 */
public class GetClosedSessionsController implements ActionListener {
	private final OverviewPanel view;
	private final PlanningPokerSession[] data = null;

	/**
	 * Construct a GetClosedSessionsController for the given view
	 * 
	 * @param view
	 *            the view where the closed session button lies on
	 */
	public GetClosedSessionsController(OverviewPanel view) {
		this.view = view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Send a request to the core to get the closed sessions
		final Request request = Network.getInstance().makeRequest(
				"planningpoker/session", HttpMethod.GET);
		request.addObserver(new GetClosedSessionsRequestObserver(this));
		request.send();
	}

	/**
	 * Add given closed sessions to the overview panel This method is called by
	 * the GetClosedSessionsRequestObserver
	 * 
	 * @param sessions
	 *            an array of sessions received from the server
	 */
	public void receiveClosedSessions(PlanningPokerSession[] sessions) {
		this.receivedSessions(sessions);
	}
	
	public void receivedSessions(PlanningPokerSession[] sessions) {
		OverviewTableSessionTableModel.getInstance().refreshSessions(sessions);
	}

}
