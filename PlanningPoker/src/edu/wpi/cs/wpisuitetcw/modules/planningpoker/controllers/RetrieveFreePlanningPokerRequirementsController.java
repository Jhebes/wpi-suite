/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.CreateSessionPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.ViewSessionTableModel;
import edu.wpi.cs.wpisuitetng.exceptions.NotImplementedException;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Controller to handle retrieving free requirements from the server and
 * displaying them in the {@link CreateSessionPanel}
 */
public class RetrieveFreePlanningPokerRequirementsController {
	private static RetrieveFreePlanningPokerRequirementsController instance;
	/** The create session panel */
	protected CreateSessionPanel panel;

	/** The requirements retrieved from the server */
	protected PlanningPokerRequirement[] data = null;

	/**
	 * Constructs a new RetrieveFreePlanningPokerRequirementsController
	 * 
	 * @param panel
	 *            the create session panel
	 */
	private RetrieveFreePlanningPokerRequirementsController() {

	}

	public static RetrieveFreePlanningPokerRequirementsController getInstance() {
		if (instance == null) {
			instance = new RetrieveFreePlanningPokerRequirementsController();
		}
		return instance;
	}

	/**
	 * Sends a request for all of the requirements
	 * 
	 * @throws NotImplementedException
	 */
	public void refreshData() {
		final RequestObserver requestObserver = new RetrieveFreePlanningPokerRequirementsRequestObserver(
				this);
		Request request;
		request = Network.getInstance().makeRequest(
				"planningpoker/requirement", HttpMethod.GET);
		request.addObserver(requestObserver);
		request.send();
	}

	/**
	 * This method is called by the
	 * {@link RetrieveFreePlanningPokerRequirementsRequestObserver} when the
	 * response is received
	 * 
	 * @param requirements
	 *            an array list of requirements returned by the server
	 * @throws NotImplementedException
	 */
	public void receivedData(ArrayList<PlanningPokerRequirement> requirements) {
		ViewSessionTableModel.getInstance().refreshRequirements(requirements);
	}

	/**
	 * This method is called by the
	 * {@link RetrieveFreePlanningPokerRequirementsRequestObserver} when an
	 * error occurs retrieving the requirements from the server.
	 */
	public void errorReceivingData(String error) {
		JOptionPane.showMessageDialog(panel,
				"An error occurred retrieving requirements from the server. "
						+ error, "Error Communicating with Server",
				JOptionPane.ERROR_MESSAGE);
	}
}
