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

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.req;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * Handles requests to server to store sessions of Planning Poker
 * 
 * @author Josh Hebert
 * 
 */
public class AddRequirementRequestObserver implements RequestObserver {

	// The controller this is tied to
	private final AddRequirementController controller;

	/**
	 * Creates a listener attached to the controller
	 * 
	 * @param addVoteController
	 *            Tied controller
	 */
	public AddRequirementRequestObserver(AddRequirementController c) {
		
		this.controller = c;
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
		
		// cast observable to request
		Request request = (Request) iReq;

		// get the response from the request
		ResponseModel response = request.getResponse();
		System.out.println("Got response");
		if (response.getStatusCode() == 200) {
			System.out.println("Decoding response");
			PlanningPokerSession session[] = PlanningPokerSession.fromJSONArray(response.getBody());
			System.out.println("Success!");
			if(session.length == 0){
				controller.buildNewSession0();
			}else{
				
				controller.addReq(session[0]);
			}
			
		} else {
			
		}
		
		
		
	}

	/**
	 * What do we do if there's an error?
	 */
	@Override
	public void responseError(IRequest iReq) {
		System.err
				.println("The request to add a requirement failed. (Response Error)");
	}

	/**
	 * What do we do when there's a general network failure?
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.err
				.println("The request to add a requirement failed. (General Failure)");
	}

}
