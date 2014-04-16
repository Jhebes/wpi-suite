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
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.req;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.GenericPUTRequestObserver;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.entitymanagers.ViewSessionTableManager;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.viewSessionComp.ViewSessionReqPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller responds when the user clicks the "Create" button by using
 * all entered information to construct a new session and storing in the
 * database
 *  
 */
public class AddRequirementToSessionController implements ActionListener {


	private ViewSessionReqPanel view;

	
	public AddRequirementToSessionController(ViewSessionReqPanel view) {
		this.view = view;
		System.out.println("Constructed the action listener");
	}

	
	public void addReq(PlanningPokerSession s){
		
		System.out.println("Constructing update");
		PlanningPokerRequirement r = new PlanningPokerRequirement();
		r.setName(this.view.getNewReqName());
		this.view.clearNewReqName();
		r.setDescription(this.view.getNewReqDesc());
		this.view.clearNewReqDesc();
		s.addRequirement(r);
		
		System.out.println("Sending update");
		//Get Session 1
		//Update the session remotely
		final Request request = Network.getInstance().makeRequest(
				"planningpoker/session", HttpMethod.POST);
		request.setBody(s.toJSON());
		// Listen for the server's response
		request.addObserver(new GenericPUTRequestObserver());
		// Send the request on its way
		request.send();
		System.out.println("Update Sent");
		
		
		
		final Request request2 = Network.getInstance().makeRequest("planningpoker/session/".concat(String.valueOf(this.view.session.getID())), HttpMethod.POST);
		request2.setBody(s.toJSON());
		request2.addObserver(new GenericPUTRequestObserver());
		request2.send();
		
		new ViewSessionTableManager().fetch(s.getID());
		
		this.view.sessionReqTable.repaint();
		
		
		
	}
	
	/** builds a new default session 
	 * 
	 */
	public void buildNewSession1(){
		PlanningPokerSession pp1 = new PlanningPokerSession();
		pp1.setID(1);
		final Request request2 = Network.getInstance().makeRequest("planningpoker/session/1", HttpMethod.PUT);
		request2.setBody(pp1.toJSON());
		request2.addObserver(new GenericPUTRequestObserver());
		request2.send();
	}
	
	
	
	/*
	 * This method is called when the user clicks the vote button
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		
		System.out.println("Requesting null session");
		
		final Request request = Network.getInstance().makeRequest("planningpoker/session/".concat(String.valueOf(this.view.session.getID())), HttpMethod.GET);
		request.addObserver(new AddRequirementToSessionRequestObserver(this));
		request.send();
		
	
		System.out.println("Request Sent");
	
	}
}
