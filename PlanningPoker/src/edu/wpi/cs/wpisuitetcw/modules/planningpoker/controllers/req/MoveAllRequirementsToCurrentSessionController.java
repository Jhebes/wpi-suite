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
import java.util.List;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.stash.SessionStash;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.viewSessionComp.ViewSessionReqPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.tablemanager.RequirementTableManager;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;


/**
 * This controller adds all the requirements to the specified session
 * 
 */
public class MoveAllRequirementsToCurrentSessionController implements ActionListener {

	private PlanningPokerSession session = null;
	private ViewSessionReqPanel view;
	
	/**
	 * Construct the MoveAllRequirementsToAllController by storing the given
	 * PlanningPokerSession and ViewSessionReqPanel
	 * @param s A PlanningPokerSession that would be stored
	 * @param v A ViewSessionReqPanel that would be stored
	 */
	public MoveAllRequirementsToCurrentSessionController(PlanningPokerSession s, ViewSessionReqPanel v) {
		session = s;
		view = v;
	}

	/**
	 * TODO WHat?
	 * @param s
	 */
	public void receivedData(PlanningPokerSession s){
		PlanningPokerRequirement r;
		
		for(String a : view.getAllLeftRequirements()){
				r = s.getReqByName(a);
				List<PlanningPokerRequirement> d = new ArrayList<PlanningPokerRequirement>();
				d.add(r);
				s.deleteRequirements(d);
				session.addRequirement(r);
		}

		s.save();
		session.save();
		
		final RequirementTableManager a1 = new RequirementTableManager();
		a1.refreshRequirements(1, s.getRequirements());
		final RequirementTableManager a2 = new RequirementTableManager();
		a2.refreshRequirements(session.getID(), session.getRequirements());
		view.getAllReqTable().repaint();
		view.getSessionReqTable().repaint();

		view.validateActivateSession();
		view.refreshMoveButtons();
	}
	
	/*
	 * This method is called when the user clicks the vote button
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		receivedData(SessionStash.getInstance().getDefaultSession());
	}
}
