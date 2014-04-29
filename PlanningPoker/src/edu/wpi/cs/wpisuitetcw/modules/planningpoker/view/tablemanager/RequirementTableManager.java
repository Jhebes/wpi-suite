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

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.stash.SessionStash;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.session.RequirementTableModel;

/**
 * A requirement table manager provides a mapping of integers to table models.
 */
public class RequirementTableManager {
	/** Hashmap of table models for each session ID */
	private static Map<Integer, RequirementTableModel> t = new HashMap<Integer, RequirementTableModel>();

	/**
	 * Gets the ith table model in the mapping
	 * @param i The ID of the requirement table model to fetch
	 * @return The matching requirement table model
	 */
	public RequirementTableModel get(int i) {
		this.fetch(i);
		return RequirementTableManager.t.get(i);
	}

	/**
	 * Initializes the ith table model
	 * @param i The I of the requirement table model to initalize
	 */
	public void init(int i) {
		RequirementTableModel a = RequirementTableManager.t.get(i);
		if (a == null) {
			a = new RequirementTableModel();

		}
		RequirementTableManager.t.put(i, a);
	}

	/**
	 * Updates the requirement table models, refreshing them for a particular model
	 * @param i The ID of the requirement table model to fetch
	 * @param requirements The requirements received from the server
	 */
	public void refreshRequirements(int i,
			List<PlanningPokerRequirement> requirements) {
		RequirementTableModel a = RequirementTableManager.t.get(i);
		if (a == null) {
			a = new RequirementTableModel();

		}
		a.refreshRequirements(requirements);
		RequirementTableManager.t.put(i, a);
	}

	/**
	 * Fetches and updates new requirements from the server for the ith table model
	 * @param i The ID of the requirement talbe model to fetch
	 */
	public void fetch(int i) {
		final List<PlanningPokerRequirement> reqs = SessionStash.getInstance()
				.getSessionByID(i).getRequirements();
		this.refreshRequirements(i, reqs);
	}
}
