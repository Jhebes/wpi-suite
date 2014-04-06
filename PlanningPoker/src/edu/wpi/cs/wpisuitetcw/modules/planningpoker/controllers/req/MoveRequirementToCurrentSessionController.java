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
import java.util.ArrayList;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.GenericPUTRequestObserver;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.entitymanagers.ViewSessionTableManager;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerVote;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.SessionInProgressPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.viewSessionComp.ViewSessionReqPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller adds all the requirements to the specified session
 * 
 * @author Josh Hebert
 * 
 */
public class MoveRequirementToCurrentSessionController implements ActionListener {

	private PlanningPokerSession session = null;
	private ViewSessionReqPanel view;

	public MoveRequirementToCurrentSessionController(PlanningPokerSession s, ViewSessionReqPanel v) {
		this.session = s;
		this.view = v;
	}

	public void receivedData(PlanningPokerSession s){
		PlanningPokerRequirement r;
		
		//for(String a : this.view.getLeftSelectedRequirements()){
				r = s.getReqByName("TEST");
				ArrayList<PlanningPokerRequirement> d = new ArrayList<PlanningPokerRequirement>();
				d.add(r);
				s.deleteRequirements(d);
				session.addRequirement(r);
				ViewSessionTableManager.getInstance().refreshRequirements(0, s.getRequirements());
				ViewSessionTableManager.getInstance().refreshRequirements(session.getID(), session.getRequirements());
				
		//}
		
		final Request request = Network.getInstance().makeRequest("planningpoker/session/".concat(String.valueOf(s.getID())), HttpMethod.POST);
		request.setBody(session.toJSON());
		request.addObserver(new GenericPUTRequestObserver(this));
		request.send();
		
		final Request request2 = Network.getInstance().makeRequest("planningpoker/session/1", HttpMethod.POST);
		request2.setBody(s.toJSON());
		request2.addObserver(new GenericPUTRequestObserver(this));
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
		final Request request = Network.getInstance().makeRequest("planningpoker/session/1", HttpMethod.GET);
		request.addObserver(new MoveRequirementToCurrentRequestObserver(this));
		request.send();
		
		

	}
}
