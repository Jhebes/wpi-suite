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
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerVote;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.CreateSessionPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.SessionInProgressPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.ViewSessionTableModel;
import edu.wpi.cs.wpisuitetng.exceptions.NotImplementedException;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Controller to handle retrieving free requirements from the server.
 */
public class GetRequirementsVotesController implements ActionListener{
	/** The create session panel */
	protected SessionInProgressPanel view;

	/** The requirements retrieved from the server */
	protected PlanningPokerRequirement[] data = null;

	
	//The id of the session to retrieve
	private PlanningPokerSession session;
	//The requirement to get votes for
	private PlanningPokerRequirement req;
	/**
	 * Constructs a new RetrieveFreePlanningPokerRequirementsController
	 */
	public GetRequirementsVotesController(SessionInProgressPanel view, PlanningPokerSession session) {
		this.session = session;
		this.view = view;
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
		this.req = this.session.getReqByName(this.view.getSelectedRequirement());
		PlanningPokerRequirement r = session.getReqByName(this.req.getName());
		//ArrayList<PlanningPokerVote> votes = new ArrayList<PlanningPokerVote>();

		System.out.println("Votes for selected requirement:");
		for(PlanningPokerVote v : r.votes){
			System.out.print(v.getCardValue()+ " ");
		}
		System.out.println();
		view.setVoteList(r.votes);
	}

	/**
	 * This method is called by the
	 * {@link GetRequirementsVotesRequestObserver} when an
	 * error occurs retrieving the requirements from the server.
	 */
	public void errorReceivingData(String error) {
		JOptionPane.showMessageDialog(view,
				"An error occurred retrieving requirements from the server. "
						+ error, "Error Communicating with Server",
				JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		final Request request = Network.getInstance().makeRequest("planningpoker/session/".concat(String.valueOf(this.session.getID())), HttpMethod.GET);
		request.addObserver(new GetRequirementsVotesRequestObserver(this));
		request.send();
	}
}
