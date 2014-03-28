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
 *    JPage
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers;

import javax.swing.JOptionPane;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.CreateSessionPanel;
import edu.wpi.cs.wpisuitetng.exceptions.NotImplementedException;

/**
 * Controller to handle retrieving free requirements from the server and
 * displaying them in the {@link CreateSessionPanel}
 */
public class RetrieveFreePlanningPokerRequirementsController {

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
	public RetrieveFreePlanningPokerRequirementsController(
			CreateSessionPanel panel) {
		this.panel = panel;
	}

	/**
	 * Sends a request for all of the requirements
	 * 
	 * @throws NotImplementedException
	 */
	public void refreshData() throws NotImplementedException {
		throw new NotImplementedException();
	}

	/**
	 * This method is called by the
	 * {@link RetrieveFreePlanningPokerRequirementsRequestObserver} when the
	 * response is received
	 * 
	 * @param requirements
	 *            an array of requirements returned by the server
	 * @throws NotImplementedException
	 */
	public void receivedData(PlanningPokerRequirement[] requirements)
			throws NotImplementedException {
		throw new NotImplementedException();
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
