/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.get.requirementmanagerrequirements;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * This observer handles responses to requests for all planning poker session.
 */
public class GetAllRequirementsRequestObserver implements RequestObserver {

	/**
	 * The parent controller.
	 */
	private GetAllRequirementsController controller;

	/**
	 * Constructs a request observer for getting all requirements.
	 * @param controller The parent controller
	 */
	public GetAllRequirementsRequestObserver(GetAllRequirementsController controller) {
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
		Requirement[] requirements = Requirement
				.fromJsonArray(iReq.getResponse().getBody());
		if (requirements == null) {
			requirements = new Requirement[0];
		}
		final List<Requirement> returnedReqs = new ArrayList<Requirement>();
		for(Requirement r : requirements){
			if(r.getIteration().equals("Backlog")){
				returnedReqs.add(r);
			}
		}
		controller.receivedRequirements(returnedReqs);
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
