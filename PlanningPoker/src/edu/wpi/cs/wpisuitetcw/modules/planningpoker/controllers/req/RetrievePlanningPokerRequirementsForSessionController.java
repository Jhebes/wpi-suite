/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.req;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.session.CreateSessionPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.tablemanager.RequirementTableManager;
import edu.wpi.cs.wpisuitetng.exceptions.NotImplementedException;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * A singleton controller that retrieves free requirements from the server.
 */
public class RetrievePlanningPokerRequirementsForSessionController {
	
	/** The create session panel */
	protected CreateSessionPanel panel;

	/** The requirements retrieved from the server */
	protected PlanningPokerRequirement[] data = null;
	
	private int target;
	
	/** An instance of this controller */
	private static RetrievePlanningPokerRequirementsForSessionController instance = null;
	
	/**
	 * Return an instance of RetrievePlanningPokerRequirementsForSessionController
	 * @return Return an instance of RetrievePlanningPokerRequirementsForSessionController
	 */
	public static RetrievePlanningPokerRequirementsForSessionController getInstance() {
		if (instance == null) {
			instance = new RetrievePlanningPokerRequirementsForSessionController();
		}
		return instance;
	}

	/**
	 * Sends a request to get all of the requirements of a session
	 */
	public void refreshData(int t){
		this.target = t;
		Logger.getLogger("PlanningPoker").log(Level.INFO, "Refreshing table for session " + t);
		final Request request = Network.getInstance().makeRequest("planningpoker/session/", HttpMethod.GET);
		request.addObserver(new RetrievePlanningPokerRequirementsForSessionRequestObserver(this));
		request.send();
	}

	/**
	 * This method is called by the
	 * {@link RetrievePlanningPokerRequirementsForSessionRequestObserver} when the
	 * response is received
	 * 
	 * @param session
	 *            an array list of requirements returned by the server
	 * @throws NotImplementedException
	 */
	public void receivedData(PlanningPokerSession session){
		final RequirementTableManager a = new RequirementTableManager();
		a.refreshRequirements(this.target, session.getRequirements());
	}

	/**
	 * This method is called by the
	 * {@link RetrievePlanningPokerRequirementsForSessionRequestObserver} when an
	 * error occurs retrieving the requirements from the server.
	 */
	public void errorReceivingData(String error) {
		JOptionPane.showMessageDialog(panel,
				"An error occurred retrieving requirements from the server. "
						+ error, "Error Communicating with Server",
				JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * @return The target ID
	 */
	public int getTarget() {
		return target;
	}

}
