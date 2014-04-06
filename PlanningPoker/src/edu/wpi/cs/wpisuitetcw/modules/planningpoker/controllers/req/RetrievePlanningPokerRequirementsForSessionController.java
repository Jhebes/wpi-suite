/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.req;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.entitymanagers.ViewSessionTableManager;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.CreateSessionPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.ViewSessionTableModel;
import edu.wpi.cs.wpisuitetng.exceptions.NotImplementedException;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Controller to handle retrieving free requirements from the server.
 */
public class RetrievePlanningPokerRequirementsForSessionController{
	private static RetrievePlanningPokerRequirementsForSessionController instance;
	/** The create session panel */
	protected CreateSessionPanel panel;

	/** The requirements retrieved from the server */
	protected PlanningPokerRequirement[] data = null;
	public int target;
	/**
	 * Constructs a new RetrieveFreePlanningPokerRequirementsController
	 */
	public RetrievePlanningPokerRequirementsForSessionController() {
	}
	
	public static RetrievePlanningPokerRequirementsForSessionController getInstance() {
		if (instance == null) {
			instance = new RetrievePlanningPokerRequirementsForSessionController();
		}
		return instance;
	}

	/**
	 * Sends a request for all of the requirements
	 */
	public void refreshData(int t){
		this.target = t;
		System.out.println("Refreshing table for session " + t);
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
//		System.out.println("Current requirements are:");
//		for(PlanningPokerRequirement r: session.getRequirements()){
//			System.out.println(r.getName());
//		}
		ViewSessionTableManager a = new ViewSessionTableManager();
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


}
