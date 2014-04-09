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

import java.util.ArrayList;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * This observer handles responses to requests for all open planning poker
 * sessions
 * 
 * @author Hoang Ngo
 * 
 */
public class GetOpenSessionsRequestObserver implements RequestObserver {
	private final GetOpenSessionsController controller;

	/**
	 * Construct a GetOpenSessionsRequestObserver for the given controller
	 * @param controller a GetOpenSessionsController
	 */
	public GetOpenSessionsRequestObserver(GetOpenSessionsController controller) {
		this.controller = controller;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi
	 * .cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		// Parse all sessions out of the response body
		PlanningPokerSession[] sessions = PlanningPokerSession
											.fromJSONArray(iReq
															.getResponse()
															.getBody());

		// Filter the open sessions
		ArrayList<PlanningPokerSession> tempOpenSessions = new ArrayList<PlanningPokerSession>();
		for (int i = 0; i < sessions.length; i++) {
			if (sessions[i].isOpen()) {
				tempOpenSessions.add(sessions[i]);
			}
		}

		// Convert the open sessions arrayList to array
		PlanningPokerSession[] openSessions = 
				new PlanningPokerSession[tempOpenSessions.size()];
		openSessions = tempOpenSessions.toArray(openSessions);

		// Pass the sessions back to the controller
		controller.receiveOpenSessions(openSessions);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.
	 * cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest iReq) {
		// TODO print the error message to the overview panel
		System.err.println("The request to get open sessions failed");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng
	 * .network.models.IRequest, java.lang.Exception)
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		// TODO print the error message to the overview panel
		System.err.println("The request to get open sessions failed");
	}

}
