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

import java.util.ArrayList;
import java.util.Arrays;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * This observer handles responses to requests for all planning poker session
 * 
 * 
 */
public class GetAllSessionsRequestObserver implements RequestObserver {

	private GetAllSessionsController controller;

	public GetAllSessionsRequestObserver(GetAllSessionsController controller) {
		this.controller = controller;
	}

	/*
	 * Parse the session out of the response body and pass them to the
	 * controller
	 * 
	 * @see
	 * edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi
	 * .cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		PlanningPokerSession[] sessions = PlanningPokerSession
				.fromJSONArray(iReq.getResponse().getBody());
		if (sessions == null) {
			sessions = new PlanningPokerSession[0];
		}
		controller.receivedSessions(new ArrayList<PlanningPokerSession>(Arrays
				.asList(sessions)));
	}

	/*
	 * @see
	 * edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.
	 * cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest iReq) {
		fail(iReq, null);
	}

	/*
	 * What to do when we fail to get sessions from the server
	 * 
	 * @see
	 * edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng
	 * .network.models.IRequest, java.lang.Exception)
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {

	}

}
