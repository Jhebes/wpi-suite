/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers;

import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * Handles requests to server to store sessions of Planning Poker
 * 
 */
public class AddVoteRequestObserver implements RequestObserver {

	// The controller this is tied to
	// private final AddVoteController controller;

	/**
	 * Creates a listener attached to the controller
	 * 
	 * @param addVoteController
	 *            Tied controller
	 */
	public AddVoteRequestObserver(AddVoteController addVoteController) {
		// this.controller = addVoteController;
	}

	/*
	 * Parse the session that was received from the server then pass them to the
	 * controller.
	 * 
	 * @see
	 * edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi
	 * .cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		System.out.println("Vote successfully stored!");
	}

	/**
	 * What do we do if there's an error?
	 */
	@Override
	public void responseError(IRequest iReq) {
		System.err
				.println("The request to add a vote failed. (Response Error)");
	}

	/**
	 * What do we do when there's a general network failure?
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.err
				.println("The request to add a vote failed. (General Failure)");
	}

}
