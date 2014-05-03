/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.vote;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * An observer for a request to retrieve planning poker sessions that have
 * not been assigned to a planning poker session.
 */
public class GetRequirementsVotesRequestObserver implements
		RequestObserver {

	/** The controller managing the request */
	protected GetRequirementsVotesController controller;

	/**
	 * Constructs the observer
	 * 
	 * @param controller
	 *            The controller to update upon receiving the server response
	 */
	public GetRequirementsVotesRequestObserver(
			GetRequirementsVotesController controller) {
		this.controller = controller;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		// cast observable to request
		final Request request = (Request) iReq;

		// get the response from the request
		final ResponseModel response = request.getResponse();

		if (response.getStatusCode() == 200) {
			final PlanningPokerSession[] session = PlanningPokerSession.fromJSONArray(response.getBody());
			controller.receivedData(session[0]);
		} else {
			controller.errorReceivingData("Received "
					+ iReq.getResponse().getStatusCode()
					+ " error from server: "
					+ iReq.getResponse().getStatusMessage());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void responseError(IRequest iReq) {
		// an error occurred
		controller.errorReceivingData("Received "
				+ iReq.getResponse().getStatusCode() + " error from server: "
				+ iReq.getResponse().getStatusMessage());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		// an error occurred
		controller.errorReceivingData("Unable to complete request: "
				+ exception.getMessage());
	}
}
