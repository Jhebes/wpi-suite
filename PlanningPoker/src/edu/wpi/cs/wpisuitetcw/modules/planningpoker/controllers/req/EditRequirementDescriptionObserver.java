/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.req;

import java.util.ArrayList;
import java.util.Arrays;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * An observer for a request to retrieve planning poker sessions that have
 * not been assigned to a planning poker session.
 */
public class EditRequirementDescriptionObserver implements RequestObserver {

	/** The controller managing the request */
	protected EditRequirementDescriptionController controller;

	/**
	 * Constructs the observer
	 * 
	 * @param addRequirementController
	 *            The controller to update upon receiving the server response
	 */
	public EditRequirementDescriptionObserver(
			EditRequirementDescriptionController addDescriptionController) {
		this.controller = addDescriptionController;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		// cast observable to request
		Request request = (Request) iReq;
		// get the response from the request
		ResponseModel response = request.getResponse();
		PlanningPokerSession[] session = PlanningPokerSession.fromJSONArray(response.getBody());
		controller.receivedData(session[0]);	
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void responseError(IRequest iReq) {
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		
	}
}
