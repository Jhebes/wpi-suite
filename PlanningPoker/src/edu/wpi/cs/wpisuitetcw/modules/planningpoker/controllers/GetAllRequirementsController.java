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

import java.util.ArrayList;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ImportRequirementsTableModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * A singleton controller that retrieves all sessions from the core and 
 * publishes them to the view
 */
public class GetAllRequirementsController {

	/** An instance of this controller */
	private static GetAllRequirementsController instance;

	private GetAllRequirementsController() {}

	/**
	 * Instantiates a new controller tied to the specified view. 
	 */
	public static GetAllRequirementsController getInstance() {
		if (instance == null) {
			instance = new GetAllRequirementsController();
		}
		return instance;
	}

	/**
	 * Add the given array of PlanningPokerRequirements to the 
	 * ImportRequirementsTableModel
	 * @param requirements An array of PlanningPokerRequirements 
	 * that would be added to the ImportRequirementsTableModel
	 */
	public void receivedData(PlanningPokerRequirement[] requirements) {
		ArrayList<PlanningPokerRequirement> requirementsList = new ArrayList<PlanningPokerRequirement>();
		for (PlanningPokerRequirement requirement : requirementsList) {
			requirementsList.add(requirement);
		}

		ImportRequirementsTableModel.getInstance().refreshRequirements(
				requirementsList);
	}

	/**
	 * Send a request to the server to get all the PlanningPokerRequirements
	 */
	public void refreshData() {
		// Send a request to the core to retrieve the sessions
		final Request request = Network.getInstance().makeRequest(
				"planningpoker/requirement", HttpMethod.GET);
		request.addObserver(new GetAllRequirementsRequestObserver(this));
		request.send(); // send the request
	}
}
