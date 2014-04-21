/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.session;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * A controller that sends requests to server to store Planning Poker Session
 */
public class AddSessionRequestObserver implements RequestObserver {
	
	/** The controller this is tied to */
	private final AddSessionController controller;
	
	/**
	 * Creates a listener attached to the controller
	 * @param a Tied controller
	 */
	public AddSessionRequestObserver(AddSessionController a) {
		this.controller = a;
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
	}

	/**
	 * Print an message on the console when it gets a error response
	 */
	@Override
	public void responseError(IRequest iReq) {
		System.err.println("The request to add a session failed.");
	}

	/**
	 * Print an message on the console when a general network failure
	 * happens
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.err.println("The request to add a session failed.");
	}

}
