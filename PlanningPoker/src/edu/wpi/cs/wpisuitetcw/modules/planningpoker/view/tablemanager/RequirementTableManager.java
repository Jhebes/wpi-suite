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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.req.RetrievePlanningPokerRequirementsForSessionController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.stash.SessionStash;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.RequirementTableModel;

public class RequirementTableManager {
	/** Hashmap of table models for each session ID */
	private static HashMap<Integer, RequirementTableModel> t = new HashMap<Integer, RequirementTableModel>();

	public RequirementTableModel get(int i) {
		System.out.println("Processing query for table for session " + i);
		this.fetch(i);
		return RequirementTableManager.t.get(i);
	}
	
	public void init(int i){
		System.out.println("Initializing session " + String.valueOf(i));
		RequirementTableModel a = RequirementTableManager.t.get(i);
		if(a == null){
			a = new RequirementTableModel();
			
		}
		RequirementTableManager.t.put(i, a);
	}
	
	public void refreshRequirements(int i, ArrayList<PlanningPokerRequirement> requirements) {
		RequirementTableModel a = RequirementTableManager.t.get(i);
		if(a == null){
			System.out.println("Not present, building");
			a = new RequirementTableModel();
			
		}
		a.refreshRequirements(requirements);
		RequirementTableManager.t.put(i, a);
		System.out.println("Done");
	}
	public void fetch(int i){
		System.out.println("Fetching session details for session " + i);
		ArrayList<PlanningPokerRequirement> reqs = SessionStash.getInstance().getSessionByID(i).getRequirements();
		this.refreshRequirements(i, reqs);
	
	}
	
	
}
