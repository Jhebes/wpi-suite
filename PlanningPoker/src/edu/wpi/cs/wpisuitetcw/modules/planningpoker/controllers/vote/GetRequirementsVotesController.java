/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.vote;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

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
public class GetRequirementVotesController implements ActionListener{
	/** The create session panel */
	protected CreateSessionPanel panel;

	/** The requirements retrieved from the server */
	protected PlanningPokerRequirement[] data = null;

	
	//The id of the session to retrieve
	private int id;
	//The requirement to get votes for
	private PlanningPokerRequirement req;
	/**
	 * Constructs a new RetrieveFreePlanningPokerRequirementsController
	 */
	private GetRequirementVotesController(int id, PlanningPokerRequirement req) {
		this.id = id;
		this.req = req;
	}
	

	/**
	 * This method is called by the
	 * {@link GetRequirementsVotesRequestObserver} when the
	 * response is received
	 * 
	 * @param session
	 *            an array list of requirements returned by the server
	 * @throws NotImplementedException
	 */
	public void receivedData(PlanningPokerSession session){
		PlanningPokerRequirement r = session.getReqByName(this.req.getName());
	}

	/**
	 * This method is called by the
	 * {@link GetRequirementsVotesRequestObserver} when an
	 * error occurs retrieving the requirements from the server.
	 */
	public void errorReceivingData(String error) {
		JOptionPane.showMessageDialog(panel,
				"An error occurred retrieving requirements from the server. "
						+ error, "Error Communicating with Server",
				JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		final Request request = Network.getInstance().makeRequest("planningpoker/session/".concat(String.valueOf(this.id)), HttpMethod.GET);
		request.addObserver(new GetRequirementsVotesRequestObserver(this));
		request.send();
	}
}
