/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Chris Casola
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * Handles requests to server to store sessions of Planning Poker
 * 
 * @author Josh Hebert
 * 
 */
public class AddSessionRequestObserver implements RequestObserver {

	private final AddSessionController controller;

	public AddSessionRequestObserver(AddSessionController controller) {
		this.controller = controller;
	}

	/*
	 * Parse the message that was received from the server then pass them to the
	 * controller.
	 * 
	 * @see
	 * edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi
	 * .cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		// Get the response to the given request
		final ResponseModel response = iReq.getResponse();

		// Parse the message out of the response body
		final PlanningPokerSession session = PlanningPokerSession.fromJson(response.getBody());
		
		controller.onSuccess(session);

	}

	@Override
	public void responseError(IRequest iReq) {
		System.err.println("The request to add a session failed.");
	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.err.println("The request to add a session failed.");
	}

}
