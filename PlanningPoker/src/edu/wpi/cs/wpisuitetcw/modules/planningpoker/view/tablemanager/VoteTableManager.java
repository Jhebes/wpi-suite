/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.tablemanager;

import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.req.RetrievePlanningPokerRequirementsForSessionController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerVote;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.RequirementTableModel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.VoteTableModel;

public class VoteTableManager{
	private static HashMap<UUID, VoteTableModel> t = new HashMap<UUID, VoteTableModel>();

	public VoteTableModel get(UUID i){
		System.out.println("Processing query for table for requirement " + i);
		//this.fetch(i);
		return VoteTableManager.t.get(i);
	}
	
	public void init(UUID i){
		System.out.println("Initializing session " + i.toString());
		VoteTableModel a = VoteTableManager.t.get(i);
		if(a == null){
			a = new VoteTableModel();
			
		}
		VoteTableManager.t.put(i, a);
	}
	
	public void refreshRequirements(UUID i, List<PlanningPokerVote> votes) {
		
		VoteTableModel a = VoteTableManager.t.get(i);
		if(a == null){
			System.out.println("Not present, building");
			a = new VoteTableModel();
			
		}
		a.refreshVotes(votes);
		VoteTableManager.t.put(i, a);
		System.out.println("Done");
	}
	public void fetch(UUID i){
		System.out.println("Fetching session details for requirements " + i.toString());
		RetrievePlanningPokerRequirementsForSessionController a = new RetrievePlanningPokerRequirementsForSessionController();
		//a.refreshData(i);
	}
	
	
}
