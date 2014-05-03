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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.req.RetrievePlanningPokerRequirementsForSessionController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerVote;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.VoteTableModel;

/**
 * A vote table manager provides a mapping of integers to table models.
 */
public class VoteTableManager{
	private static Map<UUID, VoteTableModel> t = new HashMap<UUID, VoteTableModel>();

	/**
	 * Gets the ith table model in the mapping
	 * @param i The ID of the session table model to fetch
	 * @return The matching session table model
	 */
	public VoteTableModel get(UUID i){
		Logger.getLogger("PlanningPoker").log(Level.INFO, "Processing query for table for session " + i);
		//this.fetch(i);
		return VoteTableManager.t.get(i);
	}
	
	/**
	 * Initializes the ith table model
	 * @param i The I of the session table model to initalize
	 */
	public void init(UUID i){
		Logger.getLogger("PlanningPoker").log(Level.INFO, "Initializing session " + i.toString());
		VoteTableModel a = VoteTableManager.t.get(i);
		if(a == null){
			a = new VoteTableModel();
			
		}
		VoteTableManager.t.put(i, a);
	}
	
	/**
	 * Updates the session table models, refreshing them for a particular model
	 * @param i The ID of the session table model to fetch
	 * @param votes The sessions received from the server
	 */
	public void refreshSessions(UUID i, List<PlanningPokerVote> votes) {
		
		VoteTableModel a = VoteTableManager.t.get(i);
		if(a == null){
			Logger.getLogger("PlanningPoker").log(Level.INFO, "Not present, building");
			a = new VoteTableModel();
			
		}
		a.refreshVotes(votes);
		VoteTableManager.t.put(i, a);
		Logger.getLogger("PlanningPoker").log(Level.INFO, "Done");
	}
	
	/**
	 * Fetches and updates new sessions from the server for the ith table model
	 * @param i The ID of the session talbe model to fetch
	 */
	public void fetch(UUID i){
		Logger.getLogger("PlanningPoker").log(Level.INFO, "Fetching session details for sessions " + i.toString());
		new RetrievePlanningPokerRequirementsForSessionController();
		//a.refreshData(i);
	}
}
