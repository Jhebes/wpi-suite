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

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.get.session.GetAllSessionsController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.stash.SessionStash;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ViewEventManager;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * A singleton controller that retrieves all sessions from the core
 * and publishes them to the view
 */
public class GetAllRequirementsController {

	/** An instance of this controller */
	private static GetAllRequirementsController instance = null;

	/**
	 * Instantiates a new controller
	 * Private because this is a singleton.
	 */
	private GetAllRequirementsController() {}

	public static GetAllRequirementsController getInstance() {
		if (instance == null) {
			instance = new GetAllRequirementsController();
		}
		return instance;
	}
	
	/**
	 * Add the given list of requirements to the SessionStash
	 * @param sessions A list of Requirements that would be
	 * added to SessionStash
	 */
	public void receivedRequirements(List<Requirement> requirements) {
		//For every requirement
		for(Requirement r: requirements){
			boolean isContained = false;
			//Check if a requirement by the same name as the import candidate is present
			//in any session
			for(PlanningPokerSession s : SessionStash.getInstance().getSessions()){
				for(PlanningPokerRequirement ppr : s.getRequirements()){
					isContained = isContained || (ppr.getName().equals(r.getName()));
					if(isContained){
						break;
					}
				}
				if(isContained){
					break;
				}
			}
			
			//If not, add it to Session 1 (The NULLSESSION)
			if(!isContained){
				PlanningPokerRequirement newReq = new PlanningPokerRequirement();
				newReq.setName(r.getName());
				newReq.setDescription(r.getDescription());
				newReq.setCorrespondingReqManagerID(r.getId());
				SessionStash.getInstance().getSessionByID(1).addRequirement(newReq);
			}
		}
		GetAllSessionsController.getInstance().retrieveSessions();
	}

	/**
	 * Send a request to retrieve the requirements from the database.
	 */
	public void retrieveRequirements() {
		// Send a request to the core to retrieve the sessions
		final Request request = Network.getInstance().makeRequest(
				"requirementmanager/requirement", HttpMethod.GET);
		request.addObserver(new GetAllRequirementsRequestObserver(this));
		request.send(); // send the request
	}

}
