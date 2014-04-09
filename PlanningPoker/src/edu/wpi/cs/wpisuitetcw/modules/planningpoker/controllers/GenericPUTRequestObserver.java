/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers;

import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * Handles requests to server to store pieces of data in the database
 */
public class GenericPUTRequestObserver implements RequestObserver {
	//The controller this is tied to
	private final ActionListener controller;
	
	/**
	 * Creates a listener attached to the controller
	 * @param a Tied controller
	 */
	public GenericPUTRequestObserver(ActionListener a) {
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
	}

	/**
	 * What do we do if there's an error?
	 */
	@Override
	public void responseError(IRequest iReq) {
		System.err.println("The request to add a new entry failed. (ERR)");
	}

	/**
	 * What do we do when there's a general network failure?
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.err.println("The request to add a session failed. (NETW)");
	}

}
