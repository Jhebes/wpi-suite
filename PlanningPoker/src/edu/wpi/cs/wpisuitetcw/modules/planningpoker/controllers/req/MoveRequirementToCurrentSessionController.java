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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.put.GenericPUTRequestObserver;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerVote;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.SessionInProgressPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.viewSessionComp.ViewSessionReqPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.tablemanager.RequirementTableManager;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller adds all the requirements to the specified session
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
		
		for(String a : this.view.getLeftSelectedRequirements()){
				r = s.getReqByName(a);
				ArrayList<PlanningPokerRequirement> d = new ArrayList<PlanningPokerRequirement>();
				d.add(r);
				s.deleteRequirements(d);
				session.addRequirement(r);
				
		}
		
		s.update();
		session.update();
		
		RequirementTableManager a1 = new RequirementTableManager();
		a1.refreshRequirements(1, s.getRequirements());
		RequirementTableManager a2 = new RequirementTableManager();
		a2.refreshRequirements(session.getID(), session.getRequirements());
		this.view.allReqTable.repaint();
		this.view.sessionReqTable.repaint();
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
		request.addObserver(new MoveRequirementToCurrentSessionRequestObserver(this));
		request.send();
		
		

	}
}
