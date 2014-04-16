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

import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * Handles requests to server to activate a session of Planning Poker
 * 
 */
public class ActivateSessionObserver implements RequestObserver {
	private final ActivateSessionController controller;
		
	/**
	 * Creates a listener attached to the controller
	 * @param a Tied controller
	 */
	public ActivateSessionObserver(ActivateSessionController a) {
		this.controller = a;
	}

	/**
	 * Parse the message that was received from the server then pass them to the
	 * controller.
	 * 
	 * @see
	 * edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi
	 * .cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {		
		controller.onSuccess();

	}

	/**
	 * What do we do if there's an error?
	 */
	@Override
	public void responseError(IRequest iReq) {
		System.err.println("The request to add a session failed.");
	}

	/**
	 * What do we do when there's a general network failure?
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.err.println("The request to add a session failed.");
	}

}
