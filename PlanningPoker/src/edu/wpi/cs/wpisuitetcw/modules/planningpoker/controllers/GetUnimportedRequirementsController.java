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

import javax.swing.JOptionPane;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ImportRequirementsTableModel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.CreateSessionPanel;
import edu.wpi.cs.wpisuitetng.exceptions.NotImplementedException;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * A singleton controller that retrieves all requirements that
 * are not imported from the database
 */
public class GetUnimportedRequirementsController {
	/** An instance of this controller */
	private static GetUnimportedRequirementsController instance;
	
	/** The create session panel */
	protected CreateSessionPanel panel;

	private GetUnimportedRequirementsController() {}

	/**
	 * Instantiates a new controller
	 */
	public static GetUnimportedRequirementsController getInstance() {
		if (instance == null) {
			instance = new GetUnimportedRequirementsController();
		}
		return instance;
	}

	/**
	 * Sends a request to get all the requirements that are not imported
	 */
	public void refreshData() {
		final Request request = Network.getInstance().makeRequest(
				"requirementmanager/requirement", HttpMethod.GET);
		request.addObserver(new GetUnimportedRequirementsRequestObserver(this));
		request.send();
	}

	/**
	 * This method is called by the {@link RetrieveFreeRequirementsRequestObserver}
	 * when the response is received. It then calls refreshUnimportedRequirements() of 
	 * ImportRequirementsTableModel
	 * 
	 * @param requirements
	 *            an array list of requirements returned by the server
	 * @throws NotImplementedException
	 */
	public void receivedData(Requirement[] requirements) {
		ArrayList<Requirement> filteredRequirements = new ArrayList<Requirement>();
		for (Requirement requirement : requirements) {
			if (requirement.getIteration().equals("Backlog")) {
				filteredRequirements.add(requirement);
			}
		}

		ImportRequirementsTableModel.getInstance()
				.refreshUnimportedRequirements(filteredRequirements);
	}

	/**
	 * This method is called by the
	 * {@link RetrieveFreeRequirementsRequestObserver} when an error occurs
	 * retrieving the requirements from the server.
	 */
	public void errorReceivingData(String error) {
		JOptionPane.showMessageDialog(panel,
				"An error occurred retrieving requirements from the server. "
						+ error, "Error Communicating with Server",
				JOptionPane.ERROR_MESSAGE);
	}
}
